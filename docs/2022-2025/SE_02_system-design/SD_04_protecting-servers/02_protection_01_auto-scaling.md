# Protection server : Auto-scaling `adaptation`
- [NFR_03_Scaling](../SD_02_Non-functional-req/02_NFR_03_Scaling.md#scaling-protects-server-from-death-spiral-)

## Overview
- Dynamically add capacity to match demand
```mermaid
flowchart LR
    A[- server run with \nEnough Capacity \n - by scaling⭐] --> B[No Overload]
    B --> C[No Timeouts]
    C --> D[No Retries]
    D --> E[No retry storm \n No Death Spiral]
```
**Scaling policies(AWS)**
- **Scheduled Scaling** : Changes capacity at known times.
- **Predictive Scaling** : Uses historical traffic patterns to provision capacity before demand arrives.
- **Metric based scaling**
    - **SIMPLE**:
        - Performs one scaling action and waits for a cooldown period
        - CPU > 70% → add 2 servers
        - Wait 5 minutes before another action
    - **STEP**:
        - Scales by different amounts based on alarm severity.
        - | CPU utilization |        Action |
                          | --------------- | ------------: |
          | 60–70%          |  Add 1 server |
          | 70–85%          | Add 2 servers |
          | Above 85%       | Add 4 servers |

    - **TARGET TRACKING**
        - Keeps a metric near a target value.
      ```
      - Target CPU utilization = 60%
      - CPU > 60% → scale out
      - CPU < 60% → scale in
      ```

## AWS - Auto scaling
- [AWS ASG (auto scaling group).md](../../CE_02_AWS_SAA/01_compute/01_ASG.md)
- Communication between components are asynchronous and event-driven.
```mermaid
flowchart TD
    A[Application / AWS Resources] -->|Publish metrics| B[Amazon CloudWatch]
    B --> C1[Metric Storage: \n Namespace + Metric name + Dimensions]
    C1 --> C2[aggregate metrics \n avg, sum, max, min, percentile,etc]
    C2 --> D[CloudWatch Alarm:\n evaluate rules]
    D -->|Alarm state change| E[Scaling Policy]
    E --> F[EC2 Auto Scaling / Application Auto Scaling]
    F -->|Change desired capacity| G[Create or remove resources]
    G --> H[EC2 Instances / ECS Tasks / DynamoDB Capacity]
    H -->|New metrics| B
```
**Stages**

![img.png](../../../99_img/2025/se_02_sd/bm-sd/img_8888.png)

| Stage        | What happens                                                                        |
| ------------ | ----------------------------------------------------------------------------------- |
| Monitoring   | CloudWatch collects and aggregates metrics                                          |
| Detection    | Alarm waits for the configured evaluation period                                    |
| Provisioning | EC2 instance, container task, or pod is created                                     |
| Deployment   | Application starts, loads configuration, connects to dependencies, and warms caches |
| Health check | Load balancer waits until the resource is healthy                                   |



