# DynamoDB AWS CLI Commands

This document provides a comprehensive guide to DynamoDB AWS CLI commands, including all common options available for various operations.

---

## Table Information
- **Table Name:** `ps-games`
- **Primary Key:**
    - Partition Key: `id` (Number)
    - Sort Key: `name` (String)
```json5
[
    {
        "AttributeName": "id",
        "KeyType": "HASH"
    },
    {
        "AttributeName": "name",
        "KeyType": "RANGE"
    }
]

```
---

## Common Options for DynamoDB CLI Commands

| **Option**                         | **Purpose**                                                                                                                                    |
|------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| `--page-size`                      | Reduces the number of items fetched per API request to improve performance and avoid timeouts or excessive memory use. scan and query          |
| `--max-items`                      |                                                                                                                                                |
| `--table-name`                     | Specifies the table name for the operation.                                                                                                    |
| `--key`                            | Defines the primary key of the item for operations like `GetItem`, `UpdateItem`, `DeleteItem`.                                                 |
| `--item`                           | Provides the item attributes for `PutItem`.                                                                                                    |
| `--update-expression`              | Specifies how to update attributes in `UpdateItem`.                                                                                            |
| `--condition-expression`           | Adds conditions for operations like `PutItem`, `UpdateItem`, and `DeleteItem`.                                                                 |
| `--filter-expression`              | Filters results during `Scan` or `Query` based on attribute values.                                                                            |
| `--key-condition-expression`       | Specifies conditions for `Query` to fetch items based on partition/sort key.                                                                   |
| `--projection-expression`          | Limits the attributes returned by operations like `Query` or `Scan`.                                                                           |
| `--expression-attribute-values`    | Maps placeholders to actual values used in expressions.                                                                                        |
| `--expression-attribute-names`     | Maps placeholders to actual attribute names to avoid reserved words.                                                                           |
| `--return-values`                  | Specifies what is returned after `PutItem`, `UpdateItem`, or `DeleteItem`. Values: `NONE`, `ALL_OLD`, `UPDATED_OLD`, `ALL_NEW`, `UPDATED_NEW`. |
| `--select`                         | Used in `Query` or `Scan` to specify the returned data (`ALL_ATTRIBUTES`, `COUNT`, `SPECIFIC_ATTRIBUTES`).                                     |
| `--consistent-read`                | Ensures strongly consistent reads for `GetItem`, `Query`, or `Scan`. Default: eventually consistent.                                           |
| `--region`                         | Specifies the AWS region for the DynamoDB operation.                                                                                           |
| `--return-consumed-capacity`       | Shows read/write capacity units consumed by the operation. Options: `INDEXES`, `TOTAL`, `NONE`.                                                |
| `--limit`                          | Restricts the number of items returned by `Query` or `Scan`.                                                                                   |
| `--exclusive-start-key`            | Specifies the starting point for `Query` or `Scan` to continue from the last response.                                                         |
| `--index-name`                     | Specifies a secondary index for `Query` or `Scan`.                                                                                             |
| `--segment` and `--total-segments` | Divides a `Scan` into parallel operations for faster processing.                                                                               |

---

## 1. Put Item (Insert a New Item)
```bash
aws dynamodb put-item \
  --table-name ps-games \
  --item '{
      "id": {"N": "102"},
      "name": {"S": "God of War Ragnarok"},
      "release_year": {"N": "2022"},
      "genre": {"S": "Action-Adventure"},
      "rating": {"N": "9.8"},
      "platforms": {"SS": ["PS5", "PS4"]},
      "multiplayer": {"BOOL": false},
      "version": {"N": "1"}
  }' \
  --region us-west-2
```

---

## 2. Get Item (Retrieve a Specific Item)
```bash
aws dynamodb get-item \
  --table-name ps-games \
  --key '{
      "id": {"N": "102"},
      "name": {"S": "God of War Ragnarok"}
  }' \
  --region us-west-2
```

---

## 3. Query (Fetch Items Based on Partition Key)
```bash
aws dynamodb query \
  --table-name ps-games \
  --key-condition-expression "id = :id" \
  --expression-attribute-values '{
      ":id": {"N": "102"}
  }' \
  --region us-west-2
```

---

## 4. Scan (Retrieve All Items in a Table)
```bash
aws dynamodb scan \
  --table-name ps-games \
  --region us-west-2
```

---

## 5. Delete Item (Remove an Item)
```bash
aws dynamodb delete-item \
  --table-name ps-games \
  --key '{
      "id": {"N": "102"},
      "name": {"S": "God of War Ragnarok"}
  }' \
  --region us-west-2
```

---

## 6. Update Item (Modify Existing Attributes)
```bash
aws dynamodb update-item \
  --table-name ps-games \
  --key '{
      "id": {"N": "102"},
      "name": {"S": "God of War Ragnarok"}
  }' \
  --update-expression "SET rating = :new_rating, genre = :new_genre" \
  --expression-attribute-values '{
      ":new_rating": {"N": "9.9"},
      ":new_genre": {"S": "Adventure"}
  }' \
  --region us-west-2
```

---

## 7. Add a New Attribute (Using UpdateItem)
```bash
aws dynamodb update-item \
  --table-name ps-games \
  --key '{
      "id": {"N": "102"},
      "name": {"S": "God of War Ragnarok"}
  }' \
  --update-expression "SET file_size = :size" \
  --expression-attribute-values '{
      ":size": {"N": "85"}
  }' \
  --region us-west-2
```

---

## 8. Conditional Update (Prevent Overwrites)
```bash
aws dynamodb update-item \
  --table-name ps-games \
  --key '{
      "id": {"N": "102"},
      "name": {"S": "God of War Ragnarok"}
  }' \
  --update-expression "SET rating = :new_rating" \
  --condition-expression "rating < :current_rating" \
  --expression-attribute-values '{
      ":new_rating": {"N": "9.7"},
      ":current_rating": {"N": "9.9"}
  }' \
  --region us-west-2
```

---

## 9. List All Tables (In Your AWS Account)
```bash
aws dynamodb list-tables --region us-west-2
```

---

## 10. Describe Table (View Table Structure and Capacity)
```bash
aws dynamodb describe-table \
  --table-name ps-games \
  --region us-west-2
```

---

## 11. Delete Table (Clean Up Table)
```bash
aws dynamodb delete-table \
  --table-name ps-games \
  --region us-west-2
```

---

### Notes:
- Replace attribute values as needed for your use case.
- Ensure proper IAM permissions are configured before running commands.
- Use these commands responsibly in your AWS environment!
