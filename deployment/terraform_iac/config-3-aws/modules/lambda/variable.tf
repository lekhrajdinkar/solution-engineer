# --- AWS ---
variable "permission_boundary_name" {  type = string }
variable "aws_account_id" {  type = string }
variable "aws_account_alias" {  type = string }
variable "aws_env" {  type = string }
variable "aws_assume_role_arn" {  type = string }
variable "aws_primary_region" {  type = string }
variable "aws_secondary_region" {  type = string }
variable "aws_vpc_id" {  type = string }
variable "aws_zone_id" {  type = string }
variable "tags" {  type = map(string) }

# --- app ---
variable "app_name" {  type = string }
variable "app_component" {  type = string }
variable "app_env" {  type = string }
variable "app_domain_name" {  type = string }
variable "container_port" {  type = number }
variable "cpu" {  type = number }
variable "memory" {  type = number }
variable "task_count" {  type = number }
variable "okta_domain_name" {  type = string }

# -----lambda function--------
variable "function_name" {  type = string }
variable "function_1_handler" {  type = string }
variable "function_1_runtime" {  type = string }
variable "function_1_timeout" {  type = number }
variable "function_1_memory_size" {  type = number }
variable "function_1_architectures" {  type = list(string) }
variable "function_1_env_var" {  type = map(string) }



