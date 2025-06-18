/*
data "aws_secretsmanager_secret" "db_password"{
  arn = ""
}
*/

locals {
  container_env_vars  = [
    {
      name = "SPRING_PROFILE_ACTIVE"
      value = var.app_env
    },
    {
      name  = "DB_PASSWORD"
      value = ""
    }
  ]

  secrets             = [
    {
      name      = "DB_PASSWORD"
      valueFrom = ""
    }
  ]
}
###################################################################
# ECR
###################################################################
module "ecr"{
  source = "modules/ecr"

  app_component = var.app_component
  app_env = var.app_env
  app_name = var.app_name
  aws_primary_region = var.aws_primary_region
  tags = var.tags
}

###################################################################
# ECS -> cluster, service, task/container + ALB (listener, targetGroup)
###################################################################
module "ecs"{
  source = "modules/ecs"

  app_component       = var.app_component
  app_env             = var.app_env
  app_name            = var.app_name
  aws_account_id      = var.aws_account_id
  aws_primary_region  = var.aws_primary_region
  tags                = var.tags
  vpc_id              = var.aws_vpc_id

  container_image = "${module.ecr.ecr_repository_url}:latest"
  container_port  = var.container_port
  host_port       = var.container_port
  memory          = var.memory
  cpu             = var.cpu
  app_domain_name = var.app_domain_name

  desired_count     = var.task_count

  container_env_vars = local.container_env_vars
  secrets            = local.secrets
  policy_templatefile_name = "policy_03_ecs_task_exec"

  depends_on = [module.ecr]

}

###################################################################
# ALLOW TRAFFIC FROM ALB to ECS TASK
###################################################################
resource "aws_security_group_rule" "alb_egress_to_ecs_service" {
  security_group_id = module.ecs.alb_sg_arn
  type              = "egress"

  from_port         = var.container_port
  to_port           = var.container_port
  protocol          = "tcp"
  source_security_group_id = module.ecs.ecs_srv_sg_arn
}