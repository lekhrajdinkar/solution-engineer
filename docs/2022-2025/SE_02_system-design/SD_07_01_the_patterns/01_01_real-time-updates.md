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
- [01_01_request-response.md](../SD_01_Foundation/05_IPC/01_01_request-response.md) ❌
- [01_02_polling.md](../SD_01_Foundation/05_IPC/01_02_polling.md) | `short and long`
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
### hop-2. Server-Side Push/pull