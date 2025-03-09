# Interceptor 

### Definition 
A Spring-specific mechanism that runs before and after controller execution. 

### Lifecycle 
Works within Spring MVC, before and after controller execution. 

### Use Cases 
Pre-processing, authentication, modifying response, performance monitoring. 

### Executes On 
`HandlerMethod` (Controller methods).

### Implementation 
Implements `HandlerInterceptor`. 


## Interceptor - Works in Spring MVC 
Interceptors execute **before and after controller methods** in Spring MVC. 

- Example (Logging Interceptor)

```java 
@Component 
public class LoggingInterceptor implements HandlerInterceptor {
    @Override 
    public boolean preHandle(HttpServletRquest request, HttpServletResponse response, Object handler) {
        System.out.println("Interceptor: Before Controller"); 
        return true; 
    }

    @Override 
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exp) {
        System.out.println("Interceptor: After Controller"); 
    }
}
```

- Use Cases: 
  - Authentication && authorization 
  - Logging request processing time 
  - Modifying the model before rendering a view. 

## Registering the Interceptor 
```java 
@Configuration 
public class WebConfig implements WebMvcConfigurer {
    @Autowired 
    private LoggingInterceptor loggingInterceptor; 

    @Override 
    public void addInterceptor(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor); 
    }
}
```