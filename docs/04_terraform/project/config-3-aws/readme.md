# HCP workspace: aws-config-maps-outbound-`dev1`-`all`
- cd ./
- https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev1-all
- deploys these 3 modules : 
  - [iam](main-01-iam.tf)
  - [s3](main-01-iam.tf)
  - [ecs](main-01-iam.tf)
- terraform plan --var-file .\env\dev1.tfvars

- run other module on `dev2`, individually
  - separate workspace created. (**CLI-driver**)

---

# HCP workspace: aws-config-maps-outbound-`dev2`-`<configName>`
- **terraform plan --var-file ..\..\env\dev2.tfvars**
- **terraform apply --var-file ..\..\env\dev2.tfvars --auto-approve**

##  aws-config-maps-outbound-`dev2`-eks
- cd ./modules/eks
- https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-eks

##  aws-config-maps-outbound-`dev2`-sqs-sns
- cd ./modules/lambda
- https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-sqs-sns

##  aws-config-maps-outbound-`dev2`-lambda
- cd ./modules/sqs_sns
- https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-lambda

##  aws-config-maps-outbound-`dev2`-vpc
- cd ./modules/vpc
- https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-vpc

##  aws-config-maps-outbound-`dev2`-eventbridge
- cd ./modules/eventbridge
- https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-eventbridge