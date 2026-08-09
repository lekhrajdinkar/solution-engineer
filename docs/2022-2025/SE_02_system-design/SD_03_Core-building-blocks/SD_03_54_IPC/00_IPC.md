# Inter process Communication
- https://youtu.be/AMNWLz_f6qM?si=T076QSntCR53atIb | bm
---
## Overview
| Communication    | One-to-One                                            | One-to-Many                   | Examples                          |
| ---------------- | ----------------------------------------------------- | ----------------------------- | --------------------------------- |
| **Synchronous**  | ✅ Request → Response                                  | ❌ Rare                        | REST, gRPC                        |
| **Asynchronous** | ✅ Queue, one-way notification, async request/response | ✅ Pub/Sub, Event Bus, Fan-out | Kafka, RabbitMQ, SNS, EventBridge |

IPC format:
- text based: `JSON`, `XML`
- binary: `Protobuf`, `avro`

---
## pattern
- [01_synchronous.md](01_synchronous.md)
- [02_asynchronous.md](02_asynchronous.md)
- [03_streaming.md](03_streaming.md)
- [04_batch_file_based.md](04_batch_file_based.md)
- [05_more.md](05_more.md)
