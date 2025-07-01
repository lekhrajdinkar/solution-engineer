## PODS : intro
- containers are encapsulated inside pods
- HPA (horizontal pod scaling) 
  - **declarative**: kubectl autoscale deployment  deployment-1 --max=10 --cpu-percent=70
  - also define yaml spec.
- VPA (vertical pod scaling)
- can have **multi-container pod** (rare use-case)
  - c1 - api
  - c2 - some helper api
  - both live/die together
  - shares same name network and storage by-default.

## pod : description yaml
```
apiVersion
kind
metadata
  name:pod-1
  label:
  
  ## pod level
  securityContext:   🔷
annotation:
  secret.reloader.stakater.com/auto: "true" 
  configmap.reloader.stakater.com/auto: "true" 
  reloader.stakater.com/auto: "true" 
  
spec:
  🟡tolerations:
  
  🟡nodeSelector:
    kubernetes.io/arch: "amd64" | "arm64"
    karpenter.sh/capacity-type: "spot"    ❓     
    
  🟡affinity:
    nodeAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
      preferredDuringSchedulingIgnoredDuringExecution:
      requiredDuringSchedulingRequiredDuringExecution: future
      
  🔸mounts:
  🔸volumes:
  
  🔸restartPolicy: Always | Never 
  🔸serviceAccountName: sa-1 #default  is default sa
  🔸resources:  # better to use LimitRange object
    request:
    limit:
  initContainers:
    -
    -
  containers:
    - name: c1
      image: eg: image has ENTRYPOINT ["sleep"] & CMD ["10"]
      🔸command: ["sleep"] ENTRYPOINT of dockerfile or --entrypoint of dcoker run ...
        - sleep
      🔸args: ["10"] CMD of dockerfile or docker run --entrypoint <> ...
        - 10
      ports:
        - containerPort: 8080
        - containerPort: 8443
      env:
        - name:
          value :
        - name:
          valueFrom: 
            🔸configMapKeyRef :
              - name:
                key: 
        - name:
          valueFrom: 
            🔸secretKeyRef: 
              - name:
                key: 
                
      ## contaioner level
      securityContext:  🔷
          runAsUser: 1000
          capabilities: 
            add: ["MAC_ADMIN", "SYS_TIME", "NET_ADMIN"]
            drop" ["ALL"]
          allowPrivilegeEscalation: false
          runAsGroup: 101      
           
    - name: c2
      image:
```

## pod : commands
```
- kubectl get/describe pod pod-1
- kubectl get pod <pod-name> -o yaml > pod-definition.yaml
- kubectl edit pod <pod-name>
  
== Edit ==  
  - only the properties listed below are editable: ✅
      spec.containers[*].image
      spec.initContainers[*].image
      spec.activeDeadlineSeconds
      spec.tolerations
      spec.terminationGracePeriodSeconds

  - cannot edit ❌
      env var
      sa
      resource limits 
 
      delete and re-create ✅
        - kubectl get pod pod-1 -o yaml > my-new-pod.yaml > edit it
        - kubectl delete pod pod-1    
        - kubectl create -f  my-new-pod-new.yaml
```
