provider "aws" {
  region = var.aws_primary_region

  assume_role {
    role_arn = var.aws_assume_role_arn
  }
  shared_credentials_files = ["./credentials"]
  profile                  = "trf"

}

provider "aws" {
  region = var.aws_secondary_region

  alias = "secondary"
  assume_role {
    role_arn = var.aws_assume_role_arn
  }
  shared_credentials_files = ["./credentials"]
  profile                  = "trf"
}

terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.15.0"
    }
  }
}