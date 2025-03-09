# Filter 

### Definition 
A servlet-based mechanism for request/response modification before it reaches the controller. 

### Lifecycle 
Executes before reaching Spring MVC.

### Use Cases 
Logging, security checks, request transformation, CORS handling. 

### Executes On 
`ServletRequest` and `ServletResponse`. 

### Implementation 
Implements `javax.servlet.Filter` or `jakarta.servlet.Filter`. 


## Filter - Works at the Servlet Level 

Filters execute **before Spring MVC** processes the request. 

- Example(Logging Filter)

```java
@Component 
public class LoggingFilter implements Filter {
    @Override 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
                throws IOException, ServletException {
        System.out.prinltn("Filter: Request received"); 
        chain.doFilter(request, response); 
        System.out.println("Filter: Response sent")
    }
}
```

- Use Cases :
  - Authenticaiton (Basic security checks)
  - Request modification (e.g., encoding)
  - CORS handling

## Key Takeaways 
- **Filters** operate at the servlet level (before Spring MVC).
- **Interceptors** work inside Spring MVC (before/after controller methods).
- **Filters** are for **low-level request processing**, while interceptors handle **business logic**.
- Use **interceptors** for logging, modifying requests/responses, and performance monitoring.  