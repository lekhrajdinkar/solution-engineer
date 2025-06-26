# A. ECS
## 1 Expose
- expose ALB (public subnet) with DNS
- db also publicly exposed
## 2 rate limit
- alb + waf
## 3 TLS
- alb --> ACM
## 4 network filter (ingress/Egress)
- alb > sg
- tg > sg

---
# B. EKS [04_EKS](../../03_Kubernetes/04_EKS)
## 1 Expose with TLS
- **clusterIP service** for app-service>pod
- platform team added
  - **ingress Controller** ( `host-main` ) 
    - TLS ?
    - rate limiting ?
    - ...
  - **ALB controller**
    - security group
    - WAF [08_WAF+FirewallManager.md](../../01_aws/06_Security/08_WAF%2BFirewallManager.md)
      - rate limit
      - ...
- **Routing**: 
  - helix AWS : R53
    - `appl1.org.com` --> `host-main`
    - appl2.org.com --> host-main
    - ...
  - App-1 AWS
    - k8s ingress object :: 
    - host: `appl1.org.com`
      - path-1 : service-1
      - path-2 : service-2
      - ...
    - tls
      - secret (aws scret > extSecret)
      - encryption Object while cluster setup
## 2 Rate limit
- level-1 : ingress-controller
    - ...
    - ...
- level-2 : fargate pod
  - ...
  - ...
- level-3 : program level
  - ...
  - ...
## 3 network filter (ingress/Egress)
- level-1 : ingress-controller
  - ...
  - ...
- level-2 : fargate pod
  - attach ENI + sg
  - K8s object: network policy

---
# C More
- **Documentation** : app level Swagger
- **versioning** : app level