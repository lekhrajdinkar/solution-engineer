# SpringBoot
## Reference/s
- https://www.baeldung.com/spring-boot
- https://www.baeldung.com/spring-boot-annotations
- https://www.baeldung.com/spring-boot-starters
- https://www.baeldung.com/spring-exceptions
--- 
## Topics
### Core Spring Concepts:
- Dependency Injection (DI),
- Aspect-Oriented Programming (AOP)
- Inversion of Control (IoC).

### Spring Boot Basics:
- Spring Boot **CLI**
- admin setup : https://chatgpt.com/c/478413ee-1707-408e-b3f3-d13ee00e7471
- Spring Boot **Starter project**
  - Spring MVC
  - RESTful Web Services
  - Spring Data JPA
  - Spring Security
-  **Auto-Configuration**
  - customize Auto-Configuration
  - create own **custom starter project**
    - [02_SB_AutoConfig.md](02_SB_AutoConfig.md)
    - customize : we write our custom auto-configurations, we want Spring to use them conditionally.
       - create your own class with @Configuration - new bean + Override Method. eg:WebSecurity, Datasource,etc
       - Apply then conditionally - @ConditionalOnProperty, etc.
       - properties.
- more:      
  - Overview of SB.
  - bootstrap simple webApp
  - Spring vs SB
  - `@SpringBootApplication` --> @EnableAutoConfiguration, @Configuration, @ComponentScan
  - worked on --> web, jpa, test, okta, rmq, ibmmq, doc, okta, etc.
  - `@WebFilter`
  - `banner.txt` keep in resources folder

### later topics
- Spring Boot Actuator: https://chatgpt.com/c/7094d3bf-5952-4b93-bd73-a8abf39ebda1
- Testing in Spring Boot:
- Microservices with Spring Boot
- Spring Cloud