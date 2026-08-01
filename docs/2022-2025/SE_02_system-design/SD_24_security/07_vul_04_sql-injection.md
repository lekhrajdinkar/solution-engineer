# SQL Injection
## Overview
- oldest hacks
- https://www.youtube.com/watch?v=cG4FPks53AU
- blind sql injection:
  - true/false
  - sleep

## Fix
- Always Use **prepared statement** in java
  - keep the query separate fron user Input
- Sanitize user input
- Use WAF to monitor suspicious activity
- Monitor logs for suspicious activity