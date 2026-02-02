# Distributed Messaging System
> Distributed messaging queues are crucial 
> - for building **scalable** and **reliable** distributed systems, 
> - by enabling async communication across microservices 👈🏻
> - Also used in big data processing

---
## Overview
- https://www.youtube.com/watch?v=tBXK49CB-BU
- entire infra: cluster, message brokers, routing, delivery mechanisms, etc

---
## Communication protocol
### 💠Pub/Sub Model : `kafka, KDS`
- Messages are categorized into **topics**
- allows **multiple consumers** to receive the same message, 
- making it suitable for **broadcasting** information

Async comm between client-server

> **At Least Once Delivery**
>   - subscriber receives a message but loses connection before acknowledging it.
>   - leading the topic to re-send the message when the connection is re-established
>   - **idempotent operation** yields the same outcome regardless of how many times it's performed
>
> **Message Ordering** : "first-in, first-out" (FIFO)
>
> **Message Replay**, due to their underlying persistent storage

**Components(4)**
- Publishers:
- Topics:
    - Act as channels or intermediaries with specific information.
    - Persistent Storage via Topic
- Subscribers:
    - Clients that listen for data from topics.
    - can subscribe to multiple topics based on their needs.
    - Unlike streaming, subscribers listen to the topic, not directly to the publishers
- Messages: Represent data or event

**More**
- **separation of concern**. separate topic for each business domain.
- **Content based filter** subscriber to filter data

---
### 💠P2P Model : `SQS, rmq`
- [01_SQS.md](../CE_02_AWS_SAA/05_decoupling/01_SQS.md)
- [04_active_MQ.md](../CE_02_AWS_SAA/05_decoupling/04_active_MQ.md)

- Messages are stored sequentially in a **queue**
  - intermediary, temporarily storing messages 
  - until they are processed by recipients
  - allowing asynchronous data transfer
  - decouples different parts of a system
  
- **exactly-once processing**
  - one consumer
  - Once a consumer processes a message, it is removed from the queue

---
### 💠Event-Driven-Messaging : `EventBridge`

---
### 💠Stream-processing: `kafka`
- [03_01_KDS_KinesisDataStream.md](../CE_02_AWS_SAA/05_decoupling/03_01_KDS_KinesisDataStream.md)
- [03_02_KDF_KinesisDataFirehose.md](../CE_02_AWS_SAA/05_decoupling/03_02_KDF_KinesisDataFirehose.md)
- https://youtu.be/mG3xQb_-rV4?si=hki7lXuwQRX0qalf