# CQRS vs CRUD
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360668/posts/2192532488
---
## Overview
> Different model for read and write and single database cant handle both
```mermaid
flowchart LR
    D[Doctor<br/>Update record] --> CMD[Command Side]
    A[Admin<br/>Schedule appointment] --> CMD
    I[Insurance<br/>Process claim] --> CMD

    CMD --> SQL[(Transactional SQL DB)]

    SQL -->|Events| BUS[Event Bus]
    BUS --> NOSQL[(Read-Optimized DB)]

    P[Patient<br/>View history] --> QUERY[Query Side]
    QUERY --> NOSQL
```

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

---
## Simple CQRS  

```mermaid
flowchart LR
    CMD[Command Model] --> DB[(Same Database)]
    QRY[Query Model] --> DB
    style CMD fill:yellow,color:black
    style QRY fill:yellow,color:black
```
## Pure CQRS

![img_2.png](../../../../99_img/2025/se_02_sd/01/05/img_2.png)

```mermaid
flowchart LR
    U[Client] --> C[Command API]
    U --> Q[Query API]
    C --> W[(Write Database)]
    W --> E[Events-bus \n - Event-sourcing-pattern]
    E --> |events|R[(Read Model)]
    Q --> R
    style Q fill:yellow,color:black
    style C fill:yellow,color:black
```
---
## Example:
```mermaid
flowchart LR
    C[Command API] --> W[(LSM Write Store)]
    W --> E[Kafka / Change Events]
    E --> P[Projection Consumer]
    P --> R[(B-Tree Read Store)]
    Q[Query API] --> R
    style W fill:cyan,color:black
    style R fill:green,color:black
```
