# AWS Storage service
- https://chatgpt.com/c/677dbc0a-3414-800d-8960-b0d969c9ffda | ebs,efs,Fxs,snowball
- https://chatgpt.com/c/6a717fbf-d7c0-83e8-801c-b6e597b5d89d | EFS `8/2026`
---
## Overview
- choose : **size capacity**
- choose : **throughput**
  - **operation throughput** / iops / eg: `16k/s` 
      - read iops
      - write iops
  - **data throughput** / bandwidth / eg: `1024 MB/s`
    - data transfer rate like 100 

    >   - **Bandwidth** = maximum data transfer capacity, e.g. **500 MB/s**
    >   - **Data throughput** = actual data transferred, e.g. **350 MB/s**

## categories
| Storage type   | AWS service   | Access model                            |
| -------------- | ------------- |-----------------------------------------|
| Block storage  | EBS           | Disk volumes attached to compute        |
| File storage   | **EFS / FSx** | Directories and files (over network)    |
| Object storage | S3            | Objects(any file) accessed through APIs |

Memory rule:
- EBS = virtual hard drive.
- EFS = shared Linux network drive.

---
## 1. EC2 instant store
[01_instance_store.md](01_instant-store.md)

## 2. EBS
[01_EBS.md](02_EBS.md)

## 3. EFS
[02_EFS.md](03_EFS.md)

## 4. FSx
[02_FSx_serverless-FS.md](04_FSx.md)

## 5. S3
- [05_01_S3.md](05_01_S3.md)
- [05_02_S3-advance.md](05_02_S3-advance.md)

---

## More
- [07_more_01_Storage-gateway.md](07_more_01_Storage-gateway.md)
- [07_more_02_AWS-transfer.md](07_more_02_AWS-file-transfer.md)
- [07_more_03_snowball.md](08_migration_01_snowball.md)
- [07_more_04_AppFlow.md](08_migration_03_AppFlow.md)
- [07_more_05_DataSync.md](08_migration_02_DataSync.md)

---

## Fundamental

```mermaid
flowchart LR
    localApp[EC2-instance] --> OS[Operating System]
    remoteApp["EC2-instance \n(remote)"]
    Protocols["Network Protocols\n(NFS / SMB )"]
    
    subgraph Storage_Host ["Storage Layer"]
        OS --> LocalFS["File Systems\n(ext4 / NTFS / APFS)"]
        Hardware[("Physical Hardware\n(SSD / HDD / NVMe)")]
    end

    remoteApp --> internet[🌐\ninternet] --> Protocols
    Protocols -->|⭐access files \n over network|OS
    LocalFS -->|⭐Organizes bytes \n on blocks| Hardware
    
    style Protocols fill:green,color:white
    style LocalFS fill:green,color:white
```


### 1. File System
A file system defines how data is organized and stored, manage:
- File names
- Directories
- Permissions
- File size
- Timestamps
- Location of file data on disk

| Operating system | Common file systems |
| ---------------- | ------------------- |
| Linux            | ext4, XFS           |
| Windows          | NTFS                |
| macOS            | APFS                |


### 2. Access storage over network

  | network Protocol           | Mainly used by               | AWS example                     |
  |----------------------------| ---------------------------- | ------------------------------- |
  | **NFS**                    | Linux and Unix systems       | EFS, FSx for OpenZFS            |
  | **SMB**                    | Windows systems              | FSx for Windows File Server     |
  | **Lustre protocol/client** | High-performance computing   | FSx for Lustre                  |
  | **iSCSI**                  | Block storage over a network | Some enterprise storage systems |


| AWS service                     | Main client OS    | Protocol               |
| ------------------------------- | ----------------- | ---------------------- |
| **Amazon EFS**                  | Linux             | NFSv4                  |
| **FSx for Windows File Server** | Windows           | SMB                    |
| **FSx for NetApp ONTAP**        | Linux and Windows | NFS, SMB, iSCSI        |
| **FSx for OpenZFS**             | Linux             | NFS                    |
| **FSx for Lustre**              | Linux             | Lustre client protocol |

---
## ⭐Quick Summary on All storage options
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