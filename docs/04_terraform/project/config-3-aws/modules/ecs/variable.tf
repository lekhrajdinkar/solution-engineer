variable "app_name" { type = string }
variable "app_component" { type = string }
variable "app_env" { type = string }
variable "aws_primary_region" { type = string }
variable "aws_account_id" { type = string }
variable "tags" { type = map(string) }
variable "vpc_id" { type = string }
variable "desired_count" { type = number }

# task definition and container
variable "cpu" { type = number }
variable "memory" { type = number }
variable "container_image" { type = string }
variable "container_port" { type = number }
variable "host_port" { type = number }
variable "app_domain_name" {  type = string }
variable "container_env_vars" { type = list(any) }
variable "secrets" { type = list(any) }

variable "policy_templatefile_name" { type = string }

variable "alb_sg_ingress_object" {
  type = list(object({
    port = number
    protocol = string
    cid_block = string
  }))
  default = [
    {
      port = 80
      protocol = "tcp"
      cid_block = "0.0.0.0/0"
    },
    {
      port = 443
      protocol = "tcp"
      cid_block = "0.0.0.0/0"
    }]
}


