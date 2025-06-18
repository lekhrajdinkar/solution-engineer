# Kubernetes / K8s
- Google/CNCF
- k8s - 01 intro: https://chatgpt.com/c/6726267e-2a8c-487d-8ffe-937c2c4d0f0f
- Monolithic  vs Microservice
- microservices advantages in the cloud
- monolith's challenges in the cloud

--- 
## Monolithic
- Entire software run as `single heavy process` on `expensive hardware`
  - tightly couple
  - legacy design and architecture.
  - redundant logic, 1000 lines of code, no modern language and design principles.
  -  built with a single technology stack

- Not designed to take full advantage of cloud-native features such as cloud's elasticity/auto-scaling, managed services, and distributed architectures.  
- other challenges and limitation:
  - `scaling` of single feature in impossible, and scaling whole app is pricey.
  - `upgrade` : downtime and upgrade window.
  - `failure` in any part of a monolithic application can potentially bring down the entire system
  - Higher `operational costs` and less efficient use of computational resources.
  - `size grows`: new updates/features, keep on making appl more `heavy`.

---
## Micro service
- architecture: https://chatgpt.com/c/2f54de12-b416-4a76-80a0-ebd286b0c467
- small independent services,  lightweight applications, for each business/feature.
- `On-demand scalability` : run MS on different hosts /Availability
- `Optimal resource usage` : run on matching hardware-requirement| efficeint | low cost.
- `distributed nature` : whole app is distributed among many MS. which adds up some complexity.
- Seamless updates(rollout)/rollbacks without any downtime.
- `Fault-tolerance`:
  - system continues to operate properly in the event of the failure of some of its components
- `Service-discovery`
  - process of automatically detecting network locations of service instances.
  - `service registry` - database of available service instances. eg :  Netflix Eureka
    - client --> `service registry` 
    - client --> load balancer > queries to service registry.

> can run on Cloud and take full advantage of cloud.

---
## Monolithic --> Microservice
- modernize monolith business applications / Distributed software.
- Not all monolithic app is good candidate.
- complex and risky, due to its tightly coupled components and dependencies.
- not smooth, has to survive below challenges:
  - `Refactoring phase` : break down into modules
  - `application resiliency` as whole.
  - `Choosing runtimes` on cloud : 
    - underlying OS, hardware, library, runtime env for each MS. there might be conflict.
    - running well on one hardware/runtine , but not working same on other.
    - Solution:` Application containers`:
      - encapsulated `lightweight` runtime environments.
      - promised `consistent` software environments.
      - each MS/module running in their own execution environments `isolated` from one another.
      

