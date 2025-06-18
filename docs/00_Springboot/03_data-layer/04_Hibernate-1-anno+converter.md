- anno : - https://chatgpt.com/c/1375c062-4b67-437d-860b-e065a2980f57
--- 
# Hibernate
## Annotation
### Common Annotation
  - `@Entity`(name="new_entityname"),  @version feild
  - `@Basic`(fetch=FetchType.EAGER,optional=true).
    - this is already added by default on all column.
    - basic mapping, field to a db column.
  - `@Table`(name="new_tablename") 
  - `@Column`(name="new_colname", updateble=f/t), more 
    - Lenght=255, string-valued column length. / @Length
    - Precision and Scale. for decimal feild.
  - `@Transient`
  
### relationship
- @OneToOne, 
- @OneToMany,  @ManyToOne, 
  - `@JoinTable and @JoinColumn()`
  - `@OrderBy("colInManySide ASC")` Sorting Children within Each Parent
  - `@Fetch @batchSize` - use together in relation : 1-2-M,etc

- @ManyToMany : Add FK and create JoinTable bts.
  - `@WhereJoinTable(clause = "columnInJoinTable='value-1'")`

### identifier
- @embeddedId
- @Id
- @GeneratedValue() `@TableGenerator`()
- @GeneratedValue() `@SequenceGenerator`()
- @GeneratedValue() `@GenericGenerator`()
- check more: [04_Hibernate-2-identifier+validator.md](04_Hibernate-2-identifier+validator.md)
  
### Inheritance
- `@MappedSuperclass` on parent class
- `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)` : on parentClass
- `@DiscriminatorColumn(name="columnName",discriminatorType = DiscriminatorType.INTEGER)` : on parentClass
- `@DiscriminatorValue("1")` : on ChildClass

### Performance
- q.setFetchSize(10)
- @BatchSize(size = 20) at Entity level for All operations.
- custom code to achieve batch behaviour :
  - for loop >> on iteration 20, perform flush amd clear em/session.
- Small Fetch Size: May lead to more frequent database calls, increasing network latency and overhead.
- Large Fetch Size: Reduces the number of database calls but consumes more memory as more rows are loaded into memory at once.
- `@BatchSize` on Entity level
- option for relation/many-side:
  - `@Fetch` 
    - (FetchMode.SELECT) : https://chatgpt.com/c/1375c062-4b67-437d-860b-e065a2980f57
      - fetch associated entities lazily, to avoid loading unnecessary data upfront.
      - optimizing performance when dealing with large collections.
    - (FetchMode.Join) : load early

  - `@BatchSize` 
    - Use along with @Fetch.
    - @OneToMany(mappedBy = "order")  @BatchSize(size = 10) private List<OrderItem> items; 
    - optimize the loading of collections.
    - instead of issuing separate SELECT queries for each item, Hibernate will fetch 10 items at a time, reducing the number of queries executed.
    - but may increase memory usage, if batch size is big.
    
  - @OneToMany(`fetch` = FetchType.LAZY/EAGER), etc
  - @Basic(fetch=FetchType.EAGER)

### Convertor (rarely used)
- `@Convert (converter=abc.class)` 
  - more like binder - which jackson for JSON<-->Object(DTO)
  - converter for DB::table<-->Object(Entity)
  - use case : performing encryption/decryption, data transformations, that are NOT directly supported by JPA, etc
  - implement **AttributeConverter<entityType, dbType>** , override:
    - dbType **convertToDatabaseColumn**(entityType)
    - entityType **convertToEntityAttribute**(dbType)
- `@Lob` byte[]
- `@Temporal(TemporalType.DATE)` private Date birthDate;
  - temporal, meaning relating to time.
  - java has seperate set of API(Java.sql.*), to deal with DB Date/time eg: connection code, etc
  - `java.util.*` and `java.time.*`  <--convert-->  `Java.sql.*`
- `@Enumerated`(EnumType.STRING/ORDINAL) MyEnum
  - enums to their ordinal values or names
  - hibernate automatically converts to enum value, if not mentioned.
- Automatic/inbuilt conversion by Hibernate :
  - Boolean to int (t-1, f-0)
  - Boolean to String(t-"true")
  - java.time.LocalDate to java.sql.Date  <<<
  - java.time.LocalDateTime to java.sql.Timestamp <<<
  - enum to string/ordinal. String/int var1


### Other
- `@embedded and @embeddable`
  - define a class whose instances can be embedded in an entity. 
  - This class does not have its own table but shares the table of the owning entity.
  - @Embedded @AttributeOverrides : use to override columnName whiling embedding
  ``` 
  @Embedded  
  @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "home_street")),
        @AttributeOverride(name = "city", column = @Column(name = "home_city")), ...
    })
  ```
- `@SqlResultSetMapping` : Map @NamedNativeQuery result to target(Entity/Tuple)

### Annotation from SB-Jpa-Data-starter
- `@EnableTransactionManagement`
- `@Transactional`
- `@Query` + `@Param`
- `@QueryHints`({@QueryHint(name = "org.hibernate.fetchSize", value = "10")})

---
- **pending**
  - program on inheritance
  - program on relationship