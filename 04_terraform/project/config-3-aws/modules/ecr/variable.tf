variable "app_name" { type = string }
variable "app_component" { type = string }
variable "app_env" { type = string }
variable "aws_primary_region" { type = string }
variable "tags" { type = map(string) }
variable "aws_assume_role_arn" {  type = string }