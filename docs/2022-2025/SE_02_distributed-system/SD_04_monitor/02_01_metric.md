# Metrics
## ✔️ latency
- performance metrics
- tradeoff : latency vs accuracy.
- time it takes for data to travel from one point in a system to another.
- could be:
  - **network latency**, time for a request to go from client to server and back.
  - **machine latency**, time to read data from memory or disk.
  - ...
```
-- estimated time in microSec, to  --
read 1MB for Data from:
    memory - 250 
    SSD - 1000
    1GBPS network - 10k
    HDD - 20k
So there is latency always. no need to remeber, just of idea.
```

**Evaluate** https://www.youtube.com/watch?v=lJ4NEMNBeS4
- P90 Latency : evaluate user experience, check on aws metrics
- P95 Latency :
- P99 Latency :
- Median latency: 

> percentile : sort the data set and determining the index position of the desired percentile
> Median : sort the data set and determining the (mid+midNext)/2
> 

---
## ✔️ Throughput
- performance metrics
- the amount of work a computer can perform within a given time.
- could be:
  - data transferred per second
  - no of http req per sec
  - ...

---
## ✔️ Bitrate
- use to measure video streaming
