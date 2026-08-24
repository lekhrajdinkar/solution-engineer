# Real-time Updates / push notifications
- https://www.hellointerview.com/learn/system-design/patterns/realtime-updates
- [network-essential](../SD_04_network-essential)
- https://www.youtube.com/watch?v=EX5uZV3Tzss
> when you need servers to **proactively push updates** to clients.

## The Problem
- in collaborative document editor like `Google Docs`, see that change within `milliseconds`.
- `request-response model`: clients ask for data --> servers respond --> then **connection closes**
- so, core challenge is **establishing efficient, persistent communication channels** between clients and servers

## More use-cases
```
Ticketmaster
Uber
WhatsApp
Robinhood
Google Docs
Strava
Online Auction
FB Live Comments
Online Chess
ChatGPT
```
---
## The Solution
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
### hop-1. Client-Server Connection Protocols
> need persistent connections or clever polling strategies

```
Simple Polling: The Baseline
Long Polling: The Easy Solution
---
Server-Sent Events (SSE): The Efficient One-Way Street
Websockets: The Full-Duplex Champion
WebRTC: The Peer-to-Peer Solution
```
- [01_02_polling.md](../SD_01_Foundation/05_IPC/01_02_polling.md)
- [03_streaming-TCP-based.md](../SD_01_Foundation/05_IPC/02_streaming-TCP-based.md)
- [04_video-streaming.md](../SD_01_Foundation/05_IPC/04_video-streaming.md)


**Infra in between client and server must support persistent connection like wss and sse**
- L7 load balancers aren't guaranteeing
- L4 load balancers will support websockets natively, **since the same TCP connection is used for each request.**

```mermaid
flowchart TB
    subgraph ClientLayer ["Client Side"]
        Client["<b>Client / Browser</b><br>• WSS / WebSocket Client<br>• EventSource (SSE)"]
    end

    subgraph EdgeLayer ["Edge & Ingress Infrastructure"]
    direction LR
        CDN["<b>CDN / Edge Proxy</b><br>• Bypass caching for /ws & /sse<br>• TLS Termination"]
        LB["<b>Layer 4 / Layer 7 Load Balancer</b><br>• <i>Least Connections</i> Algorithm<br>• WebSocket Upgrade Support<br>• Extended Idle Timeout (Keep-Alive)"]
        Gateway["<b>API Gateway / Reverse Proxy</b><br><i>(NGINX / Envoy / HAProxy)</i><br>• Disable Response Buffering (SSE)<br>• HTTP/1.1 Upgrade: websocket<br>• Persistent Connection Tracking"]
    end

    subgraph ServerLayer ["Backend Infrastructure"]
        App1["<b>Realtime Server 1</b><br>• Open TCP/Socket Handles<br>• epoll / kqueue Event Loop"]
        App2["<b>Realtime Server 2</b><br>• Open TCP/Socket Handles<br>• epoll / kqueue Event Loop"]
        RedisPubSub[("<b>Pub/Sub Broker</b><br>(Redis / Kafka)<br>• Cross-server message broadcast")]
    end

    %% Flow connections
    Client -->|"1. TLS Handshake / Initial HTTP"| CDN
    CDN -->|"2. Forward connection"| LB
    LB -->|"3. Route via Least Connections"| Gateway
    Gateway -->|"4. Persistent TCP Stream (WSS / SSE)"| App1
    Gateway -->|"4. Persistent TCP Stream (WSS / SSE)"| App2

    App1 <--> RedisPubSub
    App2 <--> RedisPubSub

    %% Styling
    style ClientLayer fill:#121212,stroke:#555555,stroke-dasharray: 5 5,color:#ffffff
    style EdgeLayer fill:#121212,stroke:#89b4fa,stroke-dasharray: 5 5,color:#ffffff
    style ServerLayer fill:#121212,stroke:#a6e3a1,stroke-dasharray: 5 5,color:#ffffff

    style Client fill:#1e1e2e,stroke:#ffffff,stroke-width:1.5px,color:#ffffff
    style CDN fill:#1e1e2e,stroke:#89b4fa,stroke-width:1.5px,color:#ffffff
    style LB fill:#1e1e2e,stroke:#89b4fa,stroke-width:1.5px,color:#ffffff
    style Gateway fill:#1e1e2e,stroke:#89b4fa,stroke-width:1.5px,color:#ffffff
    style App1 fill:#1e1e2e,stroke:#a6e3a1,stroke-width:1.5px,color:#ffffff
    style App2 fill:#1e1e2e,stroke:#a6e3a1,stroke-width:1.5px,color:#ffffff
    style RedisPubSub fill:#313244,stroke:#fab387,stroke-width:1.5px,color:#ffffff
```

---
### hop-2. Server-Side Push/pull