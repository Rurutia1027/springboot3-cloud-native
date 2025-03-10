# Spring Security Session Management 

## Concurrent Session Control 

- Spring Security provides mechanisms to **limit the number of active sessions per user**

### Key Configurations 
- Limit concurrent sessions per user 
```java 
@Configuration  
public class SecurityConfig {
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionPreventLogin(true)            
            ); 
        return http.build(); 
    }
}
```

### Session Expiry Handling 
- `maxSessionsPreventsLogin(true)`: Rejects new logins when the session limit is reached. 
- `maxSessionsPreventsLogin(false)`: Allows new logins but invalidates the oldest session. 



## Session Expiration Handling 
### Session Timeout 
- Set session timeout in **Spring Boot properties**

```properties 
server.servlet.session.timeout=30m # Expires session after 30 minutes of inactivity 
```

- Configure session timeout in **Java-based config**:

```java
@Bean 
public ServletListenerRegisterationBean<HttpSessionEventPublisher> sessionListener() {
    return new ServletListenerRegisterationBean<>(new HttpSessionEventPublisher()); 
}

// this ensures Spring Security can detect session expiration events and remove stale sessions.
```

### Session Fixation Protection 
- Spring Security prevents **session fixation attacks** by creating a new session after authentication:
```java
http.sessionManagement(session -> sessionFixation().newSession()); 
```

There are two options for this 
- `.newSession()` creates a completely new session.
- `.migrationSession()` keeps existing attributes but assigns a new session ID. 

## Session Management in Cluster Environment 

### Challenges in Clustere Environments 
- **Session Stickness**: User should be routed to the same node. 
- **Session Replication**: Synchronizing session data across nodes. 
- **Session Persistence**: Using external storage like **Redis** or **Database**. 

### Solutions 

#### Load Balancer Sticky Sessions 
- The load balancer ensures user requests always reach the same server.
- **Pros**: Simple, no external storage needed. 
- **Cons**: If a node crashes, sessions are lost. 

#### In-Memory Session Replication (Hazelcast, Tomcat Clustering)

- Spring Session with Hazelcast example: 
  
```java 
@Bean 
public HazelcastInstance hazelcastInstance() {
    return Hazelcast.newHazelcastInstance(); 
}
```

- **Pros**: Fast, synchronized across nodes. 
- **Cons**: Increases memory usage and network overhead. 

#### External Session Storage (Spring Session + Redis/JDBC)
- Stores sessions in **Redis** or a **RMDB** for  persistence. 
- Spring Boot + Redis Session Management
```java
@EnableRedisHttpSession
public class RedisSessionConfig {
    @Bean 
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(); 
    }
}
```

- **Pros**: Persistent, scalable, works across clusters.
- **Cons**: Slight latency due to database/Redis lookups. 


## Best Practices 
- For scalability and high avaiability, **Spring Session with Redis** is the most recommended approach.