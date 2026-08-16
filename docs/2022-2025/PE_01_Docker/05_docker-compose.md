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

## Example
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

