## leadership 
- initiative | problem-solving
- don't wait for order and continue the work
- lead junior by example | take decision
- **re-evaluate design and write future upgrade** ✅
    - SNS + add dlq + enable SSN delivery logs
    - de-duplicationID 
    - Dashboard API for UI team
    - helix : mc, eks, custom metrics (engine request process rate)

### 1. short term sacrifice for long term gain
- created CIAM dashboard
- removed hardcoding so many thing.
- created config table. perform datamod with doing release in prod.

### 2. calculated risk
- share_qty | t+2
- UMC : quick fix - added feature to stage file and update it then release it
  - created endpoints to stage and manually publish.
  - Documented steps. since  manual steps so risk

### 3. Simplified Complex Problem
- **outbound config:**
  - flexibility in active/deactivate outbound
  - future changes were considered
  - weight based, k1=[v1,v2,...], resolve by weight
  - future state : dynamoDB
- **UMC**
  - added staging areas/s3, for manual update
  - api to enable/disable
- **processing bucket**
  - all event driver by some bucket_mapping_id
  - will be used for tracking and custom metric
- **pySpark Job**

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
### 4. decision with limited time Data

### 5. project with tight deadline
- CIAM

### 6. lead end2end project
- **maps**
    - scalable system design : ms + event-driven + eventpayload
    - API design
    - implementation
    - handle 

### 7.1 deal with offshore / juniors 🟢
- **communication** : `collaboration | leadership | ownership | empathy`
    - transparent with them. 
    - **listen** | no friction | no blame
    - makesure no one have any personal problem
    - comm based on their strength and background | not want to overload | steps by step approaches
    - appreciate work and keep motivated.
    - periodic 1-2-1 session : clarification + highlight area of improvement
    - async comm - confluence + developer guide
    - sync comm - teams daily chat, tag, 
    - summary for long session + ping bullet point/minutes
    - collaborate with team, trust team, seek help from offshore, delegate task
- **leaning** : empower with guidance
    - taught them terraform, and now they rae doing all IAC and pipeline
    - guide on tech  and soft skills
    - my design review and feedback.
    - taught harsh py
    - scale people
- **planning** 
    - assign work based on strenght.
    - plan work with available bandwidth | jira > subtask
    - set clear expectation + set context | discuss roadmap and timeline
    - stepping only when needed.
    - shared UT scenario list, i add more.
    - discuss high level implementation pla and give feedback
    - everyone tell upfront their vacation plan
    - network outage + laptop taken for security
- code review and daily commit
- delegate unplanned work | discuss daily priority | set clear expectation

### 7.2 soft skills 🟢
- Simplify complex idea
- understand individual challenge
- towards the end ensure all ok, any followup question ?
- long session : summarize
- active listening - take input and share my thoughts
- always calm and no friction
   - stay compose and supportive
   - rather than pushy
- strategic collaboration with seniors
- lead by example
- encourage team work
- ensure eveyone equally involved in planning, solutioning and retrospectives

### 8. context switching

### 9. handle unrealistic deadline
- re-scoping

### 10. project did not go as planned
- **maps**
    - monthly release for Business and non-functional
    - postpone non-functional items due to delayed QA testing, since working on multiple release
- **FSR**
    - planned writing test-case / karma for new screen in phase-2
    - API contract changed, ended up reworks.
    - pushed as planned but impacted the release and delivery








