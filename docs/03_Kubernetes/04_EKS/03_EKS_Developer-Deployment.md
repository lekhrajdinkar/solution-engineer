## Deployment
### 1. strategy
- helm install --> will create deploymnet object with whatever stragey mentioned in deploymnet object.
```yaml
strategy:
  type: RollingUpdate
  rollingUpdate:
    maxSurge: 25%
    maxUnavailable: 25%
```
- **maxSurge**: 25% → up to 25 extra pods (new version) can be created → total pods may reach 125 during update.
- **maxUnavailable**: 25% → up to 25 old pods can be taken down at a time → at least 75 pods remain available during update.
- customize:
    ```yaml
    helm install myapp ./chart \
      --set strategy.rollingUpdate.maxSurge=30 \
      --set strategy.rollingUpdate.maxUnavailable=10
    ```
-