# A. WAF (webapp FireWall)
## 1. Intro
- **web-ACL**
- work on **layer:7**, thus integrate with ALB :point_left:
  - not other ELB
- prevent **SQL injection** and **Cross-Site Scripting (XSS)**
- rule to allow/deny traffic on:
  - **protocol** : http, udp, https
  - source/target **IP + port**
    - up to `10,000 IPs` max in a set
    - use multiple Rules for more IPs
  - **body**
  - **URI strings** 
  - Size constraints
  - **geo-match** 
    - block countries (client IP)
  - **Rate-based rules** 
    - eg: 10 req/per
    - this rule prevents **DDoS protection**

```yaml
- keep IP `static/fixed`:
    - use fixed for API for ALB
    - use AWS global accelerator (has fixed any-cast IPs)
```
---
## 2. Architecture example :dart:
- App --> ALB(layer:7) --> **WAF(layer:7):ACL** --> AWS global accelerator --> web-client.
- ![img.png](../99_img/security/others/img.png)

--- 
## 3. WAF::Deployment option :point_left: :point_left: :dart:
### regionally
  - **ALB** 
  - **API-gateway** 
  - **AppSync**(GrapgQL-API)

### globally
  - **CloudFront** distribution

---
## 4. exam scenario
- block country and allow some IP from that country. use WAF :dart:
```
#1 
An online gaming company wants to block access to its application from specific countries. 
however, the company wants to allow its remote development team (from one of the blocked countries) to have access to the application.
The application is deployed on Amazon EC2, running under an ALB with WAF.

- Use AWS "WAF::geo-match-statement" listing the countries that you want to block **
- Use AWS "WAF::IP-set-statement"    that specifies the IP addresses that you want to allow through **

  ALB,ACL does not have geo-retriction thing <<<
```

---
# B. FireWall manager (regional)
- All types of firewall, at common place.
- AWS org 
  - management acct
    - create **security policy**
    - apply these policy on multiple member account in org

- **Security policies** :
  - `WAF rules` (Application Load Balancer, API Gateways, CloudFront)
  - `AWS Shield Advanced` (ALB, CLB, NLB, Elastic IP, CloudFront)
  - `Security group` : EC2:ENI , ALB and ENI-resources in VPC
  - `R53 Resolver` ( DNS Firewall)
  - AWS Network Firewall ?


      




