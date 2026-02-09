# Bloom Filter
## Overview
- https://www.youtube.com/watch?v=GT0En1dGntY
- **probabilistic data structure** that tells you:
    - ❌ Definitely not present
    - ✅ Probably present  === false positive
- TradeOff `accuracy` for `speed + memory` 👈🏻
  - Extremely memory efficient, since using bit array
  - Great for high-throughput systems

**Working**
- Using a bit array 
- and multiple hash functions
- to mark the presence of elements
- Key operations: Insertion  and lookup  of elements.

![img.png](../../../99_img/2026/04/01/02/img.png)

**optimization**
- Use Optimal number of hash functions
```
k= m/n (ln2)

m = number of bits in the Bloom filter
n = number of inserted elements
ln2 ≈ 0.693
```
- increasing array size
- cross-checking with multiple Bloom filters

## Real-world applications 
- database performance
  - use it, to minimize disk lookups
- web and internet applications 
- cryptocurrency systems 
- network routers  
- spell checkers