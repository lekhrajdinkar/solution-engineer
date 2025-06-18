- https://aws.github.io/copilot-cli/
--- 
# AWS copilot 
## intro
- CLI tool for **production-ready containerized app** to:
  - build 
  - release 
  - operate  

- Provisions all required **infrastructure** for containerized apps (ECS, VPC, ELB, ECR…)
  - cloudformation stack.
- Automated deployments with one command using **CodePipeline**
- Deploy to multiple **environments**
- Troubleshooting, logs, health status…
---
## hands-on
- check : https://github.com/aws-samples/aws-copilot-sample-service
- deploy:
```
copilot init --app demo \
  --name api \
  --type "Load Balanced Web Service" \
  --dockerfile "./Dockerfile" \
  --deploy
```
- more commands
```
- copilot init
- copilot svc init 
- copilot job init 
- copilot pipeline init 
- copilot env init
```
```
- copilot/**service-1**/manifest.yml
- copilot/**job-1**>/manifest.yml
- copilot/**environment-1**/manifest.yml
```