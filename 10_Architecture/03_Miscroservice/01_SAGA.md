https://www.youtube.com/watch?v=d2z78guUR4g&ab_channel=ByteMonk
---
## SAGA
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
- saga vs 2 phase
![img.png](../img/03/img.png)