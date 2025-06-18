## 1. AWS glue (ETL)
 - `Crawler` prepare Metadata and prepare `catalog` (help in data discovery)

## 2. QuickSight
- create interactive, ML powered, AI/BI `dashboard` (create user), share
- source (saas app, S3:CSV,etc ,DB )

## 3. Athena 
- uses glue crawler
- source(s3,KDS, DS-connectors) --> `serverless SQL run`,at scale --> result s3/QuickSight 
- `5$/TB scan cost`, save :
  - compress
  - formatted data : CSV, avro, parquet(using Aws glue) 
  - organise data in dataTime fashion.

## 4. RedShift (OLAP/data wareHouse )
- uses glue crawler, 10X faster
- Load PB of data in Cluster (master,worker with `spectrum`) from source(s3,KDS, prg, glue)
  - `|| sqs Run `
  - complex join and aggregation
- result goes to s3/QuickSight

## 5. OpenSearch
- search and Analysis for `Unstructured Data` from CW:log + stream(DynoDB,KDS).
- CW:Logs,KDF --> OpenSearch

## 6. EMR
- Run `Hadoop` Cluster (Master.Core.task Nodes)

