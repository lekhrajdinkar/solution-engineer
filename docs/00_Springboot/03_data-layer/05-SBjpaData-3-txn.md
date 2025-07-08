- https://www.baeldung.com/transaction-configuration-with-jpa-and-spring - topics

---
# Hibernate - Transaction management
- add @Bean **PlatformTransactionManager**
- add **@EnableTransactionManagement**, or add spring-data-*-starter dependencies
- org.springframework.transaction = **DEBUG**
- fact:
  - Mixing the database I/O with other types of I/O in a transactional context isn’t a great idea.

## A. @Transactional
- manage transaction boundaries in a **declarative** way.
- Spring creates **proxies**, to inject transactional-logic, before and after the running method.
- only **public** methods.
- best place apply on service method 
  - can also apply on repo methods
- @Transactional(attribtes=) : check below

### `propagation` 
- purpose:
  - define the transactional behavior between multiple transactional methods.
  - how the transaction management system handles existing and new transactions
- **requires_new** -> AlwaysNewT 
  - If a transaction exists, the current method will join it
  - If no transaction exists, a new one will be created.
- **Required** -> t1 else t2new 
  - Suspends any existing transaction and starts a new one
- **mandatory** -> t1 else ex 
  - Requires an existing transaction
  - If no transaction exists, an exception is thrown

- **SUPPORTS** -> t1 
  - If a transaction exists, the method will participate in it.
  - If no transaction exists, it will execute non-transactionally
- **none**  / NOT_SUPPORTED
  - Suspends any existing transaction and executes non-transactionally.
- **never** ->
  - Must execute without a transaction
  - Throws an exception if a transaction exists
- **Nested**
  - inner txn :  independent transaction within the context of an existing/outer transaction.
  - if inner transaction rolls back, it only rolls back the nested transaction, not the outer transaction.
  - Not all databases and transaction managers provide full support for nested transactions.
  - it uses Save points.
  - enable it:
```
  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) 
  {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
    transactionManager.setNestedTransactionAllowed(true); // Enable nested transactions                   <<< 
    return transactionManager;
  }
```
  
### `isolation` 
- txn side effect : Dirty-read, Non-repeatable read, Phantom read.
- protection levele: READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ and SERIALIZABLE.
- can check: [03_ACID.md](../../08_Database/01_rdbms/03_ACID+Locks.md)

### `timeout`

### `readOnly` 
– t/f 
- just a hint for the persistence provider that the transaction should be read only. 
- optimizes performance.

### `rollbackFor / noRollbackFor`
- @Transactional(`rollbackFor` = {Exception.class, SpecificException.class})
- @Transactional(`rollbackForClassName` = {"java.lang.Exception", "com.example.SpecificException"})
- if we don't mention any rule, rollback happens on unchecked-exceptions/RuntimeException

---

## B. programmatic txn 
### intro
- check [StudentServiceImpl.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fservice%2FStudentServiceImpl.java)
- **TransactionTemplate** utility provided by Spring to programmatically manage transaction
- Amix with AOP
- open and search : https://chatgpt.com/c/7b6dd03e-ca98-44d5-87a6-73c23026a009
