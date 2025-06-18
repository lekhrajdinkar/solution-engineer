## network
- docker `inspect` <c1> : it will network details

### 1. bridge
- `project_dir_default` - one bridge n/w (default)
  - all containers are connected.
  - ![img_2.png](img/crash-course/network/img_2.png)
- can create many bridge n/w.
  - **docker network create  --driver=bridge --subnet ... n1**
  - ![img_1.png](img/crash-course/network/img_1.png)

- **Embedded DNS**
  - `privateIP` == containerName(act as hostname)
  - ![img_3.png](img/crash-course/network/img_3.png)
  
### 2. host
- internet
- intranet - inf, cg, etc

### 3. none
- c1 is not connected to host n/w + default n/w
- hence c1 cannot be exposed.
- c1 cannot connect to other container c2,c3,...

---

## Screenshots
![img.png](img/crash-course/network/img.png)