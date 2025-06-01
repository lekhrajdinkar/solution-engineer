:point_right: CHECK this : https://github.com/lekhrajdinkar/03-spring-cloud-v2/tree/main/Notes :point_left:
---

## 1. Micro service communication
- https://chat.deepseek.com/a/chat/s/6e7456d4-cc1b-42be-ae19-c3ede730936f
```text
#1
microservices ms-1 and ms-2 communicaton
- sysnc
- asysnc

#2
ms1 (pod-1), ms2 (pod-2)
services communing over aws eventbridge - bus-1, async.
i would like to add distrubted tracing as well.

#3
ms1 (pod-1), ms2 (pod-2) : java springboot on AWS EKS
services communing over rabbitMQ - queue-1, async.
i would like to add distrubuted tracing.
monitoring backed : AWS x-rays

#4
ms1 (pod-1), ms2 (pod-2) : java springboot on AWS EKS
services communing sysnc over HTTPS.
i would like to add distributed tracing.
monitoring backed : AWS x-rays

#5
ms1 (pod-1), ms2 (pod-2) : java springboot on AWS EKS
ms1 makes call ms2(GET) communing async over SQS. How ms1 will get data from ms2.Also
Also i would like to add distributed tracing.
monitoring backed : AWS x-rays
```

## 2. Monitoring (Non-aws)
- **actuator**
    - http://localhost:8083/spring/actuator/metrics --> show metric **names**
    - [actuator.json](actuator.json)
- **prometheous** 
  - https://chat.deepseek.com/a/chat/s/5effe43a-7c05-433f-8df6-3326b6e311c6
  - http://localhost:8083/spring/actuator/prometheus
  - [PrometheusMicrometerConfig.java](../../src/main/java/com/lekhraj/java/spring/SB_99_RESTful_API/configuration/PrometheusMicrometerConfig.java)
- **grafana**
    - launch locally : **docker** run -d -p 3000:3000 --name=grafana grafana/grafana-enterprise
    - http://localhost:3000
    - https://lekhrajdinkar.grafana.net/a/grafana-setupguide-app/getting-started | github ld account :point_left:
    - admin | admin
- **micometer**
  - like open-telemetry, to intrumnet, but sends to prometheus server (UI : grafana)
  - open-telemetry send to multiple server
  - eg: [MicrometerController.java](../../src/main/java/com/lekhraj/java/spring/SB_99_RESTful_API/controller/MicrometerController.java)
  - ```text
    test_counter_total{application="spring-lekhraj-app",} 1.0
    test_counter_total{application="spring-lekhraj-app",} 2.0
    test_counter_total{application="spring-lekhraj-app",} 3.0
    ...
    ```