## ✔️learning / skills
### 1 innovation solution
- Solved problem with innovation

### 2 Complex problem

| **STAR Element** | **Response**                                                                                                                                                                                                                                                                                                                                                           |
| ---------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| ✔️ **Situation** | In our production environment, SNS alerts were intermittently failing, and business users were not receiving critical email notifications.                                                                                                                                                                                                                             |
| ✔️ **Task**      | I needed to identify the root cause and implement a reliable, long-term solution to ensure alert delivery regardless of network issues.                                                                                                                                                                                                                                |
| ✔️ **Action**    | Through investigation, I found the failures occurred during firewall updates that blocked outbound internet traffic. Since SNS used public endpoints, the service was unreachable. I resolved this by configuring a **VPC endpoint for SNS**, enabling private communication via AWS’s internal network. I also tested the solution under simulated outage conditions. |
| ✔️ **Result**    | After the change, SNS alerts became consistently reliable, even during firewall updates. This restored business confidence, improved system reliability, and reduced incident escalations.                                                                                                                                                                             |

```
more
- sns alerts, failed multiple time : created 🔸vpce
- message beyond 256 KB, 🔸S3 extended
- outbound enable/disable based on bucket, flexible schema design with RDBMS and then to dynamoDB noSQL
    - complexity incresed > rebalance, allocations, hasetf, etc more attribures
    - multiple vale for  same key > resolve by weight 
- token refresh lambda
- OutOfMemory : created seperate cache server, data grows, used enums
- batch job min : DB call optimize + enums
- CIAM simulator and dashboard
- FSR : 15 min password expire 👈🏻
```

### 3 learn new
| **Step**                                      | **Approach**                                                                                                                                          |
| --------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| ✔️ **1. Depends on type of technology**       | My approach varies based on whether it's a new language, framework, or concept.                                                                       |
| ✔️ **2. Learning a new programming language** | - Compare it with known languages (Java, Python, TypeScript)<br>- Understand syntax, typing, memory model<br>- Analyze how it handles common patterns |
| ✔️ **3. Learning a new framework/library**    | - Identify the problem it solves<br>- Understand the abstraction it provides<br>- Explore what it hides under the hood                                |
| ✔️ **4. Learning strategy**                   | - Follow a structured and focused path<br>- Avoid information overload<br>- Choose 1–2 quality resources (blogs, videos, docs) and stick to them      |
| ✔️ **5. Practice and Reinforcement**          | - Build small projects or POCs<br>- Learn by doing<br>- Set aside time regularly to go deep<br>- Reinforce by applying and practicing consistently    |


### 4 project most proud-of
- what took you achieve that accomplishment 👈🏻
- not the accomplishment

| **STAR Element** | **Answer**                                                                                                                                                                                                                                                                                                                                                                |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **✔️ Situation** | We worked on modernizing a legacy TACT system, which was monolithic, hard to scale, tightly coupled, and had frequent performance and stability issues. It ran only on mainframes.                                                                                                                                                                                        |
| **✔️ Task**      | I was responsible for building the **interface module**, which had:<br>🔸 **Inbound module** — data pipeline to process small CSV files from S3 (used PySpark)<br>🔸 **Outbound module** — event-driven microservices using SQS, EventBridge, SNS, Lambda, Java/Python APIs, and RDBMS schema design                                                                      |
| **✔️ Action**    | - Developed **stateless REST APIs** using Java/Python + Spring Boot<br>- Built **event-driven architecture** using SQS, EventBridge, and Lambda<br>- Implemented **distributed tracing** using OpenTelemetry and X-Ray<br>- Used **Aurora Serverless** with read replicas for performance<br>- Set up **DR strategy** and automated deployments using Terraform + Harness |
| **✔️ Result**    | The system became scalable, observable, and cloud-native. It improved my skills in **system design**, **AWS architecture**, and **cross-team collaboration**.                                                                                                                                                                                                             |

### 5 applied CAP theorem
- Can always be achieved in single region
- for distributed system, B2C
- so never applied but thought about scenarios, how my organization follow CAP.
- being financial org, Data consistency is important
- used Aurora serverless
- no no-SQL distributed DB, becoz of eventual consistency.

### 6.1 disagreement on system Design (yours)
- **inbound etl**
    - S3 > SQS > `poll`(py) > etl::thread
    - s3 > SQS > lambda-trigger (async + destination) > api >  exposed etl as API. === `PUSH`
- **Aurora config vs DynamoDB** :
    - needed flexibility in schema evolution
  
### 6.2 disagreement on system Design (others)

### 7.1 balance innovation with stability

### 7.2 innovation solutions
- create API for dev and QA profile.
- enable/disable , config API
- mock incoming message | mock vendor behaviour
- create API to S3 bucket view and download + UI ng
- create API for message broker
- merge TIP files + additional logic to decode row and show summarized API. API to validate it.
- For CIAM : day-1 + day-2 response | staging failure response

### 8. Technical quality vs consistency

### 9. Design phase with business 
- how you interact

### 10. evaluating build vs buy decision

