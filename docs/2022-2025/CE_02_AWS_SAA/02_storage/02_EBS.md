# EBS
![img.png](../99_img/dva/storage/01/img.png)
### 1. Intro
- **AZ bounded** 👈
- Have **volumes**
- **network drive** (bit latency, same az) + **limited performance**
- can be attach/detach to ec2-i
- persist data, even after their termination
- only be mounted to **one instance** at a time. multiple volumes can be attached. `1-2-M`
- **deleteOnTermination** 
  - root volume - `true`
    - if disable it on running app - how ? console or api/cli**
  - additional ebs volume - `false`
- use `e2label` **command** to change label name
  - scenario:
  ```text
    - ec2-1 root volume > snapshot-1 > created volume-2 > attached to ec2-2 as additional volume 
    - vol-1 is root vol for ec2-2
    - re-b0ot ec2-2, it will boot from volume-2, rather than vol-1
  ```
  
### 2. EBS: snapshot
- Snapshots are **incremental backups** :dart:
  - which means that only the blocks on the device that have changed after your most recent snapshot are saved.
- `point in time` snapshot.
  - no need to detach volumn while taking snapshot, but recommended.
- **cross az/region restore** 👈
  - ![img_1.png](../99_img/dva/storage/01/img_1.png)
- Build an AMI, will also create EBS snapshots 👈

- store snapshot to **archive tier**
  - 75% cheaper, save cost
  - but restore time 24-72 hrs 
  - ![img_3.png](../99_img/dva/storage/01/img_3.png)
  
- accidental delete 
  - setup **recycle bin** with retention policy (1 day to 1 year)
  - ![img_2.png](../99_img/dva/storage/01/img_2.png)

- **Fast Snapshot Restore** (FSR)  

### 3. Security
- encrypt at rest, both - **volume and snapshot** using KMS

### 4. Types :books:
- **General Purpose SSD**
  - **gp2**  
    - size defines iops --> `3 iops / GB`    
    - max -->  `16TB | 3K iops | 125 MB/s`
  - **gp3** 
    - max -->  `16TB | 16k iops | 1000 MB/s`
    - System boot volumes, Virtual desktops, Development and test environments
    - Balanced price/performance for a wide variety of workloads
    
- **Provisioned IOPS SSD**
  - **io1** 
    - max -->  `16TB | 64k iops | 1000 MB/s`
  - **io2** 
    - max -->  `64TB | 256k iops | 4000 MB/s`
    - supports multi attach 👈
      - max - 16 ec2-i
    - databases workloads

- **HDD**
  - dont use as boot volume :dart:
  - **HDD**  / Throughput Optimized HDD / `st1`
    - max -->  `? | 500 iops | max-500 MB/s`
    - Big Data, Data Warehouses, Log Processing
    -  
  
  - **cold HDD**  / `sc1`
    - max --> `? | 250 iops | max-250 MB/s`
    - data that is infrequently accessed

```

General Purpose SSD (gp3):
- IOPS: Up to 16,000 IOPS.
- Throughput: Up to 1,000 MB/s.
- Use Case: Balanced price/performance for a wide variety of workloads.

Provisioned IOPS SSD (io2/io2 Block Express):
- IOPS: Up to 64,000 IOPS (io2), up to 256,000 IOPS (io2 Block Express).
- Throughput: Up to 1,000 MB/s (io2), up to 4,000 MB/s (io2 Block Express).
- Use Case: Critical applications requiring high performance and reliability.

Throughput Optimized HDD (st1):
- IOPS: Up to 500 IOPS.
- Throughput: Up to 500 MB/s.
- Use Case: Big data, data warehouses, and log processing.

Cold HDD (sc1):
- IOPS: Up to 250 IOPS.
- Throughput: Up to 250 MB/s.
- Use Case: Infrequently accessed data with lower cost requirements.
```


## Exam 🎯    
EBS volume : automate:
- every 12 hr screenshot
- delete older screenshot
- options:
  - use event rule schedular > lambda > ...
  - use Amazon **Data Lifecycle manager** **