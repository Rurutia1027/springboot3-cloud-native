# Authorization in Spring Security 6.x 

**Spring Security** provides a comprehensive set of tools for authentication and authorization in modern Java applications. In **Spring Security 6.x**, there are several key features and improvements that make authorization management more flexible and powerful.

### Core Concepts 
- **Authorization** refers to the process of determining whether a user has the correct permissions to access a particular resource. 
- In **Spring Security**, authorization typically happens after authentication (which determines who the user is). 


### Key Components in Authorization 
#### Roles && Authorities 
- **Roles**: High-level categories that groups users, e.g., **ADMIN**, **USER**. 
- **Authorities**: Fine-grained permissionsthat define specific actions users can perform, e.g., **READ_PRIVILEGES**, **WRITE_PRIMITIVES**.

#### GrantedAuthority 
- Represents a granted authority or permission, typically granted to the authenticated user. 

#### AuthenticationManager 
- It is responsible for managing authentication and authorization decisions

### Configuring Authorization in Spring Security 6.x 
#### Basic Authorization Configuration 
- We can configure authorization rules using the `HttpSecurity API`, which allows you restrict access based on URLs, roles, or authorities.  
```java
@Configuraiton 
public class SecurityConfig {
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic() 
        .and()
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequest(auth -> auth 
           .requestMatchers("/book", "/log.html", "/auth", "/session.html")
           .requestMatchers("/book/**").authenticated()
           .anyRequest().authenticated()
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .formLogin(form -> form
            .loginPage("/login.html")
            .loginProcessingUrl("/auth")
            .usernameParameter("user")
            .passwordParameter("pass")).permitAll())
        .sessionManagement() 
        .invalidSessionUrl("/session.html")
        .maximumSessions(1)
        .maxSessionPreventsLogin(true); 
    
       return http.build(); 
    }
}
```

#### Method Security Annotations 
Spring Security 6.x enhances method-level security. We can annotate methods with `@PreAuthorize`, `@Secured`, or `@RolesAllowed` to specify access control. 

- `@Secured`: Restrict access based on roles 
```java 
@Secured("ROLE_ADMIN")
public void someMethod() {
    // ...
}
```

- `@PreAuthorize`: More flexible; can use SpEL (Spring Expression language)
```java 
@PreAuthorize("hasRole('ROLE_ADMIN') and hasPermissions(#user, 'EDIT')")
public void editUser(User user) {
    // ... 
}
```


- `@RolesAllowed`: Similar to `@Secured`, but from te **JSR-250** standard. 
```java 
@RolesAllowed("ADMIN")
public void adminOnlyMethod() {
    // Accessible only by users with the AMDIN role 
}
```

### Access Control Strategies 
- URL-based Authorization: We can restrict access to different URLs basedon the user's role and authorities.
  - Example: `antMatchers("/admin/**").hasRole("ADMIN")` restricts access to the /admin/** path to users with the ADMIN role. 

- Method-based Authroization: Authorization can also be configured at the method level using annotations. 
  - This is useful for more granular control over who can invoke particular service methods. 


### OAuth2 and OpenID Connect Authorization 

Spring Security 6.x proides extensive support for OAuth 2.0, OAuth 2.0 login, and OpenID Connect. These protocols are typically used to provide  **Single Sign-On(SSO)** and Federated Identity systems. 

#### OAuth2 Authorization Code Flow
- Spring Security supports authorization via an extenral OAuth2 provider, such as Google or GitHub. 
```java 
http.oauth2Login()
    .loginPage("/custom-login")
    .userInfoEndpoint() 
    .userAuthoritiesMapper(userAuthoritiesMapper()); 
```


#### JWT (JSON Web Tokens)
- Often used for stateless authentication in APIs
- JWT tokens can carry user roles and permissions to be validated on each request. 

```java
@Configuration 
public class SecurityConfig {
    @Override 
    pro
}
```


### Customizing Authorization with Filters 

We can implement custom authorization logic by using filters, either to modify the authorization behavior or check additional conditions before granting access. 

- Example of adding a custom authorizaiton filter: 
```java
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
                throws ServletException, IOException {
        String token = request.getHeader("Authorization"); 
        if (Objects.isNull(token) && token.startsWith("Bearer ")) {
            // Add custom authroization logic 
        }

        // continue with following filter operations 
        filterChain.doFilter(request, response); 
    }
}
```

### Spring Security's Default Authorization Rules 

Spring Secuirty 6.x comes with several built-in default authorizatiion rules: 
- permitAll(): Grants access to anyone. 
- authenticated(): Requires the user to be authenticated. 
- hasRole(): Grants access if the user has the specified role. 
- hasAuthority(): Grants access based on authority/permission. 
- denyAll(): Denies access to anyone. 


### Security Context and Session Management 
In Spring Security, authrization decisions rely on the **Security Context**, 
which holds the current user's authentication and authorizaiton details. 
We can manage session-based authorization via:

- **Session Management**: To handle session fixation and concurrency control.
```java 
http.sessionManagement()
    .maximumSessions(1)
    // if session expired go where 
    .expiredUrl('/login?expired'); 
```

### Access Control Lists (ACLs)
Spring Security 6.x suports **Access Control Lists(ACLs)** to manage fine-grained permissions for domain objects. 
ACLs allow for more detailed permissions management, such as defining who can read, write, or delete specific objects. 

```java
// Configure ACL
@PreAuthorized("hasPermission(#book, 'READ')")
public void viewBook(Book book) {
    // Access logic 
}
```

### Custom Authorization Providers 
If built-in authorization strategies aren't sufficient, Spring Securiy allows us to implement our own authorization providers using the `AuthorizationManager`.
```java 
public class CustomAuthorizationManager implements AuthorizationManager<AuthroziationContext> {
    @Override 
    public AuthorizationDecision check(Supplier<Authentication> authentication, AuthorizationContext context) {
        // custom authorization logic 
        return new AuthorizationDecision(true);
    }
}
```
