### concept
- [🗨️gpt](https://chat.deepseek.com/a/chat/s/00db1638-b5bc-4a70-a585-4a487e210a63) 👈🏻
- Monitor scaling events
- **Cluster Autoscaler** is differenct❌
    - node level scaling
    - mix with node affinity/taints
- has:
    - **deployment** : deployment-1
    - **metric (single / mutli)** :
        - cpu and memory
        - custom metric
        - high traffic
    - **behaviour** : policy - up /down
        - stabilization window
        - no/% of pod up/down, every x seconds

### Example/yaml
```yaml
metrics🔸:
- type: Resource
  resource:
    name: cpu
    target:
      type: Utilization
      averageUtilization: 70

behavior🔸:
  scaleUp:
    policies:
      - type: Pods
        value: 2
        periodSeconds: 15
      - type: Percent
        value: 50
        periodSeconds: 60
    selectPolicy: Max
```

### datadog metric
- Ext/custom metric (datadog)
- intro
    - capture application-specific load patterns
    - eg: no of business req count , no of transaction, etc
    - good for stateful app
- **flow**: app-metric > datadog/prometheous > HPA (monitor it) > perform scaling
- **hands-on**
- Deploy datadog Adapter/agent in cluster
- list all datadog metrics: curl "https://api.datadoghq.com/api/v1/metrics?api_key=${DD_API_KEY}&application_key=${DD_APP_KEY}"
    - eg: datadog.nginx.net.request_per_s
    - format: `datadog.METRIC_NAME_IN_DATADOG{TAG_FILTERS}`
    - ...
- kubectl get --raw "/apis/external.metrics.k8s.io/v1beta1/namespaces/default/**datadog.nginx.net.request_per_s**" | jq

```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: nginx-hpa
spec:
  scaleTargetRef 🔸:
    apiVersion: apps/v1
    kind: Deployment 🔸
    name: nginx
  minReplicas🔸: 2
  maxReplicas🔸: 10
  metrics🔸:
    - type: External
      external:
        metric:
          name: datadog.nginx.requests_per_s
          selector:
            matchLabels: # Automatic tags/label are added
              kube_container_name: nginx
              kube_service: my-webapp
              kube_namespace: ns-1
              pod_name: pod-1
        target:
          type: AverageValue
          averageValue: 100  # Scale when average requests per second per pod exceeds 100
```

### warm pool
- pending...

### troubleshoot :: scaling issue
- kubectl describe hpa
- kubectl get events
- hpa-controller logs
- Verify cluster has available resources
- Verify readiness/liveness probe configuration
- Optimize container images

---
## scenarios🟡
### C.1 configure HPA for a **stateful** application
- **StatefulSets** instead of Deployments
- Implement PV and PVC
  - external storage class 
  - CSI : EFS, S3
- init containers for storage preparation

### C.2 Mission-critical autoscaling strategy
- implement a **warm pool** for sudden traffic spike
- Implement **readiness** probes on pod
- Implement **multi-metric scaling** (CPU, memory, custom metrics like queue length)
- Combine with cluster autoscaler for node-level scaling 
- configure:
  - aggressive scale-up (low stabilization window, high percent/pod-count) 
  - conservative scale-down

### C.3 Time based auto-scaling
- did metric-based scaling so far, above.
#### Option-1 : **CronHPA controller** : modifies HPA specs on schedule

```bash
# === insatll er controller with helm ==
helm repo add stable https://charts.helm.sh/stable
helm install cronhpa stable/cronhpa
```

```yaml
apiVersion: autoscaling.cronhpa.io/v1
kind: CronHorizontalPodAutoscaler
metadata:
  name: myapp-cronhpa
spec:
   scaleTargetRef:
     apiVersion: apps/v1
     kind: Deployment  # scale deploymnet
     name: myapp
   schedules:
   - name: "business-hours"
     description: "Scale up during business hours"
     schedule: "0 9 * * MON-FRI"  # At 09:00 AM Monday-Friday
     targetSize: 10
   - name: "off-hours"
     description: "Scale down after hours"
     schedule: "0 18 * * MON-FRI" # At 06:00 PM Monday-Friday
     targetSize: 2
   - name: "weekend"
     description: "Weekend scaling"
     schedule: "0 0 * * SAT"      # At midnight on Saturday
     targetSize: 1
```

#### Option-2 : **CronJon** to patch HPA
- kubectl patch hpa my-hpa --patch '{"spec": {"minReplicas": 10}}' 
```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: myapp-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: myapp
  minReplicas: 1
  maxReplicas: 15
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
```

```yaml
# Scale-up CronJob (runs at 8:45 AM weekdays)
apiVersion: batch/v1
kind: CronJob
metadata:
  name: scale-up
spec:
  schedule: "45 8 * * MON-FRI"
  jobTemplate:
    spec:
      template:
        spec:
          containers:
            - name: kubectl
              image: bitnami/kubectl
              command:
                - /bin/sh
                - -c
                - |
                  kubectl patch hpa myapp-hpa \
                    --type='json' \
                    -p='[{"op": "replace", "path": "/spec/minReplicas", "value": 5}]'
          restartPolicy: OnFailure

# Scale-down CronJob (runs at 7:00 PM weekdays)
apiVersion: batch/v1
kind: CronJob
metadata:
  name: scale-down
spec:
  schedule: "0 19 * * MON-FRI"
  jobTemplate:
    spec:
      template:
        spec:
          containers:
            - name: kubectl
              image: bitnami/kubectl
              command:
                - /bin/sh
                - -c
                - |
                  kubectl patch hpa myapp-hpa \
                    --type='json' \
                    -p='[{"op": "replace", "path": "/spec/minReplicas", "value": 1}]'
          restartPolicy: OnFailure
```