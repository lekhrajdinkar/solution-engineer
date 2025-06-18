# SSA Dicussion

## A. Block IP to ec2-i
### 1. NACL 
- use VPC/subnet `ACL` : allow/deny rule(rule-priority)
- block a specific IP.
- ![img.png](../99_img/ssa-discussion/29-2/img.png)

### 2.1. Allow traffic from ALB only
- Again ACL has already blocked IP
- ![img_1.png](../99_img/ssa-discussion/29-2/img_1.png)

### 2.1. Allow traffic from ALB only + WAF
![img_2.png](../99_img/ssa-discussion/29-2/img_2.png)

### 3. CF with geo-restriction + WAF
![img_3.png](../99_img/ssa-discussion/29-2/img_3.png)