# SOLID
- https://youtube.com/watch?v=5999cgzA95A (skip)

## Overview
| Principle                     | Meaning                                        | System-design interpretation                                                |
| ----------------------------- | ---------------------------------------------- | --------------------------------------------------------------------------- |
| **S — Single Responsibility** | One component should have one reason to change | Split system by clear business responsibility                               |
| **O — Open/Closed**           | Open for extension, closed for modification    | Add new behavior without constantly changing existing services              |
| **L — Liskov Substitution**   | Implementations should be safely replaceable   | Swap implementations without breaking consumers                             |
| **I — Interface Segregation** | Prefer small focused interfaces                | Avoid huge APIs/contracts forcing clients to depend on unused operations    |
| **D — Dependency Inversion**  | Depend on abstractions, not implementations    | Services depend on contracts/interfaces rather than specific infrastructure |
