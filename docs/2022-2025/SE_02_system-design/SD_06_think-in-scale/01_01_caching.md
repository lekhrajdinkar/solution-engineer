# Core building block : Caching
Reference
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2190592600
- https://www.youtube.com/watch?v=1NngTUYPdpI
- https://www.hellointerview.com/learn/courses/system-design/lesson/thinking-in-scale/caching 👈
- [REST API :: caching](../SD_08_API-Design/04_api_design_06_caching.md)

--- 
## Overview
> Act as **high-speed data storage layer.** Caches are essential for scalable systems

**Definition**
- storing frequently used data in a **different location**  other from the original data source 
- RAM (`10k` times faster), 
- if **closer to client**, Reduce network response time, to provide more faster data access
- **layers**: databases  built-in, in-process cache, client cache, **external (scales well, default)**, CDN global cache

**candidates**
- ideal for **static or immutable data**,
- for **Dynamic data**, its complex and need to efficiently design system to:
    - synchronize data across multiple locations
    - invalidate cache.
---
## Where to cache / cache layer
**Hardware Level**
- CPU caches are built into hardware for faster data retrieval from memory.
- This is generally out of scope for software engineers

### 1. external (default)
- `redis, memcache`
- **Scale well** because every application server can share the same cache
- ensuring a single source of truth  | simplicity | default

```
                  ┌──────────────┐
                  │    Cache     │
                  └──────▲───────┘
                         │
             1. Check    │ (Read/Write)
              Cache      │
                         │
┌──────────┐       ┌─────┴──────────────┐   2. Fallback:    ┌──────────────┐
│  Client  │ <===> │ Application Server │ ----------------> │   Database   │
└──────────┘       └────────────────────┘    Read on Miss   └──────────────┘
```

[**Distributed caching**](02_01_distributed-caching.md)
- fault-tolerant
- scalable
- serves the response from closest cache-node, thus improved performance
- Also suitable for session management
- **Redis** is beyond just being cache

---
### 2. CDN 
> read through pattern
- [CDN : global caching to optimize network latency](../SD_03_Core-building-blocks/SD_03_53_network/02_core_02_latency+regionalization.md#a-cdn)
- static files. 
- also cache **public API responses, HTML pages**, 
- even run **edge logic** to personalize content or enforce security rules before requests reach your servers
- eg: Cloudflare, Fastly, and Akamai

```
without CDN, adds 250–300 ms of latency per request.
With CDN   , in 20–40 ms. That is a **massive performance difference.**
```
---
### 3. Client side
- data close to the requester to avoid unnecessary network calls
- caching within a client library 
- browser level `redux`
- `Redis clients` cache cluster metadata, that way client can route requests directly to the right node

**tradeoff:**
- Data can go stale and invalidation is harder

---
### 4. in process cache (fast)
> It is great for speed but not a replacement for Redis |  optimization layer
- As **hardware improves**, servers run on machines with a **lot of memory**. 
- can use that memory to cache data directly inside the application process,
- instead of always calling out to Redis or the database.
- good for:
```
Configuration values
Feature flags
Small reference datasets
Hot keys
Rate limiting counters
Precomputed values
...
```

---
## Caching arch pattern/s

```
Cache-Aside   → App manages cache
Read-Through  → Cache manages reads
Write-Through → Cache writes DB immediately
Write-Behind  → Cache writes DB later
Refresh-Ahead → Cache refreshes before expiry
```

### 1. cache Aside (Lazy Loading)
-  only caches data when needed, which keeps the cache lean.
- a cache miss causes extra latency.

Read
```mermaid
flowchart LR
    A1[App] -->|1. Get| C1[(Cache)]
    C1 -->|2. Miss| A1
    A1 -->|3.1 Read| DB1[(DB)]
    DB1 -->|3.2 Data| A1
    A1 -->|4. Put in cache| C1
    style C1 fill:orange,color:black
```

```mermaid
sequenceDiagram
    participant A as Application
    participant C as Cache
    participant DB as Database

    A->>C: GET(key)

    alt Cache Hit
        C-->>A: Data
    else Cache Miss
        C-->>A: Miss
        A->>DB: Read data
        DB-->>A: Data
        A->>C: Store in cache
        A-->>A: Return data
    end
```

Write
```mermaid
flowchart LR
        A2[App] -->|1. Write| DB2[(DB)]
        A2 -->|2. Invalidate / Update| C2[(Cache)]
    style C2 fill:orange,color:black
```

### 2. Write-Through Cache
> - Redis itself does not natively support write-through. We need application code or a framework to implement this pattern.
> - less popular, needs specialized caching infrastructure and still has consistency edge cases.

dual-write
- application writes only to the **cache**. 
- The cache then synchronously writes to the **database**, before returning to the application

Benefit: 
- Consistent | Cache and database are always in **sync**, **provide dual-write happened successfully.** 
- else  the systems can end up inconsistent

Trade off: 
- slower write
- latency  | **Doesn't minimize network calls**, as the database is always hit.
- also **pollute the cache** with data that may never be read again.
- complexity : need retry logic, error handling, etc

```mermaid
flowchart LR
    A[Application] <--> C[(Cache)]
    C --> |sync| DB[(Database)]
```


### 3.  Write-Back/behind Cache
> use case: need high write throughput and eventual consistency is acceptable | **Common in analytics and metrics pipelines.**

- The cache **batches** and writes the data to the database **asynchronously** in the background.
- Benefit:  makes writes very fast
- trade off: eventual consistency .

```mermaid
flowchart LR
    A2[App] -->|1. Write| C2[(Cache)]
    C2 -->|2. Return immediately| A2
    C2 -.->|3. Async write| DB2[(DB)]
```
---
### 4. Read-Through (eg: CDN)
> It is less common in practice than **cache-aside.**
- cache acts as a **smart proxy**. 
-  application **never** talks to the database directly. 
- On a cache miss, the **cache itself fetches from the database**, stores the data, and returns it.
- use case:   **CDNs** or similar infrastructure. 👈

```mermaid
flowchart LR
    A1[App] -->|1. Get| C1[(Cache)]
    C1 -->|Hit| A1
    C1 -->|Miss| DB1[(DB)]
    DB1 -->|Load data| C1
    C1 -->|Return| A1
    style C1 fill:orange,color:black
```

---
## Caching Invalidation and eviction
> Since cache memory is limited, have to free-up space

we dont want stale data in cache
- Write-Through Cache, invalidate the old data as well, sync 👈🏻
- Write-Back Cache,invalidate the old data as well, Async 👈🏻

### **Least Recently Used (LRU)⭐:** 
- Removes the data that hasn't been accessed for the longest time
- oldest one

### **Least Frequently Used (LFU):** 
- Removes data that has been accessed the fewest times (4:07).
- rarely used

### **Last-In, First-Out (LIFO)**

### **First-In, First-Out (FIFO)** 

### **random eviction**

### TTL based

---
## Common Caching Problems
### 1. Cache Stamped (Thundering herd)
![img.png](../SD_01_foundation/img.png)

- when a popular cache entry expires and many requests try to rebuild it at the same time
- handle:
  - **Request coalescing** (single flight)
  - **Cache warming**: Refresh popular keys proactively before they expire. This only helps when using TTL-based expiration.


```mermaid
sequenceDiagram
    autonumber
    actor Clients as Multiple Clients (Concurrent)
    participant App as Application Server
    participant Cache as Cache (Redis/Memcached)
    participant DB as Database

    Note over Cache: Key expires / evicted (TTL = 0)

    par High-Volume Concurrent Reads
        Clients->>App: Request hot key
    end

    App->>Cache: 1. Check cache for key
    Cache-->>App: 2. Cache MISS (Key missing/expired)

    Note over App,DB: ⚠️ Thundering Herd / Cache Stampede: All concurrent requests fall back to DB simultaneously!

    par Overwhelming DB Traffic
        App->>DB: 3. Read from DB (Full table / heavy joins)
        DB-->>App: 4. Return results (High latency / DB CPU 100%)
    end

    App->>Cache: 5. Re-populate cache with key + TTL
    App-->>Clients: 6. Respond to clients
```
---
### 2. Cache Consistency
- Cached data can fall out of sync with the database.
- complexity around staleness and invalidation
- Cache failures can crush your database

**There is no perfect solution**
- Cache invalidation on writes
- **Short TTLs** for stale tolerance
- Accept eventual consistency: metrics, and analytics, a short delay is usually fine.

---
### 3. Hot Keys
-  cache entry that receives a huge amount of traffic compared to everything else.
- a single hot key can overload one cache node
- eg: celebrity post
- handle:
  - Replicate hot keys: Store the same value on multiple cache nodes and load balance reads across them.
  - Add a local fallback cache: Keep extremely hot values **in-process cache** to avoid pounding Redis. 👈 👈
  - Apply rate limiting: Slow down abusive traffic patterns on specific keys.

### 4. Cache failures
- what if cache failed, Will your database get crushed by the sudden traffic spike ?
- **circuit breakers** so we don't overwhelm the database with a stampede

---
## Bring Cache to system
> - need to handle high read traffic. Your database becomes the bottleneck, latency starts creeping up
> - Most importantly, don't cache everything and when a well-indexed database is enough.
> - if **consistency or staleness** of data is not a major concern.

**Delivery Framework**
1. Identify the bottleneck ⭐
   - Read-heavy workload:
   - Expensive queries:
   - Latency requirements:
   - High database CPU:
2. Decide what to cache + cache key
   - Not everything should be cached. Focus on data that is read frequently, and updated rarely
   - eg: cache user profiles since they're read on every page load but only updated when users edit their settings
   - eg:  trending posts feed
3. Choose your cache architecture
4. Set an eviction policy
   -  invalidate on writes or  rely on TTL
5. Address the downsides







