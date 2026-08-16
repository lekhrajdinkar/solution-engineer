# RPC
- https://youtube.com/watch?v=4w8pEyJMpvo

---
## Overview
> - RPC treats a remote service call like calling a local function
> - ecosystem is not matured as REST (simple JSON over HTTP. )

- Built on **HTTP/2** (thus supports streaming ?)
- Fast binary payload (compact/small) with **gRPC/Protobuf** + Generated client/server code
- No explicit code to ser-de, automatically happens.

![img.png](../../../99_img/2025/se_02_sd/rpc.png)

---
## Good for:
- **low-latency service-to-service calls** | microservice arch
- Both services are controlled by the **same organization**

not good for:
- limited browser support, hence hard for UI developer to work with.

---
## REST vs GRPC
Unlike REST, RPC endpoints usually describe actions, not resources.
```
CreatePost
IndexPost
RankPost
GetRecommendations
```
> - REST: What resource do you want?
> - RPC: What operation do you want the service to execute?

| REST                   | RPC                              |
| ---------------------- |----------------------------------|
| Resource-oriented      | Action-oriented                  |
| `GET /posts/123`       | `GetPost(123)`                   |
| Usually JSON/HTTP      | Often Protobuf/HTTP2             |
| Better for public APIs | Better for internal services     |
| Looser coupling        | Tighter contract / strong typing |

