# Improvement :point_left:
## A performance :yellow_circle:
### achieved
- TACT / COPS
  - legacy  app performance - inner sql, database connection pool
  - bulk review and approve - UI enhancement + spinner (prevent multiple click)
  - enums
### proposal
- cache static data + refresh api

---
## B monitoring :yellow_circle:
- built dashboard in angular
- **proposal-1:**
  - custom metric (label, dimension)
  - traceability for l,sqs,eb microservice
  - x-ray, dd(otel)
---
## C business :yellow_circle:
### achieved
### proposal

---
## D AWS :yellow_circle:
### achieved
- vpce
- sqs extended queue 256

### proposal
- parallel processing with sns fifo group id 
- event bridge pipe (source> filter > transform > target) + add de-duplication id
- AWS event-1 data (pass temp result as event data)
- lambda cold start fix: remove eni, run outside  vpc, layers, use: py

---
# learnings :point_left:
- Devops
  - did terraform, easy to understand, persnal project done
  - okta, kafka terraform script
  - create harness pipeline + delegates
  - Minikube and persnal EKS
- kubernetes
  - preparing for CKAD, did training, completing online labs