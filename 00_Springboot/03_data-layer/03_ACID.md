- https://chatgpt.com/c/22d9f577-17f2-4d43-9013-401b18ca58e0

--- 
# ACID principle
- All DB has underlying solution for ACID

## Atomicity
- start txn
- unit of work 
- commit txn

---
## Consistency
- pk
- fk
- constraints

---
## ISOLATION 
- READ_UNCOMMITTED >> READ_COMMITTED >> REPEATABLE_READ >> SERIALIZABLE
- code
```Java
@Transactional(isolation = Isolation.READ_COMMITTED)
    public void standardOperation() {
        // ...
    }

SHOW default_transaction_isolation;  -- Typically "read committed"
ALTER SYSTEM SET default_transaction_isolation = 'repeatable read';  <<<
```

### 1. write lock (present default)
- **problem** : `no concurrency at all`
  - txn1 , txn2 --> both are writing same record same time.
- solution is `write-lock`
  - txn-1 took w-lock > performing write
  - txn-2 waits
  - txn-1 done
  - txn-2 took w-lock
  
### 2. read/write lock
- **problem** : `Dirty read` (READ_UNCOMMITTED) :left_point:
  - txn1  --> writing same record same time.
  - txn3 --> reading
- solution is `read/write lock` (READ_COMMITTED) :left_point:
    - txn-1 took w-lock > performing write
    - txn-3 waits
    - txn-1 done
    - txn-2 took R-lock >> read
  
### 3. version/sanpshot 
- **problem** : `no repeating read`
  - txn-1 took w-lock > performing write
  - txn-2 waits
  - txn-1 done
  - txn-2 took R-lock >> Read 
  - txn-1 took w-lock > performing write AGAIN :left_point:
  - txn-2 should read it again and get updated value.
- solution is `version/sanpshot` (REPEATABLE_READ) :left_point:
    - txn-2 will get latest from latest version
  
### 4. range lock 
- **problem** : `phantom read`
- solution - range lock (SERIALIZABLE) :left_point:

```
## SUMMARY ##

Isolation_Level	    Dirty_Reads	    Non-Repeatable-Reads	Phantom-Reads
READ_UNCOMMITTED	✗	            ✗	                    ✗
READ_COMMITTED	    ✓	            ✗	                    ✗
REPEATABLE_READ	    ✓	            ✓	                    ✗
SERIALIZABLE	    ✓	            ✓	                    ✓
```
```
# postgres
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

# jdbc
Connection conn = dataSource.getConnection();
conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
```
  
---
## Durability
- data never crashes