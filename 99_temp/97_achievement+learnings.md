# Improvement :point_left:
## A. performance :green_circle:
### achieved
- TACT / COPS
  - legacy  app performance - inner sql, database connection pool
  - bulk review and approve - UI enhancement + spinner (prevent multiple click)
  - enums
### proposal
- cache static data + refresh api

---
## B. monitoring :green_circle:
### achieved
- built dashboard in angular + s3 URI + s3 metadata on dynamoDB
### proposal
- custom metric (label, dimension)
- traceability for l,sqs,eb microservice
- x-ray, dd(otel)

---
## C. **CORE app** :green_circle:
### achieved
- ETL - autosys jobs : event-bus + scheduled-event for (SLA) + dependency+check (DB base)
- SWIFT - driver program
- ETL driver program : flask + unicorn
  - Design
  - poc program.
- helping **fsr**
  - jwt token validation + method based authorization @pre/postAuth + helped to understand Auth/implicit flow
  - suggest **implicit flow with pkce**
  - batch job 15 sec IAM token
- **tact** :  
  - built screen for TACt fto swift message. no angular Vanilla JS + simulator to fast forward developmnet
- **refactor**
  - eg: swift for AF vs AFIS
  - SB property load, avoid inner class
  - simple, clean
  - utility api : kafka, config, ibm/mq, decode TIP file
  - health check dynamic factor
- **quick prod fixes:**
  - data mod
  - code fix (not delegate to offshore)
  
### proposal
- solution-1 : **autosys**
  - proposal, Custom CRD (jil : yaml) > spin up k8s::Job + side car pod > push event > kafka
  - event processing 
  - custom controller (DemonSet) > read yml > spin up other job
- solution-2 : **autosys**
  - s3:file-drop > lambda > webhook::harness pipeline
- **fsr autosys** :parking:

---
## D. AWS :green_circle:
### achieved
- vpce
- sqs extended queue 256
- handle concurrency with fifo queue + isolation level
- MRAP
- lambda layer
- OAuth : token refresh lambda
- batch job (RDS iam based auth, 15 min expiry)
- arch disagree
  - s3 > SQS vs S3 > eb:bus >... can replay and archive...

### proposal
- parallel processing with sns fifo group id 
- event bridge pipe (source> filter > transform > target) + add de-duplication id
- AWS event-1 data (pass temp result as event data)
- lambda cold start fix: remove eni, run outside  vpc, layers, use: py
- IAM based auth, expired in 15 min, best for B2B internal omm, inside VPC, good use-case

---  
## E. more
- always checking what going on etacs,fsr,path tech upgrade
- found k8s - logs temp > not forwarded to dd
- infosys training cheat jar
- georgey - decrypt password util
- footNotes Deletion tools CSV to delete script (cascaded and complex joins, 7 tables)


---
# learnings :point_left:
- Devops
  - did terraform, easy to understand, persnel project done
  - okta, kafka terraform script
  - create harness pipeline + delegates
  - Minikube and persnal EKS
- kubernetes
  - preparing for CKAD, did training, completing online labs




