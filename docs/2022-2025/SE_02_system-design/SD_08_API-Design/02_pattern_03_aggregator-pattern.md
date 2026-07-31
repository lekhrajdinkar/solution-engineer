# Aggregator Pattern
## Overview
https://www.youtube.com/watch?v=6W8FCW2rWNQ
> **design approach** that simplifies combining data from multiple services into a single, unified response

---
## Aggregator service
> - looks similar to aggregator in saga
> - Can also perform aggregation on API gateway

Flow
- receives a client request, 
- sends out requests to multiple microservices, 
- compiles and processes the data, 
- and then sends a **unified response** back to the client.

Type/s: 
- **Simple Aggregators:** 
    - Handle straightforward scenarios 
    - where data from services can be directly combined without extensive processing, 
    - eg:  displaying product categories on an e-commerce homepage.

- **Complex Aggregators:** 
    - Deal with intricate scenarios involving dependencies and complex computations, 
    - eg:  **personalized financial dashboard** 

---
## Aggregator service : Implementation Methods
**Scatter-Gather:** 
- The aggregator sends requests **simultaneously** to multiple services 
- and waits for all responses before combining them.

**Chained Pattern:** 
- Requests are made **sequentially**, 
- with the output of one service becoming the input for the next,
- useful when there are dependencies between services 

**Branch Pattern:** 
- Similar to scatter-gather but allows for different processing paths based on responses, 
- **handling conditional logic and complex workflows**  👈🏻

## Challenges 
- Aggregator service -> **single point of failure**
- **Performance Overhead**
  - Slow or failing underlying services can impact the aggregator service.
  - aggregating multiple responses can introduce latency, 
  - thus requiring optimization.  👈🏻
- As complexity grows, **maintaining and scaling** the aggregator ms becomes challenging