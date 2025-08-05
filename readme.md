# Solution Engineer Knowledge Base

Welcome to the Solution Engineer Knowledge Base, a comprehensive collection of documentation, guides, and resources organized for effective learning and reference in Docker, Kubernetes, Terraform, AWS, and related technologies.

## Overview

This repository is structured with **MkDocs** navigation in mind to provide clear and easy access to curated content for solution engineers. It covers a broad range of topics including containerization, orchestration, infrastructure as code, cloud, messaging, observability, and system design.

## How to Run MkDocs

To serve and browse this documentation locally using MkDocs, follow these steps:

1. **Install MkDocs** (if not already installed):

```bash
pip install mkdocs
```

2. **Navigate to the root directory** of your documentation where the `mkdocs.yml` file is located:

```bash
cd path/to/your/docs
```

3. **Serve the documentation locally:**

```bash
mkdocs serve
```

4. **Open your browser** and navigate to:

```
http://127.0.0.1:8000/
```

You will see a live preview of the documentation with the navigation as defined.
5. **Build the static site** for deployment:

```bash
mkdocs build
```

This generates a `site/` folder with static HTML files you can deploy to any web server.
## Table of Contents

### Docker ✅
[02_docker](docs/02_docker)
- Architecture Overview
- Commands & Developer Guide
- Docker Compose
- Storage Concepts
- Networking Fundamentals


### Kubernetes ✅
[03_Kubernetes](docs/03_Kubernetes)
- Getting Started with Kubernetes
- Minikube: Introduction & Hands-on labs
- Core Concepts: Architecture, Containers, Namespaces, Labels & Selectors, Controllers
- Pods: Basics, Placement, Monitoring, Readiness/Liveness, Resource Management, Disruption Budgets
- Workloads: StatefulSets, ReplicaSets, Jobs, DaemonSets, Deployments (including EKS specific)
- Networking: Services, Ingress, Network Policies
- Storage: Volumes, Storage Classes
- Security: Secrets, ConfigMaps, Service Accounts, Authentication, Authorization and RBAC
- Advanced Topics: API Versions, Custom Resource Definitions
- CKAD Certification Preparation: Topics, Labs, Command Cheatsheet
- EKS Cluster Setup & Developer Guides (Multi-tenant, Annotations, IRSA)
- Helm Charts: Kickoff and basics


### Terraform ✅
[04_terraform](docs/04_terraform)
- DevOps with Terraform
- Getting Started Guide
- HCL Language Fundamentals
- Modular Terraform


### Continuous Delivery Pipeline ✅
[05_harness](docs/05_harness)
- Bash Scripting for Pipelines
- Harness Delegates Setup
- Pipeline Examples for Multiple Projects


### AWS ✅
[01_aws](docs/01_aws)
- Compute Services: EC2, ECS, EKS, Lambda, App Runner, Step Functions
- Storage: S3 Basics & Advanced, EBS, EFS, FSx, Snow Family
- Databases: RDS, Aurora, DynamoDB, DAX
- Messaging & Streaming: SQS, SNS, Kinesis, ActiveMQ, EventBridge, MSK
- Networking: VPC, NAT, Peering, ELB, CloudFront, Global Accelerator, API Gateway, RAM, Route 53
- Security: IAM, SSO & Directory Services, Cognito, KMS, Secrets Manager, SSM Parameter Store, ACM, WAF, Shield, HSM
- Monitoring: CloudWatch Metrics & Logs, X-Ray, CloudTrail
- Disaster Recovery & Migration Strategies
- Analytics: Athena, Redshift, QuickSight, Glue ETL
- AWS DVA-C02 Certification Prep
- Practice Tests


### Message Broker ✅
[06_message-broker](docs/06_message-broker)
- Messaging Protocols Overview
- Kafka: Getting Started, Extended Notes, Project Case Studies
- RabbitMQ: Kickoff and Java Project Example


### Observability 🔸🔸🔸
[09_observability](docs/09_observability)
- Observability Kickoff
- Spring Boot Observability
- Datadog Monitoring


### System Design 🔸🔸🔸
- [10_System_Design](docs/10_System_Design)
- [blogs_01_byteByteGo.md](docs/10_System_Design/blogs_01_byteByteGo.md)
- Theorems & Design Patterns: CAP, Tech Stacks, Concepts
- High Performance Design
- High Availability Architecture
- Scalability: Services, Databases, Microservices (including orchestration & API design)
- Reliability: Resiliency & Durability
- Distributed Systems Basics
- Observability in System Design
- Security: JWT, OAuth2, OIDC, SAML, PKI
- Database & Storage Design Patterns
- System Design Examples and Case Studies