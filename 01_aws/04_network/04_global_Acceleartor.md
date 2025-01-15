# AWS Global accelerator (global)

## 0. network problems
### latency
- alb-1(api-1) is running on region-1 **mumbai**
- alb-2(api-2) is running on region-2 **europe**
- client in **USA** connects: 
  - to region-1 (high latency) - path-1
  - to region-2 (low latency)  - path-2
- can optimize path to use path-2 (**intelligent-routing**)
  - to reach out closest target server

### server hopping
- alb-1(api-1) is running on region-1 **mumbai**
  - client: 
    - **USA** --> hops overs multiple server/s s1 > s2 > s3 > s4 > s4 --> to reach alb-1
    - **europe** --> hops overs multiple server/s s1 > s2 > s3 --> to reach alb-1
    - **nepal** --> hops overs few server/s > s1 --> to reach alb-1
- this creates risk of **loss of network and data packet**. 
- ![img.png](../99_img/CF/ga/img.png)

- can prevent hopping by using stable **AWS global privatelink**
  - ![img_1.png](../99_img/CF/ga/img_1.png)
---
## 1. Intro
- **UDP traffic** support : no :point_left:
- uses AWS global **network infrastructure of AWS** / privateLink / edge location
  - performance 60% 
  - reduce latency
  - fast failover
  - **optimizes the path to your application** to prevent packet loss and reduce latency 
- provides **2 static anycast IP addresses** that act as a fixed entry point to your application endpoint/s.
```yaml
- Uni-cast IP
  - one ip assigned to one server

- Any-cast IP
  - same ip assigned to multiple server
  - fixed IP,  does not support dynamic IP addresses.
```

## 2. Provision
- create `accelerator` for **our Application Endpoint**
```yaml
####  our Application Endpoint
- launch ec2-1 in region-1 (us-east-1)
- launch ec2-2 in region-2 (us-west-1)
- launch alb-1 in region-2 with ec2-3
  
####  accelerator
- create accelerator-1 : has dn-1               <<<<
- create Listener/s :
    - listener-1 : 
        - TCP:80
        - endpoint-group -1 (weight - w1 ):     <<<< 
            - endpoint-1 : ec2-1 
            - endpoint-2 : ec2-2
            - endpoint-3 : alb-2
            - endpoint-4 : static-ip 
        - endpoint-group -2 (weight - w2 ):  
          - ...
          - ...
        - setup health as well, for each endpoint.
- hit dn-1 url:
    - goes to us-east-1,ec2-2 everytime, since iam in CA,USA

#### failover
- make ec2-1 fail, update sg : deny traffic.
- -hit dn-1 url
  - goes to us-east-1,ec2-1 now
```

---
## 3. Security
- integrated with **AWS-sheild**
  - thus provides **DDoS protection**

--- 
## 4. shifting traffic in `Blue/green deployment`
- **Blue/green deployment** 
  - technique for releasing applications by **shifting traffic** between two identical environments,
  - running different versions of the application: 
    - "Blue" is the current version 
    - "green" the new version.
  - This type of deployment allows you to test features in the green environment without impacting the currently running version of your application. 
- Use AWS Global Accelerator to distribute a portion of traffic to a particular deployment :point_left:
 
## 5. scenario
```
A gaming company is looking at improving the availability and performance of its global flagship application 
which utilizes User Datagram Protocol and needs to support fast regional failover in case an AWS Region goes down. 
The company wants to continue using its own custom Domain Name System (DNS) service.
--- sol ---
AWS Global Accelerator improves performance for a wide range of applications over TCP 
or UDP by proxying packets at the edge to applications running in one or more AWS Regions. 
Global Accelerator is a good fit for non-HTTP use cases, such as gaming (UDP), IoT (MQTT), 
or Voice over IP, as well as for HTTP use cases that specifically require static IP addresses or deterministic, 
fast regional failover.
```