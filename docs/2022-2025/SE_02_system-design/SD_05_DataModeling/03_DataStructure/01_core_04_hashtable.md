# hash indexes
## Reference
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/db-indexing#hash-indexes

---
## Overview
- hash indexes serve a specialized purpose: they excel at **exact-match queries** 
  - B-trees perform nearly as well for exact matches while supporting range queries and sorting
- simply a persistent **hashmap implementation**| Hash collisions
- trading flexibility for **super-fast O(1)** lookups.

## Working

```
buckets[hash("alice@example.com")] -> [ptr to page 1]
buckets[hash("bob@example.com")]   -> [ptr to page 2]
```

```mermaid
graph LR
    subgraph Inputs ["Input Keys"]
        direction LR
        K1["john@test.com"]
        K2["tim@test.com"]
    end

    subgraph HashLayer ["Hash Function"]
        HF["<b>Hash<br>Function</b>"]
    end

    subgraph IndexMap ["Hash Index Table (Disk / Memory)"]
        direction LR
        R1["<b>Key:</b> d5f43...  |  <b>Value:</b> [ Pointer &cir; ]"]
        R2["<b>Key:</b> 2e2bd...  |  <b>Value:</b> [ Pointer &cir; ]"]
        R3["<b>Key:</b> --------  |  <b>Value:</b> [ Pointer &cir; ]"]
        R4["<b>Key:</b> --------  |  <b>Value:</b> [ Pointer &cir; ]"]
    end

    subgraph DiskTargets ["Database Pages on Disk"]
        direction LR
        DP_Tim[("Disk page with full<br>record of <b>tim</b>")]
        DP_John[("Disk page with full<br>record of <b>john</b>")]
    end

%% Flow connections
    K1 --> HF
    K2 --> HF

    HF -->|"hash(john@test.com)"| R2
    HF -->|"hash(tim@test.com)"| R1

    R1 -->|"Direct Page Pointer"| DP_Tim
    R2 -->|"Direct Page Pointer"| DP_John

```

---
## Real-World Examples
- in-memory databases. Redis
- Despite their speed for exact matches, hash indexes are relatively rare in practice 👈

> **B-trees** are usually the better choice due:
> - to their efficient handling of disk I/O patterns.
> - perform nearly as well for exact matches while supporting range queries and sorting

---
## Interview
**consider hash indexes when:**
- You need the **absolute fastest possible exact-match** lookups
- You'll **never need range queries or sorting**
- You have **plenty of memory** (hash indexes tend to be larger than B-trees)
