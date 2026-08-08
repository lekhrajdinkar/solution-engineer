# Amazon FXs (serverless + fully managed)
## Overview
**high performance** FS over network, can be mounton :
- ec2-instance (multiple OS supported)
- on-prem VM ( for networking : `vpn` or `directConnect`)  👈
  
More:
- multi-AZ
- KMS encrypted.
- automated backup to S3

---    
## 1. FSx for `Luster FS`

```mermaid
flowchart LR
    hpcNode1["HPC Compute Node 1"]
    hpcNodeN["HPC Compute Node N"]
    
    Protocols["Parallel File Client / Driver\n(Lustre Protocol)"]
    
    subgraph Storage_Host ["Amazon FSx for Lustre"]
        ManagedFS["Parallel Shared File System\n(Metadata & Storage Servers)"]
        Hardware[("High-Throughput NVMe / SSD\n(Optional S3 Integration)")]
    end

    hpcNode1 --> Protocols
    hpcNodeN --> Protocols
    Protocols -->|⭐High-speed parallel I/O \n over network| ManagedFS
    ManagedFS -->|⭐Stripes file data across \n multiple storage targets| Hardware
    
    style Protocols fill:green,color:white
    style ManagedFS fill:green,color:white
```
- mount on :
    - ec2-i (Unix/Linux OS) 👈
- supported storage option : `SSD` , `HDD`
- supported protocol : `Lustre Protocol`, `POXIS-compliant`
- **size**: `100s PB` |  **iops** : `in millions`   | **throughput**  `100s of GB/s with large clister`

- **More**
    - integrate with **S3**  :dart: :dart:
        - transparently presents `S3 objects as files` and allows you to write changed data back to S3
        - ability to both process the
            - **hot data** in a parallel and distributed fashion.
            - **cold data** on Amazon S3
    - **use case**
        - HPC, ||, ML, Modeling
    - **deployment option**  👈
        - **scratch** : short term storage, 6x faster, `no data replication`
        - **persistent** : Long term storage: data replication in same AZ
        - ![img.png](../99_img/storage/more/img.png)

---  
## 2. FSx for `Windows File System`
- support **Microsoft’s Distributed File System (DFS)** 👈
- **size**: `100s PB` 
- **iops** : `in millions`   
- **throughput**  `10 GB/s`
- integrate with:
  - ms AD - self or AWS managed ms AD.
  - ACLs
  - ms DFS : group multiple FS 

```mermaid
flowchart LR
    app1["Windows / ⭐Linux Server 1\n(App Server)"]
    app2["Windows Workstation\n(Client)"]
    
    Protocols["Network Protocol\n(SMB / NTFS)"]
    
    subgraph Storage_Host ["Amazon FSx for Windows File Server"]
        ManagedFS["Shared File System\n(NTFS / Active Directory Integration)"]
        Hardware[("Multi-AZ Storage Cluster\n(SSD / HDD Managed Storage)")]
    end

    app1 --> Protocols
    app2 --> Protocols
    Protocols -->|access shared files \n over network| ManagedFS
    ManagedFS -->|Organizes files & NTFS ACLs \n across distributed storage| Hardware
    
    style Protocols fill:green,color:white
    style ManagedFS fill:green,color:white
```

----
## 3. FSx for `NetApp ONTAP` 
- **compatible with lots of system**
- additional feature: compression, Point-in-time instantaneous cloning

![img_1.png](../99_img/storage/more/img_1.png)

```mermaid
flowchart LR
    linuxApp["Linux App Server"] --> nfsProtocol["Network Protocol\n(NFS)"]
    winApp["Windows App Server"] --> smbProtocol["Network Protocol\n(SMB)"]
    dbServer["Database Server"] --> iscsiProtocol["Block Protocol\n(iSCSI)"]

    subgraph Storage_Host ["Amazon FSx for NetApp ONTAP"]
        ManagedFS["ONTAP Volume / Storage Virtual Machine\n(WAFL File System)"]
        Hardware[("High-Performance Storage Pool\n(SSD / Auto-Tiered Capacity Pool)")]
    end

    nfsProtocol -->|access shared files| ManagedFS
    smbProtocol -->|access shared files| ManagedFS
    iscsiProtocol -->|Exposes raw block LUN| ManagedFS
    ManagedFS -->|Organizes snapshots & blocks \n across tiered storage| Hardware
    
    style nfsProtocol fill:green,color:white
    style smbProtocol fill:green,color:white
    style iscsiProtocol fill:green,color:white
    style ManagedFS fill:green,color:white
```
----
## 4. FSx for `OpenZFS`
- **compatible with lots of system**
- additional feature: compression, Point-in-time instantaneous cloning

```mermaid
flowchart LR
    app1["Linux Workload 1\n(EC2 / Container)"]
    app2["Linux Workload N\n(EC2 / Container)"]
    
    Protocols["Network Protocol\n(NFSv3 / NFSv4)"]
    
    subgraph Storage_Host ["Amazon FSx for OpenZFS"]
        ManagedFS["ZFS File System\n(ZPOOL / Datasets)"]
        Hardware[("High-IOPS NVMe / SSD\nManaged Storage")]
    end

    app1 --> Protocols
    app2 --> Protocols
    Protocols -->|access low-latency files \n over network| ManagedFS
    ManagedFS -->|Organizes copy-on-write blocks \n & zfs snapshots| Hardware
    
    style Protocols fill:green,color:white
    style ManagedFS fill:green,color:white
```

---
## Exam 🎯
https://aws.amazon.com/fsx/when-to-choose-fsx/

![img_1.png](../99_img/practice-test-01/wz03/01/img_1.png)

FSx :: ontap | ZFS | windows | Luster

![img.png](../99_img/practice-test-01/06/63/comparefxs.png)
