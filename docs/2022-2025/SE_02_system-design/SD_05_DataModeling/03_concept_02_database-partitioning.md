# Database partitioning
## reference
- https://www.youtube.com/watch?v=oJj-pltxBUM&ab_channel=High-PerformanceProgramming - intro
- https://www.youtube.com/watch?v=VcTPmEJeKM4&ab_channel=AWSEvents - aws rds sharding

---
## Overview
- Database partitioning splits a large dataset into smaller parts called partitions.
```mermaid
flowchart LR
    A[Application] --> R[Partitioning Rule]

    R --> P1[(Partition 1)]
    R --> P2[(Partition 2)]
    R --> P3[(Partition 3)]

    P1 --> D1[Users 1-1000]
    P2 --> D2[Users 1001-2000]
    P3 --> D3[Users 2001-3000]

    style R fill:#f4b183,stroke:#333
    style P1 fill:#9dc3e6,stroke:#333
    style P2 fill:#9dc3e6,stroke:#333
    style P3 fill:#9dc3e6,stroke:#333
```
## Partitioning type : split method
| Partitioning type           | Split method             | Example                                                            |
| --------------------------- | ------------------------ | ------------------------------------------------------------------ |
| **Horizontal partitioning** | Split by rows            | Users 1–1M on DB1, users 1M–2M on DB2                              |
| **Vertical partitioning**   | Split by columns         | User profile columns in one table, large bio/image data in another |
| **Functional partitioning** | Split by business domain | Users DB, Orders DB, Payments DB                                   |

```mermaid
flowchart TD
    DB[(Large Database)]

    DB --> H[Horizontal Partitioning<br/>Split rows]
    DB --> V[Vertical Partitioning<br/>Split columns]
    DB --> F[Functional Partitioning<br/>Split by domain]

    H --> H1[(Partition 1<br/>Rows 1-1000)]
    H --> H2[(Partition 2<br/>Rows 1001-2000)]

    V --> V1[(User Core<br/>id, name, email)]
    V --> V2[(User Details<br/>bio, image, preferences)]

    F --> F1[(User Database)]
    F --> F2[(Order Database)]
    F --> F3[(Payment Database)]

    style H fill:#f4b183,stroke:#333
    style V fill:#9dc3e6,stroke:#333
    style F fill:#a9d18e,stroke:#333
```
## Partitioning type: Data split
| Partitioning type               | How data is split | Example               |
| ------------------ | ----------------- | --------------------- |
| Range partitioning | By value range    | Dates, user IDs       |
| Hash partitioning  | By hash of key    | `hash(user_id) % 4`   |
| List partitioning  | By fixed category | Country or region     |
| Time partitioning  | By time window    | Daily or monthly logs |

```mermaid
flowchart LR
    T[Range partitioning]
    Q[Incoming record<br/>partition key = event_date] --> R{Date range}
    R -->|2024-11-01 to 2024-11-30| P1["Nov 2024<br/>112,308 rows<br/>129.0 MB<br/>ACTIVE"]
    R -->|2024-12-01 to 2024-12-31| P2["Dec 2024<br/>142,551 rows<br/>203.2 MB<br/>ACTIVE"]
    R -->|2025-01-01 to 2025-01-31| P3["Jan 2025<br/>101,912 rows<br/>536.8 MB<br/>ACTIVE"]
    R -->|2025-02-01 to 2025-02-28| P4["Feb 2025<br/>6,596 rows<br/>36.6 MB<br/>ACTIVE"]
    style R fill:#f4b183,stroke:#333
    style T fill:transparent,stroke:none,color:blue

```
---
## Benefits
| Benefit                 | Explanation                                                                         |
| ----------------------- | ----------------------------------------------------------------------------------- |
| **Scalability**         | Data is distributed across multiple partitions or nodes, allowing horizontal growth |
| **Faster Queries**      | Partition pruning scans only the relevant partition                                 |
| **Parallel Processing** | Multiple partitions can be processed simultaneously                                 |
| **Easy Maintenance**    | Old partitions can be archived, detached, or deleted independently                  |

```mermaid
flowchart LR
    DB[(Large Table)] --> P1[(Partition 1)]
    DB --> P2[(Partition 2)]
    DB --> P3[(Partition 3)]

    Q[Query] --> R{Relevant partitions}
    R --> P2
    R --> P3

    P1 --> M1[Archive or delete independently]
    P2 --> X[Parallel processing]
    P3 --> X

    style DB fill:#f4b183,stroke:#333
    style R fill:#fff2cc,stroke:#333
    style X fill:#a9d18e,stroke:#333
```
---
## Indexes on partition
### local
- Pros: fast partition queries, easier maintenance.
- Cons: cross-partition searches may need multiple index scans.
```mermaid
flowchart TD
    Q1[Query: January orders] --> P1[January Partition]
    P1 --> I1[Local Index<br/>user_id, order_date]

    P2[February Partition] --> I2[Local Index]
    P3[March Partition] --> I3[Local Index]

    style I1 fill:#d9eaff,stroke:#7b4ce2
    style I2 fill:#d9eaff,stroke:#7b4ce2
    style I3 fill:#d9eaff,stroke:#7b4ce2
```
### global
- Pros: fast global search and one lookup path.
- Cons: expensive updates and harder partition maintenance.
```mermaid
flowchart TD
    Q[Query: Find user_id across all months] --> GI[Global Index]

    GI --> P1[January Partition]
    GI --> P2[February Partition]
    GI --> P3[March Partition]

    style GI fill:#f8d7ec,stroke:#d63384
```
## Partition Skew — Uneven Distribution
- Partition skew happens when some partitions hold much more data or traffic than others.
- choosing Bad partition key 
```mermaid
flowchart LR
    subgraph Balanced["Balanced Distribution"]
        P1["P1<br/>250K rows"]
        P2["P2<br/>250K rows"]
        P3["P3<br/>250K rows"]
        P4["P4<br/>250K rows"]
    end

    subgraph Skewed["Skewed Distribution"]
        S1["P1<br/>50K rows"]
        S2["P2<br/>100K rows"]
        S3["P3<br/>300K rows"]
        S4["P4 🔥<br/>550K rows"]
    end
```
| Problem                  | Impact                                              |
| ------------------------ | --------------------------------------------------- |
| Hot partition            | One node becomes overloaded                         |
| Uneven storage           | Some nodes fill faster                              |
| Higher latency           | Requests wait on the busiest partition              |
| Poor scalability         | Adding capacity may not fix the hotspot             |
| Inconsistent performance | Queries are fast on some partitions, slow on others |

---
## Example
### hash partition
```sql
CREATE TABLE users (
    user_id INT,
    username VARCHAR(50)
) PARTITION BY HASH (user_id);

-- Partition 0: Stores rows where `hash(user_id) % 4 == 0`
CREATE TABLE users_p1 PARTITION OF users
    FOR VALUES WITH (MODULUS 4, REMAINDER 0);

-- Partition 3: Stores rows where `hash(user_id) % 4 == 3`
CREATE TABLE users_p2 PARTITION OF users
    FOR VALUES WITH (MODULUS 4, REMAINDER 3);
```
### Time-Based Partitioning
```sql
-- Parent table (logical)
  CREATE TABLE sales (
  id SERIAL,
  sale_date DATE,
  customer_id INT,
  amount DECIMAL(10,2)
  ) PARTITION BY RANGE (sale_date);

-- Yearly partitions
CREATE TABLE sales_2023 PARTITION OF sales
FOR VALUES FROM ('2023-01-01') TO ('2024-01-01');

CREATE TABLE sales_2024 PARTITION OF sales
FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');

... manually create more in future...

-- ===== Automatic Partition Creation =====

-- PostgreSQL example: Function to create next month's partition
CREATE OR REPLACE FUNCTION create_next_month_partition()
RETURNS TRIGGER AS $$
BEGIN
    EXECUTE format(
        'CREATE TABLE IF NOT EXISTS sales_%s PARTITION OF sales '
        'FOR VALUES FROM (%L) TO (%L)',
        to_char(NEW.sale_date, 'YYYY_MM'),
        date_trunc('month', NEW.sale_date),
        date_trunc('month', NEW.sale_date) + INTERVAL '1 month'
    );
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Trigger to run before INSERT
CREATE TRIGGER trg_sales_partition
BEFORE INSERT ON sales
FOR EACH ROW EXECUTE FUNCTION create_next_month_partition();

-- option-2: pg_cron
```