# API Versioning
## Overview
- API versioning allows you to **introduce breaking changes** without breaking existing clients.
```
Breaking changes include:

    Removing or renaming fields
    Changing field types
    Changing response structure
    Changing endpoint behavior
    Making an optional field required

```
```
Non-Breaking changes include:

    Adding a new optional response field is usually backward compatible
    Adding new capbility (new endpoint)
```

```mermaid
flowchart TD
    C{API change}

    C -->|Need more data| F[Add an optional field]
    C -->|Need new capability| E[Add a new endpoint]

    F --> NB[Non-breaking change]
    E --> NB

    C --> R[Remove or rename a field]
    C --> T[Change a field type]
    C --> I[Change required input]
    C --> M[Change response meaning]

    R --> B[Breaking change]
    T --> B
    I --> B
    M --> B

    NB --> S[Ship without new API version]
    B --> V[Create a new API version]

    style NB fill:#d9ead3,stroke:#2e7d32,color:black
    style S fill:#d9ead3,stroke:#2e7d32,color:black
    style B fill:#f4cccc,stroke:#b71c1c,color:black
    style V fill:#f4cccc,stroke:#b71c1c,color:black
    style F fill:#d9ead3,color:black
    style E fill:#d9ead3,color:black
    style R fill:#f4cccc,color:black
    style T fill:#f4cccc,color:black
    style I fill:#f4cccc,color:black
    style M fill:#f4cccc,color:black
```
---
## Design API to Evolve
Goal of API Evolution
- The goal is not to become good at versioning.
- The goal is to avoid needing new versions.
>Design your API so that it grows through additive changes.
```mermaid
flowchart TD

    A[Need a change?]

    A -->|Need more data| B[Add optional field]
    A -->|Need new capability| C[Add new endpoint]
    A -->|Need breaking change| D[Create v2]

    B --> E[Stay on v1]
    C --> E

    style E fill:#d9ead3,color:black
    style D fill:#f4cccc,color:black
```

## Common Versioning Strategies
### 1. URL Path Versioning
```
GET /v1/users/123
GET /v2/users/123
```
> Most common and interview-friendly.

Advantages:
- Explicit and easy to understand
- Easy to route, document, and monitor
- Different versions can run independently

Disadvantage:
- Version becomes part of the resource URL
- Run old and new versions **side-by-side** until clients have migrated, then retire the old version.

```mermaid
timeline
    title API Version Lifecycle

    v1 Live : Initial release
            : Clients integrate

    Launch v2 : /v2 released
              : Breaking changes added

    Deprecate v1 : Announce deprecation
                 : Publish migration guide
                 : Set Sunset date

    Migration Period : Run v1 and v2 side-by-side
                     : Monitor remaining v1 clients

    Retire v1 : Remove v1
              : Only v2 remains
```

### 2. Header Versioning
```
GET /users/123
Accept: application/vnd.company.v2+json
---
Custom header:
    App-version : 2
    X-Version : 2
```
Advantages:
- Clean resource URL
- Supports content negotiation


### 3. param Versioning
```
GET /users/123?version=2
```
- Simple, but usually less preferred because versioning is mixed with filtering parameters and may complicate caching.