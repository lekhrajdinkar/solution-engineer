## CTEs (Common Table Expressions) :point_left:
- It improves the **readability / maintainability** of SQL code.
  - Simplifies complex queries.
- **temporary result** set that, can be referenced within a SELECT, INSERT, UPDATE, or DELETE statement
  - re-use result multiple times.
- can include **recursive definitions** for hierarchical data.
```
# =========== Syntax ==============

WITH cte_name (optional_column_names) AS (  -- explicit
    query_definition
),
WITH cte_name2  AS (  -- Inferred from the query
    query_definition2
)
SELECT * FROM cte_name;

# =========== example 1 ==============

WITH AverageSalary AS (
    SELECT AVG(salary) AS avg_salary
    FROM employees
)
SELECT name, salary
FROM employees, AverageSalary
WHERE salary > avg_salary;

# =========== example 2 ==============

WITH DepartmentSalary AS (
    SELECT department, AVG(salary) AS avg_salary
    FROM employees
    GROUP BY department
),
HighEarners AS (
    SELECT name, salary, department
    FROM employees
)
SELECT he.name, he.salary, ds.avg_salary
FROM HighEarners he
JOIN DepartmentSalary ds ON he.department = ds.department
WHERE he.salary > ds.avg_salary;

```

---
## Views
### Regular
- virtual and **recompute** their results every time query them.

### Materialized Views
- object that contains the results of a query and **stores** them physically on disk.
- improves performance 
- create **indexes** on materialized views to further optimize performance
- limitation:
  - Data is static until refreshed, which can lead to stale results. 
  - perform frequent refreshes. 
    - Note: use `pg_cron` for periodic refresh
  - Storage Overhead
- syntax :
```
CREATE MATERIALIZED VIEW view_name AS
SELECT query
WITH [NO] DATA;

DROP MATERIALIZED VIEW view_name;

REFRESH MATERIALIZED VIEW view_name WITH DATA;
REFRESH MATERIALIZED VIEW view_name WITH NO DATA; --> clean up
```

---
## Analyze Query
- **EXPLAIN ANALYZE** SELECT * FROM employees WHERE age > 30

---
## Partitioning

