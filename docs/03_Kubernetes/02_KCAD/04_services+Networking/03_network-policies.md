## network policy
- cluster has VPC span across nodes.
    - by default all traffic `allow` b/w ALL pods, services.
    - can add restriction with **n/w policy**.
    - pod, services  === VM, has IP:port in VPC.
    - ![img.png](../99_img/07/policy/img.png)
    - ![img_1.png](../99_img/07/policy/img_1.png)
- policy:
    - in rule added to allow incoming traffice, then default respomse out is allowed.
    - And don't add separate egress rule.
    - eg: DB-pod <---allow---- API-pod. good. enough
    - yml definition:
      - ingress --> from : cidr block, other pod of ns-1, etc
```
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: db-policy
spec:  # apply on pods
  podSelector:
    matchLabels:
      role: frontend  
  policyTypes:
  - Ingress
  # - Egress
  ingress: # define `from` and `ports`
  - from:
    # -------rule-1--------
    - podSelector:  
        matchLabels:
          name: api-pod
      # ------ AND -------    
      nameSpaceSelector:
        matchLabel:
          name: prod 
     # ------- OR, rule-2--------    
    - ipBlock
        cidr:             
    ports:
    - protocol: TCP
      port: 3306
  - ...
  - ...
```
- case-1 : AND
```
  - podSelector:
    nameSpaceSelector:
    ipBlock
```

- case-2 : OR
```
  - podSelector:
  - nameSpaceSelector:
  - ipBlock
```

---
