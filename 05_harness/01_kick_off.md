- https://developer.harness.io/docs/continuous-delivery/get-started/key-concepts
---
# Harness
## onboarding
- https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/settings/
- **Account**: `lekhrajdinkar`  
  - **Organization**: `default`
    - **project**
      - **outbound-api : dashboard**
        - pipelines 
        - users 
        - Environments
        - Services
      - outbound-ui : dashboard
        - ...
        - ...
--- 
## project1 - maps-outbound-api
### A. pre-work (plateform team)
#### 1 setup : secrets
- **aws**
  - aws_eks_get_token
    - **aws eks get-token  --cluster-name maps-outbound-us-west-2-dev2-eks-fargate-cluster --region us-west-2**
    - Need to update token manually, once expired
    - https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/settings/secrets/aws_eks_get_token/overview
  - aws_eks_cluster_ca_data
  - aws_533267082359_secret_access_key
- **minikube**
  - minikube-admin-client-key
  - minikube-admin-client-crt
  - C:\Users\Manisha\.minikube\profiles\minikube
- **github**-access-token-org
- **terraform**-hcp-dev

#### 2 setup : delegates
[02_delegates.md](02_delegates.md)

#### 3 setup : connectors
- **kubernetes**
  - k8s-eks-cluster-connector
  - k8s-minikube-cluster-connector
- **github**-lekhrajdinkar-connector
- **terraform**-hcp-connector
- **aws**
  - aws-secret-manager-connector
  - aws-account-connector
- **more**
  - nexus-repo-connector 
  - service-now-connector

![img.png](img.png)

#### 4 Access control
- **service account** : none
- **user group** : `app_DevLead` (LDAP role)
  - u1, u2
- **roles**
  - found 19, built-in. eg: pipeline-executor
  - create custom role
    - pipeline-owner
    - pipeline-developer
  - role has defined granular **permission**.
    - **resource/s : action/s**
    - service  : R , W, Edit, View, etc
    - template : R , W, Edit, View, etc
    - pipeline : R , W, Edit, View, etc
    - ...

---
### B. CD pipeline (developer team)
#### 1 template
#### 2 pipeline
- https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/orgs/default/projects/mapsoutboundapi/pipelines
- pipeline > stages (build, deploy, another pipleline) > steps (run, image push, etc)
  - input set
  - triggers

#### 3 services :x:

#### 4 environment :x:
- **env-group**
  - oz-dev
    - dev1 (pre-prod)
    - dev2 (pre-prod)
    - prod\
    
---
## project2 - maps-outbound-ui
- [dashboard](https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/module/cd/orgs/default/projects/frontendproject/overview)