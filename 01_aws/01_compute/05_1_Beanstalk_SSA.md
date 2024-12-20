- https://chatgpt.com/c/6763df4d-2998-800d-aea7-4430e4d96fc5
--- 
# Elastic Beanstalk 
## A. Common **Web** Architecture
![img.png](../99_img/dva/beanstalk/01/img.png)

## B. Intro
- Managed services, deals with:
  - **Managing infrastructure**
    - databases, `RDS`
    - load balancers, `ELB` + networking
      - ELB type cannot be changed once provided :point_left:
      - if ALB, then cannot change to NLB.
    - scaling concerns, `ASG`
    - Monitoring
    - ...
    - note: create **cloudFormation** template bts for infra.
    
  - **Deploying Code**
    - into multiple env, 
    - compute: 
      - primarly EC2 
      - ECS under the hood for container App
    
- Basically from central place, dealing with all these services, being used commonly for web development. :point_left:
  - **developer focus on code**
  - rest all beanStalk will do `infra + deploymnet`

## C. pricing
- `Free` 
- but pay for underlying infra.

## D. Elastic Beanstalk : `Application`
### D.1 **Environment/s**
- create multiple `dev, prod, qa`
  - can **clone** env as well
- it represents infra which are running our application version.
- Type/tier:
  - **web-server**  Environment
    - can send traffic to worker
  - **worker** Environment
    - for long-running task,jobs,scheduledJobs
  - ![img.png](../99_img/compute/img.png)
  
### D.2 **configuration (for env)**
- **deployment** 
  - **deployment mode**:
    - [05_2_Beanstalk_DVA-deployments.md](05_2_Beanstalk_DVA-deployments.md)
    
  - **availability**:
    - **single instance** (with/without using spot)
    - **High availability** (with/without using spot+od)
    - ![img_1.png](../99_img/compute/img_1.png)
  
- **Networking**
  - choose VPC and subnet
  - public IP for ec2.
    - `enable` for single ec2
    - `disable` for multiple, since we wil ALB to expose.
  - ELB 
    - can be shared with all env, to save some cost. :point_left:
    - security groups
  - ASG - min, max, trigger(metric,alarm)
  
- **Database**
  - DB will get link with beanstalk env lifecycle.
  - if env is deleted, database will be deleted too. :point_left:
  
- **Monitoring**
  - X-ray
  - logs
  - metrics

### D.3 **Application version** 
- define **platform** 
  - language 
  - runtime : can use Docker :point_left:
- upload code (`zip`)
  - code, v1
  - code, v2
  - ...
  - code, `v1000` (max)
  - use s3 bts for storing zip :point_left:
- **Application version lifecycle**
  - enable it, To phase out old application versions.
  - Option not to delete the source bundle in S3 to prevent data loss

      
---
## Z. Hands on
```yaml
- Create Application
  - create environment : dev
    - choose type/tier: web | worker(job,long-running-task)
  - choose platform : language, runtime,etc 
  - code: upload it.
  - deployment mode:
    - single EC2 with ASG 
    - high availabilty - multiple EC2 with ASG
  - iam role for permission:
      - `AWSElasticBeanStalkWebTier`,
      - `AWSElasticBeanStalkWorkerTier`, 
      - `AWSElasticBeanStalkMultiContainerDocker`
      - ...
  - Skipped and using default
    - Networking
    - Database
    - Monitoring
  
# === DONE === 
  
- check cloudFormation stack (composer UI)

- get domain url and use it.
- upload new code, seamlessly.
- check other tab : CW, monitor, mmanged updates, alarms, health, etc
- Check `configuration` link on left
  - show all the configs for env
  - edit them

```
