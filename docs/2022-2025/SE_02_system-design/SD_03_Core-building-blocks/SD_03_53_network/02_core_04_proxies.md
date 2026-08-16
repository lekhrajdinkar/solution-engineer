# Core building: Forward/reverse Proxy
- https://www.youtube.com/watch?v=qbuMKSTv3yU
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2190592400
---
## Overview
| Aspect                 | Forward Proxy                | Reverse Proxy                       |
| ---------------------- | ---------------------------- |-------------------------------------|
| Hides                  | Client identity              | Server identity                     |
| Used by                | Users or internal networks   | Applications and platforms          |
| Examples               | Corporate proxy, VPN gateway | NGINX, Controlpanel in k8s, AWS ALB |

---
## Forward Proxy
```mermaid
flowchart LR
    subgraph ClientSide["Client Side"]
        C[Client]
        FP[Forward Proxy]
        C --> FP
    end
    FP --> I[Internet / External Server]
```
```mermaid
flowchart LR
    subgraph ServerSide["client Side"]
        C1[Client 1] --> FP[Forward Proxy]
        C2[Client 2] --> FP
        C3[Client 3] --> FP
    end    
    FP --> I[Internet / External Server]
```
**Common uses**
- bypass firewall restrictions 
- conceal their identity, as the origin server only sees the forward proxy's IP address (1:24-1:31). 

---
## Reverse Proxy
```mermaid
flowchart LR
    U[User / Client] --> RP
    subgraph ServerSide["Server Side"]
        RP[Reverse Proxy]
        RP --> S1[Server 1]
        RP --> S2[Server 2]
        RP --> S3[Server 3]
    end
```
**Common uses**
- Load balancing
- TLS termination
- Authentication
- Rate limiting
- Caching (css, html, etc)
- Hiding backend servers
- Routing requests to microservices
