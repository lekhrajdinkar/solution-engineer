# postgres - functions
## 1 String Functions
| **Function**       | **Description**                     | **Usage Level**  | **Example**                          |
|---------------------|-------------------------------------|------------------|--------------------------------------|
| `length()`          | Get the length of a string         | Most Used        | `SELECT length('hello');`           |
| `concat()`          | Concatenate strings                | Most Used        | `SELECT concat('a', 'b', 'c');`     |
| `upper()`/`lower()` | Convert to upper/lowercase          | Most Used        | `SELECT upper('hello');`            |
| `strpos()`          | Find substring position            | Rarely Used      | `SELECT strpos('hello', 'e');`      |
| `regexp_replace()`  | Replace substring using regex      | Advanced Use     | `SELECT regexp_replace('123abc', '[a-z]', '');` |

---

## 2 Mathematical Functions
| **Function**    | **Description**                 | **Usage Level**  | **Example**                |
|------------------|---------------------------------|------------------|----------------------------|
| `abs()`         | Absolute value                 | Most Used        | `SELECT abs(-5);`          |
| `round()`       | Round to nearest integer       | Most Used        | `SELECT round(4.6);`       |
| `sqrt()`        | Square root                    | Rarely Used      | `SELECT sqrt(16);`         |
| `power()`       | Raise to a power               | Rarely Used      | `SELECT power(2, 3);`      |
| `random()`      | Generate random number         | Advanced Use     | `SELECT random();`         |

---

## 3 Date/Time Functions
| **Function**     | **Description**                  | **Usage Level**  | **Example**                        |
|-------------------|----------------------------------|------------------|------------------------------------|
| `now()`          | Current date/time               | Most Used        | `SELECT now();`                   |
| `age()`          | Difference between dates        | Most Used        | `SELECT age('2024-01-01');`       |
| `date_trunc()`   | Truncate to specified precision | Rarely Used      | `SELECT date_trunc('month', now());` |
| `to_char()`      | Format date/time                | Rarely Used      | `SELECT to_char(now(), 'YYYY-MM-DD');` |
| `interval`       | Add/subtract time intervals     | Advanced Use     | `SELECT now() + interval '1 day';` |

---

## 4 Aggregate Functions
| **Function**         | **Description**                 | **Usage Level** | **Example**                  |
|-----------------------|---------------------------------|-----------------|------------------------------|
| `COUNT(*)`           | Counts rows.                   | Most Used       | `COUNT(*) → 42`             |
| `SUM(value)`         | Sums values.                   | Most Used       | `SUM(salary) → 50000`       |
| `AVG(value)`         | Calculates average.            | Most Used       | `AVG(salary) → 5000`        |
| `MIN(value)`         | Finds minimum value.           | Most Used       | `MIN(salary) → 1000`        |
| `STRING_AGG(value, delimiter)` | Concatenates strings.| Rarely Used     | `STRING_AGG(name, ', ')`    |

---

## 5 Window Functions (skip) :red_circle:
| **Function**         | **Description**                      | **Usage Level** | **Example**                             |
|-----------------------|--------------------------------------|-----------------|-----------------------------------------|
| `ROW_NUMBER()`       | Assigns a unique row number.         | Most Used       | `ROW_NUMBER() OVER (PARTITION BY dept)` |
| `RANK()`            | Assigns rank with gaps.              | Most Used       | `RANK() OVER (ORDER BY salary DESC)`    |
| `LEAD(value)`        | Accesses subsequent row value.       | Advanced        | `LEAD(salary) OVER ()`                  |
| `LAG(value)`         | Accesses previous row value.         | Advanced        | `LAG(salary) OVER ()`                   |

---

## 6 JSON/JSONB Functions
| **Function**                        | **Description**                                              | **Usage Level** | **Example**                                               |
|-------------------------------------|-------------------------------------------------------------|-----------------|-----------------------------------------------------------|
| `->`                                | Accesses a JSON object field by key.                        | Most Used       | `json_column->'key' → "value"`                           |
| `->>`                               | Accesses a JSON object field as text.                       | Most Used       | `json_column->>'key' → value`                            |
| `#>`                                | Accesses a JSON object using a path.                        | Advanced        | `json_column#>'{path,key}' → "value"`                    |
| `#>>`                               | Accesses a JSON object field as text using a path.          | Advanced        | `json_column#>>'{path,key}' → value`                     |
| `JSONB_ARRAY_ELEMENTS(jsonb)`       | Expands a JSON array to a set of rows.                      | Advanced        | `JSONB_ARRAY_ELEMENTS('[1,2,3]') → 1, 2, 3`              |
| `JSON_BUILD_OBJECT(keys, values...)`| Builds a JSON object from key-value pairs.                  | Most Used       | `JSON_BUILD_OBJECT('a', 1, 'b', 2) → {"a":1,"b":2}`      |
| `JSONB_BUILD_ARRAY(values...)`      | Builds a JSONB array.                                       | Most Used       | `JSONB_BUILD_ARRAY(1, 2, 'a') → [1,2,"a"]`               |
| `JSONB_SET(jsonb, path, value)`     | Updates a JSONB object by setting a value at a path.        | Advanced        | `JSONB_SET('{"a":1}', '{a}', '2') → {"a":2}`             |
| `JSONB_INSERT(jsonb, path, value)`  | Inserts a value into a JSONB array at a specified path.     | Rarely Used     | `JSONB_INSERT('[1,2]', '{1}', '1.5') → [1,1.5,2]`        |
| `TO_JSON(value)`                    | Converts a SQL value to JSON.                               | Most Used       | `TO_JSON(ROW('a', 1)) → {"f1":"a","f2":1}`               |
| `JSON_EACH(json)`                   | Expands JSON object to key-value rows.                      | Rarely Used     | `JSON_EACH('{"a":1, "b":2}') → "a", 1 and "b", 2`        |
| `JSON_OBJECT(keys, values...)`      | Constructs a JSON object from text arrays.                  | Advanced        | `JSON_OBJECT(ARRAY['a','b'], ARRAY[1,2]) → {"a":1,"b":2}`|

### Example:
```
 SELECT '{"name": "John", "age": 30}'::json->'name' AS result; -- "John"

 SELECT '{"name": "John", "age": 30}'::json->>'age' AS result; -- "30", not 30
 
 SELECT JSONB_ARRAY_ELEMENTS('[10, 20, 30]'::jsonb) AS value; -- 10 \n 20 \n 30
 
 SELECT JSON_BUILD_OBJECT('name', 'Alice', 'age', 25) AS result;  -- {"name":"Alice","age":25}

```
---
## 7 Array Functions
| **Function**                | **Description**                                     | **Usage Level** | **Example**                             |
|-----------------------------|---------------------------------------------------|-----------------|-----------------------------------------|
| `ARRAY_APPEND(array, value)`| Appends an element to the end of the array.         | Most Used       | `ARRAY_APPEND(ARRAY[1, 2], 3) → {1,2,3}`|
| `ARRAY_PREPEND(value, array)`| Prepends an element to the beginning of the array.| Most Used       | `ARRAY_PREPEND(0, ARRAY[1, 2]) → {0,1,2}`|
| `ARRAY_CAT(array1, array2)` | Concatenates two arrays.                           | Most Used       | `ARRAY_CAT(ARRAY[1,2], ARRAY[3,4]) → {1,2,3,4}`|
| `CARDINALITY(array)`        | Returns the number of elements in the array.       | Most Used       | `CARDINALITY(ARRAY[1,2,3]) → 3`          |
| `ARRAY_REMOVE(array, value)`| Removes all occurrences of a value in the array.   | Rarely Used     | `ARRAY_REMOVE(ARRAY[1,2,2], 2) → {1}`    |
| `ARRAY_REPLACE(array, search, replace)` | Replaces occurrences of a value.    | Rarely Used     | `ARRAY_REPLACE(ARRAY[1,2,3], 2, 99) → {1,99,3}`|
| `UNNEST(array)`             | Expands an array into a set of rows.               | Advanced        | `UNNEST(ARRAY[1,2,3]) → 1, 2, 3`         |
| `ARRAY_POSITION(array, value)`| Returns the position of the first occurrence.   | Rarely Used     | `ARRAY_POSITION(ARRAY[10,20,30], 20) → 2`|
| `ARRAY_AGG(value)`          | Aggregates values into an array.                   | Advanced        | `ARRAY_AGG(column) → {val1, val2, ...}`  |
| `STRING_TO_ARRAY(string, delimiter)` | Splits a string into an array.          | Most Used       | `STRING_TO_ARRAY('a,b,c', ',') → {a,b,c}`|
| `ARRAY_TO_STRING(array, delimiter)` | Joins array elements into a string.      | Most Used       | `ARRAY_TO_STRING(ARRAY['a', 'b'], ',') → 'a,b'`|

---

## 8 Full-Text Search Functions
| **Function**               | **Description**                   | **Usage Level** | **Example**                         |
|-----------------------------|-----------------------------------|-----------------|-------------------------------------|
| `TO_TSVECTOR(text)`         | Converts text to tsvector.        | Rarely Used     | `TO_TSVECTOR('english', 'text')`   |
| `TO_TSQUERY(text)`          | Converts text to tsquery.         | Rarely Used     | `TO_TSQUERY('english', 'query')`   |
| `TS_RANK(vector, query)`    | Ranks search results.             | Advanced        | `TS_RANK(vector, query)`           |

---

## 9 Geometric Functions
| **Function**               | **Description**                   | **Usage Level** | **Example**                         |
|-----------------------------|-----------------------------------|-----------------|-------------------------------------|
| `POINT(x, y)`              | Creates a geometric point.         | Rarely Used     | `POINT(1, 2)`                      |
| `LENGTH(segment)`          | Calculates segment length.         | Rarely Used     | `LENGTH(LINE((0,0),(3,4))) → 5`    |

---

## 10 Network Functions
| **Function**               | **Description**                   | **Usage Level** | **Example**                         |
|-----------------------------|-----------------------------------|-----------------|-------------------------------------|
| `INET_CLIENT_ADDR()`        | Returns client IP.                | Rarely Used     | `INET_CLIENT_ADDR()`               |
| `HOSTMASK(address)`         | Returns network host mask.        | Advanced        | `HOSTMASK('192.168.1.0/24')`       |

---

## 11 System Information Functions
| **Function**               | **Description**                   | **Usage Level** | **Example**                         |
|-----------------------------|-----------------------------------|-----------------|-------------------------------------|
| `VERSION()`                | Returns PostgreSQL version.        | Most Used       | `VERSION()`                        |
| `PG_TABLE_SIZE(table)`     | Gets table size.                   | Advanced        | `PG_TABLE_SIZE('users')`           |
| `CURRENT_SETTING(name)`    | Returns configuration parameter.   | Advanced        | `CURRENT_SETTING('work_mem')`      |

---

## 12 Advanced and Rarely Used Functions
| **Function**               | **Description**                   | **Usage Level** | **Example**                         |
|-----------------------------|-----------------------------------|-----------------|-------------------------------------|
| `SETVAL(sequence, value)`  | Sets sequence value.              | Rarely Used     | `SETVAL('seq_name', 42)`           |
| `TS_HEADLINE(text, query)` | Generates search result snippet.  | Advanced        | `TS_HEADLINE('document', query)`   |

---