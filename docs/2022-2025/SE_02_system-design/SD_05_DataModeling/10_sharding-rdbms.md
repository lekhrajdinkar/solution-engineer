## intro
- [YT](https://www.youtube.com/watch?v=be6PLMKKSto&ab_channel=Exponent)
- Sharding splits a large database into smaller, independent chunks ("shards") distributed across multiple servers
- **Horizontal Scaling**
- global users table is split by region
- users table
- users_europe (PostgreSQL Server 1)
- users_europe (PostgreSQL Server 2)
- **advantages**
- ✅ Improved Performance (queries run on smaller datasets).
- ✅ Fault Isolation (one shard failing doesn’t crash the whole DB).
- postgres Doesnot provide automatic sharding
- do manually
- **Citus** (PostgreSQL Extension)
- useful for : Multi-tenant SaaS apps (isolate customer data).

## Sharding Strategies
- first create/deploy manually db server/s. shard-1,Shard 2,...
- Application code:
- **Key-Based (Hash) Sharding**
- -- Shard 1: user_id % 4 = 0
- -- Shard 2: user_id % 4 = 1
- **Range-Based Sharding**
- -- Shard 1: order_id 1-1000
- -- Shard 2: order_id 1001-2000
- **Directory-Based Sharding**
- -- Lookup table: :point_left:
- SELECT shard_location FROM shard_map WHERE user_id = 123;

## Challenges of Sharding
- ❌ Complexity (joins across shards are hard).
- ❌ No ACID across shards (distributed transactions are slow).
- ❌ Rebalancing (moving data between shards is tricky).