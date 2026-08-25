# IPC| Inter process Communication
- https://youtu.be/AMNWLz_f6qM?si=T076QSntCR53atIb | bm
- [01_01_request-response.md](01_01_request-response.md)
- [01_02_polling.md](01_02_polling.md)
- [02_01_streaming-TCP-based.md](02_01_streaming-sse.md)
- [02_02_streaming-wss.md](02_02_streaming-wss.md)
- [02_03_streaming-webRTC.md](02_03_streaming-webRTC.md)
- [02_04_Streaming-ABS.md](02_04_Streaming-ABS.md) 🔺
- [02_05_streaming-gRPC-based.md](02_05_streaming-gRPC-based.md) 🔺
- [03_01_event-driven.md](03_01_event-driven.md)
- [03_02_message-driven.md](03_02_message-driven.md)
- [05_batch_file_based.md](05_batch_file_based.md)

---
## IPC format:
- text based: `JSON`, `XML`
- binary: `Protobuf`, `avro`

## technologies and transport

| Communication   | Common technology | Typical transport |
| --------------- | ----------------- | ----------------- |
| REST API        | HTTP              | TCP               |
| gRPC            | HTTP/2            | TCP               |
| WebSocket       | WebSocket         | TCP               |
| Kafka           | Kafka protocol    | TCP               |
| RabbitMQ        | AMQP              | TCP               |
| SSE             | HTTP              | TCP               |
| WebRTC          | RTP/UDP           | `UDP`               |
| QUIC / HTTP/3   | QUIC              | `UDP`               |
| Video streaming | HLS/DASH          | Usually TCP       |
| Real-time video | WebRTC/RTP        | Usually `UDP`       |


---
## Overview

```
IPC (Inter-Process Communication)
│
├── 1. Synchronous (Request / Response)
│      ├── REST / HTTP (JSON / XML)
│      ├── gRPC (Protobuf / HTTP/2)
│      ├── GraphQL
│      ├── Polling
│           ├── short Polling
│           ├── Long Polling
│
├── 2. Asynchronous (Decoupled)
│   ├── Point-to-Point / Queue-based (e.g., AWS SQS, RabbitMQ)
│   ├── Pub/Sub (e.g., AWS SNS, Google Cloud Pub/Sub)
│   ├── Event Streaming (e.g., Apache Kafka, AWS Kinesis)
│   ├── Webhooks / HTTP Callbacks (e.g., Stripe, GitHub)
│   └── Architecture Patterns (Enabled by Async/Events)
│       ├── Fan-Out Pattern
│       ├── Event Sourcing
│       └── CQRS (Command Query Responsibility Segregation)
│
├── 3. Streaming Protocols
│   ├── TCP-based
│   │   ├── WebSockets (Bidirectional full-duplex)
│   │   ├── Server-Sent Events (SSE - Unidirectional push)
│   │   └── gRPC Streaming
│   └── UDP-based
│       ├── QUIC / HTTP/3
│       └── WebRTC / RTP (Real-time audio/video)
│
├── 4. Batch & Storage-Mediated
│   ├── Object Storage (AWS S3, GCS)
│   ├── File Transfer (SFTP, NFS, SMB)
│   └── Database CDC Pipelines (Change Data Capture)

```

---
## ✔️Synchronous (Request/Response)

```mermaid
flowchart TD
%% Main Synchronous Root
    SYNC["🔄 Synchronous Communication :: \n Request-Response style"] --> HTTP["REST"]
    SYNC --> RPC["gRPC "]
    SYNC -->GQL["GraphQL"]
    SYNC --> POLL[Polling]
    POLL --> POLLL[Long Polling]
    POLL --> POLLS[Short Polling]
    style POLLL fill:yellow,color:black
    style POLLS fill:yellow,color:black

```
---
## ✔️Streaming :: Text

```mermaid
flowchart TB
    %% Streaming
    STREAM["text streaming protocol \n TCP based"] --> SERVER[Server-to-Client Streaming]
    STREAM --> CLIENT[Client-to-Server Streaming]
    STREAM --> BI[Bidirectional Streaming]
    SERVER --> SSE[SSE]
    SERVER --> GRPCS[gRPC Server Streaming]
    CLIENT --> GRPCC[gRPC Client Streaming]
    BI --> WS[WebSocket / WSS]
    BI --> GRPCBI[gRPC Bidirectional Streaming]
    style SSE fill:yellow,color:black
    style WS fill:yellow,color:black
```




## ✔️Streaming :: Audio/Video 

```mermaid
flowchart TD
    AV["<b>Audio / Video Streaming Protocols</b>"]
    AV --> UDP["<b>UDP-Based (Low-Latency & Real-Time)</b>"]
    AV --> TCP["<b>TCP-Based (Adaptive Bitrate Streaming - ABS)</b>"]
    UDP --> W_RTC["<b>WebRTC</b><br>• Sub-second latency P2P / SFU<br>• Video calling, conferencing"]
    UDP --> RTP["<b>RTP / RTCP / SRT</b><br>• Live video contribution & ingest<br>• Packet loss recovery without TCP stalls"]
    TCP --> HLS["<b>HLS (HTTP Live Streaming)</b><br>• .m3u8 playlists + .ts/.fmp4 segments<br>• Standard CDN-cacheable video delivery"]
    TCP --> DASH["<b>MPEG-DASH</b><br>• .mpd manifest + adaptive bitrate<br>• Open industry standard over HTTP"]
    TCP --> RTMP["<b>RTMP (TCP-based Ingest)</b><br>• Legacy protocol for streamer-to-platform ingest"]
    style W_RTC fill:yellow,color:black

```

---
## ✔️Asynchronous (Decoupled)

```mermaid
flowchart TB
    ASYNC["<b>2. Asynchronous (Decoupled)</b>"]
    ASYNC --> P2P["<b>Point-to-Point / Queue-Based</b><br>• Single consumer per message<br>• Task/worker distribution<br>• <i>AWS SQS, RabbitMQ</i>"]
    ASYNC --> PUBSUB["<b>Publish / Subscribe</b><br>• 1-to-Many broadcast / push<br>• Decoupled consumers<br>• <i>AWS SNS, Google Pub/Sub</i>"]
    ASYNC --> STREAM["<b>Event Streaming</b><br>• Append-only ordered distributed log<br>• Replayable consumer groups<br>• <i>Apache Kafka, AWS Kinesis</i>"]
    ASYNC --> HOOKS["<b>Webhooks / Callbacks</b><br>• Cross-service HTTP push notifications<br>• <i>Stripe, GitHub</i>"]
```


```mermaid
flowchart TB
    PATTERNS["<b>Architecture Patterns</b><br>(Enabled by Async / Events)"]
    PATTERNS --> FAN["<b>Fan-Out Pattern</b><br>Topic pushes to multiple parallel queues/workers"]
    PATTERNS --> EVENTSRC["<b>Event Sourcing</b><br>State stored as an immutable sequence of events"]
    PATTERNS --> CQRS["<b>CQRS</b><br>Segregates Command (Write) and Query (Read) models"]
    style PATTERNS fill:cyan,color:black
```



