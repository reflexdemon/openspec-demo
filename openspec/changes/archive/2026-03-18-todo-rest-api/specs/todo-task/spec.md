## ADDED Requirements

### Requirement: Task entity with rich attributes
The system SHALL provide a Task entity with the following attributes:
- `id`: Long, auto-generated unique identifier
- `title`: String (1-200 chars), required
- `description`: String (0-5000 chars), optional
- `priority`: Enum (LOW, MEDIUM, HIGH, URGENT), defaults to MEDIUM
- `status`: Enum (PENDING, IN_PROGRESS, COMPLETED, CANCELLED), defaults to PENDING
- `dueDate`: LocalDate, optional
- `assignee`: String (user identifier), optional (null means unassigned)
- `createdAt`: LocalDateTime, auto-set on creation
- `updatedAt`: LocalDateTime, auto-updated on modification
- `version`: Long for optimistic locking

### Requirement: User identification
The system SHALL use a `userId` header parameter for user identification in API requests. The header name SHALL be `X-User-Id`.

#### Scenario: User identification header present
- **WHEN** a request includes `X-User-Id: user123` header
- **THEN** the system SHALL use `user123` as the user identifier for the operation

#### Scenario: User identification header missing
- **WHEN** a request does not include the `X-User-Id` header
- **THEN** the system SHALL return HTTP 400 Bad Request with error message

### Requirement: Task assignment
The system SHALL allow any user to assign an unassigned task to themselves by setting the assignee to their userId.

#### Scenario: Assign unassigned task
- **WHEN** user123 assigns a task with `assignee: null`
- **THEN** the system SHALL set `assignee` to `user123`

#### Scenario: Reassign task to self
- **WHEN** user456 assigns a task currently assigned to user123
- **THEN** the system SHALL set `assignee` to `user456`

### Requirement: View other users' tasks
The system SHALL allow any user to view tasks created by other users, including assigned and unassigned tasks.

#### Scenario: List all tasks across users
- **WHEN** user requests paginated list of tasks
- **THEN** the system SHALL return tasks regardless of original creator
