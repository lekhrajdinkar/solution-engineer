# Global infra
## AWS
- virtual infra, online delivery on IT, pay by use, go-global in few minutes.
- stop guessing capcity, elastic.
- manage remotely.
- AWS Quick History
    - since 2002, SQS S3 Ec2 first 3, etc.

## Global infra : 
### regions
- choose region by avialiability, pricing, close to client, data governence.
- region-specific vs global-sevice(iam, r53, cloudFront/CDN)

### az
- isolated for DR 
- datacenters connected with low letencuy n/w.
- us-west-2a / **AWS-1**  might not us-west-2a / **AWS-2** :point_left:
  - To coordinate Availability Zones across accounts, you must use the **AZ ID**
    - usw2-az1
    - usw2-az2
    - ...
    
### edge loc
- 400+
- content caching for faster delivery.
- CDN
- service : CF:distribution, Lambda@edge, DX

### AWS outpost
-  fully managed service that extends AWS infrastructure, services, and tools to on-premises locations
-  It allows you to run AWS services locally in your data center or on-premises, 
- providing a hybrid cloud solution for workloads that need to remain on-premises due to latency, data residency, or other regulatory requirements.
- **fargate** is not supported at OUTPOST :point-left:
  - eg: ECS with ec2 launch type can run outpost, not fargate.
  
---
# Side Notes
1. All services are `publicly` accessible
2. All service has `policy`. 
   - eg: bucket-policy, sns-policy, lambda-policy, VPC-endpoint policy, etc.