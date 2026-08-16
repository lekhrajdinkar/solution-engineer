# Network Essential- part 1
## reference
- https://youtube.com/watch?v=tpgoQwMg__M | OSI bm
- https://youtu.be/SHkbPm1Wrno | hi

--- 
## Overview : OSI

```
Application   → HTTP,SMTP,TELNET/SSH, FTP,DNS request
Presentation  → TLS encryption, format (HTML,DOC,JPGG,etc)
Session       → communication/session management === SOCKET , RPC
Transport     → TCP or UDP "packet", source/destination ports
Network       → source/destination IP
Data Link     → source/destination MAC
Physical      → Wi-Fi/radio/cable bits, light pulses
```

| #     | Layer        | Main responsibility                                  | Common examples                 |
| ----- | ------------ | ---------------------------------------------------- |---------------------------------|
| **7** | Application  | Network services used by applications                | HTTP, HTTPS, DNS, SMTP, FTP     |
| **6** | Presentation | Data format, encoding, encryption, compression       | TLS/SSL, JSON, JPEG, UTF-8      |
| **5** | Session      | Establish/manage/close communication sessions        | RPC sessions, sockets concepts  |
| **4** | Transport    | End-to-end delivery, ports, reliability              | TCP /QUIC(Modern) , UDP                    |
| **3** | Network      | IP addressing and routing                            | IPv4, IPv6, ICMP, routers       |
| **2** | Data Link    | Local-network delivery using MAC addresses           | Ethernet, Wi-Fi, switches, ARP* |
| **1** | Physical     | Transmits raw bits as electrical/radio/light signals | Cable, fiber, radio             |


![img_2.png](img_2.png)


---
## TCP/IP vs OSI
```mermaid
%%{init: {'theme': 'base', 'themeVariables': { 'primaryColor': '#4682B4', 'edgeLabelBackground':'#ffffff', 'tertiaryColor': '#fff'}}}%%
graph TD


%% OSI Model Subgraph
    subgraph OSI_Model ["OSI Model"]
        direction TB
        OSI_L7(Application Layer)
        OSI_L6(Presentation Layer)
        OSI_L5(Session Layer)
        OSI_L4(Transport Layer)
        OSI_L3(Network Layer)
        OSI_L2(Data Link Layer)
        OSI_L1(Physical Layer)

    %% Node Styling for OSI
        style OSI_L7 fill:#367C9B,stroke:#367C9B,color:#fff,rx:5,ry:5
        style OSI_L6 fill:#367C9B,stroke:#367C9B,color:#fff,rx:5,ry:5
        style OSI_L5 fill:#367C9B,stroke:#367C9B,color:#fff,rx:5,ry:5
        style OSI_L4 fill:#76B5C5,stroke:#76B5C5,color:#1a1a1a,rx:5,ry:5
        style OSI_L3 fill:#46A5B4,stroke:#46A5B4,color:#1a1a1a,rx:5,ry:5
        style OSI_L2 fill:#76D7EA,stroke:#76D7EA,color:#1a1a1a,rx:5,ry:5
        style OSI_L1 fill:#76D7EA,stroke:#76D7EA,color:#1a1a1a,rx:5,ry:5

    %% Connections for OSI
        OSI_L7 --> OSI_L6
        OSI_L6 --> OSI_L5
        OSI_L5 --> OSI_L4
        OSI_L4 --> OSI_L3
        OSI_L3 --> OSI_L2
        OSI_L2 --> OSI_L1
    end

%% TCP/IP Model Subgraph
    subgraph TCP_IP_Model ["TCP/IP Model"]
        direction TB
        TCP_L4(Application Layer)
        TCP_L3(Transport Layer)
        TCP_L2(Internet Layer)
        TCP_L1(Network Access Layer)

    %% Node Styling for TCP/IP
        style TCP_L4 fill:#367C9B,stroke:#367C9B,color:#fff,rx:5,ry:5
        style TCP_L3 fill:#76B5C5,stroke:#76B5C5,color:#1a1a1a,rx:5,ry:5
        style TCP_L2 fill:#46A5B4,stroke:#46A5B4,color:#1a1a1a,rx:5,ry:5
        style TCP_L1 fill:#76D7EA,stroke:#76D7EA,color:#1a1a1a,rx:5,ry:5

    %% Connections for TCP/IP
        TCP_L4 --> TCP_L3
        TCP_L3 --> TCP_L2
        TCP_L2 --> TCP_L1
    end

%% Subgraph Styling
    style OSI_Model fill:#E0F2F7,stroke:#ADD8E6,stroke-width:2px
    style TCP_IP_Model fill:#E0F2F7,stroke:#ADD8E6,stroke-width:2px
```

---
## A. 📚Layer 3 : Network
### 1. IP
> IP by far the most common for system design interviews

- the protocol that handles routing and addressing.
- It's responsible for breaking the data into packets, handling packet forwarding between networks, and providing best-effort delivery to any destination IP address on the network. 
- Ipv4 (4.3 billion only)| Ipv6 (340 undecillion)
- OS is configured to switch over formats. socket.getHostByName('localhost') --> can resolve to ipv6 or ipv4
- **LocalHost**
    - localhost resolves to` 127.0.0.1/8` or `::1/8` (ipv6)| loopback interface
    - `c\:window\System32drivers\etc\host`

```mermaid
flowchart LR
    APP[Application]

    APP --> LOOP["localhost<br/>127.0.0.1<br/>Same machine"]

    APP --> PRIVATE["Private IP<br/>192.168.x.x / 10.x.x.x<br/>Private network"]

    APP --> PUBLIC["Public IP<br/>8.8.8.8<br/>Internet"]

    PRIVATE --> NAT[NAT / Router]
    NAT --> PUBLIC
```
| Type                     | Example                        | What it means                                       |
| ------------------------ | ------------------------------ | --------------------------------------------------- |
| **Public IP**            | `8.8.8.8`, `104.26.9.238`      | Globally routable on the Internet                   |
| **Private IP**           | `192.168.1.1`, `192.168.1.100` | Used inside a private network such as your home/VPC |
| **Localhost / Loopback** | `127.0.0.1`                    | Refers back to the same machine                     |

**Dynamic Host Configuration Protocol (DHCP)**
- is a network management protocol used on Internet Protocol (IP) networks
- for automatically assigning IP addresses and other communication parameters to devices

### 2. InfiniBand
- which is used extensively for massive ML training workloads)

---
## B. 📚layer 4 : transport
> provide end-to-end communication services

```
    TCP = reliable pipe | UDP = Fast but Unreliable
    TLS = secure pipe
    HTTP = language spoken through the pipe
```
---
### 1. UDP (fast, but unreliable)
- best effort delivery, superfast, packet might get lost or unorder
- Browsers don't have widespread support for UDP, yet outside of **WebRTC**
```
Connectionless          : No handshake or connection setup 👈
No guarantee of delivery: Packets may be lost without notification
No ordering             : Packets may arrive in a different order than sent
Lower latency           : Less overhead means faster transmission
```
---
### 2. TCP (slow, reliable delivery)
> **QUIC** is a new protocol that aims to provide some of the same benefits of TCP with some modernization and performance benefits.

**TCP connection**
- TCP handshake
- TCP stateful connection  established | tunnel/stream
- TCP will ensure that recipients of messages acknowledge their receipt and, if they don't, will **retransmit** the message until it is acknowledged.

```
Connection-oriented : Establishes a dedicated connection before data transfer
Reliable delivery   : Guarantees that data arrives in order and without errors
Flow control        : Prevents overwhelming receivers with too much data
Congestion control  : Adapts to network congestion to prevent collapse
```
**Abstraction**
- http/https provides abstraction over TCP
- py, java, etc, all has lib to comm with http/https

```mermaid
flowchart LR
    A[Application: Python / Java] --> B[HTTP / HTTPS]
    B --> C[TCP Connection]
    C --> D[IP / Network]
```

**TCP handshake:**
- Connection starts with **3-way handshake**: SYN → SYN-ACK → ACK
- Identified by: Source IP + Source Port + Destination IP + Destination Port

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
### TCP vs UDP

| **Feature**        | **UDP**                      | **TCP**                                      |
| ------------------ | ---------------------------- | -------------------------------------------- |
| Connection         | Connectionless               | Connection-oriented                          |
| Reliability        | Best-effort delivery         | Reliable delivery                            |
| Ordering           | No ordering guarantee        | Maintains byte order                         |
| Flow Control       | No                           | Yes                                          |
| Congestion Control | No                           | Yes                                          |
| Header Size        | 8 bytes                      | 20–60 bytes                                  |
| Speed              | Usually lower overhead       | More overhead                                |
| Use Cases          | Streaming, gaming, VoIP, DNS | Web traffic, APIs, file transfer, email, SSH |


---
## C. 📚Layer 7: Application
### 1. HTTP/HTTPS
- https://youtube.com/watch?v=jQ6_XhsMwws
- [http-evolution](01_basic_03_http-evolution.md)
- A stateless, text-based protocol commonly used for APIs, built on top of TCP
- HTTP connection : HTTP --> TCP handshake
- HTTPS connection : HTTP --> TCP handshake --> [TLS handshake](../../SD_24_security/03_protocol_https_tls.md)
    - Also **handshake/s takes time.**

**connection** 
  - between the client and server is a **state** that both the client and server must maintain. 
  - Unless we use features like **HTTP keep-alive or HTTP/2 multiplexing**, 
  - we need to repeat this connection setup process for every request,
  - like, **short live stateless connection.** : open-close, open-close, ...

> ℹ️ While HTTP can be used directly to build websites, 
> - oftentimes system designs are concerned with the **communication between services via APIs.** 
> - 3 main API paradigms: REST, GraphQL, and gRPC.
> - [API-Design](../../SD_08_API-Design)

```mermaid
sequenceDiagram
    participant C as Client
    participant D as DNS Server
    participant S as Web Server

    C->>D: DNS query for domain
    D-->>C: IP address

    Note over C,S: TCP three-way handshake
    C->>S: SYN
    S-->>C: SYN-ACK
    C->>S: ACK

    Note over C,S: HTTP request/response
    C->>S: HTTP GET request
    Note right of S: Server processing
    S-->>C: HTTP response<br/>Web page content

    Note over C,S: TCP connection teardown
    C->>S: FIN
    S-->>C: ACK
    S->>C: FIN
    C-->>S: ACK
```

### 2. DNS
- https://youtube.com/watch?v=Lsd80uR9Shs (skip)

### 3. stream protocols (SSE + Websocket)
- [core concept :: socket](01_basic_03_socket.md)
- [check here](../SD_03_54_IPC/03_streaming-TCP-based.md#2-websocket--wss-)

### 4. WebRTC
- [check here](../SD_03_54_IPC/05_more.md#2-webrtc)