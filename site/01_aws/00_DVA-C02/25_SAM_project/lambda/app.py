import os
import boto3
import json
from botocore.exceptions import ClientError

# Initialize DynamoDB client
dynamodb = boto3.resource('dynamodb')
table_name = os.getenv('TABLE_NAME')
table = dynamodb.Table(table_name)

def lambda_handler(event, context):
    try:
        # Insert an item into the table
        table.put_item(
            Item={
                'id': '123',
                'message': 'Hello, DynamoDB!'
            }
        )

        # Retrieve the item
        response = table.get_item(Key={'id': '123'})
        item = response.get('Item', {})

        return {
            "statusCode": 200,
            "body": json.dumps({
                "message": "Successfully accessed DynamoDB!",
                "item": item
            })
        }

    except ClientError as e:
        return {
            "statusCode": 500,
            "body": json.dumps({
                "error": str(e)
            })
        }

