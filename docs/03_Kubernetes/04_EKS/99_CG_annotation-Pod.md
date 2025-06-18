# Annotation
## pod : instrumentation.opentelemetry.io/xxxxxx
- The **OpenTelemetry Operator** must be installed in the cluster.
- The pod’s namespace should have the **operator’s Instrumentation resource** configured
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-java-app
  annotations:
    # Enable OpenTelemetry auto-instrumentation (Java)
    instrumentation.opentelemetry.io/inject-java: "true"

    # (Optional) Specify the OpenTelemetry Collector endpoint
    instrumentation.opentelemetry.io/otel-collector-endpoint: "http://otel-collector:4317"

    # (Optional) Set the service name
    instrumentation.opentelemetry.io/otel-service-name: "my-java-service"

    # (Optional) Additional Java agent settings
    instrumentation.opentelemetry.io/java-image: "ghcr.io/open-telemetry/opentelemetry-operator/autoinstrumentation-java:latest"
    instrumentation.opentelemetry.io/java-jvm-args: "-Dotel.traces.sampler=parentbased_traceidratio -Dotel.traces.sampler.arg=0.1"

    # (Optional) Disable metrics/logs if needed
    instrumentation.opentelemetry.io/otel-metrics: "false"
    instrumentation.opentelemetry.io/otel-logs: "false"
spec:
  containers:
    - name: my-java-app
      image: my-java-app:1.0.0
 ```


