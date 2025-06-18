data "aws_caller_identity" "current" {}

data "aws_iam_policy" "permission_boundary"{
  arn = "arn:aws:iam::${data.aws_caller_identity.current.account_id}:policy/${var.permission_boundary_name}"
}

# create inline policy from template
resource "aws_iam_policy" "inline_policy" {
  name        = var.policy_name
  description = var.policy_desc
  path        = "/"
  policy      = templatefile("${path.module}/templates/${var.policy_templatefile_name}.tftpl", var.policy_templatefile_value_map )
  tags        = var.tags

  lifecycle {
    ignore_changes = [ tags ]
  }
}

# Role
# attach - trusted policy + permission_boundary
resource "aws_iam_role" "role"{
  name                  = var.role_name
  assume_role_policy    = templatefile("${path.module}/templates/${var.trsusted_policy_templatefile_name}.tftpl", { trusted_service = var.trusted_service} )
  permissions_boundary  = data.aws_iam_policy.permission_boundary.arn

  lifecycle {
    ignore_changes = [ tags ]
  }
}

# Attach Role with inline policy
resource "aws_iam_role_policy_attachment" "role-policy-attachment" {
  policy_arn = aws_iam_policy.inline_policy.arn
  role       = var.role_name

  depends_on = [ aws_iam_policy.inline_policy, aws_iam_role.role]
}