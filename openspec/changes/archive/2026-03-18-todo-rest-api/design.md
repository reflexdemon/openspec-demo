## Context

Building a multi-user TODO REST API using Spring Boot with JPA. The API will provide CRUD operations for tasks with rich attributes (title, description, priority, status, due date, assignee). Using H2 in-memory database for development with planned migration to MySQL. Skip authentication (simplified model) but use X-User-Id header for user identification.

## Goals / Non-Goals

**Goals:**
- Provide RESTful API for TODO task management
- Support multi-user with task assignment capabilities
- Enable pagination, sorting, and filtering of tasks
- Generate OpenAPI documentation via Swagger
- Use Jakarta Bean Validation for request validation
- Implement global exception handling
- Ensure easy database migration from H2 to MySQL via JPA

**Non-Goals:**
- Authentication/authorization (X-User-Id header for identification only)
- User management endpoints
- Task comments or attachments
- Real-time notifications
- Task dependencies or subtasks

## Decisions

### 1. Layered Architecture (Controller → Service → Repository)

**Decision:** Use standard layered architecture with TaskController, TaskService, and TaskRepository.

**Rationale:** Clear separation of concerns, testable, follows Spring conventions. Controller handles HTTP, Service contains business logic, Repository handles data access.

**Alternative:** Use `@RepositoryRestResource` for automatic REST exposure - rejected because it provides less control over API shape and documentation.

### 2. DTO Pattern for API Requests/Responses

**Decision:** Create separate DTOs (TaskCreateRequest, TaskUpdateRequest, TaskResponse) instead of exposing entity directly.

**Rationale:** 
- Prevents over-posting attacks
- Decouples API contract from database schema
- Allows field transformations (e.g., LocalDateTime to ISO string)

**Alternative:** Use entity classes directly with Jackson views - rejected for complexity.

### 3. JPA Repository with Pageable

**Decision:** Use `JpaRepository<Task, Long>` with `Pageable` parameter for list operations.

**Rationale:** Provides built-in pagination, sorting, and CRUD operations. Specification API for dynamic filtering.

**Alternative:** Custom Repository implementation - rejected as unnecessary complexity.

### 4. Springdoc OpenAPI

**Decision:** Use springdoc-openapi library (`springdoc-openapi-starter-webmvc-ui`).

**Rationale:** 
- Auto-generates OpenAPI 3.0 spec from annotations
- Integrates with Swagger UI
- Works with Bean Validation annotations

**Alternative:** Manual OpenAPI spec - rejected due to maintenance burden.

### 5. Global Exception Handling with @ControllerAdvice

**Decision:** Implement a single `GlobalExceptionHandler` class with `@ControllerAdvice`.

**Rationale:** Centralized error handling ensures consistent error response format across all endpoints.

### 6. Optimistic Locking

**Decision:** Include `@Version` field in Task entity.

**Rationale:** Prevents concurrent update conflicts without requiring database-level locks. Spring Data JPA handles version checking automatically.

## Package Structure

```
com.example.todo
├── controller
│   └── TaskController.java
├── service
│   ├── TaskService.java
│   └── TaskServiceImpl.java
├── repository
│   └── TaskRepository.java
├── entity
│   ├── Task.java
│   ├── Priority.java
│   └── TaskStatus.java
├── dto
│   ├── TaskCreateRequest.java
│   ├── TaskUpdateRequest.java
│   ├── TaskResponse.java
│   └── PagedResponse.java
├── exception
│   ├── TaskNotFoundException.java
│   ├── GlobalExceptionHandler.java
│   └── ErrorResponse.java
└── config
    └── OpenApiConfig.java
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/tasks | Create new task |
| GET | /api/tasks | List tasks (paginated, sortable, filterable) |
| GET | /api/tasks/{id} | Get single task |
| PUT | /api/tasks/{id} | Update task |
| DELETE | /api/tasks/{id} | Delete task |

**Query Parameters for GET /api/tasks:**
- `page` (default: 0)
- `size` (default: 20)
- `sort` (e.g., `dueDate,asc`)
- `status` (filter)
- `assignee` (filter)

## Error Response Format

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "title",
      "message": "Title is required"
    }
  ]
}
```

## Database Configuration

**Development (H2):**
```properties
spring.datasource.url=jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1
spring.jpa.hibernate.ddl-auto=create-drop
```

**Production (MySQL):**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| X-User-Id header can be spoofed | Skip auth per requirements; trust header in this demo |
| Pagination without total count query | JPA handles count query automatically |
| Large datasets with sorting | Add indexes on frequently sorted columns |
| H2 to MySQL migration issues | Use standard JPA/Hibernate; test with MySQL before production |

## Open Questions

1. Should `assignee` be validated against existing users? (Decided: No, keep simple for MVP)
2. Maximum page size limit? (Decided: Default 100, configurable)
3. Soft delete vs hard delete? (Decided: Hard delete per requirements)
