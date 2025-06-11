- docker `build` -t repoName/image-1:version . or -f Dockefile-1
- docker pull | push | login

## image
- docker images  
- docker rmi image-1
- docker tag image-id-1 name:version/latest

## Container
### common
- docker `run` 
  - your-command  (optional, eg sleep 5000) 
  - --name = container-name - c1
  - -it 
  - -d 
  - -v 
  - --mount type=volume, source=vol-1, target=location-on-container
  - -e k=v -e k=v ... 
  - -p host:container 
  - --network = n1 
  - **--user 100** 
  - --cap-add/drop CAPABILITY-1  --cap-add/drop CAPABILITY-2 ...
  - --entrypoint python app-1.py --> override ENTRYPOINT ["python", "app-1.py"]
  - registry-1/repoName-1/image-1:latest   :point_left: image at last
  
- docker `ps` -a
- docker start | stop | restart c1
- docker rm c1
- **docker exec c1 <command>**
- docker logs -f c1 : live log trail
- **docker inspect** nspect a container's network configuration

### volume and network
- docker volume create **vol-name-1**:location-on-host
- docker network create  --driver=bridge --subnet ... n1

---
## dockerfile (text file)
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
- ADD
- WORKDIR
- EXPOSE 8080
- COPY
- **ENV** NODE_ENV=production
- **ARG** APP_VERSION=1.0 , Defines build-time variables
- **VOLUME** /data
- USER userId  
  - by default all process run the `root user` (with limited set of **capability**)

```
### apk ###

apk update
apk add --no-cache package-name (eg: curl)
apk del package-name
apk search package-name
apk info

# Combine apk commands into one block to reduce image layers and improve build performance.
# Use --no-cache to avoid storing index files and temporary data in the final image
# rm -rf /var/cache/apk
```
- Reduce size of image:
  - Multi-stage builds
  - Alpine-based images 
  - remove unnecessary dependencies.
  - docker scan
