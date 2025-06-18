- https://chatgpt.com/c/795ed757-ef25-48fc-a1c5-6755aab9bb03
- https://www.baeldung.com/spring-boot-annotations
--- 
## Auto-Config
- Starter project > `spring.factories` = listOfConfigClasses (which can be applied conditionally)
- SpringApplication.run() > create AC, Loads:
  - Standard beans
  - Conditional-1(Is webApp)--> beans-1
  - Conditional-2 : beans-2
  
- **Notice this pattern** 
  - @Configuration Class-1 implements interface-1 >> Override method --> gives new bean. :point_left:
  - this is sb config customization way.
  - ```
    public class MyWebConfig implements WebApplicationInitializer {
       onStartup(ServletContext servletContext) {
         ...
       }
    }
    ```
  - note: **WebApplicationInitializer**, provides spring way to configure **ServletContext**

### Conditions
1. @ConditionalOnClass and @ConditionalOnMissingClass
2. @ConditionalOnBean and @ConditionalOnMissingBean
3. @ConditionalOnProperty
4. @ConditionalOnResource
5. @ConditionalOnWebApplication and @ConditionalOnNotWebApplication
6. @ConditionalExpression
7. `@Conditional` : Custom condition

- Apply these on
  - @Configuration class
  - @Bean method

---

## Custom starter project
1. create new Spring project, call it your starter.
2. Create Auto-configuration/s:
   - @Configuration Class-1 --> with Bean with conditions ,to enable/disable,
   - @Configuration Class-2 --> with Bean with conditions ,to enable/disable, ...
   - @Configuration Class-N --> with Bean with conditions ,to enable/disable
       
3. Register Auto-Configuration Classes
   - resource/META-INF/spring.factories=Class-1, Class-2, ... Class-N
   
4. DONE

5. Now package it and use it other project : 
   > `mvn clean package/install` --> local Maven repo.

4. If want to exclude to Class-1:
   - @SpringBootApplication(exclude={Class-1})

