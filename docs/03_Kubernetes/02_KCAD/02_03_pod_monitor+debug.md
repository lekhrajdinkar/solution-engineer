![img.png](../99_img/99_2_img/03/01/img.png)

--- 
## minikube addon: metric-Server
- heapster is deprecated, use metric Server
- **in memory** analytic, does not store on disk
- `kubelet` has `C-advisor`, responsible to receiver **performance data** and send to metric-server
  
![img_1.png](../99_img/99_2_img/03/01/img_1.png)

## commands
- k top node
- k top pod
- k logs -f pod-1 # all containers
- k logs -f pod-1 **container-1**
- k logs -f pod-1 **container-2**

## Checking Logs from Multiple Kubernetes Pods
- kubectl logs -l **app=my-app** -n my-namespace  [-c <container-name> ] **--tail=100**  // label
- kubectl logs **pod/pod-1 pod/pod-2** --prefix
- Kubernetes Dashboard provides a GUI. eg: **lens**.
- kubectl logs -l app=my-app **--previous** // for Crashed Pods 👈🏻
- filter log:
    -  | **jq** 'select(.level == "error")'
    -  | **grep** "ERROR"
- Default Location --> Node-level: /var/log/containers/
    - Rotated every 10MB, max 5 files 👈🏻
    - `--container-log-max-size`
    - `--container-log-max-files`

## AWS CW::log (cluster masterNode only)
- By default, EKS doesn't send application logs to CloudWatch - only control plane logs. log group:
    - /aws/eks/cluster-name/cluster
    - /aws/eks/cluster-name/workload/**namespace/pod-name**
```
aws logs filter-log-events \
  --log-group-name "/aws/eks/my-cluster/workload/my-namespace/my-pod" \
  --start-time $(date -d '1 hour ago' +%s000) \
  --filter-pattern "ERROR"
```

## take heap dumps from pod before, it died
- `-XX:+HeapDumpOnOutOfMemoryError`
- `-XX:HeapDumpPath=/path/to/dump.hprof`
- JVisual, jhat for local
- actuator will die, so cant use it.

## Default Log Behavior in Fargate (No CloudWatch)
- Fargate does not store logs on disk
- CloudWatch Logs is disabled
- logs are ephemeral—they disappear when the pod terminates or crashes.
- use **Sidecar Container** for Log Forwarding

```yaml
    spec:
      containers:
        - name: main-app
          image: my-application:latest
          ports:
            - containerPort: 8080

        # 📚 CloudWatch Logs Sidecar
        - name: cloudwatch-sidecar
          image: amazon/aws-for-fluent-bit:latest
          env:
            - name: AWS_REGION
              value: us-west-2
          volumeMounts:
            - name: app-logs
              mountPath: /var/log/app
            - name: config
              mountPath: /fluent-bit/etc/cloudwatch.conf
              subPath: cloudwatch.conf
          command: ["/fluent-bit/bin/fluent-bit", "-c", "/fluent-bit/etc/cloudwatch.conf"]

        # 📚 Datadog Sidecar
        - name: datadog-sidecar
          image: gcr.io/datadoghq/agent:latest
          env:
            - name: DD_API_KEY
              valueFrom:
                secretKeyRef:
                  name: datadog-secret
                  key: api-key
            - name: DD_LOGS_ENABLED
              value: "true"
            - name: DD_LOGS_CONFIG_CONTAINER_COLLECT_ALL
              value: "true"
          volumeMounts:
            - name: app-logs
              mountPath: /var/log/app
              readOnly: true
            - name: docker-socket
              mountPath: /var/run/docker.sock
              readOnly: true

      volumes:
        - name: app-logs
          emptyDir🔸: {}
        - name: config
          configMap🔸:
            name: fluent-bit-config
        - name: docker-socket
          hostPath🔸:
            path: /var/run/docker.sock
```