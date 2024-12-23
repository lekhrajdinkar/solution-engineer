# AWS KMS

## 1. Encrypt/decrypt (< 4KB) 
- straight forward, nothing new
- ![img.png](../99_img/dva/kms/01/img.png)

---
## 2. Encrypt/decrypt (> 4KB) `big files`
- happens at client side :point_left:
- generating **data key**: `DEK` 
  - using it for en/de
  - can cache this cache and re-use
  - SDK simplify, use it.

```yaml
- aws kms generateDatakey
  - plaintext DEK
- aws kms generateDatakey --CMK-1
  - plaintext DEK
  - plaintext DEK + CMK-1 ==> encrypted DEK (ciphertextBlob)
- aws kms generateDatakeyWithoutPlaintext --CMK-1
  - plaintext DEK + CMK-1 ==> encrypted DEK (ciphertextBlob)
```

### 2.1 **envelop encryption**
![img_1.png](../99_img/dva/kms/01/img_1.png)

### 2.2 **envelop de-cryption**
![img_2.png](../99_img/dva/kms/01/img_2.png)

---
