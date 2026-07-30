# Microservice
## Overview
**reference**
- [start from here 👈👈](00_01_microservice-system.md)
- https://www.youtube.com/watch?v=hrvx8Nv9eQA&list=PLJq-63ZRPdBsPWE24vdpmgeRFMRQyjvvj bm playlist



``` mermaid
graph TD
    %% Base Web & Security Fundamentals
    subgraph Web_Fundamentals [Web & Security Fundamentals]
        HTTPS[HTTPS Security] -->|Uses| SSL[SSL Handshake]
        HTTPS -->|Creates| Tunnel[Secure Data Tunnel]
        HTML[HTML Structure] -->|Enables| Links[Hyperlinks]
    end

    %% Software Architectural Styles & Layers
    subgraph Architecture_Styles [Software Architectural Styles]
        direction TB
        style Architecture_Styles fill:#f9f9f9,stroke:#333,stroke-width:1px
        
        subgraph Layered_Architecture [Layered n-tier Architecture]
            PL[Presentation Layer] --> BL[Business Layer]
            BL --> PE[Persistence Layer]
            PE --> DL[Database Layer]
        end

        subgraph Architectural_Patterns [Key Architecture Patterns]
            MVP[MVP Architecture: Model-View-Presenter]
            Orch[Orchestration: Central Coordinator]
            Chor[Choreography]
            CQRS[CQRS: Separate Read/Write Workloads]
            Microkernel[Microkernel: Core + Plug-ins]
            MS[Microservices: Independently Deployable Modules]
            EDA[Event-Driven Architecture: Event Production & Consumption]
            DDD[Domain-Driven Design: Domain Logic Focus]
            SBA[Space-Based: Scalability & Consistency]
        end
    end

    %% Network Infrastructure Components
    subgraph Network_Infrastructure [Network Infrastructure Components]
        LB[Load Balancers] -->|Use Cases| Traffic[Traffic Distribution]
        LB -->|Use Cases| HA[High Availability]
        LB -->|Use Cases| SSLTerm[SSL Termination]
        LB -->|Use Cases| Session[Session Persistence]
        LB -->|Use Cases| Scale[Horizontal Scalability]
        LB -->|Use Cases| Health[Health Monitoring]

        FW[Firewalls] -->|Use Cases| PortRules[Port-Based Rules]
        FW -->|Use Cases| IPFilter[IP Address Filtering]
        FW -->|Use Cases| ProtoRules[Protocol-Based Rules]
        FW -->|Use Cases| TimeRules[Time-Based Rules]
        FW -->|Use Cases| StateInsp[Stateful Inspection]
        FW -->|Use Cases| AppRules[Application-Based Rules]
    end

    %% Memory Hierarchy
    subgraph Memory_Layer [Memory Hierarchy]
        direction BT
        HDD[Hard Disk Drives: Slower, Bigger, Long-term] --> SSD[Solid-State Drives: Fast Persistent]
        SSD --> RAM[Main Memory RAM: Primary App Storage]
        RAM --> Caches[Caches: Close to CPU]
        Caches --> Registers[Registers: Ultra-fast CPU Storage]
    end

    %% Links between components to show relationships
    PL --> LB
    DL --> CQRS
    DL --> Memory_Layer

```

Would you like me to create an additional specific diagram for one of these components, such as a detailed sequence chart of the SSL handshake or a flow mapping out the specific Load Balancing algorithms?
