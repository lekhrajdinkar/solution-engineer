- https://chatgpt.com/c/2616aaed-2668-4b78-85c8-f5c81f5ed6a0
---
# APO
- `Aspect` - aspect is a modular unit of cross-cutting concern, `eg : metrics`
- `join-point` - points in the **execution** at code. `eg : method execution`
- `point-cut` - expression/criteria to define joint-point `eg: annotated method`
- `Advice` - code

-  J P A
-  **s3 bucket publish example** :point_left:
  - `Aspect` : publish report into s3 bucket.
  - `pointcut` - @After("expression to find report method")publish_s3() { // logic is `advise` } 
  - at end of method execution is `jointpoint`
---

## Aspects
- Spring AOP encapsulate Cross cutting concept, like
  - logging 
  - transaction management
  - security 
  - `metric` 
  - exception
  - could be anything, `publish s3`, etc

- applied across Spring beans, multiple classes, methods, etc **without modifying the original classes**.
- defined using annotations.
- implemented using proxies.

## Join points
- Aspects are **applied** to the code at specified join-points
- **points in the execution** of the application.
- example:
    - `Method call`: Join points where a method is called.
    - `Method execution`: Join points where a method is invoked or executed.
    - `Constructor execution`: Join points where a constructor is invoked or executed.
    - `Field access`: Join points where a field is accessed (read or written).
    - `Exception handling`: Join points where an exception is thrown or caught.
    - `Object initialization`: Join points where an object is initialized.
    - `Static initialization`: Join points where a class is initialized.

## Pointcut
- It defines the **criteria/expression for matching join points**.
- notice expression below
- Types: before, after(irrespective, normally, exception), around(before+after)
  - 2.1. Before:
    - **advice** runs before the join point,
    - and does not have the ability to prevent the execution of the join point's method.
    - ```@Before("@annotation(org.springframework.security.access.annotation.Secured)")``` :point_left:

  - 2.2. After:
    - **advice** runs after the join point, 
    - regardless of whether the join point  completes normally or by throwing an exception.
    - ```@After("execution(* com.example.service.*.*(..))")```

  - 2.3. After returning:
    - **advice** runs, after the join point completes **normally**.
    - without throwing an exception
    - ```@AfterReturning("execution(* com.example.service.*.*(..))")```

  - 2.4. After throwing :
    - **advice** runs, if the join point exits by throwing an **exception**.
    - ``` @AfterThrowing(pointcut = "execution(* com.example.service.*.*(..))", throwing = "ex")```


  - 2.3. Around:
    - advice surrounds the join point, allowing you to run custom code
    - before and after the join point's execution.
    - ```@Around("execution(* com.example.service.*.*(..))")```

## Advice
- code being runs at a particular join point.

---
- multiple aspects defined for same join point, then can run then in order by using `@order`
```
@Aspect
@Order(1)
public class LoggingAspect {
    // Aspect implementation
}

@Aspect
@Order(2)
public class ValidationAspect {
    // Aspect implementation
}

```




