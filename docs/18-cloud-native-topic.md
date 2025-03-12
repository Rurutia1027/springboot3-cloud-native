# Cloud Native 

## Core Characteristics of Cloud Native 
Cloud Native is not single technology but an **architectural philosophy and design patterh** that typically includes:
- **Contarinerization**: Applications run inside containers(e.g., Docker) to ensure environmental consistency and facilitate deployment and scaling.
- **Microservices Architecture**: The applicaiton is broken into independent services taht communicate with each other and can scale individually.
- **Dynamic Orchestration**: Kubernetes and similar tools manage service deployment, scaling, and fault recovery automatically.
- **Declarative APIs**: Infrastructure is managed using API-driven configurations like Kubernetes YAML files or Terraform scripts.
- **Resilience & Self-Healing**: The applicaiton should handle failures automatically to ensure high availability (e.g., Duboo provides some fault-tolerance mechanisms).
- **Observability**: Applications should have comprehensive logging, monitoring, and tracing (e.g., Prometheus, Grafana, Jaeger).
- **DevOps** & **CI/CD**: Automation in deployment through CI/CD pipelines (e.g., GitHub Actions, Jenkins). 

--- 

## How Our Project Aligns with Cloud Native 
### What Our Project Already Has 
#### Spring Boot + Dubbo -> Microservices Architecture 
- Our project is built on Spring Boot and Dubbo, which facilitate **distributed RPC communication**, aligning with the microservices paradigm.

#### Docker / Kubernetes Support -> Containerization & Orchestration 
- We adopt Docker and Kubernetes deployment configurations -- this meets Cloud Native dynamic orchestration standards. 

#### Service Discovery (Zookeeper / Nacos) -> Dynamic Service Management 
- Using Zookeeper / Nacos for service disovery ensures automatic service registration and discovery. 

#### Distributed Transactions -> Resilience & High Availability 
- Dubbo provdes **retry**, **rate limiting**, and **circut breaking** mechanisms, contributing to fault tolerance. 

#### Redis Caching -- Performance Optimization 
- Using Redis for caching improves system throughput. 

### Potential Gaps That Need Improvement 
#### Production-Grade Kubernetes Best Practices 
* Even though we adopt **Kubernetes**, but we do not adopt**Heml Charts**, **Kustomize**, or an **Operator**, our deployment strategy may not be fully Cloud Native. 

#### Complete CI/CD Pipeline 
* Our GitHub Actions setup mainly adds **MySQL support**, but we lack a full **CI/CD pipeilne** (e.g., Blue-Green, or Canary deployments), that should be improved. 

#### Observability & Moitoring 
- We haven't mentioned a monitoring stack like **Prometheus** + **Grafana** + **Loki** + **Jaeger**. If our project lacks proper tracing and logging, it may not be fully Cloud Native. 

#### Stateless Architecture 
- Cloud Native applications should be **stateless** where possible. We may need to ensure that our service are designed to work independently of persistent local state. 

--- 

## Conclusion  

Our current project meets serveral Cloud Native characteristics, but to be **full Cloud Native**, we considering adding: 
- **Kubernetes Production Practices** -- Helm Chars for deployment, Kubernetes Operators for automation.
- **Complete CI/CD Pipeline** -- Automate builds, testing, and deployments with GitHub Actions, ArgoCD or Tekton.
- **Observability & Monitoring** -- Integate Prometheus (metrics), Grafana(dashboards), Loki(logs), and Jaeger(tracking).
- **Stateless Architecture Enhancements** -- Use Redis for session storage instead of in-memory sessions. 

### Blue-Green Deployment 
- **Blue-Green Deployment** is a strategy that reduces downtime by running two versions of an application(Blue & Green) simultaneously. One version(Blue) serves traffic while the new version(Green) is deployed and tested. When Green is stable, traffic is switched, making deployments seamless.