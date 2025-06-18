- https://developer.hashicorp.com/terraform/tutorials/configuration-language

--- 
# HCL ( HashiCorp Configuration Language)
## A. basic
- used write `configuration` - to create infra.
- **String-interpolation** 
  - `web-sg-${var.resource_tags["project"]}-${var.resource_tags["environment"]}`
- write in JSON or yaml
- state file `terraform.tfstate` - keep it secure and encrypted.


## B. project structure 
  - `terraform { ... required_version="", required_provider={} }`
  - can make declaration anywhere. But its good practice to have seperate files.
  - keep them in same-folder (`root-module`)
  - every Terraform configuration is part of a module
  ```
    - root module:
        - main.tf
        - backend.tf - org, `TF_CLOUD_ORGANIZATION`=org1
        - variable.tf
        - provider.tf
        - dev.qa,prod.`tfvars`
        - s3-resource.tf, sqs-resource.tf, etc
        - output.tf
        - /directory-1/
                child-module-1/
                ├── main.tf
                ├── variables.tf
                ├── outputs.tf
        - /directory-2/child-module-2.tf
        
        ** root-module can use other module's config file
  ```
---
## C. Language constructs
### 1.  provider
- **aws** : https://registry.terraform.io/providers/hashicorp/aws/latest
- providerName_resourceType --> **aws_**`key_pair` , **aws_**`security_group`
  
### 2. variable  (name,32 char max)
- `types`  
  - number, string, bool
  - list(T), map(T) - key is string  type and value is of T type.
  - tuple, object({k1 = string <newline> k2 = number  })
  - any
- check : variable.tf
- assign value : 
  - via `tfvars` file --> check values-dev, values-prod.tfvars
  - via `env variables` prefixed with **TF_VAR_<variablename>**
  - default values.
- use variable : **var**.<<variableName>>
- replace default value:   `-var`, `-var-file`
  - **terraform apply -var var1=val1**
  - **terraform apply -var-file var1=val1.tfvar**
- `validation` { condition=, error_message=}
- **sensitive = true** : mandatory
  - apply will prompt to enter values 
  - or can provide via `.tfvars` file too.
    
### 3. output
- `terraform output` -> query : output1, json, etc.
- `terraform output output-1` --> view a specific output
- after terraform apply, output will get printed on console.
- sensitive = true --> will not be printed on logs. : will not be printed on logs.

### 4. locals
- locals { instance_count = var.environment == "production" ? 5 : 1 }
- name to complex expressions or repeated values
- making your configuration easier to read and maintain.
- sensitive = true : will not be printed on logs.
- usage : **local**.<<variableName>>

### 5. resource
- `attribute` : ( optional, mandatory)
  - argument - property we pass. eg `ami`
  - attribute - property resource has, once created. eg: `id`.
- `dynamic attribute`. eg  :`tags`
  ```
  dynamic "tags" {
    for_each = <collection>
    content {
     # use each.value
     # "${count.index}" 
    }
  }
  
  result :: tags = [ content-0, content-1, etc ]
  ```
- `meta-attribute` eg : `count` in resource
  - Manage `similar resources` with count.
  - `replicates` the given resource with given count.
  ``` 
        eg: resource "aws_instance" "app" { count = 4 }
          - length(aws_instance.app) : 4
          - aws_instance.app : list of all instances.
          - aws_instance.app.*.id : list of ids
          - aws_instance.app[0] : first instance tr provisioned.
          - aws_instance.app[count.index] : current index, useful while iterate.
  ```
- **Resource Lifecycle** 
```
lifecycle {
    create_before_destroy = true
    prevent_destroy = true #for critical resource
    ignore_changes = [ tags]
    ...
  }
```
- **Resource dependencies**
  - terraform graph
  - `Implicit`, eg: ec2 > ingress , automatically infer by attribute.
  - `Explicit` : certain scenario, need to tell explicitly using `deponds_on`
    - eg : depends_on = [aws_s3_bucket.r1, aws_instance.r1]
    

### 6. variable set
- use variable across workspace/s.  

### 7. Functions
- check complete list : https://developer.hashicorp.com/terraform/language/functions
- **numeric** -> abs(-12.3),ceil/floor(5.2), log(number, base), parseint("100", 2/10/16),  pow(3, 2)
- **string** -> format,start|endswith, join("-", ["foo", "bar", "baz"]), regex(pattern, string), replace, split, substr, upper/lower, etc
- **collection**
  - `lookup`(map,key) - like map1.get(k1) in java.
  - `key`(var.projects)
  - `sort`(key(var.projects))
  - `value`(var.projects)
  - `slice`(var.private_subnet_cidr_blocks, 0, 2)
  - merge()
  - count()
  - length()
- **file system**
  - `templatefile`(tftpl-file-1, map)
  - `file`(file-1)
- **Date time**
- **IP netwpork**
- `jsonencode`(), `try`(expression1, expression2, ..., fallback)


### 8. Terraform template
- `.tftpl` files
- used as templates for generating configuration-files / other-text-files.
- `dynamically generate` files by substituting variables and expressions within the template.
- eg: 
    ```
    # user_date.tftpl >> shell script text file having lots of placeholders- ${placeholder-1}, etc
    user_data= `templatefile`("user_data.tftpl", { placeholder-1 = var.value1, placeholder-2 = var.value2 })
    ```
### 9. expressions
- **ternary operation**
- **count criteria**
  - associate_public_ip_address = (`count.index` == 0 ? true : false)

## 10. for-each
- for-each = var.projects, projects is **map(object)**  [p1:{}, p2:{}]
  - `each.key` and `each.value`
- for-each = var.projects, projects is **list/set(object)**  [0:{}, 1:{}]
  - `each.key` === index,  `each.value` === item
- eg:
  - value = { for p in sort(keys(var.project)) : p => module.elb_http[p].elb_dns_name }
  - for_each = { for i, instance in var.allowed_ports : i => instance }
- use case: with resource, dynamic attribute in resource, module, output, etc
- **fact** : `mutliple for_each` on resource:
  - cannot use two for_each expressions directly at the same level within a single resource block.  <<<
  - However, can have multiple dynamic blocks, each with its own for_each. eg:
    - resource "aws_instance" "example" {
    -    for_each
    -    dynamic "tag" { for_each ... }
    -    dynamic "ingress" { for_each ... }
    - }
    
### 11. data source
- makes configuration more **dynamic**
  - query (`read only`) **external information or resources** that are not managed by your Terraform configuration.
  - dynamically fetch data from 
    - **APIs** eg:
      - query information about your `VPC`, subnet, sg
      - query `machine image IDs` from a cloud provider
    - other Terraform **state backends**
    - Terraform outputs from **other configurations**.
- eg: `aws_ami`, `aws_vpc`, `aws_security_group`
```
data "aws_vpc" "main" {
  filter {
    name   = "tag:Name"
    values = ["main-vpc"]
  }
}

resource "aws_subnet" "example" {
  vpc_id            = data.aws_vpc.main.id  # Reference the VPC ID
  cidr_block        = "10.0.1.0/24"
  availability_zone = "us-west-2a"
}
```
- inline datasource. eg:
```
data "aws_iam_policy_document" "example" {
  statement { ... }
  statement { ... }
}
```
- `data "aws_caller_identity" "current" {} `
  - retrieve details about the AWS account and credentials currently being used to execute the Terraform code
  - returns : account_id, arn, user_id
---

## Z. More
```
client-webapp = {
      public_subnets_per_vpc  = 2,
      private_subnets_per_vpc = 2,
      instances_per_subnet    = 2,
      instance_type           = "t2.micro",
      environment             = "dev"
    },
    internal-webapp = {
      public_subnets_per_vpc  = 1,
      private_subnets_per_vpc = 1,
      instances_per_subnet    = 2,
      instance_type           = "t2.nano",
      environment             = "test"
    }
---
project-root/
├── config1/
│   ├── main.tf
│   ├── variables.tf
│   ├── outputs.tf
├── config2/
│   ├── main.tf
│   ├── variables.tf
|   ├── outputs.tf

- Each configuration (in config1/ and config2/) is independent of the other. 
- You need to run Terraform commands separately for each configuration.
- Each configuration will have its own state file.
- Resources defined in one configuration are not aware of resources defined in another configuration
---
ssh-keygen -C "your_email@example.com" -f ssh_key

resource "aws_key_pair" "ssh_key" {
  key_name = "ssh_key"
  public_key = file("ssh_key.pub")
}


```

