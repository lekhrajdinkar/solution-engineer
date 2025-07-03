- helm chart: [spring_app_helm_v1](helm/spring_app_helm_v1)
- reference: https://chat.deepseek.com/a/chat/s/fdefc9eb-f153-4f45-91c9-6695d9f9b202

## plan / cheatsheet

| Step | Component                         | Command                                                                                  |
| ---- |-----------------------------------|------------------------------------------------------------------------------------------|
| 1    | PostgreSQL                        | `kubectl create -f 02_postgres-pod.yaml -n dev1-helm`                                    |
| 2    | RabbitMQ                          | `kubectl create -f 02-rmq-pod.yaml -n dev1-helm`                                         |
| 3    | Build Helm dependencies           | `helm dependency build ./helm/spring_app_helm_v1`                                        |
| 4    | Update Helm dependencies          | `helm dependency update ./helm/spring_app_helm_v1`                                       |
| 5    | Render templates (preview output) | `helm template release-blue ./helm/spring_app_helm_v1 > rendered-output.yaml`            |
|      | Simulate install with debug       | `helm install --dry-run --debug release-blue ./helm/spring_app_helm_v1`                  |
|      | Render specific file only         | `helm template release-blue ./helm/spring_app_helm_v1 --show-only templates/deploy.yaml` |
|      | Override values inline            | `helm install release-blue ./helm/spring_app_helm_v1 --set rabbitmq.enabled=false`       |
| 6    | First time install                | `helm install release-blue ./spring_app_helm_v1 -f values-2.yaml`                        |
| 7    | Check release status              | `helm status release-blue`                                                               |
| 8    | View final generated manifest     | `helm get manifest release-blue`                                                         |
| 9    | View applied values               | `helm get values release-blue`                                                           |
| 10   | Upgrade with new values           | `helm upgrade release-blue ./spring_app_helm_v1 --create-namespace -f values-2.yaml`     |
| 11   | Rollback to previous version      | `helm rollback release-blue 1`                                                           |
| 12   | Uninstall the release             | `helm uninstall release-blue`                                                            |
|      | Keep release history              | `helm uninstall release-blue --keep-history`                                             |


 
## more
```yaml
==== understand syntax ====
{{- if not .Values.autoscaling.enabled }}  {{- end }}
---
{{ .Values.replicaCount }}
---
{{- with .Values.imagePullSecrets }}
imagePullSecrets:
{{- toYaml . | nindent 8 }}  ## notice . , it will replaced with value
{{- end }}
vs
imagePullSecrets:
{{- toYaml .Values.imagePullSecrets | nindent 8 }} ## no .
# so what if .Values.imagePullSecrets not present, then error. go with with above.
---
image: "{{ .Values.image.repository }}:{{ .Values.image.tag | default .Chart.AppVersion }}"
---
            {{- range .Values.springApp.env }}
            - name: {{ .name }}
              value: {{ .value | quote }}
            {{- end }}
```

---
# project-2 - microservices
- in progress - ms