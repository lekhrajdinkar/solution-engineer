# Network Model
## reference
- https://youtube.com/watch?v=tpgoQwMg__M

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
| ----- | ------------ | ---------------------------------------------------- | ------------------------------- |
| **7** | Application  | Network services used by applications                | HTTP, HTTPS, DNS, SMTP, FTP     |
| **6** | Presentation | Data format, encoding, encryption, compression       | TLS/SSL, JSON, JPEG, UTF-8      |
| **5** | Session      | Establish/manage/close communication sessions        | RPC sessions, sockets concepts  |
| **4** | Transport    | End-to-end delivery, ports, reliability              | TCP, UDP                        |
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