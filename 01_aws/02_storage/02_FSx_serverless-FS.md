## Amazon FXs (serverless)
## 1. FXx : Intro
- **serverless fileSystem** + fully managed
- **high performance** FS. 
- **multi-AZ**  (regional)
- mount on:
  - on-prem ( for networking : `vpn` or `directConnect`)  :point_left:
  - ec2-instance
- KMS encrypted.

## 2. File System type +  protocol types
- **File System type** (4):  Windows File System, Lustre, ONTAP, OpenZFS
- **protocol types** : protocol for communication
  - **SMB** 
    -  Server Message Block
    - network file-sharing protocol
    - read, write, and manage files over a network
    - primarily used in **Windows**
    
  - **NFS** 
    - Network File System 
    - distributed file system protocol
    - primarily used in **unix/linux**
    - Allows multiple clients to access shared directories
    - version
      - NFSv3 Stateless
      - NFSv4 Stateful
      
  - **iSCSI**
    - Internet Small Computer Systems Interface
    - transfer TCP/IP n/w
    
  - **NTFS** 
    - file system developed by Microsoft for the Windows OS
    
---
## 3. Fxs : Types (4)
### Fxs for `Windows File System`
- supported protocol : `SMB & NTFS` smb-server message block
- storage option : SSD and HDD.
- DR : s3 backup
- mount on ec2-i (OS : windows + `Linux` too)
- integrate with 
  - `ms AD` - self or AWS managed ms AD.
  - `ACLs`
  - `ms DFS` : group multiple FS 
- performance:
  - millions of iops
  - 100s PB data
  - max : 10 GB/s
    
### Fxs for `Luster FS`
- usecase : `distributed` computing for HPC, ||, ML, Modeling
- protocol : POSIX,
- luster : linux + cluster
- parallel DFS
- use case : HPC, ML, video process, modelling, etc
- storage option : SSD and HDD
- `integrate with S3` : can R/W from S3 as FS <<<
- performance:
  - millions of iops
  - 100 GB/s
- deployment option:
  - `scratch` : short term storage, 6x faster, `no data replication`
  - `persistent`: Long term storage: data replication in same AZ
  - ![img.png](../99_img/storage/more/img.png)

### Fxs for `NetApp ONTAP` 
- protocol : NFS, SMB, iSCSI
- use case : Move workloads running on `ONTAP or NAS`
- `auto-scaling`, compression
- Point-in-time instantaneous cloning
- `compatible` with lots of system.
- ![img_1.png](../99_img/storage/more/img_1.png)

### Fxs for `OpenZFS`
- protocol : NFS (v3,4.1,4.2)
- use case : Move workloads running on `ZFS`
- compression.
- performance : millions of iops
- Point-in-time instantaneous cloning
- `compatible` with lots of system. same as netApp FS.


----
