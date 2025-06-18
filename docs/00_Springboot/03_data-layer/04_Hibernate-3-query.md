- NamedQuery :https://www.baeldung.com/hibernate-named-query
- Polymorphic queries : https://chatgpt.com/c/1375c062-4b67-437d-860b-e065a2980f57
- @SqlResultSetMapping : https://chatgpt.com/c/7a6449ba-dede-478f-9778-1c7a9a5d5d9d
--- 
# hibernate 
## A. Query
- parent class : Query<T> 
- children     : TypeQuery<T>, NamedQuery<T>, NamedNativeQuery<T>
### TypedQuery<T> 
- T = ResultType
- It ensures that the query result matches a specific type, at compile time.
```
TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.department = :dept", Employee.class);
query.setParameter("dept", "IT");
List<Employee> employees = query.getResultList();
Employee employee = query.getSingleResult();
```
- never used.

### NamedQuery<T> (hql) / NamedNativeQuery<T> (sql)
- SB-JPA-DATA repository uses these BTS for `@Query`
- also follows result typing.
- centralized, Pre-validated queries, refer them by name and reuse it.
- use unique name in PC to avoid collision.
- prefer NamedQuery, use NamedNativeQuery only if certain feature not support by JPQL, and need DB specific optimization, etc
```
#==============
# 1 : Define
#==============

@NamedQuery(name = query-1,  query='static HQL/SQL')
@Entity / @mappedSuperClass
class Entity {

}
  - more attribute:
    - timeout = 1, 
    - fetchSize = 10, 
    - cacheable=t/f,  
    - resultClass = Result.class
    
#==============
# 2 : use it
#==============

Query<Result> q = session.createNamedQuery("query-1", Result.class)
q.setparameter(k,v)

q.getSingleResult();
q.getResultList()/Set();

```
### Result to custom-class/Tuple
- reference:  https://chatgpt.com/c/7a6449ba-dede-478f-9778-1c7a9a5d5d9d
- way-1: @NamedNativeQuery(...,  **resultClass** = Result.class)
```
@NamedNativeQuery(
    name = "Employee.findSummary",
    query = "SELECT id, name, department FROM employee",
    resultClass = Result.class
    
    # keep names in sync b/w query and result class           
)

List<Employee> employees = entityManager.createNamedQuery("Employee.findAll", Employee.class).getResultList();

```
- way-2: **@SqlResultSetMapping** (name=Mapping_1, ...) + @NamedNativeQuery(...)

```
@SqlResultSetMapping(
    name = "EmployeeDTOResult",
    classes = @ConstructorResult(
        targetClass = EmployeeDTO.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "department", type = String.class)
        }
    )
)

@NamedNativeQuery(
    name = "Employee.findSummary",
    query = "SELECT id, name, department FROM employee",
    resultClass = Result.class,            
    resultSetMapping = "Mapping_1"         
)

List<EmployeeDTO> employees = entityManager.createNamedQuery("Employee.findSummary").getResultList();
```
- WAY-3 : tuple ** :point_left:
```
@NamedNativeQuery(
    name = "Employee.findSummary",
    query = "SELECT id, name, department FROM employee"
)

List<Tuple> results = entityManager.createNamedQuery("Employee.findSummary", Tuple.class).getResultList();    <<<

for (Tuple tuple : results) {
    Long id = tuple.get("id", Long.class);
    String name = tuple.get("name", String.class);
    String department = tuple.get("department", String.class);
    System.out.println("id: " + id + ", name: " + name + ", department: " + department);
}
```
### Result mapping : more
1. Tuple (more like Object[],hetrogeneous)
    - use Tuple / List<Tuple> : for single ot mutlipe result
    - add <artifactId>javatuples</artifactId>
        - Im-mutable : Pair, triplet - hence maintain data integrity.
2. class MyClass implements **ResultTransformer** :
    - First @override **transformTuple**(,)
    - then use it on any query , q.setResultTransformer(RT rt)
    - inbuilt ResultTransformer.
        - Transformers.TO_ARRAY
        - Transformers.TO_LIST
        - Transformers.ALIAS_TO_ENTITY_CLASS

---
## B. Pagination (query result)
```
int pageNumber = 1;
int pageSize = 10; 
q.setFirstResult((pageNumber-1) * pageSize);  //offset 
q.setMaxResults(pageSize);                    // Limit
q.getResultList();
```

- Another way (old): get ids and then sublist ids
```
    //STEP-1 : sort ids as well / gives total count.
    Query q = entityManager.createQuery("Select f.id from Foo f order by f.id");
    List<Integer> ids = q.getResultList();
      
    //STEP-2
    Query query = entityManager.createQuery("Select f from Foo e where f.id in :ids");
    query.setParameter("ids", fooIds.subList(0,10));
    List<Foo> fooList = query.getResultList();
```

## C. ScrollableResults (query result)
- hibernate feature that allows iterating through query **results** in a memory-efficient way.
- instead of loading all rows into memory, it fetches rows in batches, making it suitable for **processing large datasets**.
- q.scroll()
```
ScrollableResults resultScroll = query.scroll(ScrollMode.FORWARD_ONLY);
query.setFetchSize(10);
while (scrollableResults.next()) {
     Employee employee = (Employee) scrollableResults.get(0);
}

# more navigation method: 

next(): Move the cursor to the next row.
previous(): Move the cursor to the previous row.
first(): Move the cursor to the first row.
last(): Move the cursor to the last row.
scroll(int positions): Move the cursor by a specific number of rows (positive for forward, negative for backward).
setRowNumber(int rowNumber): Jump to a specific row by its number

```
---
## D. Polymorphic queries
- implicitly : query for parent-entity, automatically includes ALL child records --> 6 records.
- explicitly : use **Treat**, to change this behaviour.
- open and search : https://chatgpt.com/c/1375c062-4b67-437d-860b-e065a2980f57
- check below queries:
```
List<Vehicle> vehicles = session.createQuery("FROM Vehicle", Vehicle.class).getResultList();
// Implicit polymorphism: vehicles list will contain instances of both Car and Bike

List<Car> cars = session.createQuery("FROM Car", Car.class).getResultList();
// Explicit polymorphism: cars list will contain only instances of Car

List<Vehicle> vehicles = session.createQuery("FROM Vehicle v WHERE TREAT(v AS Car).numberOfDoors > 3", Vehicle.class) .getResultList();  <<<
```
---
## E. HQL
### joins
- INNER : JOIN, 
- OUTER : (LEFT JOIN / RIGHT JOIN)
- FETCH JOIN  : load associated entities eagerly
- fact: 
  - since relation is already defined with 121,12M, M2M, etc. FK/common col already  added. 
  - hence dont need to explicitly define **ON-condition** with Join.
  - ``` 
    @Query("SELECT e, d FROM Employee e JOIN Department d ON e.department.id = d.id AND d.name = :departmentName")
    List<Object[]> findEmployeesByDepartmentName(@Param("departmentName") String departmentName);
    ```
- https://chatgpt.com/c/1375c062-4b67-437d-860b-e065a2980f57

### STATIC queries
- JPQL and Native-SQL
- JPQL take advantage of OOP of entity class.
- Positional parameters ?1,2,etc vs named parameters (preferred)
- Sorting :: order by  e.feild1, e.feild2
- sorting entities in a 1-2-M relation, meaning list on many side `@OrderBy("childName ASC")` List<T> children. // Parent has many Children
- Sorting : in JPQL/HQPL itself :  NULLS LAST, NULLS FIRST at the end.
- Sorting Query Results with Spring Data : https://www.baeldung.com/spring-data-sorting#sorting-with-spring-data

### DYNAMIC : Criteria API 
- pending / skip

---
## F Batch  processing `in-progress`
- Custom batch code. for>flush/clear after 20.
- `hibernate.jdbc.batch_size`=20
- `spring.jpa.properties.hibernate.jdbc.batch_size`=20
- @BatchSize(size = 20) at Entity level for all Operations (CRUD)
- fact:@GeneratedValue(strategy = GenerationType.IDENTITY ) will disable batch-INSERT Silently. USE SEQUENCE.

---
## Z.Pending:
1. TransactionTemplate prg
2. ScrollableResults program - when processing large dataset, (not sending large Dataset in batches to UI)
3. Query Plan Cache
4. @NamedQuery More attribute
   - cacheMode=GET, IGNORE, NORMAL, PUT, or REFRESH
   - flushMode=ALWAYS, AUTO, COMMIT, MANUAL, or PERSISTENCE_CONTEXT