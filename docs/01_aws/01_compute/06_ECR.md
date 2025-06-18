## command
**aws ecr get-login-password** --region region | 
  - **docker login** 
  - --username AWS
  - --password-stdin `aws_account_id.dkr.ecr.region.amazonaws.com`
- docker **push** `aws_account_id.dkr.ecr.region.amazonaws.com`/**app-name**:latest
- docker **pull** `aws_account_id.dkr.ecr.region.amazonaws.com`/**app-name**:latest
---
## action
- `ecr:BatchCheckLayerAvailability`: 
  - Allows the task to check the availability of image layers in an Amazon ECR repository to determine if they already exist.
- `ecr:GetDownloadUrlForLayer`: 
  - Provides the ability to get a pre-signed URL for downloading image layers from Amazon ECR.
- `ecr:BatchGetImage`: 
  - Grants permission to pull images by retrieving metadata and image layers from an Amazon ECR repository.
- `ecr:GetAuthorizationToken`: 
  - Enables the retrieval of an authorization token used to authenticate to an Amazon ECR registry for Docker CLI and other client pull/push operations.