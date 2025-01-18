# ELB (regional)
- **DNS name** : `XXXX.region.elb.amazonaws.com` 
- public IP might change :point_left:
- https://docs.aws.amazon.com/elasticloadbalancing/latest/application/introduction.html

## 1. Proxy server with additional feature
- sits b/w client and backend-server. **hides** the backend server's IP address.

- **forwards** client requests to the appropriate backend server based on configured rules in `balanced distribution way`.
  - Content-Based Routing (url, queryparam,etc)
- **gateway** : offers a synchronous decoupling of applications
- client **Session** Stickiness
  - Enforce stickiness with cookies
- integrated with ACM, WAF to add **security**. 
  - Termination of SSL/TLS at the ELB level
  - allowing it to decrypt and inspect incoming traffic before forwarding it to the backend instances.
  - separate `public-traffic` and `private-traffic`
- also act as **reverse-proxy**
  - it forwards client requests to backend servers and sends responses from those servers back to the clients.
  
## 2. Cross-Zone Load Balancing 
- `mutli-AZ`(span over AZs), forwards traffic to multiple ec2 in different AZs.
- if az-1 has more instances running, most traffic must forward to az-1
- ![img.png](../99_img/ec2/im-3.png)

## 3. health-check
- At **tg-level**. forwards traffic to healthy tg.
- **Grace Period** : helps to avoid **premature health check failures**.
- impaired status of EC2 
  - OS check, n/w status failed on Ec2 - failed
  - **ASG** marks unhealthy, replace it.

## 4. Security group
- 2 level of SG:
  - sg-elb-1 
  - sg-ec2-i1 
  
## 5. integration
- WAF 
- ACM 
  - cert-1 for domain-1
  - cert-2 for domain-2, 
  - ...  
  - **SNI** helps to load single Cert.
- **route-53** (internet) + **Global-Accelerator** (aws private n/w) :point_left:
- Cloudwatch

## 6.Client Stickiness with cookies
- storing session data on ec2-i/tg
- may create **imbalance** :point_left:
- ![img_1.png](../99_img/ec2/im-2.png)
- alternative approach
  - use stores session data on elastiCache with TTL. 
  - [03_ElastiCache.md](../03_database/03_ElastiCache.md)
  
## 7.Types (3)
- Classic LB (deprecated) :x:
- **ALB** 
  - operate at `layer 7 : HTTP,HTTPS, websocket`
- **network LB** 
  - operate at `layer 4 : TCP, UDP, TLS`  
  - very low latency, fast
  - million of request
  - gaming
- **gateway LB** (in 2020) 
  - provides advance security
- check more detail below:
---
### 7.1 ELB : ALB - Application LB (`layer 7`)
- example flow:
  - `HTTP/S` request comes client with IP-1 to ELB
  - ELB has integration ACM, WAF, etc
  - adds extra header in http : `X-forwarded-for` : client ip
  - **rewrites** the destination IP address 
  - forward `HTTP` to 
    - **target-group** (one or many)
    - redirect
    - fixed-http-response
    
- **target-group**
    - LB >> tg [EC2-I1, EC2-I3,...] : `VM`
    - LB >> tg [VM-1 [docker-1, docker-2, ...]] : `containers`
    - LB >> tg [lambda-1, lambda-2]
    - LB >> tg [ip address] : `on-prem server IPs`
  
- **Listeners**
  - listens incoming traffic and appli **forwarding rule** and forward to tg
  - **content-based routing** :dart:
    - at path 
      - route/path/url-1 --> tg-1
      - route/path/url-2 --> tg-2
      - ...
    - at query-param  
      - /url-1?`plateform=mobile` --> tg-1
      - /url-1?`plateform=desktop` --> tg-2
      - ...

- **Cross-Zone Load Balancing** : `free`, enable for ALB

- **registration delay** :point_left: :dart:
  - (old name : Connection Draining)
  - feature of load balancers that ensures **active requests** are completed before **instances** are deregistered / terminated
  - prevents disrupting in-flight requests and ensures a smooth user experience
  - default : `300 sec / 5 min` : allow 5 min to drains
  - max : `3600 sec` / 1 hr
  - make `0 to disable`
  - **if low** like 5 sec, then:
    - ec2-i will terminate fast, and all active clients session might lost,
    - and assign to new instance on subsequent req.

---
### 7.2 ELB : NLB - Network LB (`layer 4`)
- **fast**: handles millionsOfReq/Second.
- **ultra-low latencies**
- **automatically scales** to handle the vast amounts of incoming traffic
- operates at `layer 4` 
  - TCP, UDP, TLS 
  - cannot facilitate **content-based routing** like in ALB :dart:
- health-check support multiple-protocol : `http, https, TCP`
- expose a **fixed IP** to the public web 
- **no sg** :point_left: alternatives:
  - so add sg to EC2-i or tg
  - or add network access control lists (NACLs)
  
- **use-case**
  - applications that need fixed IP addresses. `AWS assign static-IP to ALB, one for each AZ`.
  - ideal for TCP/UDP Applications.
  - microservices architectures.
  - gaming and streaming services.

- NLB target group
  - ELB/ALB :point_left:
  - EC2 instances
  - IP Addresses

- **Cross-Zone Load Balancing** : disable by default, `paid` :point_left:

---
### 7.3  ELB : GWLB - gateway LB (`layer 3`)
- (layer 3 of OSI) IP packets.
- **3rd party security instance**:
  - Deep packet inspection
  - payload manipulate
  - ...
- uses protocol-GENEVE, port-6081 
- **Cross-Zone Load Balancing** : disable by default, `paid` :point_left:
- ![img.png](../99_img/ec2/im-1.png)

---

## hands on 
### ALB
```
    - Launch `ec2-i1` and `ec2-i2`, add sg-1 to both.
      - sg-1 : allow traffic ONLY from below `elb-sg-1` 
    - create target group - `tg-1` + /health/ + http:80
    - Creat ELB - elb-1, elb-dns-1
      - choose AZs
      - add `elb-sg-1` : all public traffic
      - add Listener & Routing :  
        - Listener-1::No-contion : outside traffic on http:80  --> forward to --> `tg-1` 
        - Listener-1::consition-1 (priority-100) : path, header, queryparam, etc. [TRY] --> tg-x
        - Listener-2::No-Condition (priority-200)  on https:443 --> forward to --> tg-2 + make sure ACM has Cert for tg-dns name.
        - ...
        - ...  
        - Note:rule with higestest priorty win  
      - hit dns-1
      - terminate ec2-i1 and hit elb-dns-1 again.
```