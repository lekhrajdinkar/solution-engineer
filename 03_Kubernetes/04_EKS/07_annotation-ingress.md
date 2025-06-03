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