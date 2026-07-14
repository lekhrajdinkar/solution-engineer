## intro
- HashiCorp Configuration Language
- used write **configuration** to create infra.
- **String-interpolation** 
    - `web-sg-${var.resource_tags["project"]}-${var.resource_tags["environment"]}`
- write in JSON or yaml
- **state file** `terraform.tfstate` - keep it secure and encrypted. check on HCP.


## project-structure 
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
## Language constructs
### 🔸provider
- [aws](https://registry.terraform.io/providers/hashicorp/aws/latest)
- format :: providerName_resourceType --> eg: **aws_**`key_pair` , **aws_**`security_group`

### Related Paths
| Variable      | Description                                  |
| ------------- | -------------------------------------------- |
| `path.module` | Path to the **current module**               |
| `path.root`   | Path to the **root Terraform configuration** |
| `path.cwd`    | Path to the **current working directory**    |

### 🔸variable set
- use variable across workspace/s.

### 🔸variable  
- Max length: 32 characters
- Use snake_case for readability

  | Type              | Example                                   | Notes                                           |
  | ----------------- | ----------------------------------------- | ----------------------------------------------- |
  | `string`          | `"dev"`                                   |                                                 |
  | `number`          | `42`                                      | Integer or float                                |
  | `bool`            | `true`                                    |                                                 |
  | `list(T)`         | `list(string)` → `["a", "b"]`             | Homogeneous list                                |
  | `map(T)`          | `map(number)` → `{ "a" = 1, "b" = 2 }`    | Keys must be strings                            |
  | `tuple([T1, T2])` | `[string, number]`                        | Heterogeneous list                              |
  | `object({...})`   | `object({ id = string, count = number })` | Struct-like                                     |
  | `any`             | Accepts any type                          | Least strict, not recommended for strict typing |

- **Declare**
```terraform
variable "instance_type" {
  type        = string
  description = "Type of EC2 instance"
  default     = "t3.micro"
  sensitive = true # Will not be shown in terraform plan
}

# ✅validation
variable "port" {
  type = number
  validation {
    condition     = var.port >= 1024 && var.port <= 65535
    error_message = "Port must be between 1024 and 65535"
  }
}

# value = var.instance_type
```
- **Assigning value**

  | Method                 | Command/Usage                            |
  | ---------------------- | ---------------------------------------- |
  | `.tfvars` file         | `terraform apply -var-file="dev.tfvars"` |
  | Inline CLI             | `terraform apply -var="env=dev"`         |
  | Environment variable   | `export TF_VAR_env=dev`                  |
  | Default in declaration | `default = "dev"`                        |

    
### 🔸output
- **terraform output** -> prints all output var | can query : output1, json, etc.
- **terraform output output-1** --> view a specific output
- after terraform apply, output will get printed on console.
- **sensitive = true** --> will not be printed on logs. : will not be printed on logs.

### 🔸locals
- locals { instance_count = var.environment == "production" ? 5 : 1 }
- name to complex expressions or repeated values
- making your configuration easier to read and maintain.
- sensitive = true : will not be printed on logs.
- usage : **local**.variable_Name ⬅️

### 🔸resource
- `attribute` : ( optional, mandatory)
  - argument - property we pass. eg `ami`
  - attribute - property resource has, once created. eg: `id`.
- `dynamic attribute`. eg  :`tags`

```terraform
  dynamic "tags" {
    for_each = <collection>
    content {
     # use each.value
     # "${count.index}" 
    }
  }
  
  # result :: tags = [ content-0, content-1, etc ]
```

### 🔸meta-attribute 
- eg : `count` in resource
- Manage `similar resources` with count.
- `replicates` the given resource with given count.

```terraform
       resource "aws_instance" "app" { 
          count = 4 
          #...
       }
        
          #- length(aws_instance.app) : 4
          #- aws_instance.app : list of all instances.
          #- aws_instance.app.*.id : list of ids
          #- aws_instance.app[0] : first instance tr provisioned.
          #- aws_instance.app[count.index] : current index, useful while iterate.
```

### 🔸Resource Lifecycle
- eg:
```terraform
lifecycle {
    create_before_destroy = true
    prevent_destroy = true #for critical resource
    ignore_changes = [ tags]
    #...
  }
```
### 🔸Resource dependencies
- **terraform graph**
- `Implicit`, eg: ec2 > ingress , automatically infer by attribute.
- `Explicit` : certain scenario, need to tell explicitly using `deponds_on`
    - **depends_on** = [aws_s3_bucket.r1, aws_instance.r1]
 
---
### 🔸built-functions
- [check complete list](https://developer.hashicorp.com/terraform/language/functions)

| Function     | Description                       | Example              | Output |
| ------------ | --------------------------------- | -------------------- | ------ |
| `abs()`      | Absolute value                    | `abs(-12.3)`         | `12.3` |
| `ceil()`     | Round up to next integer          | `ceil(5.2)`          | `6`    |
| `floor()`    | Round down to previous integer    | `floor(5.8)`         | `5`    |
| `log()`      | Logarithm with base               | `log(100, 10)`       | `2`    |
| `parseint()` | Parse string to integer with base | `parseint("100", 2)` | `4`    |
| `pow()`      | Power function                    | `pow(3, 2)`          | `9`    |

| Function       | Description              | Example                            | Output            |
| -------------- | ------------------------ | ---------------------------------- | ----------------- |
| `format()`     | Format string            | `format("Hello %s", "World")`      | `"Hello World"`   |
| `startswith()` | Checks prefix            | `startswith("terraform", "terra")` | `true`            |
| `endswith()`   | Checks suffix            | `endswith("main.tf", ".tf")`       | `true`            |
| `join()`       | Join list with delimiter | `join("-", ["foo", "bar", "baz"])` | `"foo-bar-baz"`   |
| `regex()`      | Match regex              | `regex("[0-9]+", "abc123")`        | `"123"`           |
| `replace()`    | Replace substrings       | `replace("hello", "l", "X")`       | `"heXXo"`         |
| `split()`      | Split string into list   | `split(",", "a,b,c")`              | `["a", "b", "c"]` |
| `substr()`     | Substring extraction     | `substr("hello", 1, 3)`            | `"ell"`           |
| `upper()`      | Uppercase                | `upper("terraform")`               | `"TERRAFORM"`     |
| `lower()`      | Lowercase                | `lower("TERRAFORM")`               | `"terraform"`     |

| Function     | Description                  | Example                               | Output                      |
| ------------ | ---------------------------- | ------------------------------------- | --------------------------- |
| `lookup()`   | Get value from map           | `lookup(var.map1, "key1", "default")` | `"value1"` (or `"default"`) |
| `keys()`     | Get all map keys             | `keys(var.projects)`                  | `["proj1", "proj2"]`        |
| `values()`   | Get all map values           | `values(var.projects)`                | `["app", "db"]`             |
| `sort()`     | Sort a list                  | `sort(["z", "a", "b"])`               | `["a", "b", "z"]`           |
| `slice()`    | Slice a list                 | `slice(["a","b","c"], 0, 2)`          | `["a", "b"]`                |
| `merge()`    | Merge maps                   | `merge({a=1}, {b=2})`                 | `{a=1, b=2}`                |
| `length()`   | Length of list or map        | `length(["a", "b"])`                  | `2`                         |
| `contains()` | Check if list contains value | `contains(["a", "b"], "b")`           | `true`                      |
| `element()`  | Get element at index         | `element(["a", "b", "c"], 1)`         | `"b"`                       |

| Function         | Description               | Example                                          | Output (example)   |
| ---------------- | ------------------------- | ------------------------------------------------ | ------------------ |
| `templatefile()` | Render template with vars | `templatefile("main.tftpl", { name = "World" })` | `"Hello World"`    |
| `file()`         | Read file contents        | `file("mydata.txt")`                             | (contents of file) |


| Function       | Description               | Example                           | Output                   |
| -------------- | ------------------------- | --------------------------------- | ------------------------ |
| `timestamp()`  | Current UTC timestamp     | `timestamp()`                     | `"2025-07-03T18:00:00Z"` |
| `timeadd()`    | Add duration to timestamp | `timeadd(timestamp(), "1h")`      | `"2025-07-03T19:00:00Z"` |
| `formatdate()` | Format date               | `formatdate("YYYY", timestamp())` | `"2025"`                 |

| Function        | Description          | Example                           | Output            |
| --------------- | -------------------- | --------------------------------- | ----------------- |
| `cidrhost()`    | Get IP in CIDR block | `cidrhost("192.168.0.0/24", 5)`   | `"192.168.0.5"`   |
| `cidrsubnet()`  | Subdivide CIDR block | `cidrsubnet("10.0.0.0/16", 8, 1)` | `"10.0.1.0/24"`   |
| `cidrnetmask()` | Get netmask of CIDR  | `cidrnetmask("10.0.0.0/24")`      | `"255.255.255.0"` |

| Function       | Description                  | Example                               | Output                     |
| -------------- | ---------------------------- | ------------------------------------- | -------------------------- |
| `jsonencode()` | Convert value to JSON string | `jsonencode({ name = "John" })`       | `'{"name":"John"}'`        |
| `try()`        | Return first valid result    | `try(var.opt1, var.opt2, "fallback")` | `var.opt1` or `"fallback"` |


### 🔸Terraform template
- `.tftpl` files
- **templatefile** function
- used as templates for generating configuration-files / other-text-files.
- dynamically generate files **by substituting** variables and expressions within the template.
- used in writing ia policies
```terraform
    user_data = templatefile(
      "user_data.tftpl", 
      { 
        placeholder-1 = var.value1, 
        placeholder-2 = var.value2 
      }
    )
```

### 🔸expressions
- **ternary operation**
- **count criteria**
  - associate_public_ip_address = (`count.index` == 0 ? true : false)

### 🔸for-each
| Type           | Example                 | `each.key`                | `each.value`    |
| -------------- | ----------------------- | ------------------------- | --------------- |
| `map(object)`  | `{ p1 = {}, p2 = {} }`  | `"p1"`, `"p2"`            | object value    |
| `list(object)` | `[{}, {}]`              | `0`, `1`                  | object at index |
| `set(object)`  | `toset([{a=1}, {a=2}])` | unpredictable order index | object item     |

####    for-each :: usages
<details> <summary> expand </summary>

```terraform
# ✅ Use in output
output "elb_dns_names" {
  value = { for p in sort(keys(var.project)) : p => module.elb_http[p].elb_dns_name  }
}

# ✅ Use in resource
resource "aws_security_group_rule" "allow_ports" {
  for_each = { for i, port in var.allowed_ports : i => port }

  type        = "ingress"
  from_port   = each.value
  to_port     = each.value
  protocol    = "tcp"
  cidr_blocks = ["0.0.0.0/0"]
}

# ✅ Use in module
module "project_module" {
  for_each = var.projects
  source   = "./modules/project"
  name     = each.key
  config   = each.value
}

# Multiple for_each in Same Resource 🚫 Not Allowed:
resource "aws_instance" "invalid_example" {
  for_each = var.servers_1
  for_each = var.servers_2   # ❌ Error: duplicate for_each at same level
}

# ✅ Multiple for_each - Allowed via dynamic blocks:
resource "aws_instance" "example" {
  ami           = "ami-123456"
  instance_type = "t2.micro"

  dynamic "tag" {
    for_each = var.tags  #1
    content {
      key   = tag.key
      value = tag.value
    }
  }

  dynamic "ebs_block_device" {
    for_each = var.ebs_blocks  #2
    content {
      device_name = each.value.device_name
      volume_size = each.value.volume_size
    }
  }
}
```
</details>
    
### 🔸data source
- Read-only construct to fetch external information dynamically. 
- Makes configuration dynamic and environment-aware.
- Does NOT create or manage infrastructure.

| Use Case                             | Example                                        |
| ------------------------------------ | ---------------------------------------------- |
| Query cloud resources by tag or name | Fetch `VPC`, `AMI`, `Security Group`, etc.     |
| Retrieve caller/account identity     | Get current AWS account ID, ARN, user ID       |
| Pull remote state outputs            | Reference outputs from other Terraform configs |
| Inline policy or config generation   | Use in IAM policy docs, bucket policies, etc.  |

#### Example-1
```terraform
# ✅ Example: AWS Caller Identity
data "aws_caller_identity" "current" {}
output "account_id" {
  value = data.aws_caller_identity.current.account_id
}

# ✅ Example: Lookup AWS VPC
data "aws_vpc" "main" {
  filter {
    name   = "tag:Name"
    values = ["main-vpc"]
  }
}

resource "aws_subnet" "example" {
  vpc_id            = data.aws_vpc.main.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "us-west-2a"
}

# ✅ Example: Inline Data Source (IAM Policy)
data "aws_iam_policy_document" "example" {
  statement {
    actions   = ["s3:GetObject"]
    resources = ["arn:aws:s3:::mybucket/*"]
    principals {
      type        = "AWS"
      identifiers = ["*"]
    }
  }
}

```

#### Example-2 : http, external 👈🏻

| Type               | Examples                                         |
| ------------------ |--------------------------------------------------|
| **Cloud Provider** | `aws_ami`, `aws_vpc`, `google_compute_instance`  |
| **IAM & Security** | `aws_iam_policy_document`, `aws_caller_identity` |
| **Remote State**   | `terraform_remote_state`                         |
| **Utility**        | `external`, `http`  👈🏻👈🏻👈🏻👈🏻             |

```terraform
# ✅ external datasource
# Example: Call a Python script
# ${path.module} absolute path of the current module's directory.
data "external" "tags" {
  program = ["python3", "${path.module}/get_tags.py"]
}

# ✅ http Data Source
data "http" "my_ip" {
  url = "https://api.ipify.org?format=json"
}
output "my_public_ip" {
  value = jsondecode(data.http.my_ip.response_body).ip
}

```
