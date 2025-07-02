## observability /datadog
- **instrumentation.opentelemetry.io/xxxxxx**
- The **OpenTelemetry Operator** must be installed in the cluster.
- The pod’s namespace should have the **operator’s Instrumentation resource** configured

```yaml
apiVersion: v1
  kind: Pod
metadata:
  name: my-java-app
  label:
  annotations:
    # java
    instrumentation.opentelemetry.io/inject-java: "true"
    instrumentation.opentelemetry.io/otel-collector-endpoint: "http://otel-collector:4317"
    instrumentation.opentelemetry.io/otel-service-name: "my-java-service"
    instrumentation.opentelemetry.io/java-image: "ghcr.io/open-telemetry/opentelemetry-operator/autoinstrumentation-java:latest"
    instrumentation.opentelemetry.io/java-jvm-args: "-Dotel.traces.sampler=parentbased_traceidratio -Dotel.traces.sampler.arg=0.1"
    instrumentation.opentelemetry.io/otel-metrics: "false"
    instrumentation.opentelemetry.io/otel-logs: "false"
    
    # python
    instrumentation.opentelemetry.io/inject-python: "true"
    instrumentation.opentelemetry.io/otel-collector-endpoint: "http://otel-collector:4317"
    instrumentation.opentelemetry.io/otel-service-name: "my-python-service"
    instrumentation.opentelemetry.io/python-image: "ghcr.io/open-telemetry/opentelemetry-operator/autoinstrumentation-python:latest"
    instrumentation.opentelemetry.io/python-env: "OTEL_TRACES_SAMPLER=parentbased_traceidratio,OTEL_TRACES_SAMPLER_ARG=0.1"
    instrumentation.opentelemetry.io/otel-metrics: "false"
    instrumentation.opentelemetry.io/otel-logs: "false"
spec:
  containers:
    - name: my-java-app
      image: my-java-app:1.0.0
```

| **Annotation Key**                                         | **Value**                                                                         | **Description**                                                                                         |
| ---------------------------------------------------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| `instrumentation.opentelemetry.io/inject-java`             | `"true"`                                                                          | Enables auto-instrumentation for Java by injecting OpenTelemetry agent into the container.              |
| `instrumentation.opentelemetry.io/otel-collector-endpoint` | `"http://otel-collector:4317"`                                                    | Specifies the endpoint of the OpenTelemetry Collector where traces/logs/metrics should be exported.     |
| `instrumentation.opentelemetry.io/otel-service-name`       | `"my-java-service"`                                                               | Sets the service name that appears in observability backends (e.g., Datadog, Jaeger, etc.).             |
| `instrumentation.opentelemetry.io/java-image`              | `"ghcr.io/open-telemetry/opentelemetry-operator/autoinstrumentation-java:latest"` | Docker image used for the OpenTelemetry Java auto-instrumentation agent.                                |
| `instrumentation.opentelemetry.io/java-jvm-args`           | `"-Dotel.traces.sampler=parentbased_traceidratio -Dotel.traces.sampler.arg=0.1"`  | JVM arguments passed to configure trace sampling strategy — here, 10% sampling with parent-based logic. |
| `instrumentation.opentelemetry.io/otel-metrics`            | `"false"`                                                                         | Disables collection and export of metrics from the instrumented application.                            |
| `instrumentation.opentelemetry.io/otel-logs`               | `"false"`                                                                         | Disables collection and export of logs from the instrumented application.                               |

| **Annotation Key**                                         | **Value**                                                                           | **Description**                                                                                     |
| ---------------------------------------------------------- | ----------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- |
| `instrumentation.opentelemetry.io/inject-python`           | `"true"`                                                                            | Enables auto-instrumentation for Python applications using the OpenTelemetry agent.                 |
| `instrumentation.opentelemetry.io/otel-collector-endpoint` | `"http://otel-collector:4317"`                                                      | Endpoint where telemetry data (traces/logs/metrics) is sent (typically an OpenTelemetry Collector). |
| `instrumentation.opentelemetry.io/otel-service-name`       | `"my-python-service"`                                                               | Logical name of the Python service used in observability tools.                                     |
| `instrumentation.opentelemetry.io/python-image`            | `"ghcr.io/open-telemetry/opentelemetry-operator/autoinstrumentation-python:latest"` | Docker image for OpenTelemetry Python auto-instrumentation agent.                                   |
| `instrumentation.opentelemetry.io/python-env`              | `"OTEL_TRACES_SAMPLER=parentbased_traceidratio,OTEL_TRACES_SAMPLER_ARG=0.1"`        | Environment variables to configure tracing behavior (e.g., 10% sampling).                           |
| `instrumentation.opentelemetry.io/otel-metrics`            | `"false"`                                                                           | Disables metrics collection.                                                                        |
| `instrumentation.opentelemetry.io/otel-logs`               | `"false"`                                                                           | Disables logs collection.                                                                           |



