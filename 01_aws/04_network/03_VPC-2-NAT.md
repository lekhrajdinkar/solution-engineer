# VPC-2

## 1. bastion host
- Access(perform **SSH**) ec2-i in private subnet from **internet**  ( via bastion host)
- just update SGs:
  - **sg-bastion** : 
    - allow inbound traffic 0.0.0.0/0  on port 22(SSH)
  - **sg-ec2-i** : 
    - allow inbound traffic from  sg-bastion, on port 22(SSH) 
    - ![img_2.png](../99_img/vpc-1/img_2.png)
- bastion host is not typically used to manage all `outgoing traffic` from the private network to the internet.

---
## 2. NAT instance :x:
- **outdated after 2020** 
- Alternative : **NAT gateway** 
- need internet  access for ec2-i running on private subnet. how ?
  - route internet traffic to IGW via through NAT-instance.
    - **deploy NAT-instance** in public subnet
      - create ec2-i, from PreConfigured Linux AMI, `amzn-ami-vpc-nat-<year>.xxxxxxx-<cpu-arch>`
      - **disable source/destination IP check**, so that it will re-write `src` and `dest` IPs :point_left:
      - ![img_4.png](../99_img/vpc-1/img_4.png)
    - **update rtb** of private subnet
      - 0.0.0.0/0  ::  NAT-instance
    - update NAT-instance **sg**
    - update sg of private ec2-i, to allow traffic from  NAT-instance-sg
    - assign **elastic-IP**
      
- it supports **port forwarding** :point_left:
  - by modifying the instance's **iptables rules**. 
  - to forward traffic 
    - from : specific port on the NAT Instance 
    - to : port on a private EC2 instance.

- ![img_3.png](../99_img/vpc-1/img_3.png)
- https://app.diagrams.net/#Hlekhrajdinkar%2F02-spring%2Fmain%2Faws%2FVPC-1.drawio

---
## 3. NAT Gateway 
- **AZ bounded** :point_left:
- **AWS-managed**
  - no administration 
  - No Security Groups to manage
  - ...
- **higher bandwidth** (100 Gbps)
- **high availability** (multi-az)

- **provision**  
  - create one with `in each AZ`
  - assign **elastic-IP** :point_left:
  - choose **public subnet** (having IGW)
  - update route table of each private subnet.
    - if destination is `0.0.0.0/0`  ::  then route to `NAT-gateway-az-1`
    - if destination is `0.0.0.0/0`  ::  then route to `NAT-gateway-az-2`
    - ...
- NAT Gateways are primarily for **outbound-only traffic** from private subnets

- Can’t be used by EC2 instance in the same subnet, where NAT gateway is present  :point_left: :point_left:


