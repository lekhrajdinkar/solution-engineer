# Microservices Design Patterns
## 1. Service Communication

| Pattern                      | Status     | Purpose                                                   |
| ---------------------------- | ---------- | --------------------------------------------------------- |
| API Gateway                  | ✅ Existing | Single entry point for external clients                   |
| Backend for Frontend (BFF)   | ✅ Existing | Creates client-specific backends for web, mobile, etc.    |
| Async Messaging              | ✅ Existing | Decouples services using queues, topics, or event streams |
| Sync Service Mesh            | ✅ Existing | Manages synchronous service-to-service communication      |
| Sidecar                      | ✅ Existing | Runs infrastructure functionality alongside a service     |
| Ambassador                   | ✅ Existing | Proxy for inbound/outbound communication                  |
| Service Discovery            | ➕ Add      | Finds dynamically changing service instances              |
| Client-Side Discovery        | ➕ Add      | Client discovers and selects a service instance           |
| Server-Side Discovery        | ➕ Add      | Router/load balancer discovers service instances          |
| API Composition / Aggregator | ➕ Add      | Combines data from multiple services into one response    |
| Request-Reply / RPC          | ➕ Add      | Synchronous communication using REST, gRPC, etc.          |

## 2. Reliability & Resilience
| Pattern                 | Status     | Purpose                                               |
| ----------------------- | ---------- | ----------------------------------------------------- |
| Circuit Breaker         | ✅ Existing | Stops repeated calls to an unhealthy dependency       |
| Retry                   | ✅ Existing | Retries transient failures                            |
| Timeout                 | ✅ Existing | Prevents requests from waiting indefinitely           |
| Bulkhead                | ✅ Existing | Isolates resources to contain failures                |
| Fallback                | ✅ Existing | Provides alternative behavior when a dependency fails |
| Rate Limiting           | ✅ Existing | Restricts request volume                              |
| Load Shedding           | ➕ Add      | Rejects excess work when the system is overloaded     |
| Backpressure            | ➕ Add      | Slows producers when consumers cannot keep up         |
| Idempotency             | ➕ Add      | Makes repeated requests/messages safe                 |
| Dead Letter Queue (DLQ) | ➕ Add      | Stores messages that cannot be processed successfully |
| Health Check            | ➕ Add      | Determines service readiness and liveness             |


## 3. Data Management

| Pattern                     | Status          | Purpose                                               |
| --------------------------- | --------------- | ----------------------------------------------------- |
| Database per Service        | ✅ Existing      | Gives each service ownership of its data              |
| Saga — Choreography         | ✅ Existing      | Coordinates distributed transactions through events   |
| Saga — Orchestration        | ✅ Existing      | Uses a coordinator to manage distributed transactions |
| CQRS                        | ✅ Existing      | Separates read and write models                       |
| Event Sourcing              | ✅ Existing      | Stores state as a sequence of domain events           |
| Transactional Outbox        | ✅ Existing      | Reliably publishes events after database transactions |
| Inbox / Idempotent Consumer | ➕ Add           | Prevents duplicate message processing                 |
| Change Data Capture (CDC)   | ➕ Add           | Publishes database changes to other systems           |
| Materialized View           | ➕ Add           | Precomputes optimized read models                     |
| Shared Database             | ⚠️ Know / Avoid | Couples services through a common database            |


## 4. Deployment & Scaling

| Pattern                   | Status     | Purpose                                                         |
| ------------------------- | ---------- | --------------------------------------------------------------- |
| Blue-Green Deployment     | ✅ Existing | Switches traffic between old and new environments               |
| Canary Deployment         | ✅ Existing | Gradually releases changes to a subset of users                 |
| Strangler Fig             | ✅ Existing | Incrementally replaces a legacy or monolithic system            |
| Auto Scaling              | ✅ Existing | Adds/removes instances based on demand                          |
| Rolling Deployment        | ➕ Add      | Updates service instances incrementally                         |
| Feature Flags             | ➕ Add      | Separates deployment from feature release                       |
| Shadow / Mirrored Traffic | ➕ Add      | Sends copied production traffic to a new version for validation |


## 5. Configuration & Service Discovery

| Pattern                    | Status | Purpose                                                  |
| -------------------------- | ------ | -------------------------------------------------------- |
| Service Discovery          | ➕ Add  | Locates available service instances dynamically          |
| Service Registry           | ➕ Add  | Maintains the list of available service instances        |
| Externalized Configuration | ➕ Add  | Keeps configuration outside application code             |
| Centralized Configuration  | ➕ Add  | Manages configuration centrally across services          |
| Secrets Management         | ➕ Add  | Securely manages credentials, API keys, and certificates |
| Dynamic Configuration      | ➕ Add  | Updates configuration without redeploying services       |


## 6. Observability

| Pattern              | Status | Purpose                                              |
| -------------------- | ------ | ---------------------------------------------------- |
| Centralized Logging  | ➕ Add  | Aggregates logs from distributed services            |
| Distributed Tracing  | ➕ Add  | Tracks requests across service boundaries            |
| Metrics & Monitoring | ➕ Add  | Tracks latency, throughput, errors, saturation, etc. |
| Correlation ID       | ➕ Add  | Associates logs and calls belonging to one request   |
| Audit Logging        | ➕ Add  | Records security and business-sensitive actions      |
| Health Monitoring    | ➕ Add  | Continuously monitors service health                 |
| Alerting             | ➕ Add  | Notifies teams when thresholds or failures occur     |


## 7. Security

| Pattern                            | Status | Purpose                                                     |
| ---------------------------------- | ------ | ----------------------------------------------------------- |
| Service-to-Service Authentication  | ➕ Add  | Verifies workload/service identities                        |
| Authorization / Policy Enforcement | ➕ Add  | Controls which actions a caller may perform                 |
| Token Relay / JWT Propagation      | ➕ Add  | Propagates user/service identity between services           |
| mTLS                               | ➕ Add  | Encrypts and authenticates service-to-service traffic       |
| Zero Trust                         | ➕ Add  | Treats every service/network request as untrusted           |
| Secrets Management                 | ➕ Add  | Protects credentials and sensitive configuration            |
| API Gateway Security               | ➕ Add  | Centralizes authentication, authorization, throttling, etc. |


## 8. Service Decomposition & Architecture

| Pattern                          | Status | Purpose                                            |
| -------------------------------- | ------ | -------------------------------------------------- |
| Decompose by Business Capability | ➕ Add  | Defines services around business capabilities      |
| Decompose by Subdomain           | ➕ Add  | Uses DDD subdomains to identify service boundaries |
| Bounded Context                  | ➕ Add  | Establishes clear domain and ownership boundaries  |
| Anti-Corruption Layer            | ➕ Add  | Protects one domain model from another             |
| Hexagonal Architecture           | ➕ Add  | Separates domain logic from infrastructure         |
| Ports & Adapters                 | ➕ Add  | Isolates external dependencies behind interfaces   |
| Branch by Abstraction            | ➕ Add  | Helps incrementally replace legacy implementations |


## 9. Testing & Operational Patterns

| Pattern                          | Status | Purpose                                                                 |
| -------------------------------- | ------ | ----------------------------------------------------------------------- |
| Consumer-Driven Contract Testing | ➕ Add  | Ensures provider APIs remain compatible with consumers                  |
| Service Virtualization           | ➕ Add  | Simulates unavailable or expensive dependencies                         |
| Test Doubles                     | ➕ Add  | Replaces dependencies during testing                                    |
| Chaos Engineering                | ➕ Add  | Validates system behavior under failures                                |
| Synthetic Monitoring             | ➕ Add  | Continuously executes test transactions in production-like environments |
| Smoke Testing                    | ➕ Add  | Quickly validates deployments                                           |
| Production Verification          | ➕ Add  | Confirms a newly deployed version behaves correctly                     |
