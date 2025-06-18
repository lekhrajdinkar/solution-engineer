# Create an ECR repository
resource "aws_ecr_repository" "repo" {
  name = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-repo"
  tags = var.tags

  image_scanning_configuration {
    scan_on_push = true
  }

  encryption_configuration {
    encryption_type = "AES256" #kMS
    #kms_key = "kms_key_arn"
  }

  lifecycle {
    ignore_changes = [tags]
  }
}

# Output the repository URL


