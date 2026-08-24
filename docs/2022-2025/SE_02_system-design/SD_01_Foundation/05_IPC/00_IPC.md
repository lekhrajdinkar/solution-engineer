# IPC| Inter process Communication
- https://youtu.be/AMNWLz_f6qM?si=T076QSntCR53atIb | bm
- [01_01_request-response.md](01_01_request-response.md)
- [01_02_polling.md](01_02_polling.md)
- [02_01_event-driven.md](02_01_event-driven.md)
- [02_02_message-driven.md](02_02_message-driven.md)
- [03_streaming-TCP-based.md](03_streaming-TCP-based.md)
- [04_video-streaming.md](04_video-streaming.md)
- [05_batch_file_based.md](05_batch_file_based.md)

---
## IPC format:
- text based: `JSON`, `XML`
- binary: `Protobuf`, `avro`

---
## Overview

```mermaid
flowchart TB
    A[Application Communication Patterns]

    A --> SYNC[1. Synchronous ]
    A --> ASYNC[2. Asynchronous ]
    A --> STREAM[3. Streaming ]
    A --> VSTREAM[4. Video Streaming ]
    A --> BATCH[5. Batch / File-Based ]
```

| Category        |         Caller waits? | Connection                           | Common examples                  |
|-----------------| --------------------: | ------------------------------------ |----------------------------------|
| Synchronous     |                   Yes | Usually short-lived                  | REST, HTTP, unary gRPC , polling |
| Asynchronous    |                    No | Decoupled through broker or callback | Kafka, RabbitMQ, SQS, webhook    |
| Streaming       |            Continuous | Long-lived                           | WebSocket, SSE, gRPC streaming   |
| video Streaming |            Continuous | Long-lived                           | WebRTC                           |
| Batch           | No real-time response | Periodic or file-based               | SFTP, S3 files, Spark, MapReduce |

---
## A. Synchronous: Request/Response 
### HTTP-based

```mermaid
flowchart TD
%% Main Synchronous Root
    SYNC["🔄 Synchronous Communication :: \n Request-Response style"] --> HTTP["REST"]
    SYNC --> RPC["gRPC "]
    SYNC -->GQL["GraphQL"]

    SYNC --> POLL[Polling]
    POLL --> POLLL[Long Polling]
    POLL --> POLLS[Short Polling]

```
---
## B. Asynchronous Communication
### Event based 
- fanOut, 
- webhook, 
- event sourcing/CQRS

### Message based 
- point-2-point 
- pub/Sub

```mermaid
flowchart TB
    ASYNC[ Asynchronous Communication] --> MSG[Messaging]
    ASYNC --> EVENT[Event-Driven Communication]

    MSG --> QUEUE[Queue<br/>Point-to-Point]
    MSG --> PUBSUB[Publish / Subscribe<br/>One-to-Many]

    QUEUE --> MQ[RabbitMQ / SQS]
    PUBSUB --> KAFKA[Kafka / SNS / EventBridge]

    EVENT --> FAN[Fan-out]
    EVENT --> WEBHOOK[Webhook / Callback]
    EVENT --> RELATED[Related \nArchitecture \nPatterns]

    RELATED --> EVENTSRC[1. Event Sourcing]
    RELATED --> CQRS[2. CQRS]
    
    style MSG fill:yellow,color:black
    style EVENT fill:yellow,color:black
    style EVENTSRC fill:cyan,color:black
    style CQRS fill:cyan,color:black
```
---
## C. Streaming
### TCP based

```mermaid
flowchart TB
    %% Streaming
    STREAM --> SERVER[Server-to-Client Streaming]
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

## D. Video Streaming
### UDP based
- webRTC
- ABS

## Batch/File-based
- FTP
- object: AWS S3
- ...