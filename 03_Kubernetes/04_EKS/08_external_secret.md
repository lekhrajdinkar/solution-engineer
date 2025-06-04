## A. external-secrets.io
- check here pls : [05-external-secret-aws.yaml](../../deployment/manifest/spring_app_v2/05-external-secret-aws.yaml)

### 1. SecretStore
- provider : aws
  - region
  - serviceAccountRef : sa-1 (aws-isra-role-1)
```yaml
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "secretsmanager:GetSecretValue",
        "secretsmanager:ListSecrets"
      ],
      "Resource": [
        "arn:aws:secretsmanager:us-east-1:35326752882:secret:aws-secretManager-*",
      ]
    }
  ]
} 
```
### 2. ExternalSecret
- **secretStoreRef** : above one
- **target** : **k8s**-secret-1 
- **data** :
  - k8s-secret-1.key-1 = remoteRef ( **aws**-secretManager-1.key-1 )
  - k8s-secret-1.key-2 = remoteRef ( **aws**-secretManager-2.key-1 )
  - ...
  - mapping between k8s and aws

### valus.yaml (HELM)
- just for reference / ccgg
```yaml
# External Secrets Operator (ESO) - Secret Store
secretStore:
  create: true
  region: us-east-1
  serviceAccountName: backend-eso-as-deo3

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