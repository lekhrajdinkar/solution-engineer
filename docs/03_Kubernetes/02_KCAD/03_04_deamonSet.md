## DeamonSet
### intro
- https://chatgpt.com/c/684e249b-7b64-800d-95df-2aee3d508bf6

### When to use DaemonSet:
- Log collection : Splunk
- Monitoring : Datadog
- Networking (e.g., CNI plugins) : ingress-controller
- Node-local storage management

### ccgg example
- **obs**
  - opentelemetry-*	    Distributed tracing and observability (instrumented apps/services)
  - datadog	            Observability platform (metrics, logs, traces)
  - splunk-kubernetes	Logging via Splunk integration
- **cost**
  - flexera	            Likely for software asset or cloud cost management
  - cloudability	    Cost and usage monitoring tool
- **networkin/services**
  - cert-manager	    Manages SSL/TLS certificates (likely with Let's Encrypt or ACM)
  - chaos-mesh	        Chaos engineering (resilience testing of services)
  - ingress-nginx	    Ingress controller using NGINX
- **more**
  - `karpenter`	        Dynamic cluster autoscaler (more efficient than Cluster Autoscaler)
  - `external-secrets-operator`	Syncs secrets from AWS Secrets Manager/SSM into Kubernetes secrets
  - namespace-controller	Custom or enhanced namespace lifecycle management
  - `reloader`	        Auto-restarts pods when ConfigMap/Secret changes
  - rafay-*         	Rafay is a Kubernetes management platform (deployment pipelines, governance)
  - velero	            Backup and restore of cluster resources and **persistent volumes**
  - wiz	                Container/cloud **security platform** (runtime & config scan)
