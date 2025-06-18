# Common task
## Set Default value
- @Column(**columnDefinition** = "varchar(255) default 'John Snow'")
- Inside Entity class 
  - private String firstName = "John Snow";

## constraint - `Nullable`
- @Column(nullable=t/f)
- @Basic(optional=t/f)
- hibernate validator - `@NotNull` 
  - can apply on any bean, not just entity eg:jackson.

## Constraint - `unique` 
- @Column(unique=t/f) : Single column
- unique composite key:
  ```
  @Table(uniqueConstraints = {
     @UniqueConstraint(columnNames = { "personNumber", "isActive" }) ,
     @UniqueConstraint(columnNames = { "personNumber" }) 
   }, ...)
  ```