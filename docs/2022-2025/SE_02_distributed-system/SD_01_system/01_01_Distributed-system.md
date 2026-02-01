# Distributed System
> Design Distributed System (large-Scale)

## Overview
- independent computers (nodes) that appear to the users as a **single coherent system**.
- system runs on **multiple nodes** in same datacenter or geographically distributed.
- Nodes **communicate** over a network: 👈🏻
    - to achieve a common goal.
    - Coordination/collaborate there actions
    - maintain a shared state
    - Coordination can be direct
    - or via services like ZooKeeper, etcd, or consensus protocols (Raft, Paxos)
- Will discover best practice, algo, architecture to built DS. 👈🏻
- **Scenario-1**:
  - multiple os process running on same laptop (limited CPU,etc) to achieve common goal.
  - run these os process on diff laptop, connected on same network. little better.
  - take this further ahead brings the concept of DS.
- eg:
  - microservices - popular way to build DS 👈🏻
  - Simple app running on Cloud is also DS, since cloud infra Distributed with region/s, az. And may auto-scale if user base grows

    
## key concepts
✔️**Network Partition** 👈🏻👈🏻
- This occurs when a network failure (temporary and permanent)
- divides a distributed system into isolated parts,
- preventing communication between them.
- This is an **unavoidable fact** in distributed systems
- Single Node server, has No n/w Partition :)

✔️**persistent Storage**
- For DS, persistent storage is vital to ensure data retrieval, in case of failures
  - Database --> not be ideal for asynchronous operations
  - Topic/Queue

✔️**Transparency** 
- Users shouldn’t feel the system is distributed (location, access, and failure transparency)

✔️**Fault Tolerance** 
- A major goal is to survive partial failures 
- e.g., if one node fails, others continue

✔️**resilience** 

✔️**scalability**

✔️**consistency**
- Nodes may share or replicate data,
- consistency models ( eventual or strong consistency)

✔️**Concurrency & Parallelism**

---
Take note in order and format in markdown.
