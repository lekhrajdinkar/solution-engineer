- https://chatgpt.com/c/9a8dd8ab-71b5-49d6-bcfe-8d9b0aa31971
- @PersistenceContext(unitName = "entityManagerFactory_for_postgres") private EntityManager entityManagerPostgres; :point_left:
---
# persistenceContext / PC

## A Intro
- Like Spring IAC.
- runtime environment in which entity instances and lifecycle are managed. 
- EntityManager or Session(H) 
  - **thread not safe** : meaning C R U D methods does not have sync and lock code.
  - used to interact with pc
- `@persistenceContext` Session session : inject like this.

- acts as a **first-level cache**. 
  - reduce Db calls
  - improved porformance
- manages entity **life cycle**
  - states:: 
    - `Transient`(new) > `managed`(merged()) > `detached` (detach()) 
    - managed :: **delete**(markedForDelete)
    - managed :: **persist**(markedForDelete)
    - managed :: **update**(update existing managed entity)
    - managed :: **merged&Update**(load and update entity)
  - keeps track of changes made to managed entities.
  - **flush** `dirty entities` to DB, on txn::commit
  - **session/em API** 
    - persist,merge,detach,find,remove,refresh / Flush and close
    - utility: view allManagedEntity, dirtyEntity,etc

---    
## B Types
### 1 Transaction-scoped (default in sb) **
- tied to the transaction.
- It is created when the transaction starts and is closed when the transaction ends.
- eg: Spring-boot-jpa -  @EnableTransactionManagement c, then @Transaction m()
- fact:
  - used in cg maps
  - not need for micro-services arch.

### 2 Extended. 
- workflows where a sequence of operations spans multiple transactions / stateful App. @PersistenceContext(type = PersistenceContextType.EXTENDED)
- Scenario/usecase for extended PC: **stateful App**
```
  A multi-step checkout process in an e-commerce application where a user needs to add items to the cart,
  provide shipping details, and make a payment. Each step might be handled by separate transactions,
  but the cart and order entities need to be kept consistent throughout the process.
```
- **Downsides**:
  - Requires **explicit flush** to synchronize with database :point_left:
  - can introduce complexity in handling concurrency and transaction boundaries.
  - **Increased Memory Usage** + the risk of stale data.
  - Longer response times and potential deadlocks if not managed carefully

---

## C Scenarios
### multi-Http request environment (in ||)
1. PC per request
- http req1 --> thread-1 --> txn-1 --> PC-1 --> commit --> flush to DB
- http req2 --> thread-2 --> txn-2 --> PC-2 --> commit --> flush to same DB. (override)
- Summary:
  - Each HTTP request typically runs in its own thread and transaction.
  - Transactions are isolated from each other, but "concurrency-control-mechanisms"/isolation ensure data consistency.
  - Persistence contexts are tied to transactions and are independent for each request, ensuring that changes made in one request do not affect others until committed.
  - Careful design and configuration are necessary to handle concurrency and transaction management effectively in a multi-request environment.
  - Developer has to write thread-safe code/ concurrent access code, etc

2. PC shared by multiple request.
  - Service class > @PC(Extended) Session/Em > @T m1() + @T m2() + ...
  - PROS : less DB call, fast | CONS : handle concurrency

3. Global PC
  - use second level cache.
  - PROS : very less DB call, faster | CONS : handle global concurrency

- check more : https://chatgpt.com/c/9a8dd8ab-71b5-49d6-bcfe-8d9b0aa31971