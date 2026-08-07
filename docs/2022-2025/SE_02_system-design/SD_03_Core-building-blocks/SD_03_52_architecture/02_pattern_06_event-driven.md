# Modern : Event-Driven Architecture (EDA)
## ✔️References
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2190592891
- https://www.youtube.com/watch?v=hrvx8Nv9eQA
- [evolution-of-system.md](../SD_01_foundation/01_basic_01_evolution.md)
- [Message-broker / Event-broker](../../PE_03_message-broker)
- [⭐event-loop](../SD_01_foundation/05_concept_05_event-loop.md)

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
![img_2.png](../../../../99_img/2025/se_02_sd/01/04/img_2.png)
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
## ⭐Asynchronous Comm pattern
[02_asynchronous.md](../SD_03_54_Communication-pattern/02_asynchronous.md)

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
