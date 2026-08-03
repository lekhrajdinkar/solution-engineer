#  Synchronous: Request/Response

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
## 1. TCP/IP (Transmission Control Protocol)
**TCP handshake:**
- Creates a reliable, stateful connection between two endpoints.
- Connection starts with **3-way handshake**: SYN → SYN-ACK → ACK
- Identified by: Source IP + Source Port + Destination IP + Destination Port
- **Provides ordering, acknowledgments, retransmission, flow control, and congestion control**
- TCP itself does not encrypt data; TLS provides encryption.

```
    TCP = reliable pipe
    TLS = secure pipe
    HTTP = language spoken through the pipe
```
```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server
    Note over C,S: ⭐TCP 3-Way Handshake
    C->>S: SYN
    S->>C: SYN-ACK
    C->>S: ACK
    Note over C,S: 🏃‍➡️🏃‍♂️TCP Connection ESTABLISHED
    C->>S: Data (SEQ)
    S->>C: ACK
    S->>C: Data (SEQ)
    C->>S: ACK
    Note over C,S: ❌Connection Termination
    C->>S: FIN
    S->>C: ACK
    S->>C: FIN
    C->>S: ACK
```
---
## 2. HTTP / HTTPS(TLS)
A stateless, text-based protocol commonly used for APIs, built on top of TCP
- HTTP connection : HTTP --> TCP handshake
- HTTPS connection : HTTP --> TCP handshake --> [TLS handshake](../../SD_24_security/03_protocol_https_tls.md)
  - Also **handshake/s takes time.**
- **short live stateless connection.** : open-close, open-close, ...
- use case : RESTful-API, web pages

---
## 3. RPC / GRPC...
- **Description**: A high-performance, open-source RPC framework by Google.
- **Key Features**:
    - Uses Protocol Buffers (Protobuf) for serialization.
    - Supports bi-directional streaming.
    - Highly efficient binary format.
- **Common Use Cases**:
    - Low-latency communication in microservices.
    - Distributed systems needing real-time communication.
- **Supported by**: Google Cloud, gRPC libraries.
