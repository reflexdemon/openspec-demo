## ADDED Requirements

### Requirement: Create task endpoint
The system SHALL provide a POST `/api/tasks` endpoint to create a new task.

#### Scenario: Create task successfully
- **WHEN** POST `/api/tasks` is called with valid task data
- **THEN** the system SHALL return HTTP 201 Created with the created task

#### Scenario: Create task with invalid data
- **WHEN** POST `/api/tasks` is called with missing required fields
- **THEN** the system SHALL return HTTP 400 Bad Request with validation errors

### Requirement: Get task by ID endpoint
The system SHALL provide a GET `/api/tasks/{id}` endpoint to retrieve a single task.

#### Scenario: Get existing task
- **WHEN** GET `/api/tasks/123` is called for an existing task
- **THEN** the system SHALL return HTTP 200 OK with the task data

#### Scenario: Get non-existent task
- **WHEN** GET `/api/tasks/999` is called for a non-existent task
- **THEN** the system SHALL return HTTP 404 Not Found

### Requirement: Update task endpoint
The system SHALL provide a PUT `/api/tasks/{id}` endpoint to update an existing task.

#### Scenario: Update task successfully
- **WHEN** PUT `/api/tasks/123` is called with valid update data
- **THEN** the system SHALL return HTTP 200 OK with the updated task

#### Scenario: Update non-existent task
- **WHEN** PUT `/api/tasks/999` is called
- **THEN** the system SHALL return HTTP 404 Not Found

### Requirement: Delete task endpoint
The system SHALL provide a DELETE `/api/tasks/{id}` endpoint to delete a task.

#### Scenario: Delete existing task
- **WHEN** DELETE `/api/tasks/123` is called
- **THEN** the system SHALL return HTTP 204 No Content

### Requirement: List tasks with pagination
The system SHALL provide a GET `/api/tasks` endpoint that returns paginated results.

#### Scenario: Default pagination
- **WHEN** GET `/api/tasks` is called without pagination parameters
- **THEN** the system SHALL return first 20 tasks with page info (page, size, totalElements, totalPages)

#### Scenario: Custom pagination
- **WHEN** GET `/api/tasks?page=2&size=10` is called
- **THEN** the system SHALL return tasks 11-20 with updated page info

### Requirement: Sort tasks
The system SHALL support sorting on the list endpoint via `sort` parameter.

#### Scenario: Sort by due date ascending
- **WHEN** GET `/api/tasks?sort=dueDate,asc` is called
- **THEN** the system SHALL return tasks ordered by dueDate from earliest to latest

#### Scenario: Sort by priority then due date
- **WHEN** GET `/api/tasks?sort=priority,asc&sort=dueDate,asc` is called
- **THEN** the system SHALL return tasks ordered by priority, then by dueDate within each priority

### Requirement: Filter tasks
The system SHALL support filtering tasks by status and assignee.

#### Scenario: Filter by status
- **WHEN** GET `/api/tasks?status=PENDING` is called
- **THEN** the system SHALL return only tasks with status PENDING

#### Scenario: Filter by assignee
- **WHEN** GET `/api/tasks?assignee=user123` is called
- **THEN** the system SHALL return only tasks assigned to user123

### Requirement: Global error handling
The system SHALL use @ControllerAdvice to handle all exceptions and return consistent error responses.

#### Scenario: Handle validation errors
- **WHEN** a request fails bean validation
- **THEN** the system SHALL return HTTP 400 with field-level error details

#### Scenario: Handle generic exceptions
- **WHEN** an unexpected exception occurs
- **THEN** the system SHALL return HTTP 500 with a generic error message and log the exception
