#  Inter process Communication : Synchronous
> [⭐ microservices :: service-mesh](../04_architecture/02_pattern_10_service-mesh.md)

---
## Inter process Communication
- https://youtu.be/AMNWLz_f6qM?si=T076QSntCR53atIb | bm

IPC format:
- text based: `JSON`, `XML`
- binary: `Protobuf`, `avro`

```mermaid
flowchart TB
    A[Application Communication Patterns]

    A --> SYNC[1. Synchronous Communication]
    A --> ASYNC[2. Asynchronous Communication]
    A --> STREAM[3. Streaming Communication]
    A --> BATCH[4. Batch / File-Based Communication]
```
| Communication    | One-to-One                                            | One-to-Many                   | Examples                          |
| ---------------- | ----------------------------------------------------- | ----------------------------- | --------------------------------- |
| **Synchronous**  | ✅ Request → Response                                  | ❌ Rare                        | REST, gRPC                        |
| **Asynchronous** | ✅ Queue, one-way notification, async request/response | ✅ Pub/Sub, Event Bus, Fan-out | Kafka, RabbitMQ, SNS, EventBridge |

| Category     |         Caller waits? | Connection                           | Common examples                  |
| ------------ | --------------------: | ------------------------------------ | -------------------------------- |
| Synchronous  |                   Yes | Usually short-lived                  | REST, HTTP, unary gRPC           |
| Asynchronous |                    No | Decoupled through broker or callback | Kafka, RabbitMQ, SQS, webhook    |
| Streaming    |            Continuous | Long-lived                           | WebSocket, SSE, gRPC streaming   |
| Batch        | No real-time response | Periodic or file-based               | SFTP, S3 files, Spark, MapReduce |

> layer 7 network protocol:

### [A. Synchronous: Request/Response](/01_synchronous.md)
- http  / tcp handshake
- https / tls handshake
- grpc (http2.0)
- graphQL (http)

### [B. Asynchronous Communication](/02_asynchronous.md)
- event based - fanOut, webhook, event sourcing/CQRS
- message based - p2p, pubSub
- polling - short / long

### [C. Streaming](03_streaming-TCP-based.md)
- ws / wss
- gRPC stream

### [D. Batch/File-based](/04_batch_file_based.md)
- FTP
- object: AWS S3
- ...

---
### [More](/05_more.md)
- webRTC
- Video stream

## Synchronous: Request/Response

```mermaid
flowchart TB
    %% Synchronous
    SYNC[1. Synchronous Communication] --> RR[Request / Response]
    RR --> HTTP[HTTP / HTTPS REST]
    RR --> RPC[RPC / gRPC Unary]
    HTTP --> TCP1[TLS + TCP/IP]
    RPC --> TCP1
```

---
## 1. REST
[API-Design](../../SD_08_API-Design)
- [best-principles](../../SD_08_API-Design/02_protocol/01_rest_01_best-principles.md)
- [useful headers](../../SD_08_API-Design/02_protocol/01_rest_02_http-headers.md)

[microservice :: complete guide](../../SD_21_microservice)

**More**

- [TCP Overview](../06_network/02_core_01_OSI-layers.md#tcp-reliable-delivery)
- [TLS](../../SD_24_security/03_protocol_https_tls.md)
- [HTTP Overview](../06_network/02_core_01_OSI-layers.md#https)
- [http-headers](../../SD_08_API-Design/02_protocol/01_rest_02_http-headers.md)
- [http-evolution](../../SD_08_API-Design/02_protocol/01_rest_03_http-evolution.md)

---
## 2. RPC / GRPC
- [overview](../../SD_08_API-Design/02_protocol/02_grpc_01_overview.md)

---
## 3. graphQL
- [overview](../../SD_08_API-Design/02_protocol/02_grpc_01_overview.md)

