# SSA Discussion

## Architecture 
### 1. SQS, SNS and lambda with DLQ
- after retry, move to DLQ
- ![img.png](../99_img/ssa-discussion/29-1/img.png)

### 2. SQS SNS : Fan-out pattern
- ![img_1.png](../99_img/ssa-discussion/29-1/img_1.png)

### 3. S3 event
- ![img_2.png](../99_img/ssa-discussion/29-1/img_2.png)

### 4. event bridge and event rules
- rule give advance filter options
- can combine/have multiple rule and multiple targets
- `Archive`, `Replay` Events
- ![img_3.png](../99_img/ssa-discussion/29-1/img_3.png)

### 5. CT log/event
![img_4.png](../99_img/ssa-discussion/29-1/img_4.png)

### 6. more-1
![img_5.png](../99_img/ssa-discussion/29-1/img_5.png)

### 7. cache Strategies
![img.png](../99_img/ssa-discussion/29-1/img-cache.png)