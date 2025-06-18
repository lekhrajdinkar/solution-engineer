# AWS KMS CLI Commands

## 1. Create a New KMS Key
```bash
aws kms create-key \
  --description "Example KMS Key" \
  --key-usage ENCRYPT_DECRYPT \
  --customer-master-key-spec SYMMETRIC_DEFAULT
```

### Description:
- Creates a new KMS key for encryption and decryption.
- `--description`: A short description of the key.
- `--key-usage`: Specifies the cryptographic operations.
- `--customer-master-key-spec`: Specifies the key type.

## 2. List KMS Keys
```bash
aws kms list-keys
```

### Description:
- Lists all KMS keys in your account.

## 3. Describe a KMS Key
```bash
aws kms describe-key \
  --key-id <key-id>
```

### Description:
- Provides details about a specified KMS key.
- Replace `<key-id>` with the ID or ARN of the KMS key.

## 4. Enable a KMS Key
```bash
aws kms enable-key \
  --key-id <key-id>
```

### Description:
- Enables a disabled KMS key.

## 5. Disable a KMS Key
```bash
aws kms disable-key \
  --key-id <key-id>
```

### Description:
- Disables a KMS key to prevent its use.

## 6. Schedule Key Deletion
```bash
aws kms schedule-key-deletion \
  --key-id <key-id> \
  --pending-window-in-days 30
```

### Description:
- Schedules the deletion of a KMS key after a specified number of days (7 to 30).
- `--pending-window-in-days`: Specifies the waiting period before deletion.

## 7. Cancel Key Deletion
```bash
aws kms cancel-key-deletion \
  --key-id <key-id>
```

### Description:
- Cancels a scheduled key deletion.

## 8. Encrypt Data
```bash
aws kms encrypt \
  --key-id <key-id> \
  --plaintext fileb://example.txt \
  --output text \
  --query CiphertextBlob > encrypted.txt
```

### Description:
- Encrypts data using the specified KMS key.
- Replace `example.txt` with the plaintext file.

## 9. Decrypt Data
```bash
aws kms decrypt \
  --ciphertext-blob fileb://encrypted.txt \
  --output text \
  --query Plaintext | base64 --decode > decrypted.txt
```

### Description:
- Decrypts previously encrypted data.
- Replace `encrypted.txt` with the file containing the encrypted data.

## 10. Generate Data Key
```bash
aws kms generate-data-key \
  --key-id <key-id> \
  --key-spec AES_256 \
  --output text \
  --query CiphertextBlob > data_key.txt
```

### Description:
- Generates a data key that can be used for local encryption.
- `--key-spec`: Specifies the key length (e.g., `AES_256` or `AES_128`).

---

