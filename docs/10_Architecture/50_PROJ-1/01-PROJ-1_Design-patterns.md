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
- more patterns on MS : [01_design_pattern-1.md](../03_Miscroservice/01_design_pattern-1.md)
- engine > SQS
  - l1 > py(add toekn and call jav-api)
  - sns > http (health check api, not secured)
  - eb (pipe)
    - emph SQS > filter pattern > enrich with lambda + transformation > **output**
    - ![img.png](../99_img/proj-1/01/img.png)
    - output: l, kdf, eb, **ecs+taskDefination(spawn task)**  :point_left:, sns, sqs, step function, etc
- event source mapping (sync call L + batch)
- FIFO with group == multiple call 
- SQS serverless :: autoscale
- future state -> kafka topic (as point to point) + rabbit

## 3. Idempotent consumers
- kafka/rabbit consumer.
- idempotent producer.
  - ack=0,1,all

## 4. observability

## 5. MFT
- SFTP, FTPS,HTTP
- https://chatgpt.com/c/68532518-3e2c-800d-949b-8d7e54c5fbc8


