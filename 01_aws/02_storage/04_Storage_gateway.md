# Storage Gateway 
## Intro
- `on-prem`  <--> gateway(cache) <--> `aws`(data: Object-storage/S3, File-storage/FS+EFS, Block storage/EBS)
  -  **local cache** in the gateway that provides : :point_left:
    - high throughput 
    - low latency 
- **purpose** : 
  - hybrid storage, 
  - migration, 
  - DR and backup
- gateway is supposed install on on-prem datacenter. <<<
  - order `gateway Hardware appliance`
  - it has required CPU, memory, n/w, etc

## Type
- notice protocol :dart:
### s3 File gateway
- **NFS or SMB** 
- ![img_2.png](../99_img/storage/more/img_2.png)
    
### FXs file gateway
-  **SMB, NTFS, NFS, LusterFS,** 
- `on-prem`  <--> `aws`(File-storage/FS+EFS) : could directly access as well.
- `Local cache` : gateway help to cache frequent access file/data.
- ![img_3.png](../99_img/storage/more/img_3.png)
    
### Volume gateway
- **iSCSI**
- Types:
  - `Gateway-Cached Volumes`: Primary data storage is in Amazon S3, with frequently accessed data cached locally.
  - `Gateway-Stored Volumes`: Primary data storage is on-premises, with cloud-based backup.
- ![img_4.png](../99_img/storage/more/img_4.png)
    
### Tap gateway 
- tap : physical tapes, drive.
- iSCSI VTL ( virtual tap library)
- ![img_5.png](../99_img/storage/more/img_5.png)

---
### Summary

![img.png](../99_img/storage/more/img6.png)

![img.png](../99_img/storage/more/img_8.png)
