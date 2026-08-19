# Microservice
> Check in below order

---
## System design ::  Architecture pattern
- [ALL ⭐](../SD_03_Core-building-blocks/SD_03_52_architecture)
  - [monolith-arch :: Overview ](../SD_03_Core-building-blocks/SD_03_52_architecture/01_core_01_monolith-arch.md)
  - [microservice-arch :: Overview](../SD_03_Core-building-blocks/SD_03_52_architecture/01_core_02_microservice-arch.md)
  - ...
  
## Distributed System and challenges 
- Microservice are distributed system.
- [Distributed-system :: details ⭐](../SD_06_Distributed-system)

---
## API-Design
- [API-Design :: complete guide](../SD_08_API-Design)
- https://www.oreilly.com/library/view/mastering-api-architecture/9781492090625/ book

---
## Microservice :: Core pattern
- **Stranger pattern**
  - decompose by domain [DDD](../SD_03_Core-building-blocks/SD_03_52_architecture/01_DDD.md)
  - https://youtube.com/watch?v=DpuQ3-7e-rY
  - https://youtube.com/watch?v=6DQzFUDkQB8 | example - Netflix and spotify
  - low-risk incremental modernization of large legacy applications
    ```mermaid
    flowchart LR
        U[Client] --> R[Router / API Gateway]
    
        R --> M[Legacy Monolith]
        R --> N[New Microservice]
    ```
-  [**stateless_vs_stateful services**](04_core_pattern_01_stateless_vs_stateful.md)
-  [**database-per-service**](04_core_pattern_03_database-per-service.md)
-  [**Aggregator pattern**](04_core_pattern_03_aggregator.md)
- **serverless service** | eg: AWS lambda |  FaaS
    - breakdown application into multiple serverless function
    - then orchestrate with step functions
- [**sidecar**](04_core_pattern_04_sidecar.md)
- [**ambassador**](04_core_pattern_04_ambassador.md)
---
## Microservice :: communication pattern
- [⭐Communication-pattern](../SD_03_Core-building-blocks/SD_03_54_IPC)

More Reference:
- https://youtube.com/watch?v=q7K20k6rV9E
- https://youtube.com/watch?v=6DQzFUDkQB8
- https://youtube.com/watch?v=ms0qYCWJmfc

---
## Microservice :: Resilient pattern (Failure isolation)
- [Bulkhead](03_resiliency_pattern_01_bulkhead.md)
- Timeouts
- [Circuit breakers](03_resiliency_pattern_02_circuit-breaker.md)
- [Rate Limiting](../SD_04_protecting-servers/02_protection_03_RateLimiting.md)
- [auto-scaling](../SD_04_protecting-servers/02_protection_01_auto-scaling.md)
    - [heartbeat_2.md](../SD_01_foundation/03_concept_02_heartbeat_2.md)
---

## Microservice :: Modern tooling
Kubernetes already covers a lot of infrastructure concerns that older Spring Cloud components were commonly used for.

> Interview takeaway: Kubernetes replaces a lot of the infrastructure-oriented Spring Cloud stack, but Spring Cloud still has useful application-level libraries.

| Concern                    | Spring Cloud approach              | Kubernetes equivalent ⭐           |
| -------------------------- | ---------------------------------- |-----------------------------------|
| Service discovery          | Eureka                             | Kubernetes Service + DNS          |
| Client-side load balancing | Ribbon / Spring Cloud LoadBalancer | Kubernetes Service/load balancing |
| Centralized config         | Spring Cloud Config                | ConfigMaps / Secrets              |
| Routing/API gateway        | Spring Cloud Gateway               | Ingress / Gateway API             |
| Health checks              | Actuator + discovery integration   | Liveness / readiness probes       |
| Scaling                    | App-side tooling                   | HPA / KEDA                        |
| Resilience                 | Resilience4j integrations          | App-level + service mesh options  |


| Area          | Minikube                             | AWS EKS                                  |
| ------------- | ------------------------------------ | ---------------------------------------- |
| Purpose       | Local Kubernetes learning/dev        | Production-grade managed Kubernetes      |
| Runs where    | Your laptop/desktop                  | AWS                                      |
| Control plane | Local                                | Managed by AWS                           |
| Cost          | Basically free                       | EKS control plane + worker/node costs    |
| HA            | Usually single-node/local            | Multi-AZ control plane                   |
| Scaling       | Limited by your machine              | HPA + Cluster Autoscaler/Karpenter       |
| Load Balancer | `minikube service`, tunnel, NodePort | ALB/NLB via AWS integrations             |
| Storage       | Local/hostPath                       | EBS, EFS, FSx                            |
| IAM           | No AWS IAM integration               | IAM + IRSA / Pod Identity                |
| Networking    | Simplified local networking          | VPC, subnets, SGs, ENIs                  |
| Best for      | Learning, POC, local development     | Staging/production, AWS-native workloads |

---

## hands-on project
### Java
- springCloud learning | `currency conversion project`
  - https://github.com/lekhrajdinkar/spring-cloud/blob/main/README.md 
  - https://github.com/lekhrajdinkar/spring-microservices-k8s | k8s

- https://github.com/lekhrajdinkar/microservice-java/tree/main/MicroserviceModule

### Python
- https://github.com/lekhrajdinkar/microservice-python/tree/main/src/webApp1