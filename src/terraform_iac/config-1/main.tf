# name to complex expressions or repeated values
locals {
  isMicro = var.instance_type == "t2.micro" ? true : false
  local2 = "local_2"
}
# terraform
terraform {
  # can remove cloud, and run locally as well.
  cloud {
    organization = "lekhrajdinkar-org"
    workspaces {
      name = "banzai-dev_api-driven"
    }
  }

  required_providers {
    aws = {
      source  = "registry.terraform.io/hashicorp/aws"
      version = "~> 5.56.0"
    }

    random = {
      source  = "hashicorp/random"
      version = "3.1.0"
    }
  }
  required_version = ">= 1.9.0"
}

# provider
provider "aws" {
  region  = "us-west-2"
  profile = "default" # AWS config
}

