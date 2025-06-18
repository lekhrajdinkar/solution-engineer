# API Group
- so far, we were using kubectl utility. can also achieve same using REST api.
- First, get `master-node-address` and port .
  - **minikube ip**
  - **kubectl config view --minify -o jsonpath='{.clusters[0].cluster.server}'**
  
- **API groups / categories**  --> all k8s resources are grouped under:
  - /metrics
  - /healthz
  - /version --> cluster version. ?
  - `/api` * --> **core**  functionalities.
  - `/apis` * --> named. more organized. **future** functionalities.
  - /logs --> integrating with 3rd part logging server.

- so, not all user can  call all api. will authorize user, will give granular permission. [check here](./04-Authorization.md) 
  
- try:
  - curl http://localhost:6443 -k
  - curl https://master-node-address:6443/version
  - curl https://master-node-address:6443/api/v1/pods
  - ![img.png](../99_img/security/03/img.png)
  - fixes authentication issue:
    - Fix-1 :: passing--> --key --cert --cacert
    - fix-2 :: use `kubectl proxy` server.
      - It forwards the request to kubeapi server with key,cacert,and cert.
      - run on port `8001`. 
      - curl http://localhost:8001/api
      - Note: `kube proxy` and `kubectl proxy`, are different. check later.
---

## 1. /api
- curl http://localhost:6443/api -k | grep "name"
- ![img_1.png](../99_img/security/03/img_1.png)

## 2. /apis
- curl http://localhost:6443/apis -k | grep "name"
- ![img_2.png](../99_img/security/03/img_2.png)
  