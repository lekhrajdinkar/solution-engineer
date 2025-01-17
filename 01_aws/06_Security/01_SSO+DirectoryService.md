# A. **Directory Service**
## 1. Simple AD
![img_5.png](../99_img/security/org-2/img_5.png)
- aws managed AD, users present only in this AD

## 2. AD Connector
![img_6.png](../99_img/security/org-2/img_6.png)
- users present in on-prem AD, only
- `proxy` help to connect it.

## 3. AWS managed AD
![img_7.png](../99_img/security/org-2/img_7.png)
- hybrid kind : user present in both : `AWS AD` + `on-prem AD`
- `AWS AD` create trust with `on-prem AD`

---
# B. SSO or **Identity provider** 
- use case:
    - sso for all account in [aws org](./03_AWS_org+control-tower)
    - sso for all ec2 instance in an aws account
- IP has :
    - `AD : Active directory`  + integrate to `3rd party IP (OKta)`
        - [check here](#f-aws--ad-active-directory)
    - `permission set` : which user has access to what: `fine grained permission and assginmnet`
        - lek role-1(full-access) on  member-account-1
        - lek role-2(read-access) on  member-account-1
        - lek role-3(write-access) on  member-account-1
        - lek role-1(write-access) on  member-account-2

- Flow:
    - lek -> okta home  --> aws-mgt-acct:`AWS IP/SSO with permission-Set` --> `Okta SSO`
        - member-account-1 > assume role-1, role-2, role-3
        - member-account-2 > assume role-1
        - how and where SAML fits ?

- ![img_4.png](../99_img/security/org-2/img_4.png)
- ![img_8.png](../99_img/security/org-2/img_8.png)
- ![img_9.png](../99_img/security/org-2/img_9.png)
- ![img_3.png](../99_img/security/org-2/img_3.png)







