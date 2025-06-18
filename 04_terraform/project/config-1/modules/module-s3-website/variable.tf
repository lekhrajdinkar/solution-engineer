# Input variable definitions
variable "bucket_name" {
  description = "Name of the s3 bucket. Must be unique."
  type        = string
}

variable "tags" {
  description = "Tags to set on the bucket."
  type        = map(string)
  default     = {}
}

variable "index_document_suffix" {
  description = "Suffix for index documents."
  type        = string
  #default     = "index.html"
}

variable "error_document_key" {
  description = "Key for error document."
  type        = string
  #default     = "error.html"
}

# ==========
variable "s3_website_config_object" {
  description = "Configuration for S3 website"
  type = object({
    bucket_name           = optional(string, "")
    error_document_key    = optional(string, "error.html")
    index_document_suffix = optional(string, "index.html")
    tags                  = optional(map(string), {})
  })
}
