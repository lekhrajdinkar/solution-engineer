- summary:
  - LimitRange - for NS-1 (100 GB) - total for ns
  - resourceQuota  for **all** pod inside NS-1  (1 GB max to each pod)
    - pod>resource - individual pod
--- 
# Resource Requirement

- by default, no limit --> any pod can use as much resource.
- pod > spec > containers > `resources`:
  - `request` (minimum)
    - cpu: 1  --> 1 count of CPU is equivalent to `1 vCPU`. That’s 1 vCPU in AWS.
    - memory: 256M
  - `limit` (maximum)
    - cpu: 3 --> will never go beyond. this.
    - memory: 500M --> but container can use more memory, beyon limit, if available on node, and eventually terminated with OOM.

- pod will be started at desirable-Node.
- if none of the node has enough resource, then it will error  out : 
  - eg: **FailedScheduling** No nodes are available that match all of the following predicates:: Insufficient cpu
- 4 behaviour
  - no request , no limit
  - no request , limit
  - request , limit
  - request , no limit  *** --> make sure has pod has some resource request set.

## LimitRange
- manage resource usage within a namespace
- set limit at Namespace level, hence applied to all pod and need to define to each pod.
- this is effect only the newer pod.
- Types of Limits:
  - `min`: The minimum amount of a resource (e.g., CPU, memory) that must be requested by a container or Pod.
  - `max`: The maximum amount of a resource a container or Pod can request or consume.
  - `DefaultRequest`: If a container or Pod does not specify a request, Kubernetes assigns this default request.
  - `DefaultLimit`: If a container or Pod does not specify a limit, Kubernetes assigns this default limit.
  - `Resource Ratios`: Some LimitRange configurations also allow setting ratios for resources to ensure balanced use, especially for storage resources. ?
```
apiversion: v1
kind" LimitRange
metadata:
  name: 
spec:
  limits:
    - type: Container       # Container, pod, PVC
      max:
        cpu: "2"            # Maximum of 2 CPUs per container
        memory: "1Gi"       # Maximum of 1Gi memory per container
      min:
        cpu: "200m"         # Minimum of 200m CPU per container
        memory: "100Mi"     # Minimum of 100Mi memory per container
      default:
        cpu: "500m"         # Default CPU request if not specified
        memory: "200Mi"     # Default memory request if not specified
      defaultRequest:
        cpu: "300m"         # Default CPU request
        memory: "150Mi"     # Default memory request
        
    - type : PersistentVolumeClaim 
      max:
        storage: 
      min:
        storage:  
```
## ResourceQuota
- set limit on resource used by all pod in namespace, together.
```
apiversion: v1
kind" ResourceQuota
metadata:
  name:
  namespace:
spec:
  hard:
    requests.cpu
    requests.memory
    limit.cpu
    limit.memory
    
```

