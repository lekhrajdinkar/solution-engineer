# Database Indexes :: Data Structure Type
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/db-indexing


> 💡Choosing the right indexes is often a key focus in interviews. 
> Mastery of **different index types** and their trade-offs is essential.


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

![img.png](img.png)

> _m (order):The maximum number of children (pointers) an internal node is allowed to have._

Every node in a B-tree **follows strict rules:**
- All leaf `nodes` must be at the **same depth**
- Each `node` can contain between m/2 and m keys
- A node with k `keys` must have exactly **k+1 children**
- `Keys` within a node are kept in **sorted order**

```mermaid
graph TD
    %% Root Node
    subgraph RootLevel ["Index Page: Root Node (In-Memory Lookup)"]
        Root["<b>[ 50 | 90 ]</b><br>Keys sorted lexicographically / numerically"]
    end

    %% Intermediate / Leaf Index Pages
    subgraph ChildLevel ["Index Pages: Intermediate / Leaf Nodes"]
        LeftNode["<b>[ 20 | 35 | 50 ]</b><br>Keys &le; 50"]
        MidNode["<b>[ 55 | 70 | 90 ]</b><br>50 &lt; Keys &le; 90"]
        RightNode["<b>[ 100 | 120 ]</b><br>Keys &gt; 90"]
    end

    %% Target Data Pages on Disk
    subgraph DiskPages ["Database Data Pages on Disk (~8 KB each)"]
        P1[("Data Page 1<br>(age: 20..35)")]
        P2[("Data Page 2<br>(age: 36..50)")]
        P3[("Data Page 3<br><b>(age: 51..55)</b>")]
        P4[("Data Page 4<br>(age: 56..70)")]
        P5[("Data Page 5<br>(age: 71..90)")]
        P6[("Data Page 6<br>(age: 91+)")]
    end

    %% Root to Children Connections
    Root -->|Key &le; 50| LeftNode
    Root -->|"50 < Key &le; 90<br>(Target for age = 51)"| MidNode
    Root -->|Key > 90| RightNode

    %% Leaf to Disk Page Connections
    LeftNode --> P1
    LeftNode --> P2
    
    MidNode -->|"Exact Lookup: age 51 &le; 55<br>(Pulls Page 3 directly)"| P3
    MidNode --> P4
    MidNode --> P5

    RightNode --> P6

    %% Highlight / Style Key Paths
    style Root fill:#f8f9fa,stroke:#333,stroke-width:2px
    style MidNode fill:#e8f4fd,stroke:#007bff,stroke-width:2px
    style P3 fill:#d4edda,stroke:#28a745,stroke-width:2px
    style LeftNode fill:#ffffff,stroke:#6c757d,stroke-width:1px
    style RightNode fill:#ffffff,stroke:#6c757d,stroke-width:1px
    style P1 fill:#ffffff,stroke:#6c757d,stroke-width:1px
    style P2 fill:#ffffff,stroke:#6c757d,stroke-width:1px
    style P4 fill:#ffffff,stroke:#6c757d,stroke-width:1px
    style P5 fill:#ffffff,stroke:#6c757d,stroke-width:1px
    style P6 fill:#ffffff,stroke:#6c757d,stroke-width:1px
```

**Why B-trees are the default choice**
- They maintain **sorted order**, making _range queries and ORDER BY_ operations efficient
- They're **self-balancing**, ensuring predictable performance even as data grows.
  - And remain balanced even with random inserts and deletes
- They **minimize disk I/O** by matching their structure to how databases store data
- They handle both:
  - **equality** searches (email = 'x') 
  - **range searches** (age > 25) equally well


---
### 2. LSM Trees (Log-Structured Merge Trees)


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

