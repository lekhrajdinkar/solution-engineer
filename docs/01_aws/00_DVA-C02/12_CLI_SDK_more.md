# developer things
## 1 EC2 **instant metadata** API.
---
## 2 aws **profile**
---
## 3 CLI
- aws cli with MFA. : cli written in py(boto3)
  - **aws sts get-session-token** 
  - `--serial-number` arn-of-the-mfa-device 
  - `--tokencode` code-from-token 
  - `--duration-seconds` 3600
- `--region` **us-east-1** (default) :point_left:
---
## 4 aws **sdk**
- in-built **retry** mechanism (with exponential backoff)
- java py node.js
---
## 5 Aws **limits** 
### on API call.
- ec2:describeInstance - 100/s
- s3:getObject         - 5500/s
### on Resources
- open ticket ?
- service Quota API
### Exponential backoff
- while making api call, if get **ThrottlingException** or **5XX** intermittently, use it.
  - retry:1 after 1 sec
  - retry:2 after 2 sec
  - retry:3 after 4 sec
  - retry:4 after 8 sec
---
## 6 AWS CLI/SDK(java) Credentials Provider Chain :o:
- The CLI will look for credentials in this order:
1. **CLI option** – --profile, or
1. SDK :: **Java system properties** – `aws.accessKeyId` and `aws.secretKey`
2. **Environment variables** – `AWS_ACCESS_KEY_ID,AWS_SECRET_ACCESS_KEY,AWS_SESSION_TOKEN`
3. ~/.aws/credentials 
4. ~/.aws/config 
5. Container credentials – for **ECS tasks**
6. Instance profile credentials – for **EC2 Instance Profiles**
---
## 7 signV4 
- ![img.png](../99_img/dva/img.png)
- **AWS SDKs** and **AWS CLI** handle SigV4 signing automatically