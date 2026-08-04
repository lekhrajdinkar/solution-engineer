# Distributed-system
## Overview
- [distributed-system : basic.md](../SD_01_foundation/01_basic_02_distributed-system.md)
- [CAP-theorem.md](../SD_01_foundation/01_basic_03_CAP-theorem.md)

## First Step
![img.png](../../../99_img/2025/first-step.png)

| Stage              | Meaning                                                                   |
| ------------------ | ------------------------------------------------------------------------- |
|[partitioning](../SD_05_DataLayer+storage/03_concept_03_partitioning.md) | One database splits a large table into smaller logical parts              |
|[sharding](../SD_05_DataLayer+storage/03_concept_03_sharding.md)       | Those data partitions are distributed across multiple database servers    |
| **Distributed DB** | Multiple nodes coordinate replication, routing, consistency, and failover |

--
## Patterns
- [event-sourcing](../SD_03_Core-building-blocks/SD_03_52_architecture/02_pattern_02_event-sourcing.md)
- [CQRS](../SD_03_Core-building-blocks/SD_03_52_architecture/02_pattern_01_CQRS.md)

## Concepts
- [consistent-hashing.md](../SD_05_DataLayer+storage/03_concept_02_consistent-hashing.md)
- [database-replication.md](../SD_05_DataLayer+storage/03_concept_01_database-replication.md)
- [distributed-caching.md](02_01_distributed-caching.md)
- [distributed-Locking.md](02_02_distributed-Locking.md)
- [distributed-Transaction.md](02_03_distributed-Transaction.md) 
  - [SAGA.md](../SD_05_DataLayer+storage/05_pattern_02_SAGA.md)
- [distributed-FileSystem.md](02_04_distributed-FileSystem.md)
