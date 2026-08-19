# Database Indexes
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2193236998
- [http://youtube.com/post/Ugkx8-t9pQQqug7XlQCyEDpY2TGJtE1KuGPi?feature=shared](http://youtube.com/post/Ugkx8-t9pQQqug7XlQCyEDpY2TGJtE1KuGPi?feature=shared)

---
## Overview
> golden rule: index for query, not for table.

Database performance
- Database performance can make or break modern applications
- Modern databases have optimizations like **prefetching and caching** to make random access faster, but the point here still stands.
    - It's **too slow to scan** through every page of data sequentially.
- **Random access** vs **sequential access**
- system with SSD vs System with HDD

Index:
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

## Types (by core data structure) 
| Index Type       | Structure              | Best for                          |
| ---------------- | ---------------------- | --------------------------------- |
| **B-Tree index** | Sorted tree            | `=`, `<`, `>`, `BETWEEN`, sorting |
| **Hash index**   | Hash table             | Exact `=` lookup                  |
| **Bitmap index** | Bitmaps                | Low-cardinality columns           |
| **GIN/GiST**     | Specialized structures | JSON, arrays, full-text, spatial  |

## Type (by creation)
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
##  Types (by Specialized use case)
| Index     | Main purpose                  |
| --------- | ----------------------------- |
| Unique    | Prevent duplicate values      |
| Full-text | Search words inside text      |
| Covering  | Avoid additional table lookup |

### Unique Index
- Ensures duplicate values cannot exist in the indexed column or column combination
- B-tree 

### Full-Text Index
- Optimized for searching words inside large text fields.
- not using b-tree
```sql
  CREATE INDEX idx_post_content
  ON posts
  USING GIN (to_tsvector('english', content));
```
### Covering Index
- index Contains all columns required by a query, 
- so the database may return the result directly from the index without reading the table
```mermaid
flowchart LR
    Q[Query] --> I[Covering Index]
    I --> R[Return Result]
    I -. No table lookup needed .-> T[(Table)]
    style I fill:#f4b183,stroke:#333,color:black
    style T fill:#9dc3e6,stroke:#333,color:black
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

