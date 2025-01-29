- https://chatgpt.com/c/b1fe7e08-270f-4a92-a4db-b95e6beab7c7
 
---
# ASG
- performs 2 things
  - **scaling** in/out
  - **replacements** / **mask any instance failures**. (after grace period, default = `300 s`): :point_left:
    - **ec2 health check** / **impaired status**
      - Network connectivity issues.
      - Exhausted instance resources (CPU, memory, or disk).
      - Corrupted or inaccessible boot volume.
      - if instance is automatically recovered,then  :point_left: :dart:
        - identical to the original instance, including the instance ID, private IP addresses, Elastic IP addresses, and all instance metadata
        - If instance has a public IPv4 address, it retains.
        
    - **alb target - health check api**
      - by-default not configured :point_left: :dart:
    - cannot define any other custom health check :point_left:
- **regional** ( asg span over AZs)

## -1. heath check/s :dart:
- choose **grace period**
- can configure ASG health check with:
  - ELB check
  - ASG's EC2 check
  - **both**
    - ELB health checks take precedence
    - also, if ELB health check is not available or ASG can't reach ELB,  
    - then ASG health will considered.
---
## 0. Rebalancing  Activity :dart:
- https://docs.aws.amazon.com/autoscaling/ec2/userguide/what-is-amazon-ec2-auto-scaling.html
- https://docs.aws.amazon.com/autoscaling/ec2/userguide/auto-scaling-benefits.html
- https://docs.aws.amazon.com/autoscaling/ec2/userguide/as-instance-termination.html
- https://docs.aws.amazon.com/autoscaling/ec2/userguide/healthcheck.html

### AZ Rebalancing 
-  **Actions** can lead un-balance:
```
  - changing the Availability Zones (AZ) for your group 
  - or explicitly terminating/detaching instances
  - AZ with insufficient capacity, recovered
  - AZ with spot instance ( pricing matched :) 
```
  - launches new instances, 
  - can go maz 10% beyond max capacity, temporly
  - before terminating the old ones. () :point_left:
  - **reverse order** of regular scaling activity, which 
    - first terminates unhealthy
    - then launch new
  
### capacity Rebalancing
- launch new spot instance
- terminate old spot instance which is at risk of interruption.

---
## 1. Trigger
  - **CloudWatch::metric** --> **alarm** --> ASG :: scale in/out
    - CPU, memory, network, RequestCountPerTarget, custom-metric, etc
  - asg works in conjunction with `ELB`
    - if ELB::health-check fails, ASG will terminate corresponding target instances.
    - not scaling, but replacing unhealthy targets.
---
## 2. Cooldown Period
  - pausing further scaling actions for a specified amount of time, after a previous scaling activity completes.
  - this allows the system to **stabilize** before initiating another scaling operation.
  - so during this time, does not add/drop new instances.

---
## 3. Scaling types
- ref:
  - https://docs.aws.amazon.com/autoscaling/ec2/userguide/scaling-overview.html
  - https://docs.aws.amazon.com/autoscaling/ec2/userguide/as-scale-based-on-demand.html
- **a Dynamic**: 
  - a.1 `Simple scaling` :
    - create trigger(CW:Alarm) + define :: ( **single action** + set cooldown-period )
    - demo : created one and link with tg. count : `desired, min, max`.
    - single step : cpu 70 : add 3 instance.
    - before responding to additional alarms, **waits for**:
      - the current scaling activity 
      - health check replacement to complete 
      - and the cooldown period to expire 
    
  - a.2 `Step scaling` 
    - create trigger(CW:Alarm) + define :: ( **different/many fine-tuned action actions** + set cooldown-period )
      - if CPU utilization is slightly above the threshold, add 1 instance; 
      - if it is far above, add 3 more instances
      - ...
      
  - a.3 `target tracking Scaling`  **recommended** :dart:
    - react fast :dart:
    - define only **target value**. eg: 50% of **aggregate** (CPU,memory,network) utilization
      - target : fleet of 10 ec2-i
      - keep  aggregate cpu utilization 50%, else scale in/out :dart:
    - Also remove the need to manually define :dart:
      - CloudWatch alarms 
      - scaling adjustments
    
- **b scheduled** : 
  - eg: scale up/down to max/min count on weekends.
  - scheduled action sets the minimum, maximum, and desired sizes :point_left:

- **c predictive** : 
  - continuously `forecast` load and schedule scaling ahead of time.
  - Easy to create. once created ait for Week. 
  - `ML` will be applied on historic data.

---
## 4. Instance launch settings
- **Launch template** :point_left:
  - more modern and flexible way 
  - Editable/mutable: Launch Templates allow versioning
  - EC2 details (AMI, OS, Role, **tenancy**, etc), 
  - **more flexible** - mix of purchasing options :dart:
    - eg: spot instance, on-demand,etc
  - Configure `user data` for automation, during instance initialization.
  
- **Launch Configurations**
  - **Immutable** - replace entire template if changes needed :dart:
    - It is not possible to modify a launch configuration once it is created.
    - Dont get confuse with LT
  - simpler but less flexible -  does not multiple instance types like od, spot, etc

### scenario-1(tenancy) :dart:
``` 
 # host >> dedicated >> default
- Launch Template LT1 (Dedicated Tenancy)
  - VPC tenancy (default) 
- Launch Template LT2 (Default Tenancy)
  - VPC tenancy (dedicated) 
  
>> If either "Launch Template Tenancy" or "VPC Tenancy" is set to dedicated, then the instance tenancy is also dedicated
 
=== MORE RULES ===  

- VPC Tenancy Takes Precedence
- Cannot Downgrade Tenancy
- Instances in different tenancy types (shared vs. dedicated) cannot communicate within the same VPC using private IPs
```  
---
## 5. scale-down: `Default Termination Policy` :dart:
- order:
  - AZ with the most instances is selected for termination
  - Instances in the `Standby` status
  - instance launched with oldest version of launch-configuration/template.
  - oldest age
  - the instance(s) nearest the end of their billing hour. (like reserver period is close to end.)

---
## 6. Instance refresh (like k8s deploymnet ) :books:
- update ec2-i with new launch template version.
- **rolling Updates** : Replaces instances incrementally to avoid downtime.
- desired capacity is maintained : specify minimum healthy %
- can pause, resume, or cancel an instance refresh if necessary.
- specify **warm up time** : wait times for instance stabilization
```
aws autoscaling start-instance-refresh --auto-scaling-group-name <ASG-name> --preferences <json>

{
    "MinHealthyPercentage": 90,
    "InstanceWarmup": 300
}

# monitor
aws autoscaling describe-instance-refreshes --auto-scaling-group-name <ASG-name>
```
---
## 7. Maintenance
- **scenario**: :dart:
  - some maintenance work on a specific Amazon EC2 instance that is part of an ASG
  - every time the team deploys a maintenance patch
    - the instance health check status shows as out of service for a few minutes. 
  - This causes ASG to provision another **replacement** instance immediately
  - **solution**
    - Put the instance into the **Standby state** :point-left:
    - once  patching done, put instance to **in-service**

---
## 8. ASG  lifecycle hook
- perform custom actions as the Auto Scaling group launches or terminates instances
- eg:
  - install or configure software on newly launched instances
  - download log files from an instance before it terminates
  - thus, can save state of workload.

---
# keys Terms
