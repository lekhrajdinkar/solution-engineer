- https://chatgpt.com/c/22d9f577-17f2-4d43-9013-401b18ca58e0

--- 
# 1. ACID principle
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

---

---
# 2. Lock
## optimistic Locks
- read TS, Write TS, etc (TS=timestampe and version)
- add in entity : `@Version` private long version;
- `ObjectOptimisticLockingFailureException`
- **Advantages**
  - Better performance than pessimistic locking
  - No database locks held :point_left:
  - Works well for low-contention scenarios
  - Suitable for web applications with short transactions

## pessimistic Locks (postgresQL)
### Row level lock
- **mechanism**
  - SELECT FOR UPDATE (Row-Level Write Lock)
  - SELECT FOR SHARE (Row-Level Read Lock)
  - SELECT FOR NO KEY UPDATE (Weaker Write Lock)
  - SELECT FOR KEY SHARE (Weakest Lock)

- **Locking Options**
  - **NOWAIT** : Fails immediately if lock cannot be acquired
  - SKIP LOCKED :  Skips already locked rows

```sql
BEGIN;
SELECT * FROM accounts WHERE id = 1 FOR UPDATE;
-- The row is now locked for updates by other transactions
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
COMMIT;

BEGIN;
SELECT * FROM accounts WHERE id = 1 FOR SHARE;
-- Other transactions can read but cannot update this row
COMMIT;

BEGIN;
SELECT * FROM customers WHERE id = 1 FOR NO KEY UPDATE;
-- Locks row but allows updates on non-key columns
COMMIT;

BEGIN;
SELECT * FROM orders WHERE id = 1 FOR KEY SHARE;
-- Only prevents key changes
COMMIT;

```

### table level lock
```sql
BEGIN;
LOCK TABLE accounts IN ACCESS EXCLUSIVE MODE;
-- Prevents all access to the table
COMMIT;
```
- more(extra)
  - **ACCESS SHARE** - Weakest lock, acquired automatically by SELECT queries (only conflicts with ACCESS EXCLUSIVE).
  - **ROW SHARE** - Acquired by SELECT FOR SHARE, allows concurrent reads but blocks exclusive writes.
  - **ROW EXCLUSIVE** - Acquired automatically by UPDATE/DELETE/INSERT (blocks SHARE, SHARE ROW EXCLUSIVE, EXCLUSIVE, ACCESS EXCLUSIVE).
  - **SHARE UPDATE EXCLUSIVE** - Used by VACUUM/ANALYZE, blocks same mode and stronger (except ACCESS SHARE).
  - **SHARE** - Acquired by CREATE INDEX, allows concurrent reads but blocks all writes (conflicts with ROW EXCLUSIVE and stronger).
  - **SHARE ROW EXCLUSIVE** - Rarely used explicitly, blocks SHARE and same mode.
  - **EXCLUSIVE** - Blocks all concurrent writes and SHARE locks (only allows ACCESS SHARE reads).
  - **ACCESS EXCLUSIVE** - Strongest lock, acquired by ALTER TABLE/DROP TABLE, blocks all operations

### pg_lock (Monitor)
```
SELECT locktype, relation::regclass, mode, pid
FROM pg_locks
WHERE relation = 'accounts'::regclass;
```



