## Why

Build a multi-user TODO REST API using Spring Boot with JPA to support a TODO application. The API will provide CRUD operations for tasks with features like user assignment, sorting, and pagination. Using JPA allows easy database migration from in-memory (H2) to production (MySQL).

## What Changes

- Create TODO task entity with rich attributes (title, description, priority, status, due date, assignee, timestamps)
- Implement REST API endpoints for CRUD operations on tasks
- Add multi-user support with user assignment and self-assignment
- Implement pagination and sorting for task lists
- Add OpenAPI/Swagger documentation
- Configure H2 in-memory database for development
- Add Jakarta Bean Validation for request validation
- Add global exception handling with @ControllerAdvice
- Structure code for future MySQL migration

## Capabilities

### New Capabilities

- `todo-task`: Core task entity with rich attributes (title, description, priority, status, due date, assignee, created/updated timestamps)
- `todo-api`: REST API endpoints for task CRUD operations with pagination, sorting, and filtering
- `openapi-docs`: Swagger/OpenAPI documentation for the TODO API

### Modified Capabilities

<!-- No existing capabilities being modified -->

## Impact

- **New files**: Entity, Repository, Service, Controller, DTOs, Exception handlers, OpenAPI config
- **Dependencies**: Spring Boot Starter Web, Spring Boot Starter Data JPA, Springdoc OpenAPI, H2 Database, Jakarta Validation
- **API**: RESTful endpoints at `/api/tasks`
- **Database**: H2 (dev) → MySQL (prod) via JPA abstraction
