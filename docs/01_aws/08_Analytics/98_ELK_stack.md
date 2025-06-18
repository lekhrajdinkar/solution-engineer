# Components of ELK Stack:
## E Elasticsearch
- Purpose: **Search and analytics engine** for storing and querying logs/data.
- AWS Service: `Amazon OpenSearch Service` (successor to Elasticsearch service).

## L Logstash
- Purpose: **Data processing pipeline** to collect, transform, and send logs to Elasticsearch.
- AWS Alternative: Use `AWS Lambda`, `Kinesis`, or `Amazon OpenSearch Data Prepper` for similar functionality.

## K Kibana
- Purpose: **Visualization and dashboard tool** for Elasticsearch data.
- AWS Service: `Amazon OpenSearch Dashboard` (integrated with OpenSearch).