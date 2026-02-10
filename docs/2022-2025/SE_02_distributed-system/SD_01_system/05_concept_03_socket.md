# Socket
## Overview
https://www.youtube.com/watch?v=NvZEZ-mZsuI

> - **socket** is opened b/w client and server 
> - when **connection** is opened 
> - and **request** goes through this socket channel

![img.png](../../../99_img/2025/se_02_sd/08/01/img.png)
### Socket
combination of:
  - an **IP** address (identifying a device) 
  - a **port** number (identifying an app or service on that device), 
  - together forming a **socket address**
  - It's a **two-way communication channel**, like a phone line, allowing two devices to talk

> languages like Python, Java, Go, and Rust use their own wrappers or system calls 
> to interact with the OS's underlying C-based socket APIs

```
- A socket is a just software object or endpoint, which talk s to OS only, not to network
- create a socket in Python (e.g., socket.socket) 
- OS provides a "file descriptor" (a "ticket") 
    - that allows your program to read from and write to the network.
    - handles low-level details like IP routing and DNS resolution.
- python example:
    - socket.socket
    - s.connect("abc.com", 80)
    - s.recv(8080) | s.bind('abc',8080); s.listen()
```
![img_1.png](../../../99_img/2025/se_02_sd/08/01/img_1.png)

![img_2.png](../../../99_img/2025/se_02_sd/08/01/img_2.png)

![img_3.png](../../../99_img/2025/se_02_sd/08/01/img_3.png)

---
### request
 - is the data that travels inside the connection, 
 - such as an HTTP-Get, WebSocket-message, etc

---
### Connection / socket-connection
💠**Stream Sockets** 
- not built for browser
- `TCP` connection
- Provide reliable, ordered communication
- where data arrives in the correct order without glitches
- eg: Netflix streaming, real-time chat apps, dashboards, file sharing

💠**Datagram Sockets**
- not built for browser
- `UDP` connection
- Offer fast communication but can lose data
- eg: used in multiplayer games where speed is more critical than perfection, IoT

💠**Web Sockets**
- built for direct browser use ✔️ | modern
- `TCP` connection under the hood.
> - **http/1**    --> 1 connection --> 1 request --> close ❌
> - **http/1.1 (keep alive)**  --> 1 connection --> multiple request
> - **http/2.0**  --> 1 connection --> multiple request (parallel/concurrent)
> - **http/3**
> - **✔️Web-Socket** --> 1 connection --> persisted 2 way comm

- WebSockets sit on top of regular **TCP sockets** but are browser-friendly 👈🏻
- A WebSocket connection starts as an ordinary HTTP request 
- but then upgrades to a persistent, full-duplex connection, 
- allowing both browser and server to send messages at any time without polling

[02_arch_01_client-server-arch.md](02_arch_01_client-server-arch.md)

---
## Advance concepts 
### Scaling with epoll and kqueue
> backbone of high-performance servers like **Nginx**
- To handle thousands of open sockets efficiently without constantly checking each one (polling), 
- modern servers use **event-based models**.
  - **epoll (Linux)** 
  - **kqueue (macOS/BSD)** 
  - they allow the OS to notify the server, only when specific sockets have data, 
  - thus preventing CPU waste

![img_4.png](../../../99_img/2025/se_02_sd/08/01/img_4.png)