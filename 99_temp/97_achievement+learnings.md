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
## C. core app :green_circle:
### achieved
- ETL - autosys jobs : event driver + scheduled jobs (SLA) + dependency+check (DB base)
- SWIFT - driver program
- ETL driver program : flask + unicorn
  - Design
  - poc program.
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

### proposal
- parallel processing with sns fifo group id 
- event bridge pipe (source> filter > transform > target) + add de-duplication id
- AWS event-1 data (pass temp result as event data)
- lambda cold start fix: remove eni, run outside  vpc, layers, use: py
- IAM based auth, expired in 15 min, best for B2B internal omm, inside VPC, good usecase

---  
## E. more
- infosys training cheat jar

---
# learnings :point_left:
- Devops
  - did terraform, easy to understand, persnel project done
  - okta, kafka terraform script
  - create harness pipeline + delegates
  - Minikube and persnal EKS
- kubernetes
  - preparing for CKAD, did training, completing online labs




