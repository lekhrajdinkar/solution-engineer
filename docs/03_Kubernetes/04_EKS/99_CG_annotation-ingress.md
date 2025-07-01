# Annotation
## 1. on ingress : nginx.ingress.kubernetes.io/xxxxxx
- set up CORS for UI :point_left:
- notice: ssl/tls: certificated added on secret.
- Ensure you have an NGINX Ingress Controller installed
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: backend-ingress
  annotations:
    nginx.ingress.kubernetes.io/enable-cors: "true"
    nginx.ingress.kubernetes.io/cors-allow-origin: "https://ui-dew4.app-1.msi-dev.lekhraj.com"
    nginx.ingress.kubernetes.io/cors-allow-methods: "GET, PUT, POST, DELETE, PATCH, OPTIONS"
    nginx.ingress.kubernetes.io/cors-allow-headers: "DNT, User-Agent, X-Requested-With, If-Modified-Since, Cache-Control, Content-Type, Range, Correlation-id, authorization"
    nginx.ingress.kubernetes.io/cors-expose-headers: "Content-Length, Content-Range"
    nginx.ingress.kubernetes.io/proxy-read-timeout: "300"
    nginx.ingress.kubernetes.io/proxy-send-timeout: "300"
    nginx.ingress.kubernetes.io/proxy-next-upstream: "off"
spec:
  ingressClassName: nginx
  rules:
  - host: backend-dew4.app-1.msi-dev.lekhraj.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: ui-service
            port:
              number: 8080
  tls:
  - hosts:
    - backend-dew4.app-1.msi-dev.lekhraj.com
    secretName: app-backend-release-dev4-tls-cert
```
- **SSL setup**
  - option-1 : ingress controller + **tls**
  - option-2 : ALB-controller + ACM
  
- **ingress scenario-1** :
    - App1.ui.org.com → service.ui (ClusterIP)
    - App1.api.org.com → service.api (ClusterIP)
    - R53: Cname
    ```
    Subdomain	      Type	Target
    app1.ui.org.com	  A	    Ingress Controller's Load Balancer hostname <<
    app1.api.org.com  A	    Ingress Controller's Load Balancer hostname >>
    ```
- **ingress scenario-2** :
  - App1.ui.org.com → service.1 (ClusterIP)
  - App1.ui.org.com → service.2 (ClusterIP)
  - App1.ui.org.com would go service1/2
  - ans: reolve by age. so service1 (since older)

---

## ⚖️ AWS ALB Ingress vs NGINX Ingress

| Feature                    | **ALB Ingress Controller**                             | **NGINX Ingress Controller**                               |
| -------------------------- | ------------------------------------------------------ | ---------------------------------------------------------- |
| **Managed by**             | AWS                                                    | You (runs inside your EKS cluster)                         |
| **Ingress type**           | AWS-native Ingress using **Application Load Balancer** | Pod-based controller using **NGINX reverse proxy**         |
| **TLS via ACM**            | ✅ Yes (ACM certs on ALB)                               | ✅ Yes (via cert-manager or mounted certs)                  |
| **AWS WAF & Shield**       | ✅ Yes (integrates easily)                              | ❌ Not directly (you'd need external tools or custom setup) |
| **Auth (OIDC, Cognito)**   | ✅ Built-in                                             | ✅ With **OAuth2 Proxy** or NGINX config                    |
| **Rate Limiting**          | ⚠️ Limited                                             | ✅ Yes (with annotations or NGINX config)                   |
| **Caching**                | ❌ Not supported                                        | ✅ Yes (proxy caching config)                               |
| **Flexibility**            | ❌ Less (opinionated, limited customization)            | ✅ Highly customizable                                      |
| **CloudWatch Integration** | ✅ Built-in                                             | ❌ Needs manual setup                                       |

- Can integrate tools like, for NGINX Ingress Controller 👈🏻
    - cert-manager for TLS
    - OAuth2 Proxy for auth
    - ModSecurity for WAF
    - NGINX annotations for rate limiting & cac

##  AWS ALB Ingress : more 

| Feature                    | Ingress Controller (Yes/No) | How to Achieve                                                                        |
| -------------------------- | --------------------------- | ------------------------------------------------------------------------------------- |
| **SSL Termination (TLS)**  | ✅ Yes                       | Use **ACM** with ALB Ingress or mount certs (e.g., Let's Encrypt with Cert-Manager)   |
| **Authentication**         | ✅ Yes                       | OIDC/JWT via annotations (e.g., ALB) or middleware (e.g., OAuth2 Proxy, NGINX config) |
| **Authorization**          | ✅ Yes                       | Custom auth layers in NGINX or service mesh (e.g., Istio RBAC)                        |
| **Rate Limiting**          | ✅ Yes                       | NGINX annotations / Kong plugins / Envoy filters                                      |
| **Caching**                | ✅ Yes                       | NGINX proxy cache, Kong plugins, or sidecar cache (e.g., Varnish)                     |
| **WAF (Web App Firewall)** | ✅ Yes                       | Use **AWS WAF** with ALB Ingress or deploy **ModSecurity** with NGINX                 |
| **DDoS Protection**        | ✅ Yes (partially)           | Handled via **AWS Shield** on ALB/NLB                                                 |
| **Logging & Monitoring**   | ✅ Yes                       | Ingress logs + CloudWatch / Prometheus + Grafana                                      |
| **IP Whitelisting**        | ✅ Yes                       | Annotation-based IP restriction (ALB) or NGINX directives                             |
| **Custom Domain Mapping**  | ✅ Yes                       | Use Route53 to map domains to ALB DNS or external IP                                  |
