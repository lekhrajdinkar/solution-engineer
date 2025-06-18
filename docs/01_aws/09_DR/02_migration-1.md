# More AWS service (overview) - DR related
## 1. Application discovery
- scan server and gather info from on-prem VM/server. gather data can be track : `Migration Hub`
- and then create `migration plan` out of it.
- Type of discovery(scan)
  - `agent-less` : gathers,
    - configuration
    - memory
    - disk usage
    - network
  - `agent-based` : gathers addintional info like:
    - **live** network details, connections between systems
    - **live** system performance
    - **live** running processes
    - ...

## 2. MGN : Application migration service
- perform migration : 
  - `Lift and shift`.
  - convert `physical` server to `virtual` cloud server.
- supports wide range platform,os,db,volumes, etc
- hire dedicated engines to do this. `complex process`.
- minimal downtime.
- ![img.png](../99_img/dr/img.png)

## 3. SMS : server migration service
- `incremental migration of live server data`

## 4. Migration Hub
- this help to `track` migration execution

## 5. DMS and SCT
- [DMS and SCT](02_migration-2.md)

---
- fact/s
  - can also run `VMware software` on AWS