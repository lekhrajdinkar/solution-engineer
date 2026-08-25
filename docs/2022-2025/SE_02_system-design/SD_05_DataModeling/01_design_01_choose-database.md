# Data Modeling
- https://www.youtube.com/watch?v=6bZdMZb8xI8 | SQL
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2190592401 | keyValue store
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/data-modeling

---
## Overview
### **Data Modeling**
- process of defining how your application’s data is **structured, stored, and related.**
- not expected to normalize everything or produce a complete schema diagram
- expected to design something clear, functional, and aligned with your system’s requirements

### **Delivery phase**
- During **requirements gathering** step
  - identify your core entities.
  - These usually map 1:1 with tables or collections and form the backbone of your schema. 
- Later, in the **High-Level Design** step, 
  - you'll sketch a basic schema alongside your database component.

> ⚠️ A sloppy data model can lead to painful issues later. Resist choosing exotic database types.

```
SQL
 └── Relational

NoSQL
 ├── Key-Value       → Redis, DynamoDB
 ├── Document        → MongoDB, DynamoDB
 ├── Wide-Column     → Cassandra, HBase
 └── Graph           → Neo4j, Neptune

Specialized
 ├── Time-Series
 ├── Search
 ├── Vector
 ├── Spatial
 └── Ledger
```

| SQL                                        | NO-SQL                                            |
|--------------------------------------------|---------------------------------------------------|
| Data has clear relationships               | Data is accessed mainly by a unique key           |
|                                            | data model is not **hierarchical**                |
| Complex joins are required                 | Joins are not required                            |
| Strong ACID transactions matter            | Very high throughput and low latency matter       |
| Schema is structured and stable            | Schema is flexible or values are opaque           |
| Complex filtering and reporting are needed | Simple `GET`, `PUT`, `DELETE` operations dominate |
| Example: banking, orders, inventory        | Example: sessions, carts, cache, user preferences |

---
## A1. Relational Databases (SQL)
> default unless your requirements clearly signal a specialized model | stick with PostgreSQL.

✔️**Structure**
```
Database
 └── Table
      ├── Primary Key
      ├── Column A
      ├── Column B
      └── Column C
```
✔️**best for:**
- Strong consistency
- Relationships and Joins
- ACID transactions
- Complex queries

---
> **NOSql DB** : no relation | no schema |  no querying language | simple operations like get, put, and delete.

## B1. Document DB
> Data → JSON-like documents

✔️**overview**
- data modeling becomes more about **nesting and embedding related information** within documents rather than normalizing across tables.
- use when, different records have **vastly different structures.**
- store data as JSON-like documents with flexible schemas
- eliminates joins but means updating a post requires finding and **modifying the entire user document**
- **denormalize more aggressively** | meaning **duplicates**

✔️**Structure**:
```
Collection
 └── Document
      ├── _id
      ├── field A
      ├── field B
      └── nested objects / arrays
```

✔️**Example**
- MongoDB
- dynamoDb
- Firestore
- CouchDB

✔️**Best for:**
- Flexible schema
- Nested data
- high read performance, since no joins
- rapidly changing data structures. 👈

✔️**trade off:**
-  storage space 
- update complexity

---
## B2. key-value Store
✔️**Example:**
- Redis and Memcached.
- DynamoDB

✔️**overview:**
-  **simple lookups** where you fetch values by exact key match.
- extremely fast but offer limited query capabilities | O(1) |  **performance**⭐
  - key -> `String` | hashed to memoryLoc
  - value ->` String, arrays, integer`
- you **can't join or query across relationships.**

✔️**Structure** 
```
- dictionary or map

Table
 ├── Key:value
 └── ...
 
Table (dyanamoDB , little richer)
 ├── Partition Key
 ├── Sort Key
 ├── Attribute A
 ├── Attribute B
 └── Attribute C
```

✔️**best for**: 
- scenario where you only need to look up data by a single identifier.
  - Caching
  - Sessions storage
- Very fast lookups
- Simple access patterns , no  complex question
- also good for **high-write scenarios**

✔️**tradeoff:**
- great for reads but terrible for consistency when data changes.

>  In practice, you'll often use **both together.** 
> - SQL as your source of truth 
> - with a key-value cache (like Redis) in front for hot data

---
## B3. wide column database
> Data → partitioned rows with flexible columns

✔️**structure**
- organize data into **column families** where rows can have different sets of columns
- **Column family/table** → defined ahead of time.
- **Column** → values are written with the row; schema can be flexible depending on the column.

```
Keyspace
 └── Table
      ├── Partition Key
      ├── Clustering Columns (Sort Key)
      └── Other Columns/Column Family
```

✔️**Example**
- HBase, Cassandra
- Modern/cloud-managed: Google Bigtable, Amazon KeySpaces

✔️**Best for:**
- High write throughput 
  - enormous write volumes, time-series data, or analytics workloads 
  - where you primarily **append data** and run aggregations.
  -  Think telemetry, event logging, or IoT sensor data.
  -  > Time becomes a first-class citizen in your modeling.
- Massive datasets |  Horizontal scaling |  Distributed systems

```
Relational DB
     ↓
"Tables" → Rows → Columns (schema)
     ↓
Strong schema + joins


Wide-column DB
     ↓
"Partition" → Rows → Columns (no schema)
     ↓
Distributed + query-driven schema
```
| Characteristic | Wide-column DB                               |
| -------------- |----------------------------------------------|
| Data model     | Rows + column families                       |
| Schema         | Flexible                                     |
| Scaling        | Horizontal , Usually highly distributed and Availability |
| Partitioning   | Usually by partition/row key                 |
| Best for       | Huge distributed datasets                    |
| Reads          | Designed around known access patterns        |


✔️**understand structure by Example**
```mermaid
graph LR
    DB["Wide-Column Database"]
    DB --> R1["Row Key: user_101"]
    DB --> R2["Row Key: user_102"]
    R1 --> PF1["Column Family: profile"]
    R1 --> PS1["Column Family: posts"]
    PF1 --> C11["name = John"]
    PF1 --> C12["email = john@example.com"]
    PS1 --> C13["post_1 = Hello World!"]
    PS1 --> C14["post_2 = My Second Post"]
    PS1 --> C15["post_3 = Just Appended!"]
    R2 --> PF2["Column Family: profile"]
    R2 --> PS2["Column Family: posts"]
    PF2 --> C21["name = Alice"]
    PF2 --> C22["age = 30"]
    PF2 --> C23["city = NYC"]
    PS2 --> C24["post_10 = New Arrival"]
    Write["⭐New Post / Log"] -->|"Append column"| C15

    classDef db fill:#f5f5f5,stroke:#333,stroke-width:2px,color:black
    classDef row fill:#e3f2fd,stroke:#1976d2,stroke-width:2px,color:black
    classDef family fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:black
    classDef col fill:#e8f5e9,stroke:#388e3c,stroke-width:1px,color:black
    classDef write fill:#fce4ec,stroke:#c2185b,stroke-width:2px,color:black
    class DB db
    class R1,R2 row
    class PF1,PF2,PS1,PS2 family
    class C11,C12,C13,C14,C15,C21,C22,C23,C24 col
    class Write write
```
---
## B4. Graph DB
> Data → nodes + relationships
> - optimizing for traversing relationships between entities.

```
John ──FRIEND_OF──> Alice
 │
 └──WORKS_AT──> Capital Group
```
✔️**Example:**
- Neo4j
- Amazon Neptune

```mermaid
graph LR
    subgraph GraphDB ["Graph Databases"]
        direction LR
        
        %% User Nodes
        U1(("User 1"))
        U2(("User 2"))
        
        %% Post Nodes
        P1((''My first<br>post''))
        P2((''Hello<br>world!''))
        P3((''Another<br>post''))
        
        %% Edges / Relationships
        U1 -->|"Posted"| P1
        U1 -->|"Posted"| P2
        U1 -->|"Liked"| P3
        U2 -->|"Posted"| P3
    end

    %% Node Styling to match the original image
    style U1 fill:#ffffff,stroke:#28a745,stroke-width:2.5px,color:#28a745
    style U2 fill:#ffffff,stroke:#28a745,stroke-width:2.5px,color:#28a745
    
    style P1 fill:#ffffff,stroke:#007bff,stroke-width:2.5px,color:#007bff
    style P2 fill:#ffffff,stroke:#007bff,stroke-width:2.5px,color:#007bff
    style P3 fill:#ffffff,stroke:#007bff,stroke-width:2.5px,color:#007bff
```
✔️**Overview**
- Built on a **graph data model**
    - where relationships between data points are of prime importance.
    - datasets with many billions of interconnections
- **PGQL**
    - Simplifies complex queries
    - and provides deeper insights into relationships with less effort
    - Excels at finding **shortest paths** between nodes

✔️**rdbms with lots of relation/s --> messy**

![img_2.png](../../../99_img/2026/02/07/01/img_2.png)

✔️**Best for:**

| Use case               | Why graph DB fits                      |
| ---------------------- | -------------------------------------- |
| Social networks        | Friends, followers, mutual connections |
| Recommendation systems | User → product → similar users         |
| Fraud detection        | Find suspicious relationship patterns  |
| Knowledge graphs       | Connect people, places, topics         |
| Network topology       | Servers, routers, dependencies         |
| Access control         | Users, roles, permissions              |

✔️**Trade off:**
- add unnecessary complexity.
- fact: Even "graph-heavy" companies like LinkedIn and Twitter use SQL for their core relationship data. 👈

---
## C1. Specialized

| Type            | Examples                   | Best for                      |
| --------------- | -------------------------- | ----------------------------- |
| **Time-Series** | InfluxDB, TimescaleDB      | Metrics, IoT, monitoring      |
| **Search**      | Elasticsearch, OpenSearch  | Full-text search              |
| **Vector**      | Pinecone, Milvus, pgvector | AI/RAG similarity search      |
| **Spatial**     | PostGIS                    | Geographic/location data      |
| **Ledger**      | Amazon QLDB                | Immutable transaction history |


### 1. Time Series
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
### 2. BLOB Store
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

### 3. vector

---

## Interview
Tip-1
- Don't focus too much on:
  - "Cassandra is wide-column, DynamoDB is key-value."
- Instead focus on:
  - Both are distributed NoSQL databases designed for predictable, high-scale access patterns.