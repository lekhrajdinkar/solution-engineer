# KDA - Kinesis Data Analytics (serverless)
- Fully managed
- Automatic scaling

---
## A. Kinesis Data Analytics (SQL Application) / legacy
- SQL application --> run on KDA -->  real time analysis/**process** --> stream
- Source : `KDS/KDF` + also reference data from S3
- ![img_2.png](../99_img/moreSrv/analytics-2/img_2.png)

---
## B. Kinesis Data Analytics (Flink Application) / preferred
### 1. Intro
- new name : **Managed service for apache Flink**
- flink application (more advance than SQL) --> run on KDA -->  analysis/**process** --> Stream
- to **transform and analyze streaming data in real-time**  
-  provides :
  - storage - 50 GB per Kinesis Processing Unit (`KPU`).
    - ![img_3.png](../99_img/moreSrv/analytics-2/img_3.png)
    
### 2. most common use cases :dart:
- **streaming ETL**
- continuous **metric generation**
- **interactive querying** of data streams. 
- responsive **real-time analytics**
  - log analytics,
  - click stream analytics
  - Internet of Things (IoT)
  - ad tech
  - gaming
  - ...

## 3. Pricing
- no minimum fee or setup cost, 
- and you only pay for the resources your streaming applications consume