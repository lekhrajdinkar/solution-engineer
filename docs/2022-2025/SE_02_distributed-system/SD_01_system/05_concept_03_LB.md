# Load balancer LB
## Overview
- https://youtu.be/ZcNaOuxcuyA?si=9eTTSUfpUQzi112D bm
- **traffic cop** between clients and multiple servers
- **main job** is to evenly distribute client requests across available servers
- can be hardware or software-based
  - with software-based being more cost-effective and customizable.
- eg:
  - lb for backend-server
  - lb for multi-DB reader instance
  - DNS load balancer
  - ![img_3.png](../../../99_img/2026/02/01/01/img_3.png)

## Serve Selection strategy
**Random**

**Round Robin**
- Distributes requests sequentially to each server in a loop.

**Weighted Round Robin** 
- Similar to round-robin,
- but assigns more requests to more powerful servers.

**Health-based selection**
- Directs traffic based on server response time and resource utilization.

**IP-based hashing** 
- Ensures requests from a specific client always go to the same server,
- useful for caching.
- hash(IP1) --> server-3
- hash(IP2) --> server-9
- ...