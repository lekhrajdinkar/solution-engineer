#  Asynchronous Communication

```mermaid
flowchart TB
    ASYNC[ Asynchronous Communication] --> MSG[Messaging]
    ASYNC --> EVENT[Event-Driven Communication]
    ASYNC --> POLL[Polling]

    POLL --> POLLL[Long Polling]
    POLL --> POLLS[Short Polling]

    MSG --> QUEUE[Queue<br/>Point-to-Point]
    MSG --> PUBSUB[Publish / Subscribe<br/>One-to-Many]

    QUEUE --> MQ[RabbitMQ / SQS]
    PUBSUB --> KAFKA[Kafka / SNS / EventBridge]

    EVENT --> FAN[Fan-out]
    EVENT --> WEBHOOK[Webhook / Callback]
    EVENT --> RELATED[Related Architecture Patterns]

    RELATED --> EVENTSRC[Event Sourcing]
    RELATED --> CQRS[CQRS]
    
    style POLL fill:yellow,color:black
    style MSG fill:yellow,color:black
    style EVENT fill:yellow,color:black
```
---
## 1. Polling
### 1.1. Short Polling
- https://www.youtube.com/watch?v=b4qyOpGg748
- client repeatedly requests data from a server **at set intervals** 
  - using any network protocol.eg: https, etc
- **problem** : it creates many new connections and often results in empty responses.
- eg:
  - Temperature Monitoring
  - AJAX application polls bts
  - not ideal for real-time applications like chat
- **reducing** the polling interval
  -  it significantly increases the **load on the server**, 
  - as clients send many **unnecessary requests**.
  
```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server
    C->>S: Any updates?
    S-->>C: No
    Note over C: Wait 5 seconds
    C->>S: Any updates?
    S-->>C: No
    Note over C: Wait 5 seconds
    C->>S: Any updates?
    S-->>C: New data
```

### 1.2. Long Polling
- A variation where the server **holds the client's request** 
  - `hanging GET (with timeout)` 👈🏻
- until data is available **or** a timeout occurs
  - This allows the server to "push" information, 
  - but clients still need to reconnect periodically after timeouts
  - https://www.youtube.com/watch?v=pnj3Jbho5Ck (02:00)

**problem**: since holds client's request, thus resource intensive.

![img_1.png](../../../../99_img/2026/02/07/03/img_1.png)

---
## 2. Event Driven
### 2.1 Fan-out 
| Concept               | Meaning                                                                                       |
| --------------------- | --------------------------------------------------------------------------------------------- |
| **Fan-out**           | A delivery pattern: one message is copied to multiple destinations                            |
| **Publish–Subscribe** | A messaging model: publishers send to a topic, and subscribers independently receive messages |

They look similar because pub-sub usually implements fan-out

fan-out-1:

```mermaid
flowchart LR
    P[Producer] --> SNS[SNS Fan-out]

    SNS --> Q1[SQS Queue 1]
    SNS --> Q2[SQS Queue 2]
    SNS --> L[Lambda]
```

fan-out-2: implemented with pub-sub:
```mermaid
flowchart LR
    P[Producer/server] --> E[Event / Message]
    E --> B[Broker / Pub-Sub]
    B --> C1[Consumer 1]
    B --> C2[Consumer 2]
    B --> C3[Consumer 3]
```

Twitter 2012-2013 problem : https://www.youtube.com/watch?v=FEkXjNFrL1o
```
Twitter had 150 million users 
 handled write - 6,000 tweets per second. 
 Challenge-1:
  - read requests: 300,000 requests per second to serve homepages
    - User timeline 
    - Home timeline
  - Fix-1: Adding indices speeds up reads but slows down writes.
           Since reads are more frequent than writes, this is a fair trade-off.
           
  - Fix-2: 
    - pre-computed and stored user home timelines in a Redis cluster
    - Twitter serves the cached timeline from Redis, significantly reducing latency
    - When a user tweets, the tweet is replicated into the home timeline queue of each follower, 
    - resulting in thousands of writes to redis, for a single tweet
    - this is fanOut 👈🏻
  
```
![img.png](../../../../99_img/2026/02/07/04/img.png)

---
### 2.2 Webhook / callback
- just **Http Post** with event data.
- https://www.youtube.com/watch?v=oQaJn6RdA3
- traditional: polling, long-live connection
    - eating resources
- Webhooks allow servers
    - to notify client applications
    - only when new events occur, rather than requiring clients to check periodically.
- eg: gitHub make post call --> harness trigger (POST /api, idempotent), payload: {eventId...}
- benefit:
    - Webhooks improve system performance,
    - reduce latency,
    - and are crucial in modern microservices architectures for enabling system decoupling

**Example for CI/CD pipeline in AWS**
- https://youtu.be/9zfAqoTm4-Q?si=_PGo_F1tcNZvuxyi
- ![img.png](../../../../99_img/2026/01/img-10.png)

### 2.3 Event Sourcing
> Note: just arch pattern
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360668/posts/2190592897

### 2.4 CQRS
> Note: just arch pattern
- [05_pattern_01_CQRS.md](../../SD_05_DataLayer%2Bstorage/05_pattern_01_CQRS.md)

---
## 3. Messaging
### 3.1 point-2-point
Reference
- https://www.youtube.com/watch?v=2v6KqRB7adg
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360668/posts/2190592897

![img.png](../../../../99_img/2026/02/07/02/img.png) 

![img_1.png](../../../../99_img/2026/02/07/02/img_1.png)

![img_2.png](../../../../99_img/2026/04/01/01/img_2.png)

**Example of transferring large video files to thousands of machines**
1. single server approach (10 videos, 5GB each) - `15 min`
2. sharding, 5 server (2 videos each, 5GB each) - `15/5 = 3 min`
3. P2P solution - `1 sec`
    -  large file is split into small chunks and distributed among peers
    - These peers then communicate with each other in **parallel** to assemble the complete file
    - **peer discovery**
    - **peer selection strategies** within a P2P network
    - Centralized database (tracker), Gossip protocol, distributed hash table (DHT)

### 3.2 pub-sub
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