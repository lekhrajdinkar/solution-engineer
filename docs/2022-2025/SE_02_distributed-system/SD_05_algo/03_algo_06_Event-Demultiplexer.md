# Event Demultiplexer
https://www.youtube.com/watch?v=h125O5yvdg0

## overview
fast i/o operation:
- **RAM access**, which takes `nanoseconds` fast i/o
  
slow i/o operation:
- **disk access**, **network calls**, take `milliseconds` 
- **user interactions**, takes  `minutes` 

> - When a **thread** encounters an I/O task, it becomes blocked, causing the **CPU to sit idle**
> - While **creating more threads** might seem like a solution,  but
>   - it consumes more memory and CPU resources, 
>   - and managing numerous threads can lead to issues like race conditions or deadlocks, 
>   - ultimately slowing down performance
> 
> A more efficient solution is the **Event Demultiplexer** 👈🏻

Operating systems provide built-in support:
- Linux uses `epoll` 
- Mac OS uses `kqueue` 
- Windows uses `IOCP`

Node.js utilizes the `libuv` library to implement the event demultiplexer concept

---

## Concept: Event Demultiplexer
...