# developer notes
- java **-Djarmode=layertools** -jar ./target/spring-app-1.0.0.jar extract
  - extract a Spring Boot layered JAR file 
  - Spring Boot 2.3.0+
  - chmod +x ./target/spring-app-1.0.0.jar
  - layes:
    - dependencies/
    - spring-boot-loader/
    - snapshot-dependencies/ (if any)
    - application/