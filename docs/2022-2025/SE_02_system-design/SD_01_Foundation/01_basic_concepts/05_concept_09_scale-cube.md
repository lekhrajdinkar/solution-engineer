# Core building: Scale Cube 🧊
- https://www.youtube.com/watch?v=q1RUnL4xTds
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2190592667
---
## Overview
```mermaid
flowchart LR
    A[Monolith] --> X[X-axis<br/>Add instances]
    X --> Y[Y-axis<br/>Split services]
    Y --> Z[Z-axis<br/>Partition data]
```
```mermaid
flowchart TD
    S[Scale Cube] --> X[X-axis<br/>Clone instances]
    S --> Y[Y-axis<br/>Split by service]
    S --> Z[Z-axis<br/>Split by data/users]
```
| Axis       | Scaling method           | Meaning                                      | Example                                          |
| ---------- | ------------------------ | -------------------------------------------- | ------------------------------------------------ |
| **X-axis** | Horizontal duplication   | Run multiple identical application instances | Multiple Spring Boot pods behind a load balancer |
| **Y-axis** | Functional decomposition | Split the application by business capability | User, Order and Payment microservices            |
| **Z-axis** | Data partitioning        | Split users or data across instances         | Sharding customers by customer ID or region      |

---
## X-axis: Clone the application
- Best for: increasing request-processing capacity.
- Requirement: application instances should normally **be stateless.**
- Trade-off: the **shared database** may become the next bottleneck.

```mermaid
flowchart LR
    U[Users] --> LB[Load Balancer]
    LB --> A1[App Instance 1]
    LB --> A2[App Instance 2]
    LB --> A3[App Instance 3]
```
---
## Y-axis: Split by functionality
- Break a monolith into independently scalable services.
- Best for: scaling and deploying **different business capabilities** independently.
- Trade-off: 
  - introduces **distributed-system complexity**, 
  - networking challenges 
  - data consistency challenges.
  
```mermaid
flowchart LR
    U[Users] --> G[API Gateway]
    G --> US[User Service]
    G --> OS[Order Service]
    G --> PS[Payment Service]
    style OS fill:green,color:white
```
---
## Z-axis : Split by data

```mermaid
flowchart LR
    OS[Order Service]-->R[Request Router]
    R -->|Customer ID 0–999| S1[Shard 1]
    R -->|Customer ID 1000–1999| S2[Shard 2]
    R -->|Customer ID 2000–2999| S3[Shard 3]
    style OS fill:green,color:white
```


