# pattern : scaling-writes
## Reference
- https://www.hellointerview.com/learn/system-design/patterns/scaling-writes
- [02_NFR_06_Read-Write-ratio.md](../SD_02_Non-functional-req/02_NFR_06_Read-Write-ratio.md)
- [02_NFR_03_Scaling.md](../SD_02_Non-functional-req/02_NFR_03_Scaling.md)

--- 
## Problem
bottleneck: 
- individual components **hit hard limits** on disk I/O, CPU, network bandwidth,etc
- **Bursty**, **high-throughput writes** with lots of **contention**  🔺
- eg: application grows from `100 w/s` to `1000000 w/s` of writes per second,

> **Write vs. Read Tension**
> - Optimizing for write performance often degrades read performance, and vice versa.
> - Different parts of your system may require different approaches.
> - the write side is often a much bigger challenge than read side

--- 
## Solution/s
Survive with single-database, first
- **Vertical Scaling**
- **Database Choices**

Then, throwing more hardware/complexity:
- **sharding and partitioning**
- Handling Bursts with **Queues and Load Shedding**
- **Batching and Hierarchical Aggregation**

> Do due diligence to confirm **bottleneck**, before prematurely adding hardware complexity:
> - back-of-the-envelope math : calculate actual write throughput  
> - next, check if it fits within our hardware capabilities. [Numbers-to-know](../SD_06_think-in-scale/01_04_Numbers-to-know.md#2-database)

---
## A1. vertical scaling
- cloud providers and data center operators offer substantially **more powerful hardware**, we can use before, we need to re-architect our application
- good chance hardware can go further, than you think
- [Numbers-to-know](../SD_06_think-in-scale/01_04_Numbers-to-know.md#2-database)

---
## A2. Database Choices
choose underlying data stores are already **optimized for the writes**

| Database type      | Examples              | Write/storage strategy                                                        | Best suited for                      |
| ------------------ | --------------------- |-------------------------------------------------------------------------------| ------------------------------------ |
| **Wide-column**    | Cassandra             | Append-only commit log; sequential disk writes; high write throughput `10k w/s` | High-volume distributed writes       |
| **Time-series**    | InfluxDB, TimescaleDB | Optimized for timestamped sequential writes; delta encoding/compression       | Metrics, telemetry, events           |
| **Log-structured** | LevelDB               | Appends new data rather than updating data in place                           | Fast writes and key-value workloads  |
| **Columnar**       | ClickHouse            | Efficient batch writes and column-oriented storage                            | High-throughput analytical workloads |

 Next, Further **optimize** database for writes

| Optimization                       | How it helps writes                                                                                                                                   | Trade-off / Consideration                                                           |
| ---------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- |
| **Disable expensive features**     | Temporarily disable foreign key constraints, complex triggers, or full-text search indexing during high-write periods to **reduce per-write processing**. | Can reduce data integrity or search freshness; should be used carefully.            |
| **Tune write-ahead logging (WAL)** | Configure WAL so multiple transactions can be batched before flushing to disk, reducing I/O overhead.                                                 | May increase the amount of data at risk if a failure occurs before data is flushed. |
| **Reduce index overhead**          | Remove unnecessary indexes so each write requires fewer index updates.                                                                                | Reads may become slower, especially for queries that depended on those indexes.     |

> General-purpose databases are designed to handle mixed workloads reasonably well, but they're not optimized for the extremes.

More references
- [AWS databases offerings](../../CE_02_AWS_SAA/03_database)
- [Data modelling :: choose-database](../SD_05_DataModeling/01_01_choose-database.md)

---
## A3. partitioning ⭐⭐
[partitioning](../SD_05_DataModeling/02_basic_concepts/03_02_database-partitioning.md)

---
## B1. Horizontal Sharding ⭐⭐
[horizonal sharding](../SD_06_think-in-scale/01_02_sharding.md)
- select a partition key that minimizes variance, in the number of **writes per shard**
- Keep in mind, that we need to also consider how the data might be **read**

---
## B2. Handling Bursts 
- partitioning and sharding will get you 80% of the way to scale and deal with steady traffic
- Real-world write traffic **isn't steady**
- some bursts are common
- but some scenario like `4x` spike on black friday (`25%` capacity working)
- solution is **Auto-scale** on system metric, **but Scaling up and down takes time.**
- so we either need to:
  - **buffer** the writes --> Queue
  - **get rid** of writes in a way , that is acceptable to the business --> Load shedding

[02_burst.excalidraw](draw/05_write-scale/02_burst.excalidraw)

### Handling Bursts with Queues
benefit: most important is **burst absorption**

Challenge
- queues are inherently **async**, so clients will also often need a way to **call back to check** the write was eventually made.
- can have : **unbounded growth of our queue**.
  - app server continues to write to the queue **faster**, than records can be written to the database
  - Until the **backlog** drains, users are still waiting on writes

> Use queues when you expect to have bursts that are short-lived,

### Handling Bursts with Load Shedding
- actually a powerful tool
- if system is overwhelmed, you need to **decide which writes to accept** and which to reject.
  - drop the less important writes
  - downside: suboptimal experience for some users.
  - it's better than letting everything fail

example:  Uber where users are reporting their locations at regular intervals. can shed call some.

>  putting some release valves in place shows, we can keep a **bad situation** (too much load) from **turning into a disaster** (system failure),

---
## B3. Batching 
[batching and intermediate-processing](draw/05_write-scale/03_batcher.excalidraw)
- write operations have **overhead** like network round trips, transaction setup, index updates
-  most databases process batches more efficiently than individual writes
- batching writes together.
- done at the application layer 
  - application itself isn't the source of truth, 
  - no need to handle the potential for data loss.
- intermediate-processing: we can look upstream to see how we can make the incoming data easier to process.
- on Database configure `flush to disk` in ms

## B4. Hierarchical Aggregation
- For high-volume data like analytics and stream processing, you often don't need to store individual events and instead need aggregated views
-  important insight is that these views can be **built up incrementally**. 
  - Hierarchical aggregation, processes data in stages, **reducing volume** at each step

---
[04_aggregator.excalidraw](draw/05_write-scale/04_aggregator.excalidraw)

**example**: In live video streams, 
- creates an ugly situation if there are millions of viewers, millions of users are writing
- they want to see all the latest comments and the counts

Broadcast Nodes + write processor Node
- instead of writing to N viewers, we only have to write to M broadcast nodes
- Assign the users to broadcast node, using a consistent hashing scheme.
- nodes can forward updates to their respective viewers.

--- 
## interview
### Use case / scenario
```
YouTube Top K
Strava
Rate Limiter
Ad Click Aggregator
FB Post Search
Metrics Monitoring
Notification System
```

--- 
## Deep dives

--- 
## Conclusion