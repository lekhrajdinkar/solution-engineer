# Spring - Core
## reference/s
- bauleng:
  - https://www.baeldung.com/spring-tutorial
  - https://www.baeldung.com/spring-dependency-injection
  - https://www.baeldung.com/spring-exceptions
- chatgpt 
  - question: https://chat.openai.com/c/33d0d06d-0aaa-4791-bce9-da8ea3f5dd53
  - interview question (spring core): https://chatgpt.com/c/673d9844-6528-800d-92eb-fa5f45362570
---

## A. Dependency Injection
### key concept:
- `dependency`
- `IoC` : inversion of control of create object from Code to configuration(metadata) + resolve dependency
- `DI` : pattern/design for IoC.
- `Beans` : 
  - `@Scope`(ConfigurableBeanFactory.**SCOPE_SINGLETON**)
  - **singleton** (for stateless bean)
  - **prototype** - hold state and are not thread-safe by default

--- 
### developer primary 2 tasks

> don't use xml config at all. think of java config only.

- **task-1**: create/define beans in meta-config 
  - way-1: `@Configuration` > `@Bean`
  - way-2: `@Component` 
    - get created with default constructor, if parameterized not present.
    - if parameterized present with arg-1 (Type:BeanX). then beanX object must be present.
      - `@Repository`
      - `@Service` 
      - `@Controller`
    - fact with generic class.
      - @Component public class MyGenericBean<**PT,CT extends Item**> {} --> this will **NOT** create a bean. :small_red_triangle:
      - @Component public class StringItemBean extends MyGenericBean<**String, Item**> {} --> will create
      - @Bean -->  new MyGenericBean<**String, Item**> myGenericBean() --> will create
  
- **task-2**: Inject Dependency
  - **manual**
    - `@Configuration` + add this for additional bean lookup `@ComponentScan`("")
      - `@Bean` m(){ return new object() ;}
        - name = {"customBeanName1", "customBeanName2"}
        - initMethod = "init"
        - destroyMethod = "cleanup"
        - autowireCandidate = false/true
      - `@Bean` m( bean1 b1 ){ bean2 o = new object(); o.setb1(b1) ; return o; }  -  **setter injection (manual)** :point_left:
        - use-case: for mandatory dependency
      - `@Bean` m( bean1 b1 ){ bean2 o = new object(b1);  return o; } -  **contructor injection (manual)** :point_left:
        - use-case: for optional dependency
        - override dependency, previously set by construction injection.
      
  - **Autowire** - `@Autowired (required=t/f)` 
    - https://www.baeldung.com/spring-autowire
    - ResolvableType class for  superclass, interface, generic types.
    - injection happens with reflection api
    - used inside bean created with @component
    - classes with a single constructor can omit the @Autowired annotation
    - apply on :
      - `property` 
      - `method` --> get applied to `method arg`, then.
      - `constructor` --> get applied to `constructor arg`, then.
  - Autowire more
    - issue while autowire:
      - `no-bean` found.
      - `multiple-bean` found (Conflict)  NoUniqueBeanDefinitionException. resolve:
        - `@Qualifier`(BeanName)
        - `@primary`
      - `Circular dependencies`:
        - use @`order`(1) 
        - use @`DependsOn`("beanName")
        - refactor code
        - avoid using **constructor Injection** :small_red_triangle:
      - inject - map, list, set
      ```
        - create @Bean b1, b2, b3, of Bean1 type
        - @Autowire list<Bean1> listofbean1 --> inject/add b1,b2,b3 into listofbean1
      ```
      - @Resource(name = "myCustomBean") from J2EE. can also use in spring project.
---
## B. Annotation
### `@profile()`
- bean will create for that profile
- Spring profile : local, dev,qa,prod
- app.prop --> add this spring.profiles.active=local
- env var : SPRING_PROFILES_ACTIVE

### `@lookup` 
- handle scope mismatch.
- scenario-1 : inject SingleBean --> prototype bean : ok
- scenario-2 : SingleBean <-- inject prototype bean : one issue 
  - it will not create new instance of proto member.
  - if we write custom code get new instance, spring won't manage it and could create memory leak, if not cleaned by manually.
  - solution : use @Lookup
  - it's recommended to avoid injecting prototype beans into singleton beans
  - ```
    @Component
    public class MySingletonBean {
    
         @Lookup
         public MyPrototypeBean getPrototypeBean() {
               // This method body will be ignored
                return null;
         }
    }
    ```
### `@Order`
- in context of: 
  - collection :  insertion order.
  - multiple Aspect - Apply order.
  - load bean in container.
- Ordered.LOWEST_PRECEDENCE | HIGHEST_PRECEDENCE

### @DependsOn
```
@Component
@DependsOn({"beanA", "beanC"})
public class BeanB {
    // Bean implementation
}
```
---

## C. Spring IOC container
### bean Life Cycle
- also check : [Spring_02_lifeCycle.md](02_Spring_bean_lifeCycle.md)
- manage bean scopes
  - singleton : create along with IoC container on start up.
  - prototype : create on demand
  
- **stage-1** : create creation + injection
  - step-1 bean creation : by calling constructor + inject mandatory dependency
  - step-2 bean dependency injection :  resolve conflict, if comes
  - step-3 call aware
  - step-4 call Bean Post Processor
    - @component class Hook_1 implements BeanPostProcessor {} - need to register it. :point_left:
    
- BEAN READY  :green_circle:

- **stage-2** : initialization
  - I:InitializingBean > call afterPropertiesSet(){...}
  - `@PostConstruct` m() {...}
  
- **stage-3** : Destruction
  - I:DisposableBean > call destroy(){...}
  - `@PreDestory` m() {...}
  
- **Step-4** : after container is up:
  - @Component CommandLineRunner/s > run()

---

## D. Code / sample programs   
### 1. scheduled task
  - @EnableScheduling @Configuration class Config1
  - @Scheduled(cron = "0 15 10 15 * ?") m() {...}
  - @Scheduled(fixedRate = 5000) m() {...}
  - [ScheduledConfig.java](configuration%2FScheduledConfig.java)

### 2. Custom Annotation
- get class/s
- get all method/s
- anno = method.getAnnotation(myAnno1.class)
- anno.attribute1(), ...
- run you logic around it.
```
public void CustomAnnotationTest(){
        Method method = Runner1.class.getMethod("testMethod1");
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);

        if (annotation != null) {
            System.out.println("Value: " + annotation.value());
            System.out.println("Count: " + annotation.count());
        }
    }
```

