# ACM (regional)
## 1. certificate:
- **private**
  - ![img.png](../99_img/dva/img-ca.png)
  - cert can be used inside org
- **public** 
  - cert for public internet

---
## 2. Generate certificate
### by internal / ACM
- ACM generate cert
- import to ACM
- ACM automatically renews **public certificates** `60 days` before expiration

  
### bt External provider
- eg: **digicert**
- generate cert for **FQDN** or with wildcard
  - dev1.outbound.aws.org.com,  
  - dev2.outbound.aws.org.com
  - *.outbound.aws.org.com
  - *.aws.org.com
- import to ACM
- no auto re-new
  - **AWS-Config** 
    - rule:acm-cert-expiracy-check to:
    - sends eventBridge event to expiration, before `45 days` (default)
    - can catch event --> SNS alert, lambda,
    - can change days from 45 to something else.

---
## 3. certificate: Validation method
- **email** 
  - receive validatin email, follow the link in email and validate it.
  - will receive email expiry.
- **dns** : just, create `cname` entry in R53 with above dns

---
## 4. ACM: integration with services    
- **CloudFront** 
  - can monitor **days to expiry** as a metric for ACM certificates :dart:
  - can build alarms to monitor certificates based on **days to expiry**
- **R-53**
- **ELB** : ALB, NLB, ... 
  - ![img_2.png](../99_img/security/acm/img_2.png)
- **API gateway** (3 types)
  - **edge-optimized** : keep ACM cert in `us-east-1`
    - ![img_3.png](../99_img/security/acm/img_3.png)
  - **regional +  private**(with In vpc) 
    - keep ACM cert in same region
    - ![img_4.png](../99_img/security/acm/img_4.png)