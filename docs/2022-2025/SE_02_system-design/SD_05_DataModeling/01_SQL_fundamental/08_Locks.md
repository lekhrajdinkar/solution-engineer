# Database Lock
## Optimistic Locks | OCC
- col: `updated_at`, `version`
- java: `@Version` private long version;
- `ObjectOptimisticLockingFailureException`
- **Advantages**
  - Better performance than pessimistic locking
  - No database locks held :point_left:
  - Works well for low-contention scenarios
  - Suitable for web applications with short transactions

---
## Pessimistic Locks (postgresQL)
### Row level lock

**Lock options**
- `NOWAIT`: Fails immediately if lock cannot be acquired
- `SKIP LOCKED` :  Skips already locked rows

| Lock                | Purpose           | What it allows                                                                         |
| ------------------- | ----------------- | -------------------------------------------------------------------------------------- |
| `FOR UPDATE`        | Strong write lock | Prevents other transactions from updating or locking the row for conflicting purposes  |
| `FOR NO KEY UPDATE` | Weaker write lock | Prevents key-related changes by others, while being less restrictive than `FOR UPDATE` |
| `FOR SHARE`         | Read/share lock   | Other transactions can read/share the row, but conflicting updates are blocked         |
| `FOR KEY SHARE`     | Weakest lock      | Mainly protects the row's key from changes/deletion                                    |



```sqlite-psql
BEGIN;
    SELECT * FROM accounts WHERE id = 1 
    FOR UPDATE; -- 👈
    -- The row is now locked for updates by other transactions
    UPDATE accounts SET balance = balance - 100 WHERE id = 1;
COMMIT;

BEGIN;
    SELECT * FROM accounts WHERE id = 1 
    FOR SHARE; -- 👈
    -- Other transactions can read but cannot update this row
COMMIT;
    
    BEGIN;
    SELECT * FROM customers WHERE id = 1 
    FOR NO KEY UPDATE; -- 👈
    -- Locks row but allows updates on non-key columns
COMMIT;

BEGIN;
    SELECT * FROM orders WHERE id = 1 
    FOR KEY SHARE; -- 👈
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

### More

| Lock                       | Typical use                              | Effect                                                            |
| -------------------------- | ---------------------------------------- | ----------------------------------------------------------------- |
| **ACCESS SHARE**           | `SELECT`                                 | Weakest; conflicts only with `ACCESS EXCLUSIVE`                   |
| **ROW SHARE**              | `SELECT FOR SHARE` / `SELECT FOR UPDATE` | Allows concurrent reads; conflicts with stronger table locks      |
| **ROW EXCLUSIVE**          | `INSERT`, `UPDATE`, `DELETE`             | Blocks conflicting table-level locks such as `SHARE` and stronger |
| **SHARE UPDATE EXCLUSIVE** | `VACUUM`, `ANALYZE`                      | Blocks concurrent schema/maintenance operations                   |
| **SHARE**                  | `CREATE INDEX`                           | Allows reads; blocks writes                                       |
| **SHARE ROW EXCLUSIVE**    | Rare / explicit use                      | Blocks `SHARE` and stronger conflicting locks                     |
| **EXCLUSIVE**              | Rare / explicit use                      | Blocks writes and `SHARE`-type locks; allows ordinary reads       |
| **ACCESS EXCLUSIVE**       | `ALTER TABLE`, `DROP TABLE`              | **Strongest**; blocks all other table-level access                |


---
## pg_lock (Monitor)
```
SELECT locktype, relation::regclass, mode, pid
FROM pg_locks
WHERE relation = 'accounts'::regclass;
```



