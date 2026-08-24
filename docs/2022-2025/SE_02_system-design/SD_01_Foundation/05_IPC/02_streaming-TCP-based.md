# Streaming (Http based) 
## Reference:
- [01_concept_01_socket.md](../../SD_04_network-essential/02_basic-concepts/01_concept_01_socket.md)⭐
- https://www.youtube.com/watch?v=pnj3Jbho5Ck | bm ws part-1 overview (2024) 
- https://www.youtube.com/watch?v=G0_e02DdH7I | bm ws part-2 details(2024) 
- https://www.youtube.com/watch?v=BKonNa7XPdg | bm ws part-3 more deep arch (2026) 
- https://www.hellointerview.com/learn/system-design/patterns/realtime-updates#long-polling-the-easy-solution | check

---
## A. Server-to-client
### 1. grpc client streaming __ 🔺
- gRPC does support streaming.
- it's not ideal for external APIs due to limited support (e.g. no browsers support gRPC today).

---
### 2. SSE 🌐
#### Overview
> think of it : 
> - SSE is a nice hack on top of HTTP /  extension/upgrade to long-polling because it eliminates the issues around **high-frequency updates**
> - that allows a server to stream many messages/(chunks)/textual data, over time 
> - in a **single response** from the server
> - over a **single HTTP connection** /  long-lived HTTP connection through **socket**

- don't have a polling interval to negotiate or tune. 👈
- **won't be super-long-lived** (e.g. `30-60s` is pretty typical), 
- So consider how **clients re-establish connections** and how they deal with the gaps in between.
  - If a client loses its connection, it can reconnect and provide the `last event ID` it received.
  - The server can then use that ID to send all the events that occurred while the client was disconnected
- **http header** :  `Content-Length` vs `Transfer-Encoding: chunked`
- **Modern browsers have built-in support for SSE** through the `EventSource` object

```javascript
const eventSource = new EventSource('/api/updates');

eventSource.onmessage = (event) => {
  const data = JSON.parse(event.data);
  updateUI(data);
};

// Server-side (Node.js/Express example)
app.get('/api/updates', (req, res) => {
  res.setHeader('Content-Type', 'text/event-stream');
  res.setHeader('Cache-Control', 'no-cache');
  res.setHeader('Connection', 'keep-alive');

  const sendUpdate = (data) => {
    res.write(`data: ${JSON.stringify(data)}\n\n`);
  };

  // Send updates when data changes
  dataSource.on('update', sendUpdate);

  // Clean up on client disconnect
  req.on('close', () => {
    dataSource.off('update', sendUpdate);
  });
});
```

```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server

    C->>S: GET /events
    S-->>C: HTTP connection stays open

    S-->>C: event: price=100
    S-->>C: event: price=101
    S-->>C: event: price=102
```
```
 ✔️HTTP APIs you'd get a single, cohesive JSON blob as a response from the server 
 that is processed once the whole thing has been received.
 
{
  "events": [
    { "id": 1, "timestamp": "2025-01-01T00:00:00Z", "description": "Event 1" },
    { "id": 2, "timestamp": "2025-01-01T00:00:01Z", "description": "Event 2" },
    ...
    { "id": 100, "timestamp": "2025-01-01T00:00:10Z", "description": "Event 100" }
  ]
}


✔️On the other hand, with SSE, the server can push many messages 
as "chunks" in a single response from the server:

data: {"id": 1, "timestamp": "2025-01-01T00:00:00Z", "description": "Event 1"}
data: {"id": 2, "timestamp": "2025-01-01T00:00:01Z", "description": "Event 2"}
...
data: {"id": 100, "timestamp": "2025-01-01T00:00:10Z", "description": "Event 100"}
```

#### when to use ⭐ 
- situations where you want clients to get notifications or events as soon as they happen.
- A very popular use-case for SSE today is **AI chat apps, stream new tokens (words)** to the user as they are generated to keep the UI responsive

#### pros

| **Advantage**                        | **Explanation**                                                                      |
|--------------------------------------| ------------------------------------------------------------------------------------ |
| **Built into browsers**  ✔️          | Uses the native `EventSource` API; no additional client library is required.         |
| **Automatic reconnection**   ✔️        | Browsers automatically attempt to reconnect if the connection drops.                 |
| **Works over HTTP**                  | Uses standard HTTP, making it easier to integrate with existing HTTP infrastructure. |
| **More efficient than long polling** | Keeps a persistent connection open instead of repeatedly creating HTTP requests.     |
| **Simple to implement**              | Straightforward model: client connects once, server continuously sends events.       |


#### cons
> - Not ideal for very interactive apps: WebSockets are usually better for chat, multiplayer games, etc.
> - Need both **browsers** and **all infra between** the client and server to support streaming responses.
> - https://dev.to/miketalbot/server-sent-events-are-still-not-production-ready-after-a-decade-a-lesson-for-me-a-warning-for-you-2gie

| **Disadvantage**                                    | **Explanation**                                                                                                                                                                      |
|-----------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **One-way communication** + Text-based              | Server → client only. The client needs separate HTTP requests to send data to the server.                                                                                            |
| **Proxy/networking issues**  🔺                     | Proxies/load balancers must be **configured** to avoid buffering or timeouts. Sometimes  may interfere with long-lived HTTP streaming connections, making issues difficult to debug. |
| **Browser connection limits**  🔺                   | Browsers may limit concurrent connections per origin, potentially restricting the number of SSE connections.                                                                         |
| **Long-lived connections complicate monitoring** 🔺 | Connections remain open for long periods, requiring careful handling of timeouts, connection counts, and load-balancer behavior.                                                     |


---
## B. client-to-Server
### 1 grpc client streaming __ 🔺
-  not ideal for external APIs due to limited support (e.g. no browsers support gRPC today).

---
## C. Bidirectional
### 1. grpc bidirectional streaming __ 🔺
-  not ideal for external APIs due to limited support (e.g. no browsers support gRPC today).

---
### 2. WebSocket / WSS 🌐
```
Additional infa 💲💲

        Persistent/Stateful connection:
Client  ◄══════════════════════► Server 
          send / receive
          send / receive
          send / receive
          
```

overview 1
- the client opening a **long-lived connection** with the server, typically through a **socket** 👈
- allowing the server to **push** information **without a client request** and vice versa. 

overview 2
- WebSockets provide a persistent, TCP-style connection between client and server, 
- allowing for real-time, bidirectional communication with broad support (including browsers). 
- Unlike HTTP's request-response model, WebSockets enable servers to push data to clients without being prompted by a new request. 
- Similarly clients can push data back to the server without the same wait.
- WebSockets are initiated via an HTTP "upgrade" protocol, which allows an existing TCP connection to change L7 protocols.
- This is super convenient because it means you can utilize some of the existing HTTP session information (e.g. cookies, headers, etc.) to your advantage.

> ⚠️ Just because clients can upgrade from HTTP to WebSocket doesn't mean that the infrastructure will support it. 
> Every piece of infrastructure between the client and server will need to support WebSocket connections.
> If you've ever implemented Websockets you've probably hit a bunch of issues with **firewalls, proxies, load balancers, 
> and other infrastructure** that don't support WebSocket connections.
> 
> ---
> WebSockets are powerful, but the infra required to support them can be expensive
> and the overhead of **stateful connections** (especially at scale) will require significant accommodations in your design. 
> Hold off unless you really need them!

#### use cases
> when you need high-frequency, persistent, bi-directional communication between client and server.
- Stock trading websites displaying live price fluctuations 
- Chat applications
- Gaming applications that require automatic UI refreshes

#### connection
```
- Https/TCP handshake
- negotiate to upgrade, to WS with request header:
    - `Upgrade: websocket`,
    - `Connection: Upgrade`,
    - `Sec-WebSocket-Key: xxxxxxxx`
- server validates:
    - response code `101` / Switching L7 Protocols,
    - response header:
        - `Sec-WebSocket-Accept: zzzzzzzzz`,
        - which is generated by concatenating the client's key with a GUID
        - and applying SHA-1 hashing.
- establishes a persistent, **bi-directional connection**/ tunnel
- both client/server, can stream **data-frame**, uninterrupted 👈🏻
- either, initiate close connection
```

```mermaid
sequenceDiagram
    participant C as Client
    participant S as WebSocket Server
    C->>S: HTTP request<br/>Upgrade: websocket
    S-->>C: 101 Switching Protocols
    Note over C,S: WebSocket connection established
    C->>S: Message
    S-->>C: Message
    S-->>C: Live update
    C->>S: Command
    S-->>C: Live update
    C->>S: Another command
```

```mermaid
flowchart LR
    A[Browser / Angular Client] -->|WSS| B[LB]
    B -->|WebSocket| C[Uvicorn]
    C --> D[FastAPI / starlette]
    D --> E["@app.websocket('/ws')"]
    wss["wss://localhost:8080/myapp/chat"]
```


#### Dataframe

![img.png](../../../../99_img/2025/dataframe-wss.png)

| Field              | Purpose                                                       |
| ------------------ | ------------------------------------------------------------- |
| **FIN**            | `1` = Final frame of the message, `0` = More fragments follow |
| **RSV (3 bits)**   | Reserved for extensions (normally `0`)                        |
| **Opcode**         | Type of frame (Text, Binary, Ping, Pong, Close, etc.)         |
| **Mask Bit**       | `1` if payload is masked (required for client → server)       |
| **Payload Length** | Size of the payload (7, 16, or 64-bit length)                 |
| **Masking Key**    | 4-byte key used to unmask client payload                      |
| **Payload Data**   | Actual application data                                       |

Fragmentation:
- splitting large messages into smaller chunks to prevent buffer overflow
- and allow for gradual delivery of data.
- The FIN bit is used to indicate whether a fragment is the final part of a message.

#### Interview Takeaways
- WebSocket communicates using frames, not HTTP requests.
- Each frame contains a header + payload.
- FIN indicates whether more fragments follow.
- Opcode identifies the frame type (text, binary, ping, pong, close).
- Client → Server frames are masked; 
- Server → Client frames are not masked.
- Ping/Pong maintains long-lived connections and detects disconnects.


