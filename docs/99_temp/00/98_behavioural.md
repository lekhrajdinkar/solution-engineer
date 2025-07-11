## Links
- https://chatgpt.com/c/6850d0a3-3d8c-800d-98ce-1a4b4bd9a2ce | business 🗨️
- https://chatgpt.com/c/6850fafa-fc18-800d-9a4a-e8a9641cae8c | behaviour 1 🗨️
- [behaviour 2 - exponent : se | behaviour ](https://www.tryexponent.com/questions?page=2&role=swe&type=behavioral) 
- https://chatgpt.com/c/685115cc-eef4-800d-971d-1665c4af2684 | behaviour 3 🗨️

---

## 13. Team hard to work on
- I generally work well with most teams, but I find it difficult when working with teams that lack transparency or ownership. 
- it can create delays and confusion.
- I try to resolve it by initiating regular syncs
- and encouraging open communication.
- learned : proactively reaching out and aligning early to avoid miscommunication.

## 12. Conflict in team
| **STAR Element** | **Response**                                                                                                                                                                                                                                                                                                                                                            |
| ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **✔️ Situation** | I was working on a project to publish SWIFT messages to a downstream vendor, who would forward them to a transfer agent or customer agent.                                                                                                                                                                                                                              |
| **✔️ Task**      | We needed to define how to transfer these messages from our app to an external gateway. My colleague proposed using an **MFT (Managed File Transfer)** connection, while I suggested using a **message broker** for real-time publishing.                                                                                                                               |
| **✔️ Action**    | Instead of pushing my approach directly, I proposed we **list pros and cons** of both options (latency, security, supportability). We involved an architect for a third opinion and aligned on key non-functional requirements.<br>Eventually, we agreed on **message broker** for real-time, reliable delivery and added a **fallback file-based solution** as backup. |
| **✔️ Result**    | - The system became faster and more reliable.<br>- Both sides felt heard and respected.<br>- I learned that **conflicts can be healthy** if handled with open discussion, data, and empathy.                                                                                                                                                                            |

## 11. short term sacrifice for long term gain
- created CIAM dashboard
- removed hardcoding so many thing.
- created config table. perform datamod with doing release in prod.

## 10. Approach if two clients requesting two different features?
| **Step**                      | **Approach**                                                                                                                                                                  |
| ----------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| ✅ **Understand impact**       | - Talk to both clients to understand **business value**, **urgency**, and **use cases**<br>- Check if features are **blocking revenue**, **compliance**, or **key workflows** |
| ✅ **Evaluate effort**         | - Estimate time, complexity, team availability, and dependencies for both requests                                                                                            |
| ✅ **Communicate clearly**     | - Be transparent with both clients about **priority, ETA**, and reasoning                                                                                                     |


## 9. calculated risk
- share_qty | t+2
- UMC : quick fix - added feature to stage file and update it then release it 
    - created endpoints to stage and manually publish.
    - Documented steps. since  manual steps so risk

## 8. Simplified complex problem

| **STAR Element** | **Answer**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| ---------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **✔️ Situation** | We had a batch job system that processed customer data overnight. It was slow, hard to debug, and frequently failed. Performance degradation became a concern as data volume grew.                                                                                                                                                                                                                                                                                                                                                |
| **✔️ Task**      | I was asked to improve performance and reliability without a full rewrite.                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| **✔️ Action**    | - Investigated the PySpark code and found the **main bottleneck was excessive data shuffling** due to wide transformations (like `groupBy` and `join`) on large datasets.<br>- Refactored the job by **optimizing partitioning**, reducing wide transformations, and using `broadcast joins` where applicable.<br>- Rewrote the pipeline using **modular PySpark on AWS Glue**, and replaced manual shell-based triggers with **event-driven Lambda** orchestration.<br>- Added **CloudWatch logs and alerts** for observability. |
| **✔️ Result**    | - **Job runtime reduced by \~50%**<br>- **Failure rate dropped significantly**<br>- The pipeline became **easier to maintain and debug**<br>- Business gained **faster and more reliable data processing**                                                                                                                                                                                                                                                                                                                        |

```python
from pyspark.sql import SparkSession
spark = SparkSession.builder.appName("ShufflingExample").getOrCreate()

# Large dataset A
df_large = spark.read.csv("s3://bucket/large_data.csv", header=True, inferSchema=True)
# Medium dataset B
df_medium = spark.read.csv("s3://bucket/medium_data.csv", header=True, inferSchema=True)
# This join will cause a shuffle across the cluster
joined_df = df_large.join(df_medium, on="customer_id")
# Followed by groupBy - another shuffle
result = joined_df.groupBy("region").count()
result.show()


# Hint Spark to use broadcast join to avoid shuffle
joined_df = df_large.join(broadcast(df_medium), on="customer_id") # 👈🏻

# Repartition by region to optimize groupBy
df_partitioned = joined_df.repartition("region") # 👈🏻
# Aggregation now happens within partition
result = df_partitioned.groupBy("region").count()

result.show()

```

| **Problem**                     | **Fix**                                |
| ------------------------------- | -------------------------------------- |
| Large shuffle on join           | Use `broadcast()` on smaller dataset   |
| Shuffle due to groupBy          | Use `.repartition()` by the group key  |
| Inefficient wide transformation | Reduce or chain narrow transformations |


## 7. why u should not hire me

| **STAR Element** | **Answer**                                                                                                                                                                                                                                                                                                                                                                            |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **✔️ Situation** | In interviews, I’ve sometimes been asked why a company might hesitate to hire me.                                                                                                                                                                                                                                                                                                     |
| **✔️ Task**      | My task is to answer honestly while showing self-awareness and confidence in my fit.                                                                                                                                                                                                                                                                                                  |
| **✔️ Action**    | - I say you shouldn’t hire me if:<br>  • You feel I’m **overqualified** and may not stay long-term<br>  • You feel I’m **underqualified** and not ready to contribute at your expected level<br><br>- But if:<br>  • You want someone with a **balanced profile** of experience and adaptability<br>  • You want someone who can **deliver from day one** and **grow with your team** |
| **✔️ Result**    | - This shows I’m **self-aware**, flexible, and focused on **long-term fit**<br>- I leave the decision to the team while **positioning myself as a strong, thoughtful candidate**                                                                                                                                                                                                      |


## 6 Complex problem
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

## 5 You took something out your area of responsibility
| **STAR Element** | **Answer**                                                                                                                                                                                                                                                               |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **✔️ Situation** | - In my current project, our **onshore QA lead** occasionally takes extended vacations.<br>- This creates a **coordination gap** with the offshore QA team.                                                                                                              |
| **✔️ Task**      | - Ensure **QA continuity** and avoid impact on **release timelines** and **quality**.<br>- Fill in the gap during the QA lead’s absence.                                                                                                                                 |
| **✔️ Action**    | - **Voluntarily backfilled** the QA lead role<br>- Led **offshore QA coordination**, managed test cycles<br>- **Clarified requirements**, prioritized bugs<br>- Acted as a **bridge** between developers and QA team<br>- Ensured **test coverage** and sprint alignment |
| **✔️ Result**    | - Maintained **smooth releases** without delays<br>- Improved **cross-team collaboration**<br>- Gained **stakeholder trust**<br>- Showcased **flexibility** beyond core engineering duties                                                                               |


## 4 learn something new
| **Step**                                      | **Approach**                                                                                                                                          |
| --------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| ✔️ **1. Depends on type of technology**       | My approach varies based on whether it's a new language, framework, or concept.                                                                       |
| ✔️ **2. Learning a new programming language** | - Compare it with known languages (Java, Python, TypeScript)<br>- Understand syntax, typing, memory model<br>- Analyze how it handles common patterns |
| ✔️ **3. Learning a new framework/library**    | - Identify the problem it solves<br>- Understand the abstraction it provides<br>- Explore what it hides under the hood                                |
| ✔️ **4. Learning strategy**                   | - Follow a structured and focused path<br>- Avoid information overload<br>- Choose 1–2 quality resources (blogs, videos, docs) and stick to them      |
| ✔️ **5. Practice and Reinforcement**          | - Build small projects or POCs<br>- Learn by doing<br>- Set aside time regularly to go deep<br>- Reinforce by applying and practicing consistently    |


## 3 project most proud of 
| **STAR Element** | **Answer**                                                                                                                                                                                                                                                                                                                                                                |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **✔️ Situation** | We worked on modernizing a legacy TACT system, which was monolithic, hard to scale, tightly coupled, and had frequent performance and stability issues. It ran only on mainframes.                                                                                                                                                                                        |
| **✔️ Task**      | I was responsible for building the **interface module**, which had:<br>🔸 **Inbound module** — data pipeline to process small CSV files from S3 (used PySpark)<br>🔸 **Outbound module** — event-driven microservices using SQS, EventBridge, SNS, Lambda, Java/Python APIs, and RDBMS schema design                                                                      |
| **✔️ Action**    | - Developed **stateless REST APIs** using Java/Python + Spring Boot<br>- Built **event-driven architecture** using SQS, EventBridge, and Lambda<br>- Implemented **distributed tracing** using OpenTelemetry and X-Ray<br>- Used **Aurora Serverless** with read replicas for performance<br>- Set up **DR strategy** and automated deployments using Terraform + Harness |
| **✔️ Result**    | The system became scalable, observable, and cloud-native. It improved my skills in **system design**, **AWS architecture**, and **cross-team collaboration**.                                                                                                                                                                                                             |


## 2 Conflict  with manager
| **STAR Element** | **Answer**                                                                                                                                                                                                                                                                       |
| ---------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **✔️ Situation** | I was part of a large Angular upgrade project focused on front-end development — rendering JSON responses per UX designs. After go-live, all 18 offshore back-end API developers were released.                                                                                  |
| **✔️ Task**      | My manager expected me to support both front-end and back-end, including batch jobs and production issues — despite having no prior exposure to the back-end systems.                                                                                                            |
| **✔️ Action**    | - I respectfully explained the knowledge gap to my manager<br>- Suggested retaining a few API developers temporarily until things stabilized<br>- Documented every issue I encountered<br>- Proactively started learning key back-end flows using logs and limited system access |
| **✔️ Result**    | - My manager appreciated my transparency<br>- Temporary backend support was re-engaged<br>- I managed the situation without escalation<br>- Learned the value of **clarifying expectations early** and being proactive in tough situations                                       |


## 1 Tell me about yourself.
| **Section**                                   | **Answer**                                                                                                                                                                                                                                                                                   |
| --------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **✔️ Present**<br>(Who you are now)           | - Software engineer with **12 years of experience**<br>- Skilled in building **scalable backend services** and **distributed systems**<br>- Tech stack: **Java, Python, Spring Boot, Angular, AWS, RDBMS, Kubernetes (EKS)**                                                                 |
| **✔️ Past**<br>(Where you’ve been)            | - Started at **Infosys**, working on **enterprise applications**<br>- Transitioned into **cloud-native development**<br>- Worked on **modernization**, **tech debt**, and **migration** projects<br>- Led key initiatives using **AWS Aurora Serverless**, **Lambda**, and **microservices** |
| **✔️ Skills / Strengths**<br>(What you bring) | - Strong in **system design**, **microservices**, and **observability**<br>- Hands-on with **Kafka integration** and cloud-first solutions                                                                                                                                                   |
| **✔️ Future**<br>(Why you're here)            | - Looking for a role to contribute to **high-scale architecture**<br>- Eager to grow in **cloud-native engineering** and explore **GenAI applications**                                                                                                                                      |


