- link1 - https://chatgpt.com/c/67417202-5748-800d-9fc5-c032961a7c5b 
  - client credential api call, DPoP error fix
  - multiple filters
--- 
## concepts
- OAuth2 dependency :: **spring-boot-starter-oauth2-client** + **spring-security-oauth2-jose**
- LDAP : https://chatgpt.com/c/5865254e-a777-416f-ad16-8e40df050c04 
  - DN entries
- Authentication vs Authorization
- token - JWT https://jwt.io/introduction/
- web filter
- CCGG pattern/s
  - App <--> Authenticating users against an LDAP directory, **directly**. (old)
  - App <--> **OKTA** <--integrated--> LDAP Authentication
    - okta has user Authentication rule configured with LDAP
    - okta has user access config. eg: which **scope** can ask. 
    - okta has **MFA** enabled
    - one app/atmid, 1 scope, 1 client, 1 issuer, JWT-claims::scope [role1,role2,...]
     - developer has grab role and use it method level access.
  - Using SAML for single sign-on (SSO) with external identity providers/Okta.
- security on the fly : **TLS/SSL**

---
## Spring Boot Security
### Intro
- Authentication and access-control framework.
- use web-filter bts
- old: **WebSecurityConfigurerAdapter**
- `@EnableGlobalMethodSecurity`(prePostEnabled = true)` c1
  - `@PreAuthorize`("hasAuthority('SCOPE_my.spring.app.scope')") m()
- `Disable` auto-config
    - @SpringBootApplication(exclude = { **SecurityAutoConfiguration.class** })
    - spring.autoconfigure.exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
- `Enable` auto-config
  - Add **spring-boot-starter-security**
  - Add **WebSecurityCustomizer** `bean`
  ```
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ignore1", "/ignore2");
    }
  ```
  - Add  **SecurityFilterChain** `bean` - `new and functional style`.
    - notice, injecting : **HttpSecurity** http
 
  ```
  @Configuration
  @EnableGlobalMethodSecurity`(prePostEnabled = true)
  public class SecurityConfig 
  {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                .antMatchers("/path-read").hasAuthority("SCOPE_ScopeRead")    
                .antMatchers("/path-write").hasAuthority("SCOPE_ScopeWrite") //.hasRole("").hasAnyRole("","")
                .anyRequest().authenticated()                         
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults()) // Validate JWT tokens
            );
        return http.build();
    }
   }
  ```
  - [Security_01_Config.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fconfiguration%2FSecurity_01_Config.java)
    - webSecurityCustomizer-1 bean 
    - filter-1/2 bean - conditonally enable either.
    
- can have multiple filter beans. eg: :point_left:
  - filter-1 bean  @Order(1)  for url-pattern-1, do form-login
  - filter-2 bean  @Order(2)  for url-pattern-2, do Oauth-JWT-validation 
    - create more SecurityFilterChain and chain it on filter-2
    - .addFilter(filter 3 bean)
    - check reference link1 for code.
  
- **claims** (payload in JWT)
    ```
    {
    "sub": "1234567890",
    "name": "Lekhraj Dinkar",
    "roles": ["USER_ADMIN"],                               <<<
    "scp": ["ScopeRead", "ScopeWrite"],                    <<<
    "iat": 1689704000,
    "exp": 1689707600
    }
    ```
- in SpEL, refer them like
  - **SCOPE_** ScopeRead
  - **ROLE_** USER_ADMIN
---

## More topic
- **Cross-Origin Resource Sharing** (CORS) settings
- Password Management safes: 
  - AWS-secret-manager 
  - CyberArk
- Prevent **XSS** and **CSRF**
  - CSRF : enabled by default, 
  - create custom filter for further CSRF protection
   



