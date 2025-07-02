## DID NOT WORK ##

module "delegate" {
  source = "harness/harness-delegate/kubernetes"
  version = "0.1.8"

  account_id = "e0wDKKO_S46x3M75TWv0iw"
  delegate_token = "MGY2OGJmMWQwYjMwZGM5NDYzZDM5NGFlMDg5Mzk4NzY="
  delegate_name = "terraform-delegate"
  deploy_mode = "KUBERNETES"
  namespace = "harness-delegate-ng"
  manager_endpoint = "https://app.harness.io"
  delegate_image = "harness/delegate:24.10.84200"
  replicas = 1
  upgrader_enabled = true
}

provider "helm" {
  kubernetes {
    config_path = "~/.kube/config"
  }
}

/*
terraform {
  cloud {
    organization = "lekhrajdinkar-org"
    workspaces {
      name = "terraform-helm-delegate"
    }
  }
}*/

/*
helm install -i helm-delegate --namespace harness-delegate-ng --create-namespace ^
harness-delegate/harness-delegate-ng ^
--set delegateName=helm-delegate ^
--set accountId=e0wDKKO_S46x3M75TWv0iw ^
--set delegateToken=MGY2OGJmMWQwYjMwZGM5NDYzZDM5NGFlMDg5Mzk4NzY= ^
--set managerEndpoint=https://app.harness.io ^
--set delegateDockerImage=harness/delegate:24.10.84200 ^
--set replicas=1 --set upgrader.enabled=true


helm upgrade -i helm-delegate --namespace harness-delegate-ng --create-namespace harness-delegate/harness-delegate-ng --set delegateName=helm-delegate --set accountId=e0wDKKO_S46x3M75TWv0iw --set delegateToken=MGY2OGJmMWQwYjMwZGM5NDYzZDM5NGFlMDg5Mzk4NzY= --set managerEndpoint=https://app.harness.io --set delegateDockerImage=harness/delegate:24.10.84200 --set replicas=1 --set upgrader.enabled=true

*/