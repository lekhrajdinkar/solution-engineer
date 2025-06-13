## 2. create new `eks-user` (non-admin )  === cg
- same as above. but remove this groupo`system:masters`
- add RBAC for a group (say : `developer-group-1`). check below
  - create `ClusterRole` and `ClusterRoleBinding` for this group. (for cluster-level resource)
  - create `Role` and `RoleBinding` for this group. (for ns-level resource)
  - in binding object > subject > kind: user, name: `aws-role-1-user`
```
# notice linking of group and user.
# this group is EKS specfic thing only   <<<<

mapRoles: |
  - rolearn: arn:aws:iam::123456789012:role/aws-role-1
    username: aws-role-1-user    <<<
    groups:
      - developer-group-1          <<<
```

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
        atm-id: "aaaaaaaa"          <<<

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
  name: developer-role-1
  apiGroup: rbac.authorization.k8s.io
  
```
---
# C  create new service Account
- create eks object yaml, inside ns.
- for permission to k8s-resource : `role and role-binding`
- for permission to aws-resource : `IRSA`
  - **annotate** service account with `aws iam role`.
  - Pods assuming IAM roles via serviceAccount(annoated role-1)

---
![Untitled Diagram.drawio.png](Untitled%20Diagram.drawio.png)

[Untitled Diagram.drawio](Untitled%20Diagram.drawio)