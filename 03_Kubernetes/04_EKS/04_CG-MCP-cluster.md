# cg - MCP cluster
- multi tenant(shared) kube env in 2 aws region. Dedicated AWS account - **mcp-aws**
- **platform-team** already provisioned EKS cluster with add on:
  - VPC
  - establish OpenID connect
  - Cluster Role + worker Node (cluster auto-scale)
  - ingress controller (eg: nginx)
  - agents - security, logging, monitoring, reloader-rollout on configmap changes,
  - or, fargate profile for each ATMID > select NS labeled with ATmID
  
---
- **on-boarding request**, provide:
  - `cluster name`: mcp-etc|im-use1|usw2-dev|qa|prod-ns-01
  - `role` --> our AWS account: `Broad-access-role`

---
## A platform-team Action 
### action-1 : share KubeConfig 
- cluster already created, share connection detail over SSM parameter store
  - **cluster_info**
  - **kubeconfig** (cluster-1, user-1, context-1)
```
## Example

apiVersion: v1
kind: Config
clusters:
- name: mcp-im-usel-dev-ns-01
  cluster:
  server: https://kube-admin
  cacert-data: b64....
  contexts:
- name: mcp-im-usel-dev-ns-01
  context:
  cluster: mcp-im-usel-dev-ns-01
  user: mcp-im-usel-dev-ns-01
  current-context: mcp-im-usel-dev-ns-01

users:
- name: mcp-im-usel-dev-ns-01
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
  - mcp-im-usel-dev-ns-01
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
- why ?
    
### action-3.1 : Authentication 
- can check again: [02_OIDC+first_admin_user+new_user.md](02_OIDC%2Bfirst_admin_user%2Bnew_user.md)
- update kube-system ns configMap - auth_map
  - add `mapUser` - no
  - add `mapRole` - make new entry for **Broad-access-role** role arn of our aws acct.
  ```
  mapRoles: |
  - rolearn: Broad-access-role arn
    username: aws-role-1-user       <<< notice this name, will be used in role binding
    groups:
      - system:masters
      # - system:bootstrappers
      # - system:nodes
      # - system:node-proxier
  ```
- authentication(username:password/token) === user-1:token-1
  - token-1 comes from - **execute aws command : aws eks get-token --cluster --region**
  - Note: aws configure with  Broad-access-role only :point_left:
  
### Action-3.2 : RBAC
- create ClusterRole --> **to access resource for namespace label with atmid.**
- create ClusterRoleBinding.
```
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: atmid-role
# edit rules - fine grain 
rules:
- apiGroups: [""]
  resources: ["namespace"]
  verbs: ["*"]
  labelSelector
    matchLabels:
        atm-id: "aaaaaaa"          <<<

---

apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: developer-role-1-binding
subjects:
- kind: Group
  name: developer-group-1  
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: atmid-role
  apiGroup: rbac.authorization.k8s.io
```
### Action-4 :  cross account access (optional)
- mcp-aws::`eks-role`
  - inline-policy --> "eks:*" 
  - **trusted-policy**  : Broad-access-role role arn of our aws acct

---
## B Developer actions    
- namespace(label-atmid)
  - atm_id-dev|qa|prod-description
  - add tags : ATM ID
- deployment object
- services  object (cluster IP)
- ingress-rule object (host/{path} --> map to above services)

- service account - sa1 (for pod exec)
  - annotate sa object with : `eks.amazonaws.com/role-arn:` **pod-exec-iam-role:arn**
  - add **inline-policy** to role - > access s3,sqs, etc
  - add **trusted-policy** :
  ```
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
- configMap object
- External secret object 
  - ext store : aws secret manager
  ```
  pending...
  ```
       

  

