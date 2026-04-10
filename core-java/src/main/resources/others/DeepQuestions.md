### 🔹 1. High Traffic API

-   How do you identify whether bottleneck is DB, CPU, or threads?
-   How do you tune Tomcat thread pool vs DB connection pool?
-   What happens if thread pool > DB pool?
-   How would you load test your API?
-   When would you switch to reactive (WebFlux)?

----------

### 🔹 2. N+1 Query Problem (Spring Data JPA)

-   Why does Hibernate create N+1 queries internally?
-   Difference between `JOIN FETCH` vs `EntityGraph`?
-   When would you prefer DTO projection over entity fetch?
-   What are risks of switching to EAGER loading?
-   How do you detect N+1 in production?

----------

### 🔹 3. Transactions

-   Why does `@Transactional` fail in self-invocation?
-   Difference between:
    -   `REQUIRED` vs `REQUIRES_NEW`
-   What happens on checked vs unchecked exceptions?
-   How does Spring manage transaction proxies internally?
-   How would you handle partial failure in microservices?

----------

### 🔹 4. JWT Security (Spring Security)

-   Where is JWT stored on client side? Risks?
-   How do you invalidate a JWT?
-   Stateless vs stateful authentication trade-offs?
-   What happens if token is stolen?
-   How do filters work internally in Spring Security?

----------

### 🔹 5. External API Resilience

-   How does circuit breaker decide to open?
-   Difference between retry vs circuit breaker?
-   What is bulkhead pattern?
-   How do you prevent cascading failure?
-   What metrics would you monitor?

----------

### 🔹 6. Memory Issues

-   How do you analyze heap dump?
-   What is difference between memory leak vs high usage?
-   What are common causes in Spring Boot apps?
-   How does GC affect latency?
-   Which GC would you choose and why?

----------

### 🔹 7. Configuration Management

-   Order of property resolution in Spring Boot?
-   Difference between:
    -   `@Value` vs `@ConfigurationProperties`
-   How do you refresh config at runtime?
-   How do you manage secrets securely?

----------

### 🔹 8. Startup Optimization

-   What happens inside `SpringApplication.run()`?
-   How does auto-configuration work internally?
-   How do conditional annotations work?
-   What is bean initialization lifecycle?
-   Trade-off of lazy initialization?

----------

### 🔹 9. File Upload Handling

-   How does multipart handling work internally?
-   What happens if file exceeds memory threshold?
-   How do you stream directly to S3?
-   How do you secure file upload endpoints?

----------

### 🔹 10. API Versioning

-   URI vs header versioning trade-offs?
-   How do you deprecate APIs safely?
-   How do you maintain backward compatibility in DB changes?
-   How do you version microservices?

----------

### 🔹 11. Exception Handling

-   Difference between:
    -   `@ControllerAdvice` vs `@RestControllerAdvice`
-   How does Spring resolve exceptions internally?
-   How do you standardize error response across services?
-   Where do you log exceptions?

----------

### 🔹 12. Logging

-   How do you trace a request across microservices?
-   What is MDC (Mapped Diagnostic Context)?
-   How do you avoid logging sensitive data?
-   How do you manage log volume in production?

----------

### 🔹 13. DB Connection Pool

-   How does HikariCP work internally?
-   What happens when pool is exhausted?
-   How do you calculate optimal pool size?
-   What metrics indicate pool issues?

----------

### 🔹 14. Caching

-   Cache aside vs write-through vs write-behind?
-   How do you handle cache eviction?
-   What is cache stampede?
-   How do you maintain cache consistency?

----------

### 🔹 15. Production Deployment

-   What configs must never go to Git?
-   How do you do zero-downtime deployment?
-   Blue-green vs rolling deployment?
-   How do you rollback safely?

----------

### 🔹 16. Concurrency

-   How does Spring Boot handle multi-threading?
-   What is thread safety in singleton beans?
-   How do you handle shared mutable state?
-   Difference between synchronized vs lock?

----------

### 🔹 17. Data Consistency

-   Optimistic vs pessimistic locking trade-offs?
-   How does `@Version` work internally?
-   What is isolation level? Which one do you use?
-   How do you handle race conditions?

----------

### 🔹 18. Circular Dependency

-   Why constructor injection fails but setter works?
-   How does Spring resolve circular dependencies internally?
-   Why is it considered bad design?

----------

### 🔹 19. Monitoring

-   What metrics do you monitor in production?
-   Difference between:
    -   metrics vs logs vs traces
-   How does Spring Boot Actuator expose metrics?
-   How would you integrate Prometheus + Grafana?

----------

### 🔹 20. Scaling

-   Stateless vs stateful services?
-   How do you handle session in distributed systems?
-   How does load balancer distribute traffic?
-   What is sticky session and why avoid it?