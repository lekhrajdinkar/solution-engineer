# run a Spring project inside pod
## yaml reference 
- https://medium.com/@javatechie/kubernetes-tutorial-run-deploy-spring-boot-application-in-k8s-cluster-using-yaml-configuration-3b079154d232
- https://github.com/Java-Techie-jt/springboot-crud-k8s/blob/main/db-deployment.yaml
- check project-01

## minikube command
- minikube start --driver=docker
- minikube status
- minikube docker-env
- minikube ip
- minikube dashboard
- kubectl cluster-info
- minikube stop
- minikube delete ?

## steps

1. minikube start --driver=docker
2. minikube docker-env
3. kubectl create -f deployment-spring.yml
4. kubectl create -f service-spring.yml

---

## output/console
```
minikube start --driver=docker

kubectl cluster-info
    Kubernetes control plane is running at https://127.0.0.1:58455
    CoreDNS is running at https://127.0.0.1:58455/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

---
minikube status

minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
docker-env: in-use

---
minikube docker-env

SET DOCKER_TLS_VERIFY=1
SET DOCKER_HOST=tcp://127.0.0.1:58452
SET DOCKER_CERT_PATH=C:\Users\Manisha\.minikube\certs
SET MINIKUBE_ACTIVE_DOCKERD=minikube
REM To point your shell to minikube's docker-daemon, run:
REM @FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env --shell cmd') DO @%i
```

```
kubectl apply -f service-spring.yml
service/spring-k8s-service created
---
kubectl get services

NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
kubernetes           ClusterIP   10.96.0.1       <none>        443/TCP          3d2h
spring-k8s-service   NodePort    10.102.17.189   <none>        8083:30837/TCP   17s
---
kubectl get nodes -o wide

NAME       STATUS   ROLES           AGE    VERSION   INTERNAL-IP    EXTERNAL-IP   OS-IMAGE             KERNEL-VERSION                       CONTAINER-RUNTIME
minikube   Ready    control-plane   3d2h   v1.30.0   192.168.49.2   <none>        Ubuntu 22.04.4 LTS   5.15.153.1-microsoft-standard-WSL2   docker://26.1.1

```
---
## ingress
```
minikube ip
192.168.49.2

minikube service spring-k8s-service

minikube tunnel

kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
kubectl get pods -n ingress-nginx

C:\Windows\System32\drivers\etc
192.168.49.2 hello-world.local
127.0.0.1 hello-world.local

http://hello-world.local/spring/swagger-ui/index.html


  rules:
    - host: hello-world.local
      http:
        paths:
          - path: /spring
            pathType: Prefix
            backend:
              service:
                name: spring-app-service
                port:
                  number: 8080 # Matches the `port` defined in your service

```

