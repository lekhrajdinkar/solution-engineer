# Athena (serverless)

## 1. Intro
- run **adhoc SQL** query serverless, at scale
- has integration with **AWS glue** bts.
  - athena call `glue data crawler`
  - crawler, crawls over above source/s and prepare 
    - **metadata**
    - **Glue Data Catalog**
  - Athena uses these while performing query.
- **query result** will go to:
  - **S3** 
  - **Amazon QuickSight** (dashboard)  
- ![img.png](../99_img/moreSrv/athena/img.png)  

---
## 2. pricing
- pay for data Scan : `$5 / TB`
- save cost
  - **compressed**  data
  - **columnar** data
  - S3:object.csv --> **aws-glue** --> parquet(columnar)
  - organize data in S3 like `/year/month/day/hr/...` then scan by date.
  
---
## 3. Sata Source
- can load data from below source into athena
- Athena will scan,query, analyze data using SQL


### 3.1 S3
- S3 object:csv,json,avro,parquet(columnar format), 
  - vpc-logs
  - elb-logs
  - cloudtrail
  
    
### 3.2 database
- on-prem/aws (relational/NoSQL) --> **aws-glue** --> parquet
  
### 3.3 kineses data steam
  
### 3.4 DataSource `Connector`
- AWS Lambda to run Federated Queries on RDS,CW,DynamoDB,etc
- ![img_1.png](../99_img/moreSrv/athena/img_1.png)
---
## 99. hands on
```
- bucket-1 >  enable s3-access log.
- copy location-1

athena:
    - create data_source-1
    - Query editor
        - run : create DataBase db1;
        - get query from internet, to DDL/DML to get se-access-log into tables.
        - edit query with location-1.
        - run query to perform ddl and dml, table created : bucket-1_logs
        - check L select * from database-1.bucket-1_logs limit 10.
        - query more standard sql and see report.

 more:
 - recent queries
 - saved queries - also encrypted result.   
```
