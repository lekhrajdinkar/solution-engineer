# API protocol
- https://www.youtube.com/watch?v=oyYnRVQvxv4

---
## Overview
![img.png](../../../99_img/2025/api-protocol.png)
## A. sync request-response
### 0. SOAP ⚠️
> - Old and heavier legacy protocol
> - Still prevalent in industries requiring high security
> and transactional integrity, such as banking , airline reservation systems

### 1. REST (Representational State Transfer)*
- [REST Done right.md](04_api_design_02_rest-best-principles.md#a-rest-done-right)
- It's simple and widely understood 
- stable, supported everywhere.
- but can sometimes lead to **excessive data transfer**
- or require **multiple requests** to gather necessary information 

### 2. GraphQL 
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2160312223/posts/2198424021
- Developed by Facebook,
- GraphQL offers **flexible data fetching**, 
- allowing clients to request exactly what they need in a single query 
- This makes it efficient for mobile apps and scenarios with limited bandwidth 
- runs on top of HTTP


### 3. gRPC / RPC (Google Remote Procedure Call)
- Built on HTTP/2
- using Protocol Buffers (compact binary format) | lighter than json
- gRPC is designed for fast, low-latency communication between microservices
- streaming capabilities:
    - stream of request
    - stream of response
  
---
## B. live Streaming

### 1. WebSocket & 
- crucial for real-time communication.
- WebSockets 
  - full duplex
  
### 2. SSE (Server-Sent Events)
  - provides one-way server-to-client streaming, 
  - efficient for continuous feeds from server
  - eg: live comments on YouTube 
  - eg: stock tickers

### 3. GRPC streaming

---
## C. Async messaging
### 1. AMQP (Advanced Message Queuing Protocol)
-  messaging protocols for asynchronous communication.
-  decouples services
- allowing messages to be reliably delivered 
  - even if a service is temporarily offline

### 2. MQTT (Message Queuing Telemetry Transport)
-  **lightweight** publish-subscribe protocol 
- ideal for tiny **IoT devices** like smart homes and connected cars, chatting with each others