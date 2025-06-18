- reference:
  - https://www.baeldung.com/persistence-with-spring-series
  - https://www.baeldung.com/multitenancy-with-spring-data-jpa
  - DB-06-SpringJpaData-1 : https://chatgpt.com/c/a874b751-9880-4225-a96c-9052773037fa
  - DB-06-SpringJpaData-2 : https://chatgpt.com/c/8ace7914-f8cc-465e-873a-7b45974bb7b2
  - DB-06-SpringJpaData-3 : https://chat.openai.com/c/7b6dd03e-ca98-44d5-87a6-73c23026a009
  - DB-06-SpringJpaData-4 : https://chatgpt.com/c/8cdd30bb-cd6e-42dc-bb27-43e5235c8a68
    - **spring-data-jpa-projections** :point_left:
---

# SpringBoot JPA Data
## A Intro
- Provide Abstractions.
- @PersistenceContext - txn-scoped.
- AC - 3 bean - datasource, EMfactory/Sessionfactory, TxnManager.
- @Transactional
   - Create Reliable transaction with ACID.
   - provides abstraction, no to write start and commit/rollback.
- ACID (reliable transaction)
  - A tomicity : SB - @Transactional
  - C onsistency : underlying DB sol - constraints, Fk, etc
  - I solated : underlying DB sol.  
    `@Transactional(isolation="choose one") / Optimistic concurrency control`
    - RU(no concurrency - All 3 problem : dirty read, phantom, RR )
    - RC (dirty fixed, but still - phantom, RR)
    - Non-R (dirty fixed, non-R fixed, but still phantom)
    - Serializable ( All fixed )
    - D urable : underlying DB sol - permanent after commit.


## more
- **spring.sql.init.mode = never**
  - always
  - never
  - fallback : Initialize the database using scripts if no schema is detected.
- enable logging:
  - **logging.level.org.springframework.transaction=DEBUG**
  - **logging.level.org.springframework.orm.jpa.JpaTransactionManager=DEBUG**

## testing
### @Sql
- define SQL scripts to be executed before or after a test method.
- define at class or method level.
```
@SpringBootTest

public class MyRepositoryTests {

    @Autowired
    private MyRepository myRepository;

    @Test
    @Sql({"/schema_2.sql", "/dat_2.sql"})
    public void testFindAll() {
        List<MyEntity> entities = myRepository.findAll();
        // Assertions or other test logic
    }
}

```

### @SqlConfig
- used to **configure** the behavior of SQL scripts that are executed during integration tests using the **@Sql**
```
@Sql({"/schema.sql", "/data.sql"})
@SqlConfig(
  dataSource         = "myDataSource", 
  transactionManager = "myTransactionManager")
```








