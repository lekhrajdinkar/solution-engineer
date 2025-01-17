# RAM (Resource access manager)
## Intro 
```yaml
- AWS org
  - mgt acct
  - OU-1
    - member-acct-1
    - member-acct-2
    - ...
```
- It simplifies **resource sharing** 
  - eliminates the need to duplicate resources **across multiple accounts in aws Org**
  - seamlessly share resources as your organization grows
---
## Supported services
- **VPC Subnets** (not vpc itself)
- **Amazon Aurora and RDS Clusters**
- **Route 53** 
  - Share resolver rules for DNS queries.
- **AWS Transit Gateways**
  - Share transit gateways for network connectivity.
- more:
  - AWS Backup Vaults: Share backup vaults across accounts.
  - License Manager Configurations: Share license configurations.
---

## VPC sharing (part of Resource Access Manager) 
```yaml
# scenario
- AWS org
  - mgt acct (VPC-1 subnet-1/2/3/...)
  - OU-1 
    - member-acct-1  (subnet-1 shared)
    - member-acct-2   (subnet-2 shared)
    - ...
```
- share **subnet/s** (not VPC) with member-account
- member-account can view, create, modify, and delete their application resources in the **subnets shared with them** only 
