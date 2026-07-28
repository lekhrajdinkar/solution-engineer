## 2. Throughput
- measure capacity: the amount of work a computer can perform within a given time.
- Operations completed per unit time
- example:  data transferred per second, no of http req per sec

| Metric     | Meaning                            | Example                |
| ---------- | ---------------------------------- | ---------------------- |
| Latency    | Time taken by one operation        | API responds in 200 ms |
| Throughput | Operations completed per unit time | 5,000 requests/second  |

```
A system can have:
    High throughput and low latency
    High throughput and high latency
    Low throughput and low latency
    Low throughput and high latency  

>>> System processes 10,000 requests/second,but every request takes 5 seconds.
```

**Improved  throughput:**
- batching
- scaling
---
## ✔️ Bitrate
- use to measure video streaming