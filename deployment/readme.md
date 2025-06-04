# Pre-work for project/s
- **cluster** : minikube start --driver=docker
- create namespaces
  - kubectl create namespace **dev1-helm**
  - kubectl create namespace **dev1-manifest**
- notepad++ edit > EOL conversion :: `LR` or `CRLF` on **mvnw** file
- docker registry : create secret
  ```yaml
  kubectl create secret docker-registry dockerhub-secret  -n dev1-manifest \
  --docker-server=docker.io \
  --docker-username=lekhrajdinkar18 \
  --docker-password=dckr_pat_Xc5Q6X58_nVagNmIL0S7PzxSlpc \
  --docker-email=your-email@example.com
  
  kubectl create secret docker-registry dockerhub-secret  -n dev1-manifest  --docker-server=docker.io   --docker-username=lekhrajdinkar18   --docker-password=dckr_pat_Xc5Q6X58_nVagNmIL0S7PzxSlpc   --docker-email=your-email@example.com
  ```
-  kubectl get secret dockerhub-secret -n dev1-manifest -o jsonpath='{.data.\.dockerconfigjson}'

---
# project-1 - spring-app-manifest
### create image
- **manually** or pipeline
- docker build -t lekhrajdinkar18/02-backend-java-spring:spring-app-06.04.2025 . -f dockerfile-v1 (single step)
    - docker build --label "version=1.0.0" -t **spring-app:v1** . -f **dockerfile-v1**
    - docker tag spring-app:v1 lekhrajdinkar18/02-backend-java-spring:spring-app-06.04.2025
- docker push lekhrajdinkar18/02-backend-java-spring:spring-app-06.03.2025
    - https://hub.docker.com/repository/docker/lekhrajdinkar18/02-backend-java-spring/tags : pushed
  
### prepare Manifest
- artifact: [spring_app_v2](manifest/spring_app_v2)

### deploy
- cd C:\Users\Manisha\Documents\GitHub\02-backend-java-spring\deployment\manifest\spring_app_v2
- onetime:
  - kubectl create -f 01-namespace.yaml
  - kubectl create -f 02-service-account.yaml
  - middleware:
    - **Database** : kubectl create -f 02_postgres-pod.yaml
    - **RMQ** :  kubectl create -f 02-rmq-pod.yaml
- **deployment**
  - kubectl create -f 03-spring-app-deplyment.yaml
  - kubectl create -f 04-spring-app-nodeport-service.yaml
- kubectl get all -n dev1-manifest

### rollback deploymnet
- **App**
  - kubectl delete deployment spring-app-deployment -n dev1-manifest
  - kubectl delete service/spring-app-nodeport-service -n dev1-manifest
- **middleware**
  - database/postgres
    - kubectl delete pod/postgres -n dev1-manifest
    - kubectl delete service/postgres-service -n dev1-manifest
  - rmq
    - kubectl delete pod/postgres -n dev1-manifest
    - kubectl delete service/postgres-service -n dev1-manifest
    
### access app : Tunnel service (minikube)
- minikube service spring-app-nodeport-service -n dev1-manifest
```text
|---------------|-----------------------------|-------------|---------------------------|
|   NAMESPACE   |            NAME             | TARGET PORT |            URL            |
|---------------|-----------------------------|-------------|---------------------------|
| dev1-manifest | spring-app-nodeport-service |        8080 | http://192.168.49.2:30008 |
|---------------|-----------------------------|-------------|---------------------------|
* Starting tunnel for service spring-app-nodeport-service.
|---------------|-----------------------------|-------------|------------------------|
|   NAMESPACE   |            NAME             | TARGET PORT |          URL           |
|---------------|-----------------------------|-------------|------------------------|
| dev1-manifest | spring-app-nodeport-service |             | http://127.0.0.1:60126 |
|---------------|-----------------------------|-------------|------------------------|
```
- http://127.0.0.1:60126/spring/swagger-ui/index.html :working

![img.png](../99_temp/icon/img.png)

---
# Project-2 - spring-app-helm
- chart: [spring_app_helm_v1](HELM/spring_app_helm_v1)
### deployment
### upgrade :point_left:
### rollback

---
# project-2 - microservices
- in progress - ms