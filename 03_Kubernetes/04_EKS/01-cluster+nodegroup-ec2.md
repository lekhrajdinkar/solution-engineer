# setup - `machine/host` + `k8s software`
- [03_k8s-architcture+features.md](..%2F00_kickOff%2F03_k8s-architcture%2Bfeatures.md)
- create EKS cluster
- set up master node - control panel 
  - install kube-apiserver
  - coreDNS
  - controllers
  - add ons:
    - metric server
    - logging server
- set up worker node
  - kubelet
  - kube proxy
  - docker / container-d
- setup network
- set up cloud service
  - ALB (alb-controller, install with helm)
  - EBS / EFS

---
# Option - 1 :: AWS - EC2 managed service
- spin up EC2 machine
- set up VPC
- software helps with this installation
  - `kops`
  - `kubermatics`
---
# Option - 2.1 :: AWS - managed service (EKS - fargate)
# Option - 2.2 :: AWS - managed service (EKS)
- EKS is cluster architecture.
- diff:
  - **ECS** is AWS specific concept and configuration
  - EKS follows kubernetes standards.
  - both solves same problem in similar way.

## 2.1 created with **terraform /HCL**
  - HCP workspace: https://app.terraform.io/app/lekhrajdinkar-org/workspaces/aws-config-maps-outbound-dev2-eks/runs
  - HCL config: [eks](..%2F..%2F04_terraform%2Fproject%2Faws-config-maps%2F03_outbound%2Fmodules%2Feks)
  - **kubectl cluster-info**
  ```
  - `master`: https://C7467B80CEF6669327EE0493423B84A5.gr7.us-west-2.eks.amazonaws.com
  - `CoreDNS` : https://C7467B80CEF6669327EE0493423B84A5.gr7.us-west-2.eks.amazonaws.com/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
  ```  
## 2.2 created **manually**
- reference video:
  - https://www.udemy.com/course/docker-kubernetes-the-practical-guide/learn/lecture/22628019#overview
  - https://www.udemy.com/course/docker-kubernetes-the-practical-guide/learn/lecture/22628021#overview

### pre work:
- **permission** - create below roles:
  - `cluster-role-1` : 
    - standard policy
  - `node-group-role-1`
    - container registry policy
    - worker node policy 
    - CNI policy
- create **VPC** for EKS 
  - will get standard cloudformation template, use that for now.
- kubeCtl > add new context in **kubeconfig** file.
  - **aws eks update-kubeconfig --cluster cluster-1 --region r1**
  
### steps
- role-1 is used to created cluster.
  - cluster > ns:kube-sysytem > configMap: `aws-auth` : authenticate role-1.
  - later on can add more role those will get authenticated. :point_left:
- create **cluster** with above VPC and role
  - it will add default ns.
- once created > **compute** tab > add `Nodegroup`(ec2 machines) with above role + vpc
  - choose instance-type and scaling.
  - it will also install k8s software needed for **worker node**.
- choose cluster type : **public and private**
- READY :green_circle:

### Deploy K8s Object
- apply  deployment object.
- apply  service(LB type) object.
  - it will add load balancer > check ALB
- volumes (optional) : pv(efs) or sc + pvc
  - CSI - container storage interface) use to intergrate 3rd party storage on k8s.
  - check aws efs csi driver : https://github.com/kubernetes-sigs/aws-efs-csi-driver
  - install this driver :: **kubectl apply -k "github.com/kubernetes-sigs/aws-efs-csi-driver/deploy/kubernetes/overlays/stable/?ref=release-2.0"**
  - add EFS in same vpc (`fs-1`) + add security group to allow traffic with in VPC.
  - create PV and SC object
    ```
    # PV
    ...
    ...
    volumnMode: Filesystem
    storageClassName: efs-sc-1
    csi:
      driver: efs.csi.aws.com
      volumehandle:  fs-1  # id  of fs created above
    
    ---
    # SC
    ...
    ...
      name: efs-sc-1
    spec:
      provisoner: efs.csi.aws.com
    
    ---
    # PVC
    ...
    ...
      name: efs-pvc-1
    spec:
      storageClassName: efs-sc-1
      resources:
        requests:
          storage: 5Gi
    
    ---
    
    # pod
     volumns:
        - name:
          pvc:
            claimname: efs-pvc-1
    
     container:
      - ...
        ...
        volumnMounts:
          - name: 
            mountpath: /app/abc  
    ```
  