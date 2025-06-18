## Docker engine

- ![img.png](img/crash-course/img.png)
- ![img_1.png](img/crash-course/img_1.png)
  - connect to remote dcoker host.
  - export DOCKER_HOST=tcp://<REMOTE_HOST_IP>:2375
  - docker info
  - docker run -H=tcp://<REMOTE_HOST_IP>:2375

## Namespace - pid
- namespace provides isolation
- all process running on host kernal, but namespace provodes isolation.
- same process has different pid in diff namespaces.
- ![img_2.png](img/crash-course/img_2.png)

- restrict resource, which running container:
  - `--cpus`=0.5
  - `--memory`=500m