## A. FTP : AWS Transfer Family(3)
- SFTP / FTPS (outside AWS) 
- FTP with in AWS/VPC
- expose s3/EFS over FTP protocol
![img.png](../99_img/storage/img.png)

---
## B. AWS DataSync
- **scheduled continuous data sync**
  - hourly
  - daily 
  - weekly 
  - ...
  
- Move large amount of data/files to and from 
  - **On-prem(install datasync-agent) and aws** 
    - 10 Gbps
    - use multiple agent for more speed
    - ![img_1.png](../99_img/storage/img_1.png)
  - **AWS to AWS** (no agent)
    - ![img_2.png](../99_img/storage/img_2.png)
  
- **target** NO EBS :x:

- more:
  - File permissions and metadata are `preserved`
  - **protocol**: NFS and SMB :point_left:
  - TLS
  - save n/w cost with snowcone
  
- **Network perspective** 
  - **Direct Connect** ✅ – Supported. DataSync can transfer data over AWS Direct Connect for faster, private transfers.
  - **Site-to-Site VPN** ✅ – Supported. DataSync can run over a VPN tunnel for secure data transfers between on-prem and AWS.
  - **Internet** ✅ – Supported. By default, DataSync uses the public internet with encryption for transfers between on-prem storage and AWS.

- fact:
  - cannot open **locked file**
  - file is opened and **modified**, while sysnc
    - it will detect Data inconsistency during VERFYING stage.
  - above 2 files will be skipped/ found missing then :dart:
