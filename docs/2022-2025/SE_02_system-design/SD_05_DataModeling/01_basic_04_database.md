# Data Modeling
- https://www.youtube.com/watch?v=6bZdMZb8xI8 | SQL
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2190592401 | keyValue store
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/data-modeling

> **NOSql DB** : no relation | no schema |  no querying language | simple operations like get, put, and delete.

## Overview
Data Modeling
- process of defining how your application’s data is **structured, stored, and related.**
- not expected to normalize everything or produce a complete schema diagram
- expected to design something clear, functional, and aligned with your system’s requirements

Delivery phase
- During **requirements gathering** step
  - identify your core entities.
  - These usually map 1:1 with tables or collections and form the backbone of your schema. 
- Later, in the **High-Level Design** step, 
  - you'll sketch a basic schema alongside your database component.

> ⚠️ A sloppy data model can lead to painful issues later. Resist choosing exotic database types.

---
## 1. Relational Databases (SQL)
- default unless your requirements clearly signal a specialized model | stick with PostgreSQL.
- provide ACID guarantees for transactions.
- SQL is great at handling complex queries.

---
## 2. Document DB

---
## 3. key-value Store
> `dynamoDB (disk, eventual-consitency)`, `redix (inMemory)`, `Cassandra`, `apache ZooKeeper`
- one of the popular/simple NoSQL DB type.
- Stores data as a collection of key-value pairs, similar to a dictionary or map data structure.
    - key -> String. hashed to memoryLoc
    - value -> String, arrays, integer
- **flexible** due to their lack of imposed structure
- **Direct key-based access** leads to extremely fast **performance**. 
  - O(1)
  - offering low latency 
  - high throughput

| Use RDBMS when                             | Use Key-Value DB when                             |
|--------------------------------------------|---------------------------------------------------|
| Data has clear relationships               | Data is accessed mainly by a unique key           |
|                                            | data model is not **hierarchical**                |
| Complex joins are required                 | Joins are not required                            |
| Strong ACID transactions matter            | Very high throughput and low latency matter       |
| Schema is structured and stable            | Schema is flexible or values are opaque           |
| Complex filtering and reporting are needed | Simple `GET`, `PUT`, `DELETE` operations dominate |
| Example: banking, orders, inventory        | Example: sessions, carts, cache, user preferences |



---
## 4. Specialized Store
### 1. BLOB Store
![img.png](../../../99_img/2026/02/07/01/img.png)

- Stores unstructured data like photos, audio, video files, and large text files.
- Behaves like key-value stores for data-access.
- **optimized for** :
  - Storing massive amounts of unstructured data 
  - storage classes option to save cost
  - highly scalable
  - reliable
  - available
- Examples: GCS,S3, Azure Blob Storage 
- [AWS_S3-1.md](../../CE_02_AWS_SAA/02_storage/05_01_S3.md)
- [AWS_S3-2.md](../../CE_02_AWS_SAA/02_storage/05_02_S3-advance.md)

---
### 2. Time Series
![img_1.png](../../../99_img/2026/02/07/01/img_1.png)

**optimized for:**
  - for storing time-stamped data 
  - or events that occur at specific intervals (e.g., every millisecond).
  - high-volume sequential write

Example: `InfluxDB, dataDog, AWS CloudWatch, Prometheus`.

```mermaid
flowchart LR
    S1[Application Metrics] --> TS[(Time-Series Database)]
    S2[IoT Sensors] --> TS
    S3[Stock Prices] --> TS
    S4[Server Logs] --> TS

    TS --> Q1[Query by Time Range]
    TS --> Q2[Aggregation<br/>Avg, Min, Max, Count]
    TS --> Q3[Trend and Anomaly Detection]

    Q1 --> D[Dashboard / Alerts]
    Q2 --> D
    Q3 --> D

    style TS fill:#f4b183,stroke:#333
    style D fill:#a9d18e,stroke:#333
```

---
### 3. Graph DB
> A graph database traverses relationships directly instead of performing many joins.
- Built on a **graph data model** 
  - where relationships between data points are of prime importance.
  - datasets with many billions of interconnections
- **PGQL**
  - Simplifies complex queries 
  - and provides deeper insights into relationships with less effort
  - Excels at finding **shortest paths** between nodes
- Example: `Neo4j`

| Use case               | Why graph DB fits                      |
| ---------------------- | -------------------------------------- |
| Social networks        | Friends, followers, mutual connections |
| Recommendation systems | User → product → similar users         |
| Fraud detection        | Find suspicious relationship patterns  |
| Knowledge graphs       | Connect people, places, topics         |
| Network topology       | Servers, routers, dependencies         |
| Access control         | Users, roles, permissions              |


rdbms with lots of relation/s --> messy
![img_2.png](../../../99_img/2026/02/07/01/img_2.png)