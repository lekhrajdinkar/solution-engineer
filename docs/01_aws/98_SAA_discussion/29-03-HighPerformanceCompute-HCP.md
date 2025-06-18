# SAA discussion

## A. High Performance Computing (HPC)
- not a service  
- combination of multiple service to maximize AWS computation potential.

### 1. Data Management & Transfer
- AWS Direct Connect:
  - Move `GB/s` of data to the cloud, over a private secure network
- Snowball & Snowmobile
  - Move `PB` of data to the cloud
- AWS DataSync
  - Move large amount of data between `on-premises` and  AWS:Storage(`S3, EFS, FSx for Windows`, NOT-EBS)

### 2. Compute
- instance type : CPU/GPU optimzed


### 3. Network
- Placement group - `cluster`, for low latency
- `EC2 Enhanced Networking` : High bandwidth + high PPS + lower latency
  - `intel 82599 VF` (old) 10 GB/s
  - `ENA` (new) elastic n/w adaptor : 100 GB/s
  - `EFA` (elastic fabric adaptor) :dart:
    - improved ENA : uses MPI(message passing interface) which by-pass OS to make network faster.
    - for Linux only
    - for tightly coupled workloads.
- **SR-IOV** :dart:
  - (Single Root I/O Virtualization) is a hardware-based network virtualization feature 
  - enhances network performance for EC2 instances by allowing direct access to the ENI
  - `ethtool -i eth0 ` # Check if SR-IOV is enabled
  - only some instance with supported kernel version 

### 4. Storage 
- Nothing New:
  - EBS
  - EFS
  - instant store
  - s3
  - FXs

### 4. Automation
- `AWS batch`
- `AWS parallel Cluster`
  - Open-source cluster management tool to deploy HPC.
  - Ability to enable EFA
  - Automate creation of VPC,Subnet inside cluster