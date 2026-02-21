# Microservice System
## ✔️References
- https://chatgpt.com/c/2f54de12-b416-4a76-80a0-ebd286b0c467 | ms arch
- https://chat.deepseek.com/a/chat/s/6e7456d4-cc1b-42be-ae19-c3ede730936f | ms comm
- https://chat.deepseek.com/a/chat/s/3d8b4d99-81b7-4dac-ad69-519f9bc33dea | deployment arch - `event-driven` vs `deployment-driven`

---
## ✔️Overview
💠**Microservice System**
- **lightweight independent** services for each business/feature/domain.
- **distributed nature** 
  - whole app is distributed among many MS.
  - talks over network
  - which adds up some complexity.

💠**Monolithic System**
- Entire software run as `single heavy process` on `expensive hardware`
    - tightly couple
    - legacy design and architecture.
    - redundant logic, 1000 lines of code, no modern language and design principles.
    -  built with a single technology stack

- Not designed to take full advantage of cloud-native features such as cloud's elasticity/auto-scaling, managed services, and distributed architectures.
- other challenges and limitation:
    - `scaling` of single feature in impossible, and scaling whole app is pricey.
    - `upgrade` : downtime and upgrade window.
    - `failure` in any part of a monolithic application can potentially bring down the entire system
    - Higher `operational costs` and less efficient use of computational resources.
    - `size grows`: new updates/features, keep on making appl more `heavy`.
  
---
## ✔️ Key concept
### Fault-tolerance and resilience
> DS continues to operate properly in the event of the failure of some of its components
- 1 Retry-pattern
- [2 Circuit-breaker-pattern](02_pattern_02_circuit-breaker-pattern.md)
- 3 Load balancer with health mechanism

### Service-discovery
- process of automatically detecting network locations of service instances.
- service registry service -  Netflix Eureka
- k8s services ✔️
- [service-discovery](01_concept_02_service-discovery.md)

### Communication architecture
- [event-driven-arch 👈🏻](../SD_01_system/02_arch_04_event-driven-arch.md)
- [service-mesh-arch 👈🏻](../SD_01_system/02_arch_05_service-mesh-arch.md) | Side car pattern
- [API-gateway.md](02_pattern_06_API-gateway.md)
- [BFF](02_pattern_01_BFF.md)

### Data Consistency pattern
> **database per service pattern**, leds data consistency issues. solutions:
- [distributed-Transaction](../SD_02_database+storage/02_03_distributed-Transaction.md)
- [distributed-Locking](../SD_02_database+storage/02_02_distributed-Locking.md)

---
## ✔️ Benefits
On-demand **scalability** 
- run MS on different hosts /Availability
- can run on Cloud and take full advantage of cloud

**Optimal resource usage** 
- run on matching hardware-requirement
- efficient and low cost.

**No downtime**
- Seamless updates(rollout)/rollbacks without any downtime.

**Mix of technologies**
- java
- py

---
## Challenges
As microservices grow, managing complexities becomes difficult, like:
- network failures
- secure communication
- monitoring
- load balancing
- zero-downtime deployments 
- ...

Solutions:
- ...

---
## Migrate from Monolithic to Microservice
- **strangler pattern**
  - https://youtu.be/DpuQ3-7e-rY?si=zYsggXjtUcNsh-jz
- modernize monolith business applications / Distributed software.
- Not all monolithic app is good candidate.
- complex and risky, due to its tightly coupled components and dependencies.
- not smooth, has to survive below challenges:
    - `Refactoring phase` : break down into modules
    - `application resiliency` as whole.
    - `Choosing runtimes` on cloud :
        - underlying OS, hardware, library, runtime env for each MS. there might be conflict.
        - running well on one hardware/runtine , but not working same on other.
        - Solution:` Application containers`:
            - encapsulated `lightweight` runtime environments.
            - promised `consistent` software environments.
            - each MS/module running in their own execution environments `isolated` from one another.



