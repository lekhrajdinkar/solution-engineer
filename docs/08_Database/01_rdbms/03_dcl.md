## DCL 
- Data control language
- Role and permission management
- enabling fine-grained control over database access and operations.
- FORMAT : **GRANT/REVOKE** (allw/deny) `privileges (action/verbs)` on `resource` to `role` :point_left:
- thinks of IAM policy in aws, k8s RBAC, etc

## User
```
CREATE USER bob WITH PASSWORD 'password456' CREATEDB CREATEROLE;
CREATE USER alice WITH PASSWORD 'password123';
CREATE USER admin WITH PASSWORD 'adminpassword' SUPERUSER;
```

## Role
```
CREATE ROLE admin SUPERUSER;
CREATE ROLE user_role;

CREATE ROLE app_r;
CREATE ROLE app_rw;
CREATE ROLE app_rwx;

CREATE ROLE userAsRole WITH LOGIN PASSWORD 'secure_password';
```

## Attributes
- create role and user with **attributes**. 
- can alter role add/remove attribute.
- check attributes, run : **\du**
```
    LOGIN  | NOLOGIN   : Enables the role to log in as a user.
    SUPERUSER |  NOSUPERUSER : Grants all privileges.
  
    CREATEDB | NOCREATEDB : Allows the role to create databases.
    CREATEROLE | NOCREATEDB : Allows the role to create and manage other roles.
  
    INHERIT | NO*** : Allows a role to inherit privileges from granted roles.
    NOINHERIT | NO***   : Prevents privilege inheritance.
    REPLICATION | NO*** : Grants the ability to manage streaming replication.
    PASSWORD : sets login password
    
    -- add "NO" prefix to remove. eg: NOLOGIN 
```

## User
```
CREATE USER bob WITH PASSWORD 'password456' CREATEDB CREATEROLE;
CREATE USER alice WITH PASSWORD 'password123';
CREATE USER admin WITH PASSWORD 'adminpassword' SUPERUSER;

ALTER ROLE admin WITH SUPERUSER;
ALTER ROLE user_role WITH LOGIN NOINHERIT; -- NO
ALTER ROLE user_role NOLOGIN;  -- NO

-->  role inheritance. eg: user_role inherits admin privileges
GRANT admin TO user_role; 
REVOKE admin FROM user_role;

```
## Privileges
- like **verbs** in k8s 
- like **actions** in aws iam
- Resources :  **schema**, table , view (regular/ materialized ), function, SP, etc
```
  ALL    : Grants all privileges.
  SELECT : Permission to query data.
  INSERT : Permission to add data.
  UPDATE : Permission to modify data.
  DELETE : Permission to remove data.
  
  USAGE  : Grants access to schemas or sequences.
  CONNECT: Permission to connect to the database.
  
  EXECUTE: SP,Fn

======================================

-- db
GRANT CONNECT ON DATABASE mydb TO alice;

-- schema
GRANT USAGE ON SCHEMA public TO user_role;
GRANT CREATE ON SCHEMA public TO admin;

-- table
GRANT ALL ON TABLE employees TO admin; --admin is role
GRANT SELECT, INSERT ON TABLE employees TO user_role;
REVOKE DELETE ON TABLE employees FROM user_role;

-- column level
GRANT SELECT (salary) ON employees TO user_role;

-- function level
GRANT EXECUTE ON FUNCTION calculate_bonus() TO admin;

-- ==== IMP: privelges on future object/table ====                <<< 
ALTER DEFAULT PRIVILEGES IN SCHEMA public  -- add this list first
GRANT SELECT ON TABLES TO user_role;

```
