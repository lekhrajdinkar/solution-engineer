# Common patterns
- https://www.hellointerview.com/learn/courses/system-design/lesson/scaling-reads/patterns

## Overview
### 1. Pushing Realtime Updates
* *Protocols:* Start simple with HTTP polling; move to SSE or WebSockets when infrastructure demands.
* *Server-side:* Use Pub/Sub (decoupled pub/sub model) or stateful servers arranged in a consistent hash ring for heavier computation.


### 2. Managing Long-Running Tasks
* *Workflow:* Immediate client ACK with a job ID $\rightarrow$ task queued (Redis/Kafka) $\rightarrow$ worker pool executes asynchronously.
* *Trade-off:* Avoid queues for short tasks to preserve synchronous backpressure and simpler architecture.
* *Key concerns:* Job status tracking, retry strategies, Dead Letter Queues (DLQs).


### 3. Dealing with Contention
* *Techniques:* Single-node transactions, Optimistic Concurrency Control (OCC), pessimistic locking, or distributed locks/serialization queues.
* *Key rule:* Start with single-database transactions before prematurely adding distributed locking complexity.


### 4. Scaling Reads
* *Pattern:* Progression from database indexing/denormalization $\rightarrow$ horizontal read replicas $\rightarrow$ caching layers (Redis, CDNs).
* *Watch out for:* Replication lag, cache invalidation, and hot keys.


### 5. Scaling Writes
* *Mechanisms:* Horizontal sharding, vertical partitioning, batching writes, and buffering bursts with queues/load shedding.
* *Key concern:* Partition key selection to ensure even data distribution without hot partitions.


### 6. Handling Large Blobs
* *Direct Transfer:* Offload app servers by issuing presigned URLs for client-direct uploads (S3/blob storage) and CDN delivery for downloads.
* *Key concerns:* Resumable/multipart uploads, metadata sync via storage events.


### 7. Multi-Step Processes
* *Coordination:* 
  - The key insight is moving from scattered state management and manual error handling to declarative workflow definitions where the system guarantees exactly-once execution and maintains complete audit trails.
  - durable execution engines (Temporal, AWS Step Functions) : handle state management, failure recovery, and retry logic automatically.
  - event sourcing :  where each step emits events that trigger subsequent steps.
* *Guarantees:* Provides automated retries, failure recovery, and audit trails.


### 8. Proximity-Based Services
* *Indexing:* Geospatial indexes (PostGIS, Redis GEO, Elasticsearch) to partition space into regions and prune non-relevant areas.
* *Scale rule:* Useful for $100\text{k}+$ items; for small datasets (e.g., $\sim 1\text{k}$ items), full scans avoid unnecessary architectural overhead.