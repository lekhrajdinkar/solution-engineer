# Cloud Formation (IAC)
## A. Intro
- **declarative way** of provisioning AWS Infrastructure
- **IAC** 
  - **stack**-1 (for vpc)
    - **reource**-1 (**tags** - stackid(arn), logicalId, physicalId,etc)
    - rsource-2
    - ...
    - resolves order / dependency
    - ![img_2.png](img_2.png)
  - stack-2 (app stack)
  - ...
  - versioned in git
- **app composer** to visualize
- **cost**
  - Each resources within the stack is tagged with an identifier so you can easily see how much a stack costs you
  - estimate the costs of your resources
  - schedule to destroy and re-create, to save cost.

![img.png](img.png)

## B. Template
### 1. overview
```json5
• AWSTemplateFormatVersion  – identifies the capabilities of the template “2010-09-09”
• Description               – comments about the template
• Resources (MANDATORY)     – your AWS resources declared in the template
• Parameters                – the dynamic inputs for your template
• Mappings                  – the static variables for your template
• Outputs                   – references to what has been created
• Conditionals              – list of conditions to perform resource creation
```
### 2. change set
- change in template - add, modify (replacemnet=true/false), etc
- ![img_1.png](img_1.png)

### 99. example
```yaml
---
AWSTemplateFormatVersion: "2010-09-09"  # Identifies the capabilities of the template.

Description: "Sample CloudFormation template for deploying a web server." # Comments about the template.

Parameters:  # Dynamic inputs for your template.
  InstanceType:  # Logical name for the parameter.
    Description: "EC2 instance type"  # Description of the parameter.
    Type: "String"  # Type of the parameter (e.g., String, Number).
    Default: "t2.micro"  # Default value for the parameter.
    AllowedValues:  # Allowed values for the parameter.
      - "t2.micro"
      - "t2.small"
    ConstraintDescription: "Must be a valid EC2 instance type."

Mappings:  # Static variables for your template.
  RegionMap:
    us-east-1:
      AMI: "ami-0abcdef1234567890"  # AMI for US East (N. Virginia).
    us-west-1:
      AMI: "ami-0fedcba9876543210"  # AMI for US West (N. California).

Resources:  # Contains definitions of AWS resources to be created (MANDATORY).
  MyEC2Instance:  # Logical name for the EC2 instance.
    Type: "AWS::EC2::Instance"  # Specifies the resource type (an EC2 instance in this case).
    Properties:  # Contains properties to configure the resource.
      InstanceType: !Ref InstanceType  # Uses the parameter for instance type.
      KeyName: "MyKeyPair"  # Name of the key pair for SSH access.
      ImageId: !FindInMap [RegionMap, !Ref "AWS::Region", AMI]  # Uses mappings to select the AMI.
      SecurityGroupIds:  # Specifies security groups for the instance.
        - !Ref MySecurityGroup  # References the security group defined below.
      Tags:  # Adds metadata to the instance.
        - Key: "Name"
          Value: "WebServer"

  MySecurityGroup:  # Logical name for the security group.
    Type: "AWS::EC2::SecurityGroup"  # Specifies the resource type (a security group in this case).
    Properties:  # Contains properties to configure the security group.
      GroupDescription: "Allow HTTP and SSH access"  # Description of the security group.
      SecurityGroupIngress:  # Rules for inbound traffic.
        - IpProtocol: "tcp"  # Protocol type (TCP).
          FromPort: 22  # Allows SSH traffic.
          ToPort: 22
          CidrIp: "0.0.0.0/0"  # Allows traffic from all IP addresses.
        - IpProtocol: "tcp"
          FromPort: 80  # Allows HTTP traffic.
          ToPort: 80
          CidrIp: "0.0.0.0/0"

Conditions:  # List of conditions to perform resource creation.
  CreateProdResources: !Equals [!Ref "AWS::Region", "us-east-1"]  # Example condition based on region.

Outputs:  # References to what has been created.
  InstanceId:  # Logical name for the output.
    Description: "ID of the EC2 instance"  # Description of the output.
    Value: !Ref MyEC2Instance  # References the EC2 instance to get its ID.

  PublicIP:  # Logical name for the output.
    Description: "Public IP address of the EC2 instance"  # Description of the output.
    Value: !GetAtt MyEC2Instance.PublicIp  # Retrieves the public IP address of the EC2 instance.

```