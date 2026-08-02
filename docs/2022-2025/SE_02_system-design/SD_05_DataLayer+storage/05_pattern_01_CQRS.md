# CQRS
## Overview
Use it when:
- Reads and writes have very different scaling needs
- Read queries require heavy joins
- Domain rules are complex
- Different databases suit reads and writes
- Event-driven architecture already exists
  - Events must handle retries, ordering, and duplicates

Avoid it for simple CRUD systems because it adds :
- synchronization, 
- **eventual consistency,** 👈
- and operational complexity.
  
```mermaid
flowchart LR
    U[Client] --> C[Command API]
    U --> Q[Query API]
    C --> W[(Write Database)]
    W --> E[Events]
    E --> R[(Read Model)]
    Q --> R
```
Example:
```mermaid
flowchart LR
    C[Command API] --> W[(LSM Write Store)]
    W --> E[Kafka / Change Events]
    E --> P[Projection Consumer]
    P --> R[(B-Tree Read Store)]
    Q[Query API] --> R
```