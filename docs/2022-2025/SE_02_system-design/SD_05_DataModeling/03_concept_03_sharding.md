# Sharding
- [https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2192332143](https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2192332143)
> first component is system is DB, who needs scaling
---
## Overview
- Horizontal scaling (both read / write) of DB.
- Sharding = **horizontal** partitioning across **multiple database servers**.
- each shard is **independent Database server** with its own replication, index,etc
```
Partitioning → split data
Sharding     → place those partitions on different servers
```

**Highly available + fault-tolerant (replicas) + scalable (with consistent-hashing)**
```mermaid
flowchart LR
    U[Users] -->|shard key| R[Shard Router<br/>Application Middle Layer]

    R -->|user_id 1-1000| S1P[(Shard 1 Primary<br/>Independent DB \n storage,local-index, replicas, etc)]
    R -->|user_id 1001-2000| S2[(Shard 2 ...)]
    R -->|user_id 2001-3000| S3[(... )]
    R -->|user_id 2001-3000| SN[(➕ Shard N<br/>\n ⭐Horizontal Scale by adding \n more and more shards \n ⭐with consistent-hashing )]

    S1P -->|Synchronous replication| S1S[(Shard 1 Standby<br/>Failover)]
    S1P -.->|Asynchronous replication| S1R1[(Shard 1 Read Replica 1)]
    S1P -.->|Asynchronous replication| S1R2[(Shard 1 Read Replica 2)]

    S1R1 --> Q1[Read Traffic]
    S1R2 --> Q2[Read Traffic]

    style R fill:#f4b183,stroke:#333
    style S1S fill:#f4cccc,stroke:#333
    style S1R1 fill:#9dc3e6,stroke:#333
    style S1R2 fill:#9dc3e6,stroke:#333
```

## Choosing a good shard key.
Good shard key:
- evenly distributes data
- evenly distributes traffic
- supports common query patterns
- key should not change, else end up moving data across shards.

eg: **user_id - account ids - region identifier**

---
## Challenges
| Challenge   (Set-1)          |                                                                                                                                   |
|------------------------------| -------------------------------------------------------------------------------------------------------------------------------------------- |
| **Cross-shard transactions** | A transaction touching multiple shards may require distributed protocols such as two-phase commit, increasing latency and failure complexity |
| **Global secondary indexes** | Maintaining one searchable index across all shards requires coordination and can become expensive                                            |
| **Monitoring**               | Every shard must be monitored separately because load, storage, replication lag, and latency can vary                                        |

| Challenge (Set-2)            |                                                                 |
|------------------------------| --------------------------------------------------------------------- |
| **Hot shard**                | One shard receives most requests and becomes the bottleneck           |
| **Cross-shard queries**      | Requires scatter-gather across multiple databases                     |
| **Cross-shard joins**        | Expensive and difficult; often handled in application code            |
| **Rebalancing**              | Moving data when adding or removing shards is operationally expensive |
| **Failure handling**         | Each shard needs replicas, backups, monitoring, and failover          |
| **Routing complexity**       | Router must always know the correct shard mapping                     |

---
## Sharding Summary
1. Choose a strong shard key
2. Build a reliable routing layer
3. Plan for re-sharding
4. Combine sharding with replication

```mermaid
flowchart LR
    K[Good Shard Key] --> R[Routing Layer]
    R --> S[Multiple Shards]
    S --> RS[Re-sharding Strategy]
    S --> RP[Replication per Shard]

    RP --> O[Scalable and Reliable Database]

    style K fill:#d9eaff,stroke:#333
    style R fill:#f4b183,stroke:#333
    style S fill:#d9c2f0,stroke:#333
    style RP fill:#a9d18e,stroke:#333
    style O fill:#ffd966,stroke:#333
```
