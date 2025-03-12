# OAuth2 in Spring Security 6.x

## What's OAuth2?

- OAuth2 is a **token-based authentication** system.
- Allows secure login via **Google**, **GitHub**, **Facebook**, etc.
- Used for **API security** (access control).

## OAuth2 Login (Third Party Authentication)

- For logging in users via Google, GitHub, etc.
- Spring Boot Auto-Configuration

```yaml
spring:
  securit:
    oauth2:
      client:
        registrationo:
          google:
            client-id: your-client-id # this is ususally by the 3rd oauth2 provider/idp/identity provider
            client-scret: your-client-secret
            scope: profile, email # specify more fine-grain scope access controle that protected by this client-id/client-secret
```



- Spring Security Configuration 
```java
@Bean 
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.oauth2Login(oauth2 -> oauth2
            .defaultSuccessUrl("/home", true) // this gonna redirect to home path after login 
            ); 
    retur http.build(); 
}
```

- **When to use this OAuth2?**
  - Third-party login(Google, GitHub, etc.)
  - Web applications needing user authentication. 

## OAuth2 Resource Server (Protecting APIs with JWT)
- For securing APIs using OAuth2 tokens (JWTs)

```java
// Configuring a Resource Server 
@Bean 
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(oauth2 -> oauth2
         .jwt(jwt -> jwt
            .jwtAuthenticationConverter(new CustomJwtConverter()))); 

    return http.build(); 
}
```

```java 
// Applicaiton Configuration 
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://your-auth-server.com
```


- **When to use?**
  - Securing REST APIs
  - Protecting resources using JWT authentication 


--- 

## OAuth2 Authroization Strategies 
### Authroization Code (Most Secure)
- Use Case: User Login (Web Apps)
- Howt it works in Spring Security 6.x 
  - Redirects to OAuth provider -> User logs in 
  - Returns code -> Exchanges for token 

### Client Credentials 
- Use Case: Machine-to-Machine (M2M)
- How it works in Spring Security 6.x 
  - Client sends cient ID & client secert -> Gets access token in return. 

### Refresh Token 
- Use Case: Renewing Expired Tokens 
- How it works in Spring Security 6.x
  - Use refresh token to get a new access token. 

### Password Grant (Already Deprecated)

--- 

## Best Practices for OAuth2 in Spring Security 6.x 
- Use Authorization Code Flow instead of **Implicit Flow**
- Enable PKCE for SPAs & mobile apps.
- Validate JWT tokens in Resource Servers 
- Scope Tokens Properly (Minimize permissions)
- Use Refresh Tokens instead of long-lived access tokens. 