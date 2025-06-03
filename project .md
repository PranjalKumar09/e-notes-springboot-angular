**E Notes Project (Real-Time Project)**

---

## 1. Technology Stack

* **Backend:** Spring Boot (REST, JPA, AOP, Logging, Actuator, Security, OAuth Login, Caching, JUnit & Mockito, SonarQube, JMeter, Swagger, Scheduler, Docker, GitHub)
* **Frontend:** Angular or React
* **Database:** MySQL
* **Tools:** GitHub, Spring Tool Suite (STS), Figma
* **Deployment:** AWS

---

## 2. Database Schema & Entities

### 2.1. Tables Created

* **User** (with auditing fields, soft-delete flag, and account status relation)
* **Role** (maps to users via user\_role join table)
* **Category**
* **File** (stores metadata: random name, original name, display name; uses Apache Commons for utilities)
* **Notes** (fields: id, title, description, Category, createdBy, createdOn, updatedBy, updatedOn, isDeleted, deletedAt, isFavorite)
* **Todo** (fields: id, title, description, dueDate, status, user, reminder settings)
* **User\_Role** (mapping table)

> **Tip:** Use `@MappedSuperclass` for common auditing attributes (e.g., `BaseModel`) and extend entities from it.

---

## 3. Project Structure & Git Workflow

1. **Repository Setup**

   * Create repository on GitHub.
   * Define branches:

     * `dev` (developers)
     * `test` (QA testers)
     * `uat` (client/UAT)
     * `prod` (production)
   * Developers create feature-specific sub-branches under `dev` (e.g., `nav-navbar`), then merge into `dev`.
   * After `dev` is stable, merge to `test` for QA, then to `uat` for client review, then to `prod` when ready.

2. **Package Standards**

   * `com.projectname`: Base package

     * `config`: Configuration classes (e.g., ModelMapper bean, SwaggerConfig)
     * `controller`: REST controllers implementing endpoint interfaces
     * `dto`: Data Transfer Objects (input/output)
     * `entity`: JPA entity classes
     * `exception`: Custom exceptions and `@ControllerAdvice` handlers
     * `repository`: Spring Data JPA repositories
     * `service`: Service interfaces and implementations
     * `util`: Utility classes (e.g., CommonUtil, Validation)
     * `scheduler`: Scheduled tasks
     * `security`: Spring Security configurations, filters, JWT utilities
     * `payload`: Response wrappers (e.g., `ApiResponseDto`, `GenericResponse`)
   * Use `@Slf4j` (Lombok) for logging in services and controllers.

---

## 4. DTOs & Model Mapping

1. **ModelMapper Configuration**

   * Create a `@Configuration` class to provide a `ModelMapper` bean for entity-to-DTO conversion.

2. **DTO Design Principles**

   * Keep fields minimal: only necessary data for API request/response.
   * Use nested static classes for related DTOs (e.g., `NotesDto.CategoryDto`).
   * Include validation annotations (`@NotBlank`, `@Size`, `@Min`, `@Max`, `@NotNull`) on DTO fields.
   * Example:

     ```java
     public class CategoryDto {
         private Integer id;

         @NotBlank
         @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
         private String name;

         @NotBlank
         @Size(min = 10, max = 250)
         private String description;

         @NotNull
         private Boolean isActive;
     }
     ```

3. **Response Wrappers**

   * Use a generic `ApiResponseDto<T>` with fields: `timestamp`, `status`, `message`, `data`.
   * Build responses via a `CommonUtil.createBuildResponse(...)` or `CommonUtil.createErrorResponse(...)`.

---

## 5. Validation Strategy

1. **Method-Level Validation**

   * Annotate DTO parameters in controllers with `@Valid`.
   * Example:

     ```java
     @PostMapping("/save")
     public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto) { ... }
     ```

2. **Centralized Validation Logic**

   * Instead of placing all validation in controllers, create a `Validation` component under `util`.
   * Perform business-specific checks in service layer (e.g., `validation.categoryValidation(categoryDto)`).

3. **Exception Handling**

   * Create custom exceptions (e.g., `DtoValidationException extends RuntimeException`).
   * Use `@ControllerAdvice` to map exceptions to HTTP responses.

---

## 6. Soft Delete & Auditing

1. **Soft Delete**

   * Add `isDeleted` (Boolean) and `deletedAt` (LocalDateTime) fields to entities (e.g., `Notes`).
   * Instead of physical delete, set `isDeleted = true`, record `deletedAt = now()`.
   * Provide a restore endpoint to set `isDeleted = false` and clear `deletedAt`.

2. **Auditing**

   * Use Spring Data JPA’s `@EntityListeners(AuditingEntityListener.class)` on entities.
   * Add common fields in `BaseModel`: `createdBy`, `createdOn`, `updatedBy`, `updatedOn`.
   * Enable JPA auditing in configuration (`@EnableJpaAuditing`) and inject `AuditAwareConfig` to set current user.

---

## 7. Scheduled Tasks

1. **Notes Cleanup Scheduler**

   * Example: Delete notes permanently after 7 days from soft deletion.
   * Use `@Scheduled(cron = "0 0 0 * * ?")` to run daily at midnight.
   * In `NotesScheduler`:

     ```java
     @Scheduled(cron = "0 0 0 * * ?")
     public void deleteNotesScheduler() {
         LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
         List<Notes> toDelete = notesRepository.findByIsDeletedAndDeletedAtBefore(true, cutoff);
         notesRepository.deleteAll(toDelete);
     }
     ```

2. **Reminder Emails for Todo**

   * Schedule reminders based on `dueDate`/`reminderTime` in `Todo` entity.
   * Use `JavaMailSender` to send emails; set `mimeMessageHelper.setText(message, true)` for HTML content.

---

## 8. File Handling

1. **File Metadata Entity**

   * Fields: `id`, `randomName`, `originalName`, `displayName`, `uploadPath`, `size`, `contentType`.
   * Use Apache Commons IO/FFmpeg if needed for additional file utilities.

2. **File Upload & Download**

   * Upload:

     ```java
     @PostMapping("/upload")
     public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) { ... }
     ```
   * Download:

     ```java
     @GetMapping("/download/{fileId}")
     public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
         // Load file, set headers, return: ResponseEntity.ok().headers(headers).body(resource);
     }
     ```
   * Use `Files.copy(...)` or `StreamingResponseBody` for large files.

3. **Filename Truncation**

   * When `originalName` is too long (e.g., `java_programming_really_long_title.pdf`), create a truncated `displayName` (e.g., `java.prog.pdf`).

---

## 9. Notes Module

### 9.1. Entity & DTO

* **Entity:** `Notes` extends `BaseModel`, includes fields from Section 2.
* **DTO:** `NotesDto` with nested `CategoryDto`.

### 9.2. Controller & Endpoint

* **Endpoint Interface:**

  ```java
  @Tag(name = "Notes Management", description = "Operations to manage notes and file attachments")
  @RequestMapping("/api/v1/notes")
  public interface NotesEndpoint {

      @Operation(summary = "Save a new note", description = "Requires USER role.")
      @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Note saved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
      @PostMapping("/")
      @PreAuthorize("hasRole('USER')")
      ResponseEntity<?> saveNotes(
          @RequestParam String notes,
          @RequestParam(required = false) MultipartFile file
      ) throws Exception;

      @Operation(summary = "Get all notes for user", description = "Retrieves all non-deleted notes for the current user.")
      @GetMapping("/")
      @PreAuthorize("hasRole('USER')")
      ResponseEntity<?> getUserNotes();

      @Operation(summary = "Soft delete a note", description = "Sets isDeleted=true for the note.")
      @DeleteMapping("/{id}")
      @PreAuthorize("hasRole('USER')")
      ResponseEntity<?> deleteNote(@PathVariable Integer id);

      @Operation(summary = "Restore a deleted note", description = "Sets isDeleted=false for the note.")
      @PutMapping("/restore/{id}")
      @PreAuthorize("hasRole('USER')")
      ResponseEntity<?> restoreNote(@PathVariable Integer id);

      @Operation(summary = "Favorite a note", description = "Marks a note as favorite.")
      @PatchMapping("/favorite/{id}")
      @PreAuthorize("hasRole('USER')")
      ResponseEntity<?> favoriteNote(@PathVariable Integer id);

      @Operation(summary = "Search notes", description = "Search by title, description, or category.")
      @GetMapping("/search")
      @PreAuthorize("hasRole('USER')")
      ResponseEntity<?> searchNotes(
          @RequestParam(required = false) String title,
          @RequestParam(required = false) String description,
          @RequestParam(required = false) Integer categoryId
      );
  }
  ```

### 9.3. Service & Repository

* **Repository:** `NotesRepository extends JpaRepository<Notes, Integer>` with custom methods:

  ```java
  List<Notes> findByIsDeletedAndDeletedAtBefore(boolean isDeleted, LocalDateTime cutoffDate);
  Page<Notes> findByUserAndIsDeletedFalse(User user, Pageable pageable);
  List<Notes> findByUserAndIsDeletedFalseAndTitleContainingOrDescriptionContainingOrCategoryId(
        User user, String title, String description, Integer categoryId
  );
  ```
* **Service:** `NotesService` interface and `NotesServiceImpl` with validation (`validation.notesValidation(...)`), mapping, and business logic.

---

## 10. Todo Module

### 10.1. Entity & DTO

* **Entity:** `Todo` extends `BaseModel` with fields: `title`, `description`, `dueDate`, `status` (enum), `user`, `reminderTime`.
* **DTO:** `TodoDto` with nested static `StatusDto` containing enum values for status codes and names.

### 10.2. Endpoint Interface & Controller

```java
@Tag(name = "Todo Management", description = "Operations for managing todo tasks and reminders")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @Operation(summary = "Create a new todo task", description = "Requires USER role.")
    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> createTodo(@Valid @RequestBody TodoDto todoDto);

    @Operation(summary = "Get all todos for user", description = "Retrieves all active todos for current user.")
    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserTodos();

    @Operation(summary = "Update a todo", description = "Updates details or status of a todo.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> updateTodo(@PathVariable Integer id, @Valid @RequestBody TodoDto todoDto);

    @Operation(summary = "Delete a todo", description = "Soft deletes a todo.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteTodo(@PathVariable Integer id);
}
```

### 10.3. Reminder & Email

* Schedule a job (e.g., `@Scheduled(cron = "0 0 * * * ?")`) to check for todos with `reminderTime <= now()` and send reminder emails via `JavaMailSender`.
* Handle HTML email content with `mimeMessageHelper.setText(message, true)`.

---

## 11. User & Authentication Module

### 11.1. Entity & DTO

* **Entity:** `User` with fields: `id`, `username`, `email`, `password`, `isActive`, `accountStatus` (one-to-one), `roles` (one-to-many, `fetch = FetchType.EAGER`), `verificationCode`, `resetToken`, auditing fields.
* **DTO:** `UserDto`, `UserResponse` (exclude password), `PasswordChangeRequest`, `PasswordResetRequest`.

### 11.2. Endpoint Interfaces

**AuthEndpoint:**

```java
@Tag(name = "User Authentication", description = "APIs for user signup, login, and password reset")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {
    @Operation(summary = "Register a new user", description = "Creates a new user account.")
    @PostMapping("/register")
    ResponseEntity<?> register(@Valid @RequestBody UserDto userDto);

    @Operation(summary = "Login user", description = "Authenticates user and returns JWT.")
    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest);

    @Operation(summary = "Send password reset email", description = "Sends a reset link to user email.")
    @GetMapping("/send-email-reset")
    ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email);

    @Operation(summary = "Verify password reset link", description = "Validates reset token.")
    @GetMapping("/verify-reset-link")
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam String token);

    @Operation(summary = "Reset password", description = "Resets user password using valid token.")
    @PostMapping("/reset-password")
    ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetRequest resetRequest);
}
```

**UserEndpoint:**

```java
@Tag(name = "User Management", description = "Operations related to user profile and password management")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {
    @Operation(summary = "Get user profile", description = "Retrieves profile details of authenticated user.")
    @GetMapping("/profile")
    ResponseEntity<?> getProfile();

    @Operation(summary = "Change user password", description = "Changes password for authenticated user.")
    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeRequest request);
}
```

### 11.3. Security Configuration

1. **Dependencies**

   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
     <groupId>io.jsonwebtoken</groupId>
     <artifactId>jjwt-api</artifactId>
     <version>0.11.5</version>
   </dependency>
   <dependency>
     <groupId>io.jsonwebtoken</groupId>
     <artifactId>jjwt-impl</artifactId>
     <scope>runtime</scope>
   </dependency>
   <dependency>
     <groupId>io.jsonwebtoken</groupId>
     <artifactId>jjwt-jackson</artifactId>
     <scope>runtime</scope>
   </dependency>
   ```

2. **JWT Filter** (`JwtFilter extends OncePerRequestFilter`)

   * Extracts `Authorization` header, validates token via `JwtService`, sets `UsernamePasswordAuthenticationToken` in `SecurityContextHolder`.
   * On exception, writes a JSON error response:

     ```java
     private void generateResponseError(HttpServletResponse response, Exception e) throws IOException {
         response.setContentType("application/json");
         response.setStatus(HttpStatus.UNAUTHORIZED.value());
         GenericResponse body = new GenericResponse("failed", e.getMessage(), HttpStatus.UNAUTHORIZED);
         response.getWriter().write(new ObjectMapper().writeValueAsString(body));
     }
     ```

3. **SecurityConfig**

   ```java
   @Configuration
   @EnableWebSecurity
   @EnableMethodSecurity
   public class SecurityConfig {

       @Autowired
       private UserDetailsService userDetailsService;
       @Autowired
       private JwtFilter jwtFilter;

       @Bean
       public BCryptPasswordEncoder passwordEncoder() {
           return new BCryptPasswordEncoder();
       }

       @Bean
       public DaoAuthenticationProvider authenticationProvider() {
           DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
           provider.setUserDetailsService(userDetailsService);
           provider.setPasswordEncoder(passwordEncoder());
           return provider;
       }

       @Bean
       public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
           return config.getAuthenticationManager();
       }

       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(auth -> auth
                   .requestMatchers("/api/v1/auth/**", "/api/v1/home/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                   .anyRequest().authenticated()
               )
               .httpBasic(Customizer.withDefaults())
               .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

           return http.build();
       }
   }
   ```

4. **UserDetailsService & CustomUserDetails**

   * Load user by username/email, include `roles` as `GrantedAuthority` (`ROLE_...`).
   * Example `getLoggedInUser()` method:

     ```java
     public static User getLoggedInUser() {
         CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         return details.getUser();
     }
     ```

---

## 12. Logging Configuration (Logback)

Place `logback-spring.xml` or `logback.xml` in `src/main/resources`:

```xml
<configuration>

    <!-- Console Appender -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- File Appender with Rolling Policy -->
    <appender name="fileAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/logging.log</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>logs/logging-%d{yy-MM-dd_HH-mm}.%i.log</fileNamePattern>
            <maxFileSize>100MB</maxFileSize>
            <maxHistory>30</maxHistory>       <!-- Keep logs for 30 days -->
            <totalSizeCap>3GB</totalSizeCap>  <!-- Limit total log size -->
        </rollingPolicy>
    </appender>

    <!-- Root Logger -->
    <root level="info">
        <appender-ref ref="console" />
        <appender-ref ref="fileAppender" />
    </root>

</configuration>
```

> **Key Points:**
>
> * **Rolling by Size & Time**: `maxFileSize = 100MB`, `maxHistory = 30` (days), `totalSizeCap = 3GB` — old logs auto-deleted.
> * Logs printed to console and persisted in `logs/` directory.

---

## 13. Swagger / OpenAPI Configuration

1. **Dependency**

   ```xml
   <dependency>
       <groupId>org.springdoc</groupId>
       <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
       <version>2.8.5</version>
   </dependency>
   ```

2. **Properties (application.yml)**

   ```yaml
   springdoc:
     api-docs:
       path: /api-docs
     swagger-ui:
       path: /swagger-ui.html
       display-request-duration: true
       try-it-out-enabled: true
       operationsSorter: method
   ```

3. **SwaggerConfig**

   ```java
   @Configuration
   public class SwaggerConfig {

       @Bean
       public OpenAPI customOpenAPI() {
           OpenAPI openAPI = new OpenAPI()
               .info(new Info()
                   .title("E Notes API")
                   .version("1.0")
                   .description("API documentation for the E Notes application")
                   .termsOfService("http://enotes.com/terms")
                   .contact(new Contact()
                       .name("Support Team")
                       .email("support@enotes.com")
                       .url("http://enotes.com/support"))
                   .license(new License()
                       .name("E Notes License 1.0")
                       .url("https://github.com/enotes")
                   )
               );

           List<Server> servers = List.of(
               new Server().description("Development Server").url("http://localhost:8080"),
               new Server().description("Testing Server").url("http://localhost:8081"),
               new Server().description("Production Server").url("https://api.enotes.com")
           );
           openAPI.servers(servers);

           if (openAPI.getComponents() == null) {
               openAPI.setComponents(new Components());
           }

           SecurityScheme jwtScheme = new SecurityScheme()
               .type(SecurityScheme.Type.HTTP)
               .scheme("bearer")
               .bearerFormat("JWT")
               .in(SecurityScheme.In.HEADER)
               .name("Authorization");

           openAPI.getComponents().addSecuritySchemes("BearerAuth", jwtScheme);
           openAPI.addSecurityItem(new SecurityRequirement().addList("BearerAuth"));

           return openAPI;
       }
   }
   ```

4. **Annotate Endpoints**

   * Use `@Tag`, `@Operation`, `@ApiResponse`, `@ApiResponses`, and `@Parameter` on endpoint interfaces.
   * For multipart endpoints (`@RequestParam MultipartFile file`), add `@Operation(requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data")))`.

---

## 14. SonarQube Integration

1. **Setup SonarQube Server**

   * Download and install SonarQube (e.g., Community Edition).
   * Configure `sonar.properties` with database and port settings.
   * Start SonarQube via `./bin/<platform>/sonar.sh start` (ensure Java and Tomcat paths configured).
   * Default credentials: `admin` / `admin`.

2. **Maven Configuration**

   * In project root, run:

     ```bash
     mvn sonar:sonar \
       -Dsonar.host.url=http://localhost:9000 \
       -Dsonar.login=<your-token>
     ```

3. **Code Quality Fixes**

   * Example: Add `serialVersionUID` to `Serializable` entities:

     ```java
     @Entity
     @EntityListeners(AuditingEntityListener.class)
     public class User implements Serializable {
         private static final long serialVersionUID = 1L;
         // ...
     }
     ```
   * Follow Sonar suggestions on naming conventions, complexity, duplicates, etc.

---

## 15. Spring Actuator

1. **Dependency**

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
   ```

2. **Configuration (application.yml)**

   ```yaml
   management:
     endpoints:
       web:
         exposure:
           include: "*"   # In production, restrict to only needed endpoints (health, info)
           exclude: "beans,loggers"
     endpoint:
       health:
         show-details: always
       shutdown:
         access: unrestricted
   ```

3. **Common Endpoints**

   * `/actuator/health`: Show application health and details.
   * `/actuator/info`: Custom application info (version, build).
   * `/actuator/metrics`: JVM and system metrics.
   * `/actuator/loggers`: Adjust log levels at runtime.
   * `/actuator/shutdown`: Gracefully shut down the application (when enabled).

4. **Usage**

   * Monitor application status in production.
   * Integrate with Prometheus/Grafana for dashboards.

---

## 16. Deployment & Dockerization

1. **Dockerfile Example**

   ```dockerfile
   FROM openjdk:17-jdk-slim as build
   WORKDIR /app
   COPY pom.xml mvnw .
   COPY .mvn .mvn
   COPY src src
   RUN ./mvnw clean package -DskipTests

   FROM openjdk:17-jre-slim
   WORKDIR /app
   COPY --from=build /app/target/*.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "app.jar"]
   ```

2. **docker-compose.yml** (with MySQL and Eureka)

   ```yaml
   version: '3.8'
   services:
     mysql:
       image: mysql:8.0
       environment:
         MYSQL_ROOT_PASSWORD: root_password
         MYSQL_DATABASE: agri
       ports:
         - "3306:3306"
       volumes:
         - db_data:/var/lib/mysql

     eureka-server:
       image: eurekaserver:latest
       ports:
         - "8761:8761"
       environment:
         - SPRING_PROFILES_ACTIVE=dev

     agri-java:
       build: .
       ports:
         - "8080:8080"
       depends_on:
         - mysql
         - eureka-server
       environment:
         SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/agri
         SPRING_DATASOURCE_USERNAME: root
         SPRING_DATASOURCE_PASSWORD: root_password
         EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://eureka-server:8761/eureka/
   volumes:
     db_data:
   ```

3. **AWS Deployment**

   * Push Docker images to Amazon ECR.
   * Use Amazon ECS or EKS for container orchestration.
   * Configure AWS RDS for MySQL database.

---

## 17. Miscellaneous Best Practices & Notes

* **Branch Management:** Always use feature branches, code review (PR), and squash & merge into `dev`.
* **Database Migration:** For production, use a migration tool (e.g., Flyway or Liquibase) instead of `hibernate.ddl-auto=update`.
* **Role-Based Access Control:** Use `@PreAuthorize("hasRole('ROLE_NAME')")` on methods. Ensure `ROLE_` prefix matches `GrantedAuthority` values.
* **Exception Handling:** Handle filter-layer exceptions explicitly since `@ControllerAdvice` won’t catch them.
* **Password Policies:** Enforce strong password regex in `application.yml` (e.g., minimum length, complexity).
* **Logging in Code:** Use `logger.info("Class:method - Message")` or `@Slf4j` with `log.info(...)`.
* **Directory Layout:** Maintain consistent directory structure in `src/main/java` and `src/main/resources`.
* **Modularization:** If project grows, consider splitting into multiple microservices (Auth, Notes, Todo) behind a gateway.

---
]
