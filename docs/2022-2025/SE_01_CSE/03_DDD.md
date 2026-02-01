## Domain-Driven Design (DDD)

### ✔️Introduction
- approach to model system around the **core business domain and its rules**, 
- not just the technology
- Keep the business logic clean, testable, and independent of infrastructure details

### ✔️Core Concept
#### Domain
- Fund Trade Processing for Fund-of-Fund products, 
- involving both LUX and non-LUX underlyings, 
- with SWIFT and CSV-based communications.

#### Ubiquitous Language
- Use **consistent terminology** across the team (dev, BSA, QA) in docs, discussion and code
- This ensures everyone has a s**hared understanding of the domain**.
- **stakeholders** should agree on the terms and use them consistently.
- Example
```
Trade Instruction           – A buy/sell order for a fund.
Cash Movement Instruction   – An order to transfer money.
LUX Underlying portfolio           – Funds settled only via SWIFT messages.
Non-LUX Underlying  portfolio         – Funds settled via SWIFT + CSV file exchange.
NAV Date                    – The valuation date of the trade.
Settlement Date             – When cash/fund units change hands.
Instruction Status          – Pending, Sent, Confirmed, Failed.
Instruction Status          – Pending, Sent, Confirmed, Failed.
```



#### Aggregates 
##### key-points
- A cluster of related entities and value objects treated as a single unit.
- processing bucket - AF, AFIS, CIAM | ETF vs MF
- Aggregates ensure consistency and encapsulate business rules.

##### Entities🔷
- Objects with an identity
- that persists over time (e.g., allocation, rebalance trade, cash instruction).

##### Value Objects🔷
– eg: all data load as part of ETL fundamental job
- currency Exchange, holiday, fund, portfolio,

#### Domain Services 
- **Pure business logic** that doesn't naturally fit within an entity or value object.
- Example: gliding path, allocation algorithm/rule.
- They are **stateless** and can be reused across different parts of the application. 👈🏻
- They should **not** depend on **infrastructure** details like databases or APIs. 👈🏻
- They should be **pure functions** that can be **easily tested**. 👈🏻

#### Domain Events 
- Events that signify something important has happened in the domain.
- review, approve, allocation, publish trade, etc
- purpose: 
  - communicate changes in the **state of the domain**.
  - trigger side effects 
  - notify other parts of the system.

#### Bounded Context 
- Defining boundaries within which a specific domain 
- separating different parts of the domain
- example contexts:
  - `Fund Trade Processing context`
  - `Cash Movement context`
  - `Reporting context`
  - `Compliance context`
  - `SWIFT context`
- Strategic Design: Decide how contexts interact

### ✔️Layered Architecture
- **Domain Layer** 
  - Entities 
  - Value Objects
  - Aggregates
  - Domain Services (pure business logic).
- **Application Layer** 
  - Orchestrates domain logic for use cases (no business rules here).
- **Infrastructure Layer** 
  - Technical details (DB, APIs,)
  - message brokers
  - AWS
  - file systems, etc.
  - k8s
- **Interface Layer** 
  - API endpoints
  - UI controllers
  - CLI commands.