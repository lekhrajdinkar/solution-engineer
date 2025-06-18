# resources
resource "aws_security_group" "sg-1" {
  name = "sg-1"
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  # dynamic Block with for_each
  dynamic "ingress" {
    # converting list into map, index as key.
    # for_each = { for i, instance in var.allowed_ports : i => instance }

    for_each = var.allowed_ports
    content {
      from_port   = ingress.value
      to_port     = ingress.value
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }
}

resource "aws_instance" "app_server" {
  # arguments
  ami           = "ami-830c94e3"
  instance_type = var.instance_type
  tags          = {
    Name = var.ec2_server_name
  }

  # meta-arguments
  count = 2
  depends_on    = [aws_security_group.sg-1]
  lifecycle {
    prevent_destroy = true
  }
  provider = "aws"
}

resource "aws_instance" "dummy_instances" {
  for_each = var.instances
  ami           = each.value.ami
  instance_type = each.value.instance_type
  # ${count.index}
  dynamic "tags" {
    for_each = var.tags
    content{
      key = tags.key
      value = tags.value
    }
  }
}





