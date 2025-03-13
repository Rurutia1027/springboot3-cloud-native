# Dubbo Architecture 

## Dubbo Architecture 

![dubbo architecture]()


## Specification of Node's Role 
| **Node**     | **Role Spec**                                           |
|-------------|--------------------------------------------------------|
| **Provider**  | The provider exposes remote services.                  |
| **Consumer**  | The consumer calls the remote services.                |
| **Registry**  | The registry is responsible for service discovery and configuration. |
| **Monitor**   | The monitor counts the number of service invocations and tracks time consumption. |
| **Container** | The container manages the services' lifetime.          |


## Service Reltionship 

- **Container - Provider**
Container is responsible for launching, loading, and running the service Provider. 

- **Provider - Register**
Provider registers its services to Register at the time it starts. 

- **Consumer - Register**
Consumer subscribes the services it needs from the Register when it starts. 

- **Register - {Provider, Consumer}**
Register returns the Providers list to Consumer, when it changes, the Register will push the changed data to Consumer through long connection. 

- **Consumer - Provider**
Consumer selects one of the Provider's based on soft load balancing algorithm and executes the invocation, if fails, it will choose another Provider. 

- **Consumer & Provider - Monitor**
Both Consumer and Provider wil count the number service invocations and time-consuming in memory, and send the statistics to Monitor every minute. 



## Dubbo's Features 
Dubbo has the following features: **Connectivity**, **Robustness**, **Scalability** and **Upgradeability**. 

### Connectivity 
- Register is responsible for the registration and search of service addresses, like directory services, Provider and Consumer only interact with the registry during startup, and the registry does not forward requests, so it is less stressed. 
- Monitor is responsible for counting the number of serice invocations and time-consuming, the statistics will assembles in Provider and Consumer's memory first and then sent to Monitor. 
- Provider registers services to Register and report time-consuming statistics(not include network overhead) to Monitor. 

- Consumer gets a list of service provider addresses form Registry, call the provider directly according to the LB-algorithm, report the time-consuming statistics to Montior, which includes network overhead. 
- The connections between Register, Provider, and Consumer are long connections, Monitor is an exception. 
- Register is aware of the existence of Provider through the long connection, when Provider gets down, Register will push the event to Consumer.
- It doesn't affect the already running instances of Provider and Consumer even all of the Register and Monitor get down, since Consumer got a ache of Provider's list. 
- Register and Monitor are optional, Consumer can connect Provider directly. 

### Robutness 



### Scability 


### Upgradeablity 

## References 
- [Dubbo Architecture](https://dubbo.apache.org/docs/v2.7/user/preface/architecture/)