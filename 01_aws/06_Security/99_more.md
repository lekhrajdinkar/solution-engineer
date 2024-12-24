# A. GaurdDuty
- enable it , 30 days trail, fully managed by aws
- collect below data and perform `ML algo + AI`, to detect/discover any `security threat`.
  - `dns` logs
  - `vpc` logs
  - cloudTrail event : mgt, s3 ?
  - `optional` : enable these logs too... `EKS,RDS,Aurora,EBS,L,S3`
- Send `finding/discovery` data to --> `event-bridge`

- > good to find `CryptoCurrency` attack and protect.

![img_2.png](../99_img/security/others/img_2.png)

---
# B. Macie
- fully managed by aws
- uses ML + pattern match, to find `sensitive data(PII)` and protect it
  - `PII` :  persnally identifiable information
  
- send data --> `event-bridge`

![img_4.png](../99_img/security/others/img_4.png)

---
# C. Inspector
- analyze and perform `security assessment` on:
  - `container` (image scan)
  - `lambda` (code scan - CVE database)
  - `ec2` (ssm agent)  (n/w, os, code/pkg scan)
  
- send finding to -->  `event-bridge` + `AWS security Hub`

![img_3.png](../99_img/security/others/img_3.png)


---
# D. Nitro Enclave
- **isolated compute environment**
  - to process highly sensitive data
    - PII
    - healthcare
    - financial
    - ...

![img.png](../99_img/dva/kms/05/img-vdsvevev.png)

---