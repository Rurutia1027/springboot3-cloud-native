# Spring Security 

## Fixing Spring Security Issues in Unit Tests 

When adding **Spring Security**, unit tests may fail with errors like:
- `401` Unauthorized: Request is unauthenticated. 
- `403` Forbidden: User lacks required permissions or CSRF protection is blocking the request. 


### Solution-1: Use `@WithMockUser` (Recommended)

- Here we mock an authenticated user by adding `@WithMockUser` to test methods.
```java
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired 
    private MockMvc mockMvc; 

    @Test 
    @WithMockUser(username = "user", roles = {USER})
    void whenBookQuerySuccess() throws Exception {
        mockMvc.perform(get("/books"))
          .andExpect(status().isOk()); 
    }
}
```

- Why this work? Because `@WithMockUser` tells Spring Security to assume an authenticated user is making the request.


### Solution-2: Globally Disable Security in Tests (For Testing Only)

- Here we declare and create a config to disable Spring Security during test using a `@TestConfiguration` class. 
```java 
@TestConfiguration 
public class TestSecurityConfig {
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable) // Disable CSRF protection 
           .authorizeHttpRequests(auth -> auth.anyRequet().permitAll()); 
           return http.build(); 
    }
}
```


- Then, include this config in test classes
```java
@SpringBootTest 
@Import(TestSecurityConfig.class)
public class BookController {
    // ... 
}
```


- Why this work? This allows all requests without authentication, useful for integration tests. 

--- 

### Solution-3: Manually Provide Authentication in Requests 
Instead of using `@WithMockUser`, add authentication in **MockMvc** requests. 


```java 
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user; 

mockMvc.perform(get("/books"))
        .with(user("testUser").roles("USER"))
        .andExpect(status().isOk()); 
```

- If the API requires a **JWT token**:
```java
mockMvc.perform(get("/books")
    .header("Authorization", "Bearer fake-jwt-token")
    .andExpect(status().isOk()); 
)
```


--- 
### Solution-4: Fix 403 Forbidden Issues 
If **403 Forbidden** occurs, the likely causes are

#### Case-1 the user lacks the required role/permission 
- Fix: ensure the user has the correct roles: 
```java 
mockMvc.perform(post("/books")
 .with(user("admin").roles("ADMIN")))
 .andExepct(status().isOk()); 
```

#### CSRF protection is blocking the request 
- Fix: Disable CSRF in test config 
```java 
http.csrf(csrf -> csrf.disable()); 
```

- Alternative: include CSRF toen in the request 
```java 
mockMvc.perform(post("/books")
.withuser(user("admin").roles("ADMIN"))
.with(csrf())) // Add CSRF token 
.andExpect(status().isOk(); 
```

