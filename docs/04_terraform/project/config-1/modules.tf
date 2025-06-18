# import child modules
## local
module "module_s3_website" {
  source = "modules/module-s3-website"

  # pass 4 variable
  bucket_name = "my-bucket-1"
  tags = {
    Terraform   = "true"
    Environment = "dev"
  }
  error_document_key = "error.html"
  index_document_suffix = "index.html"

  # pass single object
  s3_website_config_object = {
    bucket_name = "my-bucket-1"
    tags = {
      Terraform   = "true"
      Environment = "dev"
    }
    error_document_key = "error.html"
    index_document_suffix = "index.html"
  }
}

module "module_2" {
  source = "modules/module-2"
}

## remote
# source  = "terraform-aws-modules/vpc/aws"
# source  = "terraform-aws-modules/elb/aws"