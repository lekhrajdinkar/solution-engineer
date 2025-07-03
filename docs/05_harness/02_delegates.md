## harness delegate
![img_1.png](../99_temp/05_harness_image/img_1.png)
## intro
- **lightweight**, secure **worker process** that runs within our infrastructure
  - Kubernetes cluster
  - VM
- execute tasks on behalf of the Harness Platform.
- It acts as a communication **bridge** between `Harness SaaS` and `our environment`
  - ensuring secure
  - scalable
  - efficient pipeline operations

- **Key Roles of a Delegate**
  - **Task Execution**:
    - Delegates perform **pipeline actions** directly in your environment, such as:
    ```
    - git clone
    - prepare image - Dind
    - run terrafom - login plan apply.
      - deploy aws iac ; inbound, outbound,etc
      - kafka iac
    - prepare version from pipeline variable.
    - Deploying artifacts to Kubernetes, ECS, or VMs.
      - run helm
      - run k8s manifest
    - Running scripts (e.g., Shell, PowerShell).
      - push to ecr
      - restart ecs
    - Connecting to cloud providers (AWS, Azure, GCP).
    - Integrating with tools like Terraform, or databases.
    - Push to Nexus
    - push helm package to nexux/ecr
    ```

  - **Security**:
    - Delegates make outbound connections to Harness SaaS
    - They execute tasks within our network, avoiding exposure of sensitive credentials to external systems.

  - **Connectivity**:
    - Harness SaaS **sends tasks** to the Delegate via a persistent connection (**gRPC/HTTPs**).
    - The Delegate polls for tasks, executes them locally, and sends results back.
  - **Scalability**: 
    - Add multiple Delegates for high availability or workload distribution.
    - Harness auto-scales task execution **across available Delegates**.

- Types of Delegates:
  - **Kubernetes Delegate**: Runs as a Pod (most common for containerized environments).
  - **Docker Delegate**: Runs as a container on VMs or bare metal.
  - Shell Script Delegate: Installed via script on Linux/Windows hosts.

---
## Run delegate
### 1. terraform provider
- this terraform module, deploys helm chart on K8s cluster (minikube)
- trf: [terraform-helm-delegate](https://github.com/lekhrajdinkar/02-backend-pack/blob/main/deployment/terraform_iac/config-4-harness-delegate/main.tf)
- **kubeconfig** set to minikube
- delegate added : https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/settings/delegates/list 

![img.png](../99_temp/04_trf_img/05/01/img.png)

![img_1.png](../99_temp/04_trf_img/05/01/img_1.png)

---
### 2. docker
<details> <summary>docker</summary>

```bash
        docker run  --cpus=1 --memory=2g \
        -e DELEGATE_NAME=docker-delegate \
        -e NEXT_GEN="true" \
        -e DELEGATE_TYPE="DOCKER" \
        -e ACCOUNT_ID=XXXXXXXXXXXXX \
        -e DELEGATE_TOKEN=XXXXXXXXXXXXXXXX \
        -e DELEGATE_TAGS="" \
        -e LOG_STREAMING_SERVICE_URL=https://app.harness.io/log-service/ \
        -e MANAGER_HOST_AND_PORT=https://app.harness.io harness/delegate:24.10.84200
        
        docker run  --cpus=1 --memory=2g -e DELEGATE_NAME=docker-delegate -e NEXT_GEN="true" -e DELEGATE_TYPE="DOCKER" -e ACCOUNT_ID=e0wDKKO_S46x3M75TWv0iw -e DELEGATE_TOKEN=MGY2OGJmMWQwYjMwZGM5NDYzZDM5NGFlMDg5Mzk4NzY= -e DELEGATE_TAGS="" -e LOG_STREAMING_SERVICE_URL=https://app.harness.io/log-service/  -e MANAGER_HOST_AND_PORT=https://app.harness.io harness/delegate:24.10.84200
```
</details>

### 3. K8s manifest
