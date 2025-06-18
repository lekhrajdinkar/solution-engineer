# A. `Design pattern`
## Separation of Concerns
- Keep frontend, backend, and database independent.

## Service-Oriented Architecture (SOA)
- Microservices communicating over: [microservice in details](https://github.com/lekhrajdinkar/03-spring-cloud-v2/tree/main/Notes) 
  - [REST](../../00_Springboot/02_web/04_REST.md)
  - [gRPC](../../00_Springboot/02_web/08_gRPC%2Bwebflux.md)
  - messaging systems :
    - [rmq](../../06_messaging/rmq) 
    - [kakfa](../../06_messaging/kakfa)
  
## API-first Development:
- Design APIs before implementation to enable parallel development.

## Event-Driven Architecture
- Use `event streams` for **real-time updates** 
  - Kafka, 
  - AWS::SQS + AWS::EventBridge
  - RMQ::StreamingQueue

## Serverless Architecture (auto-scale,etc)
- AWS :: Lambda
  - for scalable and cost-effective solutions.
- Other AWS serverless offering 
  - fargate (ECS/EKS)
  - sqs,s3
  - AWS DB :: DynamoDB,Aurora

## Twelve-Factor App :point_left:
- Guidelines for building `scalable` and `maintainable` applications.
- -[02_twelve_factor_app.md](02_twelve_factor_app.md)

---
# B. Infrastructure `components`:
## Front-end
- framework:
  - `Reactjs` : [project-1](https://github.com/lekhrajdinkar/01-Frontend-reactJs) | [project-2(redux)](https://github.com/lekhrajdinkar/01-Frontend-ReactJS-16-redux)
  - `Angular 2+` :  
    - js/html/css : [front-end-pack](https://github.com/lekhrajdinkar/01-front-end-pack)
    - ng: [Angular project-1 MEAN-stack](https://github.com/lekhrajdinkar/01-Frontend-MEAN-stack) | [Angular project-2 OTT](https://github.com/lekhrajdinkar/99-project-01-OTT-ng)
    - css more: [css notes-1](https://github.com/lekhrajdinkar/Notes-HTML5-CSS3/tree/master/NOTES-CSS) | [css notes-2](https://github.com/lekhrajdinkar/Notes-HTML5-CSS3/tree/master/NOTES)
  - `Vue.js`
- **SPA**
- **PWA**

## Backend 
- **GraphQL APIs** : pending
- frameworks for RESTful APIs
  - java --> **SpringBoot** 
    - [00_Springboot](../../00_Springboot)
  - js/ts/nodejs --> **Express.js** 
    - [project-1](https://github.com/lekhrajdinkar/02-Backend-API-NodeJS)
  - py --> **Django/fastApi**
    - [project-2](https://github.com/lekhrajdinkar/02-Backend-Python)

- **API Gateway**:
  - Routing, authentication, rate-limiting
  - ingress server in K8s
- **Microservices**
  - deploy via
    - containers (Docker) 
    - orchestration (**Kubernetes**) : pods+services
      - [03_Kubernetes](../../03_Kubernetes)
  - https://github.com/lekhrajdinkar/03-spring-cloud-v2/tree/main/Notes
  - [01_monolith_MicroServices.md](../../03_Kubernetes/00_kickOff/01_monolith_MicroServices.md)[00_kickOff](../../03_Kubernetes/00_kickOff)
- **Load Balancers**
  - AWS :: ELB/Elastic Load Balancer - alb, nlb,
  - `NGINX`
- Cloud services : **AWS**: lambda, s3, sqs, etc
  - [01_aws](../../01_aws)
  
---
##  Database
- **Relational Databases**: 
  - PostgresSQL / `Aurora`(serverless)
  - MySQL
- **NoSQL Databases**
  - MongoDB, 
  - Cassandra, 
  - AWS:`DynamoDB`
- Horizontal scaling : aws serverless takes care
- Distributed architecture 
  - primary writer instance
  - multiple READ instance
  - replication with encryption at rest.
  - AWS serverless takes care.
- **Caching**: 
  - Redis
  - Memcached

## CI/CD pipeline
- GitHub Actions
- Harness
- containerization: docker/k8s

## Observability and Monitoring (log,metric,traces)
- `OpenTelemetry`
- `micrometer`
- Prometheus, grafana, AWS:CloudWatch
- Application health 
- distributed tracing
  - AWS:CloudWatch>x-rays

## Authentication and Authorization
- [01_web_Security](../01_web_Security)
- SAML + SSO
- LDAP
- Okta
  - OAuth 2.0,
  - OpenID Connect

---
# C Design Aspect
## scalability
## reliability
## flexibility
## maintainability
## Observability