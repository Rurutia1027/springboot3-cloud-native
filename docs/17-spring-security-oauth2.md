# Spring Boot Starter OAuth2 Introduce 

In Spring Boot 3.0, multiple starters support the OAuth2 protocol, each designed for specific use cases. This guide explores diffrent OAuth2 starters, their focus areas, and how they can be combined in various scenarios to implement authentication and authorization effectively.

## `spring-boot-starter-oauth2-client` (For OAuth2 Login)
### Use Case
- Our project acts as a **client** and uses a **third-party OAuth provider** (e.g., Google, Okta, etc.) for authenticaiton. 
- Users log in via external OAuth providers, and our pp retrives their profile (e.g., email, name)

```xml 
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```


### Example
If we integrate Google OAuth login in our Spring Boot app, we gonna add this dependency.

```java
http.oauth2Login(withDefaults()); // this enables OAuth2 login 
```


## `spring-boot-starter-oauth2-resource-server` (For securing APIs with Tokens)
### Use Case
- Our project acts as a **resource server** and protects **endpoints** using OAuth2 tokens (JWT or opaque tokens). 
- Another system(like OAuth2 provider) issues tokens, and our API **validates them** before allowing access. 
- Typically used when implementing **OAuth2-based API security**.

- **If Using JWT for API Security**
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jose</artifactId>
</dependency>
```

### Example 
If our API expects a client to send a **Bearer token**, and we want to validate it before granting access, we use this dependency. 

```java
http.oauth2ResourceServer(oauth2 -> oauth2.jwt()); // APi expectes JWT tokens
```


## What if We Want to Be an OAuth Provider?

### Use Case
- If our projct itself wants to become an OAuth2 Authorization Server (i.e., issuing tokens to other apps), then we gonna need: 

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>
```


### Example
Our prjoect acts like **Auth0** or **Google OAuth**, issuing tokens to client applications. 
