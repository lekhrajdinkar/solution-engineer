# Postgres
## kick off - reference/s
- All - https://chatgpt.com/c/674bee20-d9e4-800d-a31e-8f60a5f511ab
  - core concept: https://chatgpt.com/c/674d6fa4-7e4c-800d-9d48-54f566791d8d
  - ddl/dml : https://chatgpt.com/c/674df644-1984-800d-870c-ccee5069b8e9
  - function: https://chatgpt.com/c/674df7ca-0ed8-800d-a8bf-9404d3ec216d

---
## A. Architecture
### Process/s
- operates with a **process-based architecture**,
- **Backend Processes**
  - for each `client` connection.
  - Handles queries, transactions, and data operations.
- **Postmaster Process** (Main Server Process)
  - Manages connections and spawns new backend processes for each client.
  - Coordinates system-level activities, like `crash recovery`.
- **Background Processes**
  - `WAL Writer`: Writes WAL (Write-Ahead Logging) data to disk.
  - `Checkpointer`: Periodically writes dirty pages from shared buffers to disk.
  - `Archiver`: Archives WAL files for point-in-time recovery (if enabled).
  - `Stats Collector`: Gathers performance and usage statistics.
- **Replication Processes**
  - WAL Sender: Streams WAL data to replicas for replication.
  - WAL Receiver: Receives WAL data on standby servers.
---
## B. Transaction
- `BEGIN`
- `COMMIT`
- `SAVEPOINT` save-point-1,2,...
- `ROLLBACK` save-point-1,2,..
  - **SET TRANSACTION ISOLATION LEVEL SERIALIZABLE**; default: `read committed`
- Best practice:
  - Use transactions for any operation involving multiple steps.
  - Implement appropriate isolation levels based on concurrency requirements.
  - Use savepoints for complex transactions to handle partial rollbacks.
  - Regularly monitor transaction performance and concurrency issues.

---
## C. Configuring and tuning
- /etc/postgresql/{version}/main/postgresql.conf (on Linux) 
- data/postgresql.conf (on Windows). 
  - max_connections = 100
  - ...
- SELECT * FROM `pg_stat_activity`;
- SELECT * FROM `pg_stat_statements`;
- Use **EXPLAIN** and **EXPLAIN ANALYZE** to understand query performance and add appropriate indexes.
  