# Jwt
- https://chatgpt.com/c/6866e4b3-d6d8-800d-a053-ad736cec9b28
- https://www.youtube.com/watch?v=iB__rLXGsas&list=PLJq-63ZRPdBt-RFGwsJO9Pv6A8ZwYHua9 bm

--
## old Session-token
- **J-session ID** in JSP app.
- session storage required by server, hence **stateful**

## JWT :: Overview
> primarily fixes Scalability issues : since session info is not stored on backend server, no need to scale it.

- JWT is normally signed, not encrypted.
```
JWT ≠ encrypted by default
JWT = Base64URL encoded header + payload + cryptographic signature

    - private key to sign
    - public key to verify jwt
```
- self-contained session token
- Stateless (no session storage required)



---  
## Benefit
- URL-safe
- Easily verified by resource server
```
exp → expiration
iss → issuer
aud → audience
scopes/roles/permissions
```
- compact  / light weight / easily and securely send over internet.
- flexible and adaptable, since use to represent **variety of claims**
- reducers the load on Authn service
- same token can be access multiple ms

| Advantage                 | Why Stateless Authentication Helps                                            |
| ------------------------- | ----------------------------------------------------------------------------- |
| **Scalability**           | Any server can handle any request because session state is not stored locally |
| **Load Balancing**        | No sticky session needed; traffic can go to any healthy instance              |
| **Reliability**           | If one server fails, another server can continue processing the request       |
| **Flexibility**           | Servers can be added, removed, restarted, or redeployed easily                |
| **Continuous Deployment** | Rolling, blue/green, and canary deployments are easier                        |
| **Simplicity**            | No session synchronization between application instances                      |
| **Traffic Management**    | Works well with autoscaling and dynamic routing                               |
| **Kubernetes Friendly**   | Pods are disposable; any pod can validate the token                           |

## Main trade-off

| Challenge            | Reason                                                                    |
| -------------------- | ------------------------------------------------------------------------- |
| **Token revocation** | A valid JWT may remain usable until it expires                            |
| **Token size**       | JWT is sent with every request                                            |
| **Security**         | Stolen tokens can be reused until expiry unless additional controls exist |

---
## JWT :: Use case
- efficient for ms comm.
- stateless Authn/AuthZ ⭐
  - authentication state travels with the request, not with a specific server.

```mermaid
sequenceDiagram
    participant C as Client
    participant A as Auth Server
    participant API as API Server

    C->>A: 1. Login / Authenticate
    A-->>C: 2. JWT
    Note over C: Store JWT

    C->>API: 3. Request + Authorization: Bearer JWT
    API->>API: Validate signature + claims
    API-->>C: 4. Response
```

---
## JWT :: Structure
> `<Header>.<Payload>.<Signature>`

### header (Base64Url encoded JSON)**

```json
{
  "alg": "HS256", // algo to sign the token
  "typ": "JWT" // always JWT
}
```

```python
json_string = '{"alg":"HS256","typ":"JWT"}'
import base64
base64url_header = base64.urlsafe_b64encode(json_string.encode()).rstrip(b'=').decode()
# eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```

### payload (Base64Url encoded JSON)
- never store sensitive info in payload.
- Contains the **claims** (user data, metadata).
- type:
  - **registered**
    ```json
    {
    
    "iss": "https://<your-okta-domain>.okta.com/oauth2/default",
    "sub": subject (user ID),
    "aud": audience,
    "exp": expiration time,
    "iat": issued at,
    "scp": []
    }
    ```
  - **Custom** :  Store any kind of info want to keep in token.
    ```json
    {
    "role": "",
    "scp": []
    }
    ```
    
### Signature / wax seal
- Ensures Authenticity of token.
- Used to verify that the token is not tampered with
- The **secret** is a shared secret key known only to the issuer 
- verifier  use public key
- It must be kept private, AWS Secrets Manager
- created from below 3 things:
```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret 👈🏻
)
```

---
## JWT :: verifier :: code example
### Sample java
- Note: SB project Automatically calls JWKS endpoint dynamically fetch and verify the JWT's signature.

### sample python
```python
from jose import jwt
import requests

# Fetch JWKS from Okta
jwks_url = "https://<your-okta-domain>/oauth2/default/v1/keys"
jwks = requests.get(jwks_url).json()

# Extract token from request (e.g., Authorization header)
token = "<access_token_here>"

# Decode token header to find 'kid'
unverified_header = jwt.get_unverified_header(token)
kid = unverified_header['kid']

# Find matching public key
key = None
for jwk in jwks['keys']:
    if jwk['kid'] == kid:
        key = jwk
        break

# Decode and verify token using public key
from jose import jwk as jose_jwk
from jose.utils import base64url_decode

public_key = jose_jwk.construct(key)
message, encoded_sig = token.rsplit('.', 1)
decoded_sig = base64url_decode(encoded_sig.encode())

if public_key.verify(message.encode(), decoded_sig):
    payload = jwt.decode(token, public_key, algorithms=['RS256'], audience="api://default")
    print("Verified payload:", payload)

```