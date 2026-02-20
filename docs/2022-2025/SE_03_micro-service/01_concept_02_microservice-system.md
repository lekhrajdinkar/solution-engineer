# Microservice System
## ✔️References
- https://chatgpt.com/c/2f54de12-b416-4a76-80a0-ebd286b0c467 | ms arch
- https://chat.deepseek.com/a/chat/s/6e7456d4-cc1b-42be-ae19-c3ede730936f | ms comm
- https://chat.deepseek.com/a/chat/s/3d8b4d99-81b7-4dac-ad69-519f9bc33dea | deployment arch - `event-driven` vs `deployment-driven`

---
## ✔️Overview
- **lightweight independent** services for each business/feature/domain.
- **distributed nature** 
  - whole app is distributed among many MS.
  - talks over network
  - which adds up some complexity.

---
## ✔️ Key concept
### Fault-tolerance and resilience
> DS continues to operate properly in the event of the failure of some of its components
- Retry-pattern
- [circuit-breaker-pattern](03_ms_01_circuit-breaker-pattern.md)

### Service-discovery
- process of automatically detecting network locations of service instances.
- service registry service -  Netflix Eureka
- k8s services ✔️

### Data Consistency pattern
> **database per service pattern**, leds data consistency issues. solutions:
- [distributed-Transaction](../SE_02_distributed-system/SD_02_database%2Bstorage/02_03_distributed-Transaction.md)
- [distributed-Locking](../SE_02_distributed-system/SD_02_database%2Bstorage/02_02_distributed-Locking.md)

### Communication architecture
- [event-driven-arch 👈🏻](../SE_02_distributed-system/SD_01_system/02_arch_04_event-driven-arch.md)
- [service-mesh-arch 👈🏻](../SE_02_distributed-system/SD_01_system/02_arch_05_service-mesh-arch.md)

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



