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
- **VPC Subnets**:
- **Amazon Aurora and RDS Clusters**
- **Route 53** 
  - Share resolver rules for DNS queries.
- **AWS Transit Gateways**
  - Share transit gateways for network connectivity.
- more:
  - AWS Backup Vaults: Share backup vaults across accounts.
  - License Manager Configurations: Share license configurations.
---