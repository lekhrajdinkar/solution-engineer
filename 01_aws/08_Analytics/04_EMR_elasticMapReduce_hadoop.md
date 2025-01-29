# EMR ( Elastic Map Reduce)
-  **cloud big data platform** for processing vast amounts of data using open source tools such as :
```
  - Apache Spark, 
  - Apache Hive, 
  - Apache HBase, 
  - Apache Flink, 
  - Apache Hudi
  - Presto
```
-  **3x faster**
-  Petabyte-scale analysis at less than **half of the cost** of traditional on-premises solutions

## 1. Intro

- creates **Hadoop/spark clusters**
  - **Master Node**: 
    - Manage the cluster
    - coordinate
    - manage health
  - **Core Node**: 
    - long running tasks 
    - store data
  - **Task Node** (optional, for compute): :point_left:
    - Just to run tasks 
    - usually Spot instance

- For short-running jobs 
  - you can spin up and spin down clusters and **pay per second** for the instances used.
- For long-running workloads, 
  - you can create **highly available clusters** that automatically **scale** to meet demand.
  
## 2. Use cases
- data processing 
- ML
- web-indexing`
- big-data/hadoop