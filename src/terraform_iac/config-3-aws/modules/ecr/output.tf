output "ecr_repository_url" {
  value = aws_ecr_repository.repo.repository_url
  description = "The URL of the ECR repository"
}