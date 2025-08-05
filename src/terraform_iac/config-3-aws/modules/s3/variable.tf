variable "aws_account_alias" { type = string }
variable "create_bucket_flag" {
  type    = bool
  default = true
}
variable "bucket_name" { type = string }
variable "logging_configs" { type = map(string) }
variable "versioning_configs" {
  type = object({
    enabled            = bool
    mfa_delete_enabled = bool
  })
}
variable "tags" { type = map(string) }

variable "object_lock_flag" {
  type    = bool
  default = false
}
variable "force_destroy_flag" {
  type    = bool
  default = false
}
variable "object_ownership" {
  type    = string
  default = "BucketOwnerEnforced"
}

variable "primary_region" { type = string }
variable "secondary_region" { type = string }

variable "replicate_flag" { type = bool }
#variable "replicate_role_arn" { type = string }
#variable "replicate_key_kms_arn" { type = string }
variable "replicate_bucket_name" { type = string }
#variable "kms_master_key_id" { type = string }

variable "mft_account_arn" { type = string }

variable "attach_policy_flag" {
  type    = bool
  default = false
}
variable "policy" {
  type    = string
  default = null
}

variable "acl" {
  type    = string
  default = "private"
}
variable "attach_deny_insecure_transport_policy" {
  type    = bool
  default = false
}
variable "sse_configs" { type = map(string) }