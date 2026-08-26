# Real-time Updates / push notifications
## reference
- https://www.hellointerview.com/learn/system-design/patterns/realtime-updates
- https://www.hellointerview.com/learn/system-design/patterns/realtime-updates/quick-reference
- [network-essential](../SD_04_network-essential)
- https://www.youtube.com/watch?v=EX5uZV3Tzss

---
## use-cases / scenario
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/google-docs | ws | h
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/fb-live-comments 
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/whatsapp 
  - hop:1 sse to receive | rest to send 
  - hop:2 p/s

```
More:
    Ticketmaster
    Uber
    Robinhood
    Strava
    Online Auction
    Online Chess
    ChatGPT
---
Live Dashboards and Analytics : | sse | p/s
Gaming and Interactive Applications : we | h
```
---
## Problem
> when you need servers to **proactively push updates** to clients.
- in collaborative document editor like `Google Docs`, see that change within `milliseconds`.
- `request-response model`: clients ask for data --> servers respond --> then **connection closes**
- so, core challenge is **establishing efficient, persistent communication channels** between clients and servers

---
## Solution :: 2 hop framework
- solution requires two distinct pieces:

```mermaid
flowchart RL
    Updates(("<b>Updates</b>"))
    Server["<b>Server</b>"]
    Client["<b>Client</b>"]
    Updates -->|"<b>hop 2</b><br>How does our server get triggered<br>when updates happen?"| Server
    Server -->|"<b>hop 1</b><br>How do updates propagate to clients?"| Client
    style Client fill:#1e1e1e,stroke:#ffffff,stroke-width:1.5px,color:#ffffff
    style Server fill:#1e1e1e,stroke:#ffffff,stroke-width:1.5px,color:#ffffff
    style Updates fill:#1e1e1e,stroke:#ffffff,stroke-width:1.5px,color:#ffffff
```
---
## ✔️hop-1. Client-Server Connection Protocols
> need persistent connections or clever polling strategies

```
Simple Polling: The Baseline
Long Polling: The Easy Solution
---
Server-Sent Events (SSE): The Efficient One-Way Street
Websockets: The Full-Duplex Champion
WebRTC: The Peer-to-Peer Solution
```

### 1. Polling
- [01_01_request-response.md](../SD_01_Foundation/05_IPC/01_01_request-response.md)
- [01_02_polling.md](../SD_01_Foundation/05_IPC/01_02_polling.md) | `short and long polling`

### 2. Streaming
- [02_01_streaming-sse.md](../SD_01_Foundation/05_IPC/02_01_streaming-sse.md) | `SSE`
- [02_02_streaming-wss.md](../SD_01_Foundation/05_IPC/02_02_streaming-wss.md) | `WS`
- [02_03_streaming-webRTC.md](../SD_01_Foundation/05_IPC/02_03_streaming-webRTC.md)| `webRTC`


```mermaid
flowchart TD
    Start{"What is the communication pattern & requirement?"}
    Start -->|"Direct Peer-to-Peer / Audio-Video"| WebRTC["<b>WebRTC</b><br>• Sub-second ultra-low latency UDP<br>• P2P audio, video & data streams"]
    Start -->|"Server-to-Client Unidirectional Push"| SSE["<b>Server-Sent Events (SSE)</b><br>• Lightweight HTTP text streaming<br>• Stock tickers, live notifications"]
    Start -->|"Bidirectional Low-Latency Exchange"| WS["<b>WebSockets (WSS)</b><br>• Full-duplex persistent TCP socket<br>• Realtime chat, multiplayer gaming"]
    Start -->|"Periodic Updates / Simple HTTP Model"| Polling{"Update frequency & latency tolerance?"}
    Polling -->|"Infrequent updates / Stale data acceptable"| SP["<b>Short Polling</b><br>• Fixed-interval HTTP requests<br>• Low server load, simple caching"]
    Polling -->|"Near realtime without WebSocket infra"| LP["<b>Long Polling</b><br>• Server holds HTTP open until event<br>• Fallback for legacy environments"]

```
---
## ✔️hop-2. Server-Side Push/pull
- https://www.hellointerview.com/learn/system-design/patterns/realtime-updates#server-side-push-pull | check this
- how we propagate updates from the **source to the server**.
- we need a **trigger**:
  - Pulling ( via Polling )
  - Pushing ( via Consistent Hashes) 
  - Pushing ( via Pub/Sub )

![img.png](draw/img2.png)

### 1. Pulling ( via Polling )
- notice arrow sign below, its pull

```mermaid
flowchart LR
    Client["<b>Client</b>"]
    Server["<b>Server</b>"]
    DB[("<b>DB</b>")]
    UpdateSrc{{"<b>Update<br>Source</b>"}}

    Client -->|"<b>Poll repeatedly</b><br>for updates"| Server
    Server -->|"Read query"| DB
    UpdateSrc -->|"<b>Write updates to DB</b><br>as they happen"| DB

```

### 2. Pushing
- the client(say `user A`) has a **persistent connection (ws,sse,long-poll)** to one server 
- and that server is responsible for sending updates to the client.
- from update server, when a message needs to be sent: 
    - Figure out which server, "User A" is connected to. ⭐
    - Send the message to that server. how ? message-Broker/gRPC ?
    - That server will look up which (websocket, SSE, long-polling) request is associated with "User A".
    - The server will then write the message **via the appropriate connection.** 

#### 2.1. Pushing (Simple Hashing)
>  we'll have a **central Zookeeper / Etcd service** with metadata
> - there are N server, can assign them each a number 0 through N-1.
> - allows the servers to keep in sync as it updates.

```
serverIndex = hash(userId) % N | N = 2

    User A → hash(A) % 2 → 0 → Server 1
    User B → hash(B) % 2 → 1 → Server 2
    User C → hash(C) % 2 → 0 → Server 1
```

```
When a client connects, the following happens:
- The client connects to a random server.
- The server uses Zookeeper's server list to compute which server is responsible 
  for the client (by hashing their ID and applying modulo N).
- The server redirects the client to the appropriate server.
- The client connects to the correct server.
- The server adds that client to a map of connections.

update server:
- has update for A
- uses Zookeeper's server list to compute which server is responsible user A
- publish reponse to realtime server
- That server will look up which (websocket, SSE, long-polling) request is associated with "User A".
- The server will then write the message via the appropriate connection.
```

```mermaid
flowchart TD
    %% ZooKeeper / Coordination Layer
    ZK[("<b>ZooKeeper / etcd</b><br>• Cluster Membership & Server Count (N=2)<br>• Node Registry: {Server 0, Server 1}")]

    %% Update Pipeline
    subgraph UpdatePlane ["Update Path"]
        UpdateSvc["<b>Update Server</b><br>• Has update for User A"]
    end

    %% Application Server Layer
    subgraph Cluster ["WebSocket Server Cluster (N=2)"]
        WS0["<b>WebSocket Server 1 (ID = 0)</b><br>• In-memory map: [User A → WS#123, User C → WS#124]"]
        WS1["<b>WebSocket Server 2 (ID = 1)</b><br>• In-memory map: [User B → WS#456]"]
    end

    %% Client Layer
    subgraph Clients ["Connected Clients"]
        UserA["<b>User A</b>"]
        UserB["<b>User B</b>"]
        UserC["<b>User C</b>"]
    end

    %% Topology & Configuration sync
    ZK -.->|"Server Count N & Addresses"| UpdateSvc
    ZK -.->|"Cluster Heartbeats & IDs"| WS0
    ZK -.->|"Cluster Heartbeats & IDs"| WS1

    %% Connection mapping (hash(id) % 2)
    UserA --> WS0
    UserC --> WS0
    UserB --> WS1

    %% Update Delivery
    UpdateSvc -->|"1. Compute: hash(A) % 2 = 0<br>2. Route directly to Server 1"| WS0
    WS0 -->|"3. Push to active socket (WS#123)"| UserA

```
---
#### 2.2. Pushing (Consistent Hashing)

```
serverIndex = hash(userId) % R | R,ring = 360

    User A → hash(A) % 360 → move clockwise --> 0 → Server 1
    User B → hash(B) % 360 → move clockwise --> 1 → Server 2
    User C → hash(C) % 360 → move clockwise --> 0 → Server 1

```
- The hashing approach works great when N is fixed, but becomes problematic when we need to scale our service up or down
- It maps both servers and users onto a hash ring,
- and each user connects to the next server they encounter when moving clockwise around the ring.
- use, when system needs to scale dynamically.

---
#### 2.3 Pushing :: via Pub/Sub

```mermaid
flowchart LR
    UpdateSrc{{"<b>Update Source</b><br>for Client A"}}
    PubSub[("<b>Pub/Sub Broker</b><br>(Redis / SNS)")]
    Server1["<b>Server 1</b><br>• Holds Client A Socket"]
    Server2["<b>Server 2</b><br>• Idle / Other Clients"]
    ClientA["<b>Client A</b>"]

    UpdateSrc -->|"1. Publish event<br>(channel: client_a)"| PubSub
    PubSub -->|"2. Broadcast / Fan-out"| Server1
    PubSub -->|"2. Broadcast / Fan-out"| Server2

    Server1 -->|"3. Push (Active Socket)"| ClientA
    Server2 -.->|"No active socket<br>(Ignores event)"| ClientA

```

```mermaid
sequenceDiagram
    autonumber
    actor UserA as User A
    participant EP1 as Endpoint Server 1
    participant Redis as Pub/Sub (Redis)
    participant Update as Update Server

    rect 
    Note over UserA,Redis: Phase 1: Connection & Subscription Setup
    UserA->>EP1: 1. Create connection (WSS / SSE)
    activate EP1
    EP1->>Redis: 2. Register subscription to topic (e.g., SUBSCRIBE topic_1)
    activate Redis
    deactivate Redis
    deactivate EP1
    end

    rect 
    Note over Update,UserA: Phase 2: Event Ingestion & Message Broadcast
    Update->>Redis: 1. Publish update to topic (e.g., PUBLISH topic_1 "data")
    activate Redis
    Redis-->>EP1: 2. Subscribed servers receive the message
    deactivate Redis
    activate EP1
    EP1-->>UserA: 3. Message is passed through existing connection
    deactivate EP1
    end
```
- Q1: 
  - When consuming messages from a shared Kafka topic/partition,
  - what do we do with messages intended for **clients that are offline** 
  - or do not currently have an active socket connection?
- Q2:
  - If 50,000 users share 250 partitions (shards) in a single Kafka topic
  - and updates arrive simultaneously, how is m**essage ordering preserved for each user?**
- q3: if we create **1 topic per user**, how do we handle millions of users without overloading the broker?

---
## Summary 💡
> [realtime-update](draw/01_realtime-update.excalidraw) | [read-link](https://excalidraw.com/#json=tp6lsIiuKf6HzfHl6WwoW,YpgunMqS_OrMo51UXEz1pQ)
![img.png](draw/img.png)

> [realtime-update-problems](draw/01_02_realtime-update-problems.excalidraw)  | [read-link](https://excalidraw.com/#json=r0O3ZBV9zOMqGjw5BqWSi,CNtqqb5pP3ErlnjTKKaQrA)
![img.png](draw/img1.png)

---
## Common Deep Dives ⭐
### 1. handle connection failures and reconnection
- Real-world networks are unreliable: Wi-Fi/mobile connections drop, clients lose connectivity, and servers can restart.

| **Problem**                               | **Solution**                                                            |
| ----------------------------------------- | ----------------------------------------------------------------------- |
| Detect a dead/zombie connection           | **Heartbeat / ping-pong** mechanism                                     |
| Client reconnects after disconnect        | **Automatic reconnection with backoff**                                 |
| Avoid overwhelming the server             | **Exponential backoff + jitter**                                        |
| Client missed messages while disconnected | **Sequence numbers / offsets**                                          |
| Resume from where client stopped          | Client sends **last received sequence number**                          |
| Prevent data loss                         | Server retains messages/events for a **replay window**                  |
| Multiple server instances                 | Use **shared durable state / message broker** rather than server memory |
| Duplicate messages after reconnect        | Use **message IDs / sequence numbers + idempotent processing**          |


```
Client                         Server
  │                               │
  │──── WebSocket Connect ───────►│
  │                               │
  │◄──── Event #101 ──────────────│
  │◄──── Event #102 ──────────────│
  │                               │
  │──── Ping ────────────────────►│
  │◄──── Pong ────────────────────│
  │                               │
  X──── Connection Lost ──────────X
  │
  │     reconnect with backoff
  │
  │──── Reconnect ────────────────►│
  │    ⭐last_received = 102      │
  │                               │
  │          replay event         │
  │◄──── Event #103 ──────────────│
  │◄──── Event #104 ──────────────│
  │◄──── Event #105 ──────────────│
```

---
### 2. A single user has millions of followers who all need the same update
"celebrity problem" 
- Instead of writing the update to millions of individual user feeds, 
- cache the update once and distribute through multiple layers and fanout pattern
- Regional servers can pull the update and push to their local clients, reducing the load on any single component.

**Batching and Hierarchical Aggregation (write scale pattern)**

```mermaid
flowchart LR
    %% Clients
    subgraph Senders ["User Actions"]
        UserA["User A"]
        UserB["User B"]
        UserC["User C"]
        UserD["User D"]
        Dots["..."]
    end

    %% Ingestion / Write Layer
    subgraph IngestionLayer ["Write Processors"]
        WP1["Write<br>Processor 1"]
        WP2["Write<br>Processor 2"]
        WP3["Write<br>Processor 3"]
    end

    %% Aggregation Layer
    subgraph AggregationLayer ["Root Aggregator"]
        Root["<b>Root<br>Processor</b>"]
    end

    %% Broadcast Layer
    subgraph BroadcastLayer ["Broadcast Nodes"]
        BN1["Broadcast<br>Node 1"]
        BN2["Broadcast<br>Node 2"]
    end

    %% Target Receivers
    subgraph Receivers ["Subscribed Clients"]
        RecA["User A"]
        RecB["User B"]
        RecC["User C"]
        RecD["User D"]
        RecDots["..."]
    end

    %% Action Connections
    UserA -->|"Like"| WP1
    UserB -->|"Comment"| WP1
    UserC -->|"Like"| WP1
    UserD -->|"Comment"| WP3

    %% Aggregation Flow
    WP1 --> Root
    WP2 --> Root
    WP3 --> Root

    %% Distribution & Delivery
    Root --> BN1
    Root --> BN2

    BN1 --> RecA
    BN1 --> RecB
    BN2 --> RecC
    BN2 --> RecD

```
### 3. maintain message ordering across distributed servers
- Each server maintains its own vector clock, 
- and messages **include timestamp information** 
- that helps recipients determine the correct order.

