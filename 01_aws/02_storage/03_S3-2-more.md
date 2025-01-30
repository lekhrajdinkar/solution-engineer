# S3 continue...
## 1. S3 Glacier object : Vault lock :yellow_circle:
- WORM policy : write once, read many.
- set `retention period`, can be extend.
- `usecase`: data retention. and compliance
- `lock` object in glacier
- retention mode:
  - `compliance` --> no longer be deleted/updated in the future, not even by root.

---
## 2. S3 object lock
- WORM policy.
- set `retention period`, can be extend.
- set (optional) `legal hold` : lock `indefinitely`. (irrespective of retention-period)
- retention mode:
  - `compliance` --> no longer be deleted/updated in the future, not even by root.
    - only way to delete, delete account itself. :point_left:
  - `Governance` --> root user can update/delete.

---
## 3. Storage lens service  :yellow_circle:
- Understand, analyze, and `optimize` storage across entire `AWS Organization` (acct > region > bucket)
- `dashboard` : enable by default/cant delete.
  - aggregated reports/csv gernerted by specific metric --> can publish to CW for free.
  - `advance` metric (available for 15 month), paid
  - `free` metric (available for 14 day, once generated)
  - metric/s :
    - `summary` metric : insight to object -size, count, fastest growing bucket, etc
    - `cost-optimization` metric : insight to non-current, incomplete multiparts, etc
    - `Data protection` metric: count of encrypted Bucket, replication rule
    - `Access-mgt` : object owner
    - `event` metric : s3-eventNotification count, etc
    - `Activity` + `statusCode` : GET, POST, etc +   count of 200, 404, etc
    - `performance` : s3 transfer acce enable count

- ![img_6.png](../99_img/storage/s3-2/img_6.png)

---
## 4. CLI
### S3 sync command :dart:
- **one-time copy of data** :point_left:
- uses the **CopyObject** APIs to copy objects between Amazon S3 buckets. 
- **lists** the source and target buckets to identifies:
  - **missing** objects.
  - objects that have **different LastModified** dates 
- The sync command **on a versioned bucket copies** 
  - only the current version of the object :point_left:
  - previous versions aren't copied.
- By default, this preserves object metadata, NOT ACL :point_left:
  - but the access control lists (ACLs) are set to FULL_CONTROL for your AWS account,
  - which removes any additional ACLs. 
- If the operation fails, you can run the sync command again **without duplicating previously** copied objects.
- `aws s3 sync s3://DOC-EXAMPLE-BUCKET-SOURCE s3://DOC-EXAMPLE-BUCKET-TARGET`

## PutObject
- include header : `x-amz-server-side-encryption : AES256|aws:kms`  to encrypt.
- include header : `aws:SecureTransport` : allow HTTPS  , not HTTP

## 5. static website
- **url format**: :dart:
  - http://bucket-name.s3-website.Region.amazonaws.com
  - http://bucket-name.s3-website-Region.amazonaws.com

## 6. search/list s3 onject fast
![img.png](img.png)