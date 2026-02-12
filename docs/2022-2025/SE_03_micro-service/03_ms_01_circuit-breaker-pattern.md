# Circuit breaker pattern, CBP
https://www.youtube.com/watch?v=dJI2saoM5_k
> Analogy: electrical circuit breaker
>  - it trips cut off power during a surge or short circuit 
>  - to prevent damage from **power surges**.
>  - similarly, 
>    - CBP  prevents a service from being overwhelmed (overload)
>    - when, a dependent service is slow or unavailable
>    - by, interrupting the flow of requests


## First check : Fault Handling in DS
### Transient Faults
- **momentary-issues**, like 
  - network glitches
  - ...
  
- **Example**: 
  - A file upload failing due to network congestion
  - would be reattempted once the network stabilizes
  
- > resiliency strategy :  `Retry Pattern`

### Persistent Faults
- **more lasting issues**, like:
  - complete service outage 
  - data corruption
  - ...
- they cause **cascading failures**, that can bring down an entire system. 👈
- **Example**: 
  - In a high-frequency trading platform , 
  - if a critical component becomes unresponsive 
  - immediately rejecting new requests conserves resources 
  - and allows for quicker recovery

- > Resiliency strategy : `CBP` 👈🏻
    - Repeated retries are counterproductive for persistent faults, 
    - as they waste resources. so wont work here.
    - so, failing fast is often the best strategy, go with CBP.

---  
## overview
- CBP is safeguard to **Persistent Faults (cascading failures)**
- prevents continuous attempts of an operation **likely to fail**
- Thus **adds resilience** to the microservice 👈🏻

```
E-commerce System Example  (cascading failures)
=================================================

Services: Product, Shopping Cart, Order ,Recommendation 

Scenario: Product service experiences an outage  and becomes unresponsive 

First Level Impact: 
    Shopping Cart service receives errors 
    customers can't add items 
    
Second Level Impact: 
    Order service cannot process new orders 
    due to the malfunctioning shopping cart 
    
Third Level Impact: 
    Recommendation service receives incomplete data 
     as the order service struggles 
     
Result: Initial failure in the product service,
 cascades causing significant disruption, lost sales, and damage to reputation 
```

## How the Circuit Breaker Pattern Works
- Acts as a **proxy(intelligent gatekeeper)**  
- that monitors operation amd decides:
  - whether to allow a next request to proceed 
  - or return an exception immediately 
  - or wait 

**States of a Circuit Breaker**

**Closed State**
- Normal operation, requests flow through 
- Constantly monitors the health of the dependent service
- Tracks failed requests, latency, and other metrics 
- Acts as a counter, incrementing failures 

**Open State** 
- If the failure rate exceeds a threshold 
- or the service becomes unresponsive 
- the circuit breaker trips and opens
- Subsequent requests are immediately rejected  without even trying to reach the failing service

**Half-Open State** 
- After a timeout period, the circuit breaker enters a half-open state 
- A limited number of test requests are allowed through to the dependent service
- If these test requests succeed 
  - the circuit breaker returns to the closed state
  - If they fail, it goes back to the open state

## Implementation Libraries
- **Netflix Hystrix**  🔺
  - A pioneer for Java, but no longer actively maintained
- **Resilience4j** ✔️
  - Actively maintained , lightweight, and modular
  - todo, try it.
