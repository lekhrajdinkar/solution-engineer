## ECS vs EKS
- `Clusters`
    - logical grouping of tasks or services.
    - Equivalent to `Cluster in Kubernetes`

- `Tasks` === pod
    - A single running copy of a container defined by a task definition.

- `Task Definitions` == pod
    - Blueprints for your application that specify the container images, CPU, memory, and other settings.

- `Services`  === Replica Set
    - Allows you to run and maintain a specified number of instances of a task definition simultaneously.

- `Container Instances` == work Nodes
  - Amazon EC2 instances registered to your cluster and used to run tasks.
  - Equivalent in Kubernetes: `Nodes`
  
- `Elastic Load Balancing (ELB)`   === Service (specifically, LoadBalancer type)
  - Distributes incoming application traffic across multiple targets.
  
- `Auto Scaling` === Horizontal Pod Autoscaler
  - Adjusts the desired count of tasks in a service automatically based on criteria.
  
- `ECS Agent` === Kubelet
  - Software that runs on each container instance and communicates with ECS to start and stop tasks.
  
- `ECS Fargate`
  - A serverless compute engine for containers that eliminates the need to manage EC2 instances.
---
