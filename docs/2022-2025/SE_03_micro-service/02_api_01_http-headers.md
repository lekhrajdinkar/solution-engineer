# Http headers

## Overview
https://www.youtube.com/watch?v=fFHl7psnvz0 bm

**Authorization**
- `Bearer xxxxx`

**Content-Type**
- Specifies the media type of data being sent 
- `application/json`
- `text/html`

**Accept**
- Informs the server  about the media types the client can handle or prefers to receive.
- else server gives `406 Not acceptable`

**Cache-Control** 
- Manages browser caching behavior to improve performance.

**Set-Cookie** 
- Allows the server to send small pieces of data (cookies) to the client's browser
- to remember the client's state
- send only over https
- client send is in each subsequent request

**CORS (Cross-Origin Resource Sharing)** 
- [07_vul_cors.md](../SE_02_distributed-system/SD_03_security/07_vul_cors.md)

**X-Frame-Options**
- `X-Frame-Options:DENY`
- springBoot automatically adds this.
 