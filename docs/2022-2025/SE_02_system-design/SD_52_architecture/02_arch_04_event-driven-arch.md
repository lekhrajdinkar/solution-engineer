# Event-Driven Architecture (EDA)
> - **EDA** focuses on asynchronous, event-based communication, 
> - **Service Mesh** manages synchronous communication.
---
## ✔️overview
- https://www.youtube.com/watch?v=hrvx8Nv9eQA
- traditional **request-response model**
  - inefficient due to tight coupling and escalating complexity
- Modern EDA, software design pattern 
  - where services communicate through:
  - the generation, propagation, and consumption of events
- EDA **decouples** services, 
  - enabling more scalable, flexible, and efficient systems 👈🏻
- **key point to consider in design EDA system** 👈🏻
  -  **event processing order** 
  -  **idempotency**
  - Handling **eventual consistency** across multiple services.

---
## ✔️Messaging-broker ...
- [check here for details](../../PE_03_message-broker)
### P2P queue
### Pub-Sub (Kafka)
### Event-Driven-Messaging : EventBridge
### Stream-processing

---
## ✔️Component
![img_2.png](../../../99_img/2025/se_02_sd/01/04/img_2.png)
### Event
- eg: order placed event

### Broker
- allows producers and consumers to communicate
- without direct knowledge of each other, 
- relying on a common messaging infrastructure.
- **An intermediary** 
  - (like Kafka, RabbitMQ, or Azure Service Bus)
  - that **handles events** by:
    - `queuing` 
    - `streaming` them between producers and consumers.

### Event Producers 
- `Microservices` or systems that generate events.
- eg: order micro-service,
- producing an "order placed" event

### Event Consumers  
- Services that consume events 
- and trigger various actions.
- **Simple** Event Processing
- **Complex** Event Processing
  - Multiple events are aggregated 
  - and analyzed to detect patterns

---
## ✔️Real-World EDA Use Cases: 
### Netflix 
- Handles over a billion events daily, 
- Every user action generates an event consumed by various services, 
  - such as the **recommendation engine**. 
  - ...
- also monitors service health, generating events for alerts or automated recovery.
> - Netflix uses EDA for asynchronous user events
> - and a Service Mesh for synchronous service-to-service communication.

![img.png](../../../99_img/2025/se_02_sd/01/04/img.png)

### Uber
- Manages millions of rides daily using EDA 
- for real-time data processing and service coordination. 
- A "ride requested" event is consumed by multiple services 
  - matching 
  - ETA,
  - pricing. 
- Uber also collects real-time traffic data via telemetry events from driver phones, optimizing routes.

![img_1.png](../../../99_img/2025/se_02_sd/01/04/img_1.png)
---
