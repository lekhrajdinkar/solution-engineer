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

---
### hop-2. Server-Side Push/pull