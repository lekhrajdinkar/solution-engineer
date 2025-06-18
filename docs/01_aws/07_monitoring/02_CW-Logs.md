- https://www.udemy.com/course/aws-certified-developer-associate-dva-c01/learn/lecture/29322962#overview
- https://www.udemy.com/course/aws-certified-developer-associate-dva-c01/learn/lecture/37910042#overview
--- 
# Cloudwatch : `logs` :books:
- **expiration** policies (defined at `log-group` level)
  - never expire
  - set 1 day to 10 years
  - CloudWatch **Logs do not expire** unless a retention policy is set. :point_left:
- logs are **encrypted** by KMS

## 1 `log group`
- for each application create a log group
- ![img_3.png](../99_img/cw/cw-1/img_3.png)

---
## 2 `Log streams`
- instance within log group
- has **log event**

---
## 3 `log insight`
- ![img.png](../99_img/cw/cw-1/img.png)
- **query engine**
  - on historical logs-group on same/**cross**  aws account
- `log query >>> get result >>> export / visualize on CW::dashboard`
  - **purpose built query** language
  - find common query example on console itself.
  - fetch event, sort event, filter event, save and add to CW::dashboard.

---
## 4 `log source`
- `SDK`
  - java/py app running on
- **ECS/EKS**
  - container-agent : collect from containers
- **AWS Lambda**
  - lambda-agent : collect from function logs 
- **VPC Flow Logs**
- **Route53** 
- more:
  - API Gateway
  - Elastic Beanstalk : internal-app-agent
  - CloudTrail 
- **EC2 / on-prem server**
  - but install `agent`
    - **CloudWatch Unified Agent** : `new`
    - **CloudWatch log Agent** : `old`
    - ![img_1.png](../99_img/dva/monitor/img_1.png)
    - ![img.png](../99_img/dva/monitor/img.png)

---
## 5 `export logs`
### via `CreateExportTask`
- destination: **Amazon S3**
  - not real time
  - batch export
  - takes up to `12 hrs`

### via `log-group subscription-filter`
- **KDS** Kinesis Data Streams
- **KDF** Kinesis Data Firehose
- **Lambda**
- **OpenSearch**
- ![img_2.png](../99_img/cw/cw-1/2/img_2.png)

---
## 6 `log subscription-filter`
- can create `max=2`
- **subscribe** to **log group**
  - also, can also to listen/subscribe to multiple same/cross account's log group 
  - and perform **log aggregation**:
  - ![img_2.png](../99_img/cw/cw-1/img_2.png)
  - ignore above action, check below.
  
- **listener action/s**:
  - do some processing with Lambda
  - deliver (KDF)
  - stream (KFS)
  - ![img_1.png](../99_img/cw/cw-1/img_1.png)

--- 
## 7 `live trail`
- for debugging
- select log-group > log-instance (optional) > start trail
- 1 hr free every day
- close after done.

--- 
## 8 `metric filter`
- log-group > create **metric** on pattern-1
  - `max=3` **dimension**
- later on create alarm around this metric. [03_CW-Alarms.md](03_CW-Alarms.md)
- ![img.png](../99_img/dva/monitor/img3.png)

--- 
## 9 `encrypt log`
- at **log-group** level
- can create loggroup with KMS encryption
- can also associate existing loggroup with kms key :point_left:
  - using CLI
    - **aws logs associate-kms-keys** --log-group-name --kms-key-id --region
  - not from console.
- Dont forgget to update key policy as well, to allow loggroup.
- ![img.png](../99_img/moreSrv/dva/img.png)

--- 
## ??
- **dashboard**
  - insight-rule-1 to ingest dashboard data
  - insight-rule-2
  - ...
- powered by `sagemaker`
- eg:
  - **lambda-insight**,
  - **CW-container-insight**
  - **app-insight**
  - **cw-contributor-insight**
    - build from `VPC logs`. etc
    - find heaviest n/w user, urlWithMostError, IPs,

---
## 99. hands on
```
- create lambda 
- run multiple time
- lambda "log-group-1" created :
    - log instance-1
    - log instance-2
    
// actions on  log-group-1 :
// 1. export to s3

// 2. create subscription-filter (max=2)
action: KDF > S3
    
// 4 . create log-stream-1 from log-group-1 > start live tail
...
```