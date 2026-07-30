# idempotency
## Overview
- Idempotency ensures that an operation can be performed multiple times 
- without changing the outcome 
  - Idempotency-Key: 8f6b1d4e-...
  - TTL is mandatory, don’t keep forever.

✔️ **handshake between retries & idempotency**
```
 --- Normal flow  ---
Client sends request with Idempotency-Key
Server:
    checks key
    processes request
    stores response
    Client gets response

--- Retry flow  ---
Client retries with same key, on : timeOut, 429, 5XX
Server:
    sees key already processed
    returns cached response
    No duplicate side effects
```

✔️ **Intelligent Retry**
- Immediate retry for network retry
- Exponential backOff + max retries

---
## PayPal Example
https://www.youtube.com/watch?v=S3nq_Iq4eMI bm (no need)

**PayPal implementation to avoid duplicate payment**
- `idempotency keys` extensively to prevent duplicate payments
- http header: `PayPal-Request-Id`
- it first checks an in-memory cache (like Redis) and then persistent storage (like a relational database) for the key.
- If found, the stored result is returned.

![img_1.png](../../../99_img/2026/04/01/02/img_1.png)

