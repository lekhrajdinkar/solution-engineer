# Pattern 3: multi-step-processes
## reference
- https://www.hellointerview.com/learn/system-design/patterns/multi-step-processes
- [03_04_distributed-Transaction.md](../SD_05_DataModeling/02_basic_concepts/03_04_distributed-Transaction.md)

external post
- https://www.youtube.com/watch?v=VvUdvte1V3s | "Six Little Lines of Fail"
- https://www.linkedin.com/blog/engineering/distributed-systems/log-what-every-software-engineer-should-know-about-real-time-datas-unifying

drawings
- https://excalidraw.com/#json=TQ-kHabdMDaCbCp2p2lzk,R_1RoTN1tcfPXrh5m-FX-A
- https://excalidraw.com/#json=BC-2X_o_NaL1PP309r618,wLeY0o5z_aT6zUXXqaynpQ | saga c vs o

--- 
## Problem
Real applications often need to **coordinate** dozens of (flaky) services and systems. eg: **e-commerce order** fulfillment workflow: 
[e-commerce-workflow :: edge-cases](draw/03_multi-process/01_02_e-commerce-workflow-edge-cases.excalidraw)

> complex and brittle:
>  - **business-level concerns** : The messy **complexity** of business needs.
>  - **system-level concerns** (crashes, retries, failures, hooks to handle waits,etc)

---
**Solution for reliable system** ⭐
- starting simple
- sagas pattern
- Event-Driven pattern
- Workflow systems

--- 
## 1. Single Server Primitives
[e-commerce order :: failure-scenarios](draw/03_multi-process/01_04_order-processing-system-failure-scenarios.excalidraw)
- **Crashes and Failures**: 
  - eg: server crashes after charging payment but before reserving inventory
  - if server dies and new comes. it has to know exactly where last one left off.
- **Callback Routing**: eg: If we've scaled out to several API servers behind a load balancer, that callback can land on a completely different host

[e-commerce order :: patch-1](draw/03_multi-process/01_03_order-processing-system-architecture.excalidraw)
**patch-1**
- persist the order's state to a **database** after each step so no single host has to remember it,
- route callbacks through a **pub/sub layer**

**More issues:**
- **Error handler**:
  - Database remembers our progress, but it doesn't act on it.  If a server dies mid-order, the row just sits there.
  - Something has to notice the stalled order, decide which step is safe to retry,
  - That's a poller, a locking scheme, and retry logic,
- haven't solved **compensation**
- ...
- The never-ending patching list is indicative of a **structural problem**
> The diagram actually doesn't look that bad, but a real world implementation is a **tangled ball of wires and spaghetti code.**

---
## 2. The Saga Pattern
### Overview
wrap all the steps in one big distributed transaction
- [SAGA](../SD_05_DataModeling/02_basic_concepts/03_04_distributed-Transaction.md) is sequence of local steps, where each step has a matching compensating action.
- You run the steps one at a time, 
- and if a later one fails, you walk backwards, firing those compensations
- Each step commits on its own, so nothing **holds a lock across services**
- each need their own retries and idempotency
> rather than guaranteeing that all the steps happen together or not at all, like in 2 Phase commit. They guarantee that **whatever did happen can be undone.**

2 ways to coordinate a saga.

### choreography
-  there is no coordinator at all. 
- Each worker 
  - subscribes to the events it cares about, 
  - does its piece, 
  - and emits new events for the next worker to react to.
- The overall flow isn't written down anywhere; it emerges from those reactions
- Choreography works great for **fully independent services**  (e.g. those owned by **independent teams** that are not tightly coupled) and for mid-complexity workflows.

[saga-choreography-event-flow.excalidraw](draw/03_multi-process/02_01_saga-choreography-event-flow.excalidraw)

### orchestration
- where a **single coordinato**r owns the flow and tells workers what to do.
  - that coordination has to survive crashes 👈
  - bookkeeping
  - state outlives crashes, retries, and all the compensation logic
- Because the whole **sequence lives in one place**, 
- orchestration works best for **very complex workflows** where you need central control and **visibility**.

### orchestration vs choreography
[02_02_saga-choreography-vs-orchestration.excalidraw](draw/03_multi-process/02_02_saga-choreography-vs-orchestration.excalidraw)

---
## 3. Event-Driven Choreography
### Overview
it's a close cousin of `event sourcing`
- same as **Single Server Primitives Approach**, but with one evolution step:
- stop storing the **current state** 
- and instead store the **stream of events**, that got us there.
- workers react to those events to **drive the process forward** for coordination
> - `Event-sourcing` treats this event log as the **source of truth** and **derives state** from it. 
> - but here we're mainly using the log to **coordinate work**
- Our API service is now just a thin initiating **wrapper around the event store** | they are now just workers who consume events :)

**event Store:** `Kafka`,  `Redis Streams`
- it holds the entire history of the system
- Workers (ordinary service processes **subscribed** to the log) consume events, perform their work, and emit new events.

[event-driven-Choreography](draw/03_multi-process/03_01_event-driven-order.excalidraw)

### benefit
- Fault tolerance:
  - Workers consume the log as a **consumer group**
  - If a worker dies, its **partitions are reassigned** and the next worker resumes from the last committed position
  -  workers need to be  workers need to be idempotent
- Scalability:  add more workers to handle higher load
- Observability: log itself is a complete audit trail of everything that happened
- Flexibility: Appending a new reaction is easy | but Inserting a step into the middle of the chain is harder

### Example - Fund of fund service
[fund-allocation-outbound-high-level.excalidraw](draw/03_multi-process/fof/fund-allocation-outbound-high-level.excalidraw)

---
## 4. Workflow Orchestration

--- 
## interview

--- 
## Deep dives

--- 
## Conclusion

---
## 🎯 use case
> particularly when there is a **lot of state** and a lot of **failure handling**

```
- Uber
- Payment System
- Notification System
- agent pipelines, are long chains of exactly these flaky, stateful steps
```
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/uber#multi-step-processes | `uber`
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/payment-system#multi-step-processes
- https://www.hellointerview.com/learn/system-design/problem-breakdowns/notification-system#multi-step-processes




