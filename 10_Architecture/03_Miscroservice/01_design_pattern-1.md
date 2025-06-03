## reference
- https://chat.deepseek.com/a/chat/s/81394dc5-20ff-45bb-8fc3-001520d7ef4f
---
## Desifn patterns :parking:
### 1. SAGA
- https://www.youtube.com/watch?v=d2z78guUR4g&ab_channel=ByteMonk
- Concept of a long-running, interconnected sequence of operations, like a "saga" in storytelling
- data consistency without relying on traditional ACID transactions (which are impractical in distributed systems).
- steps:
  - Breaks a transaction into smaller, local steps.
  - uses compensating actions (rollback logic) if a step fails.
  - eg: E-Commerce Order
    ```text
    Step 1: Reserve inventory → Step 2: Charge payment → Step 3: Ship order.
    If payment fails: Trigger compensation → "Release inventory" + "Notify user."
    ```
```text
Purpose: 
Manage distributed transactions across multiple services.

Implementation:
Choreography-Based: Each service emits events to trigger the next step.
Orchestration-Based: A central coordinator manages the transaction flow.
Implement compensation actions for rollback (e.g., CancelOrder, RefundPayment).

Use Case: 
Order processing in e-commerce (inventory, payment, shipping services).
```
- SAGA vs 2PC (2 phase commit)
![img.png](../img/03/img.png)

---
### 2. Circuit Breaker
```text
Purpose: 
Prevent cascading failures in distributed systems.

Implementation:
Use libraries like Resilience4j or Hystrix.
Define thresholds (e.g., failure rate, timeout) to trip the circuit.
Implement fallback mechanisms (e.g., cached responses, default values).

Use Case: 
Microservices calling external APIs (e.g., payment gateways, third-party services).
```
---
### 3. CQRS (Command Query Responsibility Segregation)
```text
Purpose: 
Separate read and write operations for better performance and scalability.

Implementation:
Command Side: Handles state-changing operations (e.g., CreateOrder, UpdateUser).
Query Side: Optimized for read operations (e.g., GetOrderDetails, GetUserProfile).
Use Event Sourcing (optional) to store state changes as a sequence of events.

Use Case: 
Useful in systems where read and write workloads are highly imbalanced (e.g., e-commerce, reporting systems).
```