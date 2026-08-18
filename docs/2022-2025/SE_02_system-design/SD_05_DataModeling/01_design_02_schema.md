# Schema design
## 3 key factors
```mermaid
graph TD
    Root["<b>Schema Design</b><br>Three key factors"]
    
    F1["<b>1. Data Volume</b><br>• Where can data live?<br>• Single DB vs. distributed"]
    F2["<b>2. Access Patterns</b><br>• How is data queried?<br>• Drives indexes & structure"]
    F3["<b>3. Consistency Requirements</b><br>• How strict?<br>• ACID vs. eventual consistency"]

    Root --> F1
    Root --> F2
    Root --> F3

    %% Styling
    style Root fill:#f8f9fa,stroke:#333,stroke-width:2px
    style F1 fill:#ffffff,stroke:#007bff,stroke-width:1.5px
    style F2 fill:#ffffff,stroke:#28a745,stroke-width:1.5px
    style F3 fill:#ffffff,stroke:#dc3545,stroke-width:1.5px
```

**Data volume** 
> determines where your data **can physically live.** 
- A social media app with millions of users might need data spread across multiple data stores, which drives schema design choices.
- If user data and post data need to live on separate systems for performance or organizational reasons,
- they necessarily need distinct schemas with careful consideration of how they reference each other.

**Access patterns**
> How will **your data be queried?**
- A news feed that loads "recent posts by followed users" suggests you'll want denormalized data or carefully designed indexes.
- An analytics dashboard that aggregates data across time periods might need different table structures entirely. 
- This comes naturally from your APIs. Just ask what queries will I need to support each endpoint?

**Consistency requirements** 
> determine **how tightly coupled your data can be.** 
- Financial transactions need strong consistency (no partial charges), which often means keeping related data in the same database with ACID guarantees.
- But a user's activity feed can handle eventual consistency (it's okay if a like shows up a few seconds later),
- which allows you to distribute that data across separate systems with different schemas optimized for different access patterns.