# lambda function - handler(event,context)
## A. context
-  metadata about the Lambda function execution environment.
```
{
  "function_name": "MyLambdaFunction",
  "function_version": "$LATEST",
  "invoked_function_arn": "arn:aws:lambda:us-east-1:123456789012:function:MyLambdaFunction",
  "memory_limit_in_mb": 128,
  "aws_request_id": "1234-5678-9012",
  "log_group_name": "/aws/lambda/MyLambdaFunction",
  "log_stream_name": "2024/12/13/[$LATEST]abcdef1234567890",
  "identity": null,
  "client_context": null
}

```

## B. event
- contains the input data for the Lambda function.
### 1. API Gateway 
```
{
  "resource": "/{proxy+}",
  "path": "/my/path",
  "httpMethod": "GET",
  "headers": {
    "Accept": "application/json",
    "Authorization": "Bearer abc123",
    "Host": "example.com"
  },
  "queryStringParameters": {
    "param1": "value1",
    "param2": "value2"
  },
  "pathParameters": {
    "proxy": "my/path"
  },
  "stageVariables": {
    "stageVarName": "stageVarValue"
  },
  "body": "{ \"key\": \"value\" }",
  "isBase64Encoded": false
}

```

### 2. ALB
```
{
  "requestContext": {
    "elb": {
      "targetGroupArn": "arn:aws:elasticloadbalancing:us-east-1:123456789012:targetgroup/my-target-group/6d0ecf831eec9f09"
    }
  },
  "httpMethod": "GET",
  "path": "/my/path",
  "queryStringParameters": {
    "param1": "value1",
    "param2": "value2"
  },
  "headers": {
    "accept": "text/html,application/xhtml+xml",
    "accept-language": "en-US,en;q=0.9",
    "host": "my-load-balancer-1234567890.us-east-1.elb.amazonaws.com",
    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36",
    "x-forwarded-for": "203.0.113.1",
    "x-forwarded-port": "443",
    "x-forwarded-proto": "https"
  },
  "body": null,
  "isBase64Encoded": false
}

```

### 3. S3 event notification
```
{
  "Records": [
    {
      "eventVersion": "2.1",
      "eventSource": "aws:s3",
      "awsRegion": "us-east-1",
      "eventTime": "2024-12-13T12:00:00.000Z",
      "eventName": "ObjectCreated:Put",
      "s3": {
        "bucket": {
          "name": "my-bucket",
          "arn": "arn:aws:s3:::my-bucket"
        },
        "object": {
          "key": "my-folder/my-object.txt",
          "size": 12345,
          "eTag": "123456789abcdef",
          "sequencer": "00123456789abcdef"
        }
      }
    }
  ]
}

```

### 4. SQS
```
{
  "Records": [
    {
      "messageId": "1",
      "receiptHandle": "AQEB...",
      "body": "{\"key\": \"value\"}",
      "attributes": {
        "ApproximateReceiveCount": "1",
        "SentTimestamp": "1609459200000",
        "SenderId": "123456789012",
        "ApproximateFirstReceiveTimestamp": "1609459201000"
      },
      "messageAttributes": {},
      "md5OfBody": "e99a18c428cb38d5f260853678922e03",
      "eventSource": "aws:sqs",
      "eventSourceARN": "arn:aws:sqs:us-east-1:123456789012:MyQueue",
      "awsRegion": "us-east-1"
    }
  ]
}

```
### 5. Dynamo
```
{
  "Records": [
    {
      "eventID": "1",
      "eventName": "INSERT",
      "eventVersion": "1.1",
      "eventSource": "aws:dynamodb",
      "awsRegion": "us-east-1",
      "dynamodb": {
        "Keys": {
          "id": { "S": "123" }
        },
        "NewImage": {
          "id": { "S": "123" },
          "name": { "S": "John Doe" },
          "age": { "N": "30" }
        },
        "SequenceNumber": "111",
        "SizeBytes": 54
      }
    }
  ]
}
```

![img.png](../99_img/dva/l/img.png)