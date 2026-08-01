# Throttling >> Rate limiting
> Systems often implement both, starting with throttling and moving to rate limiting if capacity is still threatened
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2190592399
- https://www.youtube.com/watch?v=_qNHROq0pGk bm
- https://bytebytego.com/courses/system-design-interview/design-a-rate-limiter

---
## ️✔️Overview - Throttling
- **Slows down the rate of requests** by:
    - delaying them
    - or limiting processing resources,
- ,rather than **rejecting them outright**.
- Useful for maintaining service availability under heavy load,
- ensuring all requests are **eventually served**
- Type:
    - **Dynamic Throttling**: adjusts based on real-time metrics like CPU load
    - **Adaptive Throttling**: uses machine learning to predict spike

![img.png](../../../99_img/2026/02/image-4.png)

---
## ✔️Overview - Rate limiting
- Best place to implement is on : [📚API-gateway](02_api_02_apiGateway.md)
- Thresholds / **strict cap** on operations
  - ensuring that if a certain number of requests are exceeded within a given time frame.
  - Requests exceeding the limit are blocked or receive a `429` Too Many Requests error.
- safeguards systems, from: 
  - preventing brute-force attacks
  - Denial of Service (DoS) + DDOS
  
---  
## ✔️Rate limiting : Types/level
| Level            | Identified by               | Main purpose             |
| ---------------- | --------------------------- | ------------------------ |
| User-based       | User ID, API key, JWT       | Prevent individual abuse |
| Geographic-based | Country, region, IP range   | Control regional traffic |
| Server-based     | Service, instance, endpoint | Protect system capacity  |

### user-based
- Limit requests per user, API key, client ID, or JWT subject.
- Example: `100 requests/minute per use`
- Use for: Preventing one user from abusing the API

### geographic-based
- Limit traffic based on country, region, or CDN edge location.
- Example: `10,000 requests/minute from one region`
- Use for:
  - Controlling regional traffic spikes
  - Blocking or restricting unsupported locations


### server-based
- Limit requests reaching a server, service, or endpoint
- Example: `5,000 requests/second per API server`
- Use for:
  - Preventing server overload
  - Maintaining system availability
---
## ✔️Rate Limiting : Algos (5)
### 1 Token bucket `(rate-limit)`
- bucket with token, refilled at interval  === **Refill rate**
- but with pre-defined capacity (to hold limited token)  === **Bucket size**
- have to tune these params well

![img_2.png](../../../99_img/2026/02/07/04/img_2.png)

---
### 2 Leaking bucket `(throttle)`
- no token thing.
- Bucket has **capacity** (to hold request)  === **Bucket size**
- bucket has **leak**, for constant rate processing === `FIFO queue`

![img_3.png](../../../99_img/2026/02/07/04/img_3.png)

---
### 3 Fixed window counter
- divides the timeline into fix-sized time windows 
- and assign a **threshold counter** ,  for each window
- Each request:
  - increments the counter by one.
  - NOT logging request-time 🔺
- Once the counter reaches the threshold, new requests are dropped **until a new time window starts.**
- problem:  **burst of traffic at the edges of time windows**
  - ![img_4.png](../../../99_img/2026/02/07/04/img_4.png)

---
### 4 Sliding window log
- variation for above to fix that edge burst problem
- algo:
  - also **log** request-time, when any request comes.
  - when next window state, it clean-ups all logs older that interval (1 min)
  - this way we: 
    - start new window 
    - then slide it to left till time (last request in **log**)

![img_5.png](../../../99_img/2026/02/07/04/img_5.png)

---
### 5 Sliding window counter