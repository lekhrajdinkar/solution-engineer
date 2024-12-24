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
    - backend:
      - integration with **federated Identity provide** - Okta,fb,google,SAML,OIDC
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

## 5. Integration
- **ALB** 
- **API-gateway**
- ![img_3.png](../99_img/moreSrv/api-gateway/img_3.png)

---
# B. Cognito : Identity pool
- help to provide `temp AWS credential ( with fine grain permission)`, so outside user access aws resource directly.
- once user is signed-in with `Cognito : user pool` or `3rd party login`
- then, they exchange `auth token` with `AWS credential` 
- ![img_4.png](../99_img/moreSrv/api-gateway/img_4.png)


