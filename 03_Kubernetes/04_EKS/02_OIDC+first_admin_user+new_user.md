# A. on cluster creation
- When the EKS cluster is created, then it creates below 2 things as well:

## 1 `identity provider / OIDC`
- an OIDC provider (oidc-1) is automatically associated with it.
- if not found manually configured it:
  - install `eksctl` - https://eksctl.io/installation/
  - **eksctl utils associate-iam-oidc-provider --cluster $cluster_name --approve**
- **aws eks describe** --cluster-name xxxx : check issuer url.
- purpose : used for IRSA
  
## 2 `first admin user`
- run : aws sts-assume-role `aws-role-used-by-admin`
- **the IAM entity (user or role) that creates the cluster** is automatically granted **system:masters** group in the **aws-auth** ConfigMap.
- **system:masters** group:
  - provides full admin access to the cluster.
- this admin user can add more user and add permission, using k8s RBAC. check below section.
- run aws eks update-config --cluster=c1
  - **kubeconfig** get updated with new context-1
    - user : arn:aws:iam::123456789012:role/**aws-role-used-by-admin**
    - cluster : c1
- run : kubectl commands ...
  - flow:
    - aws eks get-token
    - sts:GetCallerIdentity with your IAM credentials (e.g., from ~/.aws/credentials or an IAM role).
    - AWS STS returns a presigned **URL** containing - user ID, account ID
    - kubectl cli, converts to -->  k8s-aws-v1.<base64-encoded-sts-**url**>
    - k8s-aws-v1.aHR0cHM6Ly9zdHMuYW1hem9uYXdzLmNvbS8...
    - send this token to the EKS cluster’s API server with Authorization header
    - API server decodes the token to extract the STS presigned URL
    - forwards the URL to **AWS IAM Authenticator**
    - The authenticator checks the **aws-auth** ConfigMap in the kube-system namespace o map the IAM identity to a Kubernetes user/group
```
aws-auth:
========
...
mapRoles: |
  - rolearn: arn:aws:iam::123456789012:role/aws-role-used-by-admin
    username: arn:aws:iam::123456789012:role/aws-role-used-by-admin    
    groups:
      - system:masters         
...
```
```
kubeconfig
============
...
users:
- name: arn:aws:iam::123456789012:role/aws-role-used-by-admin
  user:
    exec:
      apiVersion: client.authentication.k8s.io/v1beta1
      args:
      - --region
      - us-west-2
      - eks
      - get-token  === sts:GetCallerIdentity  with your IAM credentials (e.g., from ~/.aws/credentials or an IAM role).  <<<<
      - --cluster-name
      - maps-outbound-us-west-2-dev2-eks-fargate-cluster
      - --output
      - json
      command: aws

```
![img.png](img.png)

---
# B create new user (external OIDC / identity token based)
- reference : https://chatgpt.com/c/673940a9-d1cc-800d-a117-847107be2e53

## 1. create new `eks-user` (admin)
- Note: users === outside k8s user === represent aws `IAM user` or `IAM role`
  - of same AWS account
  - or cross AWS account
- create `aws-role-1`, attach policies/permission:
  - **AmazonEKSClusterPolicy**
  - **AmazonEKSWorkerNodePolicy** 
  - **AmazonEKSServicePolicy**
  - **inline** : 
    - eks:AccessKubernetesApi, DescribeCluster, ListClusters, 
    - iam:getRole,PassRole
  - https://us-east-1.console.aws.amazon.com/iam/home?region=us-west-2#/roles/details/eks-cluster-role-1-for-federated-user?section=permissions - created manually.
- let's create new-user  (for `aws-role-1-user`)
- `first-admin-user` has to update `aws-auth` ConfigMap.
  - **kubectl edit configmap aws-auth -n kube-system**
```
mapRoles: |
  - rolearn: arn:aws:iam::123456789012:role/aws-role-1
    username: aws-role-1-user    <<<
    groups:
      - system:masters
      # - system:bootstrappers
      # - system:nodes
      # - system:node-proxier
```
- next login :
  - aws configure (with aws-role-1) :point_left:
  - update **kubeconfig**
  ```
    kubectl config set-cluster new-context-1 --server=https://<eks-cluster-endpoint>
    kubectl config set-context new-context-1 --cluster=my-cluster --user=aws-role-1-user
    kubectl config use-context new-context-1
  ```
---
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

