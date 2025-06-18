# spring MVC 
## overview :green_circle:
- flow with - handler mapping, view resolver, view renderer, interceptor/inbuilt-filter
- https://chatgpt.com/c/efc733ec-0a20-4be2-88da-df50535517b3 - basic flow overflow.

## @Bean `WebMvcConfigurer` 
- create anonymous class out of it and override below method:
  - public void addCorsMappings(CorsRegistry registry) {...
  - public void addResourceHandlers(ResourceHandlerRegistry registry) { ...
  - public void configureViewResolvers(ViewResolverRegistry registry)
  - public void addInterceptors(InterceptorRegistry registry) {
  -  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
  - ...
```
@Configuration
public class WebMvcConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            // Configure Cross-Origin Resource Sharing (CORS)
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Adjust allowed origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            // Configure resource handlers for serving static files
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/static/")
                        .setCachePeriod(3600); // 1 hour caching
            }

            // Configure view resolvers
            @Override
            public void configureViewResolvers(ViewResolverRegistry registry) {
                registry.viewResolver(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"));
            }

            // Configure interceptors
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new CustomInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/login", "/error");
            }

            // Configure content negotiation (e.g., JSON/XML response format)
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer.favorParameter(true)
                        .parameterName("format")
                        .defaultContentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .mediaType("json", org.springframework.http.MediaType.APPLICATION_JSON)
                        .mediaType("xml", org.springframework.http.MediaType.APPLICATION_XML);
            }

            // Configure message converters for HTTP request/response
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                // Add custom converters if needed
            }

            // Configure path match options
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                configurer.setUseTrailingSlashMatch(false);
            }
        };
    }

    // Locale resolver for internationalization
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }
}

```
