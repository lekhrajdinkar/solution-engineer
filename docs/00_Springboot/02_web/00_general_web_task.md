# Things developer does for REST API:

## 1. Customizing REST Endpoints and Controllers
- Endpoint Design: Follow RESTful principles, ensuring that URIs represent resources and use appropriate HTTP methods (GET, POST, PUT, DELETE, etc.).
- Controller Annotations: Use @RestController to define RESTful controllers and @RequestMapping or more specific annotations like @GetMapping, @PostMapping, etc., to map HTTP requests to handler methods.
- Path Variables and Request Parameters: Use @PathVariable and @RequestParam to capture and process data from the URI and query parameters.
- Request Body: Use @RequestBody to bind the HTTP request body to a Java object for POST and PUT requests.
- Response Entity: Return ResponseEntity to have full control over the HTTP response, including status codes and headers.

## 2. Content Negotiation
- Multiple Representations: Support different representations (JSON, XML) by configuring content negotiation.
- Media Types: Use produces and consumes attributes in request mapping annotations to specify supported media types.

## 3. Request Validation and Formatting
- Custom Validators: Create custom validation annotations and logic for request parameters and bodies.
- Date and Time Formatting: Customize date and time formats in request and response bodies.
- Validator library:
    ```
  <artifactId>spring-boot-starter-validation</artifactId>
  <artifactId>hibernate-validator</artifactId>
  <artifactId>validation-api</artifactId>
  ```

## 4. Custom Exception Handling and Error Responses
- Global Exception Handling: Use @ControllerAdvice along with @ExceptionHandler to handle exceptions globally and provide consistent error responses.
- Custom Error Response Structure: Define a custom error response structure to return meaningful error messages and codes.
- Validation Errors: Use @Valid to trigger validation and handle validation errors gracefully.

## 5. Request and Response Logging
- Logging Interceptors: Implement interceptors to log incoming requests and outgoing responses for debugging and monitoring purposes.
- Log Filters: Use filters to log request and response details conditionally, based on configurations.

---

## 6. Security
- CORS Configuration: Configure CORS to allow or restrict cross-origin requests based on security policies.
- Authentication and Authorization: Implement security mechanisms using Spring Security, including JWT (JSON Web Token) for stateless authentication.
- CSRF Protection: Ensure protection against Cross-Site Request Forgery (CSRF) attacks.
- Role-Based Access Control (RBAC): Define roles and restrict access to endpoints based on roles.

## 7. Rate Limiting and Throttling
- Rate Limiting Filters: Implement rate limiting to protect the API from abuse and ensure fair usage.
- Throttling Policies: Define and apply throttling policies to control the rate of requests from clients.

--- 

## 8. API Documentation
- Swagger/OpenAPI Integration: Use Swagger or Springdoc OpenAPI to generate interactive API documentation.
- Annotations: Use Swagger annotations (@Api, @ApiOperation, etc.) to provide metadata for API documentation.
- 
## 9. API Versioning
- URI Versioning: Include version numbers in the URI (e.g., /api/v1/resource).
- Header Versioning: Use custom headers to specify API versions.
- Content Negotiation Versioning: Use the Accept header to specify version (e.g., application/vnd.example.v1+json).

--- 

## 10. Pagination and Sorting
- Spring Data JPA: Utilize Spring Data JPA's built-in pagination and sorting capabilities.
- Custom Pagination: Implement custom pagination logic if required.
-
## 11. HATEOAS (Hypermedia as the Engine of Application State)
- Implementing HATEOAS: Use Spring HATEOAS to add hypermedia links to the API responses, enabling clients to navigate the API dynamically.
- Resource Assemblers: Use ResourceAssembler classes to encapsulate the logic of creating resource representations with links.

## 12. Asynchronous Processing
- Async Controllers: Use @Async and CompletableFuture to handle long-running requests asynchronously.
- Deferred Results: Use DeferredResult or WebAsyncTask to return responses asynchronously.

## 13. Testing
- Unit Tests: Write unit tests for controllers using MockMVC.
- Integration Tests: Implement integration tests to verify the entire API flow.
- API Contract Testing: Use tools like Postman or Pact to ensure API contracts are met.
- 
## 14. Handling File Uploads and Downloads
- MultipartFile: Use MultipartFile to handle file uploads.
- Streaming Responses: Stream large files to clients to manage memory efficiently.
- By customizing these aspects, you can create a robust, secure, and scalable REST API tailored to your specific needs.
