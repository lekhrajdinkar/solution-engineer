## references
- https://www.baeldung.com/rest-with-spring-series
- web filter : https://chatgpt.com/c/34abc85f-eabb-47ac-b525-7c2c6af8023a 
 
---
## REST Actions

### 1 actions list-1
- https://chatgpt.com/c/0471007c-7d4e-4a04-bd37-d6262d5f9aaf - REST Actions
- `create`
- `logging interceptor/filter` --> not doing, having common logging.
- `Content Negotiation` - Support different representations : `produces/consumes` = MediaType.APPLICATION_JSON
- `API versioning`
- `API DOC`
- `security` - using OAuth2.0
- `Validation` - using hibernate JSR validator + custom validator anno.
- `Formatting` - using jackson - custom serialization/de-S..
- `CORS` setup - add frontend url
- `Pagination and Sorting` - pageable and page<E>

### 2 more actions list-2 (pending)
- **Async Controllers**: 
  - Use **@Async("taskExecutor-1")** and **CompletableFuture** to handle long-running requests asynchronously :point_left:
  - [AsyncController.java](../../../src/main/java/com/lekhraj/java/spring/SB_99_RESTful_API/controller/AsyncController.java)
  - **@EnableAsync** - enable
  - create @Bean(name = "taskExecutor-1") **ThreadPoolTaskExecutor** : [AsyncConfig.java](../../src/main/java/com/lekhraj/java/spring/SB_99_RESTful_API/configuration/AsyncConfig.java)
  
- **HATEOS**
  - https://chatgpt.com/c/67414b1b-9018-800d-a683-8a632932177a
  - return **EntityModel<Result>** from api method.
  - use **WebMvcLinkBuilder** to create **Link**
  ```
  User user1 = new User(id, "John Doe", "john.doe@example.com"); // current api result
  ...
  Link allUsersLink = WebMvcLinkBuilder
   .linkTo(
       WebMvcLinkBuilder
          .methodOn(UserController.class)
          .getAllUsers())                     <<< another controller/api method
   .withRel("all-users");
   ...
   return EntityModel<User>.of(user1, allUsersLink);
  ```
  - sample response : `_links`
  ```
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
  
    "_links": {
        "all-users": {
            "href": "http://localhost:8080/api/users"
        }
    }
  }
  ```

---
## A Create API
- check [JewelleryController.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fcontroller%2FJewelleryController.java)
- @ResponseBody + `@Controller` =` @RestController`
- Modify HttpResponse
  - inject `HttpServletResponse response` as method arg directly and manipulate response.
  - ResponseEntity<?> ResponseEntityBuilder -->  statusCode, headers and body.
  - @ResponseBody - serialized into JSON
- Bind `Map<String,String>` with
  - RequestHeader
  - RequestBody
  - PathVariable
  - RequestParam
- can make above input optional. can set default value for above input.
  - eg:  @PathVariable(value="pathVariable2", required = false) String pathVariable2_optional :

---

## B. Validation / JSR 380
- apply on DTO/Bean/ENTITY
- https://chatgpt.com/c/a04dc001-e879-43e0-a39d-acd01b9ef2c7
- Add dependeny : **spring-boot-starter-validation**
- **annotation**: `@email`, `@size`, `@NotBlank("")`, etc
- use `@Valid` annotation to trigger validation in Controller or where ever binding happens:
    - @Valid @ResquestBody dto //method arg
    - Apply on method return.
- validate response: https://chatgpt.com/c/b8c60911-2df5-478b-9804-67c3ecf9506d

### Custom validator 
- check [hibernate_validator](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fhibernate_validator)
- can inject **BindingResult** as well.
- just implement `ConstraintValidator<Anno,feildType>`
- ```
  // Apply @NameCheckAnnotation_1 on feilds
    
  public class NameValidator implements ConstraintValidator<NameCheckAnnotation_1, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
     return true;
  }
  }
  ```
  
- implement **ResponseBodyAdvice** class :point_left:
  - The default Spring validation mechanism does not automatically validate objects wrapped in ResponseEntity. 
  - However, you can achieve this by creating a custom ResponseBodyAdvice implementation.
  - @Valid / @validated :: cannot perform on ResponseEntity.
  - check : https://chatgpt.com/c/591c7b06-4ac0-4f03-a328-038cde9cf7ca

---

## C. Formatting ( Serialize / De-serialize)
- more: [05_Jackson.md](05_Jackson.md)
- binding happens with internal Serialize/De-serialize by jackson, 
  - has inbuilt serializer and de-serializer
  - can create custom ones too.
  - customize **objectMapper**, check [JacksonConfig.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fconfiguration%2FJacksonConfig.java)
- note: String to LocalDateTime : `@DateTimeFormat` (From SB, not jackson)
- HttpMessageConverter
  - new MappingJackson2HttpMessageConverter()

---
## D. Version
- https://chatgpt.com/c/7fa2c12d-eada-4991-944f-cfad8d084805
  - @RequestMapping("/api/v1")
  - @GetMapping(value = "/users", `params = "version=1"`)
    - while consuming, set requestParam :  version=1
  - @GetMapping(value = "/users", `headers = "X-API-VERSION=1"`)
    - while consuming, set header : X-API-VERSION=1
    
  - @GetMapping(value = "/users", `produces = "application/vnd.company.app-v1+json"`)
    - while consuming, set header : Accept=application/vnd.company.app-v1+json

  - Choosing the Right API versioning Approach:
    - URI Path Versioning: Clear and straightforward, widely used.
    - Request Parameter Versioning: Useful for flexibility in request parameters.
    - Header Versioning: Keeps the URL clean, suitable for clients that can easily add headers.
    - Content Negotiation Versioning: Good for complex API evolution but can be harder to debug.

---

## E. REST : consume `Pending...`
- https://chatgpt.com/c/9719e1f6-c4e4-4fac-8941-178c26acc484
### RestTemplate
### webClient

---

## F. HATEOAS `Pending...`
- add hypermedia links to the API responses, enabling clients to navigate the API dynamically
- hands-on pending.

# project : `Pending...`
- SetTimeouts API
- API to download file.
- Filters and Interceptors / `InterceptorRegistry`
- webClient and RestTemplate
- custom Binder
- ResponseBodyAdvice program
- `@WebFilter`
- Send response other than JSON