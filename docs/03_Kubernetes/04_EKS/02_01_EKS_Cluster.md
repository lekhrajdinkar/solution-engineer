## A. Reference/chatgpt
- **cluster setup** 🗨️
    - [https://chat.deepseek.com/a/chat/s/1125c5bb-48e4-4aff-9e3e-4720011cfd45](https://chat.deepseek.com/a/chat/s/1125c5bb-48e4-4aff-9e3e-4720011cfd45)  
    - [https://chat.deepseek.com/a/chat/s/da139fc5-e06f-42e3-8419-a2c17a94a9cf](https://chat.deepseek.com/a/chat/s/da139fc5-e06f-42e3-8419-a2c17a94a9cf)
- **create first user** 🗨️
    - [https://chatgpt.com/c/673940a9-d1cc-800d-a117-847107be2e53](https://chatgpt.com/c/673940a9-d1cc-800d-a117-847107be2e53) 
    - [https://chatgpt.com/c/67371203-d934-800d-94f1-3c996d9584dd](https://chatgpt.com/c/67371203-d934-800d-94f1-3c996d9584dd)
- **Authentication + IRSA** 🗨️
    - [https://chatgpt.com/c/67342f43-7220-800d-8831-68fe91ea7a87](https://chatgpt.com/c/67342f43-7220-800d-8831-68fe91ea7a87)
    - [aws-auth](https://chatgpt.com/c/6734280e-7d48-800d-b410-280da79926fe)
- **pipeline/helm** 🗨️
    - [https://chatgpt.com/c/67346f23-ce58-800d-9b35-a0ccf088f920](https://chatgpt.com/c/67346f23-ce58-800d-9b35-a0ccf088f920)
    - [https://chatgpt.com/c/67352892-e094-800d-a053-9a51c1074097](https://chatgpt.com/c/67352892-e094-800d-a053-9a51c1074097)
    - [https://chatgpt.com/c/67358116-3f1c-800d-96c6-c6d447f1b283](https://chatgpt.com/c/67358116-3f1c-800d-96c6-c6d447f1b283)

---
## B EKS cluster :: Architecture
### B.1 master node / control panel 
- kube-apiserver
- coreDNS
- controller manager
- **add ons:**

  | **Category**            | **Tool**                     | **Purpose / Description**                                            |
  | ----------------------- |------------------------------|----------------------------------------------------------------------|
  | **Observability**       | `opentelemetry-*`            | Distributed tracing and observability (instrumented apps/services)   |
  |                         | `datadog`                    | Observability platform (metrics, logs, traces)                       |
  |                         | `splunk-kubernetes`          | Logging via Splunk integration                                       |
  | **Cost Management**     | `flexera`🔸                    | Software asset or cloud cost management                              |
  |                         | `cloudability`               | Cloud cost and usage monitoring                                      |
  | **Networking/Services** | `cert-manager`🔸             | Manages SSL/TLS certificates (e.g., Let's Encrypt, ACM)              |
  |                         | `chaos-mesh`                 | Chaos engineering for resilience testing                             |
  |                         | `ingress-nginx`              | NGINX-based Ingress controller                                       |
  | **Other Tools**         | `karpenter`🔸                | Dynamic autoscaler (more efficient than Cluster Autoscaler)          |
  |                         | `external-secrets-operator`🔸  | Syncs secrets from AWS Secrets Manager/SSM to Kubernetes secrets     |
  |                         | `namespace-controller`       | Enhanced namespace lifecycle management                              |
  |                         | `reloader`🔸                   | Auto-restarts pods when ConfigMap/Secret changes                     |
  |                         | `rafay-*`                    | Rafay platform for K8s management (deployments, governance)          |
  |                         | `velero`🔸                     | Backup and restore of cluster resources and PVC                      |
  |                         | `wiz` 🔸                     | Container/cloud security platform (runtime & configuration scanning) |

  
### B.2 worker node
- kubelet
- kube proxy
- docker / container-d

### B.3 setup network
- awsvpc ✅
- none
- host (node's)
- bridge (docker bridge)

### B.4 cloud service
- ALB (alb-controller, install with helm)
- EBS / EFS / s3
- Aws Secrets manager -> externalSecret -> k8s:Secret
- ...
- with ISRA can make pods to access any other service ⬅️

---
## D. EKS cluster Setup (platform team) :: terraform/HCL 🟡
### options
- 🔸AWS managed service (EKS - **fargate**) ✔️
- 🔸AWS managed service (EKS - **ec2 launch type**) ❌
    - Node 
    - Nodegroup
    - [udemy course demo 1](https://www.udemy.com/course/docker-kubernetes-the-practical-guide/learn/lecture/22628019#overview)
    - [udemy course demo 2](https://www.udemy.com/course/docker-kubernetes-the-practical-guide/learn/lecture/22628021#overview)
- 🔸AWS :: **EC2** ❌
    - **provision** up EC2 machine
    - set up VPC **manually**
    - software installation **manually**
  
### artifacts 
- [HCP workspace - state file](https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-eks/runs) 👈🏻👈🏻
- HCL **configuration** : [terraforn config](..%2F..%2F04_terraform%2Fproject%2Faws-config-maps%2F03_outbound%2Fmodules%2Feks)
- aws provider using `role-1` (will become **admin user**)
  ```
  === attach on role-1 ====
  - AmazonEKSServicePolicy
  - AmazonEKSClusterPolicy
  - AmazonEKSWorkerNodePolicy
  - inline
    - eks:AccessKubernetesApi, DescribeCluster, ListClusters,
    - iam:getRole,PassRole
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
    - with standard cloudformation/terraform template.
    - or. our own template
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

- READY  ✅✅
  - **aws eks describe --cluster=cluster-1**
  - or, **kubectl cluster-info**
  - **aws eks update-kubeconfig --cluster cluster-1 --region r1**
  ```
    - `master`: https://C7467B80CEF6669327EE0493423B84A5.gr7.us-west-2.eks.amazonaws.com
    - `CoreDNS` : https://C7467B80CEF6669327EE0493423B84A5.gr7.us-west-2.eks.amazonaws.com/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
  ``` 

---
### D.5 admin user (automatic created)
- Note: users === outside k8s user / federated === represents aws `IAM user/role` === role1
- **admin-user** (role-1) auto created
- **user-2** [eks-cluster-role-1-for-federated-user](https://us-east-1.console.aws.amazon.com/iam/home?region=us-west-2#/roles/details/eks-cluster-role-1-for-federated-user?section=permissions) 
- admin > run :: **kubectl edit configmap aws-auth -n kube-system**
```yaml
#aws-auth: (kube-system namespace)
...
mapRoles: |
  - rolearn: arn:aws:iam::123456789012:role/role-1
    username: arn:aws:iam::123456789012:role/role-1    
    groups:
      - system:masters         
...

---

#kubeconfig
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

### D.5 Authentication flow 👈🏻
```txt
- 🔺aws eks get-token  === sts:GetCallerIdentity

- it invokes 🔺sts:GetCallerIdentity with our IAM credentials 
  (from ~/.aws/credentials > profile-1).

- AWS STS returns a presigned **URL** containing - user ID, account ID, roleArn
  - eks converts to 🔺k8s-aws-v1.<base64-encoded-sts-url>
  - k8s-aws-v1.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx...
  
- send token to kube-API-server in Authorization header
  - API server decodes the token 
  - extract the STS presigned URL
  - forwards the URL to 🔺AWS IAM Authenticator
  - The authenticator checks the 🔺aws-auth ConfigMap (kube-system namespace)
```

---
### D.6 Create :: Nodegroup (manaully try)
- aws console > **compute** tab 
- add `Nodegroup`(ec2 machines)
- input:
    - node-group-role-1
    - vpc-1
    - instance-type
    - scaling
- it will  install k8s software needed for **worker node**.
- READY ✅✅

---
### D.7 volumes : EFS
- **storageClass (CSI) + pvc**
- integrate 3rd party storage on k8s -EFS
    - https://github.com/kubernetes-sigs/aws-efs-csi-driver
    - install this driver :: **kubectl apply -k "github.com/kubernetes-sigs/aws-efs-csi-driver/deploy/kubernetes/overlays/stable/?ref=release-2.0"**
    - add EFS in **same vpc-1** 
    - add **security-group** to allow traffic with in VPC.
  
```yaml
# SC
              ...
              ...
            name: efs-sc-1
            spec:
              provisoner: efs.csi.aws.com

              ---
              
      # PV
      ...
      ...
      volumnMode: Filesystem
      storageClassName: efs-sc-1
      csi:
        driver: efs.csi.aws.com
        volumehandle:  fs-1  # id  of fs created above
    
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
          - name: vol-1
            pvc:
              claimName: efs-pvc-1
    
       container:
        - ...
          ...
          volumnMounts:
            - name: vol-1
              persistenceVolumeClaim:
                claimName:
              #mountpath: /app/abc  
```
  