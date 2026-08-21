# Sharding
## Reference
- https://www.hellointerview.com/learn/courses/system-design/lesson/thinking-in-scale/sharding
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/data-modeling#scaling-and-sharding
- https://www.youtube.com/watch?v=L521gizea4s | hi
- [https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2192332143](https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2192332143)
- https://www.youtube.com/watch?v=be6PLMKKSto&ab_channel=Exponent
- first component in system who needs scaling is **Database**
---
## Overview
> Sharding = **horizontal** partitioning across **multiple database servers**.

**When to choose sharding:**
- scale storage
- scale Read throughput
- scale Write throughput

**Approach**
- choosing **shard key**
- choosing a **partition strategy** (hashing) that keeps related data together 👈
- call out trade off
- Address how to handle growth (Consistent hashing)

> Each shard is **independent Database server** with its own replication, index, etc | Combine sharding with replication

**Partitioning vs Sharding**
```
Partitioning → split data
Sharding     → place those partitions on different servers
```
- useful for : Multi-tenant SaaS apps (isolate customer data).
- `postgres` Doesn't provide automatic sharding, do manually, **Citus** (PostgreSQL Extension)

**Example:**
- Highly available + fault-tolerant (replicas) + scalable (with consistent-hashing)
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
---
## STEP-1. Choosing a good shard key.
> - choice of shard key is **often permanent** and affects every query
> - 💡Golden Rule: Shard by your **primary access pattern** to keep related data collocated on the same shard.

**Good shard key:**
- **Evenly distributes** data/traffic | high cardinality
- Should **not change**, else end up moving data across shards.
- Supports common **query/access patterns**
  - **a. Collocation** (Keeping Related Data Together), to Avoid **cross-shard queries** whenever possible 👈
  - eg: timeline show posts from users you follow. following user-1(shard-1) and user-2(on shard-2), involve cross shard query
  - **b. prevent hot shard**
  - eg: `createdAtYear` as key, will bring all recent data in one node and cause hot shard later, if quering recent data

![img.png](img.png)

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
## STEP-2. choose distribution Strategies

```mermaid
graph TD
    Data["Incoming Data (Write / Query)"] --> Choice{Partitioning \nStrategy}

    Choice --> R["<b>Range-Based</b><br>e.g., ID 0–10k, 10k–20k"]
    Choice --> H["<b>Hash-Based</b><br><code>hash(key) % N</code>"]
    Choice --> L["<b>List / Entity-Based</b><br>e.g., Region, Tenant"]

    H --> S1["-Balanced distribution<br> -Harder range scans"]
    R --> S2["-Easy range queries<br> -Risk of hot spots"]
    L --> S3["-Clear isolation<br> -Risk of uneven shard sizes"]

    style Choice fill:#f8f9fa,stroke:#333,stroke-width:2px
    style H fill:#ffffff,stroke:#007bff,stroke-width:1.5px
    style R fill:#ffffff,stroke:#28a745,stroke-width:1.5px
    style L fill:#ffffff,stroke:#dc3545,stroke-width:1.5px
```

```
Range-Based Sharding
  - Shard 1: order_id 1-1000
  - Shard 2: order_id 1001-2000
  
Key-Based (Hash) Sharding
  - Shard 1: user_id % 4 = 0
  - Shard 2: user_id % 4 = 1
  
Directory-Based Sharding
  - SELECT shard_location FROM shard_map WHERE user_id = 123;
```

### 1. Range based
- in early days only shard-1 is active, other 2 were idle

![img_2.png](img_2.png)

> ⚠️ Be careful with time-range sharding.
> - This is usually an **anti-pattern for write-heavy systems.** 👈
> - making all current writes hit the same shard (the latest time range), creating a **hot shard.**
> - works better for archival or analytics workloads where **recent data is read-heavy**.

### 2. Hash based ⭐
- even distribution across shards
![img_1.png](img_1.png)

**trade off**
- **Rebalancing**: new shared add,  now its mode 4 (before mode 3), resulting into huge re-shuffling
- solution: consistent hashing

![img_3.png](img_3.png)

### 3. Directory based
![img_4.png](img_4.png)

trade off:
- extra hopping and latency
- SPF

### 4. hybrid
- mix of above
- have a routing layer to implement

---
## Challenges 🔺
### 1. hot shards
- Add dedicated shard for celebrity problem and use **directory shard** to move their post in shard-4 as shown below.
![img_5.png](img_5.png)

### 2. Cross-shard queries
- Cannot eliminate completely
- eg: get all popular top 10 post, across whole platform 
  - sol-1: **cache expensive** queries in cache with TTl
  - Sol-2: **denormalize data**, repeating data acoss data for some scenarios

![img_6.png](img_6.png)

### 3. maintain consistency
- [distributed-Transaction](02_03_distributed-Transaction.md)

### More
| Challenge                    |                                                                                                                                   |
|------------------------------| -------------------------------------------------------------------------------------------------------------------------------------------- |
| **Cross-shard transactions**🔺 | A transaction touching multiple shards may require distributed protocols such as two-phase commit, increasing latency and failure complexity |
| **Global secondary indexes** | Maintaining one searchable index across all shards requires coordination and can become expensive                                            |
| **Monitoring**               | Every shard must be monitored separately because load, storage, replication lag, and latency can vary                                        |
| **Hot shard**🔺              | One shard receives most requests and becomes the bottleneck           |
| **Cross-shard queries**      | Requires scatter-gather across multiple databases                     |
| **Cross-shard joins**        | Expensive and difficult; often handled in application code            |
| **Rebalancing** 🔺             | Moving data when adding or removing shards is operationally expensive |
| **Failure handling**         | Each shard needs replicas, backups, monitoring, and failover          |
| **Routing complexity**       | Router must always know the correct shard mapping                     |


---
## Database scaling
![img.png](../../../99_img/2025/first-step.png)

| Stage            | Meaning                                                                   |
| ---------------- | ------------------------------------------------------------------------- |
|[partitioning](../SD_05_DataModeling/02_basic_concepts/03_02_database-partitioning.md) | One database splits a large table into smaller logical parts              |
|**sharding**      | Those data partitions are distributed across multiple database servers    |
| **Distributed DB** | Multiple nodes coordinate replication, routing, consistency, and failover |
