# Dubbo Architecture 

## Dubbo Architecture 

![dubbo architecture](./dubbo-architecture.jpg)


## Specification of Node's Role 
| **Node**     | **Role Spec**                                           |
|-------------|--------------------------------------------------------|
| **Provider**  | The provider exposes remote services.                  |
| **Consumer**  | The consumer calls the remote services.                |
| **Registry**  | The registry is responsible for service discovery and configuration. |
| **Monitor**   | The monitor counts the number of service invocations and tracks time consumption. |
| **Container** | The container manages the services' lifetime.          |

## Service Invocation Steps

- step1, Container is responsible for launching, loading, and running the service Provider.
- step2, Provider registers its services to Register at the time it starts.
- step3, Consumer subscribes the services it needs from the Register when it starts.
- step4, Register returns the Providers list to Consumer, when it changes, the Register will **pull** the changed data
  to Consumer through long connection.
- step5, Consumer selects one of the Providers based on soft load balancing algorithm and executes the invocation, if
  fails, it will choose another Provider.
- step6, Both Consumer and Provider will count the number service invocations and time-consuming in memory, and send the
  statistics to **Monitor** everytime.

## Dubbo's Features 

Dubbo has the following features: **Connectivity**, **Robustness**, **Scalability** and **Upgradeability**.

### Connectivity 

### Robutness 

### Scalability

### Upgradeablity 

## References 
- [Dubbo Architecture](https://dubbo.apache.org/docs/v2.7/user/preface/architecture/)