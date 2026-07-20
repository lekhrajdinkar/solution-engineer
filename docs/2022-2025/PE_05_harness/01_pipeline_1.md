# project-1 pipelines

## ✔️infra-pipeline 
- deploy aws, kafka, okta, etc
### stage1 : trf plan
- code g-scan
- custom bash :: **TRF_PLAN**
### stage2 
- harness approval
- harness-template-2 :: manual approval
### stage3  : trf apply
- code g-scan
- custom bash ::**TFR_APPLY**

<details> <summary>Custom bash scripts</summary>

```bash
terraform -v
tfr_workspace=<+pipeline.variables.tf_ws>
tfe_ws_id=<+pipeline.variables.tf_ws_id>
tfe_host=<+pipeline.variables.tf_host>

# login
# option-1
wget ansible/tfeSync.zip
unzip tfeSync.zip
./tfesync -w tfe_ws_id
# option-2
terrafom login -p $TFE_TOKEN

# create :: credential.trfc.son with $TFE_TOKEN
# create :: backend.tf with tf_ws_id
terrafom init
terrafom plan -var-file ./env/${ENV}.tfvars
```
</details>

## ✔️api-pipeline
- 3 diff pipeline, one for each env.
### stage 1 : ecr push
- code g-scan
- get version (from branch name)
```bash
  GIT_BRANCH = <+pipeline.variables.GIT_BRANCH>
  VERSION = $GIT_BRANCH.split('\/')[1]
  VERSION_WITH_SEQ = "${VERSION}-"<+pipeline.sequenceId>
```

- `harness-template-1` :: **build and push to docker Hub** ❌
- `harness-template-2` :: **build and push to AWS-ECR**  ✅
    - aws connector (aws secret key from broadAccessRole)
    - aws account id + region
    - image name
    - codebase, already present in pipeline

### stage 2 : Approval
- only for prod pipeline 
- servicenow approval

### stage 3 : ecs deploy
- custom bash :: deploy to ECS
```bash
  current_role = $(aws sts get-caller-identity)
  export $(printf "AWS_ACCESS_KEY=%s AWS_SECRET_ACCESS_KEY=%s AWS_SESSION_TOKEN=%s")
  $(aws sts assume --role harness-pipleline-role --session-name --query "Credential.[AccesskeyId, SecretAccesskey, SessionToken] --output text")
  
  current_role = $(aws sts get-caller-identity)
  
  old_tasks=$(aws ecs list-task --cluster --service-name --region    --query tasjArn[*] --output text)
  for task in old_tasks; 
  do aws ecs stop task --cluster --service-name --region
  aws ecs stop task --cluster --service-name --region --force-new-deployment
```

