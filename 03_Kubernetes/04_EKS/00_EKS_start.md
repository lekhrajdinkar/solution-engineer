# references
- starbucks Q: 
- OIDC provider: with Okta `Dont use this`  <<<
    - https://dev-16206041-admin.okta.com/
    - https://dev-16206041.okta.com/
    - eks-cluster-app : https://dev-16206041-admin.okta.com/admin/app/oidc_client/client/0oal3d72smuSHBhwF5d7#tab-general
        - client_id : 0oal3d72smuSHBhwF5d7
        - issuer URI :
            - https://dev-16206041.okta.com/oauth2/default (default)
            - https://dev-16206041.okta.com/oauth2/ausl3dg4kkpyvEBft5d7

---          
# Additional :point_left:
- [10_podDisruptionBudget.yaml](../../deployment/manifest/spring_app_v2/more/10_podDisruptionBudget.yaml)
- [09_HPA.yaml](../../deployment/manifest/spring_app_v2/more/09_HPA.yaml)
- [08_external_secret.md](99_CG_Ext-secret-2)
- **annotation**:
  - [07_annotation-ingress.md](99_CG_annotation-ingress)
  - [07_annotation-Pod.md](99_CG_annotation-Pod)
  - [07_annotation-sa.md](99_CG_annotation-sa)
---  
# chatgpt:
- EKS 04 - Authentication + IRSA  : https://chatgpt.com/c/67342f43-7220-800d-8831-68fe91ea7a87
- EKS 02 - OIDC with okta : https://chatgpt.com/c/67341083-2714-800d-b4f6-6b52821c0181 `skip`
- EKS-03 - `ns:kube-system > configMap:aws-auth` : https://chatgpt.com/c/6734280e-7d48-800d-b410-280da79926fe
    - access  specific namespace based on iam-role-assumed-by-sa  <<<
    - eg: sa-1 > assumed role-1 > access ns-1
- EKS + harness pipeline - `handson`
  - https://chatgpt.com/c/67346f23-ce58-800d-9b35-a0ccf088f920
  - https://chatgpt.com/c/67352892-e094-800d-a053-9a51c1074097
  - https://chatgpt.com/c/67358116-3f1c-800d-96c6-c6d447f1b283
- setup new user
  - https://chatgpt.com/c/67371203-d934-800d-94f1-3c996d9584dd
---
```

aws ec2 describe-subnets --filters "Name=vpc-id,Values=vpc-id-from-output" --region us-west-2
aws ec2 describe-vpcs --vpc-ids vpc-id-from-output --region us-west-2
aws eks list-fargate-profiles --cluster-name your-cluster-name --region us-west-2
aws ec2 describe-vpc-endpoints --filters "Name=vpc-id,Values=vpc-id-from-output" --region us-west-2
aws ec2 describe-route-tables --filters "Name=vpc-id,Values=vpc-id-from-output" --region us-west-2
aws ec2 describe-security-groups --filters "Name=vpc-id,Values=vpc-id-from-output" --region us-west-2


aws eks describe-cluster  --name maps-outbound-us-west-2-dev2-eks-fargate-cluster --region us-west-2 --query "cluster.identity.oidc.issuer"
aws iam create-open-id-connect-provider --url https://oidc.eks.us-west-2.amazonaws.com/id/867FAFA03F6706024B5895223D5D3451 --client-id-list sts.amazonaws.co
aws eks get-token  --cluster-name maps-outbound-us-west-2-dev2-eks-fargate-cluster --region us-west-2
aws eks update-kubeconfig --name maps-outbound-us-west-2-dev2-eks-fargate-cluster --region us-west-2

aws eks describe-update --name maps-outbound-us-west-2-dev2-eks-fargate-cluster  --update-id 388626d9-068d-3325-b988-f15ecd94ee51 --region us-west-2
# got update it from trf logs

kubectl get configmap aws-logging -n kube-system
```
---
## ECS vs EKS
- `Clusters`
    - logical grouping of tasks or services.
    - Equivalent to `Cluster in Kubernetes`

- `Tasks` === pod
    - A single running copy of a container defined by a task definition.

- `Task Definitions` == pod
    - Blueprints for your application that specify the container images, CPU, memory, and other settings.

- `Services`  === Replica Set
    - Allows you to run and maintain a specified number of instances of a task definition simultaneously.

- `Container Instances` == work Nodes
    - Amazon EC2 instances registered to your cluster and used to run tasks.
    - Equivalent in Kubernetes: `Nodes`

- `Elastic Load Balancing (ELB)`   === Service (specifically, LoadBalancer type)
    - Distributes incoming application traffic across multiple targets.

- `Auto Scaling` === Horizontal Pod Autoscaler
    - Adjusts the desired count of tasks in a service automatically based on criteria.

- `ECS Agent` === Kubelet
    - Software that runs on each container instance and communicates with ECS to start and stop tasks.

- `ECS Fargate`
    - A serverless compute engine for containers that eliminates the need to manage EC2 instances.