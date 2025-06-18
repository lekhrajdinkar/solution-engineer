## 1. create clusters
- minikube
- EKS - terraform (with ld role)
  - iam role 1 - cluster
  - iam role - nodegroup / fargate profile
  - vpc
  - cluster : attach rol1 , vpc, establish oidc
  - iam : id provider for oidc
- check first admin user for role id.
- create new role with for sa-1 (needed for pod) : `k8s-sa-role`
  - permission: full s3
  - trusted : federated, aud : system:dev-ns

## 2. create 2 new user for cluster
- for aws-iam-role-1 : admin --> groups : system:masters
- for aws-iam-role-2 : develop --> groups : dev-group
  - role : add permission for dev-ns resource only
  - role-binding : subject:dev-group  

## 3. deploy k8s manifest
- push docker manually.
  - update code to read from s3 object 
- dev-ns with label: atmid
- sa-1 annotate  with  `k8s-sa-role`
- deployment
- ext secret : aws secret manager
- service - lb
- check aws alb (public ip, private ip)
- next, ingress rule
  - rule-1 
- *** update harness pipeline service ***
- kubectl get pods **-n ingress-nginx**

## 4. do same with helm
- create new harness pipeline for helm deployment
- helm release state stored remotely inside cluster.

## 5. create pipeline to deploy to eks cluster
- created https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/orgs/default/projects/mapsoutboundapi/pipelines/_spring_app_eks_pipeline/pipeline-studio?storeType=INLINE
- update cluster connector, env, infra