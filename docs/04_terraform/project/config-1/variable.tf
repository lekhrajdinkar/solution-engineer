# used
variable "ec2_server_name" {
  type        = string
  default     = "appServerTerraform"
  description = "ec2 ami-0124ee9682f33ad99 t2.micro | freeTier"
}

variable "aws_access_key" {
  type        = string
  default     = ""
  description = "aws_access_key"
}

variable "aws_access_value" {
  type        = string
  default     = ""
  description = "aws_access_value"
}

variable "instance_type" {
  description = "Type of AWS EC2 instance"
  type        = string
  default     = "t2.micro"
}

variable "allowed_ports" {
  type    = list(number)
  default = [22, 80, 443]
}

# ==============================

variable "instance_count" {
  type    = number
  default = 2

  # validation
  validation {
    condition     = var.instance_count > 0
    error_message = "Instance count must be greater than 0."
  }

  validation {
    condition     = var.instance_count < 10
    error_message = "Instance count must be less than 10."
  }
}

variable "enable_monitoring" {
  type    = bool
  default = true
}

variable "availability_zones" {
  type    = list(string)
  default = ["us-west-2a", "us-west-2b"]
}

variable "tags" {
  type    = map(string)
  default = {
    "Environment" = "Dev"
    "Team"        = "maps-dev-interface"
  }
}

# list of object
variable "instances" {
  type = list(object({
    name          = string
    ami           = string
    instance_type = string
  }))
  default = [
    { name = "web-server", ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro" },
    { name = "app-server", ami = "ami-0c55b159cbfafe1f0", instance_type = "t3.micro" }
  ]
}

