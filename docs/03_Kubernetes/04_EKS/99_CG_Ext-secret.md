- Deploy the External Secrets Operator to your Kubernetes cluster.
```
helm repo add external-secrets https://charts.external-secrets.io
helm repo update
helm install external-secrets external-secrets/external-secrets

```
- ensure Kubernetes service account has access to AWS Secrets Manager.
```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "secretsmanager:GetSecretValue"
      ],
      "Resource": "arn:aws:secretsmanager:<region>:<account-id>:secret:<secret-name>*"
    }
  ]
}
```
- Deploy Kubernetes ExternalSecret
  - `SecretStore` 
  - `ExternalSecret`
```
# ------ SecretStore ---------

apiVersion: external-secrets.io/v1beta1
kind: SecretStore
metadata:
  name: aws-secret-store
spec:
  provider:
    aws:
      service: SecretsManager
      region: <your-region>
      auth:
        jwt:
          serviceAccountRef:
            name: <your-service-account>
            namespace: <namespace>

# ----- ExternalSecret -------

apiVersion: external-secrets.io/v1beta1
kind: ExternalSecret
metadata:
  name: my-secret
spec:
  refreshInterval: 1h
  secretStoreRef:
    name: aws-secret-store
    kind: SecretStore
  target:
    name: my-k8s-secret
    creationPolicy: Owner
  data:
  - secretKey: username
    remoteRef:
      key: my-secret-key
      property: username
  - secretKey: password
    remoteRef:
      key: my-secret-key
      property: password

```
- kubectl get secret my-k8s-secret
