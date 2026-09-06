# Leader election
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360668/posts/2190592897
- https://www.youtube.com/watch?v=TzwiGTbUSHg bm
> leader election is a vital problem in distributed systems

## problem statement
- When multiple servers are introduced, a new problem arises: **duplicate requests**
- To avoid this, only **one server should process** requests at a time, called the **leader**
```
Example:

kafka problem
- if masterNode fails, then
- which in-sync replicas would be elected as master node ?

RDS
- if primary write crashed  
- which read replicate must be promoted
```

---
## Consensus Algorithm
- Electing a leader in a distributed system is a complex problem, 
- especially with network partitions or failures
- Computer scientists have developed **consensus algorithms** like:
  - **Paxos** and **Raft** 
  - typically not implemented directly, 
  - Rather companies use **services** like `zookeeper`, `etcd`

---
## Algo
### 1. Paxos
[01_algo_01_paxos.md](../02_Advance_concepts/01_algo_01_paxos.md)

### 2. Raft
[01_algo_02_raft.md](../02_Advance_concepts/01_algo_02_raft.md)