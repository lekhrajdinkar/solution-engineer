## Circuit Breaker
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