# Performance Metrics
## 1. Latency
- **latency has tradeoff with accuracy.** 👈
  - latency sensitive system : online games, video games, etc
  - latency tolerant system: Airline booking, banking system, etc
- Time taken for data to travel from one point in a system to another.
- example:
```
 1MB for Data from:
    memory        - 250  microSec   | storage latency
    SSD           - 1000 microSec   | storage latency
    1GBPS network - 10k  microSec   | Network latency
    HDD           - 20k  microSec   | storage latency
```
**For improved latency:**
  - better storage device | use caching (HDD --> RAM)
  - better network protocol (https --> http --> TCP/UDP)
  - ...

### Latency metrics
**Evaluate** https://www.youtube.com/watch?v=lJ4NEMNBeS4
- average: 
- Max:
- min:
- **Median latency**:  sort the data set and determining the middle position. (mid+midNext)/2 for even.
- **PXX latency** (percentile): sort the data and determine the percentile position.
  - P50 : 50 % of total reqs, executed with in `80ms`
  - P90 : 90 % of total reqs, executed with in `150ms`
  - P95 : 95 % of total reqs, executed with in `200ms`
  - P99 : 99 % of total reqs, executed with in `200ms`

```
Example
Request 1  → 150 ms
Request 2  → 60 ms
Request 3  → 500 ms
Request 4  → 80 ms
Request 5  → 120 ms
Request 6  → 50 ms
Request 7  → 200 ms
Request 8  → 90 ms
Request 9  → 70 ms
Request 10 → 100 ms

total item = 10
p50 == 50 of 10 == 5 == check postion 5

Fastest                                  Slowest
   ↓                                        ↓
50  60  70  80  90 | 100  120  150  200  500
                    ↑
                  P50 area
```

---
## 2. Throughput
- measure capacity: the amount of work a computer can perform within a given time.
- example
  - data transferred per second
  - no of http req per sec
  
**Improved  throughput:**
  - batching
  - scaling
---
## ✔️ Bitrate
- use to measure video streaming
