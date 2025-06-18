# persistence-with-springBoot
## A. Annotations
-  data.sql, schema.sql, JPA-buddy-PlugIn
- `@EnableJpaRepositories`("example.baeldung.com.repo")
- `@EntityScan`("example.baeldung.com.entity")
- `@EnableTransactionManagement` 
- `@Transactional`
- `@Query` - if having indexed/named parameter then bind using `@Param` on arg.
- `@Modifying`
  - method should run inside Transaction 
  - method returns - int,void
  - annotation indicates that the method modifies the state of the database.
- testing : @Sql, @SqlConfig, @SqlGroup - 

---
## B. Multiple DataSource : 
  - DBP_01_Multiple Data Sources : https://chatgpt.com/c/7c2da8f5-3f44-4e71-b4f9-4cdadacf0ec5
  - Create 2 beans - Ds1() and Ds2(), Apply ConfigurationProperties on method -> binds returnType(DataSource).
  - create 2 beans - emF1 , emF2
  - Create 2 beans - Txm1 , Txm2
  - Create 2 sets of entities and repos:
    - package1 : entities1, repos1, : use Datasource1 
    - package2 : entities2, repos2  : Use Datasource2. how, see below ?
  - ```
    @EnableJpaRepositories(
       basePackages = "dao1/repos1/*", 
       entityManagerFactoryRef = "emF1", 
       transactionManagerRef = "txn1"
    )
    // same for another set
    ```
---
## C. Spring JPA Data
- Purpose
  - DAO/persistence layer usually consists of a lot of boilerplate code (CRUD methods, wire em, etc)
  - SJD makes it possible to remove the DAO implementations entirely.
  - find this interface and automatically create an implementation for it.
- Exception 
  - translation is still enabled by the use of the `@Repository`.
  - All DB exception converts/translated to **DataAccessException** hierachy
  - custom translator:
  ```
  # 1
  public class Custom_PersistenceExceptionTranslator implements PersistenceExceptionTranslator 
  {
    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        ...
    }
  }
  
  # 2
  @Configuration
  Public class AppConfig {
    @Bean
    public PersistenceExceptionTranslator exceptionTranslator() {
        return new Custom_PersistenceExceptionTranslator();
    }}
  ```
---  
## Repository
### 1. `CrudRepository<E,ID>` 
- returns `Iterable<E>` 
- ListCrudRepository<E,ID> --> returns `List<E>`
```
    <S extends T> S save(S entity);
    T findOne(ID primaryKey);
    Iterable<T> findAll();
    Long count();
    void delete(T entity);
    boolean exists(ID primaryKey);
```
 
### 2. `PagingAndSortingRepository<E,ID>` 
```
     Iterable<T> findAll(Sort sort);
     Page<T> findAll(Pageable pageable);
```
### 3. `JpaRepository<E,ID>`

### 4. **Custom repo**  
- own interface and impl class

### 5. **Composite repo** 
- mix of above (each is called `fragments` then). eg:
```
# 1
public interface ProductRepository extends CrudRepository<Product, Long> { }

# 2
public interface ProductPagingAndSortingRepository extends PagingAndSortingRepository<Product, Long> {}

# 3
public interface CustomProductRepository {
    List<Product> findByCustomCriteria(String criteria);
}
public class CustomProductRepositoryImpl implements CustomProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findByCustomCriteria(String criteria) {
        String jpql = "SELECT p FROM Product p WHERE p.name LIKE :criteria";
        return entityManager.createQuery(jpql, Product.class)
                            .setParameter("criteria", "%" + criteria + "%")
                            .getResultList();
    }
}

Combine the base repositories and custom repository into a single repository interface.    <<< 

@Repository
public interface CompositeProductRepository extends 
        ProductRepository, 
        ProductPagingAndSortingRepository, 
        CustomProductRepository {
}

```
---
## D. Queries
### 1. Derive methods
- methodName :: `<introducer> [distinct|Top|FIRST]+ "By" + <criteria> + "OrderBy"+propertyName () + Desc|Asc`
- `introducer` : find, read, query, count and get
- `Criteria` :: 
  - propertyName + `Is|IsNot` (T v)
  - propertyName + `IsNull|IsNotNull` (v)
  - booleanPropertyName + `True|False` ()
  - propertyName + `StartingWith` (String prefix)
  - propertyName + `EndingWith` (String suffix)
  - propertyName + `NameLike`(String likePattern);
  - propertyName + `LessThan | LessThanEqual | GreaterThan | GreaterThanEqual | Between` (int...)
  - propertyName + `In` (Collection c)
  - propertyName + `After` (ZonedDateTime)
  - propertyName + `Before` (ZonedDateTime)
  
```
List<User> findByNameIs(String name);
List<User> findByNameEquals(String name);
List<User> findByNameIsNot(String name);
List<User> findByNameIsNull();
List<User> findByNameIsNotNull();

List<User> findByNameStartingWith(String prefix);
List<User> findByNameStartingWith(String prefix);
List<User> findByNameEndingWith(String suffix);
List<User> findByNameContaining(String infix);

List<User> findByAgeLessThan(Integer age);
List<User> findByAgeLessThanEqual(Integer age);
List<User> findByAgeGreaterThan(Integer age);
List<User> findByAgeGreaterThanEqual(Integer age);
List<User> findByAgeBetween(Integer startAge, Integer endAge);

List<User> findByAgeIn(Collection<Integer> ages);

List<User> findByBirthDateAfter(ZonedDateTime birthDate);
List<User> findByBirthDateBefore(ZonedDateTime birthDate);
```

- **Grouping** : And or (Not fan of it.)
  - eg: List<User> `findByNameOrAgeAndActiveCustom`(String name, Integer age, Boolean active);
    - possibility-1 : (name = 'John' OR age = 30) AND active = true
    - possibility-2 : name = 'John' OR (age = 30 AND active = true)
    - its better to use @Query then, to take control.
    - `@Query("SELECT u FROM User u WHERE u.name = ?1 OR (u.age = ?2 AND u.active = ?3)")`


### 2. @Query
- notice:
  - **Sort**
  - **Pageable**
  - **Page<E>**
  - **PageRequest.of()**
```
------------------------
// orderby / sorting
------------------------
sort-object-1:
  - Sort.by("name")
  - Sort.by("price").descending()
  - Sort.by("price").descending().and(Sort.by("name"))
  - Sort.by(Sort.Direction.ASC, "name")

@Query(value = "JPQL", nativeQuery = f)
- List<E> l = repo.findAll(sort-object-1);

------------------------
// pagination
------------------------
Pageable p = PageRequest.of(page_no, page_size, sort-object-1); // int, int, Sort

@Query(value = "JPQL", nativeQuery = f)
- Page<E> l = repo.findAll(Pageable pageable);

@Query(value = "SQL", nativeQuery = True, countQuery = '..count(*)..')
- Page<E> l = repo.findAll(Pageable pageable);

Pageable sortedByName =PageRequest.of(0, 3, Sort.by("name"));
Pageable sortedByPriceDesc =  PageRequest.of(0, 3, Sort.by("price").descending());
Pageable sortedByPriceDescNameAsc = PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));

@Query(value = "SELECT u FROM User u WHERE u.name IN :names")
List<User> findUserByNameList(@Param("names") Collection<String> names);    
``` 
- sample Page<User> after json serialization:
```

{
  "content": [
    {
      "id": 1,
      "name": "John",
      "email": "john@example.com"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalPages": 10,
  "totalElements": 50
}
```
---
## More
- **JPA Buddy plugin** 
  - generation of JPA entities, Spring Data JPA repositories, DTOs,
  - initialization DDL scripts,
  - Flyway versioned migrations,
  - provides an advanced tool for reverse engineering.
