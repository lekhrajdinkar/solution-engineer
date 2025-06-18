variable "policy_name" {
  type = string
}
variable "policy_desc" {
  type = string
}
variable "policy_templatefile_name" {
  type = string
}
variable "policy_templatefile_value_map" {
  type = map(string)
}

variable "trsusted_policy_templatefile_name" {
  type = string
  default = "trusted_policy"
}


variable "role_name" {
  type = string
}

variable "trusted_service" {
  type = string
}


variable "tags" {
  type = map(string)
}

variable "permission_boundary_name" {
  type = string
  default = "DefaultPermissionBoundary"
}


