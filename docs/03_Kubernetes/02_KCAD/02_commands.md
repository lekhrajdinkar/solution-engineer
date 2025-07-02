- kube-apiserver -h
- k exec kube-apiserver-controlplane -n kube-system -- kube-apiserver -h

## create pod without definition yaml / imperative command
```
- kubectl run pod-1 --image=image-1 -n=ns-1
- kubectl get svc ( or service )
- kubectl get all
- k get all
- k exec pod-1 < my-command >
- k logs -f pod-1 c1
- k top node
- k top pod
```

## create/delete some pods
```
- kubectl create -f .\maps-outbound-pod.yml
- kubectl delete -f .\maps-outbound-pod.yml
- kubectl create -f .\maps-outbound-pod.yml

- kubectl get pods
- kubectl get pod maps-outbound-pod -o yaml
- kubectl delete pod maps-outbound-pod
```

## 2.2 rs:
```
- kubectl get replicaset
- kubectl create -f <yaml>
- kubectl scale --replicas=6 -f replicaSet-definition.yaml
- kubectl scale --replicas=6 replicaset replicaset-1
- kubectl delete replicaset rs-1
    - all linked pods will be deleted.
-  kubectl get replicaset -o yaml > sample.yaml
- Note: use rs
```

## 2.3 staefulSet:
```
- kubectl create -f <yaml>
- kubectl scale --replicas=6 statefulset statefulset-1 # pod-1 > pod-2 > .... pod-6
- kubectl scale --replicas=3 statefulset statefulset-1 # pod-6 down > pod-5 down > pod-4 down. (reverse order)
```

## output option with get command
```
-o json Output a JSON formatted API object.
-o name Print only the resource name and nothing else.
-o wide Output in the plain-text format with any additional information.
-o yaml Output a YAML formatted API object.
```

## Namespace
```
- kubectl get pods --namespace=default
- kubectl create namespace dev
- kubectl config set-context $(kubectl config current-context) --namespace=dev
- kubectl get pods --all-namespaces  ( or just -A )
- use while create/delete/replace
```

## Don’t create it(–dry-run) and -o yaml
```
- kubectl run nginx --image=nginx --dry-run=client -o yaml > abc.yaml
- kubectl create deployment --image=nginx deployment-1  --replicas=4 --dry-run -o yaml
- kubectl expose pod redis --port=6379 --name redis-service --type=NodePort --dry-run=client -o yaml
- kubectl create service clusterip redis --tcp=6379:6379 --dry-run=client -o yaml
```

## imperative + declarative/yaml
```
- kubectl run redis --image=redis:alpine --dry-run=client -o yaml > redis-pod-def.yaml
- kubectl expose pod redis --name=redis-service --port=6379 --type=clusterip
- kubectl create deployment webapp --replicas=2 --image=kodekloud/webapp-color
- kubectl scale deployment webapp --replicas=3
- kubectl create ns dev-ns
- kubectl create deployment redis-deploy --replicas=2 --image=redis --namespace=dev-ns
- kubectl run httpd --image=httpd:alpine --namespace=default
- kubectl expose pod httpd --type=ClusterIP --name=httpd --port=80 --namespace=default
```

## Deployment
```
- k create -f deployment-1.yaml
- k delete deployment deployment-1
- k set image deployment-1 c1 new-image (imperative way) --record=true
- k edit deployment deployment-1 --record=true
- k rollout status deployment/deployment-1 --> status for deployment, status of each replica/pod
- k rollout history deployment/deployment-1 --> show revision history
- k rollout undo deployment/deployment-1 --to-revision=1
```

## Autoscaling / HPA
```
- k autoscale deployment  deployment-1 --max=10 --cpu-percent=70 : creates HPA
- kubectl autoscale deployment my-app   --min=1 --max=5   --metric=custom_metric_name   --target-value=100
- k get/describe/delete hpa hpa-1
```

## edit non-editable feild in pod
```
-  kubectl edit pod ubuntu-sleeper-3
- this open yaml in vi
- save, this saved below tmp copy
- kubectl delete pod ubuntu-sleeper-3
- kubectl create -f /tmp/kubectl-edit-1598052637.yaml
```

## pass arg/cmd
```
- kubectl run pod-1 image=image-1 -- --arg1 value --arg2 value2
- arg1/2 are argument to kubectl
- they arg to container running
```

## configmap and env
```
- kubectl get configmaps
- kubectl describe configmap db-config
- kubectl create configmap webapp-config-map --from-literal=APP_COLOR=darkblue --from-literal=APP_OTHER=diregard
```

##  secrets
```
kubectl create secret generic db-secret \
  --from-literal=DB_Host=sql01 \
  --from-literal=DB_User=root \
  --from-literal=DB_Password=password123
```

## Service Account
```
-  k get pod web-dashboard-6cbbc88b59-96zjk -o yaml
-  found :
    - serviceAccountName: default
    - volumeMounts > mountpath : /var/run/secrets/kubernetes.io/serviceaccount
    - k exec web-dashboard-6cbbc88b59-96zjk -- cat /var/run/secrets/kubernetes.io/serviceaccount/token
    - default sa has not enough permission.
- create new account
    - k create serviceaccount dashboard-sa
    - permission added , using RBAC
    -  k create token dashboard-sa
    - use this token
```

## resource and schedular
```
- k taint  nodes node1 taint1=blue:**taint-effect**
- k label nodes node-1 size=large
```

## KubeConfig
```
- kubectl config use-context research --kubeconfig=my-kube-config
- kubectl config view
- kubectl config use-context context-01
- kubectl config -h ---> this will show more command.
- kubectl get pods --kubeconfig=my-kube-config
```

## Security
```
- kubectl get pods -n kube-system -o wide + then describe pod
- cat /etc/kubernetes/manifests/kube-apiserver.yaml
- ps -aux | grep kube-apiserver
```

## Authentication
```
```

## Authorization
```
- kubectl auth can-i [create|delete]    [deploymnets|node|pods] --as user1 --namespace ns1
- k get roles
- k get role kube-proxy -o json -n kube-system
- k auth can-i list pods --as dev-user --namespace default
- k create role developer --verb=get --verb=list --verb=delete --resource=pod
- k create rolebinding dev-user-binding --user=dev-user --role=developer
- k edit role developer --> it wil open yaml in vi editor.
- k get clusterroles | wc
- k get clusterrolebindings | wc
- k create clusterrole clusterrole1 --verb=* --resource=nodes --dry-run=client -o yaml
- k create clusterrolebinding cluster-role-binding1 --user=michelle --clusterrole=clusterrole1
```

## Admission controller
```
- k create ns webhook-demo
- kubectl -n webhook-demo create secret tls webhook-server-tls \
    --cert "/root/keys/webhook-server-tls.crt" \
    --key "/root/keys/webhook-server-tls.key"
   # k get secret webhook-server-tls -n webhook-demo -o yaml
   # notice  tls.crt  and tls.key
- k create -f /root/webhook-deployment.yaml
- k create -f /root/webhook-service.yaml
```

## API version
```
- kubectl api-versions
- kubectl api-resources
- kubectl api-resources | grep deployments
- kubectl explain resource-1 -->  kubectl explain job
```

## CRD and CR
```
- k get customresourcedefinitions
```

## helm
```
- helm pull --untar bitnami/wordpress
- helm lint chart-1
- helm package chart-1
- helm registry login -u -p (ecr, nexus, docker, etc)
- helm create chart-1
- helm install releaseName bitnami/chartName
- helm update releaseName chart-1
- helm list
```  








