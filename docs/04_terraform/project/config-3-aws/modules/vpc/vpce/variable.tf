#############
# prefix
#############
variable "app_name" {  type = string }
variable "app_component" {  type = string }
variable "aws_primary_region" {  type = string }
variable "app_env" {  type = string }

### VPCE ####
variable "aws_vpc_id" {  type = string }
variable "tags" {  type = map(string) }

variable "sg_ingress_object" {
  type = list(object({
    port = number
    protocol = string
    cidr_blocks = list(string)
  }))
}

variable "private_subnet_route_table_id" {  type = string }
variable "public_subnet_route_table_id" {  type = string }
variable "private_subnet_ids" { type = list(string)}

