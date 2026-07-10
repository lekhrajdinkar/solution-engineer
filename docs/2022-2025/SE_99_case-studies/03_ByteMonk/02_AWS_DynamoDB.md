# AWS dynamoDB
- https://youtu.be/ey0HsdZSpoc?si=fByOjcTStTAztvMS
- AWS outage that occurred on `October 2025`,
- specifically focusing on the DynamoDB service in the` US East-1`
- The outage, which lasted about **15 hours**

## Root Cause
- A race condition between two DNS enactors,
- led to the accidental deletion of IP addresses for DynamoDB's main endpoint.
- This left the DNS record empty,
- preventing both customers and internal AWS services from connecting to DynamoDB

## Cascading Failures
- Due to tight coupling between AWS services,
- the DynamoDB failure triggered a **domino effect**:
    - EC2 instances couldn't launch new instances
    - Lambda functions failed to execute
    - Network Load Balancers experienced connection issues
    - The AWS Management Console became inaccessible for many users

## Lessons Learned
- Tight Coupling & Dependencies
    - Over-reliance on a single service can lead to widespread failures.
- Preventing Cascades by: 👈👈
    - Implement circuit breakers
    - timeouts and reties
    - rate limiting
    - graceful degradation
- Multi-Region Architecture
    - Distributing services across multiple regions enhances resilience against regional outages.
- Observability & Monitoring
    - Robust logging, metrics, and alerts are crucial
    - for quick issue identification and resolution.