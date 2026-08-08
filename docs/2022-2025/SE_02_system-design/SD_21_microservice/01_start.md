# Microservice

## API-Design
[check here](../SD_08_API-Design)

## System design ::  Architecture pattern
- [ALL ⭐](../SD_03_Core-building-blocks/SD_03_52_architecture)
  - [monolith-arch :: Overview ](../SD_03_Core-building-blocks/SD_03_52_architecture/01_core_01_monolith-arch.md)
  - [microservice-arch :: Overview](../SD_03_Core-building-blocks/SD_03_52_architecture/01_core_02_microservice-arch.md)
    
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
- [⭐Communication-pattern](../SD_03_Core-building-blocks/SD_03_54_Communication-pattern)

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