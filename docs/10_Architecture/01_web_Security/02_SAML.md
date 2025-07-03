## concept
- [gpt🗨️](https://chatgpt.com/c/674f757c-65ac-800d-915f-37f764adb69d)
- SAML (Security Assertion Markup Language):
- A protocol primarily used for Single Sign-On (SSO) **authentication**.
- Allows users to log in once and access multiple services without re-authenticating.
- Commonly used in enterprise environments.
- **browser-based single sign-on**
- It allows identity providers (IdPs) like Okta to authenticate users and pass their identity to Saas, AWS, etc
- **Okta** simplifies SAML setup via pre-built integrations with many SPs. ⬅️

---
## Okta
```yaml
  - https://dev-16206041-admin.okta.com/ 
    # github user (admin) + mfa
  - https://dev-16206041.okta.com/ 
    #for regular user
  
  # Issuer - App intergraction OAuth
  - https://dev-16206041-admin.okta.com/admin/app/oidc_client/client/0oal3d72smuSHBhwF5d7#tab-general
    - client_id : 0oal3d72smuSHBhwF5d7
    - issuer URI :
      - https://dev-16206041.okta.com/oauth2/default (default)
      - https://dev-16206041.okta.com/oauth2/ausl3dg4kkpyvEBft5d7
```

## hands on
### Connect to AWS with okta
- soon