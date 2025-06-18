# AWS Lambda Exam Questions

## Multiple-Choice Questions

1. **Which of the following statements about AWS Lambda is true?**
    - A) You must provision EC2 instances to run Lambda functions.
    - B) AWS Lambda scales automatically based on the number of events triggered.
    - C) You are charged for Lambda functions even when they are not in use.
    - D) Lambda functions can only be triggered by S3 events.

2. **What is the maximum execution time for an AWS Lambda function?**
    - A) 5 minutes
    - B) 10 minutes
    - C) 15 minutes
    - D) Unlimited

3. **Which of the following services can trigger an AWS Lambda function? (Select TWO)**
    - A) Amazon S3
    - B) AWS CloudFormation
    - C) Amazon DynamoDB Streams
    - D) Amazon RDS

4. **Which AWS service is best suited to monitor and log AWS Lambda function executions?**
    - A) Amazon CloudFront
    - B) Amazon CloudWatch
    - C) AWS Config
    - D) AWS Inspector

5. **How does AWS Lambda handle scaling?**
    - A) It creates more EC2 instances based on the load.
    - B) It automatically provisions additional Lambda functions as demand increases.
    - C) It requires manual intervention to scale.
    - D) It uses Elastic Load Balancers to distribute requests.

---

## Scenario-Based Questions

6. **A company wants to process uploaded images in an Amazon S3 bucket automatically. The images must be resized and saved back to another S3 bucket. Which combination of AWS services should be used?**
    - A) Amazon EC2, Amazon S3, and AWS CLI
    - B) Amazon S3, AWS Lambda, and Amazon CloudWatch
    - C) AWS Lambda, Amazon DynamoDB, and Amazon RDS
    - D) Amazon S3, Amazon Kinesis, and AWS Lambda

7. **You are designing a serverless web application. The application must invoke a backend service using AWS Lambda when API requests are received. Which service would you use to expose the API to clients?**
    - A) Amazon SQS
    - B) Amazon API Gateway
    - C) Amazon SNS
    - D) AWS CloudFormation

8. **A Lambda function has intermittent high latencies when processing requests. What should you check to optimize its performance? (Select TWO)**
    - A) Ensure the function is stateless.
    - B) Increase the memory allocation for the Lambda function.
    - C) Use AWS Step Functions to divide tasks.
    - D) Reduce the number of concurrent executions.

9. **Which AWS Lambda feature allows you to set the amount of memory allocated to a function and automatically allocates proportional CPU power?**
    - A) Memory Tiers
    - B) Execution Tiers
    - C) Provisioned Concurrency
    - D) Memory Allocation Setting

10. **Which of the following best describes AWS Lambda’s pricing model?**
    - A) Fixed monthly fee for unlimited execution
    - B) Pay-per-execution and duration of execution
    - C) Based on the number of EC2 instances provisioned
    - D) Based on storage used in Amazon S3


---

## True/False Questions

11. AWS Lambda requires you to manually manage server scaling.  
    **True/False**

12. You can set environment variables in AWS Lambda to store sensitive information securely.  
    **True/False**

13. Lambda functions are always stateful and maintain context between invocations.  
    **True/False**

---

## Correct Answers

### Multiple-Choice Questions
1. B
2. C
3. A, C
4. B
5. B

### Scenario-Based Questions
6. B
7. B
8. A, B
9. D
10. B

### True/False Questions
11. False
12. True
13. False

---

```
A company uses AWS Lambda for processing data streams from Amazon Kinesis. 
Occasionally, processing times exceed the maximum Lambda execution duration. 
What should the company do to handle this?

A) Increase Lambda memory allocation.
B) Use AWS Step Functions to orchestrate workflows.
C) Split the stream into smaller shards.
D) Enable Provisioned Concurrency for Lambda.
(Answer: B)
---
You need to design a solution where AWS Lambda must securely connect to an 
Amazon RDS instance without hardcoding credentials. What should you use?

A) Store credentials in Lambda environment variables.
B) Use AWS Secrets Manager to retrieve database credentials at runtime.
C) Embed credentials in the Lambda function code.
D) Attach an IAM role to Lambda with database access policies.
(Answer: B or D, depending on the detailed context.)
---
A Lambda function invoked through Amazon API Gateway is experiencing 
high latencies due to cold starts. How can you minimize these latencies?

A) Increase the timeout limit for API Gateway.
B) Use AWS X-Ray to trace cold starts.
C) Enable Provisioned Concurrency for the Lambda function. **
D) Increase the function’s memory allocation.

C Provisioned Concurrency ensures that a specific number of execution environments are pre-warmed and ready to process requests.
```
