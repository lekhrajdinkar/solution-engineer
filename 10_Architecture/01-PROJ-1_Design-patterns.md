# A. Design pattern/s
## 1. proxy pattern
- s3 file dropped > lambda1 (s3 event)
- **lambda act as proxy**
- process event data and decide which api to call
  - sync: java-api-1 (prepared data-1)
  - ...
  - Async: java-api-2 (prepared data-1) > write response to 
    - dynamoDB
    - SQS
    - Aurora + eventBus event **

## 2. Event-driven microservices using Lambda and EventBridge
- s3 file drop > l1 > java-api-1 > sqs-fifo > l2 > java-api-2 > event-bus-1 > process-api > sqs-2
- more patterns on MS : [01_design_pattern-1.md](03_Miscroservice/01_design_pattern-1.md)

## 3. Idempotent consumers
- kafka/rabbit consumer.
- idempotent producer.
  - ack=0,1,all

## 4. 

## 5.

---
# B. Issue and fixes
## list of items
- vpc endpoint
- handle concurrency with fifo queue.
- Extended sqs 256
- MRAP
- prepared initial draft of DR.
- IAM based taken expired in 15 min
- lambda layer > terraform
- token refresh lambda
- SWIFT - driver program
- ETL driver program : flask + unicorn
  - Design
  - poc program.

## proposals
- x-rays + x-ray sdk
- archive loc : s3 URI + built angular UI
- otel + dataDog
