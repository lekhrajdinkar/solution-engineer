# CloudHSM 
![img.png](../99_img/dva/kms/03/img.png)

## Intro
- **HSM** = `Hardware Security Module`

- **CloudHSM**
  - service to provision HSM in **high-availability**  cluster
  - tamper resistant, FIPS 140-2 Level 3 compliance
  - MFA support
  
- **CloudHSM Client** 
  - dedicated Software

- HSM cluster
  - ![img_1.png](../99_img/dva/kms/03/img_1.png)

- integrated with KMS
  - supports **SSE-C** (symmetric + asymmetric keys)
   - ![img_2.png](../99_img/dva/kms/03/img_2.png)