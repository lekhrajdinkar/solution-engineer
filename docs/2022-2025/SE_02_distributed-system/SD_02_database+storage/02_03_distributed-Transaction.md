# Distributed Transaction
## Overview
- Reference
  - https://www.youtube.com/watch?v=d2z78guUR4g bm ⭐
  - https://www.baeldung.com/cs/saga-pattern-microservices post
  - https://github.com/lekhrajdinkar/data-engineer/blob/main/docs/2012-2021/07_ACID%2BLocks.md | ACID
- monolith system, ACID on single database
- DS: Achieve **data-consistency** across different parts of a system,
  - transaction spans several services/ms
  - participant performs local transactions
  - thus extend the concept of ACID properties to scenarios where transactions span **multiple databases**
- Check below solution/s. 
--- 

## Solutions
### ✔️ Two-Phase Commit (2PC)
- traditional
- Also called - **Blocking Atomic Commit Protocol**

![img.png](../../../99_img/2026/02/01/img.png)
> Flow
> >components: a coordinator service + participant service/s
> 
> 💠prepare Phase
> - A central coordinator sends a "prepare" message to all participants.
> - Each participant checks if it can commit and responds "yes" or "no"
> 
> 💠Abort Phase 
> - only if coordinator dies
> 
> 💠commit/rollback
> - If all participants respond "yes," the coordinator sends a "commit" message.
> - If even one responds "no" or times out, the coordinator sends a "rollback" message

- use opensource implementation like `Apache Zookeeper`, Dont built from scratch.

**Drawbacks of 2PC:**
- Latency and Performance
  - Additional communication and coordination steps introduce latency
- single point of failure
  - coordinator node, can become the
- Blocking 
  - All other services need to wait until the slowest service finishes
  - Services become dependent on each other and the coordinator
- Deadlock: 
  - Transactions can deadlock if participants wait for each other to release resources

![img.png](../../../99_img/2026/02/01/img_7.png)

---
### ✔️  SAGA orchestration 
>  - preferred for simpler sagas 
>  - or when a clear audit trail and centralized control are needed

Example : flight booking
![img_1.png](../../../99_img/2026/02/01/img_1.png)

![img_2.png](../../../99_img/2026/02/01/img_2.png)

- A central Saga orchestrator manages the flow, 
- sending commands to participants
  - explicitly tells each service what action to take
- and tracking progress

Advantages
- Simpler implementation for individual services, 
- clear audit trail

Disadvantages
- Single point of failure if the orchestrator fails,
- services are highly dependent on the orchestrator

---
### ✔️  SAGA choreography
> - better for complex sagas with many services 
> - or when high scalability 
> - and loose coupling are required

![img_3.png](../../../99_img/2026/02/01/img_3.png)

- No central conductor;
- participants communicate directly **through events**
- Each service listens for events from other services and **acts autonomously**

Advantage
- Services are more independent and less reliant on a central coordinator, **better scalability** 👈🏻

Disadvantages
- Complex implementation 
  - services need to understand events
  - difficult to trace the flow

> `Axon Saga` – a lightweight framework and widely used with Spring Boot-based microservices

---
## Comparison
### 2PC vs saga
| Feature            | Two-Phase Commit (2PC)                                      | Sagas                                                                 |
|--------------------|--------------------------------------------------------------|------------------------------------------------------------------------|
| Control            | Central coordinator orchestrates transactions                | Can be choreographed (participants communicate directly) or orchestrated (central orchestrator manages flow) |
| Communication      | Synchronous; participants wait for coordinator               | Asynchronous; participants react to events independently              |
| Atomicity          | Strong atomicity; all operations succeed or fail             | Eventual consistency; temporary inconsistencies until compensation completes |
| Flexibility        | Less flexible; susceptible to coordinator failures           | More flexible and resilient; compensating transactions for error recovery |
| Performance        | Slower due to synchronous communication                      | Generally faster due to asynchronous communication and concurrent execution |
| Resource Locking   | Involves locking resources; can lead to contention            | Does not require resource locking                                     |
| Suitability        | Strong consistency, limited participants                     | Long-running, complex transactions across multiple services where eventual consistency is acceptable |

![img_5.png](../../../99_img/2026/02/01/img_5.png)

### orch-saga vs choreo-Saga
![img_4.png](../../../99_img/2026/02/01/img_4.png)

### orch-saga vs 2PC
> since both has central coordinator

![img_6.png](../../../99_img/2026/02/01/img_6.png)

