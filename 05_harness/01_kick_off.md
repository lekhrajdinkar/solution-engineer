https://developer.harness.io/docs/continuous-delivery/get-started/key-concepts
---
# Harness
- **Account**: `lekhrajdinkar` : https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/settings/
- **Organization**: `default`
- module - **Cd** , CI, security testing, etc

--- 
## project:1 - maps-outbound-api
- https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/home/orgs/default/projects/mapsoutboundapi/details
- `update secret` : https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/settings/secrets/aws_eks_get_token/overview :point_left:
  -  **aws eks get-token  --cluster-name maps-outbound-us-west-2-dev2-eks-fargate-cluster --region us-west-2**
### common
- **pipeline**
  - https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/orgs/default/projects/mapsoutboundapi/pipelines
  - pipeline > stages (build, deploy, another pipleline) > steps (run, image push, etc)
    - input set
    - triggers
- **services**
- **connectors**
    - k8s-cluster-connector
    - `github-repo-connector`
    - terraform-hcp-connector
    - aws-secret-manager-connector
    - aws-account-connector
      - authentication : AWS access key *, assume role on aws-delegate, IRSA
    - nexus-repo-connector (optional)
    - service-now-connector (optional)
- **environment**
  - env-group : `oz-dev`
      - `dev1`
        - infrastructure definition : none
        - input files
      - `dev2`
      - `prod`
### more
- **secrets**
    - added by aws-account-connector
    - added manual
- **Access control**
  - **service account** : none
  - **user group** : `OZ_DEV_MAPS_DEV_LEAD` ( u1, u2,  coming from ? integrate with lDAP ?)
    - manage role-binding --> `role-1 <==bind==> resource-group-1` ,etc.
    - added binding : `pipelineExecutor - All-Project-Level-Resources`
    - **roles**
      - found 19, built-in. eg : `pipeline-executor`, etc
      - cant add more with enterprise version - role-1,2,...
      - role has granular permission.
      - create later:
        - OZ_dev_role : service, template, pipeline
        - OZ_prod_role  : service, template, pipeline
    - **resource-groups**
      - found 1 : `All-Project-Level-Resources`  
      - cant add more with enterprise version - resource-group-1,2,...
      - create later:
        - OZ_dev_resource-group : services, templates, pipelines
        - OZ_prod_resource-group : services, templates, pipelines

---
## project:2 - maps-outbound-ui (`hold`)
- https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/module/cd/orgs/default/projects/frontendproject/overview