## AWS AppFlow

- securely transfer data between SaaS applications (like Salesforce, SAP, Slack) and AWS services (S3, Redshift, etc.) without writing custom code.
- **Schedule or trigger** data flows without manual intervention.
- **Process** and **transform** data  using :
    - Lambda
    - custom mappings

### use case
- **SaaS to AWS** 
  – Sync data from Salesforce, Google Analytics, Zendesk, etc., to S3, Redshift, or EventBridge.
  
- **AWS to SaaS** 
  – Push data from S3 or DynamoDB to applications like Salesforce.

- When NOT :x: to Use :
    - **For large-scale ETL pipelines** → Use AWS Glue or Step Functions.
    - For **real-time streaming** → Use Kinesis or EventBridge instead.