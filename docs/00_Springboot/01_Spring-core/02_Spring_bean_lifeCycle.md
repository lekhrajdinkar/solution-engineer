## lifecycle
- The lifecycle of a Spring bean consists of several stages, from instantiation to destruction.
  - performing initialization
  - performing cleanup tasks.
- fact: don't use aware, better way is to inject.
  ```
  @Autowired    private BeanFactory beanFactory;
  @Autowired    private ApplicationContext applicationContext;
  ```
---

###  `stages` : bean's lifecycle

1.` Instantiation And populate Properties`:
- The bean is created using its constructor or a factory method.
- Dependencies and properties are set on the bean, either through setters or fields.

2. `Aware Interfaces`:
- If the bean implements Aware interface
  - BeanNameAware,
  - BeanClassLoaderAware,
  - BeanFactoryAware, or ApplicationContextAware,etc
    - Note: can also @autowired them directly.

3. `BeanPostProcessors`:
- `hooks` for customizing the bean lifecycle,
- If there are any BeanPostProcessor implementations `registered` in the context, they are applied.
- These can modify the bean instance before and after initialization, which is next step.

> bean is Constructed

4. `Initialization`:
- If the bean implements `InitializingBean`, Spring calls its `afterPropertiesSet()` method.
- `@PostConstruct` annotated method is called.

> Ready for Use 

5. `Destruction`:
- If the bean implements DisposableBean, Spring calls its "destroy()" method,
 when the bean is no longer needed.
-@PreDestroy annotated method, it is called.

6. `Destroy`:
- The bean is destroyed and its resources are released.

---

# More Notes:
- It's important to note that not all beans go through every stage of the lifecycle.
- For example, beans that are singleton-scoped only go through the lifecycle once,
- while prototype-scoped beans go through the lifecycle each time they are requested from the container.
