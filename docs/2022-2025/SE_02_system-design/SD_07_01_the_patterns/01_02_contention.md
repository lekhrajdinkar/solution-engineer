# Dealing with Contention
- https://www.hellointerview.com/learn/system-design/patterns/dealing-with-contention

## Terms
- **Contention** : it occurs when multiple processes compete for the same resource at the same time
- race conditions, 
- deadlocks, 
- locks(tied with txn) ,
- distributed locks (leased lock with TTl, outside DB)

---
## 1. Conditional Writes
### problem🔺 : `lost update`
Single row locking :: buying concert tickets online. user1 and user2
- last 1 ticket scenario, `A15`
- decrementing a counter, flipping a status

### First solution 
- non-atomic and no conditional write

```sqlite-psql
-- STEP-1 Read the current count
SELECT available_seats FROM concerts WHERE concert_id = 'weeknd_tour';

-- below must be atomic ?
    -- STEP-2 The app checks available_seats > 0, then writes the new value back
    UPDATE concerts
    SET available_seats = available_seats - 1
    WHERE concert_id = 'weeknd_tour';
    
    -- STEP-3 
    INSERT INTO booking (user_id, concert_id, seat_number, purchase_time) 
    values ('user-1', 'weeknd_tour', 'A15', 'xxxx-xx-xx:xx:xx:xxxx')
```
both user-1/2 will end up buying same ticket with solution-1
- read and write aren't atomic, are two separate steps, and in between them the world can change
- The window is tiny, microseconds in memory and milliseconds over a network
- problem only gets worse when you scale. even small race condition windows create massive conflicts

### Next, Add Conditional Writes + wrap with transaction
- Decrement the count, but only if a seat is left
- database won't let two updates change the same row at the same time.
-  two writes that need to stick together, so wrap the both operations in BEGIN and COMMIT

```sqlite-psql
BEGIN TRANSACTION; -- 👈

    WITH reservation AS (
      -- STEP-1
      UPDATE concerts
      SET available_seats = available_seats - 1
      WHERE concert_id = 'weeknd_tour'
        AND available_seats > 0 -- 👈
      RETURNING concert_id
    )
    
    -- STEP-2 : INSERT only if got available seat -- 👈
    INSERT INTO booking (user_id, concert_id, seat_number, purchase_time)
    SELECT 'user123', concert_id, 'A15', NOW()
    FROM reservation;

COMMIT;
```

### next, Guarding the "resource", with conditional Write
> - **counter** can answer "is there a ticket left."
> - **Only the ticket** row can answer "is this ticket left."

- Assuming, last 10 ticket scenario, `A15-A25`are last 10.
- with above conditional logic `available_seats > 0`, both user could still endup buying same `A15`
- **fix is to guard the resource/seat**
- Give every ticket its own row, and **aim the exact same conditional write**


```sqlite-psql
UPDATE tickets
SET status = 'sold', user_id = 'user123'
WHERE concert_id = 'weeknd_tour'
  AND seat_number = 'A15' -- 👈
  AND status = 'available';-- 👈
```

---
### Summary

> 💡As long as the database **can evaluate your guard as part of the write**, you're done.
> - The `WHERE clause` === compare-and-set, provides **optimistic concurrency check**
> - the same move, DynamoDB makes with a ConditionExpression, 
> - Redis with SET NX, 
> - Cassandra with a lightweight transaction, 
> - or, an HTTP API with an If-Match header.

---
## 2. Pessimistic Locking
> Pessimistic locking prevents conflicts by acquiring locks upfront.

### problem🔺 : `read-decide-write`
multi-row Lock
- Say a group of four wants to sit together.
- find four open seats in a row,
- and only then claim them.


```sqlite-psql
-- The catch is that **you've locked every open seat** , just to claim four.

BEGIN TRANSACTION;

    -- Lock the open seats in this section while we pick a block
    SELECT seat_number FROM seats
    WHERE concert_id = 'weeknd_tour'
      AND section = 'floor'
      AND status = 'available'
    FOR UPDATE;
    
    -- App scans the result, finds A15-A18 open and adjacent, then claims them
    UPDATE seats
    SET status = 'sold', user_id = 'user123'
    WHERE concert_id = 'weeknd_tour'
      AND seat_number IN ('A15', 'A16', 'A17', 'A18');

COMMIT;
```

---
## 3. Optimistic Concurrency Control
### problem🔺 : `read-decide-write` (but care collisions)

> OCC = don't lock while reading; detect conflict when writing.

| Approach                | What happens                                                                 |
| ----------------------- | ---------------------------------------------------------------------------- |
| **Pessimistic locking** | "I'll lock it now so nobody else can touch it."                              |
| **OCC**                 | "Everyone can proceed; I'll check at write time whether someone changed it." |

```
⭐== common implementation ==

SQL       → version column
HTTP      → ETag + If-Match
etcd      → revision
DynamoDB  → version attribute
```

```sqlite-psql
-- Both Alice and Bob read: 1 seat, version 42 --👈

-- Alice writes first:
BEGIN TRANSACTION;
UPDATE concerts
SET available_seats = available_seats - 1, version = version + 1 
WHERE concert_id = 'weeknd_tour'
  AND version = 42;  -- the version Alice read --👈

INSERT INTO tickets (user_id, concert_id, seat_number, price, purchase_time)
VALUES ('alice', 'weeknd_tour', 'A15', 750.00, NOW());
COMMIT;
-- Succeeds. seats = 0, version = 43

-- Bob writes against the version he read:
BEGIN TRANSACTION;
UPDATE concerts
SET available_seats = available_seats - 1, version = version + 1
WHERE concert_id = 'weeknd_tour'
  AND version = 42;  -- stale, the row is on 43 now --👈

-- Bob's UPDATE matches 0 rows. Check the count, roll back, skip the insert.
ROLLBACK;
```

---
## 4. Isolation Levels
### problem🔺 : `write skew`
> Write skew is a concurrency problem where two transactions read the same state, then update different rows, causing a business rule to be violated.

```
>  business rule:: At least one of two accounts must have money.
---
Account A: $100
Account B: $100
---

Transaction 1                     Transaction 2
(User-1 withdraws A)              (User-2 withdraws B)

Read A = $100                     Read A = $100
Read B = $100                     Read B = $100

"I can withdraw from A"           "I can withdraw from B"

UPDATE A → $0                     UPDATE B → $0

---

Both transactions think:
"The other account still has $100, so we're safe."

---
but  Final state:
Account A: $0
Account B: $0

```

```mermaid

sequenceDiagram
    participant A as Alice Transaction
    participant DB as Database
    participant B as Bob Transaction

    Note over DB: Initial state:<br/>Alice = ON_CALL<br/>Bob = ON_CALL<br/>Rule: At least 1 must be ON_CALL

    A->>DB: Read schedule
    DB-->>A: Alice + Bob are ON_CALL
    A->>A: Bob is still ON_CALL<br/>I can step down

    B->>DB: Read schedule
    DB-->>B: Alice + Bob are ON_CALL
    B->>B: Alice is still ON_CALL<br/>I can step down

    A->>DB: UPDATE Alice → OFF
    DB-->>A: Success

    B->>DB: UPDATE Bob → OFF
    DB-->>B: Success

    Note over DB: ❌ Final state:<br/>Alice = OFF<br/>Bob = OFF<br/><br/>Rule violated!

```

- [isolation level](../SD_05_DataModeling/01_SQL_fundamental/07_ACID.md)
- They control how much one transaction can see(**READ**) of another's in-flight work.
- SERIALIZABLE is the one that does catch skew write.
- `BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE; ...`

| Isolation Level      | What you can see                                    | Key point                               |
| -------------------- | --------------------------------------------------- | --------------------------------------- |
| **READ UNCOMMITTED** | Can see uncommitted changes                         | Rarely used; allows dirty reads         |
| **READ COMMITTED**   | Only committed changes                              | Default in **PostgreSQL**               |
| **REPEATABLE READ**  | Same data read multiple times within a transaction stays consistent | Default in **MySQL (InnoDB)**           |
| **SERIALIZABLE**     | Transactions behave as if executed one at a time    | Strongest isolation; lowest concurrency |

![img.png](draw/img3.png)

![img.png](draw/img_1.png)

---
## 5. Distributed Locks
### problem🔺: Exclusive locks
- [03_03_distributed-Locking.md](../SD_05_DataModeling/02_basic_concepts/03_03_distributed-Locking.md)

---
## ✔️Equivalents outside SQL database

| Technique                  | In SQL                                    | The same move elsewhere                                                                            |
| -------------------------- | ----------------------------------------- | -------------------------------------------------------------------------------------------------- |
| **Conditional write**      | `WHERE` predicate on the write            | DynamoDB `ConditionExpression`, Redis `SET NX`, Cassandra lightweight transaction, HTTP `If-Match` |
| **Optimistic concurrency** | Version column with `WHERE version = ...` | HTTP ETags / `If-Match`, etcd revision, DynamoDB version attribute                                 |
| **Pessimistic locking**    | `SELECT ... FOR UPDATE`                   | A mutex or distributed lock held while you decide                                                  |
| **Serializable isolation** | `ISOLATION LEVEL SERIALIZABLE`            | Mostly relational only                           |
| **Distributed lock**       | Reservation row with a TTL                | Redis `SET NX EX`, ZooKeeper or etcd lease                                                         |

---
## ✔️Approach /summary
> Like with much of system design, there isn't always a clear-cut answer. You'll need to consider the tradeoffs of each approach based on your specific use case

**single-home assumption**
  - If you need strong consistency under high contention, 
  - keep the contended resource in **one authoritative store**
  - do everything you can to keep the relevant data in a **single database first.**
  - **Two situations** break the single-home assumption
    - 1 distributed transaction
    - 2 same record is writable in multiple places at once,  and you're into conflict resolution:
    - like last-write-wins, vector clocks, or CRDTs

| Approach                   | Use When                                                                      | Avoid When                                                   | Typical Latency                                    | Complexity |
| -------------------------- | ----------------------------------------------------------------------------- | ------------------------------------------------------------ | -------------------------------------------------- | ---------- |
| **Conditional Write**      | Your check is a predicate on the row you're writing (counter, status, claim)  | The decision needs app logic or spans other rows             | Low — one atomic statement                         | Low        |
| **Pessimistic Locking**    | Read-decide-write where the check isn't a `WHERE` predicate; high contention  | Low contention, or a conditional write already covers it     | Low per operation, but holds a lock others wait on | Low        |
| **Optimistic Concurrency** | Same read-decide-write, but collisions are rare; high read/write ratio        | High contention where retries pile up                        | Low when no conflict; retry cost on conflict       | Medium     |
| **Serializable Isolation** | Write skew or a cross-row invariant with no single row to lock                | Hot, high-contention paths where abort/retry cost is high    | Medium — conflict tracking                         | Medium     |
| **Distributed Locks**      | Exclusivity must span a wait, external call, or multiple steps (reservations) | A single-row guard inside one transaction already handles it | Low for simple status writes                       | Medium     |


```mermaid
flowchart TD
    Start{"What is your update / write pattern?"}
    Start -->|"Target row predicate only<br>(Counter, status flip, claiming)"| A["<b>Conditional UPDATE</b><br>• <code>UPDATE ... WHERE ...</code><br>• Atomic & simplest; no locks needed"]
    Start -->|"Multi-step read-decide-write"| Contention{"Write collision / contention frequency?"}
    Contention -->|"Common / High contention"| B["<b>Pessimistic Locking</b><br>• <code>SELECT ... FOR UPDATE</code><br>• Holds row lock; queues threads"]
    Contention -->|"Rare / Low contention"| C["<b>Optimistic Concurrency Control (OCC)</b><br>• Version counter / Timestamp<br>• Validate on commit & retry"]
    Start -->|"Invariant spans unrelated rows<br>(Write skew anomalies)"| D["<b>Serializable Isolation</b><br>• Or materialize invariant to a single lockable row"]
    Start -->|"Lock outlives single DB transaction<br>(Checkout flows, external API calls)"| E["<b>Distributed Lock / Reservation</b><br>• TTL-based lock or state machine<br>• Coordinates slow & multi-step steps"]

```
---
## ✔️Deep Dive
### ABA problem
### Handle DeadLock
### Performance
> performance when everyone wants the same resource

---
## ✔️interview