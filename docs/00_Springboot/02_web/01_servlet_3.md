- https://chatgpt.com/c/d7dba5ab-7f7a-4c1c-a443-f67f15ca09a2 :green_circle:
--- 
# Servlet 3
## new feature/s
- programmatically **register** servlets,filter and lister into it **ServletContext** / SC , using `WebApplicationInitializer`
 - eliminating need of web.xml

 - ```
   public class MyWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic servlet = servletContext.addServlet("exampleServlet", new ExampleServlet());
        servlet.addMapping("/example");
    }
    }
   ```
- **Anno** based - `@WebServlet`, `@WebFilter`, `@WebListener`
- allows **asynchronous** request processing
```
@WebServlet(urlPatterns = "/asyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.start(() -> {
            try {
                // Simulate long-running task
                Thread.sleep(5000);
                response.getWriter().write("Async Response");
                asyncContext.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
```
- programmatically configure **security** settings, but will use sb-security-starter.
- Enhanced **file upload** capabilities with the `@MultipartConfig` 
---

## @webListener
- used to perform actions in response to various **events** in a web application
  - event eg : creation and destruction of the sessions and request objects
  - based  on event already parent listener present, just create subclass out of it
    - HttpSessionListener
    - ServletRequestListener
    - ...
```
@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("Request initialized");
        // Perform tasks such as logging request details, initializing request-specific data, etc.
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Request destroyed");
        // Perform tasks such as cleaning up request-specific data, logging request end, etc.
    }
}

```

## Servlet Context / SC 
-  like spring IAC - AC or hibernate - PC, we have web container - SC
- sample code:
```
  - InputStream inputStream = servletContext.getResourceAsStream("/WEB-INF/resource.txt");
  
  - String paramValue = servletContext.getInitParameter("paramName");
  
  - servletContext.setAttribute("attributeName", attributeValue);
    Object attributeValue = servletContext.getAttribute("attributeName");
    servletContext.removeAttribute("attributeName");
   
  - servletContext.addServlet("dynamicServlet", new MyServlet()); 

```

## @WebFilter
- Components that can perform **filtering tasks** on request and response objects
- before and after the request is processed by a servlet.
  - logging, 
  - authentication, 
  - input validation, 
  - transformation of response content. - intercept
```

@WebFilter(
    urlPatterns = "/*",
    initParams = {
        @WebInitParam(name = "paramName", value = "paramValue")
    }
)
public class ConfigurableFilter implements Filter {

    private String paramValue;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        paramValue = filterConfig.getInitParameter("paramName");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Use the initialization parameter in the filter logic
        System.out.println("Initialization Parameter: " + paramValue);

        // Pass the request and response to the next filter or servlet in the chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}

```





