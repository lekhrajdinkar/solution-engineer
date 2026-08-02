# Hashing concept
- https://www.youtube.com/watch?v=pU1uifHXhE4
- [consistent-hashing](../SD_05_DataLayer+storage/03_concept_02_consistent-hashing.md) : hashing with number line

---
## Overview
Hashing is about turning data into a **fixed-size fingerprint**
- Deterministic – same input, same hash
- One-way – not decryptable, hard to reverse
- Avalanche effect (uniform distribution) – tiny input change = totally different hash
- Fast to compute
- Collision resistant

---
## popular hash Algo
```
SHA-256
SHA-512
bcrypt
scrypt
Argon2 (best choice today)
```
---
## Salt
- **salt** is random data added before hashing, 
- to protect from **rainbow attack**

---
## Hash table
- data structure (array, etc) that stores **key–value pairs** 
- and gives you fast access to values using the key. O(1)
- java HashMap, HashSet
```
hash(key) = key % 10

| Key (userId) | Value (name) |
| ------------ | ------------ |
| 101          | "Alice"      |  101 % 10 = 1
| 205          | "Bob"        |  205 % 10 = 5
| 330          | "Charlie"    |  330 % 10 = 0

Index:  0        1        2   3   4        5        6   7   8   9
Value: (330,C) (101,A)   -   -   -     (205,B)     -   -   -   -
```