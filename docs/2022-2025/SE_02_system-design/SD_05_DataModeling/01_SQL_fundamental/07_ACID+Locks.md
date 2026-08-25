- https://chatgpt.com/c/22d9f577-17f2-4d43-9013-401b18ca58e0

--- 
# 1. ACID principle
- All DB has underlying solution for ACID
- https://www.youtube.com/watch?v=Sahvj-0UYxM

## Atomicity
- start txn
- unit of work 
- commit txn

---
## Consistency
takes data from one valid state to another valid state
- pk
- fk
- constraints

---
## ISOLATION 
isolation level
- the amount of data that is visible in a transaction
- when the other services access the same data simultaneously.
  
> **READ_UNCOMMITTED >> READ_COMMITTED >> REPEATABLE_READ >> SERIALIZABLE** 👈🏻

```sql
# postgres
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

SHOW default_transaction_isolation;  -- Typically "read committed"
ALTER SYSTEM SET default_transaction_isolation = 'repeatable read'; 
```

```Java
Connection conn = dataSource.getConnection();
conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
---
@Transactional(isolation = Isolation.READ_COMMITTED)
    public void standardOperation() {
        // ...
    }
```

### Dirty read
`no isolation`
- txn1 , txn2  -> both are reading/writing same record, same time.

### READ_UNCOMMITTED ✔️
`write-lock`
- txn-1 took w-lock first > performing write
- txn-2 waits for writing only
  - but not waiting for read
  - can still read 👈🏻
  - hence called read-uncommitted
- txn-1 done
- txn-2 took w-lock > performing write

### READ_COMMITTED  ✔️✔️
`read lock` `write lock`
- txn-1 took write-lock > performing write
- txn-2 has to take read-lock, once write-lock is release
- txn-1 released write-lock
- txn-2 took Read-lock > read
  
### REPEATABLE_READ ✔️✔️✔️
`version/sanpshot`
- txn-1 took w-lock > performing write. set **value-1**
- txn-2 waits
- txn-1 released w-lock
- txn-2 took R-lock >> Read **value-1** >> released lock
- txn-1 took w-lock >> performing write AGAIN to **value-2**
- txn-2 should read updated **value-2**

sol - read from **latest version**

  
### SERIALIZABLE ✔️✔️✔️✔️
`range lock` 
- Solves **phantom read**

---
## SUMMARY
```
Isolation_Level	    Dirty_Reads	    Non-Repeatable-Reads	Phantom-Reads
READ_UNCOMMITTED	✗	            ✗	                    ✗
READ_COMMITTED	    ✓	            ✗	                    ✗
REPEATABLE_READ	    ✓	            ✓	                    ✗
SERIALIZABLE	    ✓	            ✓	                    ✓
```

---
## Durability
- data never crashes

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



