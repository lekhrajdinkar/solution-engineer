## A. docker
- [02_docker](..%2F..%2F02_docker)
- **machine** vs **virtual machine** vs **container** (partial virtualization : share OS, n/w)
- image --> run on docker-agent --> **Container** 
  - docker-demon / container-d
  - ecs-agent on ec2-i
- container mgt tool in AWS : 
  - container orchestration : ecs and eks(k8s)
  - image repo : ecr, 
  - launch type : fargate or ec2
- ![img.png](../99_img/compute/ecs/img.png)

--- 
## B. k8s vs ECS
- Analogy with k8s.
  - `eks cluster` - **ecs cluster**
  - `worker-node` -  fargate or **ec2 nodes**.
  - `pod`(c1) - **task**(c1) or **service**[task]
  - `pod/rs/deployment` manifest yaml - **task definition**
  -` control panel/master node`  - ?
  - `service` - **ALB** --> tg --> asg [task-1]
    - desired task = 1
    - max task count = 3
  - `task schedular` - ?

---
## C. ECS  (elastic container service)
- For fargate launch, don't think underlying ec2-i/s
- Configure ASG to scale out the  ECS cluster when the **ECS service's CPU utilization** rises above a threshold :dart:
  - configure a target tracking scaling policy
  - metric: `ECSServiceAverageCPUUtilization`
- ECS-cluster (with launch type = ec2)
  - EC2-i1 (`docker-agent`) 
    - task-1 (c1)
    - task-2 (c2)
    - ...
  - EC2-i2 : task-11, task-22, ...
  - EC2-i3 : task-111, task-222, ...
  - ...

### pricing :dart:
- **Fargate launch type**
  - you pay for the amount of **vCPU and memory** resources that your containerized application **requests**.
  - **from** the time your container images are pulled 
  - **until** the Amazon ECS Task terminates
  - rounded up to the nearest second. 
  
- **EC2 launch type** 
  - there is no additional charge for the EC2 launch type.
  - pay for AWS resources 
    - EC2 instances 
    - EBS volumes
    
### ECS Demo:(launchtype = ec2)
#### 1 create **cluster-1** 
  - launchType : ec2 (worker nodes)
  - has **ECS-Cluster capacity provider** :point_left:

#### 2 create **task-definition-1** : `json` metadata
  - **resources** 
    - os
    - cpu, ram
  - **task-role(1)** : attach to ecs-agent  (permission - ecr, cw, ecs)
  - **task-exec-role(each definition)** : attach to task.
  - **container/s**  : `max 10` :point_left:
    - `image uri `
    - `port mapping`
      - container-port
      - host-port: 
        - for ec2 launch:: if 0, then get dynamic port and alb finds it.  :point_left:
        - for fargate launch :: not needed.
    - `env var`
      - hardcode
      - read from SSM store / secret manager
      - bulk fetch env file from s3 :o:
      - ![img.png](../99_img/dva/compute/ecs/img.png)
    - `storage` : EFS or default(21GB EBS)
      - ec2-i storage : **bind mount** to c1,c2,etc
      - for fargate use ephemeral storage. ? 
      - ![img.png](../99_img/dva/compute/ecs/img_1.png)

#### 3 **run task**:
  - task (for job) **directly**,  
  - or wrap task with **service** (for long-running web-app) : srv1:[t1,t2,t3]
    - service name - `service-1`
    - choose :  task-definition :`task-definition-1` **
    - Desire capacity : 2 tasks - task-1(c1), task-2(c2)
    - Define **networking**
      - choose `subnet/VPC`
      - create `sg` : allow traffic http,etc --> this will attach to ec2-i or `hidden-ec2-i/in-fargate`

#### 4 **expose** task/service 
- create **ALB-1**
  - health check for tg
  - listener(http:80)  --> tg-1 --> [  task-1(c1), task-2(c2) ]
- host-port : if 0, then get dynamic port and alb will find those  :point_left:
- ![img.png](../99_img/dva/compute/ecs/img_2.png)
- ![img_1.png](../99_img/dva/compute/ecs/img_3.png)
  
#### 5 Auto Scaling 
- create **ASG-1** to scale up/down task 
- For ec2 launch
  - option-1 (`ASG-1`) : CW --> metric(CPU,etc) --> `ASG`(task)
  - option-2 (`ECS-Cluster capcity provider`): preferred to use, smart, better.
- For fargate: easy
  - ECS-Cluster capacity provider : intelligent to do everything.

#### 6 task placement (ec2 launch type)
- note: ec2 instance role : add permission to pull image from ecr
- ![img_1.png](../99_img/dva/compute/ecs/img_4.png)
- statisfy:
  - **1 CPU, memory, and port requirements**
  - **2 Task Placement Strategy**
    - `Binpack` : fill ec2-i1 first, then ec2-i2, etc
    - `random` 
    - `spread` : 
      - Tasks are placed evenly based on the specified value
      - eg: instanceId, az, etc
  - **3 Task Placement Constraints**
    - `distinctInstance` : different EC2 instances
    - `memberOf` :  member Of **expression** (`CQL` - cluster query language - advance)
      ```
      "placemnetConstraints :[
            {
                "type": "memberof"
                "expression" : "attribute:ecs.instance-type =~ t2.*"
            }
      ]
      ```
#### 7 ECS networking mode :point_left:
```yaml
        #### Bridge Mode (for EC2 launch type)

- Default Docker networking mode.
- Containers share the host’s network namespace but get mapped ports.
- Good for traditional containerized apps but lacks direct IPs.

        #### Host Mode (for EC2 launch type)

- Containers use the host’s network directly (no port mapping).
- Lower latency but can cause port conflicts.

        #### Awsvpc Mode (for both EC2 & Fargate) (Recommended)

- Assigns a dedicated ENI (Elastic Network Interface) to each task.
- Provides full networking features, including security groups and VPC routing.
- Required for Fargate tasks.

        #### None Mode

- Disables networking (containers have no external network access).
- Used for isolated workloads.
```

####  READY :green_circle:
- check cluster > task > container, logs/event,
- update service - manually update Desire capacity : 5
- in prod, service Auto Scaling (ASG, ECS-Cluster capcity provider) will do same.

#### 8 update task
- `rolling update` 
  - **default**:
    - min healthy: 100%
    - max : 200%
- more example for understaning:
  - **min : 50% and max: 100%**
    - ![img.png](../99_img/dva/compute/ecs/img_10.png)
  - **min : 100% and max: 150%**
    - ![img_1.png](../99_img/dva/compute/ecs/img_11.png)

---      
## D. screenshot
### 1. launch type:
![img_1.png](../99_img/compute/ecs/img_1.png) 
![img_2.png](../99_img/compute/ecs/img_2.png)
### 2. iam roles/policies:
![img_3.png](../99_img/compute/ecs/img_3.png)
### 3. alb
![img_4.png](../99_img/compute/ecs/img_4.png)
### 4. Storage:
![img_5.png](../99_img/compute/ecs/img_5.png)
### 5 scale : ASG + Ecs-cluster capcity provider
![img_6.png](../99_img/compute/ecs/img_6.png)

--- 
## E. trigger ecs task
### 1 with eventBridge ( trigger )
![img_7.png](../99_img/compute/ecs/img_7.png)
### 2 with eventBridge ( scheduled )
![img_8.png](../99_img/compute/ecs/img_8.png)
### 3 with SQS + autoScale
![img_9.png](../99_img/compute/ecs/img_9.png)

---
## F.alerts
![img_10.png](../99_img/compute/ecs/img_10.png)

---

## exam scenarios :dart:
- For **ECS Anywhere** on an on-premises Linux server, you need to **install** the following **agents**:
```text
SSM Agent     – Allows AWS Systems Manager to **manage** the server remotely <<<
ECS Agent     – Connects the server to the Amazon ECS control plane for task scheduling.
Docker Engine – Required to run containerized workloads.
```

- **EKS Anywhere**: 
  - Runs Kubernetes clusters on on-premises infrastructure using `bare-metal` or `VMware-vSphere`. 
  - It provides full lifecycle management for Kubernetes clusters.
  - bare-metal, Runs directly on physical servers **without a hypervisor.** || best for cost and performance :point_left:
  - VMware-vSphere, Runs on **virtualized** infrastructure using ESXi hypervisor.
  - https://aws.amazon.com/blogs/containers/introducing-amazon-ecs-anywhere/

- **EKS Distro** (EKS-D) is 
  - the open-source Kubernetes distribution used by Amazon EKS. 
  - It allows you to run self-managed Kubernetes clusters on any infrastructure, including 
    - on-premises 
    - and other clouds :point_left:
---
- Deploy ECS on **AWS outpost** 
  - better latency and connectivity.
  - supports only `EC2-launch-type`.
---

