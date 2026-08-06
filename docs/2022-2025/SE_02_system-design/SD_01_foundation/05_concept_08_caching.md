# Core building block : Caching
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2190592600
--- 
## Overview
- [REST api_caching](../SD_08_API-Design/04_api_design_06_caching.md)
- Act as **high-speed data storage layer.**
- Definition:
  - storing frequently used data in a **different location**  
  - other from the original data source
  - to provide faster data access.
  - Reduce response time

**candidate for cache**
  - ideal for **static or immutable data**, 
    - for Dynamic data, its complex andneed to efficiently design system to:
      - `synchronize data` across multiple locations
      - `invalidate cache.`
  - if **single entity** reads or writes data
  - if **consistency or staleness** of data is not a major concern. like social-media post.👈
    - else, design a system to properly **invalidate** stale data


**When Caching is Helpful** 
- Minimizing/avoid frequent Network Calls**
- Speeding Up Computationally Expensive Operations**
- Preventing Database Overload (Data Hotspots)**
    - When many clients (e.g., millions of users) try to access the same popular data
    - reducing read requests on Database

---
## Caching Systems for Writes
✔️**Write-Through Cache:**
- When data is edited, the system writes the data to both :
    - the cache
    - the main database, **at the same time**
- Benefit: Cache and database are always in **sync**.
- Downside: **Doesn't minimize network calls**, as the database is always hit.

![img_6.png](../../../99_img/2026/02/01/01/img_6.png)

✔️**Write-Back Cache:**
- When data is edited, the system  updates
    - the cache **immediately** + sends a response back to the client (non-blocking)
    - The database is updated **asynchronously** at a later time.
        - (e.g., randomly, scheduled every 30 seconds or 5 minutes).
- Benefit: **Faster** response to the client, because the database isn't immediately touched.
- Downside: The cache and database can **temporarily be out of sync, with stale data**.

![img_7.png](../../../99_img/2026/02/01/01/img_7.png)

---
## Where can Caching be Placed
### Hardware Level
- CPU caches are built into hardware for faster data retrieval from memory.
- This is generally out of scope for software engineers

### Client Level
- The client can store data to avoid going to the server.
- redux

### Server Level 
💠 **centralized** cache for all node like eg redis. 
- ensuring a single source of truth
- single point of failure
- simplicity
- limited scaling

![img_4.png](../../../99_img/2026/02/01/01/img_4.png) 

💠 **local cache of each node**

![img_5.png](../../../99_img/2026/02/01/01/img_5.png)

💠 **Distributed** Caches.
- fault-tolerant
- scalable
- serves the response from closest cache-node, thus improved performance
- Also suitable for session management
- **Redis** is beyond just being cache
- check here: [distributed caching](../SD_06_Distributed-system/02_01_distributed-caching.md)

---
## Caching Invalidation
we dont want stale data in cache
- Write-Through Cache, invalidate the old data as well, sync 👈🏻
- Write-Back Cache,invalidate the old data as well, Async 👈🏻
- TTL based eviction

---
## Cache Eviction Policies 
Since cache memory is limited, have to free-up space

💠**Least Recently Used (LRU):** 
- Removes the data that hasn't been accessed for the longest time
- oldest one

💠**Least Frequently Used (LFU):** 
- Removes data that has been accessed the fewest times (4:07).
- rarely used

💠**Last-In, First-Out (LIFO)**

💠**First-In, First-Out (FIFO)** 

💠**random eviction**
