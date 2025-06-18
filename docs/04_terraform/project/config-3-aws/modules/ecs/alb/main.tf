data "aws_subnets" "vpc_subnets" {
  filter {
    name   = "vpc-id"
    values = [var.vpc_id] # Replace with your VPC ID
  }
}

# 1. Create a security group for the ALB
resource "aws_security_group" "alb_sg" {
  name        = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-alb-sg"
  description = "Security group for ALB"
  vpc_id      = var.vpc_id

  dynamic "ingress" {
    for_each = var.sg_ingress_object
    content {
      from_port   = ingress.value.port
      to_port     = ingress.value.port
      protocol    = ingress.value.protocol
      cidr_blocks = [ingress.value.cid_block]
    }
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# 2. Create the ALB
resource "aws_lb" "alb" {
  name               = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-alb"
  load_balancer_type = "application"
  security_groups    = [aws_security_group.alb_sg.id]
  subnets            = data.aws_subnets.vpc_subnets.ids
  enable_deletion_protection  = false
  internal                    = true
  tags = var.tags
  /*access_logs {
    bucket = ""
    prefix = ""
    enabled = false
  }*/
}

# 2.1. Create a listener for the ALB
resource "aws_lb_listener" "listener_http" {
  load_balancer_arn = aws_lb.alb.arn
  port              = 80
  protocol          = "HTTP"

  # ssl_policy = ""
  # certificate_arn = data.aws_acm_certificate.cert_arn

  default_action {
    type             = "redirect"
    redirect {
      status_code = "HTTP_301"
      port = "443"
      protocol = "HTTPS"
    }
  }
}

# 2.2. Create a listener for the ALB, with "default rule"
# resource "aws_lb_listener_rule" "c2_listener_rule" { action {}, condition {}, priority}
resource "aws_lb_listener" "listener_https" {
  load_balancer_arn = aws_lb.alb.arn
  port              = 443
  protocol          = "HTTPS"
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ecs_target_group.arn
  }
}

# 3. Create a target group for ECS tasks
# IMPORTANT - Don;t forget to register ecs service with this tg under load_balancer { target_group_arn =}
resource "aws_lb_target_group" "ecs_target_group" {
  name        = "${var.app_name}-${var.app_component}-${var.aws_primary_region}-${var.app_env}-tg"
  target_type = "ip"
  port        = var.container_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id

  health_check {
    path                = "/"
    interval            = 150
    timeout             = 30
    healthy_threshold   = 3
    unhealthy_threshold = 3
    matcher             = "200"
  }
}

/*
resource "aws_route53_record" "alb"{
  name    = var.app_domain_name
  type    = "A"
  zone_id = ""
  alias {
    evaluate_target_health = false
    name                   = aws_lb.alb.dns_name
    zone_id                = aws_lb.alb.zone_id
  }
}
*/


