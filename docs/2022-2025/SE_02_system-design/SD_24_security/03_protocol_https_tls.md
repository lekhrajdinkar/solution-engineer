# HTTPS / TLS
## Overview

- **man in the middle** Attack with `http`
- TLS / transport layer security (level=4) | https (level=7)
- Its **TLS handshake**, which make Https connection secure, bts.
- All communication is encrypted with symmetric key exchanged between client and **trusted-server**.
- Servers identity is trusted by **Certificate** [x.509.](01_04_x.509.md)
> Certificates authenticate identity.They Do NOT encrypt application data.

---
## why need certificate (Skip)?
```
understand by Example
✔️Flow:
Server creates RSA key pair
Server shares "public-key" 👈🏻
symetric key (fast):
    cleint create preMasterSecret
    encrypt with public key
    server decrypts and get it.
Client encrypts data with symetric key
Server decrypts with symetric key

✔️Fatal flaw:
How does the client know the "public-key" actually belongs to the server?

That’s the classic Man-in-the-Middle (MITM) problem.
An attacker can replace the public key and transparently decrypt/re-encrypt traffic.

👉 Conclusion:
Encryption without authentication is useless on the internet.

This is exactly why TLS + certificates exist.
```
![img_5.png](../../../99_img/2026/02/02/img_5.png)

---
## Modern TLS 1.3
### 1. Certificate issuance (offline)
- Server (abc.com) generates key pair (abc-private-key + abc-public-key)
- Sends CSR to CA.
- CA verifies domain ownership
- CA signs certificate with CA-private-key
- CA issued public CERTIFICATE-1 [having abc-public-key + CA-signature]

```mermaid
flowchart LR
    S["abc.com Server"] --> KP["Generate key pair"]

    KP --> PRIV["abc-private-key 🔒<br/>stays on server"]
    KP --> PUB["abc-public-key 🔑"]

    PUB --> CSR["CSR<br/>domain + public key"]
    CSR --> CA["Certificate Authority"]

    CA --> VERIFY["Verify domain ownership"]
    VERIFY --> SIGN["Sign using<br/>CA private key"]

    SIGN --> CERT["abc.com Certificate<br/>abc-public-key 🔑<br/>CA signature"]
```
### 2. Browser / OS trust store — offline
```mermaid
flowchart TD
    TRUST["Browser / OS Trust Store"]

    TRUST --> R1["Root CA A Certificate"]
    TRUST --> R2["Root CA B Certificate"]
    TRUST --> R3["Root CA C Certificate"]

    CLIENT["Browser"]
    URL["https://abc.com"]

    CLIENT --> URL

    NOTE["Browser trusts Root CAs<br/>NOT abc.com's certificate directly"]

    TRUST --> NOTE
```
![img.png](../../../99_img/2026/02/03/img.png)

### 3. Server authentication
```mermaid
sequenceDiagram
    participant C as Client / Browser
    participant S as abc.com Server

    C->>S: Connect to https://abc.com

    S->>C: abc.com Certificate
    S->>C: Intermediate CA Certificate

    Note over C: Validate certificate chain
    Note over C: ✓ CA signature
    Note over C: ✓ Domain = abc.com
    Note over C: ✓ Valid / not expired
    Note over C: ✓ Intermediate → trusted Root

    Note over C: Server identity trusted ✅

    Note over S: abc-private-key 🔒
    S->>C: CertificateVerify<br/>signature over handshake

    Note over C: Verify using<br/>abc-public-key 🔑

    Note over C,S: Server proves possession<br/>of abc-private-key ✅
```

### 4. ECDHE → shared secret → symmetric keys
```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server

    Note over C: Generate ephemeral key pair
    Note over C: Private 🔒 stays local
    Note over C: Public 🔑

    Note over S: Generate ephemeral key pair
    Note over S: Private 🔒 stays local
    Note over S: Public 🗝️

    C->>S: Client ECDHE public key 🔑
    S->>C: Server ECDHE public key 🗝️

    Note over C: Compute shared secret ⭐<br/>Client private + Server public

    Note over S: Compute same shared secret ⭐<br/>Server private + Client public

    Note over C,S: Both now have the same shared secret ⭐

    Note over C,S: HKDF derives symmetric TLS session keys 🔐

    C->>S: Encrypted application data
    S->>C: Encrypted application data
```
---
## mTLS
TLS + “UNO reverse card” for authentication 😄

| Aspect                         | TLS (one-way)         | mTLS (two-way)                           |
| ------------------------------ | --------------------- | ---------------------------------------- |
| Server authenticates to client | ✅                     | ✅                                        |
| Client authenticates to server | ❌                     | ✅                                        |
| Certificates used by           | Server only           | **Server + Client**                      |
| Use cases                      | Browsers, public APIs | Microservices, internal APIs, zero-trust |
---
## Encryption
![img_2.png](../../../99_img/2026/02/02/img_2.png)

![img_3.png](../../../99_img/2026/02/02/img_3.png)