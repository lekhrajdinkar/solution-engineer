# Docker architecture and container fundamentals

These notes focus on the mental models and trade-offs expected in senior software
engineer (SSE) and forward-deployed engineer (FDE) interviews. Commands and
Dockerfile examples belong in [the developer guide](06_developer-guide-2.md).

## 1. What problem does Docker solve?

Docker packages an application, its user-space dependencies, and default runtime
configuration into an image. The same immutable artifact can move through
development, CI, staging, and production.

The main benefits are:

- **Consistency:** the tested artifact is the deployed artifact.
- **Isolation:** processes receive isolated views of the filesystem, process tree,
  network, hostname, users, and other kernel resources.
- **Efficiency:** containers share the host kernel, so they usually start faster
  and use fewer resources than virtual machines.
- **Portability:** OCI-compatible images work across compatible container
  runtimes, provided that the host OS and CPU architecture match the image.

> **Important:** containers are not small virtual machines. A Linux container
> needs a Linux kernel. Docker Desktop runs Linux containers on macOS and Windows
> by using a lightweight Linux virtual machine.

## 2. High-level architecture

```text
Developer / automation
        |
        | docker CLI, SDK, or Compose
        v
Docker Engine API (Unix socket, named pipe, or protected TCP endpoint)
        |
        v
dockerd ------------------------------------------------ Registry
  |         manages images, containers, networks, volumes   ^
  |                                                         | pull/push
  +--> BuildKit (image builds)                              |
  |
  +--> containerd (image transfer and container lifecycle)
           |
           +--> containerd-shim (supervises the container)
                    |
                    +--> runc (creates the OCI container, then exits)
                              |
                              v
                       container process
```

### Component responsibilities

| Component | Responsibility |
| --- | --- |
| `docker` CLI | Converts user commands into Docker Engine API requests. |
| `dockerd` | Manages Docker objects and coordinates builds, networking, storage, and container lifecycle. |
| BuildKit | Executes image builds using a concurrent, cache-aware build graph. |
| `containerd` | Manages image content and the container lifecycle for Docker Engine. |
| `containerd-shim` | Supervises a running container and reports its status without keeping the low-level runtime alive. |
| `runc` | Low-level OCI runtime that configures namespaces, cgroups, mounts, capabilities, and starts the container process. |
| Registry | Stores and distributes image manifests and content-addressed blobs; examples include Docker Hub and Amazon ECR. |

The exact internals can evolve. The durable design idea is the separation between
the user-facing engine, a high-level runtime, and an OCI-compatible low-level
runtime.

![Docker architecture](../../99_img/2025/docker/crash-course/img.png)

## 3. Image, container, and process

### Image

An image is an immutable, content-addressed package composed of:

- ordered, read-only filesystem layers;
- an image configuration containing metadata such as the default user,
  environment, working directory, `ENTRYPOINT`, and `CMD`;
- a manifest that identifies the configuration and layers for a platform; and
- optionally, an image index that points to manifests for several platforms such
  as `linux/amd64` and `linux/arm64`.

Tags such as `my-api:1.4` are mutable names. A digest such as
`my-api@sha256:...` identifies immutable content. Use digests when exact
reproducibility matters.

Not every Dockerfile instruction creates a filesystem layer. Instructions such as
`RUN`, `COPY`, and `ADD` change the filesystem; instructions such as `ENV`,
`CMD`, and `EXPOSE` primarily change image metadata.

### Container

A container is a runtime configuration plus a process started from an image. At
creation time, the runtime adds a writable layer above the image's read-only
layers and configures isolation, resource limits, mounts, networking, and
security policy.

```text
container = image + runtime configuration + writable layer + running process(es)
```

The container lives while its main process (PID 1 inside the container) lives.
If PID 1 exits, the container stops. Additional processes may be started with
`docker exec`, but containers should normally have one service responsibility,
not necessarily exactly one operating-system process.

Changes in the writable layer disappear when the container is removed. Durable
or shared data belongs in a volume, bind mount, database, or object store.

![Image and container relationship](../../99_img/2025/docker/crash-course/img_2.png)

## 4. How Linux containers are isolated

Containers are ordinary host processes constrained by kernel features. They do
not contain their own kernel.

### Namespaces: control what a process can see

| Namespace | Isolates |
| --- | --- |
| PID | Process IDs and process tree. A process can have one PID in the container and another on the host. |
| Mount | Filesystem mount points. |
| Network | Interfaces, routes, firewall rules, and ports. |
| UTS | Hostname and domain name. |
| IPC | Shared memory, semaphores, and message queues. |
| User | User and group ID mappings; used by rootless mode and user-namespace remapping. |
| Cgroup | The process's view of cgroup membership. |

### Cgroups: control what a process can use

Control groups account for and limit resources such as CPU, memory, process
count, and I/O. Namespace isolation without resource limits does not prevent one
container from exhausting the host.

### Filesystem isolation

A storage driver presents the image's read-only layers and the container's thin
writable layer as one filesystem. On modern Linux hosts this is commonly
`overlay2`, though the implementation depends on the platform and configuration.

### Security controls

Docker combines several controls rather than relying on a single boundary:

- Linux capabilities remove many privileges from the process;
- seccomp restricts system calls;
- AppArmor or SELinux can enforce mandatory access-control policy;
- read-only filesystems and controlled mounts reduce writable surface area;
- user namespaces or rootless mode reduce the impact of container root; and
- cgroups limit resource consumption.

Isolation is configurable and can be weakened. Options such as `--privileged`,
host namespace sharing, broad capabilities, sensitive bind mounts, or mounting
the Docker socket materially increase risk.

## 5. Container root is a security boundary question

Images often default to UID 0. In a standard rootful Docker setup, container UID
0 is also UID 0 at the host-kernel level, although namespaces, capabilities,
seccomp, and other controls restrict it. Therefore, "root in a container is not
host root" is an unsafe simplification.

Production baseline:

- run the application as a non-root UID/GID;
- drop all capabilities and add back only those required;
- use a read-only root filesystem where practical;
- set CPU, memory, and PID limits;
- never bake credentials into an image or pass long-lived secrets in build args;
- avoid exposing the Docker daemon socket;
- scan images and generate an SBOM in CI;
- pin trusted base images and rebuild regularly for security updates; and
- consider rootless Docker, user-namespace remapping, or a stronger sandbox for
  untrusted workloads.

`CAP_SYS_ADMIN` is especially broad and should not be treated as a routine way to
fix permission problems. `CAP_NET_ADMIN` permits network administration inside
the applicable network namespace.

## 6. Container lifecycle

```text
image missing? --> pull --> create --> start --> running --> stop/kill --> exited
                              ^          |
                              |          +--> pause/unpause
                              +-------------- restart

exited --remove--> deleted container
image and persistent volumes remain separate objects
```

What happens during `docker run IMAGE COMMAND`:

1. The client sends a request to `dockerd`.
2. Docker resolves and pulls the image if it is absent locally.
3. Docker creates container metadata and a writable filesystem layer.
4. Docker configures namespaces, cgroups, mounts, networking, and security.
5. The runtime starts the configured process.
6. Docker attaches the terminal or returns immediately in detached mode.

`docker run` is effectively `docker create` followed by `docker start`.
`docker exec` starts another process in an already-running container.

## 7. Docker Engine versus Docker Desktop

| Docker Engine on Linux | Docker Desktop |
| --- | --- |
| Runs Linux containers directly using the host Linux kernel. | Provides Docker tooling on macOS, Windows, and Linux desktop environments. |
| Usually managed as a system service. | Includes a UI, CLI, Engine, Compose, BuildKit, and integrations. |
| Host paths and networking map directly to the Linux host. | Linux containers on macOS/Windows run inside a managed Linux VM, so files and networking cross a VM boundary. |

This distinction matters when diagnosing file performance, bind-mount ownership,
host networking, memory allocation, and architecture emulation on developer
laptops.

## 8. Containers versus virtual machines

| Concern | Container | Virtual machine |
| --- | --- | --- |
| Isolation boundary | Kernel features around host processes | Hypervisor and a separate guest kernel |
| Startup | Usually milliseconds to seconds | Usually seconds to minutes |
| Footprint | Application and user-space dependencies | Full guest operating system plus application |
| OS flexibility | Must be compatible with the host kernel | Can run a different guest OS/kernel |
| Security boundary | Lighter and more configurable | Usually stronger by default |

The choice is not binary. Production platforms commonly run containers inside
VMs to combine image portability and scheduling with a stronger tenant boundary.

## 9. OCI and portability

The Open Container Initiative standardizes three important boundaries:

- **Image Specification:** the image format and metadata;
- **Runtime Specification:** how an unpacked filesystem bundle is executed; and
- **Distribution Specification:** how registry content is pushed and pulled.

OCI compatibility improves interoperability, but it does not make an image
universally portable. Check the operating system, CPU architecture, required
kernel features, filesystem behavior, and external dependencies.

## 10. Host data and diagnostics

On a default rootful Linux Engine, Docker data is commonly stored under
`/var/lib/docker`; containerd content may be stored under
`/var/lib/containerd`. These are implementation details, not application APIs.
Do not edit these directories manually.

Useful diagnostic commands:

```bash
docker version                    # Client/server versions and API compatibility
docker info                       # Runtime, storage driver, cgroups, rootless status
docker system df                  # Docker disk usage
docker image inspect IMAGE        # Image config, layers, platform, and digests
docker container inspect NAME     # Runtime configuration and state
docker stats                      # Live resource metrics
docker events                     # Real-time daemon events
docker context ls                 # Available daemon endpoints
```

Prefer Docker contexts or SSH for remote daemons. An unauthenticated Docker API
on TCP port 2375 is effectively remote root access to the host.

## 11. Senior-level discussion prompts

### Why did a container exit immediately?

Its PID 1 exited. Inspect `docker logs`, the configured `ENTRYPOINT`/`CMD`, the
exit code in `docker inspect`, signals, health dependencies, and memory-pressure
events. Do not keep it alive with an artificial sleep unless it is a debugging
session.

### Why is the rebuilt image unexpectedly large?

Possible causes include copying a large build context, placing secrets or
artifacts in an earlier layer and deleting them only in a later layer, retaining
package caches, or shipping build tools in the runtime stage. Inspect layer sizes
and use `.dockerignore`, multi-stage builds, cache mounts, and a minimal runtime
base.

### Why does it work on one machine but not another?

Check CPU architecture, OS/kernel compatibility, image tag drift, bind-mounted
host files, environment and secrets, port conflicts, filesystem permissions,
resource limits, and dependency availability. "It is in a container" removes
some environmental variation, not all of it.

### When should a container not be used?

Examples include workloads requiring a different kernel, cases needing a strong
hostile-tenant boundary without an additional sandbox, or simple deployments
where container operational complexity offers no meaningful benefit.

## References
- https://learn.kodekloud.com/learn/courses/docker-training-course-for-the-absolute-beginner
- [Docker architecture overview](https://docs.docker.com/get-started/docker-overview/)
- [Docker Engine security](https://docs.docker.com/engine/security/)
- [Docker rootless mode](https://docs.docker.com/engine/security/rootless/)
- [Docker BuildKit](https://docs.docker.com/build/buildkit/)
- [OCI specifications](https://specs.opencontainers.org/)


