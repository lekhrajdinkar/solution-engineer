# AWS config (regional)
## 1. Intro
- Records: aws-resource **auditing and Compliance** using **config-rules**.

- On event of changes/modification on AWS resources =>  evaluate these rule, then:
  - take **remediation action**
  - and/or, send **notification**

- cannot not deny from evaluating. :point_left:
- for each aws-resource, on console can view :
  - **configured rules**  
  - **compliance status**
  - **CloudTrail api call**
  - **event table** : 
    - timeline of changes and other event.
- ![img.png](../99_img/moreSrv/aws-config/img.png)

---
## 2. Config Rule
- `Json` configuration
### 2.1 AWS managed 
- already defined, 75+
- eg:
  - Check **unrestricted SSH** access to sg
  - Do we have **publicly accessed s3 buckets** 
  - Any changes made on **ALB** 

### 2.2 Custom rules 
- define using **Lambda function**
- eg:
  - evaluate :: EBS disk == type gp2
  - evaluate :: EC2 instance == t2.micro
    - all instance
    - by instance.id


## 3 Action on compliance failure
### 3.1. remediation action
- **`SSM` Automation Document**
- **`custom` Automation Document**, eg:
  - ![img_1.png](../99_img/moreSrv/aws-config/img_1.png)
  
### 3.2 notification
- use **Event-Bridge** Event
- use **SNS** -> filter -> subscriber
- ![img_2.png](../99_img/moreSrv/aws-config/img_2.png)  

---
## 4. Pricing: 
- not free tier.
- `$0.003 `per **configuration item/rule** recorded per region,
- `$0.001` per config rule **evaluation** per region

---
## 5. hands on
```
- AWS config, option:
    - Record for all resource **
    - Record for specfic resource
- create role-1
- choose delivery method: S3 bucket ** / cross acct bucket
- stream config change to SNS : T/F
- Rules (75 + ) : checkbox
    - approved amis by Id > enter parameter/s : ami id 
    - approved amis by tag
    - restricted-ssh > no parameter
        - play around > add/remove inbound rule for port 22/ssh
        - and check compliance status

```