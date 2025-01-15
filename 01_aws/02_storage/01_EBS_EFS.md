- https://chatgpt.com/c/677dbc0a-3414-800d-8960-b0d969c9ffda
  - ebs,efs,Fxs,snowball
---
# Storage
- check these 3 aspects:
  - `size` (capacity)
  - `iops` 
    - read iops
    - write iops
  - `throughput` (MB/s)

--- 
## A. EC2 instant-store
### Intro
- **better Read/write iops** :smile:
  - high-performance hardware disk
  - depends on ec2-i family type.
  - ![img.png](../99_img/dva/storage/01/img99.png)
- risk of data loss if h/w fails 
- **manual backup**
- volume size is **fixed** 
  - determined by the EC2 instance type.
- fact : AMIs do not preserve instance store data :point_left:
- fixed to host machine
  - cannot be detached or reattached
- can be used as boot volume :point_left: not preferred
---
## B. EBS
![img.png](../99_img/dva/storage/01/img.png)
### 1. Intro
- **AZ bounded** :point_left:
- Have **volumes**
- **network drive** (bit latency, same az) + **limited performance**
- can be attach/dettach to ec2-i
- persist data, even after their termination
- only be mounted to **one instance** at a time. multiple volumes can be attached. `1-2-M`
- **deleteOnTermination** 
  - root volume - true
  - additional ebs volume - false
  
### 2. EBS: snapshot
- `point in time` snapshot.
  - no need to detach volumn while taking snapshot, but recommended.
- **cross az/region restore** :point_left:
  - ![img_1.png](../99_img/dva/storage/01/img_1.png)
- Build an AMI, will also create EBS snapshots :point_left:

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
  - **io2** 
    - max -->  `16TB | 64k iops | 1000 MB/s`
  - **io3** 
    - max -->  `64TB | 256k iops | 4000 MB/s`
    - supports multi attach :point_left:
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
---
## C. EFS (regional)
### Intro
- **high availability** Managed NFS (network file system)
- protocol    : **NFSv4**
- file system : **POXIS**
- **3x times expensive** than EBS(gp2), because:
  - no capacity planning
    - auto-Scale in Size(PB) 
    - auto/manual adjust performance.
  - supports 
    - multi-AZ (Regional)  :point_left:
    - single AZ
    - ![img.png](img.png)
    - attach to multiple EC2 ( **Linux based AMI** only) :point_left:
  - high performance 
    - Read - `3 GB / s`
    - Write - `1 GB / s`

- **use case**
  - content management, web serving, data sharing, Wordpress, big data, media processing.
    
### storage class
- **lifecycle policy** to move between 
  - **standard** 
  - **Infrequent-Access** (after n1 days) 
  - **Archive** ((after n2 days)) 50%
- same like in s3.

---
### EFS Throughput Modes
- **Bursting Throughput** ( default)
  - throughput scales with file system size

- **elastic Throughput**
  - throughput scale regardless of size
  - auto-scale with the best performance. (R/recommended)

- **provisioned Throughput**
  - manually configure throughput.
  - If your workloads require even higher and consistent throughput
  - allows you to specify the throughput you need, independent of the amount of data stored.

| **Category**          | **Option**              | **Description**                                                                                  | **Best For**                           |
|------------------------|-------------------------|--------------------------------------------------------------------------------------------------|-----------------------------------------|
| **Performance Modes**  | **General Purpose**     | Low latency, limited concurrency, fixed throughput per client.                                  | Latency-sensitive workloads.            |
|                        | **Max I/O**            | Higher latency, massive concurrency, elastic throughput scaling.                                | High-concurrency workloads.             |
| **Throughput Modes**   | **Bursting Throughput** | Default mode; scales with file system size.                                                     | Variable workloads with spiky demand.   |
|                        | **Provisioned**        | Fixed throughput, independent of file system size.                                              | Consistent high-throughput workloads.   |
|                        | **Elastic Throughput** | Automatically scales throughput to match workload needs (Enhanced Mode).                       | Unpredictable or spiky workloads.       |


---
### EFS Performance Mode
- **general-purpose** ( default)
  - **low-latency** operations :)
  - lower throughput
  - and is not ideal for highly parallelized/concurrent big data processing tasks.
  
- **max I/O** 
  - Highly `parallelized` applications and **big data workloads** that require higher throughput.
  -  supports thousands of `concurrent` connections and higher I/O operations.
  -  **higher latencies**
  - higher throughput

| **Performance Mode** | **Latency**      | **Throughput Scaling**     | **Concurrency**               | **Best For**                  |
|-----------------------|------------------|-----------------------------|--------------------------------|--------------------------------|
| **General Purpose**   | Low             | Fixed limits per instance   | Few to hundreds of clients     | Latency-sensitive applications |
| **Max I/O**           | Slightly higher | Elastic with client numbers | Thousands of clients           | High-concurrency workloads     |

---  
### Security
- choose VPC/subnet >  add `sg`
- Encryption at rest using `KMS` + enable/disable automatic backup

### hands on
  ```
  - Create EFS `efs-1` + efs-sg-1
  - Ec2-i1 and i2 : launch instance > attach efs-1
  - choose mount location : /mnt/efs/fs1
  - aws automatically adds sg
      - ec2-i1-sg : inbound rule : Type:NFS, protocol:TCP, port:2049, source:efs-sg-1
      - similary outbound rule.
  - ssh to ec2-i1 and echo "hello" >  /mnt/efs/fs1/hello.txt
  - ssh to ec2-i2 and cat  /mnt/efs/fs1/hello.txt
  ```
---
## Extra
- ![img.png](../99_img/ec2/img_4.png)
- ![img_1.png](../99_img/ec2/img_3.png)

- price compare
```yaml
Storage Class	            Price (per GB)  

EBS General Purpose (gp3)	$0.08
EBS General Purpose (gp2)	$0.10
EBS Provisioned IOPS (io1)	$0.125
EBS Provisioned IOPS (io2)	$0.125
EBS Magnetic (standard)	    $0.05

=== SSD 12 cent , for HDD 5 cent

EFS Standard	            $0.30
EFS Standard-IA	            $0.025
EFS One Zone	            $0.16
EFS One Zone-IA	            $0.0133

=== standard 30 cent , IA - 2 cent
```