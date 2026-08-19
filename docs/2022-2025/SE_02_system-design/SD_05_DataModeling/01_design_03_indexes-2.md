# Database Indexes :: Data Structure Type

> - Choosing the right indexes is often a key focus in interviews. 
> - Mastery of **different index types** and their trade-offs is essential.

---
## Most common one

```mermaid
graph TD
    Start{"Need efficient data access on large table?"} -->|No| Scan["Full Table Scan is fine"]
    Start -->|Yes| QueryType{"What type of data / query?"}

    QueryType -->|Full-Text / Substring Search| Inv["<b>Inverted Index</b><br>(Elasticsearch, Postgres FTS)"]
    QueryType -->|Geospatial 2D Lat / Long| Geo["<b>Geospatial Index</b><br>(Geohashing / Redis, R-Tree / PostGIS)"]
    QueryType -->|In-Memory Exact Match Only| Hash["<b>Hash Index</b><br>(Redis key-value lookup)"]
    QueryType -->|Range queries, Ordering, Exact match| BTree["<b>B-Tree Index (Default)</b><br>(Standard SQL / Postgres)"]

    style Start fill:#f8f9fa,stroke:#333,stroke-width:2px,color:black
    style QueryType fill:#f8f9fa,stroke:#333,stroke-width:2px,color:black
    style Inv fill:#ffffff,stroke:#007bff,stroke-width:1.5px,color:black
    style Geo fill:#ffffff,stroke:#28a745,stroke-width:1.5px,color:black
    style Hash fill:#ffffff,stroke:#ffc107,stroke-width:1.5px,color:black
    style BTree fill:#ffffff,stroke:#dc3545,stroke-width:1.5px,color:black
    style Scan fill:#ffffff,stroke:#6c757d,stroke-width:1.5px,color:black
```

| Index Type | Structure / Mechanism | Best Used For | Production Examples |
| :--- | :--- | :--- | :--- |
| **B-Tree** *(Default)* | Balanced search tree of sorted keys pointing to child nodes or data pages. | Exact match (`=`), Range queries (`<`, `>`), Sorting (`ORDER BY`), Prefix matching. | PostgreSQL, MySQL, default for most relational DBs. |
| **Hash Index** | Hash table mapping key hashes directly to page pointers. | Pure exact lookups (O(1)) in memory; **not** suitable for range queries or sorting. | Redis, in-memory caches. *(Rarely used as primary on-disk DB index)*. |
| **Geospatial Indexes** | • **Geohashing**: Converts 2D lat/long into 1D prefix strings indexed via B-Trees.<br>• **R-Trees**: Hierarchical bounding boxes/clusters.<br>• **Quad Trees**: Recursive 4-way grid splitting based on node density. | 2D spatial queries, radius search, bounding-box proximity. | Redis (Geohashing), PostGIS extension on PostgreSQL (R-Trees). |
| **Inverted Index** | Maps individual terms/tokens to the list of document/row IDs containing them. | Full-text search, arbitrary substring/keyword search within strings. | Elasticsearch, Apache Lucene, PostgreSQL Full-Text Search. |

---
### 1. B-Tree Indexes
- [btree.md](03_DataStructure/01_core_02_btree.md)

---
### 2. LSM Trees (Log-Structured Merge Trees)
- [LSM.md](03_DataStructure/01_core_03_LSM.md)

---
### 3. Hash Indexes


---
### 4. Geospatial Indexes


---
### 5. Inverted Indexes

---
## Index Optimization Patterns
### Composite Indexes
### Covering Indexes

