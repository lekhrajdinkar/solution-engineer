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
- identify software **vulnerability and network exposures** (unintentional open ports)
- references:
  - https://docs.aws.amazon.com/inspector/latest/user/what-is-inspector.html
  - https://www.youtube.com/watch?v=SM_esXHbJ4M (check from 9:00)
- fully managed : create **Assessment template**
  - select **target**
  - select assessment **package** to run (need SSM agent to be installed)
  - select **duration**.
- analyze and perform **security assessment** on assessment targets:
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

---
## E. More
### 1. AWS Security Hub
- **scenario** : Audit performed on AWS account. have to fix complaince for future. which AWS service can use ?
- AWS Security Hub provides a **comprehensive view** of your security posture in AWS. 
- It aggregates, organizes, and prioritizes security findings from multiple AWS services and third-party tools. 
- It continuously monitors your environment for vulnerabilities and compliance violations by **integrating** with services:
  - Amazon GuardDuty, 
  - AWS Config, 
  - Amazon Macie
  - ...
  - AWS Lambda: automate response actions.
- It uses **standards** such as 
  - CIS AWS Foundations
  - PCI DSS 
  - HIPAA

### 2. AWS Systems Manager 
- https://docs.aws.amazon.com/systems-manager/latest/userguide/what-is-systems-manager.html
- unified interface for managing your AWS resources. 
- It provides operational **insights** and **automation** for tasks like
  - **patching**   :point_left:
  - **configuration  management**   :point_left: 
  - **compliance**  :point_left: 
- Key features include:
  - Automation: Automates common IT tasks like applying patches or updates across AWS resources.          
  - **Run Command**:  :dart:
    - Allows remote execution of commands on EC2 instances without needing to log in. **SSM agent** must be installed 
    - no need to perform SSH
    - can install packages
    - create and assume role with IAM SSM permission.
  - Patch Manager: Automatically applies security patches to your systems.                                
  - State Manager: Ensures that instances remain in the desired configuration state.                      
  - Compliance: Tracks the compliance of your systems with internal policies and external regulations.    
                                                                                                           


