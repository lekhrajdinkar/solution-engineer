https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360643/posts/2192056439

Evolution of System Design video lesson (Module 1, Video 2)

---

## 📜 Key Architectural Paradigms Over Time

### 1. **1960s – Mainframe Computing**
* **Model:** Monolithic, centralized processing where all data processing and application logic ran on a single large machine.
* **Access:** Dumb terminals served merely as display inputs/outputs.
* **Characteristics:** 
  * High hardware cost, 
  * low flexibility, 
  * single point of failure, 
  * but simple centralized administration.


### 2. **1980s – Monolithic & Client-Server Architecture**
* **Model:** Transition toward client-server models and single-tier/two-tier application monoliths as personal computers gained processing power.
* **Characteristics:** 
  * Workloads split between client interfaces and database backends, 
  * though core applications often remained tightly coupled monolithic binaries.


### 3. **2000s – Microservices & Distributed Systems**
* **Model:** Breaking down monolithic services into smaller, loosely coupled, independently deployable services.
* **Characteristics:** 
  - Scales out horizontally rather than vertically.
  - Focus shifts to service discovery, 
  - load balancing, 
  - inter-service communication, 
  - and resilience.


### 4. **2010s – Event-Driven Architectures**
* **Model:** Asynchronous communication patterns using message brokers and event buses (e.g., Kafka, RabbitMQ).
* **Characteristics:** 
  - Decoupled producers and consumers, 
  - improved fault tolerance, 
  - and support for real-time processing/streaming pipelines.


5. **2020s – Serverless & AI-Integrated Architectures**
* **Model:** Fully managed event-driven compute (FaaS/Serverless) alongside specialized distributed infrastructure for large language models and machine learning workflows.
* **Characteristics:** 
  * Pay-per-use execution, 
  * automatic scaling to zero, 
  * edge computing, 
  * and specialized retrieval-augmented/vector infrastructure.

---

## 🔑 Core Takeaways

* System design evolution is driven by **scaling needs**, **cost efficiency**, and **reducing blast radiuses** (fault isolation).
* As systems move from centralized mainframes to:
  * modern distributed/event-driven setups, 
  * system design complexity shifts from managing raw hardware capacity to :
    * managing **latency**, 
    * **consistency**, 
    * and **network orchestration**.