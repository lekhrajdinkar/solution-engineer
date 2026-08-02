# Leader election
> leader election is a vital problem in distributed systems

## Overview
- concept - https://www.youtube.com/watch?v=TzwiGTbUSHg bm
  - When multiple servers are introduced, a new problem arises: **duplicate requests**
  - To avoid this, only **one server should process** requests at a time
  - This chosen server is called the **leader**
- kafka problem
  - if masterNode fails, then
  - which in-sync replicas would be elected as master node ?

---
## Consensus Algorithm
- Electing a leader in a distributed system is a complex problem, 
- especially with network partitions or failures
- Computer scientists have developed **consensus algorithms** like:
  - **Paxos** and **Raft** 
  - typically not implemented directly, 
  - Rather companies use **services** like `zookeeper`, `etcd`

### Paxos

### Raft