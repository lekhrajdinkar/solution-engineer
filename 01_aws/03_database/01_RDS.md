- https://chatgpt.com/c/675945a8-f8b8-800d-a789-e07e6db38e8d
--- 
- **AWS RDBMS offering** 
  - Option-1 : `on EC2`
    - Provision Ec2
    - install RDBMS and maintain it (os patching, security update, etc)
  - Option-2 : AWS `Aurora`
  - **Option-3** : AWS `RDS` 

---
# RDS 
- **regional**
- RDBMS | OLTP
- migrate to Aurora :point_left:
  - involves significant systems administration effort

## Advantages of RDS
### 2.1 fully managed (not serverless)
- RDS **does not** allow you to access the host OS of the database
  - use **RDS custom** :point_left:
  - allow some customization capabilities of underlying DB and **OS** (limited)  :dart:
  
- Automates **administrative tasks** such as database setup, patching, backups, and hardware provisioning
  - manually setup **auto-scale** :: CW>Alarm>Read-replicaScale.
  - auto **OS patching** :: just choose maintenance window
- **provision step** (capcity planning)
  - choose single-AZ(default) or mutli-az(enable, if needed)
  - choose **Supported engine**:
    - Postgres, MySQL, MariaDB, Oracle, Microsoft SQL Server, IBM DB2
    - `Aurora` (AWS Proprietary database, not open source)
  - choose **EBS volume type**: 
    - `gp2`  
    - `io1`
  - choose **RDS ec2 instance s** : compute family size
    - no access/ssh
    - But `RDS custom` allow to access it  only for `SQL server` and `oracle` DB.
    - First disable automation mode, take snapshot, then access it

### 2.2 High Availability
- Supports Multi-AZ (Availability Zone) deployments for fault tolerance.
  - **az-1**-db-1 <-- `SYNC` replication --> **az-2**-db-1
- Provides **automatic failover** in case of infrastructure or hardware failures.
- Multi-AZ keeps the same connection endpoint url. :point_left:


### 2.3 Scalability
#### scale `instance` (vertical)
- Scaling involves resizing instances
- which may require **downtime**.

#### **Read-replica instance** (horizontal)
- `not built-in` scaling, but can manually create CW:alarm and ASG
-  **metric**: conn count, cpu utilization, read traffic, etc --> CW alarm --> trigger ASG in/out
- or, manually edit and create read replication.
- each Read Replicas add **new endpoints URL**, with their own DNS name :point_left:
  - use case: 
    - analytics application
    - can run `Dashboard`, `Analytics` on read replicas.
- each Read Replica is associated with a **priority tier (0-15)**. :dart:
  - In the event of a failover, Amazon Aurora will promote the Read Replica that has the **highest priorit**y.
  - If two or more Aurora Replicas share the same priority, then Amazon RDS promotes the replica that is **largest in size**
  - eg:  tier-1 (16 terabytes), **tier-1 (32 terabytes)**, tier-10 (16 terabytes), tier-15 (16 terabytes), tier-15 (32 terabytes)

#### **Underlying Storage**
- define 
  - `thresold` ( maz-size in GB ) 
  - `trigger`  eg: free space <10%, space runs last 5min, etc.
- good for unpredictable workloads

### 2.4 performance
- Uses SSD-based storage.
- `write instance DB` + `Read replica/s` for improved read performance.
- **Up to 15 READ replica/s**
  - within AZ, or
  - cross-AZ, or
  - cross-region (paid replication)
- main-DB --> `A-SYNC replication (free within region)` --> Read Replicas

### 2.5 DR support
- **PITR** `Point in Time Restore` : Continuous backups and restore to specific timestamp
#### **option-1: Stand-by replica**  
  - manually enable Multi AZ-setup for DR. not built-in.
  - master DB (az-1) --> `SYNC replica/free` --> Stand-by DB (az-2) : no R/W operation
  - `Automatic fail-over` from master to standby in DR situation.
  - just single click, can go from Single-AZ to multi-AZ RDS
    - bts : Single-AZ RDS --> screenShot (already taken) --> will be restored to Standby DB

#### **option-2: Promote Read replica**
  - RDS(single-region) --> 1hr --> backup/snapshot --> goes to S3 
    - bkp: not directly accessible, managed by aws
    - manually restore the backup into another region, in DR situation.
  - `cross-region`-read replicas, is also possible : paid
  - DR fail-over : `promote` any READ replica as main DB later.
  

### 2.6 Security
- `At-rest` encryption:
  - Database master & replicas encryption using AWS KMS
  - If the master is not encrypted, the read replicas cannot be encrypted
  - To encrypt an un-encrypted database, go through a DB snapshot & restore as encrypted
  - can only enable encryption for an Amazon RDS DB instance when you create it, not after the DB instance is created :dart:
- `In-flight` encryption: 
  - TLS-ready by default, use the `AWS TLS root certificates` client-side.
  - use the same `domain-name-1` for both the certificate and the CNAME record in Route 53.
  - Export cert in ACM 
  - when create/modify RDS instance, configure it use custom  cname `domain-name-1`.
- **IAM Authentication**: :dart:
  - works with MySQL and PostgreSQL :point_left:
  - token has a lifetime of `15 minutes`
  - can use `IAM roles` to ec2-i, to connect to your database (instead of username/pw). eg:
    - ecs (role-1) --> rds
    - lambda (role-1) --> rds
  - or, create one time `password/token` after cluster creation
- `Security Groups`: Control Network access to your RDS / Aurora DB
- No SSH available, except on **RDS Custom**
- attach **Security group** on RDS instance

### 2.7 RDS proxy
- pools open connections.
- reduces fail-over time by 66%
- access privatey only
- client --> RDS proxy --> RDs instance
- ![img.png](../99_img/db/img_5.png)

### 2.8 integration with AWS Ecosystem
- IAM, Lambda, CloudWatch, and Elastic Beanstalk.
- Simplifies building serverless or event-driven architectures

### 2.9 snapshot/backup
- for **automatic** bkp , retention 1 to 35
- for **manual**, retention - as long we want for maul backup.
- `on-prem` MySQL/postgres DB --> create db-dumps( using `Percona XtraBackup`) --> place in S3 --> restore.
- cloning
  - faster than backup > restore
  - uses `copy-on-write` - use same volume + for new changes additional storage allocated and data copied to it.

---
## 3. RDS::pricing
- Charged based on instance class and storage used.
  - standby instance
  - read replica 
- inbound data transfer is free.
- Outbound data transfer is charged based on the volume of data transferred outside of AWS
- **replication charge**: `cross-region only` :dart:

### Cost Optimization Tips
- Use Reserved Instances: Commit to a 1 or 3-year term for discounts
- Enable Auto-Scaling.
- Choose the Right Storage.
- take snapshot and delete db if you dont need. later on restore from snapshot. this will save money.

--- 
## 4. demo
```
- create single DB RDB in region-1
- choose underlying ec2 type (memory optimzed), EBS volume
- DB admin + password + DB name
- backup/screenshot : 
  - enable + retention policy upto 35 days
  - backup window preferrence.
- enable STORAGE autoscaling, give maz size : 100 GB
- Connectivity : 
  - option-1: add "specified ec2-i", will automatically configure things (good for beginner)
  - option-2: Dont connect to Ec2-i
    - define VPC, subnet
    - allow public access
    - choose SG
    - port 
- Authentication : DB password or IAM
- Monitoring : Enable
- backup window pr
- Miantaincence window
- Enable deletion prevention 

 === READY to USE ===
 
- Check monitoring dashboard : CPU, Moemory, Connections, etc
- action:
  - create read replica
  - take Snapshot + migrate Snapshot + restore to point.
  - create read replica.
 
```
---
## 99. extra : DVA
![img.png](../99_img/dva/kms/05/imgrds.png)