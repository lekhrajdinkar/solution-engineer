# Database per service pattern
- https://youtube.com/watch?v=DKQLhy9bgdk

## Overview

```mermaid
flowchart LR
    U[Client] --> O[Order Service]
    U --> P[Payment Service]
    U --> I[Inventory Service]

    O --> ODB[(Order DB)]
    P --> PDB[(Payment DB)]
    I --> IDB[(Inventory DB)]
```

## Benefits
```
Loose coupling
Independent deployment
Independent scaling
Each service can choose the best DB technology
Failure/data changes are isolated
```
## Challenges

| Challenge           | Why                                        |
| ------------------- | ------------------------------------------ |
| **Cross-service joins** | Data exists in different DBs               |
| Transactions        | No simple ACID transaction across services |
| **Consistency**         | Usually requires eventual consistency      |
| Reporting           | Aggregating data becomes harder            |

## Solutions
- Saga Pattern
- Event-driven communication
- CQRS
- Event Sourcing
- API Aggregation [aggregator.md](04_core_pattern_03_aggregator.md)