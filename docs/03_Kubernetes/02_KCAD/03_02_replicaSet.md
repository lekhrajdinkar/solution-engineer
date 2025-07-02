## A. ReplicaSet (new) ✅
- concept and yml are almost same.
- but it also manages pods which are **not defined in template section.**
- Maintain pod availability
- Self-healing mechanism
- usually don’t create ReplicaSets directly — Deployments manage ReplicaSets internally.
- Use ReplicaSet only if  **don't need rolling updates**

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: frontend-rs
spec:
  replicas: 3 🔷
  selector:
    matchLabels:
      app: frontend
```



## commands
- kubectl get replicaset
- kubectl create -f <yaml>
- kubectl scale --replicas=6 -f replicaSet-definition.yaml ⬅️
- kubectl scale --replicas=6 replicaset replicaset-1 ⬅️
- kubectl delete replicaset rs-1
  - all linked pods will be deleted.
-  kubectl get replicaset -o yaml > sample.yaml

![img_4.png](../99_img/99_2_img/rs/img_4.png)

---

## B. replication Controller (old) ❌
- makes sure specified number of pods running all the time.
- span across node/s
- manages pods, defined in template section only ⬅️
  ![img.png](../99_img/99_2_img/rs/img.png)

![img_1.png](../99_img/99_2_img/rs/img_1.png)

![img_2.png](../99_img/99_2_img/rs/img_2.png)

