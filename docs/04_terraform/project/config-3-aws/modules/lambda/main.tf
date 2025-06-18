locals {
  lambda_code_path  = "${path.module}/functions"
  prefix            = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}"
}
# ======== data source==========
data "aws_caller_identity" "current" {}
data "aws_iam_policy" "permission_boundary"{
  arn = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:policy/${var.permission_boundary_name}"
}
data "aws_subnets" "subnets" {
  filter {
    name   = "vpc-id"
    values = [var.aws_vpc_id]
  }
}
data "archive_file" "lambda_zip" {
  type        = "zip"
  source_dir  = "${local.lambda_code_path}/${var.function_name}"  # Path to your Lambda code directory
  output_path = "${local.lambda_code_path}/${var.function_name}.zip"    # Output path for the ZIP file
  excludes    = ["${var.function_name}.zip"]
}

# ======== 0. lambda layer ==========

# ======== 1. lambda function ==========

resource "aws_lambda_function" "lambda_function_1" {
  function_name = "${local.prefix}-function-1"
  description   = "Demo function-1"
  role          = aws_iam_role.lambda_exec_role.arn
  handler       = var.function_1_handler
  runtime       = var.function_1_runtime
  filename      = "${local.lambda_code_path}/${var.function_name}.zip"
  tags          = var.tags
  environment  {
    variables   = var.function_1_env_var
  }
  vpc_config {
    security_group_ids = [aws_security_group.lambda_sg.id]
    subnet_ids         = data.aws_subnets.subnets.ids
  }

  timeout = var.function_1_timeout
  memory_size = var.function_1_memory_size
  architectures = var.function_1_architectures

  # kms_key_arn = var.function_1_kms_key_arn
  # source_code_hash = var.function_1_source_code_hash
}

#========= 2. sg ========================
resource "aws_security_group" "lambda_sg" {
  name        = "${local.prefix}-lambda-sg"
  description = "Security group for lambda"
  vpc_id      = var.aws_vpc_id
  # ingress {}
  # egress {}
}

#========= 3. logs ========================
# The naming convention /aws/lambda/<lambda-function-name> is typically used for Lambda log groups
resource "aws_cloudwatch_log_group" "lambda_log_group" {
  name              = "/aws/lambda/${local.prefix}-function-1"
  retention_in_days = 1
  tags              = var.tags
  # kms_key_id      = ""
  depends_on        = [aws_lambda_function.lambda_function_1]
}

# ======== 4. lambda exec role ==========
resource "aws_iam_role" "lambda_exec_role"{
  name                  = "${local.prefix}-lambda-role"
  assume_role_policy    = templatefile("${path.module}/trusted_policy.tftpl", { trusted_service = "lambda"} )
  permissions_boundary  = data.aws_iam_policy.permission_boundary.arn
}
resource "aws_iam_policy" "lambda_inline_policy" {
  name        = "${local.prefix}-lambda-policy"
  description = "lambda execution role inline policy"
  path        = "/"
  policy      = templatefile("${path.module}/policy_04_lambda_exec.tftpl", {} )
  tags        = var.tags

  lifecycle {
    ignore_changes = [ tags ]
  }
}
resource "aws_iam_role_policy_attachment" "role-policy-attachment" {
  policy_arn = aws_iam_policy.lambda_inline_policy.arn
  role       = aws_iam_role.lambda_exec_role.name
  depends_on = [ aws_iam_policy.lambda_inline_policy, aws_iam_role.lambda_exec_role]
}