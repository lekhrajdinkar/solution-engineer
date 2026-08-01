# Idempotency (Skip)
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360644/posts/2192055909
- https://www.youtube.com/watch?v=S3nq_Iq4eMI

## Overview
- Idempotency ensures that an operation can be performed multiple times without changing the outcome 
- create and store Idempotency-Key: 8f6b1d4e-...
- TTL is mandatory, don’t keep keys forever.

## Handshake between retries & idempotency
```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server
    participant D as Idempotency Store <br> (TTL is mandatory)

    C->>S: POST /payments<br/>Idempotency-Key: abc-123
    S->>D: Check abc-123
    D-->>S: Not found
    S->>D: Mark as processing
    S->>S: Process payment
    S->>D: Store completed response

    Note over C,S: Response is lost or times out

    C->>S: Retry with same key
    S->>D: Check abc-123
    D-->>S: Completed response
    S-->>C: Return stored response

    Note over C,S: Intelligent Retry (if failure occured)
    C->>S: Immediate retry for network retry
    C->>S: Exponential backOff with max retries
```

---
## PayPal Example
**PayPal implementation to avoid duplicate payment**
- `idempotency keys` extensively to prevent duplicate payments
- http header: `PayPal-Request-Id`
- it first checks an in-memory cache (like Redis) and then persistent storage (like a relational database) for the key.
- If found, the stored result is returned.

![img_1.png](../../../99_img/2026/04/01/02/img_1.png)

## Strip example

