- reference:
  - Containers : https://chatgpt.com/c/9836b4c7-2b23-497e-b06b-16885e3e18aa
--- 
## A. Containers
  - `isolated environment` : physical machine > VM > Containers(host OS)
  - Containers are similar to VMs, but share the Operating System (OS) among the applications.
  - Therefore, containers are considered lightweight.
  - Similar to a VM, a container has its own filesystem, share of CPU, memory, process space, and more.
  - As they are decoupled from the underlying infrastructure, they are portable across clouds and OS distributions.


### Container images
  - eg: Docker image
  - image built on OS-1 as base, can be run my machine having window OS. docker desktop in between.
  - best suited for microservices/MS by providing `portability` and `isolated VM`
  - `executable package`, which confine the  `code,runtime and dependencies, env var+configFile` in a pre-defined format.
  - Composed of `multiple layers`, stacked on top of a base image.
  - Images are stored in `repositories`, which can be public or private. eg : Docker Hub, ECR
  - `versioned` using tags
  - Security:
    - Use `trusted base-images`
    - identify `vulnerabilities` in images and upgrade it. docker has built in scanner.
    - regularly update images to include security patches.
    
### Container runtime 
- eg: Docker Engine

---
## B. Container orchestrators
- framework for managing containers at scale at **runtime**
- For Distributed-System (ms), provides **deployment patterns of container**.
  - automate the deployment
    - release - rollout / rollback
  - scale
  - service discovery + networking
  - storage
  - config + secrets
- orchestrators make things easier, when managing hundreds or thousands of containers.
- offerings: 
  - Docker Swarm (native)
  - Kubernetes/k8s - minikube, EKS, AKS
  - AWS - ECS
  - marathon




