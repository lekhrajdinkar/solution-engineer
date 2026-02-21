# Service mesh
## Overview
- https://www.youtube.com/watch?v=xuOJF3w4vQQ
- infrastructure layer that simplifies,
  - **synchronous service-to-service** communication in a microservices architecture
  - without requiring changes to the service code 👈🏻
- It uses **sidecar proxies** to manage:
  - Fault Tolerance
    - retries on network failure
    - ...
  - security
    - encrypted communication (mTLS)
    - ...
  - traffic
    - load balancing - Routes traffic based on versions, load conditions, or user-specific rules
    - zero-downtime deployments / Canary deployments / eg: 10% to v2(new) and 90% to v1 (old)
  - Observability
    - Provides built-in tools like distributed tracing 
    - and metrics collection to monitor service interactions
    - ...
- Sidecar proxies are deployed alongside each service and intercept all network traffic. 👈

> tools: Istio, Linkerd (lightweight), and Consul

---
## Side-cars pattern
- [check here](../../SE_03_micro-service/02_pattern_04_side-car.md)

