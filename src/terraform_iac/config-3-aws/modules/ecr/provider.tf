provider "aws" {
  region = var.aws_primary_region

  assume_role {
    role_arn = var.aws_assume_role_arn
  }
  shared_credentials_files = ["./credentials"]
  profile                  = "trf"

}

terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "5.15.0"
    }
  }
}


terraform {
  cloud {
    organization = "lekhrajdinkar-org"
    workspaces {
      name = "aws-config-maps-outbound-dev2-ecr"
    }
  }
}