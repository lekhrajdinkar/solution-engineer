terraform {
  cloud {
    organization = "lekhrajdinkar-org"

    workspaces {
      name = "docker"
    }
  }
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.1"
    }
  }
}

# https://registry.terraform.io/providers/kreuzwerker/docker/latest/docs
# provider : docker
provider "docker" {
  host     = "ssh://user@remote-host:22"
  ssh_opts = ["-o", "StrictHostKeyChecking=no", "-o", "UserKnownHostsFile=/dev/null"]
}

# string 1 - resource_type  ( provider as prefix )
# string 1 - resource_name
# resource_type.resource_name --> forms unique id for resource
resource "docker_image" "nginx" {
  name         = "nginx:latest"
  keep_locally = false
}

resource "docker_container" "nginx" {
  image = docker_image.nginx.image_id
  name  = "tutorial"
  ports {
    internal = 80
    external = 8000
  }
}