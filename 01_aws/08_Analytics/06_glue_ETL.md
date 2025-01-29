# Glue (Serverless , ETL)
## Intro
- AWS Glue is a fully managed extract, transform, and load (ETL) service 
- makes easy for customers, to prepare and load their data **for analytics**.
- **AWS Glue job** is meant to be used for batch ETL data processing.

---
## Glue Components
- Glue Data **catalog** : metadata
- Glue Data **Crawler** : scan source and create help to create metadata.
- Glue **Elastic Views**: virtual table.
- Glue **DataBrew**: clean and normalize data, using pre-built transformation
- Glue Job **Bookmarks** : prevent re-processing old data

![img_3.png](../99_img/moreSrv/analytic-1/img_3.png)

---
## Use case
- #1. transform data before loading to **redshift** data warehouse
  ![img_1.png](../99_img/moreSrv/analytic-1/img_1.png)

- #2. transform csv to **parquet** (columnar format,faster for analysis) --> for **athena**
  - very common
  - ![img_2.png](../99_img/moreSrv/analytic-1/img_2.png)

- #3. prepare data for analysis and load/store into **S3** as target.

---
## more
- **Glue Studio**: new GUI to create, run and monitor ETL jobs in Glue
- **Glue `Streaming` ETL** :
  - built on Apache Spark Streaming
  - compatible with 
    - Kinesis Data Streaming 
    - Kafka