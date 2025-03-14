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

- Register is responsible for the registration and search of service addresses, like directory services, Provider and
  Consumer only interact with the registry during startup, and the registry does not forward requests, so it is less
  stressed.
- Monitor is responsible for counting the number of service invocations and time-consuming, the statistics will assemble
  in Provider's and Consumer's memory first and then sent to Monitor.
- Provider registers services to Register and report time-consuming statistics(not include network overhead) to '
  Monitor'.
- Consumer gets a list of service provider addresses from Registry, call the provider directly according to LB
  algorithm, report the time-consuming statistic to Monitor, which includes network overhead.
- The connections between Register, Provider and Consumer are long connections, Monitor is an exception.
- Register is aware of the existence of Provider through the long connection, when Provider gets down, Register will
  push the event to Consumer.
- It doesn't affect the already running instances of Provider and Consumer even all of the Register and Monitor get
  down, since Consumer got a cache of Provider's list.
- Register and Monitor are optional, **Consumer can connect Provider** directly.

### Robustness

- Monitor's downtime doesn't affect the usage, only lose some sampling data.
- When the DB server goes down, Register can return service Provider's list to Consumer by checking its cache, but new
  Provider cannot register any service.
- Register is a peer cluster, it will automatically switch to another when any instance goes down.
- Even all Register's instance go down, Provider and Consumer can still communicate by checking their local cache.
- Service Provider's are stateless, one instance's downtime doesn't affect the usage.
- After all, the Provider's of one service go dow, Consumer can not use that service and infinitely reconnect to wait
  for service Provider to recover.

### Scalability

- Register is a peer cluster that can dynamically increases its instances, all clients will automatically discover new
  instances.
- Provider is stateless, it can dynamically increase the deployment instances, and the registry will push the new
  service provider information to the Consumer.

### Upgradeable

When the service cluster is further expanded and the IT governance structure is further upgraded, dynamic deployment is
needed, and the current distributed service architecture will not bring resistance. Here is a possible further architecture: 

![](./dubbo-architecture-future.jpg)


## References 
- [Dubbo Architecture](https://dubbo.apache.org/docs/v2.7/user/preface/architecture/)