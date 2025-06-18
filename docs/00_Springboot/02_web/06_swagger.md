# Swagger

## Enable swagger
- add dependeny
```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>

 or
 
 <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.5.12</version>
</dependency>

or

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>3.0.0</version>
</dependency>

or

<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>    <<< worked, didnt add anything else
	<version>2.1.0</version>
</dependency>
```
- add config 
  - Docker bean
  - @EnableSwagger2WebMvc
```
@Configuration
@EnableSwagger2WebMvc / @EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(PathSelectors.any())
                .build();
    }
}

```
- http://localhost:8080/swagger-ui/index.html

---

## Customize
- check : [controller](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fcontroller)
- Annotation:
  - @Tag/@Api,
  - @ApiOperation,
  - @ApiResponse,
  - @ApiParam.
