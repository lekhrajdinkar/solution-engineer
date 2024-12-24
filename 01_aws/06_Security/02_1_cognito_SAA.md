# A. CUP : Cognito user pool (Serverless)
- https://www.udemy.com/course/aws-certified-developer-associate-dva-c01/learn/lecture/19731934#notes
- Complicated service, Need to have high level idea for SAA/DVA

## 1. Intro
- ![img.png](../99_img/dva/cognito/01/img.png)
- **sign-in functionality for global user** (web/mobile)
  - simple login:
    - userid, password, custom feild, email/phone verification.
    - password-reset, acct recovery
    - enable MFA
    - send email to user. SES
    - **JWT** token
    - Authenticate through:
      - integration with **federated Identity provide** - Okta,fb,google (social login)
      - SAML (corporate login)
      - OIDC
      - Microsoft AD , LDAP
      - own serverless database of user/s
      
## 2. **hosted UI** :point_left:
- set domain
  - aws provided
  - **custom**
    - must create ACM cert in us-east-1 :point_left:
- customize UI 
  - CSS 
  - logo

## 3. lambda trigger :books:
- on user pool event
- ![img_1.png](../99_img/dva/cognito/01/img_1.png)
- ![img_2.png](../99_img/dva/cognito/01/img_2.png)

## 4. Adaptive Authentication
- **risk score** for every login activity
- if it looks suspicious, then prompted for MFA
- in case of compromised credential, takeover to email/phone confirmation

## 5. Integration example

### 1 **API-gateway**
![img.png](../99_img/dva/cognito/02/img.png)

### 2.1 **ALB** (high level)
![img_1.png](../99_img/dva/cognito/02/img_1.png)

### 2.2 **ALB** (with OIDC)
- ![img_2.png](../99_img/dva/cognito/02/img_2.png)
- ![img_3.png](../99_img/dva/cognito/02/img_3.png)

---
# B. Cognito Identity pool
- authorization.
## 1 Intro
- **once user is authenticated** with any of these,
- then can get temp **AWS credential** by assuming **IAM role**, to access aws resource.
  - role has trust policy.
```yaml
• Public Providers (Login with Amazon, Facebook, Google, Apple)
• Users in an Amazon Cognito user pool
• OpenID Connect Providers & SAML Identity Providers
• Developer Authenticated Identities (custom login server)
• Cognito Identity Pools allow for unauthenticated (guest) access
```
- ![img_2.png](../99_img/moreSrv/api-gateway/img_2.png)

---
## 2 IAM policy example
### access s3
![img.png](../99_img/dva/cognito/03/img.png)

### access dynamoDB
![img_1.png](../99_img/dva/cognito/03/img_1.png)

---
## 3 hands on
- **configure permission** (create IAM role/s - 1, 2,3 ...)
  - ![img_2.png](../99_img/dva/cognito/03/img_2.png)
- connect to **user pool** (ID provider)
  - ![img_3.png](../99_img/dva/cognito/03/img_3.png)
- **create rule** to choose role, based on :
  - claims in token 
  - user-attribute set is user pool
- next, use SDK check doc.




