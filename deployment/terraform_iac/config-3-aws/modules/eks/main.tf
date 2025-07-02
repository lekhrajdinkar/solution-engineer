locals {
  prefix = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}"
  selected_az = element(data.aws_availability_zones.available.names, 0)
}
data "aws_availability_zones" "available" {}

# ============================================================================================================

# A. Cluster
## A.1 cluster role
resource "aws_iam_role" "eks_cluster_role" {
  name               = "${local.prefix}-eks-cluster-role"
  assume_role_policy = templatefile("${path.module}/trusted_policy.tftpl", { trusted_service = "eks"})
  # managed_policy_arn = ["arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"]
}


# attach inline policy - optional
/*
resource "aws_iam_policy" "eks_cluster_inline_policy" {
  policy = templatefile("${path.module}/policy_05_eks_cluster.tftpl", {})
}
resource "aws_iam_role_policy_attachment" "eks_cluster_inline_policy_attachment" {
  role       = aws_iam_role.eks_cluster_role.name
  policy_arn = aws_iam_policy.eks_cluster_inline_policy.arn
}
*/

# attach inbuilt policy - AmazonEKSClusterPolicy
resource "aws_iam_role_policy_attachment" "eks_cluster_policy_attachment" {
  role       = aws_iam_role.eks_cluster_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
}

# A.2 EKS Cluster
resource "aws_eks_cluster" "eks_cluster" {
  name     = "${local.prefix}-eks-fargate-cluster"
  role_arn = aws_iam_role.eks_cluster_role.arn

  vpc_config {
    subnet_ids = aws_subnet.eks_private_subnet[*].id
  }

  depends_on = [aws_iam_role_policy_attachment.eks_cluster_policy_attachment, aws_vpc.eks_vpc]
}

# ============================================================================================================

# B Node - fargate profile
## B.1 eks node role
resource "aws_iam_role" "eks_pod_exec_role" {
  name               = "${local.prefix}-fargate-profile-role"
  assume_role_policy =  templatefile("${path.module}/trusted_policy.tftpl", { trusted_service = "eks-fargate-pods"})
}

# attach inline policy - optional
resource "aws_iam_policy" "eks_pod_exec_inline_policy" {
  policy = templatefile("${path.module}/policy_06_eks_pod.exec.tftpl", {})
}
resource "aws_iam_role_policy_attachment" "eks_nodegroup_inline_policy_attachment" {
  role       = aws_iam_role.eks_pod_exec_role.name
  policy_arn = aws_iam_policy.eks_pod_exec_inline_policy.arn
}

# attach inbuilt policy - AmazonEKSFargatePodExecutionRolePolicy
resource "aws_iam_role_policy_attachment" "eks_node_policy_attachment" {
  role       = aws_iam_role.eks_pod_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSFargatePodExecutionRolePolicy"
}

## B.2 eks - fargate-profile
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

# ============================================================================================================

/*
#  C Node - ec2 launch type
## C.1 NodegroupRole
resource "aws_iam_role" "eks_nodegroup_role" {
  name               = "${local.prefix}-eks-ng-role"
  assume_role_policy =  templatefile("${path.module}/trusted_policy.tftpl", { trusted_service = "eks-fargate-pods"})
  # managed_policy_arn = ["arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy", "",""]
}

# attach inline policy - optional
resource "aws_iam_policy" "eks_nodegroup_inline_policy" {
  policy = templatefile("${path.module}/policy_07_eks_nodegroup.tftpl", {})
}
resource "aws_iam_role_policy_attachment" "eks_nodegroup_inline_policy_attachment" {
  role       = aws_iam_role.eks_nodegroup_role.name
  policy_arn = aws_iam_policy.eks_nodegroup_inline_policy.arn
}

# attach inbuilt policy - AmazonEKSClusterPolicy, AmazonEC2FullAccess, CloudWatchAgentServerPolicy
resource "aws_iam_role_policy_attachment" "eks_node_policy_attachment" {
  role       = aws_iam_role.eks_nodegroup_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
}
resource "aws_iam_role_policy_attachment" "eks_AmazonEC2Policy_attachment" {
  role       = aws_iam_role.eks_nodegroup_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2FullAccess"
}
resource "aws_iam_role_policy_attachment" "eks_CloudWatchPolicy_attachment" {
  role       = aws_iam_role.eks_nodegroup_role.name
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchAgentServerPolicy"
}

## C.2 NodeGroup
resource "aws_eks_node_group" "eks_node_group" {
  node_group_name = "${local.prefix}-nodegroup"
  cluster_name    = aws_eks_cluster.eks_cluster.name
  node_role_arn   = aws_iam_role.eks_nodegroup_role.arn
  subnet_ids      = aws_subnet.eks_subnet.*.id

  scaling_config {
    desired_size = 2
    min_size     = 1
    max_size     = 3
  }

  depends_on = [aws_iam_role_policy_attachment.eks_node_policy_attachment]
}
*/

#======================================
# VPC
resource "aws_vpc" "eks_vpc" {
  cidr_block = "10.0.0.0/16"
  tags = var.tags
}
# add 2 private subnets
resource "aws_subnet" "eks_private_subnet" {
  count             = 2                                                      # Create 2 subnets (one for each Availability Zone)
  vpc_id            = aws_vpc.eks_vpc.id                                     # 10.0.0.0/16
  #cidr_block        = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index) # 10.0.1.0/24  or  10.0.2.0/24
  cidr_block        = "10.0.${count.index}.0/24"
  availability_zone = element(data.aws_availability_zones.available.names, count.index)
  tags              = var.tags
  map_public_ip_on_launch = false
}

/*
# Internet Gateway - VPC
resource "aws_internet_gateway" "eks_vpc_internet_gateway" {
  vpc_id = aws_vpc.eks_vpc.id
}

# nat gateway
resource "aws_nat_gateway" "eks_nat_gateway" {
  allocation_id = aws_eip.nat_eip.id
  subnet_id     = aws_subnet.eks_private_subnet[0].id
}
resource "aws_eip" "nat_eip" {
  vpc = true
}
*/

# Create a Route Table and Associate it with the Subnets
resource "aws_route_table" "eks_route_table" {
  vpc_id = aws_vpc.eks_vpc.id

  route {
    cidr_block = "10.0.0.0/16"
    gateway_id = "local"
    # nat_gateway_id = aws_nat_gateway.eks_nat_gateway.id
    # gateway_id = aws_internet_gateway.eks_vpc_internet_gateway.id
  }
}
resource "aws_route_table_association" "eks_subnet_association" {
  count          = length(aws_subnet.eks_private_subnet)
  subnet_id      = aws_subnet.eks_private_subnet[count.index].id
  route_table_id = aws_route_table.eks_route_table.id
}

# ============iam : identity provider ODIC ============

resource "aws_eks_identity_provider_config" "oidc" {
  cluster_name = aws_eks_cluster.eks_cluster.name

  oidc {
    identity_provider_config_name = "${local.prefix}-oidc-eks"
    issuer_url                    = "https://oidc.eks.${var.aws_primary_region}.amazonaws.com/id/${aws_eks_cluster.eks_cluster.id}"
    client_id                     = "sts.amazonaws.com"
  }
  depends_on = [aws_eks_cluster.eks_cluster]
}

# iam : ID provider - create manually
/*
resource "aws_iam_openid_connect_provider" "eks_oidc_provider" {
  client_id_list  = ["sts.amazonaws.com"]
  thumbprint_list = ["9e99a48a9960b14926bb7f3b02e22da2b0ab7280"]
  url             = "https://oidc.eks.${var.aws_primary_region}.amazonaws.com/id/${aws_eks_cluster.eks_cluster.id}"
}
*/


# =========== Role for K8s service Account for pod ============

resource "aws_iam_role" "eks_cluster_sa_role" {
  name               = "${local.prefix}-k8s-sa-role"
  assume_role_policy = templatefile("${path.module}/trusted_policy_k8s_federated_sa.tftpl", {
    # oidc_id = aws_eks_cluster.eks_cluster.identity[0].oidc[0].id
    eks_cluster_id  = aws_eks_cluster.eks_cluster.id
    region          = var.aws_primary_region
    aws_account_id  = var.aws_account_id
    ns              = var.namespace
    sa_name         = var.sa_name
  })
  depends_on = [aws_eks_cluster.eks_cluster]
}
resource "aws_iam_role_policy_attachment" "eks_cluster_sa_role_s3_full" {
  role       = aws_iam_role.eks_cluster_sa_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonS3FullAccess"
}


# =========== Role for eks federated user ============
# mauanlly added :: arn:aws:iam::533267082359:role/eks-cluster-role-1-for-federated-user
# https://us-east-1.console.aws.amazon.com/iam/home?region=us-west-2#/roles/details/eks-cluster-role-1-for-federated-user?section=permissions
# this user will connect to eks cluster
# then use it in harness connector.