## A. Scenario
- K8s Cluster has : 
- `app-1` : online shop - **deploymnet-object-1**
  - ![img_4.png](../99_img/07/img_444.png)
  - access app-1:
    - with nodeIp:port
    - with proxy
      - ![img.png](../99_img/07/img.png)
    - with `loadBalancer-service-1` + `gcp-lb-1`

- `app-2` : video-stream app - **deploymnet-object-2**
  - deploy it same cluster, as new deploymnet object
  - access : with `loadBalancer-service-2` + `gcp-lb-2`

- add `gcp-lb-3` --> forward/route traffic to  gcp-lb-1 or gcp-lb-2
  - routing rules:
    - /apparel --> gcp-lb-1
    - /video --> gcp-lb-2
- **end result** : gcp-lb-3 >> gcp-lb-1/2 >> k8s service-1/2

- Next, add security (SSL, firewall). but where ? best place,  having less maintenance :
  - add at app-1/2 level : problem , if application grows
  - add at proxy / gcp-lb-1/2/3 level
---  

## B. ingress
- Another k8s object, think of as **Layer-7 load balance** where :
  - will add routing rule, to forward traffic to multiple k8s service/s
  - will add SSL
  - will add firewall
  - thus, simplfies all such config at central place.
- ingress object components:
  - ![img_4.png](../99_img/07/img_4.png)
  
### ingress controller (pod)
  - proxy server, will be used for routing,forwarding, security
  - need to deploy this server, does not come by default
  - eg: `nginx server` : deploy at as pod 
    - 1 **configmap** for Nginx configuration 
    - 2 **SA** (with access to all these object)
      - ![img.png](../99_img/07/ingres-resource/img.png)
    - 3 expose this pod with **service** ( NodePort / loadBalancer)
      - ![img_3.png](../99_img/07/img_3.png)
      

  ```
  #1 
  
  kind: ConfigMap
  apiVersion: v1
  metadata:
    name: nginx-configuration
  spec:
    
  ```

  ```
  #2
  
  kind: ServiceAccount
  apiVersion: v1
  metadata:
    name: nginx-ingress-sa-1
  spec:
   # Roles ?
   # ClusterRole ?
   # RoleBinding ?
  ```

  ```
    #3
    
    apiVersion: v1
    kind: Service
    metadata:
      name: nginx-ingress
    spec:
      type: NodePort
      ports:
        - port: 80
          targetPort: 80
          protocol: TCP
          name: http
        - port: 443
          targetPort: 443
          protocol: TCP
          name: https
      selector:
        name: nginx-ingress
   ```

  ```
  ## ingress-controller pod ##
  
  apiVersion: extensions/v1beta1
  kind: Deployment
  metadata:
    name: nginx-ingress-controller
  spec:
    replicas: 1
    selector:
      matchLabels:
         name: nginx-ingress
    template:
      metadata:
        labels:
        name: nginx-ingress
      spec:
        containers:
          - name: nginx-ingress-controller
            image: quay.io/kubernetes-ingresscontroller/nginx-ingress-controller:0.21.0
            args:
            - /nginx-ingress-controller
            - --configmap=$(POD_NAMESPACE)/nginx-configuration
            env:
            - name: POD_NAME
              valueFrom:
                fieldRef:
                  fieldPath: metadata.name
            - name: POD_NAMESPACE
              valueFrom:
                fieldRef:
                  fieldPath: metadata.namespace
            ports:
            - name: http
              containerPort: 80
            - name: https
              containerPort: 443
  
   ```
---
### ingress resources 
- Ingress Object having:
  - routing / forwarding rules
  - Security : SSL, firewall, etc.
- eg: rule-1(path-1/2/...),  rule-2(path-1/2/...)
  - ![img_1.png](../99_img/07/ingres-resource/img_1.png)
  - ![img_2.png](../99_img/07/ingres-resource/img_2.png)
  
```
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-1
spec:
  rules:
  - host: my-online-store.com
    http:
      paths:
      - path: /wear
        backend:
          serviceName : wear-service
          servicePort : 80
      - path: /watch
        backend:
          serviceName : watch-service
          servicePort : 80 
             
  - host: watch.my-online-store.com
    http:
      paths:
      - path: *
        backend:
          serviceName : wear-service
          servicePort : 80
          
  - host: watch.my-online-store.com
    http: 
      paths:
      - path: *
        backend:
          serviceName : watch-service
          servicePort : 80
```
# understand yaml
## properties

## annotation
- `alb.ingress.kubernetes.io/backend-protocol`: HTTP: 
  - Specifies the protocol (HTTP in this case) used to communicate between the load balancer and the backend pods.
- `alb.ingress.kubernetes.io/certificate-arn`: "{{ .Values.certificateArn }}":
  - Associates an SSL certificate (from AWS Certificate Manager) with the load balancer, enabling HTTPS traffic.
- `alb.ingress.kubernetes.io/group.name`: "{{ .Values.component }}-ingress-group": 
  - Defines a group name to manage multiple Ingress resources as a single Application Load Balancer (ALB) group.
- `alb.ingress.kubernetes.io/listen-ports`: '[{"HTTP": 80},{"HTTPS": 443}]': 
  - Configures the ALB to listen on both HTTP (port 80) and HTTPS (port 443).
- `alb.ingress.kubernetes.io/load-balancer-attributes`:
  - Sets various load balancer attributes, such as enabling access logs and specifying the S3 bucket and prefix where logs are stored.
- `alb.ingress.kubernetes.io/scheme: internal`: 
  - Makes the load balancer internal, meaning it’s only accessible within the VPC and not publicly exposed.
- `alb.ingress.kubernetes.io/ssl-policy`: ELBSecurityPolicy-FS-1-2-Res-2020-10: 
  - Specifies the TLS policy for secure connections. This policy enforces certain encryption standards.
- `alb.ingress.kubernetes.io/ssl-redirect`: "443": 
  - Redirects HTTP traffic to HTTPS by specifying port 443 as the target.
- `alb.ingress.kubernetes.io/tags`: "k1=v1,..."
  - Adds tags to the ALB with information such as costCenter, envType, expDate, and ppmcId, useful for resource management and tracking.
- `alb.ingress.kubernetes.io/target-type`: ip: 
  - Specifies that the target type for the ALB is ip, meaning it targets pod IPs directly, which is often used with EKS clusters.
- `external-dns.alpha.kubernetes.io/hostname`: "{{ .Values.hostname }}": 
  - Allows the ExternalDNS controller to manage DNS records for the specified hostname.

