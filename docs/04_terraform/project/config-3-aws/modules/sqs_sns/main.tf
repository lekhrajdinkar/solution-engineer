#############################################
# SNS
#############################################
# Create an SNS Topic
resource "aws_sns_topic" "topic_alert" {
  name = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-alert"
  tags = var.tags
  # kms_master_key_id = ""
}

# Create an SNS Topic Policy
resource "aws_sns_topic_policy" "topic_policy" {
  arn = aws_sns_topic.topic_alert.arn
  # policy = jsonencode(local.sns_policy)
  policy = data.aws_iam_policy_document.sns_policy.json
}

# Create an SNS Topic Subscription (optional)
resource "aws_sns_topic_subscription" "topic_subscription" {
  topic_arn = aws_sns_topic.topic_alert.arn
  protocol  = "email"  # Change to "sms", "lambda", "http", etc., as needed
  endpoint  = "lekhrajdinkarus@gmail.com"  # Replace with the actual email or endpoint
}

#############################################
# SQS
#############################################

# Create a Standard SQS Queue
resource "aws_sqs_queue" "standard_queue" {
  name = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-queue"

  delay_seconds               = 5              # Delay for all messages (0-900 seconds), message is delayed before it becomes visible and available for processing
  max_message_size            = 262144         # Max size in bytes (default is 262144 or 256 KB)
  message_retention_seconds   = 345600         # Message retention period (60 seconds to 1209600 seconds)
  receive_wait_time_seconds   = 10             # Wait time for long polling (0-20 seconds)
  visibility_timeout_seconds  = 30             # Timeout for messages in flight (0-43200 seconds)

  # kms_master_key_id = "alias/aws/sqs"         # Specify KMS key for encryption (use alias or ARN)
  # kms_data_key_reuse_period_seconds = 300     # Reuse period for data encryption keys (1-86400 seconds)
}

# Create a FIFO SQS Queue
resource "aws_sqs_queue" "fifo_queue" {
  name                 = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-queue.fifo"
  fifo_queue           = true
  content_based_deduplication = true
}

# SQS Policy for the Standard Queue
resource "aws_sqs_queue_policy" "standard_queue_policy" {
  queue_url = aws_sqs_queue.standard_queue.id
  policy = data.aws_iam_policy_document.sqs_policy.json
}

# SQS Policy for the FIFO Queue
resource "aws_sqs_queue_policy" "fifo_queue_policy" {
  queue_url = aws_sqs_queue.fifo_queue.id
  policy = data.aws_iam_policy_document.sqs_policy.json
}
