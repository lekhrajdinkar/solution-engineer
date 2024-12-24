# EMR ( Elastic Map Reduce)

## 1. Intro
- creates `Hadoop clusters`
  - **Master Node**: 
    - Manage the cluster
    - coordinate
    - manage health
  - **Core Node**: 
    - long running tasks 
    - store data
  - **Task Node** (optional): 
    - Just to run tasks 
    - usually Spot instance
- set up : Apache Spark, HBase, Presto, Flink
  
## 2. Use cases
- data processing 
- ML
- web-indexing`
- big-data/hadoop