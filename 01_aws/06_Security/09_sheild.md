# AWS Shield
- protect `DDoS attack` (many requests at the same time)

## AWS Shield : standard
- free
- activated by-default.
- `protects` from `layer3/layer4` attacks : SYN/UDP Floods? , Reflection attacks? :point_left:

## AWS Shield : Advance
- pricing : `$3000 / organization`
  - enabled consolidated billing for your aws org. :dart:
- 24/7 access to `DDoS response team` (DRP)
- `mitigate` from `layer7` attacks :point_left:
  - Automatically creates/deploys **AWS WAF rules**
- protects these services:
  - EC2
  - ELB
  - Amazon CloudFront distributions + AWS Global Accelerator
  - Route 53
  - API gateway : NO :x:

---
## Question:
```yaml
#1 
A financial services company recently launched an initiative to improve the security of its AWS resources.
And it had enabled AWS Shield Advanced across multiple AWS accounts owned by the company.
Upon analysis, the company has found that the costs incurred are much higher than expected.

Which of the following would you attribute as the underlying reason for the unexpectedly 
high costs for AWS Shield Advanced service?

answer:
Consolidated billing has not been enabled. 
All the AWS accounts should fall under a single consolidated billing for the monthly fee to be charged only once

---

#2
```


