https://developer.hashicorp.com/terraform/tutorials/modules/module
https://developer.hashicorp.com/terraform/tutorials/modules/pattern-module-creation - check this example
---
# modules
## concept
- organize config into smaller config.
- import from Each other.
- each directory represents a separate configuration.
- benefits:
  - `Reusability`: Write code once and reuse it in multiple configurations or environments.
  - `Maintainability`: Encapsulate complex configurations into simpler, reusable components.
  - `Organization`: Keep your Terraform code organized by grouping related resources together.
- Type:
  - `remote` : 
    - "terraform-aws-modules/vpc/aws"
    - relying on the work of others to implement common infrastructure scenarios.
    - Like, packages, modules, libraries in other prog language
  - `local` : localFileSystem -  "./directory-1/child-module-1.tf"
- if child-module-1 has 2 variable and no default value set.
  - then have provide/pass value, while import module.   <<<< 
  ```
  module "child-module-1" {
    source = "./directory-1/child-module-1.tf"
    child_var_1 = value-1
    child_var_2 = value-1  <<<<
    ...
    ...
    # rather than passing soo many varaible, pass object. -- good practice.
  }
  ```
- **terraform init | get**  --> installs the module. 
- `${path.module}` --> built-in expression ,file path of the current module being executed
---

## example: my s3 module
- check : [s3](../../deployment/terraform_iac/config-3-aws/modules/s3)
- resource aws_s3_bucket
- resource aws_s3_bucket_`logging`
- resource aws_s3_bucket_`public_access_block`
- resource aws_s3_bucket_`versioning` - t/f
  - mfa_delete - t/f
- resource aws_s3_bucket_`server_side_encryption `
  - SSE-S3, SSE-KMS
- resource aws_s3_`bucket_ownership_controls`
  - **ACL disable**
    - objects in this bucket are owned by this account.
    - Access to this bucket and its objects is specified using only **policies**.
  - **ACL enable**
    - Objects in this bucket can be owned by other AWS accounts
- resource aws_s3_`bucket_policy`
- resource aws_s3_`bucket_replication_configuration`
- `more`
  - s3 event **notification**
  - **object lock** : WORM - enable / disable.
  - static **website** hosting


