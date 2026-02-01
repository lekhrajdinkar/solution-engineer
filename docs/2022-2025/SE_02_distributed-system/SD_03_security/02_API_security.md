# API Security
## API Authn
- basic Auth
- API Key
- oidc ✔️

## API Authz
- OAuth2.1 ✔️

---
## Security headers
- https://www.youtube.com/watch?v=4bQeGUzHpOE

- `Strict-Transport-Security: max-age=63072000; includeSubDomains; preload` Forces HTTPS
- `Content-Security-Policy: default-src 'self'; script-src 'self'` Controls allowed resources
- `X-Content-Type-Options: nosniff` Prevents MIME sniffing
- `X-Frame-Options: DENY` Prevents clickjacking
- `Referrer-Policy: strict-origin-when-cross-origin` Controls referrer leakage
- `Permissions-Policy: geolocation=(), camera=(), microphone=()` Restricts browser features
- `Cache-Control: no-store` Disables caching

**Recommended Secure Baseline**
```
Strict-Transport-Security: max-age=63072000; includeSubDomains; preload
Content-Security-Policy: default-src 'self'
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
Referrer-Policy: strict-origin-when-cross-origin
Permissions-Policy: geolocation=(), camera=(), microphone=()
```

