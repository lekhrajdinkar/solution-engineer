#####################
# WAY-1 : datasource **
#####################
data "aws_iam_policy_document" "sns_policy" {
  statement {
    actions   = ["SNS:Publish", "SNS:Subscribe", "SNS:Receive"]
    resources = [aws_sns_topic.topic_alert.arn]

    principals {
      type        = "AWS"
      identifiers = ["*"]
    }

    condition {
      test     = "StringEquals"
      variable = "aws:SourceAccount"
      values   = ["${var.aws_account_id}"]
    }
  }
}

#########################################
# WAY-2 : local variable + jsonencode
#########################################
locals {
  sns_policy = {
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = "*"
        Action = [
          "SNS:Publish",
          "SNS:Subscribe",
          "SNS:Receive",
          "SNS:DeleteTopic"
        ]
        Resource = aws_sns_topic.topic_alert.arn
        Condition = {
          ArnEquals = {
            "aws:SourceArn" = "arn:aws:sns:${var.aws_primary_region}:${var.aws_account_id}:*"
          }
        }
      }
    ]
  }
}

#########################################
# WAY-3 : templateFile - actual json
#########################################
## skip

# -----------------------------------

data "aws_iam_policy_document" "sqs_policy" {
  statement {
    actions   = [
      "sqs:SendMessage",
      "sqs:ReceiveMessage",
      "sqs:DeleteMessage",
      "sqs:GetQueueAttributes",
      "sqs:GetQueueUrl"
    ]
    resources = [aws_sqs_queue.standard_queue.arn]

    principals {
      type        = "AWS"
      identifiers = ["*"]
    }

    condition {
      test     = "StringEquals"
      variable = "aws:SourceAccount"
      values   = ["${var.aws_account_id}"]
    }
  }
}




