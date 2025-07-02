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

## helm
- use template engine like helm  to make it dynamic
- example
```yaml
# External Secrets Operator (ESO) - Secret Store
secretStore:
  create: true
  region: us-east-1
  serviceAccountName: sa-1

# External Secrets Operator - External Secrets
externalSecrets:
  db:
    create: false
    refreshInterval: 1m
    targetName: db-credential # target kubernetes-secret name
    amsSecretManagerSecretName: "aws-secretManager-1" # source AMS Secrets manager secret
    amsSecretManagerSecretKey1: username # source key1 in AMS Secrets manager
    targetSecretKey1: username # target kubernetes secret key1
    amsSecretManagerSecretKey2: password # source key2 in AMS Secrets manager
    targetSecretKey2: password # target kubernetes secret key2
  
  tls:
    create: true
    refreshInterval: 1m
    targetName: fsr-backend-release-deo3-tls-cert # target kubernetes-secret name
    amsSecretManagerSecretName: "aws-secretManager-2" # source AMS Secrets manager secret
    amsSecretManagerSecretKey1: certificate # source key1 in AMS Secrets manager
    targetSecretKey1: tls.crt # target kubernetes secret key1
    amsSecretManagerSecretKey2: private_key # source key2 in AMS Secrets manager
    targetSecretKey2: tls.key # target kubernetes secret key2
```

