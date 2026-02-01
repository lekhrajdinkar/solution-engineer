# rate limiting
> crucial aspect of system design, especially in large-scale distributed systems

## Distributed DOS
![img_1.png](../../../99_img/2026/02/07/04/img_1.png)

## Overview
- https://bytebytego.com/courses/system-design-interview/design-a-rate-limiter
- thresholds on operations
- ensuring that if a certain number of requests are exceeded within a given time frame,
- safeguards systems from Denial of Service (DoS) + DDOS
- Type:
  - user-based 
  - geographic-based 
  - server-based
  - ...

## Algo
https://bytebytego.com/courses/system-design-interview/design-a-rate-limiter

---
**Token bucket** 
- bucket with token, refilled at interval  === **Refill rate**
- but with pre-defined capacity (to hold limited token)  === **Bucket size**
- have to tune these params well

![img_2.png](../../../99_img/2026/02/07/04/img_2.png)

---
**Leaking bucket** (no token)
- Bucket has **capacity** (to hold request)  === **Bucket size**
- bucket has **leak**, for constant rate processing === `FIFO queue`

![img_3.png](../../../99_img/2026/02/07/04/img_3.png)

---
**Fixed window counter**
- divides the timeline into fix-sized time windows 
- and assign a **threshold counter** ,  for each window
- Each request:
  - increments the counter by one.
  - NOT logging request-time 🔺
- Once the counter reaches the threshold, new requests are dropped **until a new time window starts.**
- problem:  **burst of traffic at the edges of time windows**
  - ![img_4.png](../../../99_img/2026/02/07/04/img_4.png)

---
**Sliding window log**
- variation for above to fix that edge burst problem
- algo:
  - also **log** request-time, when any request comes.
  - when next window state, it clean-ups all logs older that interval (1 min)
  - this way we: 
    - start new window 
    - then slide it to left till time (last request in **log**)

![img_5.png](../../../99_img/2026/02/07/04/img_5.png)

---
**Sliding window counter**