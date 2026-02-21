# Service Discovery 
https://www.youtube.com/watch?v=ecuEkmFs5Vk

## Overview
> - In a **monolithic application**, components are bundled, making communication simple
> - In a **microservices architecture**, applications are split into multiple smaller services deployed across different servers or containers

mechanism that allows :
- applications to locate services dynamically.
- microservice/s to automatically discover each other on the network.
- Instead of hardcoding IP addresses or URLs, 
  - **uses service name** or other identifier 

benefit ✔️
- scalability 
  - Dynamically adjusts as new service instances are added or removed,
  - thus, enabling seamless application scaling
- Reduced Complexity
  - Simplifies service management, 
  - allowing developers to focus on core business logic
- Fault-Tolerance 
  - Ensures that if a service instance fails, 
  - other instances can be used without manual intervention
  
**Service Registry (key component)** ✔️
- `Service instances` must be registered with and deregistered from the service registry.
- crucial component that needs to be **highly available and up-to-date**
- Self-Registration Pattern 
- heartbeat mechanism
  - It sends heartbeat requests to prevent registration from expiring.
- eg:
  - `Consul or Eureka`
  - K8s, coreDNS, etcd, AWS Cloud Map

---
## Types
### Client-Side 
- client queries a service registry 
- and uses the information to connect to the appropriate service

### Server-Side Discovery ✔️
- client sends a generic request to a central **load balancer or Gateway**
- load balancer/Gateway handles the discovery process 
- and forwards the request to the correct service.
- eg: AWS API gateway, AWS ALB

---
## Java SB snippets
```
@EnableEurekaServer 
- to make the Spring Boot application a Eureka server.

@EnableEurekaClient 
- to register it with the Eureka server.

@LoadBalanced RestTemplate 
- to automatically use Eureka for service discovery by name.
- application.name=service-1
```