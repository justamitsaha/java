
### 🔹 1. High Traffic API Slowing Down

**Q:** Your Spring Boot API becomes slow under high load. What do you check?

**Answer:**

-   DB bottleneck → slow queries, missing indexes
-   Thread pool exhaustion (Tomcat threads)
-   Excessive synchronous calls (blocking I/O)
-   GC pauses / memory pressure
-   N+1 queries (common with Spring Data JPA)

**Fix:**

-   Add caching (Redis)
-   Use pagination
-   Optimize queries (`JOIN FETCH`, projections)
-   Tune connection pool (HikariCP)
-   Consider async processing

----------

### 🔹 2. N+1 Query Problem

**Q:** You notice multiple queries firing for a single request. Why?

**Answer:**

-   Due to **lazy loading** in Hibernate
-   Example: fetching Orders → triggers query per Order for items

**Fix:**

-   Use `JOIN FETCH`
-   Use DTO projection
-   Change fetch strategy carefully

----------

### 🔹 3. Handling Transactions Across Services

**Q:** You have multiple DB operations; one fails → how to rollback?

**Answer:**

-   Use `@Transactional`
-   Ensure:
    -   Same transaction context
    -   No internal method call (proxy issue)

**Advanced:**

-   For distributed systems → use **Saga pattern** (not DB transaction)

----------

### 🔹 4. API Security with JWT

**Q:** How do you secure APIs?

**Answer:**

-   Use Spring Security with JWT:
    -   Authenticate → generate token
    -   Add filter → validate token per request
    -   Store roles/claims inside JWT

**Key Concern:**

-   Token expiry + refresh strategy

----------

### 🔹 5. External API Dependency Slow

**Q:** Your service depends on a slow third-party API.

**Answer:**

-   Use:
    -   Timeout configuration
    -   Circuit breaker (Resilience4j)
    -   Retry mechanism
    -   Fallback response

**Pattern:** Fault tolerance

----------

### 🔹 6. Memory Leak / High Heap Usage

**Q:** App memory keeps increasing.

**Answer:**

-   Check:
    -   Unclosed streams
    -   Large caches
    -   Static collections
-   Tools:
    -   Heap dump + MAT
    -   VisualVM

----------

### 🔹 7. Configuration per Environment

**Q:** Different configs for dev, QA, prod?

**Answer:**

-   Use:
    -   `application-dev.yml`, `application-prod.yml`
    -   `@Profile`
-   External config (env variables, config server)

----------

### 🔹 8. Slow Startup Time

**Q:** Spring Boot app takes too long to start.

**Answer:**

-   Causes:
    -   Too many beans
    -   Heavy auto-config
-   Fix:
    -   Lazy initialization
    -   Exclude unused auto-config
    -   Use `spring.main.lazy-initialization=true`

----------

### 🔹 9. Handling Large File Upload

**Q:** Uploading large files crashes server.

**Answer:**

-   Use streaming (avoid loading in memory)
-   Configure:
    -   `spring.servlet.multipart.max-file-size`
-   Store directly to disk/cloud

----------

### 🔹 10. Versioning REST APIs

**Q:** How do you version APIs?

**Answer:**

-   URI versioning → `/v1/api`, `/v2/api`
-   Header-based versioning
-   Maintain backward compatibility

----------

### 🔹 11. Exception Handling Strategy

**Q:** How do you handle exceptions globally?

**Answer:**

-   Use:
    -   `@ControllerAdvice`
    -   `@ExceptionHandler`
-   Return consistent response structure

----------

### 🔹 12. Logging Strategy

**Q:** What logging approach do you use?

**Answer:**

-   Use SLF4J + Logback
-   Log levels:
    -   INFO → business events
    -   DEBUG → dev troubleshooting
    -   ERROR → failures
-   Add correlation ID (important in microservices)

----------

### 🔹 13. Database Connection Exhaustion

**Q:** DB connections getting exhausted?

**Answer:**

-   Tune HikariCP:
    -   max pool size
-   Ensure:
    -   Connections are closed
-   Monitor slow queries

----------

### 🔹 14. Caching Strategy

**Q:** Improve performance for frequently read data.

**Answer:**

-   Use:
    -   `@Cacheable`, `@CacheEvict`
-   Backend:
    -   Redis / in-memory
-   Handle cache invalidation carefully

----------

### 🔹 15. Deploying in Production

**Q:** What changes from local → prod?

**Answer:**

-   Disable debug logs
-   Use external config
-   Enable monitoring (Actuator)
-   Secure endpoints
-   Use containerization (Docker + K8s)

----------

### 🔹 16. Concurrent Request Handling

**Q:** How does Spring Boot handle concurrency?

**Answer:**

-   Uses thread-per-request model (Tomcat)
-   Tune:
    -   `server.tomcat.max-threads`
-   Avoid blocking calls

----------

### 🔹 17. Data Consistency Issue

**Q:** Two users update same record → conflict.

**Answer:**

-   Use:
    -   Optimistic locking (`@Version`)
    -   Pessimistic locking (if needed)

----------

### 🔹 18. Circular Dependency Issue

**Q:** App fails with circular dependency.

**Answer:**

-   Causes:
    -   Constructor injection loop
-   Fix:
    -   Refactor design
    -   Use `@Lazy` (temporary)

----------

### 🔹 19. Health Monitoring

**Q:** How to monitor service health?

**Answer:**

-   Use Spring Boot Actuator:
    -   `/actuator/health`
    -   `/actuator/metrics`

----------

### 🔹 20. Scaling Strategy

**Q:** How do you scale Spring Boot app?

**Answer:**

-   Horizontal scaling (multiple instances)
-   Stateless design
-   Use load balancer
-   Externalize session (Redis)