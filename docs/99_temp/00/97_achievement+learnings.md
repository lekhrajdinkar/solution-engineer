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
- **maps**
    - inbound ETL poc
      - no autosys jobs, event driven design ::  event-bus + scheduled-event for (SLA) + dependency+check (DB base)
      - ETL driver program : flask / fastapi + unicorn
    - outbound:
      - SWIFT - driver program
      - CIT external TD blend (BR/SS) - special series - 166
      - CIAM design + JIRA, coding, refactoring, dashboard API
      - design JSON contract and RDBMS for outbound
      - design event-payload json - allocation (1), re-balance(1), generic(M) trade/s
      - bucket_mapping_id -- driver of all event, metric and dimension
- **fsr**
    - jwt token validation + method based authorization @pre/postAuth + helped to understand Auth/implicit flow
    - suggest **implicit flow with pkce**
    - batch job 15 sec IAM token
    - disagree with UI arch : forced old them to redux, observability over Js-promises, etc
- **tact** :  
    - built screen for TACt fto swift message. no angular Vanilla JS + simulator to fast-forward development
- **refactor**
    - eg: swift for AF vs AFIS
    - SB property load, avoid inner class
    - simple, clean
    - utility api : kafka, config, ibm/mq, decode TIP file
    - health check : static vs dynamic factor
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
- **fsr autosys** :parking: check pod solution
- **custom-metric** to create event Dashboard: eb-event json payload, add tags

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
- Always checking what going on : **etacs, fsr, path** tech/helix upgrade
    - found etac k8s - logs temp > not forwarded to dd
    - fsr goes to splunk
- Infosys training : share-code.jar
- OPAC/FAC days:
    - georgey - decrypt password util
    - **footNotes Deletion** tools CSV to delete script (cascaded and complex joins, 7 tables)


---
# learnings / poc :point_left:
- **okta :: Oauth2.0**
    - https://dev-16206041.okta.com/
    - aws org :: SSO + SAML with okta ... in progress
- **terraform**, easy to understand, persnel project done
    - https://portal.cloud.hashicorp.com/services/boundary/clusters/list?project_id=ef55c361-4762-4358-9aff-65cd03c360f2 
    - https://app.terraform.io/app/lekhrajdinkar-org/workspaces
- **harness CD pipeline** 
    - **delegates** setup on minikube
    - deploy to personal k8s cluster minikube/EKS
    - preparing for CKAD, did training, completing online labs
    - https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/orgs/default/projects/mapsoutboundapi/pipelines

![img.png](img.png)



