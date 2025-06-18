# Docker compose
## intro
-  tool used to define and run multi-container Docker applications on a single Docker host.
  - `docker-swam` is native container orchestration tool.
- file name : docker-compose.yml
- command : **docker-compose up**
---
## Features
- `Service Definition`: expose container and access them dn.
- `Multi-container Setup`: Simplifies the management of multiple related containers (like a web app, database, etc.) running on the same Docker host.
- `Environment Variables`: Supports environment variable configuration for different environments (development, production, etc.).
- `Networking`: Automatically creates a network for the services to communicate with each other.
- `Volumes`: Supports persistent storage via volumes and allows mounting host directories to containers.

---
## versions :
### version-3  **
- example
  - will start the nginx **web** server and the **PostgreSQL** database, 
  - linking them through the custom network : **webnet**. 
  - You can access the web service by navigating to http://localhost:8080
```
version: '3.8'

services:
  web:
    image: nginx:latest
    container_name: nginx-web
    ports:
      - "8080:80"  # Maps port 8080 on the host to port 80 on the container
    volumes:
      - ./html:/usr/share/nginx/html  # Mounts the local 'html' folder to the container
    networks:
      - webnet

  db:
    image: postgres:latest
    container_name: postgres-db
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
      POSTGRES_DB: mydatabase
    volumes:
      - db-data:/var/lib/postgresql/data  # Persist database data
    networks:
      - webnet

networks:
  webnet:
    driver: bridge

volumes:
  db-data:
    driver: local

```
---

### version-2
- by default create one network ( name:current_dir_default ) and associate all containers with it.
- `services`:
    - container-name-1 :
        - link not needed, already **link all container/s (within default n/w)** with each other.
        - for external network can add link.
    - container-name-1 :
    - `depends on` :
    - network/s:
        - network-1
        - network-2
        - one container can be associated with multiple networks
- `networks`:
    - can also create
    - network-1
    - network-2


### version-1
- container-name-1 :
    - image or build :
    - ports:
    - environment:
        - ENV_1: xxxxx
        - ENV_2: xxxxxx
    - **link**:
        - db:db or just db, which become hostname.
        - redis:rd
- container-name-2 :
- container-name-3 :
- note: sequence of container matters. v2 has depends-on.