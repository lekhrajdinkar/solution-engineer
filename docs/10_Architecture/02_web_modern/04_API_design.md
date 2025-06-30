- [chatgpt reference](https://chatgpt.com/c/685dd143-0840-800d-8660-0f9cb8afb117)

---
## topics
```
- use diff language for diff usecase

- more type:
    - WebSocket Pattern (for real-time)
    - gRPC for internal services
    - GraphQL Gateway

- AREA-1 :: microservice in k8s (Building modular, independently deployable services.)
    comm / network :
        - service mesh
        - Latency-based Routing (Route 53)
        - B2C : Content Delivery Pattern + AWS global acc
        - Service Registry & Discovery  
        - Event-Driven + async comm + outbox pattern
        - Strangler Fig Pattern :: Rewriting monoliths incrementally.
        - expose service
        - gateway pattern > load-balancer ALB+ACM > pod/task/app
            -  Need unified entry point, rate limiting, authentication
    
    resilient
        - Bulkhead patern
        - Circuit Breaker Pattern
        - Isolate failures with Kubernetes pod resource limits and HPA.
        
        Retry with Backoff
        Circuit Breaker
        Timeouts
        Fail-Fast Pattern
        Graceful Degradation
        Fallback Pattern
   
    performance
        - Fan-out/Fan-in Pattern :: for parallel proecssing
        - Autoscale
        - Backpressure 
            - SQS (request count) --> count metric --> HPA --> more pods
            - add ddq
            - Backpressure retries + circuit breaker
        - Rate Limiting

AREA-2 :: Kubernetes & Container Orchestration

    Sidecar Pattern (log/metrics/agent alongside app)
    Adapter Pattern (use containers to wrap legacy services)
    Ambassador Pattern (for ingress/proxy)
    
    Init Container Pattern (prep work before main container)
    
    Operator Pattern (custom CRD and controllers)
    Controller Pattern (custom automation logic in cluster)
    
    Autoscaling Pattern (HPA, VPA, Karpenter)
    
    Blue-Green Deployments
    Canary Deployments 
    
✅ Area 4: Observability & Monitoring
    Centralized Logging (Fluentd/Bit → OpenSearch/CloudWatch)
    Structured Logging
    log forwarding
    
    Distributed Tracing (OpenTelemetry → Datadog,   AWS SDK --> X-Ray)
    
    Metrics Aggregation (otel/micrometer → Prometheus/Datadog → Grafana)
    
    k8s:
        Health Check 
        Readiness Probes
        resourceLimit
        resourceQuota
        
    Alerting/Alarm/serviceNow on SLOs (SLI/SLO/SLAs with CloudWatch/Grafana)    
    partner bus (datadog)

✅ Area 5 : Security

    Zero Trust Architecture
    Token-Based Authentication (OAuth2/JWT)
    mTLS
    IAM Role-based Access Control
    Secret Management Pattern (AWS Secrets Manager, Vault)
    Audit Logging
```

