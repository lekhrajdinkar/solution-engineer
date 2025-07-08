## DML 🟢

###  A. INSERT
```
```
---

###  B. UPDATE
```
```
---

###  C. DELETE
```
```
---

## DQL : Data query language
###  SELECT
```
# 1. JSONB
CREATE TABLE products ( id SERIAL PRIMARY KEY,data JSONB );
INSERT INTO products (data) VALUES ('{"name": "Laptop", "price": 1200}');

SELECT data->>'name', data->>'price' FROM products WHERE data->>'name' = 'Laptop';

```

###  Subqueries
```
```

###  Aggregation function
```
```
