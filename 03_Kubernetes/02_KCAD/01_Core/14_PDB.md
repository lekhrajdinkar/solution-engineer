## PodDisruptionBudget (PDB) 
- Kubernetes resource that helps ensure high availability during voluntary disruptions, such as:
  - Node maintenance/draining (e.g., kubectl drain)
  - Cluster autoscaling (node removal)
  - **Manual pod evictions
  - Rolling updates** 

[10_podDisruptionBudget.yaml](../../00_project/02_spring_app_manifest/10_podDisruptionBudget.yaml)