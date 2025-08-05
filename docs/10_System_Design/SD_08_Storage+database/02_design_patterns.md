## Polyglot Persistence

## CQRS (Command Query Responsibility Segregation)
- read replicas
- write replicas
- Global Database Replication

## Sharding 
- across nodes

## Partitioning
- within same node

## Schema Evolution & Versioning

## Event Sourcing

## Saga Pattern 
- Distributed transaction management across services.
- [youtube](https://www.youtube.com/watch?v=d2z78guUR4g&ab_channel=ByteMonk)
- [deepseek 🗨️](https://chat.deepseek.com/a/chat/s/81394dc5-20ff-45bb-8fc3-001520d7ef4f)
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