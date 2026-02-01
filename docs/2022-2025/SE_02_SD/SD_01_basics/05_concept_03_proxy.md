# Proxy
## Overview
- https://www.youtube.com/watch?v=qbuMKSTv3yU
- important concept in designing Distributed design

### Forward Proxy
- proxy configured at client side
- Acts on behalf of the client
  - The client's requests --> forward proxy --> reaches to the server
  - server response --> forward proxy--> client 
- Helps clients :
  - bypass firewall restrictions 
  - conceal their identity, as the origin server only sees the forward proxy's IP address (1:24-1:31). 
- eg: A VPN is a common example 👈🏻

### Reverse Proxy
- Acts on behalf of the servers
- also known as a **gateway**
- appears as the destination server to the client
- The client's request --> reverse proxy --> forwards it to the actual server
- use case:
  - **Enhances security** by hiding the original web server's information
  - cache static assets like images and HTML
  - LB, distribute requests among multiple servers
- eg: control panel in K8s 👈🏻
