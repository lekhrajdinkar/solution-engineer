resource "aws_cloudwatch_event_bus" "custom_bus" {
  name = "custom-event-bus"
}

resource "aws_cloudwatch_event_rule" "example_rule" {
  name        = "example-event-rule"
  event_bus_name = aws_cloudwatch_event_bus.custom_bus.name
  event_pattern = jsonencode({
    "source" = ["aws.s3"]
  })
}
