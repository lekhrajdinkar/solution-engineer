# A  network fundamental
## 1 cidr
- CIDR/IP-range : base-IP/fixed-bit (0-32)
- 0.0.0.0/0  or ::0 -  Any traffic in internet.
- **Private IP**
  -  **10.0.0.0 – 10.255.255.255** 
    - 10.0.0.0/8 : in big networks, 
    - 10.0.0.0/28 - will use 
    - 10.0.0.0/16 - will use
  -  **172.16.0.0 – 172.31.255.255** 
    - (172.16.0.0/12)  AWS default VPC uses this.
  -  **192.168.0.0 – 192.168.255.255** 
    - 192.168.0.0/16: Home n/w

## 2 ephemeral port
- [ephemeral port](../99_img/vpc-2/img_2.png) 
- random port client open, to receive response on that port from server. 

---
# B. VPC ( Regional)
## 1. fact
- ec2>eni>sg === ec2>sg
- s3 upload : ingress traffic

## 2. Intro
- max: `5-VPC` in  a region with **non-overlapping CIDR**.
- single VPC can have max `5-CIDR`
  - CIDR min ****/28 = 32-28 = 4 --> 2^4 = `16`
  - CIDR max ****/16 = 32-16 = 16 --> 2^16 = `65,536`
- **private resource** : referring only private IPs ranges.
- **route table** `rtb` vs **security group** `sg`
  - sg allow/deny traffic
  - rtb helps in forwarding , routing allowed traffic.

---
## 3. Default VPC : walkthrough
- ![img.png](../99_img/vpc-1/img-v2.png)
- ![img_1.png](../99_img/vpc-1/img_1-v2.png)
- ![vpc-1.png](../99_img/draw-io/VPC-1.drawio.png)

### 3.1 CIDR
- **CIDR-1** : `172.32.0.0/16`
- **CIDR-2,3,4,5**  
  - Add these once IPs are exhausted in your VPC.
    
### 3.2 subnet (found 4) 
- 4 az === 4 subnets 
- each has it own IP CIDR.
   - first 4 and last IP  are reserved (`5-resevered in each subnet`) 
   - first: **network address**
   - 2:reserved - **VPC router**
   - 3:reserved - **DNS**
   - 4:reserved - future use
   - last:reserved - **network broadcast address**, (not supported currently)

### 3.3 Route table
- vpc - associated with main-rtb
  - subnet-1 : associate with main-rtb, or create new rtb
  - subnet-2
  - ...
  - 1-2-1 mapping :point_left:

- **destination**
  - 0.0.0.0/0(internet),
  - subnet-CIDR
  - vpc-CIDR
  - custom-cidr
  - ...
- **target**
  - `igw`, `nat-g/i`, `local`, `vpc-peer`, `vgc`, `cgw`, `dxg`, `transient-gateway`
  - `VPC-endpoint`, `s3-gateway`, `dynamoDb-gateway`

- **IPv6 routing**
  - ![img_2.png](../99_img/vpc-2/ipv6-3.png)
  
### 3.4 Network ACL (NACL)
- similar to SG/Firewall, another layer of traffic check at subnet level
- Inbound + outbound rule (with weight/priority) for allow/deny traffic

- **stateless**
  - inbound rule is checked >  allow/deny 
  - if allowed > response came > outbound rule is checked >  allow/deny
  - ![img.png](../99_img/vpc-2/img.png)
- **ACL rules**
  - 1-32,766 (high to low precedence), use increment of 100.
  - first matching rule, drive decision.
  - `*` last rule, denies a request.
  - thus, Does not execute all rule, once executed a matching high priority rule, it stops.
- **default rule**
  - **allows everything in/out** :point_left:
  - don't change it, rather create new ACL and associate with your subnet.
  - ![img_1.png](../99_img/vpc-2/img_1.png)
- `subnet`  **1-2-1** `ACL`

#### sg vs ACL
- Operates at the instance level | subnet level
- Stateful| Stateless: return traffic must be explicitly allowed by rules (think of ephemeral ports)
- `allow` rules only |  `allow/deny` rules
- `All` rules are evaluated | Rules are evaluated `in order` (lowest to highest) and `first match wins`.
- ![img_3.png](../99_img/vpc-2/img_3.png)
  
---
## 4. IGW

---
## 5. NAT 
- [03_VPC-2-NAT.md](03_VPC-2-NAT.md)
  - **Bastian host**
  - NAT instance (outdated after 2020) :x:
  - **NAT gateway**

---
## 7. dual-stack mode VPC
- can not disable Ipv4, but enable Ipv6 ( for all public in AWS) `3.8*10^38`
- ec2-i will have both:
  - **private IPv4** 
  - **public IPv6** 
- so if IPv4 is exhausted, even though soo many IPv6 available, will still get exhausted error. :point_left:

---
## 8. Egress-Only Internet gateway
- used only for Ipv6
- note: update rtb ::0 | Egress-IGW
- ![img_1.png](../99_img/vpc-2/ipv6-2.png)


---
## 99. handson: create new VPC
- region - us-west-2
- vpc-1 : https://us-west-2.console.aws.amazon.com/vpcconsole/home?region=us-west-2#VpcDetails:VpcId=vpc-04ce2894d2f99bbb8
```
- edit CIDR : add IPv6 cide.
- For Internet access:
  - create `igw-1` (igw-0ee888f95b632848e, internet gateway) to vpc-1 and `attach` to vpc-1
  - create `nat-ec2-i1` (NAT - network access translation)
  - create `ngw-1` ( NAT gateway) : pending
- route table:
  - `rtb-main` : gets created automatically with vpc.
    - will automatically get associated underlying subnet/s, if not attached to any explicit rtb.
  - `rtb-explicit/s` : can create and association to subnet.
      - create `rtb-explicit-1-private-vpc1` , routes:
        - destination: internet(0.0.0.0/0) --> nat-instance-1 [nat-i](03_VPC-2.md) ==> give internet access (without exposing ec2-i)
      - create `rtb-explicit-2-public-vpc1` , routes: 
        - destination: internet(0.0.0.0/0)  --> igw-1 ==> give internet access.
        - VPC private CIDR --> local (within VPC)
        - ![img.png](../99_img/vpc-1/img.png)
  - `relation`:
    - VPC <--1-to-1--> rtb-main
    - underlying subnet/s <--1-to-1--> rtb-explicit/s or rtb-main:default
  - ![img_1.png](../99_img/vpc-1/img_1.png)

- add `subnet`
  - az-1 (us-west-2a)
    - vpc-1-subnet-`private`-1-us-west-2a
      - link with rtb-explicit-2-private-vpc1.
    - vpc-1-subnet-`public`-1-us-west-2a
      - link with rtb-explicit-2-public-vpc1.
      - contains: `ACL`+ Ec2-i1(public IP-1)
      - edit CIDR : add `IPv6` CIDR block + enable: assigning IPv6.
        - update `sg/acl` rule for IPv6
        - update `rtb` with ::0 (IPv6 anywhere)
  - az-2 (us-west-2b)
    - vpc-1-subnet-`private`-2-us-west-2b
      - link with rtb-explicit-2-private-vpc1
    - vpc-1-subnet-`public`-2-us-west-2b
      - link with rtb-explicit-2-public-vpc1
```


---
# C. VPC firewall
- managed service
- inspect all traffic to/from:
  - internet
  - peered VPC
  - Data-center/customer (DX oe S2S)
  - ...
  - ![img_4.png](../99_img/vpc-4/img_4.png)
- firewall use **gateway Load Balancer** 
  - layer 3 to 7 protection
  - tg ec2-i : **security applicance** running
    - rules - filter ip, port, protocol, domain level(*.abc.com), regex
    - action : allow, drop, alert
- more:
  - can be used to manage multiple aws account
  - analysis:
    - send logs to s3, CW, KDF

---
# D. Extra
## 1. hared service VPC
![img.png](../99_img/vpc-1/SharedserviceVPC.png)

## 2. Transit VPC
-  Transit VPC uses customer-managed EC2 instances in a dedicated transit VP` with an Internet gateway
- ![img.png](../99_img/vpc-1/Transit-VPC.png)






