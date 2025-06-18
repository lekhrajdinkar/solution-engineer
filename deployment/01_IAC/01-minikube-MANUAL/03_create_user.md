## create new user for minikube
- generating new client certificates and adding the user to your kubeconfig (say:u1)
```
openssl genrsa -out new-user.key 2048
openssl req -new -key new-user.key -out new-user.csr -subj "/CN=new-user"
openssl x509 -req -in new-user.csr -CA C:\Users\Manisha\.minikube\ca.crt -CAkey C:\Users\Manisha\.minikube\ca.key -CAcreateserial -out new-user.crt -days 365

```
- update kubeconfig file
  - context
    - user : **new-user**
    - cluster : 
    
- kubectl get ClusterRoleBindings
  - see below role :  **cluster-admin**
```
NAME              ROLE
minikube-rbac     ClusterRole/cluster-admin (existing role)
```
- Create a ClusterRoleBinding for that user with **cluster-admin** rights
```
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: admin-binding-1
subjects:
- kind: User
  name: new-user                              <<<< same name used in kubeConfig
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: cluster-admin                          <<<<
  apiGroup: rbac.authorization.k8s.io

```

  
