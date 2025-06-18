# SNS 
## A. key point
- **pub-sub model**
- ![img.png](../99_img/decouple/sns/img.png)
- `100k` topics, per account, can be extended.
- **type**
  - Standard 
  - FIFO
- **subscription**
  - max: 12 million
  - subscription with **filter policy** 
    - filter message by **message attribute**
    - ![img_6.png](../99_img/decouple/sns/img_6.png)

---
## B. Subscriber/s 
- KDF
- ![img_1.png](../99_img/decouple/sns/img_1.png)

---
## C. publisher/s
- ![img_2.png](../99_img/decouple/sns/img_2.png)

---
## D. Security
### general
- attach iam:sns-policy.
  - cross account access
  - allow other service
- **In-flight encryption**
  - `HTTPS` (with TLS)
- **At-rest encryption**
  - KMS keys (`sse-sqs`, `sse-kms`, `sse-c`)
  - Client-side encryption :  if the client wants to perform encryption/decryption itself.

---
## E. use case
- SQS + SNS **fan out pattern**
  - ![img_3.png](../99_img/decouple/sns/img_3.png)
  - ![img_4.png](../99_img/decouple/sns/img_4.png)
  - ![img_5.png](../99_img/decouple/sns/img_5.png)
