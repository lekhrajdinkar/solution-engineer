- https://chatgpt.com/c/da40b952-dbd9-46a9-ad58-92c828a89118
- demo vide: https://www.youtube.com/playlist?list=PLVz2XdJiJQxybsyOxK7WFtteH42ayn5i9
- driver : https://minikube.sigs.k8s.io/docs/drivers/
- UI - aptaKube or lens

---
## pre-requisite
### `docker`
  - docker Engine
  - docker local repo / or,  dockerhub

### `kubectl` 
- curl.exe -LO "https://dl.k8s.io/release/v1.30.0/bin/windows/amd64/kubectl.exe
  - https://kubernetes.io/docs/reference/kubectl/
  - https://kubernetes.io/docs/reference/kubectl/quick-reference/
- next:
  - check Kube config 
    - .kube/config  - default file
    - Set **KUBECONFIG** env Var

---
## Install minikube
- gcr.io/k8s-minikube/kicbase:v0.0.44
- minikube version: `v1.33.1`, download minikube.exe, set PATH, install docker (driver)
- Minikube automatically configures kubectl to use the Minikube cluster.
- next:
  - configure : CPU and memory at, node level
  - **minikube version**
  - **minikube docker-en**: allow miniKube to access local-docker-repo
  - finally launch: **minikube start --driver=docker**  // `stop`,  `delete`
  
## more:
- purpose:
  - learn Kubernetes concepts and experiment with different configurations and deployments without the need for a full-fledged cluster
  - used in CI/CD pipelines for testing Kubernetes applications.
  -  Run k8s cluster on your local machine | `single-node`

- minikube `addons` enable/disable <addon-name>
  - Ingress, 
  - Dashboard, 
  - DNS, 
  - Metrics Server
  - dashboard
      
- commands :
  - minikube `service` <service-name> --> host services with external IPs.
    - minikube tunnel
  - minikube `status`
  - minikube `dashboard`
    - http://127.0.0.1:59962/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/daemonset?namespace=default


