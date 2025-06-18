# Secret manager
## Intro
- `Rotation`
  - enforce `rotation` of secrets every X days
  - `Automate generation of secrets` on rotation (uses `Lambda`)
  
- `replicate across region`
  - `primary`
  - `replica` in region
    - while DR, can promote as primary
    
- Integration with:
    - Amazon RDS (MySQL, PostgreSQL)
    - Aurora
    - KMS
    - ...

---
## Exam scenario
- #1. API gateway API key stored in secret manager. rotate it.
  - only database credential rotate automatically.
  - for API key, use **lambda** which will request new key and update secret, **programmatically**.