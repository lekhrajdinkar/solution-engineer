# Deployment

## rollout
- ![img_1.png](../99_img/04/img_1.png)
- when we first create deployment, it performs a rollout with **revision=1**  === replicaSet-1
- next, if deployment object is **upgraded**, new rollout happens with **revision=2** === replicaSet-2
  - eg: updating label, image,etc
  - **k set image deployment-1 c1=image:version  --record=true** 
  -  or, **k edit deployment deployment-1 --record=true**
    - `--record` flag to save the command used to create/update a deployment against the revision number.
  - ![img_5.png](../99_img/04/img_5.png)

- check rollout status
  - **k rollout status deployment deployment-1** --> status for deployment, status of each replica/pod
  - **k rollout history deployment deployment-1 --revision=1** --> show revision history
- Also run : k decribe deploymnet and  check `events`

### rollout strategies :
  - `Recreate`
    - ![img_3.png](../99_img/04/img_3.png)
    - scaling down replicas = 0, then scale up to replicas-count (eg: 5)
  
  - `Rolling update` (default)
    - ![img_4.png](../99_img/04/img_4.png)
    - scale down replicas by 1, and scale up by 1.
    - if there is error with while deployment, it won't scale down further to maintain app availability.
  
  - `Blue green`
    - service (select pod by label `L1`)  --> Deployment-object-1, `blue`>> RS >> POD/s (all has L1)
    - Deployment-object-2, `green` >> RS >> POD/s (all has L2)
    - once all pod in L2 are healthy
    - update service to select pod by `L2`
    - ![img_6.png](../99_img/04/img_6.png)

  - `canary`
    - ![img_7.png](../99_img/04/img_7.png)
    - route small traffic to deployment-canary's pod, usinf common label selector on service
    - once looks ok, the delete canary
    - rollout changes to primary-deploymnet object.
    - ![img_8.png](../99_img/04/img_8.png)
    
## rollback:
- **k rollout undo deployment/deployment-1 --to-revision=1**
  - destroys the pod in replicaSet-2 (current)
  - bring back pod in replicaSet-1 (previous)




