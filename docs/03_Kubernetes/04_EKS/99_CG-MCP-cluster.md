## multi tenant EKS cluster
- multi tenant(shared) kube env in 2 aws region. Dedicated AWS account - **mcp-aws**
- **platform-team** already provisioned EKS cluster with add on:
  - VPC
  - establish OpenID connect
  - Cluster Role + worker Node (cluster auto-scale)
  - ingress controller (eg: nginx)
  - agents - security, logging, monitoring, reloader-rollout on configmap changes,
  - or, fargate profile for each appID > select NS labeled with appID
  
---
- **on-boarding request**, provide:
  - `cluster name`: mcp-etc|im-use1|usw2-dev|qa|prod-ns-01
  - `role` --> our AWS account: `Broad-access-role`

---
## ✅Platform-team::Actions 
- cluster + vpc
- desmonSet, agents, addon
- CRD
- RBAC for cluster resource
- ...

### action-1 : share KubeConfig 
- shared connection detail over SSM parameter store
    - **kubeconfig** (cluster-1, user-1, context-1)
  
```yaml
apiVersion: v1
  kind: Config
clusters:
- name: cluster-01
  cluster: cluster-01
  server: https://kube-admin
  cacert-data: b64....
  contexts:

users:
- name: cluster-01
  user:
  exec:
  apiVersion: client.authentication.k8s.io/v1beta1
  command: aws
  args:
  - eks
  - get-token
  - --region
  - us-east-1
  - --cluster-name
  - cluster-01
``` 

### Action-2 : add IAM:Identity provider for EKS
- **aws eks describe-cluster** 
    - --name <cluster-name> 
    - --region <region> 
    - --query `"cluster.identity.oidc.issuer"` 
    - --output text
- add OIDC provider in IAM:identity provider of our AWS acct.
  - `issuerId` - https://oidc.eks.us-west-2.amazonaws.com/id/eks-cluster-id
  - `audience` - **sts.amazonaws.com**      
- IRSA
    - POD-1(sa-1) running in eks-cluster(aws-1) has to assume role in **aws-tenant-1**.
        - annotated with **aws-tenant-1**/role-1 ⬅️ cross acct
        - not with **aws-1**/role-1
    
### action-3 : Authentication + RBAC
- aws-auth : **aws-tenant-1**/role-1 + group1
- k8s-role-1 (permission-1,2,...)
- k8s-roleBinding (k8s-role-1 with group1)

---
## ✅Developer::Actions    
- namespace-app1
- **deployment** object
- **services**  object (cluster IP)
- **ingress** object (host/{path} --> map to above services)
- **service account** 
    -  sa1 (for pod exec)
    - annotate sa object with : `eks.amazonaws.com/role-arn:` tenant-role-1
    - add **inline-policy** to role - > access s3,sqs, etc
    - add **trusted-policy** : 👈🏻👈🏻
  
```json5
  {
    "Effect": "Allow",
    "Principal": {
      "Federated": "arn:aws:iam::<account-id>:oidc-provider/oidc.eks.<region>.amazonaws.com/id/<eks-cluster-id>"
    },
    "Action": "sts:AssumeRoleWithWebIdentity",
    "Condition": {
      "StringEquals": {
        "oidc.eks.<region>.amazonaws.com/id/<eks-cluster-id>:sub": "system:serviceaccount:<namespace>:<service-account-name>"
      }
    }
  }
```
- **configMap object**
- **secret** > type tls (keep cert)
- **External secret** , to copy secret from aws::secretManager

       

  

