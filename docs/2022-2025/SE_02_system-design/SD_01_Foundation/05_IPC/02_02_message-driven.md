#  Asynchronous Communication : Messaging based

## Overview
| Channel Type          | Delivery                          | Best For                  | Interaction | Example                                            |
| --------------------- | --------------------------------- | ------------------------- | ----------- | -------------------------------------------------- |
| **Point-to-Point**    | One message → **one receiver**    | Command / task processing | One-to-One  | Job queue where one worker processes the task      |
| **Publish-Subscribe** | One message → **all subscribers** | Events / notifications    | One-to-Many | `OrderPlaced` → Billing + Inventory + Notification |


## point-2-point (Queue)
- AWS SQS
- rabit MQ

---
## pub-sub (Topic)
> Use Pub-Sub when one business event must trigger multiple independent downstream actions,
> without tightly coupling the producer to consumers.

- In event of network failure/partition, subscriber will leave and comeback and, need message again.
- that's why **at least once** delivery is required, (one or more times delivery),
    - hence keep idempotent consumer.
- Message/event are **ordered**.
- In **kafka** each topic is **distributed in nature (has partitions)**
- More:
    - separation of concern
    - content based filtering

```mermaid
flowchart LR
    P[Publisher] --> T[ Event Broker - Topic \n ➕➕Scale by adding more partition]
    T --> S1[Subscriber-1 / consumer-group-1]
    T --> S2[Subscriber-2 / consumer-group-2]
    T --> S[... / ...]
    T --> SN[Subscriber-N / consumer-group-N \n ➕➕Scale by adding more]
    style SN fill:cyan,color:black
```
**separation of concern** : create multiple topic
```mermaid
flowchart LR
O[Order Service] --> OC[order-created topic]
O --> OP[order-paid topic]
O --> OS[order-shipped topic]

    OC --> I[Inventory Service]
    OC --> N1[Notification Service]

    OP --> B[Billing Service]
    OP --> A[Analytics Service]

    OS --> T[Tracking Service]
    OS --> N2[Notification Service]
```
**content based filtering**: filter and then subscribe
```mermaid
flowchart LR
    P[Publisher] --> T[Order Events Topic]

    T --> F1{eventType = OrderCreated}
    T --> F2{region = US}
    T --> F3{amount > 1000}

    F1 --> I[Inventory Service]
    F2 --> N[US Notification Service]
    F3 --> R[Risk Service]
```