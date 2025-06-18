## config Map
- kubectl get/describe configmaps
- create (imperative)
  - kubectl create configmap config-1 --from-literal=k1=v1 --from-literal=k2=v2 ...
  - kubectl create configmap config-1 --from-file=abc.properties

- create (declarative)
```
apiVersion: v1
kind: ConfigMap
metadata
  name:config-map-1
data:
  - name: K1
    value: V1
  ...
  
```
---
### inject configmap as env var into `pods`
- whole config
```
envFrom:
    - configMapRef:
        name: config-map-1
```

- specific single value
```
env:
    - name: ENV_1
      valueFrom:
        configMapKeyRef:
            name:
            key:  
```