# Lake Formation (Fully managed)
## Intro
- https://aws.amazon.com/lake-formation/
- build on top of **AWS glue**
- **central place** to have all your data for analytics purposes.
- security :
  - **Fine-grained Access** (row-level, column-level) 
  - Also **centralized access** for all other analytics related services at single place.
- It automates many complex manual steps :
  - collecting (backend: **s3**) :point_left:
    - Combine structured (RDS,csv,etc) and unstructured data
  - cleansing
  - moving 
  - **cataloging data**
  - **de-duplicate** (using ML Transforms)`,
  - **source blueprints** for S3, RDS, Relational & NoSQL DB
  - ...

![img.png](../99_img/moreSrv/analytics-2/img.png)

![img_1.png](../99_img/moreSrv/analytics-2/img_1.png)

---
## more :dart:
### Question-1 (on s3): The data lake has a **staging zone** 
- where intermediary query results are kept only for 24 hours. 
- These results are also heavily referenced by other parts of the **analytics pipeline**.
- MOST `cost-effective strategy` for storing this intermediary query data ?
- options:
  ```yaml
  Amazon S3 Glacier Instant Retrieval storage class
  - minimum storage duration charge is 90 days, so this option is NOT cost-effective 
  
  Amazon S3 Standard-Infrequent Access storage class
  - minimum storage duration charge is 30 days
  
  Amazon S3 Standard storage class  **
  - no minimum storage duration charge 
  - no retrieval fee
  
  Amazon S3 One Zone-Infrequent Access storage class
  ```