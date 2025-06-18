- references
  - www.rabbitmq.com/tutorials
  - https://chatgpt.com/c/67532c74-a6d8-800d-8ad3-13afd07dcd8e
  - https://docs.spring.io/spring-boot/reference/messaging/amqp.html#messaging.amqp
  - https://www.rabbitmq.com/tutorials/tutorial-two-java-stream - `stream`
    - skip
    - using Kafka
---
# RabbitMQ
- **Producer**  --> **Exchange** >> **binding** >> **Queue** --> **consumer**
- open-source message broker system
- use-case : Distributed system, microservices, etc
- `message persistence` 
- `acknowledgments`
- `Scalability`:

---
## A install (docker)
```
docker run -d --hostname my-rabbit --name my-rabbit -p 5672:5672 -p 15672:15672  rabbitmq:3-management -e ssl_options.verify:verify_none rabbitmq:latest
>> console : guest/guest http://localhost:15672/
```
---
## B Exchange 
- Type:
  -  `Direct`** : Routes messages to queues based on `message routing key`.
  -  `Topic`    : Routes messages to queues based on `"wildcard matching" of the routing key`.
  -  `Fanout`   : Routes messages to `all queues` bound to it.
  -  `Headers`  : Uses `message headers` for routing.
- **Binding**: relationship between an exchange and a queue 

---  
## Queues:  
- Buffers that store messages until consumed

### 1. Standard Queue
- **Description**: Basic FIFO (First In, First Out) queue.
- **Features**:
  - Messages are delivered in the order they arrive.
  - Messages are removed once consumed (unless re-queued).
- **Use Case**: General-purpose message processing.

---

### 2. Priority Queue
- **Description**: Messages are prioritized based on a priority level assigned during publishing.
- **Features**:
  - Higher-priority messages are delivered first.
  - Configured using the `x-max-priority` argument.
- **Use Case**: Scenarios requiring prioritization, such as alerting systems.

---

### 3. Lazy Queue
- **Description**: Stores messages on disk rather than in memory.
- **Features**:
  - Designed for handling large queues.
  - Reduces memory usage but increases disk I/O.
- **Use Case**: High-volume message storage where memory efficiency is critical.

---

### 4. Quorum Queue (Recommended for New Projects)
- **Description**: A distributed queue type for high availability and data integrity.
- **Features**:
  - Uses the Raft consensus algorithm.
  - Replaces classic mirrored queues.
  - Supports leader elections and replication across nodes.
- **Use Case**: Applications needing high reliability and fault tolerance.

---

### 5. Classic Mirrored Queue (Deprecated in RabbitMQ 3.9+)
- **Description**: Replicates messages across multiple nodes for high availability.
- **Features**:
  - Each node has a mirror of the queue.
  - Message replication can cause performance overhead.
- **Use Case**: Legacy setups requiring HA before quorum queues were available.

---

### 6. TTL Queue
- **Description**: Messages in this queue expire after a specified time-to-live (TTL).
- **Features**:
  - Set TTL per queue or message using the `x-message-ttl` argument.
  - Expired messages are dropped or moved to a dead-letter exchange.
- **Use Case**: Temporary message holding, like caching.

---

### 7. Dead Letter Queue (DLQ)
- **Description**: A queue to which messages are routed if they are rejected or expired.
- **Features**:
  - Helps capture unprocessable messages.
  - Configured using dead-letter exchanges.
- **Use Case**: Error handling and debugging.

---

### 8. Exclusive Queue
- **Description**: A queue limited to a single connection.
- **Features**:
  - Automatically deleted when the connection closes.
  - Private to the declaring consumer.
- **Use Case**: Temporary or session-specific tasks.

---

### 99 Key Differences Between Queue Types

| **Queue Type**       | **Message Storage** | **High Availability** | **Priority Support** | **Order Guarantee** |
|-----------------------|---------------------|------------------------|-----------------------|---------------------|
| Standard              | Memory/Disk        | No                    | No                   | Yes                |
| Priority              | Memory/Disk        | No                    | Yes                  | No (priority-based) |
| Lazy                  | Disk               | No                    | No                   | Yes                |
| Quorum                | Disk               | Yes                   | No                   | Yes                |
| Classic Mirrored      | Memory/Disk        | Yes                   | No                   | Yes                |
| TTL                   | Memory/Disk        | Optional              | No                   | Yes                |
| Dead Letter           | Memory/Disk        | Optional              | No                   | Yes                |
| Exclusive             | Memory/Disk        | No                    | No                   | Yes                |

---
## C Scalability
- Supports clustering

---
## D Plugins


