## Cluster :: minikube
- k8s/minikube : [readme.md](../../deployment/readme.md)
- K8s/EKS:  [eks](../04_terraform/project/config-3-aws/modules/eks)  
- Side Note: k8s is open source solution, AWS native solution : [ecs](../04_terraform/project/config-3-aws/modules/ecs)




## Cluster :: AWS EKS
- [00_EKS_reference.md](04_EKS/00_EKS_start)
- workshop : https://catalog.us-east-1.prod.workshops.aws/workshops/afee4679-89af-408b-8108-44f5b1065cc7/en-US/010-introduction/basics/concepts-objects
- eksctl: https://github.com/eksctl-io/eksctl/tree/main/examples
- Terraform Module : [eks](../04_terraform/project/config-3-aws/modules/eks)

## Cluster :: ECS
- [03_ECS](03_ECS)


---
## More
### CKAD (Certification)
- [02_KCAD](02_KCAD) - Notes
- [00_topic.md](02_KCAD/00_certification-topic)
- [02_commands.txt](02_KCAD/02_commands.yaml)
- [03_lab.md](02_KCAD/03_lab.md)
- [00_sample_yaml](../../deployment/00_sample_yaml)

### references
- https://www.youtube.com/watch?v=qof9A8k64rA&list=PLVz2XdJiJQxybsyOxK7WFtteH42ayn5i9&ab_channel=JavaTechie
- https://www.youtube.com/watch?v=XuSQU5Grv1g&t=527s&ab_channel=KodeKloud
- https://learning.edx.org/course/course-v1:LinuxFoundationX+LFS158x+1T2024/home
- https://learning.edx.org/course/course-v1:edX+DemoX.1+2T2019/home
- k8s - 01 cases tudies https://chatgpt.com/c/6726267e-2a8c-487d-8ffe-937c2c4d0f0f
- k8s - 02 Containers https://chatgpt.com/c/9836b4c7-2b23-497e-b06b-16885e3e18aa
- k8s - 03 Components https://chatgpt.com/c/da40b952-dbd9-46a9-ad58-92c828a89118
- k8s - 04 helm https://chatgpt.com/c/da40b952-dbd9-46a9-ad58-92c828a89118
- k8s - Questions https://chatgpt.com/c/babb0cb6-b6f1-4427-8384-da10f068ed29

### interview Q

<details>

<summary>starbucks</summary>
- <a href="https://chat.deepseek.com/a/chat/s/7ad6e329-5ae5-4ae7-9d7c-e7fa955f4966"> chat 🗨️ </a>
- Checking **Logs** from Multiple Kubernetes Pods
    - kubectl logs -l **app=my-app** -n my-namespace  [-c <container-name> ] **--tail=100**  // label
    - kubectl logs **pod/pod-1 pod/pod-2** --prefix
    - Kubernetes Dashboard provides a GUI. eg: **lens**.
    - kubectl logs -l app=my-app **--previous** // for Crashed Pods
    - filter log:
        -  | **jq** 'select(.level == "error")'
        -  | **grep** "ERROR"
    - Default Location --> Node-level: /var/log/containers/
        - Rotated every 10MB, max 5 files
        - --container-log-max-size, --container-log-max-files
---    
- **AWS cw log**
    - By default, EKS doesn't send application logs to CloudWatch - only control plane logs
    - /aws/eks/<cluster-name>/cluster
    - /aws/eks/<cluster-name>/workload/**<namespace>/<pod-name>**
    - **aws logs filter-log-events** \
      --log-group-name "/aws/eks/my-cluster/workload/my-namespace/my-pod" \
      --start-time $(date -d '1 hour ago' +%s000) \
      --filter-pattern "ERROR"
---
- take heap dumps from pod before, it died
    - `-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/to/dump.hprof`
    - JVisual, jhat for local
    - actuator will die, so cant use it.
---
- Access a service running in Kubernetes without exposing it publicly in dev env.
    - forward traffic from your local machine to a Kubernetes service
        - kubectl **port-forward** svc/my-app-service 5000:80 -n <namespace>
        - 8080 is the port on your local machine.
        - 80 is the port exposed by the my-app-servic
    - forward directly to a pod (if the service has no pods
        - kubectl port-forward pod/my-pod-name 5000:80
---
- Shell into a running pod
    - kubectl **exec** -it <pod-name> -- /bin/sh
---
- Mount ConfigMap/Secret as volume
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: config-volume
      mountPath: /etc/config  <<<
  volumes:
  - name: config-volume
    configMap:
      name: my-config-1
```
- Rolling Updates & Rollbacks
    - kubectl **set image** deployment/my-app app=nginx:1.25 (old)
    - kubectl rollout status deployment/my-app
    - kubectl **rollout undo** deployment/my-app  // prvious version
    - kubectl rollout **history** deployment/my-app

---
- what is log file location in aws eks fargate , clould watch logs not enabled :point_left:
    - Default Log Behavior in Fargate (No CloudWatch)
        - Fargate does not store logs on disk
        - CloudWatch Logs is disabled
        - logs are ephemeral—they disappear when the pod terminates or crashes.
            - container’s stdout/stderr buffer
        - use **Sidecar Container** for Log Forwarding to S3
            - image: amazon/aws-for-fluent-bit:latest
            - env : AWS_REGION, S3_BUCKET
        - **awslogs** driver :
      ```yaml
      containers:
      - name: app
        image: nginx
        # Add logging driver
        logging:
          driver: awslogs
          options:
            awslogs-group: "/eks/fargate-logs"
            awslogs-region: "us-east-1"
            awslogs-stream-prefix: "my-app"
        ```
</details>