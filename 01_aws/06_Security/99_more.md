# introduction on:
## A. GaurdDuty
- fully manage
- enable it and try it, (30 days trail)
- **collect** from:
  - dns logs
  - vpc logs
  - cloudTrail management event
  - **optionally** enable these logs too
    - EKS,RDS,Aurora,EBS,Lambda,S3
    - ...
- **perform** 
  - ML algo + AI
  - to detect/discover any security threat
    - **CryptoCurrency attack** :point_left:
  - Send event-bridge notification

![img_2.png](../99_img/security/others/img_2.png)

---
## B. Macie
- fully managed
- **collect** from:
  - s3
  - ...
- **perform**:
  - uses ML 
  - pattern match
  - find **sensitive data(PII)** persnally identifiable information
  - Send event-bridge notification

![img_4.png](../99_img/security/others/img_4.png)

---
## C. Inspector
- fully managed
- analyze and perform security assessment on:
  - `container` (image scan)
  - `lambda` (code scan - CVE database)
  - `ec2` (ssm agent)  (n/w, os, code/pkg scan)
  
- send finding to -->  `event-bridge` + `AWS security Hub`

![img_3.png](../99_img/security/others/img_3.png)


---
## D. Nitro Enclave
- **isolated compute environment**
  - to process highly sensitive data
    - PII
    - healthcare
    - financial
    - ...

![img.png](../99_img/dva/kms/05/img-vdsvevev.png)

