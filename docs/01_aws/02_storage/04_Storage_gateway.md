# Storage Gateway 
## 1. Intro
-  provides **local cache** :point_left:
  - high throughput 
  - low latency 
- **purpose** 
  - hybrid storage 
  - **migration** :dart:
  - DR and backup
- gateway is supposed to be installed on on-prem datacenter :point_left:
  - order **gateway Hardware appliance**
  - it has required CPU, memory, n/w, etc

## 2. Type (3)
- ![img.png](../99_img/storage/more/img6.png)
- ![img.png](../99_img/storage/more/img_8.png)

### 2.1 File gateway
- **NFS or SMB** 
- `s3`(1 cent /per, cheap) and `FSx for windows` (70 cent / hr) :point_left:
- ![img_2.png](../99_img/storage/more/img_2.png)
- ![img_3.png](../99_img/storage/more/img_3.png)
    
### 2.2 Volume gateway
- **iSCSI**
- Types:
  - `Gateway-Cached Volumes`: Primary data storage is in Amazon S3, with frequently accessed data cached locally.
  - `Gateway-Stored Volumes`: Primary data storage is on-premises, with cloud-based backup.
- ![img_4.png](../99_img/storage/more/img_4.png)
    
### 2.3  Tap gateway :dart:
- tap : physical tapes, drive
- enables to replace using **physical tapes on-premises** with **virtual tapes in AWS** without changing existing backup workflows.
- encrypts data + compress data.
- iSCSI VTL ( virtual tap library)
- ![img_5.png](../99_img/storage/more/img_5.png)

---
## Exam scenario
- gateway vs Datasync
- ![img.png](../99_img/practice-test-01/06/63/Gateway-vs-dataSync.png)
- ![img.png](../99_img/practice-test-01/06/63/cptgpt-vjfdbvufbsvu.png)

- **comparison**:
```text
DMS:               Best for live database migrations with minimal downtime.
DataSync:          Used for file-based data transfers, not databases.
Storage Gateway:  For hybrid cloud storage, not database migrations.
```

