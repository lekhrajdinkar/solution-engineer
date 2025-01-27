- **Amazon Aurora `provisioned`** vs **Amazon Aurora `Serverless`**

---
# Aurora serverless (cluster DB)
## A. Intro
- engine : `Postgres` 3x  and `MySQL` 5x
- serverless,
- **no capacity planning**
- scale to **128TB** per db instance :point_left:
- OLTP | rdbms
- cluster  is **fault tolerance** by design:
  - cluster spans multiple AZ in region.
  - 1 primary + read replica(assign priority, can change at any time.)
  - if no reader, the primary itself re-created.

## B. DR
- **RPO** : `1 sec` | **RTP** `< 1 min` :dart:
- aurora has no **stand-by instance** thing :point_left:
  - has read replicas only in Aurora cluster, only replica can promote as primary, during DR.
  - read replica has 2 purpose : reader endpoint + availability
- self-healing,
- continuous s3 backup,
- PITR

## C. Advantages
- include RDS adv.

###  1 Global Aurora database
- cross `region` replicas in `less than a sec`. | single Database spans over multiple **region**.
- 1 Primary Region (read / write)
- Up to 5 secondary (read-only) in each region, `replication` lag is less than `1 second` :point_left:
  - Up to 16 Read Replicas per secondary region
  - **RPO**: less than second
  - **RTO**: less than a minute
- ![img_3.png](../99_img/db/img_3.png)
- provides more comprehensive failover capabilities :dart:
  - **Managed planned failover**
  - **Unplanned failover ("detach and promote")** 
  
###  2 integration **ML service** 
- `SageMaker` and `Comprehend`
- fraud detection, ads targeting, sentiment analysis, product recommendations

### 3 Auto-scaling (storage and compute are separate)
- `storage` scaling :: EBS volume - `10 GB to 128 TB` . /64TB?
- `compute` Instance :: type( eg: d.r3.large,etc), --RAM++, --cpu++.
- `Read replicas`:  (built-in, dont need to create CW + ASG, auto happens bts)
  - can add, CW metric --> triggers --> auto up/down read replicas
   
### 4 performance
- AWS cloud optimized and claim `3x` Performance improvement (on Postgres)
- master + `6-15` Read Replica, with **fast replication**
- uses **cloning**
  - faster than backup > restore
  - uses `copy-on-write` - use same volume + for new changes additional storage allocated and data copied to it.
  
### 5 Availability (cluster arch)
- `6 copies` for data access 3 AZ : `cluster` ( with reader and writer endpoint)
- instant fail-over (<30s) + `self healing` from peer2peer replication.
- ![img.png](../99_img/db/img.png)
- ![img_2.png](../99_img/db/img_2.png)

### 6 more
  - Isolation and `security`
  - `Industry compliance`
  - Push-button `scaling`  
  - Advanced Monitoring
  - Routine `Maintenance` + Automated `Patching` with Zero Downtime
  - Backtrack: `restore` data at any point of time without using backups. (earliest :`5 mim ago`)

---
## B pricing
- 20% extra cost than RDS.

---
## C. demo
```
- select engine
- select versions (so many available)
- template - prod (allow to configure everything)
    - admis + password
    - max i/o or standard
    - ec2 instance or serverless
    - choose : avialability - replica,etc
    - vpc, subnet, Ibv4
    - public access
    - sg
    - port
    - authentication
    - db name
    - ...

=== READY ====

- add more read replica
- add cross az replica 
- add region (global database)
- horizontal scaling policy (trigger : metric -CPU usage)
    - max 15 and min 1
    
```