- secretStore-1 to connect to AWS region and secret-manager-service
- external secret
    - to read from aws-secret-1/2/3/... into k8s-secret 
    - connect via secretStore-1
  
## 1. Deploy the External Secrets Operator
- deploy External Secrets Operator to our Kubernetes cluster.
- with helm
```bash
helm repo add external-secrets https://charts.external-secrets.io
helm repo update
helm install external-secrets external-secrets/external-secrets
```

## 2. create::SecretStore
```yaml
apiVersion: external-secrets.io/v1beta1
kind: SecretStore
metadata:
  name: aws-secret-store 🟡
spec:
  provider:
    aws:
      service: SecretsManager
      region: <your-region>
      auth:
        jwt:
          serviceAccountRef 👈🏻: 
            name: sa-1 🔸
            namespace: <namespace>
```

### Add permission
- sa-1 🔸 --> AWS Secrets Manager
```json5
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

## 3. ExternalSecret
- kubectl get secret my-k8s-secret

```yaml
apiVersion: external-secrets.io/v1beta1
kind: ExternalSecret
metadata:
  name: my-secret
spec:
  refreshInterval🔸: 1h
  secretStoreRef🔸:
    name: aws-secret-store 🟡
    kind: SecretStore
  target🔸:
    name: my-k8s-secret
    creationPolicy: Owner
    
  data:
  - secretKey: username
    remoteRef:
      key: aws-secret-1
      property: key-1
      
  - secretKey: password
    remoteRef:
      key: aws-secret-2
      property: key-1

```

