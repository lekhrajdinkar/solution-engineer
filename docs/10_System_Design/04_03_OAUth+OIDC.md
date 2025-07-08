## intro
- OAuth2.0 - delegated **authorization** protocol
- OIDC : on top of OAuth with addition oidc scope to return identity token for **Authentication**

## component
- auth server
- resource server
- resource owner
- client

## grant type
- client credential flow
- authorization flow
- implicit flow (old) - SPA
- authorization flow (new) - SPA
- token/jwt 
    - issuer
    - verifier
    - access token (permissions)
    - identity token 
- claims > scope

## hands on 
- https://dev-16206041.okta.com/
- setup MFA ✅
- App integration to fastAPI (py) ✅
- issuer setup ✅ (dont use default)
- set authentication rule ✅
- client credential (machine 2 machine) ✅

