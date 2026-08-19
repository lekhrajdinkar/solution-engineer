# Sharding
- [https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2192332143](https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2192332143)
> first component is system is DB, who needs scaling
---
## Overview
- When your data gets too large for a single database, you need to shard it across multiple machines
- The **key is choosing a partition strategy that keeps related data together** 👈
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
> - Golden Rule: Shard by your **primary access pattern** to keep related data collocated on the same shard.
> - choice of shard key is **often permanent** and affects every query

Good shard key:
- **evenly distributes** data/traffic | eg: **user_id - account ids - region identifier**
- supports common **query/access patterns**
- key should **not change**, else end up moving data across shards.
- **Collocation** (Keeping Related Data Together) 👈

```mermaid
graph TD
    subgraph ShardedCluster ["Sharded Cluster (Collocated by post_id)"]
        subgraph Shard1 ["Shard Node 1"]
            P1["posts (post_id: 101)"]
            C1["comments (post_id: 101)"]
        end

        subgraph Shard2 ["Shard Node 2"]
            P2["posts (post_id: 102)"]
            C2["comments (post_id: 102)"]
        end
    end

    Query["GET /posts/101 + Comments"] -->|"Single Node Query (No distributed join)"| Shard1
```
---
## Strategies

```mermaid
graph TD
    Data["Incoming Data (Write / Query)"] --> Choice{Partitioning \nStrategy}

    Choice -->|Hash of Key| H["<b>Hash-Based</b><br><code>hash(key) % N</code>"]
    Choice -->|Value Intervals| R["<b>Range-Based</b><br>e.g., ID 0–10k, 10k–20k"]
    Choice -->|Geo / Category| L["<b>List / Entity-Based</b><br>e.g., Region, Tenant"]

    H --> S1["Balanced distribution<br>Harder range scans"]
    R --> S2["Easy range queries<br>Risk of hot spots"]
    L --> S3["Clear isolation<br>Risk of uneven shard sizes"]

    style Choice fill:#f8f9fa,stroke:#333,stroke-width:2px
    style H fill:#ffffff,stroke:#007bff,stroke-width:1.5px
    style R fill:#ffffff,stroke:#28a745,stroke-width:1.5px
    style L fill:#ffffff,stroke:#dc3545,stroke-width:1.5px
```

### time-range sharding
> ⚠️ Be careful with time-range sharding. 
> - While it sounds appealing for "recent posts" queries, all current writes hit the same shard (the latest time range), creating a hot shard. 
> - This is usually an **anti-pattern for write-heavy systems.** 👈
> - Time-range partitioning works better for archival or analytics workloads where recent data is read-heavy but writes are spread out.

---
## Challenges
> Avoid cross-shard queries, This is expensive and complex.

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
