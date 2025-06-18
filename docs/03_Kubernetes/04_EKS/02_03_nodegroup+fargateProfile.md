# A. Nodegroup
## 1. intro
- collection of nodes (virtual machines) within a cluster that share the **same/Homogeneous configuration**. 
  - same instance type, 
  - disk size, 
  - AMI (Amazon Machine Image)
  - ...
- Scaling + Availability
- Workload Separation, eg:
  - nodegroup-1: frontend/s (need to more secure security, facing interbet)
  - nodegroup-2: backend/s + batch processing/s
  - ...
- Cost Optimization :Use different instance-types in separate node groups to optimize costs **based on workload requirements**. 

```
aws eks create-nodegroup \
--cluster-name my-cluster \
--nodegroup-name my-nodegroup-1 \
--subnets subnet-12345678 subnet-87654321 \
--instance-types t3.medium \
--scaling-config minSize=1,maxSize=10,desiredSize=2 \
--ami-type AL2_x86_64 \
--node-role arn:aws:iam::123456789012:role/EKSNodeInstanceRole
```

---
# B. Fargate profile
## 1 intro
- input:
  - namespace + additional label 
  - podExecutionRoleArn : role-1 (used by pods) - to Pull container images from ECR,Create ENIs, etc
  - subnets
- Also, SA annotated with role-2, mounted on pod.
  - Used by the Pod for AWS SDK/API calls
  - for accessing AWS services (like S3, DynamoDB, etc.)
- https://chatgpt.com/c/684c5acc-4de4-800d-9b8b-2bb44031a6e5

## 2 create with eks eksCtl, CRD, trf
```yaml
# =========CRD============
apiVersion: eks.amazonaws.com/v1
kind: FargateProfile
metadata:
  name: dev-fargate-profile
spec:
  clusterName: your-eks-cluster-name
  podExecutionRoleArn: arn:aws:iam::123456789012:role/your-pod-execution-role
  selectors:
    - namespace: dev-ns
```
```bash
# ==========aws cli===========
aws eks create-fargate-profile \
  --cluster-name cluster-1 \
  --fargate-profile-name profile-1 \
  --namespace dev-ns \
  --pod-execution-role-arn arn:aws:iam::123456789012:role/your-pod-execution-role
  --lable env=dev-pod
```
```terraform
# ==========trf===========
resource "aws_eks_fargate_profile" "eks_fargate_profile" {
  cluster_name = aws_eks_cluster.eks_cluster.name
  fargate_profile_name = "${local.prefix}-fargate-profile"
  pod_execution_role_arn = aws_iam_role.eks_pod_exec_role.arn

  subnet_ids = aws_subnet.eks_private_subnet[*].id

  selector {
    namespace = var.namespace
  }
  depends_on = [
    aws_eks_cluster.eks_cluster,
    aws_iam_role.eks_pod_exec_role
  ]
}
```



