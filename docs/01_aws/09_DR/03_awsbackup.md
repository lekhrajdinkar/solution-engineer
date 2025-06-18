# AWS backup
- like `aws firewall` is provides centralized security `in one place`.
- Similarly, AWS backup provide `centralized`,  backup `plan` and `policies` for all the `supported services`.

## supported services
  - Amazon EC2 / Amazon EBS
  - Amazon S3
  - Amazon RDS (all DBs engines) / Amazon Aurora / Amazon DynamoDB
  - Amazon DocumentDB / Amazon Neptune
  - Amazon EFS / Amazon FSx (Lustre & Windows File Server)
  - AWS Storage Gateway (Volume Gateway)
  - ...
  - ...
- target place : everything backed up to `S3`
  
## features
  - cross region backup
  - `cross account backup`
  - PITR
  - define backup window + retention period(cant be changed)
  - Enforce WORM, `vault lock policy` : backup cannot be deleted
  - policies:
    - `tag based`
    - `cron expression`: montly, weekly, etc
  - jobs
    - scheduled `backup`
    - scheduled `restore`

## Demo
```
// BACKUP PLAN
- create backup plan-1
    - start options : 
        - from pre-built template : choose one - "daily-monthly-1yr-retention"
        - new plan from scratch
        - existing plan > modify > create new one
    - backup policy > rules:
        - rule-1
            - backup vault / target - create vault-1 
            - backup window
            - frequency
            - lifecycle : move to cold storage > after xxxx days/month : set days/month 
            - DR : copy to another region : choose another region.
           
// READY
 plan-1 > edit > assign resource
    - choose assignmnet-1 (created below)

// ASSIGNMNET - to assign resource to plan
- create assignmnet-1
    -  create assignmnet-1-iam-role-1  
    -  assignByResourceId : resource arn
    -  assignByTag : key:env , value=prod
       - anything taged as prod, will be backuped then.
    
```