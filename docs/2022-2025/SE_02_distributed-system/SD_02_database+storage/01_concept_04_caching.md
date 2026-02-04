# Caching
> Solves performance of Distr system with reducing LATENCY

## Overview
- storing frequently used data in a **different location**  
  - other from the original data source
  - to provide faster data access.
- high-speed data storage layer. 
- caching in REST API [api_04_caching](../SD_01_system/06_api_04_caching.md)

## when to use
  - ideal for **static or immutable data**, 
    - Dynamic data, complex, need to efficiently synchronize data across multiple locations
  - **single entity** reads or writes data
  - **consistency or staleness** of data is not a major concern
    - else, design a system to properly **invalidate** stale data

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
- check here: [distributed caching](01_concept_04_caching-distributed.md)

---
##  When Caching is Helpful 👈🏻
**Minimizing Network Calls** 
- In a client-server-database architecture, 
- network calls between these separate machines are common.
- Caching the results of network operations, could
- speeds up processes by avoiding frequent calls to the database.

**Speeding Up Computationally Expensive Operations**

**Preventing Database Overload (Data Hotspots)**
- When many clients (e.g., millions of users) try to access the same popular data 
- reducing read requests on Database

---
## Caching Systems for Writes
✔️**Write-Through Cache:** 
- When data is edited, the system writes the data to both :
  - the cache 
  - the main database, **at the same time**.
- Benefit: Cache and database are always in **sync**.
- Downside: **Doesn't minimize network calls**, as the database is always hit.

![img_6.png](../../../99_img/2026/02/01/01/img_6.png)

✔️**Write-Back Cache:** 
- When data is edited, the system  updates 
  - the cache **immediately** + sends a response back to the client (non-blocking) 
  - The database is updated **asynchronously** at a later time.
    - (e.g., randomly, scheduled every 30 seconds or 5 minutes).
- Benefit: Faster response to the client, because the database isn't immediately touched.
- Downside: The cache and database can **temporarily be out of sync**.

![img_7.png](../../../99_img/2026/02/01/01/img_7.png)

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