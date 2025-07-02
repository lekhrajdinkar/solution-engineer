data "aws_caller_identity" "current" {}

data "aws_iam_policy_document" "organization-policy" {
  statement {
    effect = "Allow"
    principals {
      type       = "AWS"
      identifiers = ["arn:aws:iam::${data.aws_caller_identity.current.id}:root"]
    }
    actions = [
      "s3:*"
    ]
    resources = [
      aws_s3_bucket.this[0].arn,
      "${aws_s3_bucket.this[0].arn}/*"
    ]
  }
}

data "aws_iam_policy_document" "this" {
  count = var.create_bucket_flag ? 1 : 0
  source_policy_documents = compact([
    data.aws_iam_policy_document.organization-policy.json,
    var.attach_policy_flag ? var.policy : null
  ])
}

# Kms keys
/*
locals {
  kms_region = lookup({"us-east-1"="useast1", "us-west-2"="uswest2"}, var.primary_region)
  replication_kms_region = lookup({"us-east-1"="useast1", "us-west-2"="uswest2"}, var.secondary_region)
}
data "aws_kms_key" "s3_key" {
  key_id = "alias/${var.aws_account_alias}/${local.kms_region}/s3/0/kek"
}
data "aws_kms_key" "replication_s3_key" {
  key_id = "alias/${var.aws_account_alias}/${local.replication_kms_region}/s3/0/kek"
}
*/
