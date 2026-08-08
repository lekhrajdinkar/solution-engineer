# Aggregator Pattern
- https://www.youtube.com/watch?v=6W8FCW2rWNQ


---
## Aggregator service
> - looks similar to aggregator in saga
> - Aggregator = fan-out calls to multiple services + combine results into one unified response.

```mermaid
flowchart LR
    C[Client] --> A[Aggregator Service]
    A --> U[User Service]
    A --> O[Order Service]
    A --> P[Payment Service]
    U --> A
    O --> A
    P --> A
    A --> C
```
> Type: 
> - Simple = fetch + combine
> - Complex = fetch + orchestrate + process + combine

**Simple Aggregator** 
- Handle straightforward scenarios 
- where data from services can be directly combined without extensive processing, 
- eg:  displaying product categories on an e-commerce homepage.

**Complex Aggregator** 
- Deal with intricate scenarios involving dependencies and complex computations, 
- eg:  personalized financial dashboard 

---
## Aggregator service :: Implementation Methods

| Pattern        | Flow        | Use when                |
| -------------- | ----------- | ----------------------- |
| Scatter-Gather | Parallel    | Independent services    |
| Chained        | Sequential  | Dependent services      |
| Branch         | Conditional | Decision-based workflow |

```mermaid
flowchart LR
    T[Scatter-Gather]
    A[Aggregator] --> S1[Service A]
    A --> S2[Service B]
    A --> S3[Service C]
    S1 --> A
    S2 --> A
    S3 --> A
    A --> R[Combined Response]
    style T fill:transparent,color:blue,stroke:none
```
```mermaid
flowchart LR
    T[Chained]
    A[Aggregator] --> S1[Service A]
    S1 --> S2[Service B]
    S2 --> S3[Service C]
    S3 --> A
    style T fill:transparent,color:blue,stroke:none
```
```mermaid
flowchart LR
    T[Condition]
    A[Aggregator] --> S1[Service A]
    S1 --> D{Condition?}
    D -->|Yes| S2[Service B]
    D -->|No| S3[Service C]
    S2 --> R[Final Response]
    S3 --> R
    style T fill:transparent,color:blue,stroke:none
```


## Challenges 
- Aggregator service -> **single point of failure**
- **Performance Overhead**
  - Slow or failing underlying services can impact the aggregator service.
  - aggregating multiple responses can introduce latency, 
  - thus requiring optimization.  👈🏻
- As complexity grows, **maintaining and scaling** the aggregator ms becomes challenging