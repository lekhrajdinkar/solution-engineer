# AWS cloudFront (global)
### Pictorial description
- ![img.png](../99_img/CF/img.png)
- ![img_2.png](../99_img/CF/img_2.png)
- ![img_3.png](../99_img/CF/cf-2/img_3.png)
- **1-2-M**
  - one distribution - multiple origins

### key point
- protocol : **HTTP/RTMP**  :dart:
- CDN **content delivery network**, cache data all around the world/countries
  - `allow-list` countries
  - `black-list` countries.
- static-content cached for **TTL** (eg : a day), thus low latency.
- Also can **invalidate** cache at any time
- **security**: :point_left:
  - can integrate with **WAF** [08_WAF+FirewallManager.md](../06_Security/08_WAF%2BFirewallManager.md)
  - integrated with **AWS-shield** (firewall) [09_sheild.md](../06_Security/09_sheild.md)

- **bypass** the regional edge cache, and go directly to the origin :dart:
  - Proxy methods **PUT/POST/PATCH/OPTIONS/DELETE** go
  - **Dynamic content**, as determined at request time
---
## A. Distribution (regional cache)
![img_1.png](../99_img/CF/img_1.png)
### 1. origin
- source (3):
  - s3 bucket` or  `s3(static Web), 
  - Any http backend
  - ALB only (not other ELB)
  - remember : **NO Lambda** :x: :dart: exam confuse with lambda alot.
- distribution(edge-location) ---> **`privatelink` physical connection** ---> origin(eg:alb on us-east-1)
    - cf also needs access-policy to access origin
### 2. origin access
- make origin `publicly` accessible.
  - get list of Public IPs of all 400+ edge location
  - add app level security to allow access only to above IPs.
  - update sg to allow traffic.
- **OAC - Origin access control** 
  - policy allow CF to connect/access origin.
  - ![img_3.png](../99_img/CF/img_3.png)

---  
### 3. origin failover 
- to help support your `data resiliency` needs.
- **origin group** with 
  - primary and secondary origins
  - to configure Amazon CloudFront for high-availability and failover

---
### 4. security: feild level encryption
![img_2.png](img_2.png)

---
### 5. CloudFront Function
- **purpose**:
  - Website Security and Privacy
  - Dynamic Web Application at the Edge
  - Search Engine Optimization (SEO)
  - Intelligently Route Across Origins and Data Centers
  - Bot Mitigation at the Edge
  - Real-time Image Transformation
  - A/B Testing
  - User Authentication and Authorization
  - User Prioritization
  - User Tracking and Analytics
- **Type**:
  - lambda@Edge
  - CloudFront-Function
  - ![img_2.png](../99_img/CF/cf-3/img_2.png)  
  
#### a. lambda@Edge 
- `nodeJs` or `Py`
-  lambda is **heavy**
- scale to `1000s of request/sec`
- execution time : 5-10 sec
- globally service. :point_left:
  - author: **us-east-1**
  - replicated to edge location from author.
- ![img_1.png](../99_img/dva/l/04/img_1.png)

#### b. CloudFront-Function
- `js`
- very **light** weight 
  - 10 KB pkg
  - Max 2Mb ram
- scale to `million of Req/sec`
- **pricing**: 1/6 time cheaper than lambda.
- ![img.png](../99_img/dva/l/04/img.png)

- ![img_1.png](../99_img/CF/cf-3/img_1.png)
---
## B. Demo  
### 1. s3 as origin
```
  - create CF > distribution-1
    - choose default object (optional) : index.html
    - choose `origin` : add s3-bucket-1 + upload index.html
        - choose `origin-access` (s3 access ways) :
            - public, or
            - OAC **
                - create OAC-1 and attach to distribution-1
                - Copy OAC-1:policy
                - so we don't need to make s3-bucket-1 public
  
  - update s3-bucket-1 policy with `OAC-1:policy`.
    - this will allow distribution-1 to access s3.
   
  - Copy public-url and hit it
    - public-url --> redirect to --> public-url/index.html.
    
  - Now upload abc.png in bucket
  - hit - blic-url/abc.hit
  - this comes from distributio, not directly from s3.
  
   - Now upload sub-folder/abc.png in bucket
  - hit - public-url/sub-folder/abc.hit
  - ...
  - so on
```

### 2. ALB as origin
![img.png](../99_img/CF/cf-2/img.png)

---
## C. Pricing
![img_1.png](../99_img/CF/cf-2/img_1.png)
- **price class**
  - `100` - usa, europe, etc
  - `200` - africa, asia, etc
  - `ALL`
- ![img_2.png](../99_img/CF/cf-2/img_2.png)

---  
## D. signed URl / cookies
- CloudFront supports two types of **signers** for creating signed URLs or cookies:
  - `Trusted AWS Account Signers`
  - `Key Pair Signers` (deprecated) :x:
    - key pairs associated with the root AWS account
  - `CloudFront key groups`
    - **public key** is added to the **key group** in the CloudFront distribution settings
    - The corresponding **private key** is securely stored by the developer or application creating signed URLs.

---

## exam :dart:
### 1
- Choosing Between **ElastiCache** and **CloudFront**
  - If your goal is  (`ElastiCache`)
    - to improve the **performance of database queries** 
    - store session data in memory, use Amazon . 
  - If your goal is  (`CloudFront`)
    - to deliver content quickly to users across the globe, 
    - **reduce latency** for static and dynamic content

### 2
- cannot **directly** integrate **Cognito User Pools** with CloudFront distribution
  - via AWS `Lambda@Edge function` can accomplish 
  - but requires additional development

### 3
- If you have objects that are smaller than `1GB`,
- you should consider using Amazon CloudFront's PUT/POST commands for optimal performance. 


