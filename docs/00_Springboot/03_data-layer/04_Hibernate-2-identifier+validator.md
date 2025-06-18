# Hibernate
## Identifier : strategies
- `@GeneratedValue(Strategy = GenerationType."XXXXX")`
### AUTO 
- hibernate will choose automatically, based on dialect.
- Oracle, PostgreSQL    : Uses `SEQUENCE` because Oracle supports sequences.
- MySQL, SQL Server, H2 : Uses `IDENTITY` because MySQL supports auto-increment columns.

### IDENTITY : 
- `Auto-increment`, can apply on Number types.

### TABLE 
- ` @GeneratedValue () @TableGenerator()` 
- less common, PK is generated using a "table" that holds a set of unique keys.
  ```
  CREATE TABLE ID_GEN (
      GEN_NAME VARCHAR(255) NOT NULL,
      GEN_VALUE BIGINT NOT NULL,
      PRIMARY KEY (GEN_NAME)
  );
    
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "my_table_generator")  <<<
  @TableGenerator(
  name                = "my_table_generator", 
  table               = "ID_GEN",
  pkColumnName        = "GEN_NAME",
  valueColumnName     = "GEN_VALUE",
  pkColumnValue       = "MY_ID_GEN_1",
  allocationSize      = 1 
  )
  private Long id;
  
  - SELECT GEN_VALUE FROM ID_GEN WHERE GEN_NAME = 'MY_ID_GEN_1';
  - UPDATE ID_GEN SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'MY_ID_GEN_1';
  ```
### SEQUENCE 
- `@GeneratedValue @SequenceGenerator` : 
  ```
  CREATE SEQUENCE MY_SEQUENCE START WITH 1 INCREMENT BY 1;             <<< 
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
  @SequenceGenerator(name = "my_sequence", sequenceName = "MY_SEQUENCE", allocationSize = 1)
  private Long id;
  
  allocationSize -  number of sequence values to allocate at a time
  ```
### GENERIC -  UUID/GUID
-  `@GeneratedValue()  @GenericGenerator()` - from hibernate
- Random Sequence, avoid predictable sequences for security.
- fact:  Random values can lead to **fragmented indexes**, which can affect performance.
- **UUID/GUID** 
  - 128 bit - globally unique
  - eg: `550e8400-e29b-41d4-a716-446655440000`
  - 32 char,string, group of 5 by Hypen.
  - incorporate **TimeStamp** and **hardware** info.
  - use-case : DB:Pk, DistributedSystem, registry keys, etc
- eg:
  - org.hibernate.id.UUIDGenerator 
  ```
      @GeneratedValue( generator = "myUUID")
      @GenericGenerator(name = "myUUID", strategy = "org.hibernate.id.UUIDGenerator")
  ```
  - create own:   [CustomIdentifier.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSB_99_RESTful_API%2Fentities%2FCustomIdentifier.java)  
  ```
  public class CustomIdentifier implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return UUID.randomUUID();
    }
  }            

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generic-1")
  @GenericGenerator(name = "generic-1", strategy = "CustomIdentifier")
  private Long id;

  ```
    
- **NanoID**
  - unique IDs that are shorter and more URL-friendly.
  - eg: `DqQrAcB9jK`
  
- **TimestampId**
```
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        long timestamp = System.currentTimeMillis();
        int random = new Random().nextInt(999999);
        return timestamp + "-" + random;
    }
```
6. Composite Identifiers
- https://www.baeldung.com/hibernate-identifiers
- @Embeddable Class ABC : Also public no-agr const, define equal and hashcode
- then inject `@EmbeddedId` ABC id;

---

## D. Validator 
- <artifactId>spring-boot-starter-validation</artifactId>
- @Min / @Max / @DecimalMin/MAX / @Range- on numbetypes
- @Size(min = 3, max = 15) : on String
- @length(min = 3, max = 15) : on Collection
- @Null/ @NotNull / @NotEmpty / @NotBlank
- @AssertTrue / @AssertFalse
- @Pattern(regexp = "^[0-9]{10}$") String phoneNo
- Date:
  - @Past
  - @PastOrPresent
  - @Future
  - @FutureOrPresent
- More (H-specific) : @URL, @email, @CreditCardNumber, @Currency, @UUID
- ### Custom validator:
  - create custom Annotation1 and annotate with `@Constraint(validatedBy = Validator1.class)`
  - class Validator1 implements `ConstraintValidator`<Annotation1, String> // string:: TypeOfvalueBeingValidate 
  - Apply @Annotation1


