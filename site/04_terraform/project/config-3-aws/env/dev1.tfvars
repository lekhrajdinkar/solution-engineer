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
app_env = "dev1"
container_port = 8080
task_count = 1
cpu = 512
memory = 1024

app_domain_name = null
okta_domain_name = null
aws_zone_id = null