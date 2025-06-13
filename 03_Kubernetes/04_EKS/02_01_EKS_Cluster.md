## Reference/chatgpt
- cluster setup 
  - https://chat.deepseek.com/a/chat/s/1125c5bb-48e4-4aff-9e3e-4720011cfd45  
  - https://chat.deepseek.com/a/chat/s/da139fc5-e06f-42e3-8419-a2c17a94a9cf 
- create first user 
  - https://chatgpt.com/c/673940a9-d1cc-800d-a117-847107be2e53 
  - https://chatgpt.com/c/67371203-d934-800d-94f1-3c996d9584dd
- Authentication + IRSA  : https://chatgpt.com/c/67342f43-7220-800d-8831-68fe91ea7a87
- aws-auth : https://chatgpt.com/c/6734280e-7d48-800d-b410-280da79926fe
- pipeline
  - https://chatgpt.com/c/67346f23-ce58-800d-9b35-a0ccf088f920
  - https://chatgpt.com/c/67352892-e094-800d-a053-9a51c1074097
  - https://chatgpt.com/c/67358116-3f1c-800d-96c6-c6d447f1b283

---
## A. kubernetes Architecture
- [03_k8s-architcture+features.md](..%2F00_kickOff%2F03_k8s-architcture%2Bfeatures.md)

---
## B EKS cluster
### B.1 master node / control panel 
- kube-apiserver
- coreDNS
- controllers
- **add ons:**
  - metric server
  - logging server
  - **datadog agent**
  - **splunk agent**
  - ingress-controller / ALB controller (gateway)
  
### B.2 worker node
- kubelet
- kube proxy
- docker / container-d

### B.3 setup network
- **awsvpc**
- none
- host
- bridge

### B.4 cloud service
- ALB (alb-controller, install with helm)
- EBS / EFS
- Aws Secrets manager -> externalSecret -> k8s:Secret

---
## D. EKS cluster Setup (platform team) :: terraform/HCL :yellow_circle:
### options
- AWS managed service (EKS - **fargate**)
- AWS managed service (EKS - **ec2 launch type**)
  - Node 
  - Nodegroup
  - https://www.udemy.com/course/docker-kubernetes-the-practical-guide/learn/lecture/22628019#overview
  - https://www.udemy.com/course/docker-kubernetes-the-practical-guide/learn/lecture/22628021#overview
- AWS :: **EC2**
  - spin up EC2 machine
  - set up VPC
  - software installation manually
  
### artifacts 
- HCP workspace: https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-eks/runs :point_left:
- HCL **configuration** : [terraforn config](..%2F..%2F04_terraform%2Fproject%2Faws-config-maps%2F03_outbound%2Fmodules%2Feks)
- aws provider using `role-1` (will become **admin user**)
  ```
  === attach on role-1 ====
  - AmazonEKSClusterPolicy
  - AmazonEKSWorkerNodePolicy
  - AmazonEKSServicePolicy
  - inline
    - eks:AccessKubernetesApi, DescribeCluster, ListClusters,
    - iam:getRole,PassRole
  ``` 
- command/s
  - **kubectl cluster-info**
  - **aws eks update-kubeconfig --cluster cluster-1 --region r1**
  ```
    - `master`: https://C7467B80CEF6669327EE0493423B84A5.gr7.us-west-2.eks.amazonaws.com
    - `CoreDNS` : https://C7467B80CEF6669327EE0493423B84A5.gr7.us-west-2.eks.amazonaws.com/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
  ```  
---
### D.1 create :: 2 IAM-roles
- **cluster-role-1** :
  - standard policy
- **node-group-role-1**
  - container registry policy
  - worker node policy 
  - CNI policy
---  
### D.2 create :: VPC for EKS 
- create **vpc-1** 
  - with standard cloudformation template.
  - on your own
---
### D.3 create :: cluster
- choose cluster type : **public and private**
- Configure AWS temp credential for **role-1**(become admin): getStsToken, gimme-aws-creds(okta)
- input :
  - cluster name - cluster-1
  - **cluster-role-1**
  - vpc-1

---
### D.4 Associate :: OIDC provider
- Associate **identity provider** (OIDC)  
  - used for IRSA :point_left:
  - an OIDC provider (oidc-1) is automatically associated with it.
  - if not found manually configured
    - **eksctl utils associate-iam-oidc-provider --cluster $cluster_name --approve**

- READY  :green_circle:
  - aws eks describe --cluster=cluster-1
  - aws eks update-config --cluster=cluster-1

---
### D.5 admin user (automatic created)
- Note: users === outside k8s user / federated === represent aws `IAM user/role`
- admin-user (role-1) auto created
- user-2 (eks-cluster-role-1-for-federated-user) 
  - created role : https://us-east-1.console.aws.amazon.com/iam/home?region=us-west-2#/roles/details/eks-cluster-role-1-for-federated-user?section=permissions
  - **kubectl edit configmap aws-auth -n kube-system**
- **Authentication flow** :point_left: :point_left:
```txt
- aws eks get-token
- sts:GetCallerIdentity with your IAM credentials (e.g., from ~/.aws/credentials or an IAM role).
- AWS STS returns a presigned **URL** containing - user ID, account ID, roleArn
  - kubectl cli, converts to -->  k8s-aws-v1.<base64-encoded-sts-**url**>
  - k8s-aws-v1.aHR0cHM6Ly9zdHMuYW1hem9uYXdzLmNvbS8...
- send this token to the EKS cluster’s API server with Authorization header  <<<<
  - API server decodes the token to extract the STS presigned URL
  - forwards the URL to **AWS IAM Authenticator**
  - The authenticator checks the **aws-auth** ConfigMap in the kube-system namespace
```
```
aws-auth: (kube-system namespace)
========
...
mapRoles: |
  - rolearn: arn:aws:iam::123456789012:role/role-1
    username: arn:aws:iam::123456789012:role/role-1    
    groups:
      - system:masters         
...
```
```
kubeconfig
============
...
users:
- name: arn:aws:iam::123456789012:role/role-1
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
![img.png](../99_img/02/img.png)

---
### D.6 Create :: Nodegroup
- **compute** tab 
- add `Nodegroup`(ec2 machines)
- input:
  - node-group-role-1
  - vpc-1
  - instance-type
  - scaling
- it will  install k8s software needed for **worker node**.
- READY :green_circle:

---
### D.7 volumes : EFS
- **storageClass (CSI) + pvc**
- integrate 3rd party storage on k8s -EFS
  - https://github.com/kubernetes-sigs/aws-efs-csi-driver
  - install this driver :: **kubectl apply -k "github.com/kubernetes-sigs/aws-efs-csi-driver/deploy/kubernetes/overlays/stable/?ref=release-2.0"**
  - add EFS in **same vpc-1** 
  - add **security-group** to allow traffic with in VPC.
```
      # PV
      ...
      ...
      volumnMode: Filesystem
      storageClassName: efs-sc-1
      csi:
        driver: efs.csi.aws.com
        volumehandle:  fs-1  # id  of fs created above
    
      ---
      # SC
      ...
      ...
        name: efs-sc-1
      spec:
        provisoner: efs.csi.aws.com
    
      ---
      # PVC
      ...
      ...
        name: efs-pvc-1
      spec:
        storageClassName: efs-sc-1
        resources:
          requests:
            storage: 5Gi
    
      ---
    
      # pod
       volumns:
          - name:
            pvc:
              claimname: efs-pvc-1
    
       container:
        - ...
          ...
          volumnMounts:
            - name: 
              mountpath: /app/abc  
```
  