# Lock
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



