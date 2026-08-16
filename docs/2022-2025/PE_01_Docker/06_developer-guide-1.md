# Docker

## A. Commands

### 1. Images

#### Build an image

```bash
docker build -t repoName/image-1:version .
```

Use a specific Dockerfile:

```bash
docker build -t repoName/image-1:version -f Dockerfile-1 .
```

* `-t` → assigns a repository/name and tag.
* `.` → build context.

Example:

```bash
docker build -t myrepo/app:1.0 .
```

---

#### Registry authentication

```bash
docker login
```

By default, Docker logs in to Docker Hub.

For a private registry:

```bash
docker login private-registry-1
```

Credentials are stored locally by Docker's credential configuration.

Related commands:

```bash
docker pull image-name
docker push image-name
docker login registry-name
```

---

#### List images

```bash
docker images
```

---

#### Remove an image

```bash
docker rmi image-1
```

Can also use the image ID:

```bash
docker rmi <IMAGE_ID>
```

---

#### Tag an image

```bash
docker tag <IMAGE_ID> name:version
```

Example:

```bash
docker tag abc123 app:1.0
docker tag abc123 app:latest
```

---

### Connect to a Remote Docker Host

Docker CLI can communicate with a Docker daemon running on another machine through `DOCKER_HOST`.

```bash
export DOCKER_HOST=tcp://<REMOTE_HOST_IP>:2375
docker info
```

Or specify the remote host directly:

```bash
docker -H tcp://<REMOTE_HOST_IP>:2375 run ...
```

> ⚠️ Port `2375` normally means **unencrypted Docker API access**. Exposing it over a network is dangerous because anyone who can access the Docker daemon effectively has very powerful access to the host. Prefer SSH or TLS-protected Docker daemon access.

Example using SSH:

```bash
docker -H ssh://user@remote-host info
```

---

## 2. Containers

### `docker run`

Creates and starts a **new container** from an image.

Example:

```bash
docker run --rm -it alpine sh -c "id && capsh --print"
```

Useful for inspecting the user and Linux capabilities inside a container.

Possible output:

```text
uid=0(root) gid=0(root) groups=0(root)

Current:
cap_chown,
cap_dac_override,
cap_fowner,
...
+ep
```

Even though the process runs as `root`, Docker normally gives container root only a **limited set of Linux capabilities**.

---

### Common `docker run` options

| Option              | Meaning                                           |
| ------------------- | ------------------------------------------------- |
| `--rm`              | Remove the container automatically after it exits |
| `--name c1`         | Assign container name `c1`                        |
| `--cpus=0.5`        | Limit container to approximately half a CPU       |
| `--memory=500m`     | Limit container memory                            |
| `-i`                | Keep STDIN open / interactive                     |
| `-t`                | Allocate a pseudo-terminal                        |
| `-d`                | Run in detached/background mode                   |
| `-u` / `--user`     | Run the container process as a specific UID/user  |
| `--mount`           | Mount volumes or host directories                 |
| `-e KEY=value`      | Set environment variables                         |
| `-p host:container` | Publish a container port to the host              |
| `--network n1`      | Connect the container to network `n1`             |
| `--cap-add`         | Add Linux capabilities                            |
| `--cap-drop`        | Remove Linux capabilities                         |
| `--entrypoint`      | Override the image's `ENTRYPOINT`                 |

---

### Running as a specific user

```bash
docker run --user 1000 image-1
```

or:

```bash
docker run -u 1000 image-1
```

> 🔸 Be careful when mapping host users/UIDs into containers, especially when combined with bind mounts. File ownership and host-resource access can have unexpected security consequences.

---

### Linux capabilities

Add a capability:

```bash
docker run --cap-add CAP_SYS_ADMIN image-1
```

Another example:

```bash
docker run --cap-add CAP_NET_ADMIN image-1
```

Drop a capability:

```bash
docker run --cap-drop CAPABILITY-2 image-1
```

Example:

```bash
docker run --cap-drop NET_RAW image-1
```

> `CAP_SYS_ADMIN` is extremely powerful and should generally be avoided unless absolutely necessary.

---

### Mount a Docker volume

First create the volume:

```bash
docker volume create vol-1
```

Then mount it:

```bash
docker run \
  --mount type=volume,source=vol-1,target=/data \
  image-1
```

---

### Bind mount a host directory

```bash
docker run \
  --mount type=bind,source=/location/on/host,target=/location/on/container \
  image-1
```

Difference:

```text
Volume
Docker manages the storage location.

Bind Mount
You explicitly map a host filesystem path into the container.
```

---

### Environment variables

```bash
docker run \
  -e KEY1=value1 \
  -e KEY2=value2 \
  image-1
```

---

### Port mapping

```bash
docker run -p <HOST_PORT>:<CONTAINER_PORT> image-1
```

Example:

```bash
docker run -p 8080:80 nginx
```

Flow:

```text
localhost:8080
      ↓
Container:80
```

---

### Network

```bash
docker run --network n1 image-1
```

---

### Override `ENTRYPOINT`

If the Dockerfile contains:

```dockerfile
ENTRYPOINT ["python", "app-1.py"]
```

you can replace the entrypoint using:

```bash
docker run --entrypoint python image-1 app-2.py
```

---

### Image and command syntax

General structure:

```bash
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```

Example image:

```text
registry-1/repoName-1/image-1:latest
```

Example command:

```bash
docker run image-1 sleep 5000
```

The `COMMAND` is optional and can override the Dockerfile's `CMD`.

---

### Container management

List running containers:

```bash
docker ps
```

List all containers:

```bash
docker ps -a
```

Start:

```bash
docker start c1
```

Stop:

```bash
docker stop c1
```

Restart:

```bash
docker restart c1
```

Remove:

```bash
docker rm c1
```

---

### `docker exec`

Run a command inside an **already-running container**:

```bash
docker exec c1 <command>
```

Interactive shell:

```bash
docker exec -it c1 sh
```

or, when Bash exists:

```bash
docker exec -it c1 bash
```

Important distinction:

```text
docker run
    ↓
Creates + starts a NEW container

docker exec
    ↓
Runs a command inside an EXISTING running container
```

---

### Logs

```bash
docker logs c1
```

Follow logs continuously:

```bash
docker logs -f c1
```

`-f` = follow / live log trail.

---

### Inspect a container

```bash
docker inspect c1
```

Returns detailed JSON containing information such as:

* Networking
* IP addresses
* Mounts
* Environment variables
* Image information
* Runtime configuration
* Ports
* State

Useful for inspecting the container's network configuration.

---

## 3. Volumes

Create a named volume:

```bash
docker volume create vol-name-1
```

Inspect it:

```bash
docker volume inspect vol-name-1
```

Docker determines the actual host-side storage location.

To map a **specific host path**, use a bind mount instead:

```bash
--mount type=bind,source=/host/path,target=/container/path
```

---

## 4. Networks

Create a bridge network:

```bash
docker network create \
  --driver=bridge \
  --subnet=<SUBNET> \
  n1
```

Example:

```bash
docker network create \
  --driver=bridge \
  --subnet=172.20.0.0/16 \
  n1
```

Inspect network/container information:

```bash
docker inspect c1
```

You can also inspect the network directly:

```bash
docker network inspect n1
```

---

# B. Developer Guide

## `.dockerignore`

`.dockerignore` specifies files and directories that should **not be sent as part of the Docker build context**.

Example:

```text
.git
node_modules
.env
credentials.json
*.log
target/
```

Benefits:

* Reduces build context size.
* Speeds up builds.
* Prevents unnecessary files from being copied into the image.
* Helps prevent sensitive files such as `.env` and `credentials.json` from accidentally entering the build context/image.

---

# 1. Dockerfile

A Dockerfile is a text file containing instructions Docker uses to construct an image.

---

## `FROM`

Specifies the base image.

```dockerfile
FROM python:3.10-slim
```

---

## `ENTRYPOINT`

> Correct keyword: **`ENTRYPOINT`**, not `ENDPOINT`.

Specifies the primary executable that runs when the container starts.

Example:

```dockerfile
ENTRYPOINT ["python", "app-1.py"]
```

Arguments supplied after the image name in `docker run` are generally appended to the exec-form `ENTRYPOINT`.

Example:

```bash
docker run image-1 --debug
```

becomes conceptually:

```bash
python app-1.py --debug
```

---

## `CMD`

Provides the default command or default arguments.

Example with `ENTRYPOINT`:

```dockerfile
ENTRYPOINT ["python"]
CMD ["app-2.py"]
```

Default:

```text
python app-2.py
```

Running:

```bash
docker run image-1 app-3.py
```

results in:

```text
python app-3.py
```

`CMD` can also contain the complete default command:

```dockerfile
CMD ["python", "app-1.py"]
```

---

### `ENTRYPOINT` vs `CMD`

```text
ENTRYPOINT
    ↓
Main executable / fixed command

CMD
    ↓
Default command or default arguments
```

Common pattern:

```dockerfile
ENTRYPOINT ["python"]
CMD ["app.py"]
```

---

## `RUN`

Executes commands while the **image is being built**.

Example:

```dockerfile
RUN apt-get update && apt-get install -y curl
```

Python example:

```dockerfile
RUN pip install -r requirements.txt
```

`RUN` happens at build time.

```text
docker build
    ↓
RUN executes
```

It does not mean "run this every time the container starts."

---

## `COPY`

Copies files from the build context into the image.

```dockerfile
COPY . /app
```

Example:

```dockerfile
COPY requirements.txt .
```

---

## `ADD`

Similar to `COPY`, with extra behavior such as archive extraction and URL-related features.

For ordinary local file copying, prefer:

```dockerfile
COPY
```

over:

```dockerfile
ADD
```

unless `ADD`'s special functionality is actually needed.

---

## `WORKDIR`

Sets the working directory for subsequent instructions.

```dockerfile
WORKDIR /app
```

---

## `EXPOSE`

Documents the port the application expects to listen on.

```dockerfile
EXPOSE 8080
```

Important:

```text
EXPOSE 8080
```

does **not** automatically make the port accessible from the host.

To publish it:

```bash
docker run -p 8080:8080 image-1
```

---

## `ENV`

Defines an environment variable that exists in the image/container.

```dockerfile
ENV NODE_ENV=production
```

---

## `ARG`

Defines a build-time variable.

```dockerfile
ARG APP_VERSION=1.0
```

Can be overridden:

```bash
docker build \
  --build-arg APP_VERSION=2.0 \
  -t app:2.0 .
```

Difference:

```text
ARG
    ↓
Primarily build-time

ENV
    ↓
Available in the resulting container environment
```

---

## `VOLUME`

Declares a mount point intended for persistent/external data.

```dockerfile
VOLUME /data
```

---

## `USER`

Selects the user used to run subsequent instructions and the runtime process.

```dockerfile
USER 1000
```

or:

```dockerfile
USER appuser
```

By default, container processes commonly run as:

```text
root
```

unless the image specifies another user.

However, Docker container root normally receives only a **limited set of Linux capabilities**, rather than automatically having every host-root privilege.

---

# 2. Best Practices While Writing Docker Images

## Security / inspection tools

Useful commands/tools include:

```bash
docker inspect c1
docker stats
```

`docker stats` displays live resource usage such as:

* CPU
* Memory
* Network I/O
* Block I/O

For broader container monitoring:

```text
cAdvisor
```

Image vulnerability scanners can also be integrated into CI/CD pipelines.

---

## BuildKit ❓

**BuildKit** is Docker's modern image-build engine.

It improves image builds through features such as:

* Parallel build execution.
* Better build caching.
* More efficient dependency handling.
* Secret mounts.
* SSH forwarding.
* Cache mounts.
* Improved build output.
* Multi-platform builds when used with Buildx.

Example:

```bash
DOCKER_BUILDKIT=1 docker build .
```

Modern Docker installations commonly use BuildKit by default.

Example cache mount:

```dockerfile
RUN --mount=type=cache,target=/root/.cache/pip \
    pip install -r requirements.txt
```

Example secret mount:

```dockerfile
RUN --mount=type=secret,id=mysecret \
    cat /run/secrets/mysecret
```

This avoids baking the secret directly into an image layer.

---

## Use Small and Predictable Base Images

| Practice                                                          | Why                                                    |
| ----------------------------------------------------------------- | ------------------------------------------------------ |
| ✅ Use minimal base images such as `alpine`, `slim`, or distroless | Smaller image, faster transfer, reduced attack surface |
| ✅ Pin base-image versions such as `python:3.10-slim`              | More predictable/reproducible builds                   |
| ❌ Avoid relying blindly on `latest`                               | Rebuilds can unexpectedly use a different image        |

Example:

```dockerfile
FROM python:3.10-slim
```

instead of:

```dockerfile
FROM python:latest
```

For maximum reproducibility, image digests can also be pinned.

---

## Minimize Unnecessary Layers and Files

| Practice                                                                                       | Why                              |
| ---------------------------------------------------------------------------------------------- | -------------------------------- |
| ✅ Combine related `RUN` operations where it avoids temporary data persisting in earlier layers | Can reduce image size            |
| ✅ Clean temporary/package-manager files in the same layer                                      | Prevents image bloat             |
| ❌ Don't install unused tools                                                                   | Smaller image and attack surface |

Example:

```dockerfile
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*
```

A key reason to combine these commands is that deleting files in a later layer does not remove those files from the history of an earlier layer.

---

## Package Manager Cache

| Practice                                                | Why                                                 |
| ------------------------------------------------------- | --------------------------------------------------- |
| ✅ Use `--no-cache` when appropriate, such as with `apk` | Avoid package-index/cache files                     |
| ✅ Clean `apt` package metadata                          | Smaller images                                      |
| ✅ Use multi-stage builds                                | Keep compilers/build tools out of the runtime image |

Example:

```dockerfile
RUN apk add --no-cache curl
```

---

# Multi-Stage Builds

Multi-stage builds separate build-time dependencies from runtime dependencies.

Example:

```dockerfile
# ---- Build Stage ----
FROM node:18 AS builder

WORKDIR /app

COPY . .

RUN npm install && npm run build


# ---- Runtime Stage ----
FROM nginx:alpine

COPY --from=builder /app/dist /usr/share/nginx/html
```

Conceptually:

```text
Builder image
Node + npm + source + build tools
             |
             | copy only output
             v
Runtime image
Nginx + compiled files
```

Advantages:

* Smaller final image.
* Fewer tools available to attackers.
* Build dependencies don't appear in production.
* Cleaner separation of build and runtime environments.

---

## Security Practices

| Practice                              | Why                                                            |
| ------------------------------------- | -------------------------------------------------------------- |
| ✅ Use a non-root user with `USER`     | Reduces impact of container compromise                         |
| ✅ Avoid hardcoding secrets            | Prevent credentials from becoming part of image layers/history |
| ✅ Use secret-management mechanisms    | Better control of credentials                                  |
| ✅ Scan images regularly               | Detect known vulnerabilities                                   |
| ✅ Drop unnecessary Linux capabilities | Reduce container privileges                                    |

Avoid:

```dockerfile
ENV PASSWORD=my-secret-password
```

for secrets.

Instead, inject secrets at runtime or use a dedicated secret-management mechanism.

---

## Layer Caching

| Practice                                                                        | Why                                           |
| ------------------------------------------------------------------------------- | --------------------------------------------- |
| ✅ Order instructions from least frequently changing to most frequently changing | Maximizes cache reuse                         |
| ✅ Copy dependency files before application code                                 | Dependency installation can stay cached       |
| ✅ Use `.dockerignore`                                                           | Smaller context and fewer cache invalidations |

Poor caching example:

```dockerfile
COPY . .
RUN pip install -r requirements.txt
```

Any source change invalidates the `COPY` layer and therefore the package-install layer.

Better:

```dockerfile
COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .
```

Now application-code changes don't necessarily cause dependencies to be reinstalled.

---

# 3. Java / Spring Boot

Make a JAR executable:

```bash
chmod +x ./target/spring-app-1.0.0.jar
```

Spring Boot layered JARs can separate application content into layers so Docker can cache relatively stable dependencies independently from frequently changing application code.

Example command:

```bash
java -Djarmode=layertools \
  -jar ./target/spring-app-1.0.0.jar extract
```

Purpose:

```text
Extract a Spring Boot layered JAR
```

Typical layers:

```text
dependencies/
spring-boot-loader/
snapshot-dependencies/
application/
```

Meaning:

```text
dependencies/
    Stable third-party dependencies

spring-boot-loader/
    Spring Boot loader classes

snapshot-dependencies/
    SNAPSHOT dependencies, if any

application/
    Application classes/resources
```

The main Docker advantage is cache reuse:

```text
dependencies        → changes rarely
spring-boot-loader  → changes rarely
snapshot deps       → changes occasionally
application         → changes frequently
```

Therefore application changes don't necessarily invalidate all dependency layers.

---

# 4. Python

Example multi-stage Python Dockerfile:

```dockerfile
# ---- Build Stage ----
FROM python:3.9-slim AS builder

WORKDIR /app

COPY requirements.txt .

RUN pip install \
    --user \
    --no-cache-dir \
    -r requirements.txt

COPY . .


# ---- Runtime Stage ----
FROM python:3.9-slim

WORKDIR /app

# Installed Python packages
COPY --from=builder /root/.local /root/.local

# Application code
COPY --from=builder /app .

ENV PATH=/root/.local/bin:$PATH

USER 1000

EXPOSE 5000

CMD ["python", "app.py"]
```

Flow:

```text
Builder
  |
  ├── Install dependencies
  ├── Build/prepare app
  |
  ↓
Runtime
  |
  ├── Copy installed packages
  ├── Copy application
  ├── Run as non-root user
  ↓
python app.py
```

### Important note about the example

The example preserves the original approach of installing packages under:

```text
/root/.local
```

and then switching to:

```dockerfile
USER 1000
```

Depending on package permissions and runtime behavior, a cleaner production setup can be to create a dedicated application user and install/copy dependencies into a location explicitly readable by that user, such as a virtual environment.

Example pattern:

```dockerfile
FROM python:3.9-slim AS builder

WORKDIR /app

RUN python -m venv /opt/venv

ENV PATH="/opt/venv/bin:$PATH"

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt


FROM python:3.9-slim

WORKDIR /app

COPY --from=builder /opt/venv /opt/venv
COPY . .

ENV PATH="/opt/venv/bin:$PATH"

RUN useradd --create-home appuser

USER appuser

EXPOSE 5000

CMD ["python", "app.py"]
```

---

# Docker Mental Model

```text
Dockerfile
    ↓
docker build
    ↓
IMAGE
    ↓
docker run
    ↓
CONTAINER
```

And:

```text
Image
    = immutable application template

Container
    = running/stopped instance of an image

Volume
    = persistent Docker-managed storage

Bind Mount
    = host filesystem path mounted into container

Network
    = communication layer between containers/services

Registry
    = remote storage/distribution system for images
```

---

# Important Command Distinctions

```text
docker build
    Build image

docker pull
    Download image

docker push
    Upload image

docker run
    Create + start a new container

docker start
    Start an existing stopped container

docker exec
    Execute command in an existing running container

docker stop
    Stop container

docker rm
    Remove container

docker rmi
    Remove image

docker logs
    Read container logs

docker inspect
    Inspect detailed Docker object configuration

docker stats
    Monitor container resource usage
```

---

# Quick Interview Cheat Sheet

```text
Dockerfile → builds → Image
Image      → runs as → Container
```

```text
ENTRYPOINT = main executable
CMD        = default command / arguments
RUN        = build-time command
```

```text
COPY       = copy build-context files
WORKDIR    = working directory
ENV        = runtime/image environment variable
ARG        = build-time variable
USER       = runtime user
EXPOSE     = documents container port
```

```text
docker run  = new container
docker exec = command in existing container
```

```text
Volume     = Docker-managed persistent storage
Bind mount = host path mapped directly into container
```

```text
-p host:container
```

Example:

```text
-p 8080:80

Host :8080 → Container :80
```

Best practices:

```text
Small base image
Pin versions
Don't rely on latest
Use .dockerignore
Use multi-stage builds
Run as non-root
Don't bake secrets into images
Drop unnecessary capabilities
Clean package caches
Optimize instruction order for cache reuse
Scan images
Monitor containers
Use BuildKit
```
