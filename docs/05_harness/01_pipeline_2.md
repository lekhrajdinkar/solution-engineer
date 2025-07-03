# project-2 pipelines
## ✔️app-pipeline
- single pipeline for all env.
- for both primary and secondary regions

### Stage 1 : Build + push to nexus
- code g-scan
- get version(from helm)
- **dind** > dockerize > image:v2
- push app-image to nexus-dev
- update `helm-value.yml` to image:v2 from image:v1
- push helm-chart to nexus-dev

### Stage 2 : copy2ECR (non-prod)
- app docker image  (primary) 
- app helm package (primary)
- app docker image (secondary) 
- app helm package (secondary)

### Stage 3: helm install ((non-prod)) 🟢
- step : **helm install**

### Stage 4 : Approval
- serviceNow

### Stage 5 : Promote to prod
- image g-scan - image (not codebase) - input:imageName+version
- nexus-dev >> nexus-prod 👈🏻
- app docker image  (primary)
- app helm package (primary)
- app docker image (secondary)
- app helm package (secondary)

### Stage 6: helm install (prod) 🟢
- step : **helm install**

### Custom bash scripts
<details> <summary>Custom bash scripts</summary>

```bash
  #========== 1 get version (from helm chart) ===========
  
  helmVersion=$(cat $HELM_CHART_DIR/chart.yaml | grep version:)
  appVersion=$(cat $HELM_CHART_DIR/chart.yaml | grep version:)
  # major and minor version using regex in $major $minor
  
  #========== 2 dockerize ===========
  
  docker login -u <+pipeline.getvalue(nexus_user)> -p  <+pipeline.getvalue(nexus_password)>
  docker build -t nexus-dev/$image:appVersion --label git_branch= --label=commit_id=
  docker push  nexus-dev/$image:appVersion
  
  #========== 3  update helm (new image) >> push helm-chart to nexus-dev  ===========
  yq --version
  yq e -i '.image.name = env(repoAndImageName)' $HELM_CHART_DIR/value.yaml
  yq e -i '.image.tag = env(version)' $HELM_CHART_DIR/value.yaml
  
  helm lint  $HELM_CHART_DIR --value=$HELM_CHART_DIR/value.yaml
  helm package  $HELM_CHART_DIR
  helm registry login -u <+pipeline.getvalue(nexus_user)> -p  <+pipeline.getvalue(nexus_password)>
  helm push  $image:helmVersion nexus-dev
  
  #========== 4 copy2ECR  (nexus dev >> ecr) ===========
  crane version
  crane auth login -u <+pipeline.getvalue(nexus_user)> -p  <+pipeline.getvalue(nexus_password)> nexus-dev-registry
  
  # copy from nexus to ecr 
  export $(printf "AWS_ACCESS_KEY=%s AWS_SECRET_ACCESS_KEY=%s AWS_SESSION_TOKEN=%s")
  $(aws sts assume --role harness-pipleline-role --session-name --query "Credential.[AccesskeyId, SecretAccesskey, SessionToken] --output text")
  ecr_password=$(aws ecr get-login-password --region)
  crane auth login -u AWS -p  ecr_password erc-repo
  
  # copy app-image and helm
  crane cp nexus-prod/$app_image:$app_image $ecr-repo-prod/$app_image:$app_image
  crane cp nexus-prod/$app_image:$app_image-helm $ecr-repo-prod/$app_image:$app_image-helm
  
  #========== 5 Promote to prod (nexus dev >> nexus prod >> ECR) ===========
  
  crane version
  crane auth login -u <+pipeline.getvalue(nexus_user)> -p  <+pipeline.getvalue(nexus_password)> nexus-dev-registry
  crane auth login -u <+pipeline.getvalue(nexus_user_prod)> -p  <+pipeline.getvalue(nexus_password_prod)> nexus-prod-registry
  
  # copy from nexus to ecr (of Life cycle AWS )
  export $(printf "AWS_ACCESS_KEY=%s AWS_SECRET_ACCESS_KEY=%s AWS_SESSION_TOKEN=%s")
  $(aws sts assume --role harness-pipleline-role --session-name --query "Credential.[AccesskeyId, SecretAccesskey, SessionToken] --output text")
  ecr_password=$(aws ecr get-login-password --region)
  crane auth login -u AWS -p  ecr_password erc-repo
  
  # copy app-image : nexus dev >> nexus prod >> ECR
  crane cp nexus-dev/$app_image:$app_image nexus-prod/$app_image:$app_image 
  crane cp nexus-prod/$app_image:$app_image $ecr-repo-prod/$app_image:$app_image
  
  # copy helm  : nexus dev >> nexus prod >> ECR
  crane cp nexus-prod/$app_image:$app_image-helm $ecr-repo-prod/$app_image:$app_image-helm
  crane cp nexus-dev/$app_image:$app_image-helm nexus-prod/$app_image:$app_image-helm 
  
  #========== 6. Helm install ===========
  kubectl version
  helm version
  
  export $(printf "AWS_ACCESS_KEY=%s AWS_SECRET_ACCESS_KEY=%s AWS_SESSION_TOKEN=%s")
  $(aws sts assume --role harness-pipleline-role --session-name --query "Credential.[AccesskeyId, SecretAccesskey, SessionToken] --output text")
  
  aws ssm get-parameter --region --name "mc/cluster-1/kubeconfig" --query "parameter.Value" --output text > kubeconfig
  
  export KUBECONFID="$PWD/kubconfig"
  kubectl auth can-i create deployment -n ns-1
  
  ecr_password=$(aws ecr get-login-password --region)
  helm registry login -u AWS -p ecr_password
  
  helm pull oci://$ecr_repo/$image:$appVersion  --version $helmVersion
  tar ...  
  helm upgrade --install $RELEASE_NAME $image:$appVersion --value ./values.yaml -n --wait 300s --atomic
```
</details> 

## ✔️lambda layer pipeline
### Stage: build
- drops layer to aws s3
```bash
  export $(printf "AWS_ACCESS_KEY=%s AWS_SECRET_ACCESS_KEY=%s AWS_SESSION_TOKEN=%s")
  $(aws sts assume --role harness-pipleline-role --session-name --query "Credential.[AccesskeyId, SecretAccesskey, SessionToken] --output text")
   
  pip3 install ${requiements_path} --target ./python/lib/python3.11/site-packages
  python3 -c "import shutil.make_archive(${ZIP_FILE} , 'zip', root_dir = '.' base_dir='python')"
  aws s3 cp ${layer_name}.zip
```
