# Consistent hashing
- https://www.youtube.com/watch?v=NLMZzElM8Z4
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360645/posts/2190592398

## Problem statement
> Distribute **uniformly** among fluctuating node count (add / delete)
> - need to do **expensive** re-distribute.
> - data moves b/w node.

## Distribution Scenario
### Scenario-1: stateful traffic is distributed for serving client
![img.png](../../../99_img/2026/02/01/01/img.png)

### Scenario-2: event/message is distributed for processing
  - event/data --> kafka partition-0, 1, or 3 ?
  - event/data --> AWS Kinesis shard-0, 1, or 3 ?

### Scenario-3: NO-SQL database (sharding) 
- data is distributed across distributed shard [sharding](01_design_04_sharding.md)
- shards can be :
  - within same disk, 
  - diff disk/s of same machine 
  - diff disk/s of diff machine/s

![img_1.png](../../../99_img/2026/02/01/01/img_1.png)

---
## Solutions
### 1. Simple hashing
- [hashing.md](../SD_01_foundation/05_concept_07_hashing.md)
- shard/partition count, `p = 10`
- hash(client_key) = client_id, `id = 12345`
- assigned shard to client  = `id / p` = 5

### 2. Consistent hashing --> hashing with Number line
![img_2.png](../../../99_img/2026/02/01/01/img_2.png)
- Both servers and data keys are hashed onto the same **number line**.
- It **minimizes** data movement when nodes are added or removed in a distributed system (caches, DB shards, message brokers, pods).
- **partition-key goes to the first node/shard clockwise from its hash** 👈🏻

**Understand by example**

```
0 -------------------------------- 360
|                                  |
|                                  |
 ----------------------------------

---
 
Node A → hash 50
Node B → hash 150
Node C → hash 300
** industry grade hash function for `MD5`, `SHA-1`, `Bcrypt`

50(A) -------- 150(B) -------- 300(C) -------- back to 50

---

cleint-1 key-1 --> hash 30 --> next clockwise node = 50(A)
cleint-2 key-2 --> hash 70 --> next clockwise node = 150(B)
cleint-3 key-3 --> hash 160 --> next clockwise node = 300(C)
cleint-4 key-4 --> hash 300 --> next clockwise node = 300(C)

Node A gets: k1
Node B gets: k2
Node C gets: k3, k4

---

➕ Node Added : Node D → hash 100

50(A) ---- ✔️100(D) ---- 150(B) ---- 300(C)

cleint-1 key-1 --> hash 30 --> next clockwise node = 50(A)
cleint-2 key-2 --> hash 70 --> next clockwise node = 100(B)  ❗ (was B)
cleint-3 key-3 --> hash 160 --> next clockwise node = 300(C)
cleint-4 key-4 --> hash 300 --> next clockwise node = 300(C)

A: k1
D: k2
B: -
C: k3, k4

✅ Only keys between A → D moved
❌ Not a full reshuffle

```

