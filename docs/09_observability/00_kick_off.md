## ✅ Observe :: Micro services (Distributed System)
### 1. HTTP-Requests metrics
- Latency, errors, retries 🔸
- **Latency** : rpc > http > https
- **throughput**: no of request per/min
    - Performance per endpoint (GET/POST/PUT/DELETE)
- **error rates** (status codes 4xx/5xx)
- **Custom Business Metrics**: 
```    
    - no of etf 
    - no on mf
    - series yet to approved
    - re-balance/allocation
    - how many times cash adjusted
    - ...
```
- **ms communication**
  - retries : Attempt counts, exponential backoff stats
  - circuit breaker : State (open/closed/half-open), failure rates
  - ratelimit at ms level
  - Dist tracing, Request Flow: Latency across service boundaries
  - Rate Limiting: Throttled requests

- **External paid Service**: eg:
  - pricing api
  - one tick api
  
### 2. Infrastructure Metrics
#### compute
- **lambda metric**
- **container** : EKS pod or ECS task
    - Pod Resource Usage: CPU/memory
    - Restarts: Crash loop detection
    - Liveness/Readiness: Health check statuses
      - Heartbeats: Service alive checks
    - **CPU Usage**: Process and system-level CPU consumption
    - **Memory Usage**: Heap, non-heap, buffer pools (JVM), RSS (native)
    - **Network**: Bandwidth, connections, errors

#### Storage | DB | Cache
- **Storage**: EBS | EFS | s3
- **Database** :  
     - Aurora serverless metric 
         - R count, W count, replication delay, ACU
         - Transaction Rates: Commits, rollbacks, deadlocks
         - Connection Pools: Active/idle connections, wait time
         - Query Performance: Slow queries, latency percentiles
     - DynamoDB : RCU, RCU
- **cache**
    - Hit/Miss Ratios: Cache effectiveness
    - Evictions: Items evicted due to size limits
    - Latency: Read/write times for cache operations

#### Message Queue/Event Metrics
- sqs,sns,eb
- Queue Length: Messages waiting to be processed
- Processing Time: Consumer lag, throughput
- Errors: Failed deliveries, retries

---
### 3.1 JVM Metrics
- Garbage Collection: GC cycles, pause times, memory reclaimed
- Threads: Active, daemon, deadlocked threads
- Class Loading: Loaded/unloaded classes
- JVM Uptime: Time since last restart

### 3.1 Python Metrics
- ...

### 4 Logging & Errors
- Error Rates: Exceptions
- log error patterns


### 5. Cron job Task Metrics:  
- durations
- success/failure   
- Dependency Map:  Service-to-service call patterns

### 6. Security Metrics
- Authentication Attempts: Success/failure rates
- load balancer
- ACM

---
## ✅ Key Considerations
- Labels/Dimensions: Environment, service name, version, region
- Cardinality Management: Avoid high-cardinality labels
- Sampling: For high-volume metrics
- Alerting Rules: Define meaningful thresholds

---
## ✅ references
- https://chat.deepseek.com/a/chat/s/60acbcc7-ec7d-4980-8b85-5fa9d2a82f9f forwards logs java1
- https://chat.deepseek.com/a/chat/s/577558f7-a977-4e8b-b64d-ac8647bcb825 forwards logs java2