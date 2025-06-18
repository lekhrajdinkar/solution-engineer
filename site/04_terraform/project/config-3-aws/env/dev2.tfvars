aws_assume_role_arn = "arn:aws:iam::533267082359:role/maps-outbound-harness-pipeline-role"
permission_boundary_name = "DefaultPermissionBoundary"
aws_account_id = "533267082359"
aws_account_alias = "lekhrajdinkar"
aws_env = "dev"
aws_primary_region = "us-west-2"
aws_secondary_region = "us-east-1"
aws_vpc_id = "vpc-04ce2894d2f99bbb8"
tags = {
  purpose = "learning"
  cost_center = "999999"
  app_name = "maps-outbound"
}

app_name = "maps"
app_component = "outbound"
app_env = "dev2"
container_port = 8080
task_count = 1
cpu = 512
memory = 1024

app_domain_name = null
okta_domain_name = null
aws_zone_id = null

###############################
# VPC + VPCE
###############################
private_subnet_route_table_id = "rtb-0b2a6b90d44a9ab58"
public_subnet_route_table_id  = "rtb-0700d4edf2d7c471d"
private_subnet_ids = ["subnet-03d9d5542729daa9b", "subnet-0f003400089b6cd33"]

sg_ingress_object = [
  {
    port = 80
    protocol = "tcp"
    cidr_blocks = ["172.31.16.0/20", "172.31.32.0/20", "172.31.0.0/20"]
  },
  {
    port = 443
    protocol = "tcp"
    cidr_blocks = ["172.31.16.0/20", "172.31.32.0/20", "172.31.0.0/20"]
  }
]

###############################
# lambda
###############################
function_name             = "function-1"
function_1_handler        = "index.handler"
function_1_runtime        = "python3.8"
function_1_timeout        = 300
function_1_memory_size    = 128 #MB
function_1_architectures  = ["x86_64"]
function_1_env_var        = { "env" = "dev2"}

####################
# EKS
#################
namespace              = "dev-ns"
sa_name         = "spring-app-sa"