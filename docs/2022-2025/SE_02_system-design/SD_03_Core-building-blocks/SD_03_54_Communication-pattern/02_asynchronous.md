# B. Asynchronous Communication

```mermaid
flowchart TB
    ASYNC[Asynchronous Communication] --> MSG[Messaging]
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
```
## B.1. Polling
### B.1.1. Short Polling
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

### B.1.2. Long Polling
- A variation where the server **holds the client's request** 
  - `hanging GET (with timeout)` 👈🏻
- until data is available **or** a timeout occurs
  - This allows the server to "push" information, 
  - but clients still need to reconnect periodically after timeouts
  - https://www.youtube.com/watch?v=pnj3Jbho5Ck (02:00)

**problem**: since holds client's request, thus resource intensive.

![img_1.png](../../../../99_img/2026/02/07/03/img_1.png)

## B.2. Event Driven
### B.2.1 Fan-out 
```mermaid
flowchart LR
    P[Producer/server] --> E[Event / Message]

    E --> B[Broker / Pub-Sub]

    B --> C1[Consumer 1]
    B --> C2[Consumer 2]
    B --> C3[Consumer 3]
    B --> C4[Consumer 4]
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
![img.png](../../../99_img/2026/02/07/04/img.png)

### B.2.2 Webhook / callback
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

### B.2.3 Event Sourcing

### B.2.4 CQRS

---
## B.3. Messaging
### B.3.1 pub-sub

### B.3.2 point-2-point
https://www.youtube.com/watch?v=2v6KqRB7adg

![img.png](../../../../99_img/2026/02/07/02/img.png) ![img_1.png](../../../99_img/2026/02/07/02/img_1.png)

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
