## 1. Project Setup

- [x] 1.1 Create Spring Boot project structure with Maven
- [x] 1.2 Add dependencies: spring-boot-starter-web, spring-boot-starter-data-jpa, spring-boot-starter-validation, h2, springdoc-openapi-starter-webmvc-ui
- [x] 1.3 Configure application.properties with H2 in-memory database
- [x] 1.4 Verify application starts successfully

## 2. Entity Layer

- [x] 2.1 Create Priority enum (LOW, MEDIUM, HIGH, URGENT)
- [x] 2.2 Create TaskStatus enum (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)
- [x] 2.3 Create Task entity with all attributes (id, title, description, priority, status, dueDate, assignee, createdAt, updatedAt, version)
- [x] 2.4 Add JPA annotations (@Entity, @Table, @Id, @GeneratedValue, @Version, @Enumerated, @Column, @PrePersist, @PreUpdate)
- [x] 2.5 Add database indexes for status, assignee, dueDate columns

## 3. Repository Layer

- [x] 3.1 Create TaskRepository extending JpaRepository<Task, Long>
- [x] 3.2 Add custom query methods for filtering by status and assignee
- [x] 3.3 Verify repository works with H2 console

## 4. DTO Layer

- [x] 4.1 Create TaskCreateRequest with Jakarta validation annotations (@NotBlank, @Size)
- [x] 4.2 Create TaskUpdateRequest with optional fields and validation
- [x] 4.3 Create TaskResponse with all task fields and OpenAPI annotations
- [x] 4.4 Create PagedResponse wrapper for paginated results
- [x] 4.5 Create ErrorResponse for consistent error format

## 5. Service Layer

- [x] 5.1 Define TaskService interface (create, getById, update, delete, list with pagination/filtering)
- [x] 5.2 Implement TaskServiceImpl with business logic
- [x] 5.3 Implement pagination and sorting using Pageable
- [x] 5.4 Add filtering by status and assignee
- [x] 5.5 Handle TaskNotFoundException for missing tasks

## 6. Controller Layer

- [x] 6.1 Create TaskController with REST endpoints (POST, GET, PUT, DELETE /api/tasks)
- [x] 6.2 Add @RequestHeader("X-User-Id") validation
- [x] 6.3 Add @PageableDefault for pagination defaults
- [x] 6.4 Add query parameters for status and assignee filtering
- [x] 6.5 Add OpenAPI annotations (@Operation, @ApiResponse, @Parameter)
- [x] 6.6 Test all endpoints via Swagger UI

## 7. Exception Handling

- [x] 7.1 Create TaskNotFoundException runtime exception
- [x] 7.2 Create GlobalExceptionHandler with @ControllerAdvice
- [x] 7.3 Handle MethodArgumentNotValidException for validation errors
- [x] 7.4 Handle EntityNotFoundException for 404 responses
- [x] 7.5 Handle generic Exception for 500 responses
- [x] 7.6 Verify consistent error response format across all endpoints

## 8. OpenAPI Configuration

- [x] 8.1 Create OpenApiConfig for Swagger customization
- [x] 8.2 Configure API info (title, description, version)
- [x] 8.3 Verify /swagger-ui.html is accessible
- [x] 8.4 Verify /v3/api-docs returns OpenAPI spec
- [x] 8.5 Test that bean validation constraints appear in documentation

## 9. Integration Testing

- [x] 9.1 Write tests for TaskService CRUD operations
- [x] 9.2 Write tests for pagination and sorting
- [x] 9.3 Write tests for filtering by status and assignee
- [x] 9.4 Verify all tests pass
- [x] 9.5 Verify Swagger UI shows correct schemas and descriptions

## 10. Database Migration Preparation

- [x] 10.1 Add MySQL profile configuration in application-mysql.properties
- [x] 10.2 Update application.properties with profile switching
- [x] 10.3 Document H2 to MySQL migration steps
