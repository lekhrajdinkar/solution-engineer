# Discord

## scaling message storage
- https://www.youtube.com/watch?v=X4iAQkjtx5k


### 1. The MongoDB Era

Initial Choice: 
- MongoDB was chosen in 2015 for its **flexibility and speed** in prototyping.

The Breakdown: 
- As they reached 100 million messages, random read patterns caused **frequent cache misses**,
- forcing the system to read from disk and causing unpredictable **latency**.

Lesson: The database must match the specific data access pattern. MongoDB wasn't a bad tool, just a mismatch for their scale and usage.

### 2. The Cassandra Era

The Wishlist: They required a system that was :
- scalable, 
- self-healing, 
- low-maintenance, 
- steady and predictable. 👈

Design Decisions: 
- They used Apache Cassandra with a **composite partition key** consisting of 
  - Channel ID 
  - a 10-day time bucket to manage partition size, 
  - combined with Snowflake IDs, to bake time into message IDs.

The Problem: 
- Deletions created **tombstone** markers that slowed down reads as the system had to scan through them.
- **Garbage Collection (GC)** Running on the Cassandra's (JVM) meant, 
  - periodic GC pauses (sometimes up to 10 seconds), 
  - freezing the database.
- **Hot Partitions**: Highly active channels overwhelmed specific machines.
- **Compaction Issues**: 
  - The system struggled to keep up with background merging of data, 
  - requiring a manual "gossip dance" to keep nodes healthy.

### 3. The Move to ScyllaDB

Why ScyllaDB: 
- written in C++, eliminating the JVM and GC pauses. 👈
- **Shard-Per-Core** Design: 
  - Instead of shared resources, ScyllaDB assigns specific CPU cores to specific slices of memory and data, significantly improving efficiency.
- **Request Coalescing**: 
  - To prevent "hot partition" spikes, they built a **Rust layer** in front of the database. 
  - This acts as a bouncer, coalescing thousands of identical requests into one query to the database.
  - ![img.png](../../../99_img/2026/07/01/img_4.png)

Migration:
- They migrated trillions of messages with zero downtime by
  - writing new messages to both systems 
  - and building a custom Rust migration tool that handled the transfer in 9 days instead of an estimated 3 months.

Results and Takeaways
- Efficiency: The cluster shrunk from 177 machines to 72, while handling more traffic.
- Stability: Tail latency stabilized to a predictable 15ms, and the "weekend firefights" and manual maintenance ended.

> System Design Philosophy: 👈👈
> - Match your database to your access pattern, 
> - define your requirements before picking a tool, 
> - and use smart middle layers to absorb traffic spikes.