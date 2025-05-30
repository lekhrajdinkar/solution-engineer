## kickoff
- **Sharding vs Partitioning in RDBMS**
  - Partitioning splits a table into smaller segments within the **same** database server (by rows or columns) for performance and manageability.
  - Sharding distributes data **across multiple servers** (horizontal scaling) to handle large-scale workloads.

---
## A. RDBMS partitioning
- https://www.youtube.com/watch?v=oJj-pltxBUM&ab_channel=High-PerformanceProgramming - intro
- https://www.youtube.com/watch?v=VcTPmEJeKM4&ab_channel=AWSEvents - aws rds sharding
- **method** : list, range, hash , composite (multi-level)
- **Type**: 
  - inheritance  (flexible, complex, old)
  - Declarative (new)
- **Strategies**:
  - traditional
  - blue-green
  - logical replication

---
## B. RDBMS Sharding
- https://www.youtube.com/watch?v=be6PLMKKSto&ab_channel=Exponent

