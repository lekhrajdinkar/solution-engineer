## feature
- rolling out / rollback
- autoscale with HPA/VPA
- clustered arch : increased performance, cost efficiency, reliability, workload distribution, and reduced latency.
- Service Discovery and Load Balancing with k8s services
- Self-Healing
- Secret and Configuration Management
- Storage Orchestration : PV, PVC, SC
- addons
- scheduling

---
## Shell into a running pod
- kubectl **exec** -it pod-1 `-- /bin/sh`

---
##  A. cluster
- can be setup : cloud(ec2), on-prem(host), IaaS (EKS).
### 🟡 master Node
- control panel
- Checks memory, health, CPU, etc for each WorkerNode.
- **Kube-API Server**
    - cluster-gateway
    - any request comes to cluster --> gateway --> WorkerNode --> ...
- **Scheduler**
    - if WorkerNode-1 is 90%  and WorkerNode-1 is 30%, used.
    - then scheduler will assign new pods in WorkerNode-1
    - takes data from ETCD.
    - responsible for assigning workloads (pods) to nodes.
    - ensures workloads meet certain constraints and resource requirements.
- **ETCD** :Persistence store.
- **Controller manager**
    - runs `processes` in the background to regulate the state of the cluster.
    - Types/eg:
        - Node Controller
        - replication controller
        - Endpoints Controller
        - Service account and token Controller
- **Cloud Controller Manager**
    - enabling Kubernetes to interact with underlying cloud provider APIs.

###  🟡 worker Node (Many)
- **Container Runtime**
    - to run containers (present inside Pods)
- **Kubelet**
    - agent running on each node.
    - masterNode::API-Server <-->  Kubelet-WorkerNode
    - Kubelet communicate with masterNode using API-server
- **kube-proxy**
    - Network config
    - Network traffic Rule (from/to nodes) ingress/outgress

###  🟡 fargate profile (EKS)

---
## B. user and serviceAcct
### ServiceAcct(inside k8s)🔷
- represents processes running inside the Kubernetes cluster.
- Managed within Kubernetes
- created inside ns
- Service accounts use secrets that are automatically created and mounted to pods running under them.
- **permission** / `RBAC` : create role and role-binding for SA. (fine granular access)
- authentication not needed.
- scenario / use case:
    - **pod** using sa
        - sa token already generated and mounted on pod.
    - **jenkin** using sa - api call with sa token in Authorization header.
        - how to generate token

### User(outside cluster)🔷
- eg: An admin using `kubectl` to create a deployment.
- Represents a real human (`admins` / `developers`) or an external entity accessing the Kubernetes cluster.
-  Users are not managed by Kubernetes itself.
- they are managed outside of Kubernetes and autheticated through an `ext identity provider, OIDC`, `certificate management`, etc.).
    - minikube : certificate management
    - EKS : identity provider
- fine granular access - create role and role binding
    - admin : when cluster created one default admin is created with admin access.
    - developer user :
        - admin will create role and role binding
        - role binding - > role ref + subject(kind:user, name - use same user-name in kubeconfig file.) 🔷

---
## 🔸Summary 🔸
### Pods
- The smallest deployable units in Kubernetes that you create and manage.
- pod talks to each other using `Service` (has DNS).

### Replica Set
- one pod goes down another comes up from replica set.
- ensures certain no. of pod running at specific time at all the time.
- uses selectors(label query)
- scale in/out replica count : `horizontal scale`.
- span with cluster ⬅️
- Self-Healing

### Deployment Object
- higher-level concept that manages ReplicaSets + `updates on pods`/rollup/rollback.

### scheduler
- decides which node, a pod is assigned to.

### Kube-Controller-Manager
- Runs various controller processes in the background to regulate the state of the cluster.
- Node Controller, Replication Controller, Endpoints Controller,etc

### Nodes + nodeGroup(EKS concept)
- The worker machines in the Kubernetes cluster, which can run multiple pods.
- typically runs on a separate virtual machine (VM), but this is not a strict requirement.
- in AWS, each node is  seperate  VM for isolation.
- VMs can be easily resized, moved, or replicated,
- contains: kubelet, kube-proxy, container-runtime.

### Secret
- Store DB config, password, etc outside SB app. then we dont need to build it again.
- encrypted text, outside pod

### Config map

### ETCD
- key-Value database/store,
- 1MB max of a value.
- store all its cluster data, such as cluster state, configurations, and metadata.

### services
- An abstract way to expose an application running on a `set of Pods`, as a network service.
- has DNS and static IP
- **ClusterIP** : Exposes the service on a cluster-internal IP.
- **NodePort** : Exposes the service on each Node's IP at a static port
- **LoadBalancer** : Creates an external load balancer in the cloud provider (if supported) and assigns a fixed, external IP to the service.
- Single Service for Multiple Pods (all having same image)
- Separate Services for Different Pods (each having diff image)

### ingress controller
- ingress controller (nginx, aws:alb ingress controller)
- ingress-object (routing rule)

### admission controller

### ServiceAcct RBAC

### HPA / VPA

---
# screenshots for reference
![01](../99_img/01.png)
![01](../99_img/02.png)
![01](../99_img/03.png)
![01](../99_img/04.png)
![01](../99_img/05.png)
![01](../99_img/06.png)
![01](../99_img/07.png)
![01](../99_img/08.png)
![01](../99_img/09.png)
![01](../99_img/10.png)