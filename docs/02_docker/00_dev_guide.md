## A. Commands
### 1. image
- docker `build` -t repoName/image-1:version . or -f Dockefile-1
- docker login **private-register-1**, default dockerhub.io, store password locally.
- docker pull | push | login
- docker images  
- docker rmi image-1
- docker tag image-id-1 name:version/latest
- **connect to remote dcoker host.** DOCKER_HOST ⬅️
```
- export DOCKER_HOST=tcp://<REMOTE_HOST_IP>:2375
- docker info
- docker run -H=tcp://<REMOTE_HOST_IP>:2375
```

### 2. Container
- eg: docker run --rm -it alpine **sh -c "id && capsh --print"**
    - running to inspect use and cap
    - uid=0(root) gid=0(root) groups=0(root) 
    - Current: = cap_chown,cap_dac_override,cap_fowner,...+ep
- docker `run` 
    - `--rm` : remove container after exited
    - `--name` = container-name - c1
    - `--cpus`=0.5
    - `--memory`=500m
    - `-i` interactive
    - `-t`  allocate a terminal (useful for shell input/output)
    - `-d`  detached
    - `-u` mount host user on container 🔸 DANGER DONT TRy 🔸
    - `--mount` type=volume, source=vol-1, target=location-on-container # create vol-1 first
    - `--mount` type=bind, source=location-on-source, target=location-on-container
    - `-e` k=v -e k=v ... 
    - `-p` host:container 
    - `--network` = n1 
    - `--user` 100,CAPABILITY-1  eg: CAP_SYS_ADMIN, CAP_NET_ADMIN
    - `--cap-drop` CAPABILITY-2 
    - `--entrypoint` python app-1.py --> override ENTRYPOINT ["python", "app-1.py"]
    - **IMAGE** : registry-1/repoName-1/image-1:latest   👈🏻
    - **COMMAND**  (optional, eg sleep 5000)  ⬅️
  
- docker `ps` -a
- docker `start | stop | restart` c1
- docker `rm` c1
- docker `exec` c1 <command>** ⬅️ run existing container c1. so diff from `run`
- docker `logs` -f c1 : live log trail
- docker `inspect` : inspect a container's network configuration

### 3. volume 
- docker volume create **vol-name-1**:location-on-host

### 4. network
- docker network create  --driver=bridge --subnet ... n1
- docker inspect c1

---
## B. Developer guide
- **.dockerignore** :
  - Specifies files/directories that should be excluded when building a Docker image.
  - Reduces build context size
  - Prevents sensitive files (e.g., .env, credentials.json) from accidentally being copied into the image.
  
### 1. dockerfile (text file)
- **FROM** : Specifies the base image
- **ENDPOINT** : 
  - Specifies the primary command to run inside the container
  - always executed when the container starts
  - any arguments provided to `docker run` will be appended to the command defined in **ENTRYPOINT**
  - ENTRYPOINT ["python", "app-1.py"] : no argument
- **CMD** 
  - Purpose: Provides **default arguments** for the command specified in ENTRYPOINT
  - ENTRYPOINT ["python"] + CMD ["app-2.py"]
  - CMD ["python", "app-1.py"] --> will also work
- **RUN**
  - RUN apt-get update && apt-get install -y curl
  - RUN pip install
- ADD / COPY
- WORKDIR
- EXPOSE 8080
- **ENV** NODE_ENV=production
- **ARG** APP_VERSION=1.0 , Defines build-time variables
- **VOLUME** /data
- USER userId  
  - by default all process run the `root user` (with limited set of **capability**)


###  2. best practices while writing a docker image
- docker scan
- docker stats or cAdvisor.
- avoid latest image
- docker inspect c1
- **BuildKit** ❓

| Practice                                                 | Why                                                |
| -------------------------------------------------------- | -------------------------------------------------- |
| ✅ Use minimal base images (e.g., `alpine`, `distroless`) | Smaller, faster, more secure                       |
| ✅ Pin base image versions (e.g., `python:3.10-slim`)     | Ensures reproducible builds                        |
| ❌ Avoid `latest` tag                                     | It can lead to unexpected behavior during rebuilds |

| Practice                                      | Why                                   |
| --------------------------------------------- | ------------------------------------- |
| ✅ Combine `RUN` instructions to reduce layers | Fewer layers → smaller image          |
| ✅ Clean up temporary files in the same layer  | Avoid bloating the image              |
| ❌ Don’t install unused tools                  | Reduces image size and attack surface |

```dockerfile
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*
```

| Practice                                                    | Why                                       |
| ----------------------------------------------------------- | ----------------------------------------- |
| ✅ Use `--no-cache` (e.g., with `apk`) or clean up apt cache | Avoid leaving behind package manager data |
| ✅ Use multi-stage builds                                    | Keep build tools out of final image       |

```dockerfile
# First stage: build
FROM node:18 as builder
WORKDIR /app
COPY . .
RUN npm install && npm run build

# Final stage: runtime
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html

```

| Practice                                          | Why                                                   |
| ------------------------------------------------- | ----------------------------------------------------- |
| ✅ Use a non-root user (`USER`)                    | Prevents privilege escalation                         |
| ✅ Avoid hardcoding secrets                        | Use secrets management tools or environment variables |
| ✅ Regularly scan your image (e.g., `docker scan`) | Detect vulnerabilities                                |


| Practice                                                 | Why                                     |
| -------------------------------------------------------- | --------------------------------------- |
| ✅ Order instructions from least to most likely to change | Maximize cache reuse                    |
| ✅ Use `.dockerignore` to exclude unnecessary files       | Speeds up builds and reduces image size |


### 3. java

```
chmod +x ./target/spring-app-1.0.0.jar

-Djarmode=layertools

java -Djarmode=layertools -jar ./target/spring-app-1.0.0.jar extract
  - extract a Spring Boot layered JAR file
  
layes:
    - dependencies/
    - spring-boot-loader/
    - snapshot-dependencies/ (if any)
    - application/
```

### 4. py
```dockerfile
# ---- Build Stage ----
FROM python:3.9-slim as builder
WORKDIR /app
COPY requirements.txt .
RUN pip install --user --no-cache-dir -r requirements.txt
COPY . .

# ---- Runtime Stage ----
FROM python:3.9-slim
WORKDIR /app
COPY --from=builder /root/.local /root/.local  # Installed packages
COPY --from=builder /app .                     # Application code
ENV PATH=/root/.local/bin:$PATH

USER 1000
EXPOSE 5000
CMD ["python", "app.py"]
```
