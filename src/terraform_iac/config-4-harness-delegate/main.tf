## DID NOT WORK ##

module "delegate" {
  source = "harness/harness-delegate/kubernetes"
  version = "0.1.8"

  account_id = ""
  delegate_token = ""
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