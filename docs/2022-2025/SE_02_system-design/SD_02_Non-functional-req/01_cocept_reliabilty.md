# Reliable System
## ✔️Overview
* **Core Definition:** Reliability is the **overall outcome**—ensuring the system consistently meets operational standards under normal and adverse conditions.
* **Key Pillars:**
    * **Correctness & Consistency:** Delivering accurate results with zero data loss and high data integrity.
    * **Timeliness:** Maintaining low latency and high performance.
    * **Failure Recovery:** Utilizing Fault Tolerance (FT) and Resilience (R) strategies.
    * **Continuous Monitoring:** Leveraging health checks, metrics, failover mechanisms, and load balancing.

```mermaid
flowchart LR
    R[Reliable System: \n broader outcome]
    R --> C[- Correct & consistent results\n - Predictable behavior \n - No data loss \n - Data integrity]
    R --> F[Failure recovery - FT + R]
    R --> A[High Availability]
    R --> T[Timeliness - performance/latency]
    R --> M[Monitoring - health, metric, failover, LB]
```
### Correctness
- [02_NFR_04_consistency.md](02_NFR_04_consistency.md)
- [02_NFR_05_Durability.md](02_NFR_05_Durability.md)

### Availability
- [02_NFR_01_Availability.md](02_NFR_01_Availability.md)
- [02_NFR_03_Scaling.md](02_NFR_03_Scaling.md)

### Timeliness
- [02_NFR_02_Performance.md (latency,etc)](02_NFR_02_Performance-latency.md)
- [02_NFR_06_ReadWrite-ratio.md](02_NFR_06_Read-Write-ratio.md)

### Failure recovery
- [02_NFR_07_fault-tolerance.md](02_NFR_07_fault-tolerance__.md)
- [02_NFR_07_resiliency.md](02_NFR_07_resiliency__.md)

---
## ✔️Levels
> **Key Engineering Takeaway:** *The role of an engineer is to choose the right level of reliability for the specific business requirements rather than over-engineering.*

```
Each level adds (cost and complexity) 💲
⭐Engineer job : choose right level for requiremnet

High Availability   -------
        ↓
Fault Tolerance     --------------
        ↓
Resilience          ---------------------
        ↓
Reliability         ---------------------------------------
```
```mermaid
flowchart BT
    HA["HIGH AVAILABILITY\n<small>Minimize downtime, fast recovery</small>"]
    FT["FAULT TOLERANCE\n<small>Zero downtime, failures remain invisible</small>"]
    RS["RESILIENCE\n<small>Handles unexpected failures gracefully</small>"]
    RL["RELIABILITY\n<small>Availability + Correctness + Timeliness</small>"]
    HA --> FT
    FT --> RS
    RS --> RL
    style HA fill:#123d2d,stroke:#22c55e,stroke-width:3px,color:#22c55e
    style FT fill:#17233d,stroke:#3b82f6,stroke-width:3px,color:#60a5fa
    style RS fill:#3b2418,stroke:#f97316,stroke-width:3px,color:#fb923c
    style RL fill:#2e1c3d,stroke:#a855f7,stroke-width:3px,color:#c084fc
    linkStyle 0,1,2 stroke:#64748b,stroke-width:2px
```