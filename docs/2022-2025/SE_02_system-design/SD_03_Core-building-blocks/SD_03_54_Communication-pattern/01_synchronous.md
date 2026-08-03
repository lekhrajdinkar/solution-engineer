# A. Synchronous: Request/Response

```mermaid
flowchart TB
    %% Synchronous
    SYNC[1. Synchronous Communication] --> RR[Request / Response]
    RR --> HTTP[HTTP / HTTPS REST]
    RR --> RPC[RPC / gRPC Unary]
    HTTP --> TCP1[TLS + TCP/IP]
    RPC --> TCP1
```

```mermaid
flowchart TB
    A[Synchronous Communication]
    A --> R[1. Request / Response]
    A --> U[2. Updates / Events]
    A --> ST[3. Bidirectional Streaming]

    R --> HTTP[HTTP / HTTPS]
    R --> RPC[RPC / gRPC]
    HTTP --> TCP1[TLS + TCP/IP]
    RPC --> TCP1

    U --> POLL[Polling<br/>Client Pull]
    U --> SSE[SSE<br/>Server Push]
    U --> FAN[Fan-out<br/>1 to Many]
    ST --> WS[WebSocket / WSS]
    WS --> BI[Persistent<br/>Bidirectional Connection]
    
```

---
## 1. HTTP / HTTPS(TLS)
A stateless, text-based protocol commonly used for APIs.
- HTTP connection : HTTP protocol --> TCP handshake
- HTTPS connection : [HTTP --> TCP handshake --> TLS handshake](../SD_24_security/03_protocol_https_tls.md)
- **short live stateless connection.** : open-close, open-close, ...
- Also **handshake takes time.**
- use case - REST API

---
## 2. TCP/IP (Transmission Control Protocol) 
> **PASSIVE SERVER**, reply only if client requests

TCP handshake:
- Creates a reliable, stateful connection between two endpoints.
- Connection starts with 3-way handshake: SYN → SYN-ACK → ACK
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
    Note over C,S: TCP 3-Way Handshake
    C->>S: SYN
    S->>C: SYN-ACK
    C->>S: ACK
    Note over C,S: TCP Connection ESTABLISHED
    C->>S: Data (SEQ)
    S->>C: ACK
    S->>C: Data (SEQ)
    C->>S: ACK
    Note over C,S: Connection Termination
    C->>S: FIN
    S->>C: ACK
    S->>C: FIN
    C->>S: ACK
```

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
