## kickoff
- **Sharding vs Partitioning in RDBMS**
  - Partitioning splits a table into smaller segments within the **same** database server (by rows or columns) for performance and manageability.
  - Sharding distributes data **across multiple servers** (horizontal scaling) to handle large-scale workloads.

---
## A. RDBMS partitioning
- https://www.youtube.com/watch?v=oJj-pltxBUM&ab_channel=High-PerformanceProgramming - intro
- https://www.youtube.com/watch?v=VcTPmEJeKM4&ab_channel=AWSEvents - aws rds sharding
- https://chat.deepseek.com/a/chat/s/8483329c-7494-483f-bd9c-f296c81b084e
  - search for: RDBMS Partitioning: Types & Strategies with Simple Examples
```text
=== TOPICS asked in above deepseek chat =====
0. sharding vs partitioning in RDBMS.
1. Database - Sharding and partitioning in RDBMS 
2. Database - Sharding and partitioning in noSQL

3. No SQL DB - ACID 
4. RDBMS  - ACID

5. Blue-Green Partitioning - implementation approach rather than a PostgreSQL feature
    - Blue (production)
    - Green (with new partitioning scheme)
    Enables zero-downtime migration to partitioned tables. like in k8s
    
6 scenario1: 
table-1 create without partition. after one year millions of records inserted. can we add partition later.

7 scenario2: 
table-1 create with range partition. after one year millions of records inserted. 
can we add update partiton to hash type from  range type.

8 time-based partitioning if historical data grows rapidly. intQ
```
### Example
- hash partition
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
- Time-Based Partitioning
```
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
```
---
## B. RDBMS Sharding
- https://www.youtube.com/watch?v=be6PLMKKSto&ab_channel=Exponent

