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
    - flow: on-prem  --> AWS Direct connection --> VPC gateway(interface) --> resource(EFS/S3)
    - ![img_1.png](../99_img/storage/img_1.png)
  - **AWS to AWS** (no agent)
    - ![img_2.png](../99_img/storage/img_2.png)
  
- **target**
  - s3/glacier
  - EFS 
  - FSx

- more:
  - File permissions and metadata are `preserved`
  - protocol: NFS-POSIX, SMB
  - TLS
  - save n/w cost with snowcone

---

# Quick Summary on All storage options
- `S3`: Object Storage
- `S3 Glacier`: Object Archival
- `EBS volumes`: Network storage for one EC2 instance at a time
- `EC2 Instance Storage`: Physical storage for your EC2 instance (high IOPS)
- `EFS`: Network File System for Linux instances, POSIX filesystem
- `FSx`
  - FSx for Windows: Network File System for Windows servers
  - FSx for Lustre: High Performance Computing Linux file system
  - FSx for NetApp ONTAP: High OS Compatibility
  - FSx for OpenZFS: Managed ZFS file system
- `Storage Gateway`: S3 & FSx File Gateway, Volume Gateway (`cache & stored`), Tape Gateway
- `Transfer Family`: FTP, FTPS, SFTP interface on top of Amazon S3 or Amazon EFS
- `DataSync`: Schedule data sync from on-premises to AWS, or AWS to AWS
- `Snowcone / Snowball / Snowmobile`: to move large amount of data to
- `Database`: for specific workloads, usually with `indexing and querying`