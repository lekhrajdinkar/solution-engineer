# Network

## 1. Core concepts
**TCP connection**
- TCP handshake
- TCP connection established | tunnnel
- http/https provides abstraction over TCP
- py, java, etc, all has lib to comm with http/https

```mermaid
flowchart LR
    A[Application: Python / Java] --> B[HTTP / HTTPS]
    B --> C[TCP Connection]
    C --> D[IP / Network]
```

**LocalHost**
- **localhost** resolves to` 127.0.0.1/8` or `::1/8` (ipv6)| loopback interface
- `c\:window\System32drivers\etc\host`

**IP address**
- Ipv4 (4.3 billion only) 
- Ipv6 (340 undecillion)
- OS is configured to switch over formats:
  - socket.getHostByName('localhost') --> can resolve to ipv6 or ipv4

```mermaid
flowchart LR
    APP[Application]

    APP --> LOOP["localhost<br/>127.0.0.1<br/>Same machine"]

    APP --> PRIVATE["Private IP<br/>192.168.x.x / 10.x.x.x<br/>Private network"]

    APP --> PUBLIC["Public IP<br/>8.8.8.8<br/>Internet"]

    PRIVATE --> NAT[NAT / Router]
    NAT --> PUBLIC
```
| Type                     | Example                        | What it means                                       |
| ------------------------ | ------------------------------ | --------------------------------------------------- |
| **Public IP**            | `8.8.8.8`, `104.26.9.238`      | Globally routable on the Internet                   |
| **Private IP**           | `192.168.1.1`, `192.168.1.100` | Used inside a private network such as your home/VPC |
| **Localhost / Loopback** | `127.0.0.1`                    | Refers back to the same machine                     |


## 2. DNS
- https://youtube.com/watch?v=Lsd80uR9Shs (skip)

## 3. Network protocol
- https://youtube.com/watch?v=jQ6_XhsMwws
- TCP (reliable delivery)
- UDP ( best effort delivery, superfast, packet might get lost or unorders)
- HTTP (browser language, `stateless` ) 
- [http-evolution](01_basic_02_http-evolution.md)
- HTTPS 

## 4. Topologies
- https://youtube.com/watch?v=yBY5GJtmhg0
- https://youtube.com/watch?v=4znRDbg0SYA

## 5. NgInx
- https://youtube.com/watch?v=I6dpN0geIb4
- [event-loop.md](../../SD_01_foundation/05_concept_04_event-loop.md)