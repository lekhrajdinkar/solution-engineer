# BIG-3 of 3 | Scaling
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158857209/posts/2192532351
- https://chatgpt.com/g/g-p-6a68d3926dd4819180c1c9bf855e98f3-system-design-bm-acedemy/c/6a691f19-3b10-83e8-91aa-1f39e15adefc

## Overview
- Scaling means increasing a system’s capacity to **handle growth** in below:
  - Users scale (10k > 10M > 1B)
  - Data  scale (100GB > 10 TB > 1 PB)
  - Requests-Response traffic scale / Background-jobs scale (100 RPS > 10k RPS > 100k RPS)
- **plus, maintain:** 
  - Good performance/latency, 
  - High availability, 
  - Reliability, 
  - Acceptable cost
- **plus, understand growth rate**:
  - 10% / year , 3X / per, 10X per year, etc

```
Note:
- 100 M users --> 1   req/day
- 1   M users --> 100 req/day
>> looks similar in terms of capacity but totally 2 diff systems
```
---
## General Approach
1. Remember-1, Arch for 10k user, completely fall apart at 10M user.
2. Remember-2 **cost multiplication factor**: 
   - At large scale, cost explodes and becomes unmanageable. 
   - eg: 10 extra DB call in small scale stage, 
   - later multiplies at large scale stage.
3. Consider System lifecycle: **design 10x-100X from day-1**, or rebuild everything. :()
4. **Do not scale everything blindly.** 
   - Scaling is mainly a bottleneck-management problem, 
   - not just a server-count problem.
   - `so,Measure the system --> Identify the bottleneck. --> Scale the affected component. --> REPEAT`
    ```mermaid
    flowchart LR
        A[Traffic Increases] --> B[Measure System]
        B --> C[Find Bottleneck]
        C --> D{Which Layer?}
        D -->|Application| E[Add App Instances]
        D -->|Database Reads| F[Add Read Replicas]
        D -->|Database Writes| G[Partition or Shard]
        D -->|Repeated Reads| H[Add Cache]
        D -->|Heavy Processing| I[Use Queue and Workers]
    ```
## Scaling strategies by layer
| Layer               | Small Application                       | Medium Application                                 | Large Application                                                                  |
| ------------------- | --------------------------------------- | -------------------------------------------------- | ---------------------------------------------------------------------------------- |
| **Load balancing**  | Single load balancer                    | Highly available load balancer across multiple AZs | Global/geographical load balancing, regional load balancers, service-level routing |
| **Application**     | One or two application instances        | Stateless horizontal scaling with auto scaling     | Large stateless clusters, Kubernetes/ECS, independent microservice scaling         |
| **Cache**           | No cache or in-memory application cache | Single Redis, cache-aside pattern                  | Redis Cluster, partitioning, CDN, multi-layer cache, 95–99% hit rate               |
| **Database reads**  | Single PostgreSQL instance              | PostgreSQL primary with read replicas              | Multiple read replicas, distributed SQL or NoSQL for high-scale workloads          |
| **Database writes** | Single database writer                  | Table partitioning, optimized indexes, batching    | Sharding, distributed databases, content/data separation, hot/cold data storage    |
| **Async work**      | Mostly synchronous processing           | Queue with background worker pool                  | Kafka or distributed messaging, large consumer groups, millions of events/sec      |
| **Storage**         | Local disk or single S3 bucket          | S3 with lifecycle policies and CDN                 | Distributed object storage, multi-region replication, hot/warm/cold tiers          |
| **Deployment**      | Single server or simple container       | Docker with ECS/EKS and auto scaling               | Multi-cluster, multi-region, progressive deployment                                |
| **Availability**    | Single AZ acceptable                    | Multi-AZ deployment                                | Multi-region active-passive or active-active                                       |
| **Observability**   | Basic logs and metrics                  | Centralized logging, metrics, alerts               | Distributed tracing, SLOs, automated anomaly detection                             |

```mermaid
flowchart LR
    S[Small Application] --> M[Medium Application]
    M --> L[Large Application]

    S --> S1[Single LB]
    S --> S2[Single App]
    S --> S3[Single PostgreSQL]

    M --> M1[Multi-AZ LB]
    M --> M2[Stateless App Instances]
    M --> M3[Redis + Read Replicas]
    M --> M4[Queue + Workers]

    L --> L1[Global Load Balancing]
    L --> L2[Microservices]
    L --> L3[Redis Cluster]
    L --> L4[Sharding / NoSQL]
    L --> L5[Kafka]
    L --> L6[Multi-Region]

    style S fill:#D1FAE5,stroke:#059669,stroke-width:2px,color:#000
    style M fill:#FEF3C7,stroke:#D97706,stroke-width:2px,color:#000
    style L fill:#FEE2E2,stroke:#DC2626,stroke-width:2px,color:#000
```
## Type of Application and basic arch
### small
```mermaid
flowchart LR
    U[Users] --> LB[Single Load Balancer]
    LB --> APP[Application]
    APP --> DB[(PostgreSQL)]
    APP --> S3[S3 Storage]
```
### medium
```mermaid
flowchart TD
    U[Users] --> LB[Multi-AZ Load Balancer]

    LB --> A1[App Instance]
    LB --> A2[App Instance]
    LB --> A3[App Instance]

    A1 --> CACHE[(Redis)]
    A2 --> CACHE
    A3 --> CACHE

    A1 --> PRIMARY[(PostgreSQL Primary)]
    PRIMARY --> RR1[(Read Replica)]
    PRIMARY --> RR2[(Read Replica)]

    A1 --> Q[Message Queue]
    Q --> W[Worker Pool]

    A1 --> S3[S3]
    
    classDef stage fill:#FEF3C7,stroke:#D97706,stroke-width:2px,color:#000
    class CACHE,RR1,RR2,Q,W,S3 stage;
```
### large
```mermaid
flowchart TD
    U[Global Users] --> GLB[Global Load Balancer]

    GLB --> R1[Region 1]
    GLB --> R2[Region 2]

    R1 --> LB1[Regional Load Balancer]
    R2 --> LB2[Regional Load Balancer]

    LB1 --> MS1[Microservices Cluster]
    LB2 --> MS2[Microservices Cluster]

    MS1 --> RC1[(Redis Cluster)]
    MS2 --> RC2[(Redis Cluster)]

    MS1 --> DB1[(Sharded DB / NoSQL)]
    MS2 --> DB2[(Sharded DB / NoSQL)]

    MS1 --> K1[Kafka Cluster]
    MS2 --> K2[Kafka Cluster]

    MS1 --> OBJ1[S3 ]
    MS2 --> OBJ2[S3 ]
    MS1 --> CDN1[CDN]
    MS2 --> CDN2[CDN]

    classDef stage fill:#FEF3C7,stroke:#D97706,stroke-width:2px,color:#000
    class GLB,R1,R2,MS1,MS2,K1,K2,CDN1,CDN2 stage;
```
```mermaid
flowchart LR
    P[Publisher] --> T[ Event Broker - Topic \n ➕➕Scale by adding more partition]
    T --> S1[Subscriber-1 / consumer-group-1]
    T --> S2[Subscriber-2 / consumer-group-2]
    T --> S[... / ...]
    T --> SN[Subscriber-N / consumer-group-N \n ➕➕Scale by adding more]
    style SN fill:cyan,color:black
```
---
## Type of scaling
### Vertical Scaling
> Increase the power of a single machine.- Add more CPU, RAM, Use faster storage, Upgrade network capacity, etc

**Advantages:** 
- Simple to implement
- Minimal application changes
- Easier operations

**Limitations**
- Hardware has a maximum limit
- Can become expensive
- May remain a single point of failure
- Upgrades may require downtime

---
### Horizontal Scaling
**Advantages**
- Supports large traffic growth
- Improves fault tolerance (no SPF)
- Enables high availability
- Works well with auto-scaling

**Limitations**
- More operational complexity due to distributed nature.
  - Requires load balancing
  - Requires distributed data management (DB, cache, etc)
  - Session and consistency handling become harder

---
## Scale Cube 🧊
[01_core_06_scale-cube.md](../SD_03_Core-building-blocks/01_core_06_scale-cube.md)

---
## Scaling protects server from Death Spiral ⭐
- [death-spiral](../SD_04_protecting-servers/02_protection_00_death-spiral.md)
- [protection server:: auto-scaling.](../SD_04_protecting-servers/02_protection_01_auto-scaling.md)
