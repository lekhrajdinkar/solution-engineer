terraform {
  backend "remote" {
    organization = "lekhrajdinkar-org"
    workspaces {
      name = "banzai-dev_api-driven"
      # hostname =
    }
  }
}