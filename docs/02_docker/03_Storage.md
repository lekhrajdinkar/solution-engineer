## A.Storage
- storage drivers : based on OS picks correct one.

```
side note: 
/var/lib/docker/below-folder --> check this location
  - containers
  - images:  Stores all layers
  - volumes : 
    - Docker-managed volumes to store data here
    - Volumes can be shared between containers
    - allow to manage data seperately from host 
    - /var/lib/docker/vol-1 -- better
    - /path/to/host/dir  - host + container, both using them
```
  
- first create volume 
  - **docker volume create vol-1:location-on-host**
  - check `/var/lib/docker/vol-1` then.

- second, it Mount
- -v is old, use `--mount` on docker run ....
  - --mount type=volume, source=vol-1 (/var/lib/docker/vol-1),           target=/container/path
  - --mount type=bind,   source=/path/to/host/dir , target=/container/path
  
---
## Z.Screenshots
![img_6.png](img/crash-course/img_6.png)

![img_7.png](img/crash-course/img_7.png)