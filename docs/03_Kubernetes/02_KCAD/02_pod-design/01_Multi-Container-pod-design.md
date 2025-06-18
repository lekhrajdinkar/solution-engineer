- if any container fails, pod failed.
- All containers  in pod are expected to stay alive at all times.
- ![img.png](../99_img/04/img.png)
--- 
## initContainers
  - purpose:  task that will be run only one time when the pod is first created.
  - When a POD is first created the initContainer is run, 
  - and the process in the initContainer must run to a completion before the **real container** hosting the application starts.
  - configure **multiple** such initContainer.
    - run one at a time in sequential order
  - If any of the initContainers fail to complete, Kubernetes restarts the Pod repeatedly until the Init Container succeeds.
  - https://kubernetes.io/docs/concepts/workloads/pods/init-containers/
  - 
---
## Design pattern for multiple-container pod
### A. Sidecar
- One main container performs the primary task, 
- and additional containers (sidecars) provide **supporting functionality**, like `logging, proxying, or data syncing`.

### B. Adaptor
- An adapter container **transforms** data or interfaces between different systems or services, making them compatible with the main application.

### C. Ambassador
- A container (ambassador) acts as a **proxy** for external services,
- managing `network requests and responses` for the main container.