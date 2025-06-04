## harness delegate
### k8s delegate **
- helm [terraform-helm-delegate](../04_terraform/project/terraform-helm-delegate)
  - **kubeconfig** set to minikube
  - https://app.harness.io/ng/account/e0wDKKO_S46x3M75TWv0iw/all/settings/delegates/list : running (minikube)

![img.png](../04_terraform/99_img/05/01/img.png)

![img_1.png](../04_terraform/99_img/05/01/img_1.png)

---
### docker-delegate
```
        docker run  --cpus=1 --memory=2g \
        -e DELEGATE_NAME=docker-delegate \
        -e NEXT_GEN="true" \
        -e DELEGATE_TYPE="DOCKER" \
        -e ACCOUNT_ID=e0wDKKO_S46x3M75TWv0iw \
        -e DELEGATE_TOKEN=MGY2OGJmMWQwYjMwZGM5NDYzZDM5NGFlMDg5Mzk4NzY= \
        -e DELEGATE_TAGS="" \
        -e LOG_STREAMING_SERVICE_URL=https://app.harness.io/log-service/ \
        -e MANAGER_HOST_AND_PORT=https://app.harness.io harness/delegate:24.10.84200
        
        docker run  --cpus=1 --memory=2g -e DELEGATE_NAME=docker-delegate -e NEXT_GEN="true" -e DELEGATE_TYPE="DOCKER" -e ACCOUNT_ID=e0wDKKO_S46x3M75TWv0iw -e DELEGATE_TOKEN=MGY2OGJmMWQwYjMwZGM5NDYzZDM5NGFlMDg5Mzk4NzY= -e DELEGATE_TAGS="" -e LOG_STREAMING_SERVICE_URL=https://app.harness.io/log-service/  -e MANAGER_HOST_AND_PORT=https://app.harness.io harness/delegate:24.10.84200
```