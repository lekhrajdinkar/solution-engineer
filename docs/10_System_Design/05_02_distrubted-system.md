## intro
- independent computers (nodes) that appear to the users as a single coherent system. 
- These nodes coordinate with each other to achieve a common goal.

  | Concept                       | Description                                                                                                                                                                             |
  | ----------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
  | **Multiple Nodes**            | ✅ Yes, the system runs on multiple computers or servers. These can be in the same datacenter or geographically distributed.                                                             |
  | **Coordination**              | ✅ Nodes communicate over a network (e.g., internet) using protocols. Coordination can be direct or via services like **ZooKeeper**, **etcd**, or **consensus protocols (Raft, Paxos)**. |
  | **Fault Tolerance**           | ✅ A major goal is to survive partial failures (e.g., if one node fails, others continue).                                                                                               |
  | **Concurrency & Parallelism** | ✅ Yes, parallelism is common but it's not the **only** or **defining** property. Distributed systems also emphasize **resilience**, **scalability**, and **consistency**.               |
  | **Transparency**              | Users shouldn’t feel the system is distributed (location, access, and failure transparency).                                                                                            |
  | **Shared State**              | Nodes may share or replicate data, but **consistency models** (like eventual or strong consistency) come into play.                                                                     |
