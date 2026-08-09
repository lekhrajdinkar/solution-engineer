#  stateful and stateless architectures
- https://www.youtube.com/watch?v=QDiPjMWeVC0

## Stateful Arch
- Stores session data on a specific server or device
- If we switch devices or the server fails, we lose your session data
- Not scalable
  - managing session data across multiple servers requires additional infrastructure.

---
## Stateless Arch
> Distributed System
> - make server/node **scalable**
> - Also contributes to **consistency** (central-state or Distributed state) 
> - **Reliability** : node-1 fail, can seamless switch to node-2, without loss of session Data.

- Every request contains ONLY all necessary information, 
- and **session-data** is stored in additional infrastructure:
  - **shared central Session storage**:
  - **distributed Session storage** : AWS (ebs,efs,s3,etc)
  - avoid sticky session ❌
- so it's not like, no-state, but state is maintained outside the node. 👈🏻

## Handling Session Data in Distributed Systems

| Approach                        | How it works                                               | Advantage                           | Problem                                                        |
| ------------------------------- | ---------------------------------------------------------- | ----------------------------------- | -------------------------------------------------------------- |
| **Centralized Session Storage** | All app instances use one shared session store, e.g. Redis | Any server can access session       | Store can become bottleneck / SPOF unless replicated           |
| **Sticky Sessions**             | Load balancer keeps routing a user to the same server      | Simple                              | Poor scalability, uneven load, server failure can lose session |
| **Distributed Session Storage** | Session data is spread/replicated across multiple nodes    | Better scalability and availability | More operational complexity                                    |

## Stateless Architecture :: Common Real-World Uses

| Use Case                        | How Statelessness Helps                                                                       |
|---------------------------------| --------------------------------------------------------------------------------------------- |
| **Social Media Platforms**      | User/session state can be stored centrally; requests can be handled by any backend instance   |
| **Token-Based Authentication⭐** | JWT/access token travels with each request instead of server-local session state              |
| **Microservices Architecture⭐**  | Services can scale independently because instances don't depend on local session state        |
| **CDNs**                        | Distributed edge servers serve content without maintaining per-user application session state |
| **Load Balancing**              | Requests can be routed to any healthy instance                                                |
| **Auto Scaling**                | Instances/pods can be added or removed without migrating session state                        |
| **Rolling Deployments**         | Old instances can be replaced without losing user sessions                                    |

