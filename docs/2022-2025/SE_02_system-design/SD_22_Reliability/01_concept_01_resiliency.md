# Resilient System
## Overview
```mermaid
flowchart TB
    T1["PLANNED FOR"]
    T2["UNEXPECTED FAILURES (SURPRISES)"]
    T3["Resilience"]

    T1 --> T2

    subgraph U[" "]
        direction LR

        A["❓<br/><br/>⚡<br/><br/><b>10× Traffic Spike</b>"]
        B["❓<br/><br/>🛠️<br/><br/><b>Malformed Data</b>"]
        C["❓<br/><br/>🔗<br/><br/><b>Cascading Failure</b>"]
        D["❓<br/><br/>🛡️<br/><br/><b>Novel Attack</b>"]
    end

    T2 --> A
    T2 --> B
    T2 --> C
    T2 --> D

    style T1 fill:transparent,stroke:transparent,color:#ef4444
    style T2 fill:transparent,stroke:transparent,color:#ef4444,font-size:32px,font-weight:bold
    style U fill:transparent,stroke:transparent

    style A fill:#321821,stroke:#ef4444,stroke-width:2px,color:#ffffff
    style B fill:#321821,stroke:#ef4444,stroke-width:2px,color:#ffffff
    style C fill:#4a151c,stroke:#ff3333,stroke-width:3px,color:#ffffff
    style D fill:#321821,stroke:#ef4444,stroke-width:2px,color:#ffffff

    linkStyle 0,1,2,3,4 stroke:transparent
```

---
## Strategies/patterns (for microservice)
### 1 Decouple system with message broker

### 2 Circuit Breaker pattern
```text
Purpose: 
Prevent cascading failures in distributed systems.

Implementation:
Use libraries like Resilience4j or Hystrix.
Define thresholds (e.g., failure rate, timeout) to trip the circuit.
Implement fallback mechanisms (e.g., cached responses, default values).

Use Case: 
Microservices calling external APIs (e.g., payment gateways, third-party services).
```
### 3 Timeout + retries

### 4 rate limiting

### 5 bulk head


