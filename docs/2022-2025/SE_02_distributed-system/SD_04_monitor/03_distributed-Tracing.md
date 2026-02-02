# Distributed tracing
## Overview
- https://www.youtube.com/watch?v=XYvQHjWJJTE
- a crucial technique in microservices architecture 
- that provides insights into complex system interactions 
- by tracking individual requests across different services 

**Key components**
- **Spans**,  Each operation or task within a service generates a span.
- **Traces** , Aggregations of spans in the correct order, representing the end-to-end flow of a request.
  - `Trace ID`, A unique identifier that correlates spans across different services.

## OpenTelemetry
- A standardized, **vendor-neutral framework** 
- for instrumenting applications and 
- collecting - (log, metric, trace) data.

- **APM (Application Performance Monitoring)** tool
  - Solutions for visualizing trace data and integrating with OpenTelemetry 
  - eg: Zipkin ( Open-source ),  `DataDog`,  Splunk, Relic

- **Micrometer** 
  - A metrics collection library 
  - that can work with tracing frameworks to export **metrics** data