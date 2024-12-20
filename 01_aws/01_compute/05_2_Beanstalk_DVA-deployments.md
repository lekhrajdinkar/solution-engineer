# A. Elastic Beanstalk : `deployments`
- mode of deployments:
## 1. All at once
- **fastest**  deployment
- issue: has  **downtime**
- updates same instance :point_left:
  - ASG-1[ec1-i1:`v1`, ec1-i2:`v1` , ...]
  - ASG-1[]
  - ASG-1[ec1-i1:`v2`, ec1-i1:`v2` , ...]
- ![img_1.png](../99_img/dva/beanstalk/01/img_1.png)

## 2 Rolling
> fixing: downtime
- **Long** deployment
- steps:
  - update same instances at a time (by bucket-size), 
  - once the bucket is healthy, move to next bucket...
- ![img_2.png](../99_img/dva/beanstalk/01/img_2.png)
  - notice: v1 and v1 - **both version**s are running
- issue:
  - **rollback takes time**
  - **under capacity**

### Rolling (with additional batches)
> fixing: under capacity
- same like rolling, but application is running with **desired capacity**
  - becoz of additional batch.
  - also Small **additional cost** for a additional batch
- ![img_3.png](../99_img/dva/beanstalk/01/img_3.png)

## 3 Immutable 
> fixing: rollback time
- steps:
  - spins up new instances in a new **temporary ASG**, 
  - deploys new version to these new instances,
  - then **swaps** all the instances
  - ASG-1[v1,v1,...]
  - ASG-temp[v2,v2,...]
  - swap: ASG-1[v1->v2,v1->v1,...]
- ![img_4.png](../99_img/dva/beanstalk/01/img_4.png)
- **Quick rollback**

## 4. Traffic Splitting
> fixing : swapping of instance
- steps
  - ASG-1[v1,v1,...]
  - ASG-2[v2,v2,...]
  - point ALB to ASG-2 eventually after testing small traffic
- very quick **automated rollback**

## 99. Blue Green (not direct feature)
- steps:
  - create a **new beanstalk stage `environment`** (green)
  - deploy new version into green
  - Traffic Splitting using `R53`
    - send a small % of traffic to new deployment
  - switch over when ready
- ![img_5.png](../99_img/dva/beanstalk/01/img_5.png)

## summary
![img_6.png](../99_img/dva/beanstalk/01/img_6.png)

---
# B. Elastic Beanstalk : `CLI`

---
# C. Elastic Beanstalk : `lifecycle`

---
# D. Elastic Beanstalk : `Extension`

---
# E. Elastic Beanstalk : `cloning`

---
# F. Elastic Beanstalk : `Migration`
