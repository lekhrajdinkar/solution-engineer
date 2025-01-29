# introduction on:
## A. GaurdDuty
- fully managed
- enable it and try it, (30 days trail)
- **collect** from:
  - dns logs
  - vpc logs
  - cloudTrail management event
  - **optionally** enable these logs too
    - EKS,RDS,Aurora,EBS,Lambda,S3
    - ...
- **perform** 
  - analyzes continuous streams of meta-data generated from your account and network activity using ML/AI
  - to detect any security threat and un-usual activity
    - **CryptoCurrency attack** :point_left:
    - infrastructure deployments in a region that has never been used.
  
- fact :dart:
  - **Disabling** the service
    - will delete all remaining data, including your `findings and configurations`
  - **Suspending** the service
    - stop the service from analyzing data 
    - but does not delete your existing `findings or configurations`

![img_2.png](../99_img/security/others/img_2.png)

```text
#1
During a review, a security team has flagged concerns over an Amazon EC2 instance querying IP addresses used for cryptocurrency mining.
The Amazon EC2 instance does not host any authorized application related to "cryptocurrency mining".
Which AWS service can be used to protect the Amazon EC2 instances from such unauthorized behavior in the future? 
- GaurdDuty**
```

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
- analyze and perform **security assessment** on:
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

