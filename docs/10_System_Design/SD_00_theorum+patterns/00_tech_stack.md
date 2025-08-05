## B.1 Front-end
- framework:
    - `Reactjs` : [project-1](https://github.com/lekhrajdinkar/01-Frontend-reactJs) | [project-2(redux)](https://github.com/lekhrajdinkar/01-Frontend-ReactJS-16-redux)
    - `Angular 2+` :
        - js/html/css : [front-end-pack](https://github.com/lekhrajdinkar/01-front-end-pack)
        - ng: [Angular project-1 MEAN-stack](https://github.com/lekhrajdinkar/01-Frontend-MEAN-stack) | [Angular project-2 OTT](https://github.com/lekhrajdinkar/99-project-01-OTT-ng)
        - css more: [css notes-1](https://github.com/lekhrajdinkar/Notes-HTML5-CSS3/tree/master/NOTES-CSS) | [css notes-2](https://github.com/lekhrajdinkar/Notes-HTML5-CSS3/tree/master/NOTES)
    - `Vue.js`
- **SPA**
- **PWA**

## B.2 Backend
- **GraphQL APIs** : pending
- frameworks for RESTful APIs
    - java --> **SpringBoot**
        - [00_Springboot](../../00_Springboot)
    - js/ts/nodejs --> **Express.js**
        - [project-1](https://github.com/lekhrajdinkar/02-Backend-API-NodeJS)
    - py --> **Django/fastApi/flask**
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
    - [01_monolith_MicroServices.md](../SD_03_scalability/micro-service/01_monolith_MicroServices.md)[00_kickOff](../../03_Kubernetes/00_kickOff)
- **Load Balancers**
    - AWS :: ELB/Elastic Load Balancer - alb, nlb,
    - `NGINX`
- Cloud services : **AWS**: lambda, s3, sqs, etc
    - [01_aws](../../01_aws)

---
##  B.3 Database
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

## B.4 CI/CD pipeline
- GitHub Actions
- Harness
- containerization: docker/k8s

## B.5. Observability and Monitoring (log,metric,traces)
- `OpenTelemetry`
- `micrometer`
- Prometheus, grafana, AWS:CloudWatch
- Application health
- distributed tracing
    - AWS:CloudWatch>x-rays

## B.6. Security
- [01_web_Security](01_Security)
- SAML + SSO
- LDAP
- Okta
    - OAuth 2.0,
    - OpenID Connect