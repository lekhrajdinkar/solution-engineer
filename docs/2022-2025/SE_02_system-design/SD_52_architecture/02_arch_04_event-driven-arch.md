# Modern : Event-Driven Architecture (EDA)
## ✔️References
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2190592891
- https://www.youtube.com/watch?v=hrvx8Nv9eQA
- [evolution-of-system.md](../SD_01_foundation/01_basic_01_evolution.md)
- [Message-broker / Event-broker](../../PE_03_message-broker)

---
## ✔️Overview
> - **EDA** focuses on asynchronous, event-based communication,
> - **Service Mesh** manages synchronous communication.

- Traditional sync **request-response model** inefficient to scale, due to tight coupling.
- Modern EDA **decouples** services, 
  - enabling more scalable, flexible, and efficient systems 👈🏻
  - where services communicate through events (generation, propagation, and consumption) 
- **3 key point to consider in designing EDA system** 👈🏻
  - handle **event processing order** 
  - handle **idempotency**
  - Handle **eventual consistency** across multiple services.

---
## ✔️Architecture 
![img_2.png](../../../99_img/2025/se_02_sd/01/04/img_2.png)
### 1. Event / message
- eg: order placed event

### 2. Broker
- Allows producers and consumers to communicate, without direct knowledge of each other, 
- with an intermediary broker:
  - like Kafka, RabbitMQ, or Azure Service Bus
  - that **handles events** by: ⭐
    - queuing
    - streaming
    - ...
- these act as **persistence solution** as well, storing message/event.

### 3. Producers 
- Micro-services or systems that generate events.

### 4. Consumers  
- Services that consume events and then trigger various actions.
- **Simple** Event Processing
- **Complex** Event Processing
  - Multiple events are aggregated 
  - and analyzed to detect patterns

---
## ✔️EDA Models
### 1. Pub-Sub 
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
---
### 2. Stream-processing

---
### 3. P2P queue

---
### 4. Event-Driven-Messaging : EventBridge

---
## ✔️Real-World EDA Use Cases: 
### 1. Netflix 
- Handles over a billion events daily, 
- Every user action generates an event consumed by various services, 
  - such as the **recommendation engine**. 
  - ...
- also monitors service health, generating events for alerts or automated recovery.
> - Netflix uses EDA for asynchronous user events
> - and a Service Mesh for synchronous service-to-service communication.

![img.png](../../../99_img/2025/se_02_sd/01/04/img.png)

### 2. Uber
- Manages millions of rides daily using EDA 
- for real-time data processing and service coordination. 
- A "ride requested" event is consumed by multiple services 
  - matching 
  - ETA,
  - pricing. 
- Uber also collects real-time traffic data via telemetry events from driver phones, optimizing routes.

![img_1.png](../../../99_img/2025/se_02_sd/01/04/img_1.png)
