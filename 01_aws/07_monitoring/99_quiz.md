# DVA
## 1 
- You have a couple of EC2 instances in which you would like 
- their **Standard CloudWatch Metrics** to be collected every **1 minute**. What should you do
- :point_right: 
  - `enable Detailed monitoring` paid
  - `enable basic monitoring` free, enabled by default
    - every 5 min

---
# 4
```
You have an application hosted on a fleet of EC2 instances managed by an Auto Scaling Group that you configured its minimum capacity to 2. 
Also, you have created a CloudWatch Alarm that is configured to scale in your ASG when CPU Utilization is below 60%. Currently, 
your application runs on 2 EC2 instances and has low traffic and the CloudWatch Alarm is in the ALARM state. What will happen
```
- The number of EC2 instances in an ASG can not go below the minimum capacity
- remain in ALARM state  :point_left:
