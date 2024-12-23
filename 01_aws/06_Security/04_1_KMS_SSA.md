# KMS 
## A. Encryption
### 1. Encryption at `Fly`
- TLS / SSL certificate / HTTPS
- prevent from MITM

![img.png](../99_img/security/kms/img.png)

### 2. Encryption at `Rest`
- encryption/decryption happens at server.

![img_1.png](../99_img/security/kms/img_1.png)

### 3. Client side encryption

- Don't trust server
- cant make KMS api call

![img_2.png](../99_img/security/kms/img_2.png)

---
## B. KMS: intro
- manges **encryption-keys**
  - needs to be **rotated**
    - default : 365 days
    - range : 90 - 2650 days
    - have od rotation.
  - has kms-key **alias**
  - scope: **region** :point_left:
    - for cross region copy will need 2 separate keys, once for each region
    - eg: copy from region-1 to region-2
      - aws will **decrypt** using region-**1**-key
      - aws will **re-encrypt** using region-**2**-key

- **integrated** with:
  - `IAM`
  - `cloudTrail`, check log for KMS usage/audit.
  - `secret manager` 
    - encrypt **password** with kms-key
  - `EC2`
    - encrypt **AMI** with kms
  - `ebs`, `rds`,  `s3-key`, `sqs-keys`, etc
  - ...
  - all other service which requires encryption.

- **KMS API call** 
  - all above service makes api call to kms.*
  - we can api call with **cli/sdk**
    - to encrypt/decrypt anything(eg:env var) using kms-key-1

--- 
## C. KMS: `key types`
```
# --- symmetric(AES-256) ---
- generate single key
  - private
  
- aws-service integrated with kms, uses it.   <<<

# ---  A-symmetric (RSA) ---
- generate 2 keys
  - public ( for encrypt)
     - access it, download it.
     - share with client
  - private ( for decrypt)
  
- for client-server comm                       <<<
```

### **1. AWS owned**  `FREE`
- keys already created for services. 
- eg
  - sse-s3
  - sse-sns
  - ...

### **2. AWS managed key**  `FREE`
- request key from kms
- rotation:  automatic yearly
- key looks like - aws/serviceName/**** . eg
  - aws/rds/...
  - aws/ebs/...

### **3. Customer manged key**  `PAID`
- customer upload its own key. 
- import key into kms, which generated outside aws
- rotation:  must enable it :point_left:
- pricing 
  - `1$/month` / key
  - API calls : `0.03/10,000`

---
## C. KMS: `Regionality` :point_left:
### 1. single regional 
- same key cannot be present in 2 diff regions.
- requires additional api call (for cross region)
  - decrypt  call
  - re-encrypt call 

### 2. multi regional 
- **simplify but not recommended**
- same key replicated in multiple region
  - primary (policy-1)
  - replicated key (policy-2, in another region)
- purpose
  - encrypt in one region and use/decrypt in another region, **seamlessly**
  - don't need to re-encrypt again with another region key
- **use-case**
  - global Aurora DB
  - global Dynamo DB

---
## D. KMS: `Security`
### 1. KMS Policy
- like s3 policy
- define who can access key.
- **default policy** 
  - already exists
  - allows everyone in account  :point_left:
- **custom policy** 
  - eg: 
    - for cross account access, restricted access with in acct, etc
      - ![img_4.png](../99_img/security/kms/img_4.png)
    - give access to specific services (lambda-fn)
      ```
      lambda-1 copy ebs snapshot from one region to another region
        - only lambda-1 must have access below 2 keys, no one else.
          - region-1-key (to decrypt) 
          - region-2-key (to re-encrypt) 
      ```
---
## E. hands on
```
- create key-1
    - symetric
        - type: aws owned
- choose : regionality 
    - single region
- key policy
    - add json
    - or use console to define multiple options.
- rotation yearly : y/n

// READY
- actin:
    - disable
    - schedule for deletion 
   
- use aws-cli : encypti failtext with above key-1 
```   
---
## E. Examples
### 1.1 S3 - `CRR` replication 
- [here](./../02_storage/03_S3-1.md#security-while-crr-replication)

### 1.2 S3  - `SRR` replication
- bucket-1(key-1) --> replicate(decrypt with key-1 > encrypt with key-2) --> bucket-2(key-2)
- add permission for both keys to ...
- 
### 3. share AMI cross region
![img.png](../99_img/security/kms-2/img-100.png)
- share AMI : update `launch-permission` for AMI to allow access
- share kms-key-1 : update `kms-policy` to allow access
- Account-b >> decrypt with kms-key-1 >> re-encrypt with its kms-key-2(Account-b)

### 4. EBS volume (cross region)
![img_3.png](../99_img/security/kms/img_3.png)

### 5 more

![img.png](../99_img/security/kms-2/img.png)

![img_1.png](../99_img/security/kms-2/img_1.png)

![img_2.png](../99_img/security/kms-2/img_2.png)