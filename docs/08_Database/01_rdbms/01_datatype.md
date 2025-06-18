# postgres - Datatypes

## Numeric Types

| PostgreSQL Data Type | Java Equivalent Data Type | Example Values     |
|----------------------|---------------------------|--------------------|
| `SMALLINT`           | `short`                   | `1, 2, -32768`     |
| `INTEGER` (`INT`)    | `int`                     | `100, -2147483648` |
| `BIGINT`             | `long`                    | `9223372036854775807` |
| `DECIMAL`/`NUMERIC`  | `java.math.BigDecimal`    | `10.99, -100.5`    |
| `REAL`               | `float`                   | `3.14, 1e10`       |
| `DOUBLE PRECISION`   | `double`                  | `3.14159, 1.2e308` |
| `SERIAL`             | `int`                     | `1, 2, 3`          |
| `BIGSERIAL`          | `long`                    | `1, 2, 3`          |

## Character/String Types

| PostgreSQL Data Type | Java Equivalent Data Type | Example Values        |
|----------------------|---------------------------|-----------------------|
| `CHAR(n)`            | `String`                  | `'John  '` (5 chars)  |
| `VARCHAR(n)`         | `String`                  | `'Hello'` (up to `n`) |
| `TEXT`               | `String`                  | `'This is a text.'`   |

## Date/Time Types

| PostgreSQL Data Type | Java Equivalent Data Type       | Example Values                   |
|----------------------|---------------------------------|-----------------------------------|
| `DATE`               | `java.sql.Date`                 | `2024-12-01`                     |
| `TIME [WITH TZ]`     | `java.time.LocalTime` (or `OffsetTime` for TZ) | `13:45:30`, `13:45:30+05:30`    |
| `TIMESTAMP [WITH TZ]`| `java.time.LocalDateTime` (or `OffsetDateTime` for TZ) | `2024-12-01 13:45:30+05:30` |
| `INTERVAL`           | `java.time.Duration`            | `1 day`, `3 hours 15 minutes`    |

## Boolean Type

| PostgreSQL Data Type | Java Equivalent Data Type | Example Values |
|----------------------|---------------------------|----------------|
| `BOOLEAN`            | `boolean`                  | `true, false`  |

## Binary Types

| PostgreSQL Data Type | Java Equivalent Data Type      | Example Values   |
|----------------------|--------------------------------|------------------|
| `BYTEA`              | `byte[]`                       | `new byte[] {0xDE, 0xAD, 0xBE, 0xEF}` |

## JSON and JSONB Types

| PostgreSQL Data Type | Java Equivalent Data Type       | Example Values                             |
|----------------------|---------------------------------|-------------------------------------------|
| `JSON`               | `String`                       | `'{"key": "value"}'`                      |
| `JSONB`              | `String`                       | `'{"key": "value"}'`                      |

## Array Types

| PostgreSQL Data Type | Java Equivalent Data Type        | Example Values       |
|----------------------|----------------------------------|----------------------|
| `ARRAY`              | `Object[]` (or specific type `Integer[]`, `String[]`, etc.) | `{1, 2, 3}`, `{A, B}` |

## Geometric Types (skip)

| PostgreSQL Data Type | Java Equivalent Data Type       | Example Values               |
|----------------------|---------------------------------|-----------------------------|
| `POINT`              | `java.awt.Point`                | `new Point(1, 2)`           |
| `LINE`               | `String` (representation of a line) | `'{1, 2, 3}'               |
| `POLYGON`            | `String` (representation of a polygon) | `'((0,0), (1,1), (2,0))'` |

## Network Address Types

| PostgreSQL Data Type | Java Equivalent Data Type      | Example Values             |
|----------------------|--------------------------------|----------------------------|
| `INET`               | `java.net.InetAddress`         | `'192.168.1.1'`           |
| `CIDR`               | `java.net.InetAddress`         | `'192.168.1.0/24'`        |
| `MACADDR`            | `String`                       | `'08:00:2b:01:02:03'`     |

## UUID Type

| PostgreSQL Data Type | Java Equivalent Data Type      | Example Values                           |
|----------------------|--------------------------------|------------------------------------------|
| `UUID`               | `java.util.UUID`               | `UUID.fromString("550e8400-e29b-41d4-a716-446655440000")` |

## Other Special Types

| PostgreSQL Data Type | Java Equivalent Data Type      | Example Values          |
|----------------------|--------------------------------|-------------------------|
| `ENUM`               | `String`                       | `'small'`, `'medium'`   |
| `TSVECTOR`           | `String`                       | `'text'`                |
| `XML`                | `String`                       | `'<tag>value</tag>'`    |

