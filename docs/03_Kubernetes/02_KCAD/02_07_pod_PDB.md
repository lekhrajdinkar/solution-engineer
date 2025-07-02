## scenario
- critical API service running on Fargate that must maintain at **least 2 available pods at all times** during deployments or scaling events.

## PodDisruptionBudget (PDB)
- Kubernetes resource that helps ensure high availability during voluntary disruptions, such as:
  - Node maintenance/draining (e.g., kubectl drain)
  - Cluster autoscaling  (down) ✅
  - Manual pod evictions
  - Rolling updates ✅ 

## command
- kubectl get pdb
- kubectl describe pdb api-pdb

## example
```yaml
#
apiVersion: policy/v1
kind: PodDisruptionBudget
metadata:
  name: api-pdb
spec:
  minAvailable: 2  # At least 2 pods must always be available
  selector:
    matchLabels:
      app: critical-api

---
# 
apiVersion: policy/v1
kind: PodDisruptionBudget
metadata:
  name: api-pdb-percentage
spec:
  minAvailable: 50%  # At least half the pods must remain available
  selector:
    matchLabels:
      app: critical-api
```



[10_podDisruptionBudget.yaml](../../../deployment/manifest/spring_app_v2/more/10_podDisruptionBudget.yaml)