# Distributed Lock
## reference
- https://www.youtube.com/watch?v=qY4MfWv01pI (Skip)

## Overview
purpose:
- Ensures data integrity and consistency in distributed systems 
- by allowing only **one node** or process to access a shared resource at a time
- thus Solves: **race conditions and deadlocks**


```mermaid
flowchart LR
    A1[App Instance 1] --> L{Distributed Lock}
    A2[App Instance 2] --> L
    A3[App Instance 3] --> L

    L -->|Lock acquired| R[Critical Resource]
    L -->|Others wait / retry| W[Blocked]
```

```mermaid
flowchart LR
    A[Service Instance] --> B{Acquire Lock}
    B -->|Success| C[Critical Section]
    B -->|Failed| D[Retry / Backoff]
    C --> E[Release Lock]
    D --> B
    style D fill:yellow,color:black
```
---
## Distributed lock ?
> Distributed lock = a globally visible ownership record saying “resource X is currently owned by process Y.
```
    { 
        Lock Key   : lock:trade-order:12345
        Owner      : service-instance-7
        Expires At : 18:30:45
        Token      : 1042
    }
```
```mermaid
flowchart LR
    A[Pod-1] --> L[(Lock Store)]
    B[Pod-2] --> L
    L --> K["lock:trade-order:12345
    owner=pod-1
    TTL=30 sec"]
    A --> R[(Trade Order 12345)]
    style A fill:yellow,color:black
```

---
## ideal distributed locking principles

| # | Property             | Meaning                                                                 |
| - | -------------------- | ----------------------------------------------------------------------- |
| 1 | **Mutual Exclusion** | Only **one node/process** can hold the lock at a time                   |
| 2 | **Fault Tolerance**  | Locking system should remain available even if a node fails             |
| 3 | **Performance**      | Lock acquisition/release should be fast and efficient                   |
| 4 | **Fairness**         | Competing nodes should get a reasonable/fair chance to acquire the lock |

![img.png](../../../99_img/2026/01/img.png)

---
## Distributed Locking Approaches
| Approach                 | How it works                                                              | Pros                                                | Cons                                              |
| ------------------------ | ------------------------------------------------------------------------- | --------------------------------------------------- | ------------------------------------------------- |
| **Centralized Locking**  | One central lock server decides who owns the lock                         | Simple, fast                                        | **Single point of failure**, bottleneck           |
| **Token-Based Locking**  | A unique token/lease represents lock ownership and moves between nodes    | More fault-tolerant, avoids one central coordinator | More complex; token loss/recovery must be handled |
| **Quorum-Based Locking** | Lock is acquired only after getting approval from a **majority of nodes** | Better fault tolerance, no single-node dependency   | Higher latency and complexity                     |


**Quorum-Based Locking**

```mermaid
flowchart LR
    C[Client / Service]

    C -->|Acquire lock| N1[Lock Node 1 ✅]
    C -->|Acquire lock| N2[Lock Node 2 ✅]
    C -->|Acquire lock| N3[Lock Node 3 ✅]
    C -->|Acquire lock| N4[Lock Node 4 ❌]
    C -->|Acquire lock| N5[Lock Node 5 ❌]

    N1 --> Q{Quorum reached?}
    N2 --> Q
    N3 --> Q
    N4 --> Q
    N5 --> Q

    Q -->|3 of 5 = Majority| L[Lock Acquired]
    Q -->|Less than 3| F[Lock Failed]
```

---
## Common implementations

| # | Algorithm / Approach           | How it works                                                  | Typical use                           |
| - | ------------------------------ | ------------------------------------------------------------- |---------------------------------------|
| 1 | **Redis `SET NX` + TTL**       | Create lock only if key does not exist; TTL prevents deadlock | Simple distributed jobs               |
| 2 | **Redlock**                    | Acquire lock on majority of independent Redis nodes           | Redis-based distributed locking       |
| 3 | **DB Lock**                    | Use row lock, advisory lock, or unique constraint             | Apps already relying on relational DB |
| 4 | **ZooKeeper Lock**             | Create ephemeral sequential nodes; smallest node owns lock    | Strong coordination / leader election |
| 5 | **etcd Lease + CAS**           | Acquire key using transaction/CAS and attach a lease          | Kubernetes-style coordination  ⭐      |
| 6 | **DynamoDB Conditional Write** | `PutItem` succeeds only if lock key doesn't exist             | AWS-native locking                    |


