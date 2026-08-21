# Maps-reduce Framework
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2190592402
- https://static.googleusercontent.com/media/research.google.com/en//archive/mapreduce-osdi04.pdf

---
## History
Google 2012:
- Faced the challenge of **processing huge volumes** of data collected 24/7
- **MapReduce** helps to **process massive distributed datasets** efficiently + **fault-tolerant manner**
- library implementations MapReduce `Hadoop, spark, pySpark`

---
## Overview
- Best for: batch processing, log analysis, aggregations, indexing, and large-scale ETL.
- MapReduce is a **distributed processing model** for handling very large datasets across many machines.
```mermaid
flowchart LR
    I[Large Input Dataset] --> S[Split into chunks]

    S --> M1[Map Worker 1]
    S --> M2[Map Worker 2]
    S --> M3[Map Worker 3]

    M1 --> SH[Shuffle and Sort]
    M2 --> SH
    M3 --> SH

    SH --> R1[Reduce Worker 1]
    SH --> R2[Reduce Worker 2]

    R1 --> O[Final Output]
    R2 --> O

    style SH fill:#f4b183,stroke:#333
    style O fill:#a9d18e,stroke:#333
```
```mermaid
flowchart LR
    I[Input Data in HDFS] --> M[Map]
    M --> KV[Intermediate Key-Value Pairs]
    KV --> S[Shuffle and Sort]
    S --> R[Reduce]
    R --> O[Final Output File in HDFS]
    style M fill:#d9c2f0,stroke:#6f2dbd
    style KV fill:#d9eaff,stroke:#2f73d9
    style S fill:#f4b183,stroke:#333
    style R fill:#a9d18e,stroke:#333
    style O fill:#fff2cc,stroke:#333
```
| Phase                | Purpose                                                     |
|----------------------| ----------------------------------------------------------- |
| **Map**  `idempotent`  | Process input records and emit intermediate key-value pairs |
| **Shuffle and Sort** | Group all values belonging to the same key                  |
| **Reduce**   `idempotent`        | Aggregate grouped values into final results                 |

```
Input: "hello world hello"

Map:
(hello, 1)
(world, 1)
(hello, 1)

Shuffle:
hello → [1, 1]
world → [1]

Reduce:
hello → 2
world → 1
```

---
## Fault Tolerance 
Model handles machine failures or network partitions
- Map and Reduce operations should ideally be deterministic and **idempotent**:
- hence, can reprocess them with new worker.

```mermaid
flowchart LR
    M[Master / Coordinator] --> W1[Map Worker 1]
    M --> W2[Map Worker 2]
    M --> W3[Reduce Worker]

    W2 -->|Worker crashes| F[Task marked failed]
    F -->|Retry same input split| W4[New Worker]
    W4 --> O[Continue processing]

    style M fill:#f4b183,stroke:#333
    style F fill:#f4cccc,stroke:#333
    style W4 fill:#a9d18e,stroke:#333
```
---
## key concepts
Distributed File System / cluster: 
- Assumes data is split into chunks, 
- replicated, and spread across many machines

Central Controller / coordinator: 
- A central component in the distributed file system 
- that knows where data chunks reside 
- and communicates with all machines

Map worker/s
- Map functions operate on data **locally**, 
- meaning map programs are sent to each node.
- instead of moving large datasets
- result into  intermediary key-value structure

Reduce worker/s
- Key-Value Structure are crucial for the reduce phase 
- allowing for commonalities and patterns to be identified and reduced

---
## Use cases
> NOT low-latency real-time processing.

| Use case                       | Example                                               |
| ------------------------------ | ----------------------------------------------------- |
| Large-scale log processing     | Count errors by service, region, or hour              |
| Word or event counting         | Count words, clicks, views, or transactions           |
| ETL and data transformation    | Clean and aggregate raw files before loading          |
| Search indexing                | Build inverted indexes for documents                  |
| Reporting and analytics        | Calculate daily sales, averages, totals               |
| Machine learning preprocessing | Prepare and aggregate huge training datasets          |
| Graph processing               | Compute links, connections, or PageRank-style metrics |

```mermaid
flowchart LR
    D[Large Dataset] --> M[MapReduce]
    M --> L[Log Analysis]
    M --> E[ETL]
    M --> A[Batch Analytics]
    M --> I[Search Indexing]
    M --> ML[ML Data Preparation]
    style M fill:#f4b183,stroke:#333
```

```mermaid
flowchart LR
    F[Large file split across distributed nodes]

    F --> N1[Node 1<br/>File block 1]
    F --> N2[Node 2<br/>File block 2]
    F --> N3[Node 3<br/>File block 3]

    N1 --> M1[Map locally]
    N2 --> M2[Map locally]
    N3 --> M3[Map locally]

    M1 --> S[Shuffle and Group]
    M2 --> S
    M3 --> S

    S --> R[Reduce and Aggregate]
    R --> O[Final analysis result]

    style S fill:#f4b183,stroke:#333
    style R fill:#a9d18e,stroke:#333
```
