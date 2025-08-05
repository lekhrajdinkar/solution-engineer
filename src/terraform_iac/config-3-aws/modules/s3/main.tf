resource "aws_s3_bucket" "this" {
  count               = var.create_bucket_flag ? 1 : 0
  bucket              = var.bucket_name
  force_destroy       = var.force_destroy_flag
  object_lock_enabled = var.object_lock_flag
  tags                = var.tags
}

resource "aws_s3_bucket_logging" "this" {
  count         = var.create_bucket_flag ? 1 : 0
  bucket        = aws_s3_bucket.this[0].id
  target_bucket = var.logging_configs["target_bucket"]
  target_prefix = try(var.logging_configs["target_prefix"], null)
}

resource "aws_s3_bucket_public_access_block" "this" {
  bucket                  = aws_s3_bucket.this[0].id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

resource "aws_s3_bucket_versioning" "this" {
  bucket = aws_s3_bucket.this[0].id
  versioning_configuration {
    status     = var.versioning_configs.enabled ? "Enabled" : "Disabled"
    mfa_delete = var.versioning_configs.mfa_delete_enabled ? "Enabled" : "Disabled"
  }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "this" {
  bucket = aws_s3_bucket.this[0].id
  rule {
    bucket_key_enabled = true
    apply_server_side_encryption_by_default {
      # sse_algorithm     = "aws:kms" #sse-kms
      # kms_master_key_id = data.aws_kms_key.s3_key
      sse_algorithm = "AES256" #sse-s3
    }
  }
}

resource "aws_s3_bucket_policy" "this" {
  count  = var.create_bucket_flag ? 1 : 0
  bucket = aws_s3_bucket.this[0].id
  policy = data.aws_iam_policy_document.this[0].json
}

resource "aws_s3_bucket_ownership_controls" "this" {
  bucket = aws_s3_bucket.this[0].id
  rule {
    object_ownership = var.object_ownership
  }
  depends_on = [aws_s3_bucket_policy.this]
}

## replication
resource "aws_iam_role" "s3_replication_role" {
  inline_policy {
    name = "s3-full-access-policy"
    policy = jsonencode({
      "Version": "2012-10-17",
      "Statement": [
        {
          "Effect": "Allow",
          "Action": "s3:*",
          "Resource": "*"
        }
      ]
    })
  }
  assume_role_policy = jsonencode({
    "Version": "2012-10-17",
    "Statement": [
      {
        "Effect": "Allow",
        "Principal": {
          "Service": "s3.amazonaws.com"
        },
        "Action": "sts:AssumeRole"
      }
    ]
  })
}

resource "aws_s3_bucket_replication_configuration" "this" {
  count = var.replicate_flag ? 1 : 0

  bucket = aws_s3_bucket.this[0].id
  role   = aws_iam_role.s3_replication_role.arn
  rule {
    id     = "replication-rule-1"
    status = "Enabled"
    delete_marker_replication { status = "Enabled" }
    destination {
      bucket        = "arn:aws:s3:::${var.replicate_bucket_name}"
      storage_class = "STANDARD"
      /*encryption_configuration {
        replica_kms_key_id = data.aws_kms_key.replication_s3_key.id
      }*/
    }
    # source_selection_criteria { sse_kms_encrypted_objects {status="Enabled"} }
    filter {}
  }
}