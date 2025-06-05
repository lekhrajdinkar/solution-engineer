# Datadog
- DASHBOARD : https://us5.datadoghq.com/dashboard/lists?p=1
--- 
## 1. Install the Datadog-Agent on Kubernetes
- reference:
  - https://us5.datadoghq.com/signup/agent?platform=kubernetes
  - https://docs.datadoghq.com/containers/kubernetes/distributions/?tab=datadogoperator
  - OpenTelemetry Collector : https://docs.datadoghq.com/opentelemetry/setup/ddot_collector/
  
- **Step-1** Install the **Datadog Operator**
  ```yaml
  kubectl create namespace datadog 
  
  # install the Datadog Operator 
  helm repo add datadog https://helm.datadoghq.com
  helm install datadog-operator datadog/datadog-operator --namespace=datadog  
  kubectl create secret generic datadog-secret --from-literal api-key=2ba01eb9cb57e01bae167c008872c752 -n datadog
  ```
- **Step-2** Deploy the **Datadog Agent**
  - kubectl apply -f datadog-agent.yaml
  - [datadog-agent.yaml](datadog-agent.yaml) :point_left:
---
## 101 Monitors

---
## 102 Dashboard