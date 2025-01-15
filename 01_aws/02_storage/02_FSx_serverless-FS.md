## Amazon FXs (serverless)
## 1. FXx : Intro
- **serverless fileSystem** + fully managed
- **high performance** FS. 
- **multi-AZ**  (regional)
- mount on:
  - on-prem ( for networking : `vpn` or `directConnect`)  :point_left:
  - ec2-instance
- KMS encrypted.
- automated backup to S3 :point_left:

## 2. File System type +  protocol types
- **File System type** (4):  Windows File System, Lustre (linux + cluster), ONTAP, OpenZFS
- **protocol types** : protocol for communication
  - **SMB** 
    -  Server Message Block
    - network file-sharing protocol
    - read, write, and manage files over a network
    - primarily used in **Windows**
    
  - **NTFS**
    - file system developed by Microsoft for the Windows OS
    
  - **NFS** 
    - Network File System 
    - distributed file system protocol
    - primarily used in **unix/linux**
    - Allows multiple clients to access shared directories
    - version
      - NFSv3 Stateless
      - NFSv4 Stateful
  
  - **Lustre Protocol**  
      
  - **iSCSI**
    - Internet Small Computer Systems Interface
    - transfer TCP/IP n/w
    
---
## 3. Fxs : Types (4)
### Fxs for `Windows File System`
- mount on :
  - ec2-i (windows  OS)
  - ec2-i (Unix/Linux OS) :point_left:
- supported protocol : `SMB` , `NTFS` 
- supported storage option : `SSD`,  `HDD`
- support Microsoft’s Distributed File System (DFS) :point_left: :dart:
- **size**: `100s PB` |  **iops** : `in millions`   | **throughput**  `10 GB/s`

- **more**
  - DR : s3 backup
  - integrate with 
    - ms AD - self or AWS managed ms AD.
    - ACLs
    - ms DFS : group multiple FS 

---    
### Fxs for `Luster FS`
- mount on :
    - ec2-i (Unix/Linux OS) :point_left:
- supported storage option : `SSD` , `HDD`
- supported protocol : `Lustre Protocol`, `NFS`
- **size**: `100s PB` |  **iops** : `in millions`   | **throughput**  `?`

- **More**
  - integrate with 
    - **S3** -> can R/W from S3 as FS :point_left:
  - **usecase** 
    - HPC, ||, ML, Modeling
  - **deployment option**  :point_left:
    - **scratch** : short term storage, 6x faster, `no data replication`
    - **persistent** : Long term storage: data replication in same AZ
    - ![img.png](../99_img/storage/more/img.png)

----
### Fxs for `NetApp ONTAP` 
- protocol : `NFS, SMB, iSCSI`
- compression
- Point-in-time instantaneous cloning
- compatible with lots of system.
  - ![img_1.png](../99_img/storage/more/img_1.png)

----
### Fxs for `OpenZFS`
- protocol : `NFS`
- compression.
- Point-in-time instantaneous cloning
- `compatible` with lots of system. 
  - same as netApp ontap FS.



