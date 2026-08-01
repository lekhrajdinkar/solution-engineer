# Single point of failure.
## Overview
- check for every system component, and ask what if that component is down, then will bring whole application non-operational ?
- https://academy.bytemonk.io/products/system-design-mastery-beta/categories/2158360643/posts/2192532094
- Solution: **redundancy** (expensive but worthy for financial industries)
- eg: AWS, 
  - use multiple AZ/s, Region/s.
  - DB replication - primary + standBy
