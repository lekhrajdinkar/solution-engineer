output "s3_bucket_id" { value = aws_s3_bucket.this[0].id }
output "s3_bucket_arn" { value = aws_s3_bucket.this[0].arn }
output "s3_bucket_policy" { value = aws_s3_bucket.this[0].policy }
output "s3_bucket_region" { value = aws_s3_bucket.this[0].region }

output "aws_account_id" { value = data.aws_caller_identity.current.account_id }
output "aws_arn" { value = data.aws_caller_identity.current.arn }