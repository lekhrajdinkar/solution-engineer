# Distributed Transaction
## Overview
- transaction spans several services/ms
- participant performs local transactions
- https://www.baeldung.com/cs/saga-pattern-microservices

  
## solution-1 : 2PC 
- phases : prepare > commit
- there is a **coordinator component**, contains the logic to manage the transaction.
- ![img.png](../../../99_img/2026/02/01/img.png)
- cons:
  - coordinator node, can become the single point of failure.
  - All other services need to wait until the slowest service finishes
  - not supported in NoSQL databases.
    - ms1 (sql) , ms(noSQL) ❌

## Solution-2 : SAGA
- saga flow:
  - **Saga participant**
  - **Saga Execution Coordinator** / SEC
    - captures saga-logs (sequence of events of a distributed transaction)
    - inspects the Saga log to identify the impacted components 
    - and the sequence in which the compensating transactions should run.
    - compensating transactions must be idempotent and retryable. 👈🏻

![img_1.png](../../../99_img/2026/02/01/img_1.png)

### choreography 
- each microservice that is part of the transaction publishes an event that is processed by the **next microservice**.
- good few participants
- `Axon Saga` – a lightweight framework and widely used with Spring Boot-based microservices

![img_2.png](../../../99_img/2026/02/01/img_2.png)

![img_3.png](../../../99_img/2026/02/01/img_3.png)

### orchestration
- a **single** orchestrator is responsible for managing the overall transaction status

![img_4.png](../../../99_img/2026/02/01/img_4.png)