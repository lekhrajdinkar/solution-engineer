variable "app_name" { type = string }
variable "app_component" { type = string }
variable "app_env" { type = string }
variable "aws_primary_region" { type = string }
variable "aws_account_id" { type = string }
variable "tags" { type = map(string) }
variable "vpc_id" { type = string }
variable "container_port" { type = number }
variable "app_domain_name" {  type = string }

# ------------------------------------------------

variable "sg_ingress_object" {
  type = list(object({
    port = number
    protocol = string
    cid_block = string
  }))
}

