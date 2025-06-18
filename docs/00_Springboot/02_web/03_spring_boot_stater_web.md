- https://chatgpt.com/c/831f36a0-bce4-4372-87d4-9ab6528babc5 
--- 
# sb-starter-web
## A. features
- necessary dependencies (jackson, springMVC), 
- embedded server
- default exception
- Actuator

---
## B. useful customization class
### 1. class: WebServerFactoryCustomizer 
- interface provided by Spring Boot 
- allows you to customize the configuration of embedded web servers
  - setting ports, 
  - enabling SSL, 
  - configuring timeouts

```
@Component
public class MyTomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) 
    {
        factory.setPort(8081);  
        factory.setContextPath("/myapp");  

        // Additional customization can be done here
        factory.addConnectorCustomizers(connector -> {

        });
        
        // SSl
        Ssl ssl = new Ssl();
            ssl.setKeyStore("classpath:keystore.jks");
            ssl.setKeyStorePassword("password");
            ssl.setKeyPassword("password");
        factory.setSsl(ssl);
    }
}
```

### 2. class: ServletRegistrationBean
- Register a New Servlet (spring boot way)
- similarly have @Bean `FilterRegistrationBean<MyFilter>`

```
@Configuration
public class ServletConfig 
{
    @Bean
    public ServletRegistrationBean<CustomServlet> customServletRegistrationBean() 
    {
        ServletRegistrationBean<CustomServlet> registrationBean = new ServletRegistrationBean<>(new CustomServlet(), "/custom");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }
}
```

### 3. Class: SpringBootServletInitializer
- used when you want to deploy a Spring Boot application to an **external** servlet container
- check below for more.

---
## C. Typical things:
### 1. update embedded server
- update pom 
  - add spring-boot-starter-**undertow** / spring-boot-starter-**jetty**
  - exclude <spring-boot-starter-**tomcat** from `spring-boot-starter-web`

### 2. shut down project
- ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
- context.close();

### 3. Remove any embedded Server / Deploy on external Server
- **remove** dependencies for embeded server
- change to **war** : <packaging>war</packaging>
- extend **SpringBootServletInitializer** -> override Configure.
```
   @SpringBootApplication
   public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }
}
```
