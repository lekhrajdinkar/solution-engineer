# GraphQL
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2160312223/posts/2198424021
- https://youtu.be/1VpqNYbdB6k?si=jEw1DkQkwuTh_LvI 

> - For system design interviews specifically, the benefits of GraphQL are murky.
> - bringing up GraphQL in cases where the problem is clearly focused on flexibility

---
## Overview
- Developed by Facebook, for mobile device primarily facing issue with low internet speed to overload data
- GraphQL offers **flexible data fetching**,
- Unlike REST's fixed endpoints, GraphQL uses a **single endpoint** with a query language that lets clients specify exactly what data they need.
- runs on top of **HTTP** 👈

## Design
- design a schema that defines your data types and their relationships.
- key difference from REST is that you define relationships directly in the schema
- security at field level 👈
- flexibility creates the **N+1 problem** | batching/dataloader patterns

```
query {
  posts {
    title
    author {
      name
    }
  }
}
---
✔️1 query to get posts 
    SELECT * FROM posts;
  +
✔️100 queries to get authors
    SELECT * FROM users WHERE id = 10;
    SELECT * FROM users WHERE id = 20;
    SELECT * FROM users WHERE id = 30;
    ...
─────────────────────────
101 database queries = N + 1

--- batching ---

SELECT *
FROM users
WHERE id IN (10, 20, 30, 40, ...);
```

---
## use case
- GraphQL finds its sweet spot with complex clients and when multiple teams are making wide queries to overlapping data.
- mobile apps and scenarios with **limited bandwidth**
- diverse client with different data need
---
## tradeoff
- adds **latency** 
- adds **complexity** for the backend.
  - implement query parsing,
  - schema validation, 
  - and often sophisticated caching strategies.

