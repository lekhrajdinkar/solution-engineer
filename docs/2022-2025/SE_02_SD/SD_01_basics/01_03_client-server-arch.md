# client-server architecture
- dns concept
- http 80 | https  8443
- `nslookup` command
- ...

---
## ✔️Polling
### ➖Short Polling
- https://www.youtube.com/watch?v=b4qyOpGg748
- client repeatedly requests data from a server **at set intervals** 
  - using any network protocol.eg: https, etc
- **problem** : it creates many new connections and often results in empty responses.
- eg:
  - Temperature Monitoring
  - AJAX application polls bts
  - not ideal for real-time applications like chat
- **reducing** the polling interval
  -  it significantly increases the **load on the server**, 
  - as clients send many **unnecessary requests**.
  
> ℹ️ suitable for data that doesn't **need to be updated very frequently**

![img.png](../../../99_img/2026/02/07/03/img.png)

### ➖Long Polling
- A variation where the server **holds the client's request** 
  - `hanging GET (with timeout)` 👈🏻
- until data is available **or** a timeout occurs
  - This allows the server to "push" information, 
  - but clients still need to reconnect periodically after timeouts
  - https://www.youtube.com/watch?v=pnj3Jbho5Ck (02:00)

**problem**: since holds client's request, thus resource intensive.

![img_1.png](../../../99_img/2026/02/07/03/img_1.png)

---
## ✔️Streaming
- https://www.youtube.com/watch?v=b4qyOpGg748
- the client opening a **long-lived connection** with the server, 
- typically through a **socket**, 
- allowing the server to **push** information **without a client request**
- Analogy: client opens a file and server can write any moment until client closes file.

> ℹ️ **instantaneous experiences**
> - the server proactively sends or "pushes" data to the client, 
> - rather than passively waiting for requests.
> - Enables a **continuous flow of data**

---
## ✔️Pub-Sub 
Async comm between client-server

([🔗kakfa](../../PE_03_message-broker/kakfa) is popular tool) 👈🏻

> **At Least Once Delivery**
>   - subscriber receives a message but loses connection before acknowledging it.
>   - leading the topic to re-send the message when the connection is re-established
>   - **idempotent operation** yields the same outcome regardless of how many times it's performed
> 
> **Message Ordering** : "first-in, first-out" (FIFO)
> 
> **Message Replay**, due to their underlying persistent storage

**Components(4)**
- Publishers:
- Topics: 
  - Act as channels or intermediaries with specific information.
  - Persistent Storage via Topic
- Subscribers: 
  - Clients that listen for data from topics.
  - can subscribe to multiple topics based on their needs.
  - Unlike streaming, subscribers listen to the topic, not directly to the publishers
- Messages: Represent data or event

**More**
- **separation of concern**. separate topic for each business domain.
- **Content based filter** subscriber to filter data

---
## ✔️fan-out (opposite of pull)
**Concept**
- `Twitter` 2012-2013 problem : https://www.youtube.com/watch?v=FEkXjNFrL1o
```
Twitter had 150 million users 
 handled write - 6,000 tweets per second. 
 Challenge-1:
  - read requests: 300,000 requests per second to serve homepages
    - User timeline 
    - Home timeline
  - Fix-1: Adding indices speeds up reads but slows down writes.
           Since reads are more frequent than writes, this is a fair trade-off.
           
  - Fix-2: 
    - pre-computed and stored user home timelines in a Redis cluster
    - Twitter serves the cached timeline from Redis, significantly reducing latency
    - When a user tweets, the tweet is replicated into the home timeline queue of each follower, 
    - resulting in thousands of writes to redis, for a single tweet
    - this is fanOut 👈🏻
  
```
![img.png](../../../99_img/2026/02/07/04/img.png)

---
## ✔️Peer 2 Peer
https://www.youtube.com/watch?v=2v6KqRB7adg

![img.png](../../../99_img/2026/02/07/02/img.png) ![img_1.png](../../../99_img/2026/02/07/02/img_1.png)

**Example of transferring large video files to thousands of machines**
1. single server approach (10 videos, 5GB each) - `15 min`
2. sharding, 5 server (2 videos each, 5GB each) - `15/5 = 3 min`
3. P2P solution - `1 sec`
   -  large file is split into small chunks and distributed among peers
   - These peers then communicate with each other in **parallel** to assemble the complete file
   - **peer discovery** 
   - **peer selection strategies** within a P2P network
   - Centralized database (tracker), Gossip protocol, distributed hash table (DHT)
    
---
## ✔️Web protocol

### ➖ Rest (https/tls)
- [security/03_protocol_https_tls.md](../SD_02_security/03_protocol_https_tls.md)
- short live http/s connection. handshake takes time. 
- open-close, open-close, ...

### ➖ Web Socket (ws)
> **Full Duplex async messaging**
> - https://www.youtube.com/watch?v=pnj3Jbho5Ck 1
> - https://www.youtube.com/watch?v=G0_e02DdH7I 2
- ws:// or  wss://
- check short and Long Polling problem.
- Flow:
  - **Https/TCP handshake** === same
  - **negotiate to upgrade** to WS with request header:
    - `Upgrade: websocket`, 
    - `Connection: Upgrade`, 
    - `Sec-WebSocket-Key: xxxxxxxx`
  - server validates:
    - response code `101` / Switching Protocols, 
    - response header:
      - `Sec-WebSocket-Accept: zzzzzzzzz`, 
      - which is generated by concatenating the client's key with a GUID 
      - and applying SHA-1 hashing.
  - establishes a persistent, **bi-directional connection**/ tunnel
  - both client/server, 
    - can stream **data frame**, uninterrupted 👈🏻
    - simultaneous sending and receiving
  - either, initiate close connection

**Data frame**
```
FIN bit : Indicates if it's the final fragment of a message.
RSV bits : Reserved for future use.
Opcode : Defines the type of data (text, binary, ping, etc.).
Mask bit : Indicates if the payload data is masked (always for client-to-server frames).
Payload length : Defines the length of the data.
Masking key : Used to obscure payload data.
```
**Masking**

**Fragmentation**
- splitting large messages into smaller chunks 
- to prevent buffer overflow 
- and allow for gradual delivery of data. 
- The FIN bit is used to indicate whether a fragment is the final part of a message.

**Real-time applications**
```
WebSockets are ideal for:
    Stock trading websites displaying live price fluctuations 
    Chat applications
    Gaming applications that require automatic UI refreshes
```

### ➖ SSE / server sent event
- designed for streaming **textual data** over HTTP
- SSE is a unidirectional protocol
- server pushes data to the client over a single, long-lived HTTP connection.

### ➖ GRPC


---
## ✔️ Event-driven architecture (push based) 👈🏻
### Synchronous  / with webhook
- just **Http Post** with event data.
- https://www.youtube.com/watch?v=oQaJn6RdA3
- traditional: polling, long-live connection
  - eating resources
- Webhooks allow servers 
  - to notify client applications 
  - only when new events occur, rather than requiring clients to check periodically.
- eg: gitHub make post call --> harness trigger (POST /api, idempotent), payload: {eventId...}
- benefit:
  - Webhooks improve system performance, 
  - reduce latency, 
  - and are crucial in modern microservices architectures for enabling system decoupling

![img_1.png](img_1.png)

### Async processing
- push to message broker - SQS, Kafka, rmq 👈🏻
- DLQ for failed events
- [message-broker](../../PE_03_message-broker)
- [AWS SQS](../../CE_02_AWS_SAA/05_decoupling)


---
## ✔️Videos Streaming / ABS
- https://www.youtube.com/watch?v=kCAXpAikMVc
- ABS **Adaptive Bitrate Streaming**
  - adjusts video quality based on the viewer's internet
  - ABS works by encoding video at **multiple bitrates**
- Types of Video Streaming
  - Live streaming: 
  - On-demand streaming
  - Peer-to-peer streaming: Distributing content where viewers share their bandwidth and computing resources
### ➖DASH - Dynamic Adaptive Streaming over HTTP 
popular

![img.png](img.png)
### ➖HLS - HTTP Live Streaming
### ➖RTMP - realTime messaging Prot

