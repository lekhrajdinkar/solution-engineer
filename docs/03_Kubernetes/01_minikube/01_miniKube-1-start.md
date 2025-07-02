## minikube - intro
- UI - **aptaKube** or **lens**
- learn Kubernetes concepts and experiment with different configurations and deployments without the need for a full-fledged cluster
-  Run k8s cluster on your local machine | single-node
- [chatgpt 🗨️](https://chatgpt.com/c/da40b952-dbd9-46a9-ad58-92c828a89118)
- [demo video](https://www.youtube.com/playlist?list=PLVz2XdJiJQxybsyOxK7WFtteH42ayn5i9)

---
## Install minikube
### 1 pre-requisite :: docker
- docker Engine
- docker local repo 
- or, dockerhub

### 2 pre-requisite :: kubectl 
- curl.exe -LO "https://dl.k8s.io/release/v1.30.0/bin/windows/amd64/kubectl.exe
- https://kubernetes.io/docs/reference/kubectl/
- https://kubernetes.io/docs/reference/kubectl/quick-reference/
- next check `Kubeconfig`
- can Set **KUBECONFIG** env Var

### install
- gcr.io/k8s-minikube/kicbase:v0.0.44
- minikube version: `v1.33.1`, download minikube.exe, set PATH, install docker (driver)
- Minikube automatically configures kubectl to use the Minikube cluster.
- next:
  - configure : CPU and memory at, node level
  - **minikube version**
  - **minikube docker-en**: allow miniKube to access local-docker-repo
  - finally launch: **minikube start --driver=docker**  // `stop`,  `delete`

---
## create new user for minikube 
- (optional, just try)
- generating new client certificates and adding the user to your kubeconfig (say:u1)

```
openssl genrsa -out new-user.key 2048
openssl req -new -key new-user.key -out new-user.csr -subj "/CN=new-user"
openssl x509 -req -in new-user.csr -CA C:\Users\Manisha\.minikube\ca.crt -CAkey C:\Users\Manisha\.minikube\ca.key -CAcreateserial -out new-user.crt -days 365

```
- update kubeconfig file
  - context
    - user : **new-user**
    - cluster :

- kubectl get ClusterRoleBindings
  - see below role :  **cluster-admin**
```
NAME              ROLE
minikube-rbac     ClusterRole/cluster-admin (existing role)
```
- Create a ClusterRoleBinding for that user with **cluster-admin** rights
```
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: admin-binding-1
subjects:
- kind: User
  name: new-user                              <<<< same name used in kubeConfig
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: cluster-admin                          <<<<
  apiGroup: rbac.authorization.k8s.io

```