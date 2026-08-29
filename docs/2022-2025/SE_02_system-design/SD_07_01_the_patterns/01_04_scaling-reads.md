# pattern : scaling-reads
- https://www.hellointerview.com/learn/system-design/patterns/scaling-reads
- [02_NFR_06_Read-Write-ratio.md](../SD_02_Non-functional-req/02_NFR_06_Read-Write-ratio.md)

--- 
## Problem
- Instagram feed example
- read-to-write ratio starts at 10:1 
- but often reaches 100:1 or higher for content-heavy applications
- it's physics :  
  - CPU cores : instructions per second
  - disk I/O is bounded by, the speed of spinning platters
  - ...
> - Read scaling is about **reducing database load, not just making things faster.**
> - If your database handles the load fine but you need lower **latency**, that's a different problem with different solutions, CDN.
--- 
## Solution (3)
- Optimize read performance within your database
- Scale your database horizontally
- Add external caching layers

---
## 1. Optimize Within Your Database
### Indexes
-  with B-tree being the most common for general queries. 
- Hash indexes work well for exact matches, 
- while specialized indexes handle full-text search or geographic queries
- [01_03_indexes-1.md](../SD_05_DataModeling/01_03_indexes-1.md)
- [01_04_indexes-2.md](../SD_05_DataModeling/01_04_indexes-2.md)
- [03_DataStructure](../SD_05_DataModeling/03_DataStructure)
- [01_02_Design-schema.md](../SD_05_DataModeling/01_02_Design-schema.md) | Smart Data modeling

> confidently add indexes for your query patterns - under-indexing kills more applications than over-indexing ever will.

### hardware upgrade
- SSDs can give you 10-100x faster random I/O.
- Adding more RAM means more of your dataset sits in memory instead of on disk.
- And faster CPUs and more cores mean you can handle more concurrent queries

### Denormalization Strategies
-  store the data redundantly in a single table.
- this storage cost could be worth, for the query speed improvement.
- Always consider your read/write ratio before denormalizing. If writes are frequent, the complexity may not be worth it.

### Materialized view
- Materialized views take this further by precomputing expensive aggregations.
-  This is especially powerful for analytics queries that involve complex calculations across large datasets.

---
## 2. Scale Your Database Horizontally
> general rule of thumb:  rough estimates - DB will need to scale horizontally (or add a cache ) when you exceed `50,000-100,000` read requests per second

### [Database Replication](../SD_05_DataModeling/02_basic_concepts/03_01_database-replication.md)
  - All writes go to the primary, but reads can go to any read replica
  - solving the **throughput problem**,
  - also provide **redundancy** as a nice added benefit.
  - key challenge is **replication lag.**

### [Sharding](../SD_06_think-in-scale/01_02_sharding.md)
- improves read by:
  - smaller datasets mean faster individual queries, 
  - distribute read load across multiple databases.
- **Geographic sharding** is particularly effective for global read scaling

> primarily a write scaling technique. For most read scaling problems, adding caching layers is more effective and easier to implement.

---
## 3. Caching layer
- [caching](../SD_06_think-in-scale/01_01_caching.md)
- repeatedly querying your database for identical data - data that rarely changes between requests.
- Caches exploit this pattern by storing frequently accessed data in memory.
- CDN and Edge Caching
  - extend caching beyond your data center to global edge locations | 
  - originally designed for static assets, 
  - modern CDNs cache dynamic content including API responses and database query results.
  - provides dramatic latency improvements | reduce origin load by `90% or more.`
  
>  Focus CDN caching on content with natural sharing patterns - public posts, product catalogs, or search results.

--- 
## Interview
### when to use
- look at each of your API requests, and for the high-volume ones.
- When you sketch out your **API design**, pause at endpoints that will get hammered

### Use case/ scenario 🎯
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/bitly
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/ticketmaster
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/fb-news-feed
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/youtube

```
Ticketmaster
Bitly
Instagram
FB News Feed
YouTube Top K
Yelp
Distributed Cache
Rate Limiter
YouTube
FB Post Search
Local Delivery Service
News Aggregator
Metrics Monitoring
```

### when NOT to use
- Write-heavy systems
- Strongly consistent systems
- Small scale applications
- Real-time collaborative systems

--- 
## Deep dives
**💡What happens when your queries start taking longer as your dataset grows?**
- Fortunately, the solution is pretty straightforward. Just add indexes on columns you query frequently.
- For compound queries, column order in the index matters.

---
**💡How do you handle millions of concurrent reads for the same cached data/hot key?** ⭐
- **request coalescing**
- **Cache key fanout spreads** : distribute the load itself, make multiple cache entries
  - EG: Instead of storing the celebrity's post under one key, 
  - you store identical copies under ten different keys

---
**💡What happens when multiple requests try to rebuild an expired cache entry simultaneously?**

this is **cache stampede**
-  It's like a **DDOS** attack from your own application.
-  entry expires, requests suddenly see a cache miss in the same instant.
- Every single one tries to fetch from your database

Approaches
- **request coalescing**
- One approach uses **distributed locks** to serialize rebuilds. 
  - Only the first request to notice the missing cache entry gets to rebuild it, 
  - while everyone else waits for that rebuild to complete
- A smarter approach uses **probabilistic early refresh**

---
**💡How do you handle cache invalidation when data updates need to be immediately visible?**
- A common **naive approach** is delete the cache entry after a write.
- **cache key versioning.**

