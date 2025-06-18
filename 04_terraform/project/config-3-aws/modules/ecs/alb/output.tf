output "arn_tg_for_ecs_task" {
  value = aws_lb_target_group.ecs_target_group.arn
}

output "alb_sg_arn" {
  value = aws_lb.alb.arn
}