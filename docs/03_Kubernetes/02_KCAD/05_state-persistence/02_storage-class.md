## Storage class
- `static` provisioning 
  - specify PV first. Storage created ahead of time with static config
  - then define PVC
    - if matches then get bind
- `dynamic` provisioning
  - define storageClass/SC with parameter
  - then define PVC
    - PVC will use SC to provision needed storage.
    - so, SC creates PV dynamically.

- with Storage-Class can define  `Dynamic Storage provisioner`
```
apiVersion: v1
kind: StorageClass
metadata:
   name: gcp-sc-1
provisioner: kubernetes.io/gce-pd  <<<<<  
parameters:
  type:
  replication-types:  
  
  next, use it  inside PVC
```
---
## Screenshots
- static
  - ![img.png](../99_img/08/02/img.png)
- dynamic
  - ![img_1.png](../99_img/08/02/img_1.png)

---
[VolumeClaimTemplate](../01_Core/04_Stateful-sets.md#volume)