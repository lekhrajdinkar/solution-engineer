## container-d
- K8s initially supports docker runtime only (cli,api,build, volumne, auth, security, **runtime/container-d**)
- Afterward supports different Runtime/s via `CRI` - OCI-standard imageSpec and ContainerSpec
  - OCI - Open Container Interface.
  - Any image follows OCI/imageSpec is supported by k8s
  - Any Container follows OCI/ContainerSpec is support
  - runtime:
    - rkt
    - docker ( supported via `dockershim`)
  - v1.24 
    - `dockershim` removed 
    - docker is removed k8s
  - so, `container-d` is runtime for docker as per OCI/ContainerSpec for kubernetes.
    - now standalone project under CNCF
    - needed only runtime, no other feature, then install it.

## Command line 
### a. for container-d
- `ctl` -  debug purpose , not user-friendly
  - `ctl image` pull image1
  - `ctl run` image1 c1-name

- `nerdctl`  (advance/later)
  - docker like CLI for container-d. can run all docker cmd.
  - supports docker `compose`
  - supports new feature/s:
    - encrypted image + image signing
    - lazy pulling
    - k8 namespace

### b. For any CRI/OCI compatible container    
-  `crictl`
  - CLI for any CRI/OCI compatible container
  - install separately
  - from k8 community
  - special debugging purpose
  - aware of pods.
  - syntax quite similar