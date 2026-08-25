# Synchronous :: request/response style



--- 
## HTTP based

```mermaid
flowchart TD
%% Main Synchronous Root
    SYNC["<b>🔄 Synchronous Communication</b><br>Request / Response Pattern"]

%% Middle Tier Protocols with Context
    subgraph Protocols ["API Paradigms (Layer 7)"]
        direction TD
        HTTP["<b>🌐 REST (HTTP/HTTPS)</b><br>• Resource-oriented (CRUD)<br>• JSON payloads & status codes<br>• Public & web-facing APIs"]
        RPC["<b>⚡ RPC / gRPC (Unary)</b><br>• Action-oriented & Protobuf<br>• Strict schemas & type safety<br>• Internal service-to-service"]
        GQL["<b>🧩 GraphQL</b><br>• Single endpoint & flexible query<br>• Client specifies exact fields<br>• Eliminates over/under-fetching"]
    end

%% Network / Transport Layer
    subgraph Transport ["Transport Layer (Layer 4 & Security)"]
        direction LR
        TCP1["<b>🔒 TLS + TCP/IP</b><br>Reliable, ordered stream & byte-delivery"]
    end

%% Flow Connections
    SYNC --> HTTP
    SYNC --> RPC
    SYNC --> GQL

    HTTP -->|"HTTP/1.1 or HTTP/2"| TCP1
    RPC -->|"HTTP/2 Multiplexing"| TCP1
    GQL -->|"HTTP POST over TCP"| TCP1

```

---
### 1. REST
- [01_rest_01_overview.md](../../SD_08_API-Design/02_protocol/01_rest_01_overview.md)

---
### 2. RPC / GRPC
- [02_grpc_01_overview.md](../../SD_08_API-Design/02_protocol/02_grpc_01_overview.md)

---
### 3. graphQL
- [03_graphQL_01_overview.md](../../SD_08_API-Design/02_protocol/03_graphQL_01_overview.md)

---
## More links
- [SD_08_API-Design](../../SD_08_API-Design)
- [SD_21_microservice](../../SD_21_microservice)
- [02_pattern_10_service-mesh.md](../04_architecture/02_pattern_10_service-mesh.md)
