locals {
  s3_inline_policy_template_1 = "policy_07_s3-primary-read"
  s3_inline_policy_template_2 = "policy_07_s3-primary-replication"

  s3_bucket_1 = "archive-bucket"
}

module "s3_archive_bucket_role" {
  source = "modules/iam"
  permission_boundary_name = "DefaultPermissionBoundary"
  policy_desc = "s3 policy"
  policy_name = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-${local.s3_bucket_1}-policy"

  policy_templatefile_name = local.s3_inline_policy_template_1

  policy_templatefile_value_map = {
    region            = var.aws_primary_region
    aws_account_id    = var.aws_account_id
    app_name          = var.app_name
    app_component     = var.app_component
    app_env           = var.app_env
    bucket_name       = local.s3_bucket_1
  }

  role_name = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-${local.s3_bucket_1}-role"
  tags = var.tags
  trusted_service = "s3"
}