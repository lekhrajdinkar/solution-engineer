# 1. AWS Services  for DVA
## A. Reference
- https://aws.amazon.com/blogs/
- https://www.udemy.com/course/aws-certified-developer-associate-dva-c01

![img.png](../99_img/dva/00/img.png)

---
# 2. Progress :books:
- https://www.udemy.com/course/aws-certified-developer-associate-dva-c01/learn/lecture/19771482#overview
  - slides: :book: https://courses.datacumulus.com/downloads/certified-developer-k92/
  - practice paper: https://www.udemy.com/course/aws-certified-developer-associate-practice-tests-dva-c01/

## Section 4 `IAM` :green_circle:
- [01_IAM.md](../06_Security/01_IAM.md)
- Directory service:
  - [01_SSO+DirectoryService.md](../06_Security/01_SSO%2BDirectoryService.md)

## Section 5 `EC2` :green_circle:
- [01_EC2.md](../01_compute/01_EC2.md)

## Section 6  `EBS_EFS` :green_circle:
- [01_EBS_EFS.md](../02_storage/01_EBS_EFS.md)

## Section 7 : `ELB_ASG` :green_circle:
- [01_ELB_ASG.md](..%2F04_network%2F01_ELB_ASG.md)

## Section 8 -  `RDS + Aurora + ElastiCache` :green_circle:
- [01_RDS.md](../03_database/01_RDS.md)
- [02_Aurora.md](../03_database/02_Aurora.md)
- [03_ElastiCache.md](../03_database/03_ElastiCache.md)
```
    ElastiCache Strategies
    Amazon MemoryDB for Redis
```
## Section 9 - `Route 53` :green_circle:
- [02_Rout53.md](../04_network/02_Rout53.md)
```
    Routing Policy - Traffic Flow & Geoproximity Hands On
```

## Section 10 - `VPC Fundamentals` :green_circle:
- VPC details not needed. still can check:
  - [03_VPC-1.md](../04_network/03_VPC-1.md)
  - [03_VPC-2.md](../04_network/03_VPC-2.md)
  - [03_VPC-3.md](../04_network/03_VPC-3.md)
  - [03_VPC-4.md](../04_network/03_VPC-4.md)
- [04_CloudFront_DVA.md](../04_network/04_CloudFront_DVA.md)
```
    VPC Fundamentals - Section Introduction
    VPC, Subnets, IGW and NAT
    NACL, SG, VPC Flow Logs
    VPC Peering, Endpoints, VPN, DX
    VPC Cheat Sheet & Closing Comments
    Three Tier Architecture
```
## Section 12 - `AWS CLI, SDK` :green_circle:
- [12_CLI_SDK_more.md](12_CLI_SDK_more.md)
```
    AWS EC2 Instance Metadata
    AWS EC2 Instance Metadata - Hands On
    AWS CLI Profiles
    AWS CLI with MFA
    AWS SDK Overview
    Exponential Backoff & Service Limit Increase
    AWS Credentials Provider & Chain
    AWS Signature v4 Signing
```

## Section 11,13,14 - `S3` :green_circle:
- [03_S3-1.md](../02_storage/03_S3-1.md)
- [03_S3-2.md](../02_storage/03_S3-2.md)
- [03_S3-3.md](../02_storage/03_S3-3.md)

## Section 15 - `CloudFront` :green_circle:
- [04_CloudFront.md](../04_network/04_CloudFront.md)
- [04_CloudFront_DVA.md](../04_network/04_CloudFront_DVA.md)
```
    CloudFront - Caching & Caching Policies
    CloudFront - Cache Behaviors
    CloudFront - Caching & Caching Invalidations - Hands On
    CloudFront - Signed URL / Cookies
    CloudFront - Signed URL - Key Groups + Hands On
    CloudFront - Advanced Concepts
    CloudFront - Real Time Logs
```

## Section 16 `ECS, ECR & Fargate` :green_circle:
- [02_Containers_ECS.md](../01_compute/02_Containers_ECS.md)
- [02_Kubernetes_EKS.md](../01_compute/02_Kubernetes_EKS.md)
```
    Amazon ECS - Rolling Updates
    Amazon ECS Task Definitions - Deep Dive
    Amazon ECS Task Definitions - Hands On
    Amazon ECS - Task Placements
    Amazon ECR - Hands On
```
## Section 17 `beanstalk` :green_circle:
- [05_1_Beanstalk_SSA.md](../01_compute/05_1_Beanstalk_SSA.md)
- [05_2_Beanstalk_DVA-deployments.md](../01_compute/05_2_Beanstalk_DVA-deployments.md)

## Section 18 `Cloudformation` :green_circle:
- [18_CLOUD_FORMATION.md](18_CLOUD_FORMATION.md)

## Section 19 `SQS,SNS, KDS, KDF` :green_circle:
- [05_decoupling](../05_decoupling)

## Section 20 `Monitor` :green_circle:
- [01_CW-Metric.md](../07_monitoring/01_CW-Metric.md)
- [02_CW-Logs.md](../07_monitoring/02_CW-Logs.md)
- [03_CW-Alarms.md](../07_monitoring/03_CW-Alarms.md)
- [04_X-rays_DVA.md](../07_monitoring/04_X-rays_DVA.md) :yellow_circle:
- [05_cloudTrail.md](../07_monitoring/05_cloudTrail.md)
- [06_AWS_config.md](../07_monitoring/06_AWS_config.md)

## Section 21 : `lambda` :green_circle:
- [03_lambda-01-saa.md](../01_compute/03_lambda-01-saa.md)
- [03_lambda-dva-01-CLI.md](../01_compute/03_lambda-dva-01-CLI.md)
- [03_lambda-dva-02-trigger.md](../01_compute/03_lambda-dva-02-trigger.md)
- [03_lambda-dva-03-context+event.md](../01_compute/03_lambda-dva-03-context%2Bevent.md)

## Section 22 : `DynamoDB` :green_circle:
- [04_1_DynamoDB_SSA.md](../03_database/04_1_DynamoDB_SSA.md)
- [04_2_DynamoDB_DVA-components.md](../03_database/04_2_DynamoDB_DVA-components.md)
- [04_3_DynamoDB_DVA-operations.md](../03_database/04_3_DynamoDB_DVA-operations.md)
- [04_4_DynamoDB_DVA-cli.md](../03_database/04_4_DynamoDB_DVA-cli.md)

## Section 23 : `API-gateway` :green_circle:
- [05_1_API_gateway_SAA.md](../04_network/05_1_API_gateway_SAA.md)
- [05_2_API_gateway_DVA.md](../04_network/05_2_API_gateway_DVA.md)

## Section 24 : `CI/CD` :green_circle:
- [24_CI_CD](24_CI_CD)

## Section 25 : `SAM :Serverless Application Model` :green_circle:
- [00_serverless_pardigm.md](../00_kick_off/00_serverless_pardigm.md)
- [00_start.md](25_SAM.md)
- [SAM_project](25_SAM_project)

## Section 26 : `CDK` :green_circle:
- [26_CDK.md](26_CDK.md)

## Section 27 : `Cognito` :green_circle:
- [02_1_cognito_SAA.md](../06_Security/02_1_cognito_SAA.md)

## Section 28 : serverless more: `Step function, AppSync, Amplify` :green_circle:
- [28_1_STEP_FUNCTION.md](28_1_STEP_FUNCTION.md)
- [28_2_AppSync.md](28_2_AppSync.md)
- [28_3_Amplify.md](28_3_Amplify.md)

## Section 29: `Advance IAM (short)` :green_circle:
- [01_IAM.md](../06_Security/01_IAM.md)
- [01_SSO+DirectoryService.md](../06_Security/01_SSO%2BDirectoryService.md)

## Section 30 : `security: KMS,SSM,Secretmanager` :green_circle:
- [04_1_KMS_SSA.md](../06_Security/04_1_KMS_SSA.md)
- [04_2_KMS_DVA-cli.md](../06_Security/04_2_KMS_DVA-cli.md)
- [04_3_KMS+HSM_DVA-more.md](../06_Security/04_3_KMS%2BHSM_DVA-more.md)
- [05_SSM-param-store.md](../06_Security/05_SSM-param-store.md)
- [06_secret_manager.md](../06_Security/06_secret_manager.md)
- more (SSA)
  - [07_ACM.md](../06_Security/07_ACM.md)
  - [08_WAF + FirewallManager.md](../06_Security/08_WAF%2BFirewallManager.md)
  - [09_sheild.md](../06_Security/09_sheild.md)
  - [99_more_securiy_services_for_SSA.md](../06_Security/99_more.md)

## Section 31 : `other services` :red_circle:
### DVA

### SAA
- [97_more-services](../97_more-services)
- [09_DR](../09_DR)
- [08_Analytics](../08_Analytics)
- [10_ML](../10_ML)
- more:
  - [98_SAA_discussion](../98_SAA_discussion)
  - [practice-test-SAA](../practice-test)

---
## services
```json5

### Compute
- Amazon EC2
- AWS Lambda
- AWS Elastic Beanstalk
- Amazon ECS
- AWS Fargate
- Amazon Auto Scaling

### Containers
- Amazon ECS
- Amazon ECR
- AWS Fargate

### Storage
- Amazon S3
- Amazon Elastic File System (EFS)
- Amazon ElastiCache

### Databases
- Amazon RDS
- Amazon DynamoDB
- Amazon ElastiCache

### Networking & Content Delivery
- Elastic Load Balancing
- Amazon CloudFront
- Amazon Route 53

### Developer Tools
- AWS CodeCommit
- AWS CodeDeploy
- AWS CodeBuild
- AWS CodePipeline

### Monitoring & Observability
- Amazon CloudWatch
- AWS X-Ray

### Security, Identity, and Compliance
- AWS Identity and Access Management (IAM)
- AWS Key Management Service (KMS)

### Management & Governance
- AWS CloudFormation
- AWS CloudTrail
- Amazon EC2 Systems Manager

### Application Integration
- Amazon Simple Queue Service (SQS)
- Amazon Simple Notification Service (SNS)
- Amazon Simple Email Service (SES)
- Amazon API Gateway
- AWS Step Functions

### Analytics
- Amazon Kinesis

### Customer Engagement
- Amazon Cognito

```





