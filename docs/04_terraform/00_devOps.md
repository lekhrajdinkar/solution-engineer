- reference:
  - Overview: https://chatgpt.com/c/67457115-702c-800d-9c19-7cd222a8deff
---
# DevOps
- collaboration between **development team** and **operations teams** 
- improve the speed, quality, and reliability of SDLC / software delivery.
  - automation 
  - continuous integration (CI) 
  - continuous delivery (CD)
  - monitoring
    - Continuous monitoring of the production environment for issues
    - cloudwatch > datadog > service-now 
    - splunk on on-prem
    - more: Prometheus, Grafana
  - faster feedback loops.
    - due to frequent release from CD pipeline, users get frequent update.
- 
---
## A. IAC
- **Terraform** 
  - shared EKS cluster
- **AWS CDK** (on AZF aws account) 
  - generated cloud formation template using - java, `typescript`
  - **cdk synth**
  - **cdk deploy stack-1**
---
## B. CI
### tasks:
- Developers commit their code changes to VCS.
- After each commit, an automated build is triggered to compile the code, ensuring that the new code doesn’t break the project.
- build process - maven package
  - triggers Junit (automated testing)
  - Early detection of bugs
- trigger code scan - `synk`
  - static scan - vulnerability.
  - dynamic scan
  - code coverage
- `merging` code changes from different contributors into a shared repository frequently (develop branch)
  - manual review.
  - triggers CD pipeline to deploy to pre-prod env - dev1, qa1
  
### tools: 
- **Version Control**: Git (GitHub, Bitbucket)
- Test Automation: JUnit, Selenium, karma & jasmine.
- **Harness CI pipeline**

---
## C. CD
### tasks
- CD takes the concept of CI, a step further by automating the **release process**
- automatically deployed to a staging or pre-production environment for **further testing**
- staging environment closely matches the production environment
- Automated Rollbacks on failure
- type:
  - **Continuous Deployment**
    - automatically deployed to production without human intervention
  - **Continuous Delivery**
    - Changes are automatically deployed to staging or `pre-production` environments
    - but require **manual approval** for `production` deployment
    - monthy release planned.
    
### tools
- AWS CodePipeline - old
- **Harness CD pipeline**
- deployment with `Kubernetes and Docker` - microservices