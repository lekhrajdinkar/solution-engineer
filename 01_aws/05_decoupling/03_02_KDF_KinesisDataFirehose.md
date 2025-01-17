# B. Kinesis Data Firehose `KDF`
## 1 Intro
- easiest way to load **streaming data** into data stores and analytics tools.
  - `capture`, 
  - `transform`, 
  - `load streaming data`
  - also : batch, compress, and encrypt :point_left:
- **NearRealTime `Data Delivery streams`**
  - set **buffer-interval** `0-900 Sec`
      - if buffer-interval == 0 --> `real time`
      - if buffer-interval == 1 to 900 sec --> `Near real time`
  - set **buffer-size**
    - min : 1 MB
    - default : 5 min
    - KDF only buffers data, does not have any its own permanent storage.
      - no replay capbilty,
    
- **serverless**
  - fully managed, 
  - no administration, 
  - auto scale
  
## 2 Source and Destinations
- **source**: KDS, KCL/SDK, K-agent, AWS IoT :dart:
- **destination** ( only 3 in aws side): s3, redshift/OLAP DB, openSearch
- fact to remember :point_left: :dart:
  - When KDS is configured as the source of a KDF stream, then:
    - Firehose’s **PutRecord** and **PutRecordBatch** operations are disabled 
    - thus, Kinesis-Agent cannot write to KDF Stream directly.
    
- ![img_3.png](../99_img/decouple/img_3.png)
  - optional lambda transformation + convert format to **parquet+ORC**
  - can put failed item into s3
  - write data in **batches**
  
### 2.1 AWS 
- only these 3:
  - `s3`
    - set compression + encryption
  - `redshift`
  - `OpenSearch`

### 2.2 3rd party
- splunk
- **datadog**
  - in ccgg, `cloudwatch::log/trace/metric` >>> `datadog`
  - doing with **sns** since not too much logs.
  - but if push everything, then go with KDF

### 2.3 http
- custom destination

---
## 3 source
- app(KPL/aws-sdk) === same for KDS
- KDS
---
# extra
![img.png](../99_img/dva/00/kds/img-kdf.png)