# B tree | B+ tree
## Overview
- used in relational DB
```mermaid
flowchart TD
    R[Root] --> L1[Internal Node]
    R --> L2[Internal Node]
    L1 --> A[Leaf: A-D]
    L1 --> B[Leaf: E-H]
    L2 --> C[Leaf: I-P]
    L2 --> D[Leaf: Q-Z]

    style R fill:#1f4e78,color:#fff
    style L1 fill:#2e75b6,color:#fff
    style L2 fill:#2e75b6,color:#fff
    style A fill:#9dc3e6,color:#000
    style B fill:#9dc3e6,color:#000
    style C fill:#9dc3e6,color:#000
    style D fill:#9dc3e6,color:#000
```

## Read behavior (fast)
- Root → Internal Node → Leaf Page → Record
- Reads are efficient because the database can quickly navigate to the required page.

## Write behavior (Slow)
A write may require:
- Finding the target page
- Updating an existing disk page
- Splitting a page when it becomes full
- Updating indexes 👈
- Writing to the transaction log
- > Therefore, writes can involve random disk I/O.