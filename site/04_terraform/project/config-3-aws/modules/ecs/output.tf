output "alb_sg_arn" {
  value = module.alb.alb_sg_arn
}

output "ecs_srv_sg_arn" {
  value = aws_security_group.ecs_service_sg.arn
}