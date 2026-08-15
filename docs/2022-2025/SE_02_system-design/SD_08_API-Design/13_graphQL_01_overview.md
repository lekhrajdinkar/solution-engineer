# GraphQL
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2160312223/posts/2198424021
- https://youtu.be/1VpqNYbdB6k?si=jEw1DkQkwuTh_LvI 

> - For system design interviews specifically, the benefits of GraphQL are murky.
> - bringing up GraphQL in cases where the problem is clearly focused on flexibility

---
## Overview
- Developed by Facebook,
- GraphQL offers **flexible data fetching**,
- allowing clients to request exactly what they need in a single query
- This makes it efficient for mobile apps and scenarios with limited bandwidth
- runs on top of HTTP

---
## use case
- GraphQL finds its sweet spot with complex clients and when multiple teams are making wide queries to overlapping data.

---
## tradeoff
- On the other hand, execution of these GraphQL queries can be a source of **latency** and **complexity** for the backend