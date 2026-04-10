## 🔹 Basics (Entry–Mid Level)

-   What is Spring Boot and how is it different from Spring Framework?
-   What are the main advantages of Spring Boot?
-   What is **auto-configuration** in Spring Boot?
-   What is **Spring Boot Starter**? Give examples.
-   What is `application.properties` / `application.yml` used for?
-   What is embedded server support in Spring Boot?
-   What is the default embedded server in Spring Boot?
-   What is `@SpringBootApplication` annotation composed of?
-   What is component scanning?

----------

## 🔹 Core Concepts

-   Explain **auto-configuration mechanism** internally.
-   What is the difference between:
    -   `@Component`, `@Service`, `@Repository`, `@Controller`
-   What is `@Configuration` and `@Bean`?
-   What is **dependency injection** in Spring Boot?
-   What is the difference between:
    -   Constructor vs Setter injection?
-   What is **Spring Boot Actuator**?
-   What is **Spring Boot DevTools**?

----------

## 🔹 Web & REST

-   How do you create a REST API in Spring Boot?
-   Difference between `@RestController` and `@Controller`
-   What is `@RequestMapping` vs `@GetMapping` / `@PostMapping`?
-   How does Spring Boot handle JSON serialization?
-   What is **Jackson** in Spring Boot?
-   How do you handle exceptions globally?
-   What is `@ControllerAdvice`?

----------

## 🔹 Database & JPA

-   What is Spring Data JPA?
-   Difference between:
    -   `CrudRepository` vs `JpaRepository`
-   What is **Hibernate** and how does Spring Boot use it?
-   What is entity lifecycle?
-   What is lazy vs eager fetching?
-   What is `@Transactional`?
-   How does Spring Boot configure datasource automatically?

----------

## 🔹 Security

-   What is Spring Security?
-   How does authentication work internally?
-   What is the difference between:
    -   Authentication vs Authorization
-   What are filters in Spring Security?
-   What is JWT-based authentication?
-   How do you secure REST APIs?

----------

## 🔹 Advanced / Internal

-   Explain Spring Boot startup flow.
-   What happens inside `SpringApplication.run()`?
-   How does auto-configuration work internally? (`@EnableAutoConfiguration`)
-   What are conditional annotations? (`@ConditionalOn...`)
-   What is **Bean lifecycle**?
-   What is **ApplicationContext vs BeanFactory**?

----------

## 🔹 Microservices & Production

-   How does Spring Boot support microservices?
-   What is externalized configuration?
-   What is Spring Cloud?
-   How do you implement logging in Spring Boot?
-   How do you monitor application health?
-   How do you handle profiles (`dev`, `prod`)?

----------

## 🔹 Practical / Scenario-Based

-   How would you design a scalable REST API using Spring Boot?
-   How do you handle high traffic in Spring Boot?
-   How do you optimize database calls?
-   How do you debug memory leaks in Spring Boot?
-   How would you implement caching?

----------

## 🔹 Rapid-Fire (Very Important)

-   Default port of Spring Boot? → `8080`
-   How to change port?
-   What is `@Value` vs `@ConfigurationProperties`?
-   What is `CommandLineRunner`?
-   What is `SpringBootServletInitializer`?