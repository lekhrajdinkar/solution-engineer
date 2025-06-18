/*data "aws_subnets" "subnets" {
  filter {
    name   = "vpc-id"
    values = [var.aws_vpc_id]
  }
}*/

################################
# Interface VPCE - SQS
################################
resource "aws_vpc_endpoint" "sqs_vpce" {
  service_name = "com.amazonaws.${var.aws_primary_region}.sqs"
  vpc_id       = var.aws_vpc_id
  vpc_endpoint_type = "Interface"
  tags = var.tags
  private_dns_enabled = false
  security_group_ids = [ aws_security_group.sg.id ]

  # subnet_ids = data.aws_subnets.subnets.ids
  subnet_ids = var.private_subnet_ids
}

/*
DNS names :
vpce-0507c4fdf02b98fc3-tcgpq5r7.sqs.us-west-2.vpce.amazonaws.com
vpce-0507c4fdf02b98fc3-tcgpq5r7-us-west-2b.sqs.us-west-2.vpce.amazonaws.com
vpce-0507c4fdf02b98fc3-tcgpq5r7-us-west-2a.sqs.us-west-2.vpce.amazonaws.com
*/

################################
# Interface VPCE - SNS
################################
resource "aws_vpc_endpoint" "sns_vpce" {
  service_name = "com.amazonaws.${var.aws_primary_region}.sns"
  vpc_id       = var.aws_vpc_id
  vpc_endpoint_type = "Interface"
  tags = var.tags
  private_dns_enabled = false
  security_group_ids = [ aws_security_group.sg.id ]

  # subnet_ids = data.aws_subnets.subnets.ids
  subnet_ids = var.private_subnet_ids
}

## common SG
resource "aws_security_group" "sg" {
  name        = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-vpce-sg"
  description = "Security group for vpce"
  vpc_id      = var.aws_vpc_id

  dynamic "ingress" {
    for_each = var.sg_ingress_object
    content {
      from_port   = ingress.value.port
      to_port     = ingress.value.port
      protocol    = ingress.value.protocol
      cidr_blocks = ingress.value.cidr_blocks
    }
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

################################
# Gateway VPCE - S3
################################
#  Enabling private DNS requires both enableDnsSupport and enableDnsHostnames VPC attributes set to true for vpc
resource "aws_vpc_endpoint" "s3_vpce" {
  service_name        = "com.amazonaws.${var.aws_primary_region}.s3"
  vpc_id              = var.aws_vpc_id
  vpc_endpoint_type   = "Gateway"
  tags                = var.tags
  private_dns_enabled = false

  #security_group_ids  = [aws_security_group.sg.id]

  route_table_ids = [var.private_subnet_route_table_id, var.public_subnet_route_table_id]
}

