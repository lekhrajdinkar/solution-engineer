# AWS Global accelerator (global)

## 0. network problems
### latency
- alb-1(api-1) is running on region-1 **mumbai**
- alb-2(api-2) is running on region-2 **europe**
- client in **USA** connects: 
  - to region-1 (high latency) - path-1
  - to region-2 (low latency)  - path-2
- can optimize path to use path-2 (**intelligent-routing**) :point_left:
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
- directs traffic to optimal endpoints over 
  - the AWS global network
  - NOT internet
- supports both **TCP and UDP protocols** :point_left:
  - gaming, live video streaming, and other real-time communication applications
- integrated with **AWS-sheild** 
  - thus provides **DDoS protection** :point_left:
- uses AWS global **network infrastructure of AWS** / privateLink / edge location
  - performance 60% 
  - reduce latency
  - fast failover
  - **optimizes the path to your application** to prevent packet loss and reduce latency .
  
- provides **2 static anycast IP addresses** that act as a fixed entry point to your application endpoint/s.
  ``` 
  - The two IP addresses are mapped to multiple AWS edge locations globally. 
  - If one IP address or its associated edge location experiences an issue, 
  - the other IP address automatically takes over, ensuring uninterrupted traffic flow.
  ```
  - configure these IP addresses in DNS records, 
  - providing a single point of reference for global traffic distribution
  - eliminating the need to update DNS frequently
```yaml
- Uni-cast IP
  - one ip assigned to one server

- Any-cast IP
  - same ip assigned to multiple server
  - fixed IP,  does not support dynamic IP addresses.
```

## 2. Provision `listener`
- create `accelerator` for **our Application Endpoint**
```yaml
####  our Application Endpoint
- launch ec2-1 in region-1 (us-east-1)
- launch ec2-2 in region-2 (us-west-1)
- launch alb-1 in region-2 with ec2-3
  
####  accelerator (INTELLIGENT ROUTING on global network)
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
    - listener-2 :
        ...
        
- hit dn-1 url:
    - goes to us-east-1, ec2-2 everytime, since iam in CA,USA

#### failover
- make ec2-1 fail, update sg : deny traffic.
- -hit dn-1 url
  - goes to us-east-1,ec2-1 now
```
---
## 3. Security


 
## 4. scenario
```
#1
A gaming company is looking at improving the availability and performance of its "global" flagship application 
which utilizes User Datagram Protocol and needs to support fast regional failover in case an AWS Region goes down. 
The company wants to continue using its own custom Domain Name System (DNS) service.

---

#2
A retail company wants to rollout and test a blue-green deployment for its "global" application in the next 48 hours. 
Most of the customers use mobile phones which are prone to Domain Name System (DNS) caching. 
The company has only two days left for the annual Thanksgiving sale to commence.

As a Solutions Architect, which of the following options would you recommend to test the deployment 
on as many users as possible in the given time frame?

a. Use AWS Global Accelerator to distribute a portion of traffic to a particular deployment **
b. Use Amazon Route 53 weighted routing to spread traffic across different deployments

 >> Use AWS Global Accelerator to distribute traffic to the blue and green deployments efficiently and avoid delays caused by DNS caching
 
 ---
 
 #3
```