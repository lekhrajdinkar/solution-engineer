# Step Function (serverless)
## 1. State machine
- model **workflow** as state-machine
  - from workflow studio / console UI
  - create `ASL` (Amazon State lamguage), json.
- has **states** to do some work, like
  - ![img.png](../99_img/dva/serverlessMore/01/img.png)
  - ![img_1.png](../99_img/dva/serverlessMore/01/img_1.png)
  
- **type**: 
  - standard 
  - express
  
![img_7.png](../99_img/dva/serverlessMore/01/img_7.png)

---
## 2. state
- **type**
  - `pass`
  - `choice`
  - `wait`
  - `parallel`
  - `fail`
  - **Activity task** :point_left:
    - have poll mechanism.
    - **activity worker** (lambda, ec2, ecs) polls for task (taskToken-1)
      - API : **getActivityTask**
    - send back : 
      - API : **sendTaskSuccess** / **SendTaskfailure**
        - output/error
        - taskToken-1
    - waits for worker, options:
      - option-1: worker-1, periodically send heardbeat, API: **SendtaskHeartBeat**
        - upto 1 year :point_left:
      - option-2 : configure `TimeOutSecond`
    - ![img_6.png](../99_img/dva/serverlessMore/01/img_6.png)

---
## 3. wait for task Token
- similar to activity task
- task --> depend on 3rd app response

![img_5.png](../99_img/dva/serverlessMore/01/img_5.png)


---
## 4. Error handing
- can handle in Application code
- can handle in state machine using **retry** and  **catch**
- [udemy Video ref](https://www.udemy.com/course/aws-certified-developer-associate-dva-c01/learn/lecture/26101912#overview)

### Retry
![img_2.png](../99_img/dva/serverlessMore/01/img_2.png)

### Catch
- **error/exception**
  - note: `CustomError` in screenshot : coming from Application/lambda code
```
• States.ALL        : matches any error name
• States.Timeout    : Task ran longer than TimeoutSeconds or no heartbeat received
• States.TaskFailed : execution failure
• States.Permissions: insufficient privileges to execute code
```
- ![img_3.png](../99_img/dva/serverlessMore/01/img_3.png)

- **resultpath** : input to next state
  - ![img_4.png](../99_img/dva/serverlessMore/01/img_4.png)

---
## 5. security



