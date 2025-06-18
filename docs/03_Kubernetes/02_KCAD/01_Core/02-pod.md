## PODS
- containers are `encapsulated` inside pods
- horizontal `scale` up and down :
  - **add more pods** - y
  - add more container inside a pod - n
  - add more worker nodes - n
- can have `multi-container` pod-1 (rare use-case)
  - c1 - api
  - c2 - some helper api
  - both live/die together
  - comm : shares same name network-namespace and storage by-default.
    - no need manually setup-
    - that's one of the benefit with pod.
- HPA `horizontal pod scaling` object
  - k **autoscale** deployment  deployment-1 --max=10 --cpu-percent=70
```
apiVersion
kind
metadata
  name:pod-1
  label:
  securityContext:  # can also add container level, set user only, not capability here.
annotation:
  secret.reloader.stakater.com/auto: "true" 
  configmap.reloader.stakater.com/auto: "true" 
  reloader.stakater.com/auto: "true"  
spec:

  tolerations:
  nodeSelector:
    kubernetes.io/arch: "amd64" # arm64
    karpenter.sh/capacity-type: "spot"         <<<
    
  affinity:
    nodeAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
      preferredDuringSchedulingIgnoredDuringExecution:
      requiredDuringSchedulingRequiredDuringExecution: future
      
  mounts:
  volumes:
  
  restartPolicy: Always / Never --> container exited in pod will come up again.
  serviceAccountName: sa-1 #default  is default sa
  resources:  # better to use LimitRange object
    request:
    limit:
  initContainers:
    -
    -
  containers:
    - name: c1
      image: eg: image has ENTRYPOINT ["sleep"] & CMD ["10"]
      command: ["sleep"] ENTRYPOINT of dockerfile or --entrypoint of dcoker run ...
        - sleep
      args: ["10"] CMD of dockerfile or docker run --entrypoint <> ...
        - 10
      ports:
        - containerPort: 8080
        - containerPort: 8443
      env:
        - name:
          value :
        - name:
          valueFrom: 
            configMapKeyRef :
              - name:
                key: 
        - name:
          valueFrom: 
            secretKeyRef: 
              - name:
                key: 
      securityContext:  # can also add metadata level (pod level)
        runAsUser: <userid>
        capabilities: # only supported here
          add: ["MAC_ADMIN", "SYS_TIME", "NET_ADMIN"]
          drop" ["ALL"]
        allowPrivilegeEscalation: false
        runAsGroup: 101      
        runAsUser: 101
     
           
    - name: c2
      image:
```

- commands: 
```
- kubectl get/describe pod pod-1

- kubectl get pod <pod-name> -o yaml > pod-definition.yaml
  create yml out of existing pod
  
- kubectl edit pod <pod-name>
  - This will open the pod specification in an editor (vi editor
  
  - only the properties listed below are editable:
    spec.containers[*].image
    spec.initContainers[*].image
    spec.activeDeadlineSeconds
    spec.tolerations
    spec.terminationGracePeriodSeconds

  - cannot edit the environment variables, service accounts, and resource limits 
 
 option-1  :: delete and re-create
  - if we attemp to edit, non-editable feild then it will create a temp copy of yaml.
    copy of the file with your changes is saved in a temporary location.
    - kubectl delete pod pod-1    
    - kubectl create -f /tmp/kubectl-edit-ccvrq.yaml
    
 option-2  :: delete and re-create
  - kubectl get pod pod-1 -o yaml > my-new-pod.yaml
  - kubectl delete pod pod-1    
  - kubectl create -f  my-new-pod.yaml
```
---
## Screenshots

![img.png](../99_img/do/img-100.png)

![img.png](../99_img/imgg-1.png)
