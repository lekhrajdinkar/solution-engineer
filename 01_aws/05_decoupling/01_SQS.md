# SQS 
- oldest & Fully managed  
- auto-scale
- unlimited throughput,  
- low latency (< 10ms), 
- max msg size : **256KB** :books:
  - use extended-SQS, backed by s3.
  - ![img_5.png](../99_img/dva/sqs/img_5.png)
  
## A. De-couple Models in AWS
  - `queue` :  **SQS** 
    - coupled app (sync) 
    - de-couple app (Async)
  - `pub/sub` : SNS
  - `real time data-stream` : kinese Firehose
---
## B. Types
### 1. Standard 
- **multiple producer** p1,p2,p3, ...  ---> [queue:message-1(with Attribites)] ---> **multiple consumers** (C1,C2,C3, lambda-Consumer, ... )
  - can purge 
- **at least once delivery**  
  - multiple consumer can receive same message.
  - consumer handle duplicate message, has to delete message.
  - idempotent consumer, if needed.
- **best effect ordering** : order not guaranteed.
- msg **retention**: 
  - max     : `14 days` 
  - default :  `4 days`
- **visibility timeout** :o:
  - `0-12 hr`
  - consumer could call this api to get more time.
  - ![img_4.png](../99_img/decouple/sqs/img_4.png)
  - if too low, then may get duplicate.

- **Polling Type (2)**
  - **short polling** (default)
    - SQS sends the response right away, even if the query found no messages
  - **Long polling** 
    - Consumer can optionally “wait” for messages to arrive, if there are none in the queue 
    - set `message receive wait time` : (1-20 sec)
    - pattern : poll-1 API -- wait 10 sec -- poll-1 API -- wait 10 sec ...
    - long poll **preferred** : 
      - more gap in poll api calls, but increase **latency**
      - Amazon SQS sends an empty response only, if the polling wait time expires
      - save cpu cycle, save money $$ :dart:
      
- DLQ must also be a standard queue
- **Delivery delay**
-  **Throughput** : virtually unlimited messages per second. :point-left:

### 2. FIFO
- DLQ must also be a FIFO queue
- S3:event notification target --> only standard, not fifo :dart:
- **Exactly-once processing** :point_left:
- name : has suffix `.fifo`
- **no duplicate**. :books:
  - `content-based-de-duplication` : enable it
  - `de-duplication interval` : say 5 min
    - duplicate messages are for 5 min, will get refused.
  - how to check duplicate:
    - `de-duplication_ID` along with message
    - `SHA-246 hash of message body`
    - ![img.png](../99_img/dva/sqs/v1/img.png)
- Limited **throughput**: :dart:
  - default (batch size = 1) : `300 msg/s`
  - batch size =  2, 600 msg/s
  - ...
  - `max batch size =  10`, this max `3000 msg/s` 

- **ordered**
  - within group.
  - not across groups
  
- **multiple consumer** :books:
  - use grouped messages: **MessageGroupingId** (like in kafka)
  - group-1 ( msg1, msg-2, ...) --> consumer-1 (ordered in group-1)
  - group-2 ( msg1, msg-2, ...) --> consumer-2 (ordered in group-1)
  - ![img_1.png](../99_img/dva/sqs/v1/img_1.png)

### 3. Ephemeral queue
- Amazon SQS **Temporary** Queues 
- cannot be created directly from the AWS Management Console.
- They must be created programmatically using the AWS SDK or CLI.
  - `CreateQueue` API , attributes:
    - FifoQueue 
    - ExpiresAfter
- **short-lived**, designed for temporary message passing
- **Auto-delete** after inactivity.

---
## B. DLQ
- ![img_3.png](../99_img/dva/sqs/img_3.png)
- If a consumer fails to process a message within the Visibility Timeout or **Exception** from Consumer code.
  - then. message goes back to the queue and consume received again.
- After the **MaximumReceives threshold (say 3)** is exceeded,
  - message goes into a `Dead Letter Queue `(DLQ)
- Good to set a retention of 14 days in the DLQ
- **re-drive** 
  - push messages from the DLQ back into the `source queue / any other queue`
  - ![img_4.png](../99_img/dva/sqs/img_4.png)
---
## C. Security 
### general
- attach iam:sqs-policy.
  - cross account access
  - allow other service: 
    - eg: S3 Event Notifications To SQS Queue
- **In-flight encryption** 
  - `HTTPS` (with TLS)
- **At-rest encryption** 
  - KMS keys (`sse-sqs`, `sse-kms`, `sse-c`)
  - Client-side encryption :  if the client wants to perform encryption/decryption itself.

---
## D. price (cheap)
- **number of requests** 
  - Standard Queue : `$0.40 / million requests`.
  - FIFO Queue : `$0.50 /  million requests`.
- **data transfer**
  - inbound data is free
  - outbound traffic paid. ?
- Long Polling: No extra cost for long polling.
  - save cost, because it reduces the **number of empty receives** :dart:

---
## E. hands on
```
- create queue : queue-1
- Type : standard ** + FIFO
- configuration:
  - `visibility timeout` : 30
  - `delivery delay`
  - `receive message weight time`
-  encrytion : sse-sqs
- policies:
    - SQS access policy : json
    - Redrive allow policy : pending
- Dead-letter queue
- tags

// READY
- send : hellow world
- receive : poll messages + delete
- purge : delete all message.
```
- ![img_2.png](../99_img/decouple/sqs/img_2.png)

---
## F. API must know (for DVA) :books:
![img_6.png](../99_img/dva/sqs/img_6.png)
---
## G. use-case / arch eg
1. `SQS:queue:logs` >> CW >> metric >> alarm --> ASG [ ... multiple consumers ec2-i... ]
  - ![img.png](../99_img/decouple/sqs/img.png)
2. ASG [ FE-1, FE-2, ... ] ---> stage all request in Queue --- > ASG [ BE-1, BE-2, ...]
  - ![img_1.png](../99_img/decouple/sqs/img_1.png)
3. Overloaded DB request:
- ASG [ FE-1,...] --> Queue-1(Stage client request) --> ASG [BE-1,...] --> store to DB, OVERR-LOADED --> `lose some insert`
- ASG [ FE-1,...] --> Queue-1(`Stage client request`) --> ASG [BE-1,...] --> Queue-2(`stage-DB-request`) -->  ASG [BE-repo-1,...]
- ![img_3.png](../99_img/decouple/sqs/img_3.png)
- ![img_5.png](../99_img/decouple/sqs/img_5.png)

