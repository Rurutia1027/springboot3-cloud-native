# Spring Security: CSRF && Session Fixation 

## CSRF (Coros-Site Request Forgery)
- **Default Behavior**: CSRF is enabled by default for **form-based authentication**.
- For APIs(Stateless Applications): Disable CSRF if using JWT or OAuth2

```java
http.csrf(csrf -> csrf.disable()); 
```

- For Form-Based Apps (Recommended Approach via Spring Security 6.0)
```java
http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); 
```
- Use **CSRF tokens** stored in cookies. 
- Ensures protection for POST, PUT, DELETE, etc. 

## Session Fixation 
- **Default Behavior**: Spring Security migrates the session after authentication.
- Set Custom Strategy: 
```java 
http.sessionManagement(session -> sesion
    .sessionFixation(SessionFixationConfigurer::migrateSession)) // default
```
#### Different session fixation strategies:
- `migrateSession()` -> Creates a new session, copies attributes. (Secure & default)
- `newSession()` -> Creates a completetly fresh session (better for high-security apps).
- `none()` -> Keeps the same session (not recommended).

## Best Practices with Spring Security 6.x
- For Stateful Apps(Sessions): Use CSRF tokens && `migrateSession()`
- For APIs (JWT/Auth Headers): Disable CSRF & avoid session usage. 
- Limit Active Sessions:

```java
http.sessionManagement(session -> session
     .maximumSessions(1)
     .maxSessionsPreventsLogin(true) // prevent multiple logins
); 
```