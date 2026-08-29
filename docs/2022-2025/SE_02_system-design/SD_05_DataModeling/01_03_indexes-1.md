# Database Indexes
## Reference
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2193236998
- [http://youtube.com/post/Ugkx8-t9pQQqug7XlQCyEDpY2TGJtE1KuGPi?feature=shared](http://youtube.com/post/Ugkx8-t9pQQqug7XlQCyEDpY2TGJtE1KuGPi?feature=shared)
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/db-indexing

---
## Overview
> golden rule: index for query, not for table.

**Database performance**
- Database performance can make or break modern applications
- Modern databases have optimizations like **prefetching and caching** to make random access faster, but the point here still stands.
    - It's **too slow to scan** through every page of data sequentially.
- **Random access** vs **sequential access**
- system with SSD vs System with HDD
- core problem : Significant speed difference between RAM and disk storage with disks being much slower `100000 times`

**Index:**
- An index is an additional data structure that helps the database locate rows faster without scanning the entire table.
- in an interview, you'll typically want to **callout which columns are indexed and why**.

```
Without index: Full table scan → O(n)
With index:    Index lookup → usually O(log n)
```
**example**
```
Index on posts.user_id to quickly find all posts by a user
Index on posts.created_at to load recent posts chronologically
Composite index on (user_id, created_at) to efficiently load a user's recent posts
```
---
## when to create ✔️
- Columns used in WHERE
- Columns used in ORDER BY
- Columns used in JOIN / Foreign-key columns

## when to avoid ❔
| Situation                     | Why                                                                       |
| ----------------------------- | ------------------------------------------------------------------------- |
| **Small table**               | A full table scan may be faster than reading the index and then the table |
| **Low selectivity column**    | The value matches too many rows, so the index provides little benefit     |
| **Rarely queried column**     | Write and storage overhead are not justified                              |
| **Frequently updated column** | Every update may require index maintenance                                |

---
## Trade-off
Indexes improve read speed, but add cost:
- Extra storage ( sometimes nearly as much as the original data.)
- Slower INSERT, UPDATE, DELETE
- Index maintenance and fragmentation

it's still a good idea to closely monitor index usage and avoid creating unnecessary indexes that don't provide significant benefits.

```mermaid
flowchart LR
    Q[Frequent Queries] --> P[Profile Bottlenecks]
    P --> I[Add Index Thoughtfully]
    I --> M[Monitor Performance]
    M --> R{Improved overall?}

    R -->|Yes| K[Keep Index]
    R -->|No| D[Modify or Remove Index]

    style I fill:#f4b183,stroke:#333,color:black
    style K fill:#a9d18e,stroke:#333,color:black
    style D fill:#f4cccc,stroke:#333,color:black
```
**Practical approach**
1. Identify frequently executed queries
2. Profile them using EXPLAIN ANALYZE
3. Add indexes only where needed
4. Monitor read and write performance
5. Remove unused or duplicate indexes

## Type
- **primary** - on unique key, PK
- **secondary** - on additional col as per query need
- **GSI** on distributed DB

```mermaid
flowchart LR
    Q[Query] --> I[B-Tree Index]
    I --> P[Find row pointer]
    P --> T[Read actual table row]

    style I fill:#f4b183,stroke:#333
    style T fill:#9dc3e6,stroke:#333
```
---
## indexes::Data structure
[01_design_03_indexes-2.md](01_04_indexes-2.md)

---
## External systems and extensions⭐
**Elasticsearch**
- primary database > CDC (will add some lag) > Elasticsearch (for full-text search)
- but worth lets you search in ways your main database can't handle.

```mermaid
flowchart LR
    subgraph S1 ["🔍 Elasticsearch (Full-Text Search)"]
        direction LR
        DB[("Primary DB\n(OLTP)")] -->|"CDC Pipeline\n(minor lag)"| ES[("Elasticsearch")]
        ES --> O1["⚡ Fast tokenized & fuzzy search\n🚀 Unloads heavy queries from primary DB"]
    end
```

**Postgres Extension**

```mermaid
flowchart LR
    subgraph S5 ["⚡ Citus (Distributed SQL)"]
        direction LR
        PG4[("PostgreSQL")] --- EXT4["Citus\nExtension"]
        EXT4 --> O5["🔀 Horizontal sharding across nodes\n🌐 Distributed tables & queries"]
    end
    
    subgraph S2 ["🌍 PostGIS (Geospatial Extension)"]
        direction LR
        PG[("PostgreSQL")] --- EXT["PostGIS Extension"]
        EXT --> O2["📍 Spatial indexing (R-Tree / GiST)\n📏 Proximity, polygon & bounding-box queries"]
    end

    subgraph S3 ["🧠 pgvector (AI / Vector Search)"]
        direction LR
        PG2[("PostgreSQL")] --- EXT2["pgvector\nExtension"]
        EXT2 --> O3["🤖 High-dimensional embeddings\n🔎 HNSW / IVFFlat semantic search"]
    end

    subgraph S4 ["📈 TimescaleDB (Time-Series)"]
        direction LR
        PG3[("PostgreSQL")] --- EXT3["TimescaleDB\nExtension"]
        EXT3 --> O4["⏱️ Hypertables & auto-partitioning\n📊 Metrics, IoT & columnar compression"]
    end

```


---

## Interview 
Tip-1 : 
- connect your indexes directly to your API endpoints.
- shows you're thinking about real query performance.
- identify which columns belong in `WHERE` and `ORDER BY`

```mermaid
graph TD
    Root["<b>Index Selection Flow</b><br>Driven by API Endpoints"]
    
    API1["Endpoint: <code>GET /users/:id/posts</code>"] --> Q1["Query: Fetch posts by <code>user_id</code>"]
    Q1 --> IDX1["<b>Add Index:</b> <code>posts(user_id)</code>"]

    API2["Endpoint: <code>GET /posts/:id/comments</code>"] --> Q2["Query: Fetch comments by <code>post_id</code>"]
    Q2 --> IDX2["<b>Add Index:</b> <code>comments(post_id)</code>"]

    API3["Sorting: <code>...ORDER BY created_at DESC</code>"] --> Q3["Query: Time-based ordering"]
    Q3 --> IDX3["<b>Composite Index:</b> <code>posts(user_id, created_at)</code>"]

    style Root fill:#f8f9fa,stroke:#333,stroke-width:2px
    style IDX1 fill:#e8f4fd,stroke:#007bff,stroke-width:1.5px
    style IDX2 fill:#e8f4fd,stroke:#007bff,stroke-width:1.5px
    style IDX3 fill:#e8f4fd,stroke:#007bff,stroke-width:1.5px
```

