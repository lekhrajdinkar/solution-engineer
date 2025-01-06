# AWS X-ray :books:
- traces:
  - request > components
  - Each component dealing with the request adds its own “trace”
  - has segments and sub segments
- ![img_1.png](../99_img/dva/aa-x-ray/01/img_1.png)
---
## A. Intro
- analyze **traces** `visually`
- Understand dependencies in a **microservice architecture**
- **compatible services** 
  - AWS Lambda
  - Elastic Beanstalk
  - ECS
  - ELB
  - API Gateway
  - EC2 Instances or any application server (even on premise)
- **SDK**
  - ![img.png](../99_img/dva/aa-x-ray/01/img.png)
  - SDK will capture:
    - Calls to AWS services
    - HTTP / HTTPS requests
    - Database Calls (MySQL, PostgreSQL, DynamoDB)
    - Queue calls (SQS)
- **X-Ray daemon**
  - low level UDP packet interceptor
  - AWS Lambda / other AWS services already run the X-Ray daemon :point_left:

---
## B. X-Ray Security:
- IAM for authorization
- KMS for encryption at rest

---

## Z. extra :books:
### AWS X-Ray Troubleshooting
-  on **EC2**
  - Ensure the EC2 **IAM Role** has the proper permissions
  - Ensure the EC2 instance is running the **X-Ray Daemon**
-  on AWS **Lambda**
  - Ensure IAM execution role `AWSX-RayWriteOnlyAccess`
  - Enable  **X-Ray Active Tracing** 