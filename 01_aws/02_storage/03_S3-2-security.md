- ![img_3.png](../99_img/storage/s3-3/img_3.png)
- ![img_2.png](../99_img/storage/s3-3/img_2.png)
- ![img_1.png](../99_img/storage/s3-3/img_1.png)


- `VPC-endpoint/interface(to access s3)` --> `s3:access-point` --> `bucket:selected-object` 
  - notice `policy` attached to all.
  - ![img_1.png](../99_img/storage/s3-3/img_100.png)

## A.Scenarios
- aws1:`iam-principle` --> `aws2`:s3-bucket1/obj-1 : `cross acct access`
- principle: IAM-user  + federated use, aws-service, role

- 

---
## C. CORS
- web browser security.
```
// SPA
- server-1(ng:SPA) + server-2(api)
- browser --> server-1
- browser(ng)--> server-2(api) --> response:header:access-control-allow-Origin=server-1

// S3 static website
- bucket-1(index.html) + bucket-2(images, other resource, page-1.html)
   - update bucket policies:  getObject for all * prinicple.
- browser --> bucket-1
- browser(index.html) --> ajax::fetch(bucket-2/page-1.html) --> bucket-2 --> will work, only CORS is enabled to bucket-1 as origin 
- bucket-2 > permission tab > edit CORS > add json:origin-bucket-1
```
![img_6.png](../99_img/storage/s3-3/img_6.png)
![img_7.png](../99_img/storage/s3-3/img_7.png)

---
## D. Encryption (v:149,s:309)
- At REST
  - server side encryption, `sse` :
    - `sse-s3` (default) : encryption-key provided/owner by `aws:s3` , http header : "x-amz-server-side-encryption": `AES256`
    - `sse-kms` : encryption-key comes from `kms` ,  http header : "x-amz-server-side-encryption": `aws:kms`
      - client create kms-key before.
      - s3 --> api-call --> KMS (slow)
      - key can be rotated.
      - `dsse-kms` came 2023
    - `sse-c` : customer provide key (outside aws)
  client side encryption `cse` : client upload encrypted object
- At transit (TLC/SSL)
  - `http` endpoint
  - `https` endpoint -> update bucket policy : deny http
  - ![img.png](../99_img/storage/s3-3/img.png)
- SSE-KMS with `Encryption Context `: encrypt each file with unique key.

---
## E. MFA delete
- root account  : setupMFA
- setup aws-cli with root AccessKey  + secretAccessKey
```
aws s3api put-bucket-versioning 
    --bucket <bucket-name> 
    --versioning-configuration Status=Enabled,MFADelete=Enabled 
    --mfa "arn:aws:iam::<account-id>:mfa/<mfa-device> <mfa-code>"
    
next, attempt to delete object version

MFADelete=disabled 
```
---
# More
## ScreenShot
- 
- 
- 
- ![img_4.png](../99_img/storage/s3-3/img_4.png)
- CORS:
- ![img_5.png](../99_img/storage/s3-3/img_5.png)
---

---
## 99. S3: hands on
```  
  - create bucket - bucket-1-us-west-2, will be created in all AZ.
  - disable : ACL, , versioning
  - enable : public access + attach BUCKET POLICIES (read from any principle, resource:*)
  - encryption: SSE-S3 *, SSE-KMS, DSSE-KMS
  - upload png file
  - https://bucket-1-us-west-2.s3.us-west-2.amazonaws.com/Screenshot+2024-07-16+002401.png
  - inspect : 
      - Open link --> `s3 pre-signed url`, credential-info are encode in url.
      - access with public-url :  failed | ok after making public.
  - static website hoisting : enable on bucket + index.html
    - endpoints: https://bucket-1.s3-website.region.amazon.com
    
  // Replication
  - create another  buvket-2-us-west-1
  - create replication rule:
    - add target bucket : bucket-1-us-west-2
    - enable versionsing
    - craete new IAM
    
  // storage classes:
  - bucket-1 > mgt > create Life Cycle rule
    - select object: ALL /  by-prefix/suffix / by-tag
    - create transistion Rule/s
      - rule-1 : move from class1 to clas2 after xxdays
      - ...
      - ... 
    - -create deletion Rule/s
      - delete old version/s
      - delete all version/s
      - mark for delete
     