## 1 App pipeline :parking:
- in progress...

## 2. terraform config to deploy pipeline on harness. :parking:
- in progress...
- pipeline-1 for ms-1 (deployment helm)
- pipeline-2 for ms-2 (deployment helm)
- ...
```text
pipeline-1
    stage-1 : dev deploy : steps >>> build > push to nexus > get version > helm update (app version, values.yaml) > deploy helm
    stage-2 : QA deploy
    stage-3 : serviceNow
    stage-4 : promote to prod 
```