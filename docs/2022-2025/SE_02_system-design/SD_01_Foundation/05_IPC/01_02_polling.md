#  synchronous Communication :: Polling
- https://www.youtube.com/watch?v=pnj3Jbho5Ck bm lp
- https://www.youtube.com/watch?v=b4qyOpGg748 bm sp
- https://www.hellointerview.com/learn/system-design/patterns/realtime-updates#long-polling-the-easy-solution | check 

---
## Short Polling
### Overview
- client repeatedly requests data from a server **at set intervals** 
- using any network protocol.eg: https, etc


```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server
    C->>S: Any updates?
    S-->>C: No
    Note over C: Wait 2-5 seconds
    C->>S: Any updates?
    S-->>C: No
    Note over C: Wait 2-5 seconds
    C->>S: Any updates?
    S-->>C: New data
```

**sample code**

```javascript
async function poll() {
  const response = await fetch('/api/updates');
  const data = await response.json();
  processData(data);
}

// Poll every 2 seconds
setInterval(poll, 2000);
```

### use case
- Temperature Monitoring
- AJAX application polls bts

> not ideal for real-time applications like chat

### pros
```
- Simple to implement.  
- Stateless. 
- No special infrastructure needed.  
- Works with any standard networking infrastructure.  
``` 
### cons
```
- Limited update frequency.
  - reducing the polling interval, significantly increases the load on the server
- More bandwidth usage.
- Can be resource-intensive with many clients, establishing new connections, etc.

```
---
## Long Polling
### Overview
> Long polling = normal HTTP request + server intentionally delays the response.
- A variation where the server **holds the client's request** `hanging GET (with timeout)` 
- until data is available **or** a timeout occurs
  - This allows the server to "push" information, 
  - but clients still need to reconnect periodically after timeouts

**sample client side code**

```javascript
// Client-side of long polling
async function longPoll() {
  while (true) {
    try {
      const response = await fetch('/api/updates');
      const data = await response.json();
      
      // Handle data
      processData(data);
    } catch (error) {
      // Handle error
      console.error(error);
      
      // Add small delay before retrying on error
      await new Promise(resolve => setTimeout(resolve, 1000));
    }
  }
}
```

```mermaid
sequenceDiagram
    autonumber
    actor Client as Client
    participant Server as Server
    actor Updates as Updates (Data Source)

    %% Request 1 - Wait and receive Update 1
    Client->>Server: 1. Poll Request (Long Poll)
    activate Server
    Note over Server: Server waits (holds request open)<br>until data arrives or timeout
    
    Updates-->>Server: Event: Update 1 occurs
    Server-->>Client: 2. Response with Update 1
    deactivate Server

    %% Immediate re-poll
    Client->>Server: 3. Immediately send new Poll Request
    activate Server
    
    %% Brief wait before Update 2
    Updates-->>Server: Event: Update 2 occurs
    Server-->>Client: 4. Response with Update 2
    deactivate Server

    %% Request 3 - Long wait (Idle state)
    Client->>Server: 5. Next Poll Request
    activate Server
    Note over Server: Server enters extended waiting state<br>(holding connection open)
    deactivate Server
```

### pros and cons
- similar to short polling

| **Advantages**                                                                         | **Disadvantages**                                                                                                                   |
| -------------------------------------------------------------------------------------- |-------------------------------------------------------------------------------------------------------------------------------------|
| Builds on **standard HTTP** and works with HTTP-compatible clients and infrastructure. | 🔺**Higher latency** than persistent push mechanisms such as WebSockets or SSE.                                                       |
| **Easy to implement** compared with WebSockets.                                        | **HTTP overhead** because a new request is made after every response/timeout.                                                       |
| No special protocol or infrastructure required.                                        | Can become **resource-intensive at scale** because many requests remain open simultaneously.                                        |
| Can work with a **stateless application model** across requests.                       | Not ideal for **frequent updates** because of repeated request/response cycles.                                                     |
| Works well with existing **HTTP proxies, load balancers, and firewalls**.              | 🔺**Monitoring is harder** because requests can remain open for long periods.                                                       |
| —                                                                                      | 🔺**Browser connection limits** can restrict how many simultaneous long-polling connections a client can maintain to the same origin. |
| —                                                                                      | Although the application can be stateless, the server/LB still needs resources for each **outstanding connection**.                 |

### Long polling with a load balancer

```
Request 1
Client → LB → Server 1
              │
              └── waiting for new data


Request 2
Client → LB → Server 2
              │
              └── doesn't know about Server 1's waiting request
```
```
✔️A better architecture is usually:

                ┌──► Server 1  ──┐
                │                │
Client → LB ────┼──► Server 2  ──┼──► Shared Event Store⭐
                │                │       / Pub/Sub 
                └──► Server 3  ──┘
```


---
## Compare: long vs short polling

```mermaid
sequenceDiagram
    autonumber
    actor Client as Client
    participant Server as Server

    rect
    Note over Client,Server: Regular Polling (Fixed Frequent Intervals)
    Client->>Server: Request 1
    Server-->>Client: Response 1 (Immediate / often empty)
    
    Client->>Server: Request 2
    Server-->>Client: Response 2 (Immediate / often empty)
    
    Client->>Server: Request 3
    Server-->>Client: Response 3 (Immediate / often empty)
    end

    rect
    Note over Client,Server: Long Polling (Server Holds Request Open)
    Client->>Server: Request 1
    activate Server
    Note over Server: Server waits (holds open)<br>until data arrives or timeout
    Server-->>Client: Response 1 (Sent when data is ready)
    deactivate Server

    Client->>Server: Request 2 (Immediately re-polls)
    activate Server
    Note over Server: Server waits again...
    Server-->>Client: Response 2 (Sent when data is ready)
    deactivate Server
    end
```

