## A. create :: eks-user (non-admin)
- eg: user-app( broad-access-role )
- same as admin. but remove this group `system:masters`
- **RBAC** 
  - create ClusterRole and ClusterRoleBinding for this group. (**for cluster-level resource**)
  - create Role and RoleBinding for this group. (**for ns-level resource**)
  
### 1. update/Add : aws-auth configmap
```
mapRoles: |
  - rolearn: arn:aws:iam::123456789012:role/aws-role-1
    username: aws-role-1-user    
    groups:
      - app_DevLead         
```
### 2. ClusterRole : developer-role-1
- allow access to  any resource in namespace
```
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: developer-role-1
# edit rules - fine grain 
rules:
- apiGroups: [""]
  resources: ["namespace"]
  verbs: ["*"]
  labelSelector
    matchLabels:
        atm-id: "aa003199" 
```
### 3. ClusterRoleBinding :  developer-role-1 === app_DevLead(group)
```
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: developer-role-1-binding
subjects:
- kind: Group
  name: app_DevLead
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: developer-role-1
  apiGroup: rbac.authorization.k8s.io
  
```
---
## B. create :: service Account
- create eks object yaml, inside ns.
- for permission to k8s-resource : `role and role-binding`
- for permission to aws-resource : `IRSA`
  - **annotate** service account with `aws iam role`.
  - Pods assuming IAM roles via serviceAccount(annotated role-1)
- fact : also attaching role on fargate-role:
  - pulling image
  - eni
  - ...
---

![Untitled Diagram.drawio.png](Untitled%20Diagram.drawio.png)

[Untitled Diagram.drawio](Untitled%20Diagram.drawio)