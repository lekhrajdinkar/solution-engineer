# API Design
## Overview
> - MVC (philosophy since late 1970)
> - API type - REST,SOAP,grpc,graph
> - tools: browser developer tools, curl

**useful links**
- Java https://github.com/lekhrajdinkar/microservice-java/tree/main/docs/2012-2024/02_web
- py https://github.com/lekhrajdinkar/microservice-python/tree/main/src/webApp1
- [https_tls.md](../SD_24_security/03_protocol_https_tls.md)
- https://chatgpt.com/c/685dd143-0840-800d-8660-0f9cb8afb117
- localhost: https://www.youtube.com/watch?v=PwNJXUdMkVY

---
## Three-Step Approach
https://www.youtube.com/watch?v=Ch_IBiQvZ-c
- Ask clarifying **questions**
  - Determine the domain/scope, functionalities, and target consumers of the API.
  -  crucial to be open to feedback from other engineer
- Discuss **system requirements**
  - system scale, number of users, geographical regions, and data exchange payloads
- **Outline** the solution: 
  - Define the entities 
  - resources the API relies
  - their properties

--- 
## Best principles for designing REST APIs
- https://www.youtube.com/watch?v=pJ83mmqcvoQ
- http://youtube.com/post/UgkxLxv7GwjfLpkPlyy-j3FaW5BBdVIXJAcc?feature=shared
- Adhering to these principles ensures that your web services are **lightweight, scalable, and user-friendly**

**Use HTTP methods correctly** | `Verbs`
- ensuring they are **idempotent** when appropriate. (delete, put)
- HEAD get with header response
- OPTION, check allowed option, preflight request

**Resource Design** :
- Focus on `nouns` for URIs to identify resources (e.g., /customers),
- avoiding actions in the URL.
- Use hierarchical URIs for nested resources (e.g., /customers/123/orders).

**HTTP Status Codes** :
- Communicate the outcome of a request using standard HTTP status codes
- 200 OK
- 201 Created
- 404 Not Found
- 500 Internal Server Error

**Error Handling** :
- Provide clear, consistent, and descriptive error messages,
- often using **global exception handling** in frameworks like Spring.

**Validation** :
- Validate incoming request data using frameworks
- like **Hibernate Validato**r to ensure data integrity.

**API Versioning** :
- Implement versioning, to manage API changes without breaking existing applications.
    - **URI versioning** like /v1/customers
    - **header versioning** like Accept-Version: V1)

**Pagination, Filtering, Sorting** :
- Manage large datasets efficiently by implementing pagination,
- filtering (using query parameters),
- and sorting options to enhance usability.

**HATEOAS**
- (Hypermedia as the Engine of Application State)
- Enhance **API discoverability** by including hypermedia links in responses,
- guiding clients on possible next actions.

**Security** :
- Ensure API security by using HTTPS for encrypted communication
- and implementing authentication (e.g., OAuth 2.0, JWT)
- and authorization mechanisms, often with tools like Spring Security.
- Rate Limiting and throttling

