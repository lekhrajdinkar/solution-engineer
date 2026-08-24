# API gateway
- https://youtube.com/watch?v=JNmiOw26PGg
- https://youtube.com/watch?v=BWB-S0awDnA | API gateway vs Load balancer

---
## Overview
> GraphQL as API-gateway

**Reverse proxy** act as **single entry point** between a client and various backend ms/API
  - data transformation, response aggregation
  - **load balancing and routing**
  - Security: Authn and Authz, TLS termination, Throttling, Rate Limiting
  - caching  
  - **Analytics and Monitoring**
    - log usage and collect metrics on API calls
    - understand traffic trends
    - monitor response times.

![img_1.png](../../../../99_img/2026/01/03/img_1.png)

## Options
**cloud provided**
- [05_1_API_gateway_SAA.md](../../../CE_02_AWS_SAA/04_network/05_1_API_gateway_SAA.md)
- [05_2_API_gateway_DVA.md](../../../CE_02_AWS_SAA/04_network/05_2_API_gateway_DVA.md)

**Custom build**
- build your own with Spring Cloud gateway,etc
- more maintenance and development effort

---
## API gateway in front of LB

```mermaid
flowchart LR
    %% Clients with IPs
    subgraph Clients ["Clients"]
        direction TB
        C1["User A (IP: 34.90.202.77)"]
        C2["User C (IP: 55.175.32.498)"]
        C3["User B (IP: 66.183.42.238)"]
    end

    %% Gateway / API Layer
    Gateway["API Gateway / Middleware"]

    %% Processing / Core
    Processor(("Processing"))

    %% Load Balancer
    LB{"Load Balancer"}

    %% Backend Servers
    subgraph Servers ["Backend Servers"]
        direction TB
        S1["Server 1"]
        S2["Server 2"]
        S3["Server 3"]
        S4["Server 4"]
    end

    %% Flow Connections
    C1 --> Gateway
    C2 --> Gateway
    C3 --> Gateway
    
    Gateway --> Processor
    Processor --> LB
    
    LB --> S1
    LB --> S2
    LB --> S3
    LB --> S4

    %% Styling
    style Clients fill:transparent,stroke:none
    style Servers fill:transparent,stroke:none
    style Gateway fill:#581c87,stroke:#9333ea,color:#fff,rx:5,ry:5
    style Processor fill:#1e1b4b,stroke:#3b82f6,color:#fff
    style LB fill:#ea580c,stroke:#f97316,color:#fff
    style C1 fill:#831843,stroke:#db2777,color:#fff,rx:5,ry:5
    style C2 fill:#3b0764,stroke:#a855f7,color:#fff,rx:5,ry:5
    style C3 fill:#064e3b,stroke:#10b981,color:#fff,rx:5,ry:5
    style S1 fill:#311045,stroke:#a855f7,color:#fff,rx:5,ry:5
    style S2 fill:#311045,stroke:#a855f7,color:#fff,rx:5,ry:5
    style S3 fill:#311045,stroke:#a855f7,color:#fff,rx:5,ry:5
    style S4 fill:#311045,stroke:#a855f7,color:#fff,rx:5,ry:5
```