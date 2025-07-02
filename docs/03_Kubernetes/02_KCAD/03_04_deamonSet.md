## intro
-  ensures that a copy of a specific pod runs on all (or some) nodes in a cluster.
- Automatically schedules a pod on every new node added to the cluster
- One per node

## Use case: 
- Run background or **system-level daemons** , eg:
- log collectors
- monitoring agents like Fluentd 
- Datadog agent
- ingress-controller
- ...

| **Category**            | **Tool**                     | **Purpose / Description**                                            |
| ----------------------- |------------------------------|----------------------------------------------------------------------|
| **Observability**       | `opentelemetry-*`            | Distributed tracing and observability (instrumented apps/services)   |
|                         | `datadog`                    | Observability platform (metrics, logs, traces)                       |
|                         | `splunk-kubernetes`          | Logging via Splunk integration                                       |
| **Cost Management**     | `flexera`🔸                    | Software asset or cloud cost management                              |
|                         | `cloudability`               | Cloud cost and usage monitoring                                      |
| **Networking/Services** | `cert-manager`🔸             | Manages SSL/TLS certificates (e.g., Let's Encrypt, ACM)              |
|                         | `chaos-mesh`                 | Chaos engineering for resilience testing                             |
|                         | `ingress-nginx`              | NGINX-based Ingress controller                                       |
| **Other Tools**         | `karpenter`🔸                | Dynamic autoscaler (more efficient than Cluster Autoscaler)          |
|                         | `external-secrets-operator`🔸  | Syncs secrets from AWS Secrets Manager/SSM to Kubernetes secrets     |
|                         | `namespace-controller`       | Enhanced namespace lifecycle management                              |
|                         | `reloader`🔸                   | Auto-restarts pods when ConfigMap/Secret changes                     |
|                         | `rafay-*`                    | Rafay platform for K8s management (deployments, governance)          |
|                         | `velero`🔸                     | Backup and restore of cluster resources and PVC                      |
|                         | `wiz` 🔸                     | Container/cloud security platform (runtime & configuration scanning) |

## yaml 
- same like deploymnet

```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: log-agent
spec:
  selector:
    matchLabels:
      name: log-agent
  template:
    metadata:
      labels:
        name: log-agent
    spec:
      containers:
      - name: log-agent
        image: fluentd

```