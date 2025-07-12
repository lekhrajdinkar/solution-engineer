# Observability - spring
## 1. Tools/frameworks (non-aws )
- https://chat.deepseek.com/a/chat/s/5effe43a-7c05-433f-8df6-3326b6e311c6 :point_left:
- **actuator**
    - http://localhost:8083/spring/actuator/metrics --> show metric **names**
    - [actuator.json](../10_System_Design/03_Miscroservice/actuator.json)
- **prometheous**
    - http://localhost:8083/spring/actuator/prometheus
    - [PrometheusMicrometerConfig.java](../src/main/java/com/lekhraj/java/spring/SB_99_RESTful_API/configuration/PrometheusMicrometerConfig.java)
- **grafana**
    - launch locally : **docker** run -d -p 3000:3000 --name=grafana grafana/grafana-enterprise
    - http://localhost:3000
    - https://lekhrajdinkar.grafana.net/a/grafana-setupguide-app/getting-started | github ld account :point_left:
    - admin | admin
    - UI for Prometheus,etc
- **micro meter**
    - like otel, to instrument, but sends only `metric` to prometheus server, datadog, etc. dependecies:
        - micrometer-registry-datadog
        - micrometer-registry-prometheus
        - ...
    - otel (**unified** : `metric`, `log`, `trace`) , sends to also multiple server
    - eg: [MicrometerController.java](../src/main/java/com/lekhraj/java/spring/SB_99_RESTful_API/controller/MicrometerController.java)
  ```text
    test_counter_total{application="spring-lekhraj-app",} 1.0
    test_counter_total{application="spring-lekhraj-app",} 2.0
    test_counter_total{application="spring-lekhraj-app",} 3.0
  ```
---
