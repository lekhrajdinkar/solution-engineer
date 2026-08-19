# B tree | read-heavy workloads
## Reference
- https://www.youtube.com/watch?v=Q9xD4J3tezw
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/db-indexing#b-tree-indexes

---
## Overview
![img.png](../../../../99_img/2025/oauth2/001/img-btree.png)

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

---
## Why B-trees are the default choice
- They maintain **sorted order**, making _range queries and ORDER BY_ operations efficient
- They're **self-balancing**, ensuring predictable performance even as data grows.
    - And remain balanced even with random inserts and deletes
- They **minimize disk I/O** by matching their structure to how databases store data
- They handle both:
    - **equality** searches (email = 'x')
    - **range searches** (age > 25) equally well

---
## Read behavior (fast)
- Root → Internal Node → Leaf Page → Record
- Reads are efficient because the database can quickly navigate (sorted tree structures) to the required page.

---
## Write behavior (Slow)
A write may require:
- Finding the target page
- Updating an existing disk page
- Splitting a page when it becomes full
- Updating indexes 👈
- Writing to the transaction log