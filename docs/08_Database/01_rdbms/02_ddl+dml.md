# DDL :green_circle:
- define, modify, and manage the **structure** of database.
- **Implicitly commits** changes
## A. Schemas
- **search path** : determines the order in which schemas are searched when a table, view, function, or other database objects are referenced by name
```
CREATE SCHEMA hr;
DROP SCHEMA IF EXISTS hr CASCADE;
SET search_path TO hr;
```
---
## B Table and view
```
CREATE TABLE employees (...);
DROP TABLE employees;

-- column
ALTER TABLE employees [ ADD | DROP ] COLUMN department VARCHAR(50);     -- new column
ALTER TABLE employees ALTER COLUMN name SET DATA TYPE TEXT;             -- datatype change
ALTER TABLE employees ALTER COLUMN age SET DATA TYPE BIGINT;

ALTER TABLE employees ALTER COLUMN name SET NOT NULL;               -- NOT NULL
ALTER TABLE employees ALTER COLUMN name DROP NOT NULL;

ALTER TABLE employees ALTER COLUMN name SET DEFAULT 'Unknown';      -- DEFAULT
ALTER TABLE employees ALTER COLUMN name DROP DEFAULT;

-- constraint 
ALTER TABLE employees DROP CONSTRAINT constraint-1;             -- DROP constarint

ALTER TABLE employees ADD CONSTRAINT unique_email UNIQUE (email);                       -- unique
ALTER TABLE employees ALTER COLUMN name SET NOT NULL;                                   -- not null
ALTER TABLE employees ADD CONSTRAINT chk_hire_date CHECK (hire_date >= '2000-01-01');   -- CHECK
ALTER TABLE employees ADD CONSTRAINT pk_employee_id PRIMARY KEY (id);                   -- pk
ALTER TABLE employees ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id)       -- fk
    REFERENCES departments(id) ON DELETE CASCADE;
```
```
-- ========== VIEW ==========
CREATE OR REPLACE VIEW active_employees AS
    SELECT id, name FROM employees WHERE active = TRUE;
    
DROP VIEW IF EXISTS active_employees;
```

---
## C Indexes
- improve query performance, but take up additional disk space.
  - **speed up** SELECT queries
  - **slow down** INSERT, UPDATE, DELETE,  as the index must be updated whenever the data changes.
  
- internal `data structure` that provides quick access to rows : 
  - `B-tree index`, 
  - `Hash Index`, 
  - `GIN (Generalized Inverted Index)` - useful for indexing composite types like arrays, JSONB
  -  `GiST (Generalized Search Tree)`
  
- best use case:
  - **Frequent Query Filtering**: When you regularly run queries that filter on specific columns.
  - **Join Operations**: When you perform join queries based on indexed columns.
  - **Range Queries**: When you frequently query for ranges (e.g., `date` ranges).

```
CREATE INDEX idx_name ON employees(name);               -- Regular Index (Non-Unique)
CREATE UNIQUE INDEX idx_unique_name ON employees(name); -- Unique Index : adds overhead for enforcing uniqueness

DROP INDEX IF EXISTS idx_name;
```

## D Sequence
```
CREATE SEQUENCE seq_emp_id START WITH 1 INCREMENT BY 1;
DROP SEQUENCE IF EXISTS seq_emp_id;
    
    CREATE TABLE employees (
    id INT DEFAULT nextval('seq_emp_id'),   -- <<< 
    name VARCHAR(100)
    );
```

## E extension
- `add-ons` that extend PostgreSQL’s functionality.
- System-wide, available to all schemas.
- These can provide new:
  - data-types 
  - functions 
  - operators 
  - procedural languages ?
  
- Common pre-existing Extensions/add-on:
  - **pg_stat_statements**: Tracks SQL execution statistics. 
  - **hstore**: Enables key-value storage within PostgreSQL. === datatype
  - **postgis**: Adds support for geographic objects. 
  - **uuid-ossp**: Generates UUIDs.
- creating/install custom extension, involve several step. skip. for db admin.  
```
-- Install an Extension : Adding support for UUID generation.
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP EXTENSION IF EXISTS "uuid-ossp";
```

## F triggers
- automatically execute :
  - specified function 
  - in response to certain events (such as INSERT, UPDATE, or DELETE) occurring on a table/view
```
CREATE TRIGGER after_employee_update
    AFTER    INSERT OR UPDATE OR DELETE      ON employees
    FOR EACH ROW
        EXECUTE FUNCTION log_employee_update();
```
---
## G Function
```
CREATE FUNCTION my_function(integer) RETURNS integer AS
$$
BEGIN
    RETURN $1 * $1;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION my_other_function(text) RETURNS text AS
$$
BEGIN
    RETURN CONCAT('Hello, ', $1);
END;
$$ LANGUAGE plpgsql;
```

---
## H Stored procedure
```
```

---

# DML :green_circle:

## A. INSERT
```
```
---

## A. UPDATE
```
```
---

## A. DELETE
```
```
---

## A. SELECT
```
# 1. JSONB
CREATE TABLE products ( id SERIAL PRIMARY KEY,data JSONB );
INSERT INTO products (data) VALUES ('{"name": "Laptop", "price": 1200}');

SELECT data->>'name', data->>'price' FROM products WHERE data->>'name' = 'Laptop';


```