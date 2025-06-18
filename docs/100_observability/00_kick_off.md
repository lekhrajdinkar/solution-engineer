## what are common things in microservices, developer instruments
```text
Here are the common elements developers instrument in microservices for monitoring with tools like Prometheus:

1. Application Metrics
   HTTP Requests: Latency, throughput, error rates (status codes 4xx/5xx)

API Endpoints: Performance per endpoint (GET/POST/PUT/DELETE)

Custom Business Metrics: Transactions processed, orders placed, etc.

2. System/Infrastructure Metrics
   CPU Usage: Process and system-level CPU consumption

Memory Usage: Heap, non-heap, buffer pools (JVM), RSS (native)

Disk I/O: Read/write operations, latency

Network: Bandwidth, connections, errors

File Descriptors: Open files/sockets

3. JVM Metrics (For Java Services)
   Garbage Collection: GC cycles, pause times, memory reclaimed

Threads: Active, daemon, deadlocked threads

Class Loading: Loaded/unloaded classes

JVM Uptime: Time since last restart

4. Database Metrics
   Connection Pools: Active/idle connections, wait time

Query Performance: Slow queries, latency percentiles

Transaction Rates: Commits, rollbacks, deadlocks

5. Cache Metrics
   Hit/Miss Ratios: Cache effectiveness

Evictions: Items evicted due to size limits

Latency: Read/write times for cache operations

6. Message Queue/Event Metrics
   Queue Length: Messages waiting to be processed

Processing Time: Consumer lag, throughput

Errors: Failed deliveries, retries

7. External Service Dependencies
   HTTP Clients: Latency, errors, retries (outbound calls)

Circuit Breakers: State (open/closed/half-open), failure rates

Retries: Attempt counts, exponential backoff stats

8. Kubernetes/Container Metrics (If Applicable)
   Pod Resource Usage: CPU/memory requests vs. usage

Restarts: Crash loop detection

Liveness/Readiness: Health check statuses

9. Logging & Errors
   Error Rates: Exceptions, log error patterns

Warning Signals: Unusual but non-critical events

10. Synthetic Metrics (Proactive Monitoring)
    Heartbeats: Service alive checks

Scheduled Task Metrics: Cron job durations, success/failure

11. Distributed Tracing Metrics
    Request Flow: Latency across service boundaries

Dependency Map: Service-to-service call patterns

12. Security Metrics
    Authentication Attempts: Success/failure rates

Rate Limiting: Throttled requests

Key Non-Metric Considerations
Labels/Dimensions: Environment, service name, version, region

Cardinality Management: Avoid high-cardinality labels

Sampling: For high-volume metrics

Alerting Rules: Define meaningful thresholds

```