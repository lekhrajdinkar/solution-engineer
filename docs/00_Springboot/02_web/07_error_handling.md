# Global Error handling

## A. in `spring MVC project`
### 1. send html response for specific error code - 404
- add html page --> resource/templates/404.html 
- add property **server.error.404 = /error/path-404**
- add controller.
  - extract `ErrorAttributes` from  `WebRequest`
```
@Controller
public class ErrorController
{
    @Autowired  ErrorAttributes errorAttributes    <<<
    
    @RequestMapping("/error/path-404")
    public String handle404Error(WebRequest webRequest) 
    {
        ErrorAttributeOptions options = ErrorAttributeOptions.of(
            ErrorAttributeOptions.Include.MESSAGE, 
            ErrorAttributeOptions.Include.EXCEPTION
            );
        
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, options);
        // errorAttributes has exception detail, which can be include in html page.
        // add it to model object.
        // in html - ${errorAttribite.xxxxx}
        
        return "404"; //view name
    }
}
```

---
## B. in `REST API`
### Default handling flow
- when an exception occurs, it is automatically routed to below path.
- define **server.error.path** = /error
- **BasicErrorController** is mapped to this path. 
- it processes and send out json response
- ```
  // Sample resposne:
  
  {
  "timestamp": "2024-11-20T00:00:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No message available",
  "path": "/some-endpoint"
  }
  ```
  - fact: add more attribute in above response
    - @Component public class CustomErrorAttributes extends `DefaultErrorAttributes` : just add this bean
    - check: [CustomErrorAttributes.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fcontroller%2FCustomErrorAttributes.java)
    - add custome attribute
  
### customization-1 : BasicErrorController
- note: don't define server.error.404,etc
- when **any Exception** occurs, it is automatically routed to below path
- define **server.error.path** = /my-error-path 
- or just keep /error
- add **new** @RestController for above path
  - extract **ErrorAttributes**  from **webRequest**. like above.
  - will send out json response.
- @Component class MyBasicErrorController extends **ErrorController** { @GetMapping(/my-error-path) m()}
  - [MyBasicErrorController.java](../../../src/main/java/com/lekhraj/java/spring/SB_99_RESTful_API/controller/MyBasicErrorController.java)

### customization-2 (@ControllerAdvice)
- httpRequest send > No issue - no 401,no 500, etc > controller method m1() gets executed.
- next m1() throws exceptions
- can have **different handler for different exception** type.
  - @ExceptionHandler(Exception.class) RE<> m1(Exception e, WebRequest request) { use e ... }
  - @ExceptionHandler(Exception2.class) RE<> m1(Exception2 e, WebRequest request) {...}
  - ...

### customization-3 :: Disable tomcat Whitelabel-ErrorPage
- @EnableAutoConfiguration(**exclude** = {`ErrorMvcAutoConfiguration.class`}) --> shows Tomcat page then.
- or, **server.error.whitelabel.enabled=false**

---
## inbound / outbound flows :green_circle:
case:1 : incoming request failed, then:
-  /error + BasicErrorController (already).
-  /my-error + MyBasicErrorController (custom) + inject ErrorAttribute.

case-2 : incoming requested success, but business code failed with Exception.
- Global Exception Handling
- @ControllerAdvice/@RestControllerAdvice + @ExceptionHandler(Exception.class)
