# lambda CLI
## 1. create, update, delete
```
aws lambda delete-function \
    --function-name <function_name>
    
aws lambda create-function \
    --function-name <function_name> \
    --runtime <runtime> \
    --role <role_arn> \
    --handler <handler_name> \
    --zip-file fileb://<path_to_deployment_package>

aws lambda list-functions
    
# get config
# ==================
aws lambda get-function-configuration \
    --function-name <function_name>
    
# update config
# ==================    
aws lambda update-function-configuration \
    --function-name <function_name> \
    --runtime <runtime> \
    --role <role_arn> \
    --handler <handler_name> \
    --memory-size <memory_in_mb> \
    --timeout <timeout_in_seconds>

# update code
# ==================
aws lambda update-function-code \
    --function-name MyLambdaFunction \
    --zip-file fileb://function.zip

# Downloads the deployment package of a Lambda function
# ======================================================
aws lambda get-function \
    --function-name <function_name>
```

## 2. invoke (sync + async)
```
#  Synchronous Invocation
# =======================
aws lambda invoke \
    --function-name <function_name> \
    --payload '<json_payload>' \
    <output_file>

# Asynchronous Invocation
# =======================
aws lambda invoke \
    --function-name MyLambdaFunction \ 
    --invocation-type Event \                          <<<
    --payload '{"key": "value"}' \
    response.json
```

## 3. Add trigger - S3 notification
```
# update lambda policy
# ==================
aws lambda add-permission \
    --function-name <function_name> \
    --action "lambda:InvokeFunction" \
    --principal s3.amazonaws.com \
    --source-arn arn:aws:s3:::<bucket_name> \
    --statement-id <unique_id>

# Add S3 triger
#==============
aws s3api put-bucket-notification-configuration \
    --bucket <bucket_name> \
    --notification-configuration file://notification.json

# notification.json    
{
  "LambdaFunctionConfigurations": [
    {
      "LambdaFunctionArn": "arn:aws:lambda:region:account-id:function:function-name",
      "Events": ["s3:ObjectCreated:*"]
    }
  ]
}
```

## 4. more
```
aws logs filter-log-events \
    --log-group-name /aws/lambda/<function_name> \
    --start-time <start_timestamp> \
    --end-time <end_timestamp>
```