# A. pre work
- **cluster** : minikube start --driver=docker
- create namespaces
  - kubectl create namespace **dev1-helm**
  - kubectl create namespace **dev1-manifest**
- notepad++ edit > EOL conversion :: `LR` or `CRLF` on **mvnw** file
- docker registry : create secret
  ```yaml
  kubectl create secret docker-registry dockerhub-secret \
  --docker-server=docker.io \
  --docker-username=lekhrajdinkar18 \
  --docker-password=dckr_pat_qhk_PqImfUWoGaK2XMWOxs76PxE \
  --docker-email=your-email@example.com
  
  kubectl create secret docker-registry dockerhub-secret   --docker-server=docker.io   --docker-username=lekhrajdinkar18   --docker-password=dckr_pat_qhk_PqImfUWoGaK2XMWOxs76PxE   --docker-email=your-email@example.com
  ```
- create image:
  - **manually** or pipeline
  - docker **build** --label "version=1.0.0" -t **spring-app:v1** . -f **dockerfile-v1** 
  - docker **tag** spring-app:v1 lekhrajdinkar18/02-backend-java-spring:`spring-app-06.03.2025`
  - docker **push** lekhrajdinkar18/02-backend-java-spring:spring-app-06.03.2025
    - https://hub.docker.com/repository/docker/lekhrajdinkar18/02-backend-java-spring/tags : pushed
- deploy
  - cd C:\Users\Manisha\Documents\GitHub\02-backend-java-spring\deployment\manifest\spring_app_v2
  - kubectl create -f 01-namespace.yaml
  - kubectl create -f 02-service-account.yaml
  - kubectl create -f 03-spring-app-deplyment.yaml
---
# B. project - spring-app
## 1. Manifest
- [spring_app_v2](manifest/spring_app_v2)

## 2. HELM

---
# C. other project - 