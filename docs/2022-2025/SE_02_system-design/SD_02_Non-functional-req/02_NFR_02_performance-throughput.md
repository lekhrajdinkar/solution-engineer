# BIG-2 of 3: Performance: Throughput
- [02_NFR_02_Performance-latency.md](02_NFR_02_Performance-latency.md)
---
## A. Overview
- measure capacity: the amount of work a computer can perform within a given time.
- Operations completed per unit time
- example:  data transferred per second, no of http req per sec
- Bitrate - use to measure video streaming.

---
## B. key relationShip
| Metric     | Meaning                                | Example                |
| ---------- |----------------------------------------| ---------------------- |
| Latency    | Time taken by one operation            | API responds in 200 ms |
| Throughput | Operations completed per unit time     | 5,000 requests/second  |
| Bandwidth  | Maximum theoretical capacity. bits/sec ||

```
A system can have:
    High throughput and low latency
    High throughput and high latency
    Low throughput and low latency
    Low throughput and high latency  

>>> System processes 10,000 requests/second,but every request takes 5 seconds.
```
---
## C. Improve throughput
### 1. reduce latency
- if reduce latency, then in same time can handle more requests.

### 2. Parallelisation
**💠MapReduce**
- Benefit: lower processing time and higher throughput.
- Trade-off: coordination, partitioning and aggregation complexity.

```mermaid
flowchart LR
    F[Large File] --> C1[Chunk 1]
    F --> C2[Chunk 2]
    F --> C3[Chunk 3]
    F --> C4[Chunk 4]

    C1 --> M1[Mapper]
    C2 --> M2[Mapper]
    C3 --> M3[Mapper]
    C4 --> M4[Mapper]

    M1 --> R1[Reducer]
    M2 --> R1
    M3 --> R2[Reducer]
    M4 --> R2
``` 
**💠Queue scaling**
- Add More Consumer/workers
- distributed MS system === each kafka partition as a queue
```mermaid
flowchart LR
    P[High-rate messages] --> Q1[Queue / Partition 1]
    P --> Q2[Queue / Partition 2]
    P --> Q3[Queue / Partition 3]

    Q1 --> C1[Consumer 1]
    Q2 --> C2[Consumer 2]
    Q3 --> C3[Consumer 3]
```

### 3. improve network bandwidth
- rate of data transfer `bits/sec`
- in real word, bandwidth (theory) == throughput(actual) 👈

### 4. scaling 
**💠Scale Read Throughput**
- a leader handles write +  replica/s handles reads
- can also be part of parallelisation
- trade-off > replication lags
```mermaid
flowchart TB
    W[Writes] --> P[(Primary Database)]

    P --> R1[(Read Replica 1)]
    P --> R2[(Read Replica 2)]
    P --> R3[(Read Replica 3)]

    U[Read Requests] --> R1
    U --> R2
    U --> R3
```
---
## 🙏interview
### Ask these discuss
| Steps |    What to discuss                                |
|-------| -------------------------------------------------- |
| 1     | Clarify latency, throughput, payload and geography |
| 2     | Identify network, server and client bottlenecks    |
| 3     | Parallelize independent work                       |
| 4     | Scale consumers, queues or partitions              |
| 5     | Distinguish bandwidth from actual throughput       |
| 6     | Replicate data for read scaling                    |

