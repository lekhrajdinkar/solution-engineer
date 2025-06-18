# spring-app
## run locally
- check swagger : http://localhost:8083/spring/swagger-ui/index.html
  - port `8083`
  - context : `/spring`
 
## docker images
```
REPOSITORY                          TAG               IMAGE ID       CREATED        SIZE
lekhrajdinkar18/spring-app-window   latest            548721cfb837   2 hours ago    250MB

```
- docker **build** -f dockerfile-window - t lekhrajdinkar18/spring-app-window:latest .
- docker **tag** lekhrajdinkar18/spring-app-window:latest 548721cfb837
- docker **run** lekhrajdinkar18/spring-app-window:latest -p `8080`:8083 
- check swagger : http://localhost:8080/spring/swagger-ui/index.html

---
## push image to hub
- push to `docker hub` (optional, needed for pipeline)
  - https://hub.docker.com/repositories/lekhrajdinkar18
  - https://hub.docker.com/repository/docker/lekhrajdinkar18/spring-app/general
  - https://hub.docker.com/repository/docker/lekhrajdinkar18/spring-app-window/general :point_left:
  - docker **login**
  - docker **push** lekhrajdinkar18/spring-app-window:latest

