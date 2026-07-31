# Fault tolerance
> Failure is expected.
> The system should degrade gracefully instead of completely stopping.

- https://chatgpt.com/g/g-p-6a68d3926dd4819180c1c9bf855e98f3-system-design-bm-acedemy/c/6a6bf42e-5fa0-83e8-8bbb-c332211494b3
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158857209/posts/2193918304
---
## Overview
- 0 downtime | **highly available** system can have downtime.
- Fault tolerance is the ability of a system to continue operating when one or more components fail.

| Concept           | Focus                               |
| ----------------- | ----------------------------------- |
| High availability | Minimize downtime                   |
| Fault tolerance   | Continue operating despite failure  |
| Disaster recovery | Restore service after major failure |

---
## key relationship
### Fault tolerance vs High availability

airplane analogy - has 4 engines, but it does not require all four to remain operational.
- Engine 1 fails
- Engines 2, 3, and 4 continue working
```mermaid
flowchart LR
    E1[Engine 1 ❌ Failed]
    E2[Engine 2 ✅]
    E3[Engine 3 ✅]
    E4[Engine 4 ✅]

    E2 --> F[Flight continues]
    E3 --> F
    E4 --> F
```

**High availability** - flat tire analogy
```mermaid
flowchart LR
    A[Car running] --> B[Flat tire]
    B --> C[Short interruption]
    C --> D[Replace with spare tire]
    D --> E[Journey continues]
```
---

### 
```mermaid
flowchart TB
    subgraph HA["HIGH AVAILABILITY"]
        direction TB
        subgraph FT["FAULT TOLERANT"]
            X["System continues operating<br/>without interruption \n💲💲💲💲💲"]
        end
        Y["System may recover through<br/>quick failover \n💲💲"]
    end
    style HA fill:#0b3d2e,stroke:#22c55e,stroke-width:4px,color:#22c55e
    style FT fill:#102a56,stroke:#3b82f6,stroke-width:4px,color:#60a5fa
    style X fill:transparent,stroke:transparent,color:#ffffff
    style Y fill:transparent,stroke:transparent,color:#ffffff
```
### Fault tolerance vs Resilience
- EXPECTED FAILURES
- UN-EXPECTED FAILURES (Resilience, planned for it)
```mermaid
flowchart TB
    T1["PLANNED FOR"]
    T2["EXPECTED FAILURES"]
    T3["Fault tolerance"]

    T1 --> T2

    subgraph F[" "]
        direction LR

        A["✅<br/><br/>🖥️<br/><br/><b>Server Crash</b>"]
        B["✅<br/><br/>💽<br/><br/><b>Disk Failure</b>"]
        C["✅<br/><br/>🔗<br/><br/><b>Network Partition</b>"]
        D["✅<br/><br/>🏢<br/><br/><b>Data Center Outage</b>"]
    end

    T2 --> A
    T2 --> B
    T2 --> C
    T2 --> D

    style T1 fill:transparent,stroke:transparent,color:#22c55e
    style T2 fill:transparent,stroke:transparent,color:#22c55e,font-size:32px,font-weight:bold

    style F fill:transparent,stroke:transparent

    style A fill:#102b25,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style B fill:#102b25,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style C fill:#102b25,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style D fill:#102b25,stroke:#22c55e,stroke-width:2px,color:#ffffff

    linkStyle 0 stroke:transparent
    linkStyle 1,2,3,4 stroke:transparent
```

