# Authentication
## key term
- `UserDetail` (name,pass,role,etc),
- `UserDetailsService`
- `Authentication`
- `AuthenticationManager` and `AuthenticationManagerBuilder`
  - central point for authentication logic
  - `AuthenticationProviders`
- InMemoryUserDetailsManager : [Security_01_Config.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fconfiguration%2FSecurity_01_Config.java)
- Custom beans :  UserDetailsService AuthenticationProvider, Filters

## ways to do
1. `Form-based Authentication` (not for REST api) // http.loginForm()...
2. `Basic Authentication` / Digest Authentication (old,hashed credentials)
    - hide credential: Authorization header :: Base64-encoded string username:password.
    - it’s possible to hide the key using SSL.
3. `LDAP` - springs helps to integrating with LDAP and perform authentication.
4. `OpenID Connect` :point_left:
- springs helps to integrating with external authentication-providers(okta,google,etc)
- Identity token generate by Okta, requested by UI or consumer.
- okta:
  - Multi-factor Authentication: configuring it
  - SpringApp <--> **okta** <--> LDAP Integration, for Authentication

---