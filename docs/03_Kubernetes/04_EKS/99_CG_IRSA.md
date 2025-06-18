- https://chatgpt.com/c/67240e7e-6e38-800d-8e1d-3b7f1a8fe509
- for AWS resource permission - use IRSA
- for K8s resource - use K8s:RBAC - role and roleBinding. :point_left:
--- 
# `IRSA`
- AWS-IAM-role for k8s-service-Accounts.
- allows assigning AWS IAM roles to Kubernetes Service Accounts on Amazon EKS clusters.
- enables pods running in the cluster to securely access AWS resources.
- benefits:
  - IRSA leverages AWS’s `temporary` credentials.
  - `Fine-grained access`-  Each pod can assume an IAM role with specific permissions.
  - `Secure authentication` - Uses Amazon’s OpenID Connect (OIDC) provider to securely authenticate Service Accounts with IAM.
    - EKS-cluster(oidc-1) > NS > create SA
    - oidc-1 will authenticate SA :point_left:


## Steps
- Enable the OIDC Provider for Your EKS Cluster
- Create an IAM role - `role-1-sa-1`
  - **permission policy** : add
  - **trust policy**
    - trusted principle - OIDC provider federated user.
    - serviceAccount in k8s object > authenticated by OIDC > become federated user to assume role.  :point_left:
    - notice condition: audience is `sa of ns`
    - check this : [trusted_policy_k8s_federated_sa.tftpl](..%2F..%2F04_terraform%2Fproject%2Faws-config-maps%2F03_outbound%2Fmodules%2Feks%2Ftrusted_policy_k8s_federated_sa.tftpl)
    ```
    {
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": {
                "Federated": "arn:aws:iam::533267082359:oidc-provider/oidc.eks.us-west-2.amazonaws.com/id/C1D7C8CD6FB2C01B2998093B7999CB8D"
            },
            "Action": "sts:AssumeRoleWithWebIdentity",
            "Condition": {
                "StringEquals": {
                    "oidc.eks.us-west-2.amazonaws.com/id/C1D7C8CD6FB2C01B2998093B7999CB8D:sub": "system:serviceaccount:dev-ns:spring-app-sa"
                }
            }
        }
    ]
    }
    ```
- create sa-1 k8s object and **annotate**
  - **eks.amazonaws.com/role-arn: arn:aws:iam::<account-id>**:role/`role-1-sa-1`
- Associate the Service Account with Your `Pods`. 
  - **serviceAccountName**: sa-1

## summary
- The Kubernetes service account (sa-1) is used by a pod running in the EKS cluster.
- `sa-1` is annotated with the ARN of `iam-role-1`, allowing it to be linked to AWS permissions.
- `oidc-1`, acts as a federated identity provider for `sa-1`
- sa-1 gets token from odic-1
- token verified by iam:identity provider
- trusted policy of `iam-role-1`, permits sa-1 to assume it.