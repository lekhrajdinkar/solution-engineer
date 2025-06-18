# Annotation
## sa : eks.amazonaws.com/role-arn
```yaml
    apiVersion: v1
    kind: ServiceAccount
    metadata:
        annotations:
          eks.amazonaws.com/role-arn: arn:aws:iam::123456789012:role/my-role
 ```

- EKS mutates the Pod spec to add:
  - A **projected volume** for the AWS IAM token (aws-lam-token).
  - A **projected volume** for Kubernetes API credentials (kube-api-access-*).
  - A **downwardAPI** volume for Pod metadata.
- yaml : VOLUMEs
```yaml
  - name: aws-lam-token
    projected:
    sources:
      - serviceAccountToken:
          audience: sts.amazonaws.com  # AWS STS audience
          expirationSeconds: 86400     # 24-hour token validity
          path: token                  # Mounted at `/var/run/secrets/eks.amazonaws.com/serviceaccount/token`

  - name: kube-api-access-j4hxt
    projected:
    sources:
      - serviceAccountToken:
          expirationSeconds: 3607      # Short-lived Kubernetes API token
          path: token
      - configMap:
          name: kube-root-ca.crt      # Cluster CA certificate
          items:
            - key: ca.crt
              path: ca.crt
 ```
```yaml
  downwardAPI:
    items:
    - fieldRef:
        fieldPath: metadata.namespace  # Injects the Pod's namespace
        path: namespace
```
- yaml: pod
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
  serviceAccountName: my-service-account  # Linked to an IAM role (for IRSA)
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: aws-iam-token
      mountPath: /var/run/secrets/aws-iam-token
    - name: kube-api-access-j4hxt
      mountPath: /var/run/secrets/kubernetes.io/serviceaccount
  volumes:
  - name: aws-iam-token
    projected: {...}  # As in above snippet
  - name: kube-api-access-j4hxt
    projected: {...}  # As in above snippet
```
    