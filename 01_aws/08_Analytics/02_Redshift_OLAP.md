# Redshift (Not serverless)

## 1. Intro
- **OLAP** (online **analytic** processing) database
- **data warehousing** 
- **Redshift's internal storage** :point_left:
  - does NOT have "tiers" of storage classes like Amazon S3
- load PB of data and perform query/analysis
- like athena, Integrated with **aws:glue** (crawler > catalog)
- analytic query **result** goes to:
  - S3 
  - Amazon QuickSight (dashboard) 
  - `Tableau`
  - ...

--- 
## 2. Data Source
- ![img_2.png](../99_img/moreSrv/redshift/img_2.png)
- load data from below  sources
  - **KDF**
  - s3
  - program(Java:SDK): **in-batches** 
  - AWS glue/ETL
  - ...

---
## 3. Cluster architecture
- Cluster => **leader Node** => **compute Node** => 1000s of **spectrum**
  - performance `(10x)`, than other data warehouses and athena
  - leader node receives queries from client applications, parses the queries, and develops query execution plans
  - coordinates the parallel execution of these plans with the compute nodes
  - Aggregates the intermediate results
- **parallel query engine** 
  - run complex SQL
  - faster-joins
  - faster-aggregation
  - uses indexes + Columnar storage

![img_3.png](../99_img/moreSrv/redshift/img_3.png)

---
## 4. cross-referencing :point_left:
- Analysts can use Redshift SQL queries to access both :
  - **Redshift tables** (`recent` data)  / hot data
  - **data in S3** (`historical` data) **without moving data into redshift** :point_left:
    -  **Spectrum**, allows you to run queries on data stored in Amazon S3,
    - without having to move that data into your Redshift cluster.

---
## 5. DR
- **single-AZ** (by default)
- **Multi-AZ**  replication 
- **cross region-replication** - explicitly enable
  - incremental-snapshot(only new change), in every 8 hr. 
    - retention: 35 days.
    - stored in s3.
  - restore snapshot/s into new region : **manually/automate**.
  - ![img_1.png](../99_img/moreSrv/redshift/img_1.png)

---
## Exam :dart:
- App 
  - less than year older data --> **redshift** --> `analytic-report-1`
  - older than year --> **s3**
  - `analytic-report-2`,  reference from --> s3 + redshift
  - how to cross-reference s3 :point_left:
  - ![img.png](../99_img/practice-test-01/exam-43.png)
- Amazon Redshift **AQUA** (Advanced Query Accelerator) 
  - distributed query acceleration layer designed to speed up certain types of queries in Amazon Redshift, particularly complex analytical queries.
  - boost bt 10x
  - resolves network bandwidth + cpu processing bottleneck
- **Datashare** feature
  - **Cross-Account** Data Sharing for Amazon Redshift
  - https://aws.amazon.com/blogs/aws/cross-account-data-sharing-for-amazon-redshift/
