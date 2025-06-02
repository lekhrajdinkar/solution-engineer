# A. Joins
## 1. inner
## 2. outer
  - left outer
  - right outer

## 3. Cross
- Results in m × n rows (where m and n are row counts of each table)
- every row from the first table is combined with every row from the second table
- No join condition is specified

## self join
- special case: regular join (inner, outer, cross) where a table is **joined with itself**.
- it's useful for querying hierarchical data or comparing rows within the same table
- Commonly used eg:
  - Employee-manager relationships
  - Bill of materials (parent-child relationships)
  - Finding duplicate records
```SQL
SELECT e.employee_name, m.employee_name AS manager_name
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.employee_id
```
---
# Queries
## Sub queries
## co related queries
## recursive queries


