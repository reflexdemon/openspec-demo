## ADDED Requirements

### Requirement: OpenAPI documentation
The system SHALL expose OpenAPI 3.0 (Swagger) documentation at `/swagger-ui.html`.

#### Scenario: Access Swagger UI
- **WHEN** a user navigates to `/swagger-ui.html`
- **THEN** the system SHALL display an interactive Swagger UI for the TODO API

#### Scenario: Access OpenAPI JSON
- **WHEN** a user requests `/v3/api-docs`
- **THEN** the system SHALL return OpenAPI 3.0 specification in JSON format

### Requirement: API documentation with descriptions
The system SHALL provide descriptive documentation for all endpoints including:
- Operation summaries and descriptions
- Request/response body schemas
- Parameter descriptions
- HTTP status codes and error responses

#### Scenario: Documented create endpoint
- **WHEN** viewing the OpenAPI spec
- **THEN** POST `/api/tasks` SHALL include summary, description, request body schema, and response codes

### Requirement: Schema definitions
The system SHALL define reusable schemas for Task, error responses, and pagination.

#### Scenario: Task schema
- **WHEN** generating OpenAPI spec
- **THEN** the system SHALL include a Task schema with all entity attributes

#### Scenario: Error response schema
- **WHEN** generating OpenAPI spec
- **THEN** the system SHALL include an Error schema with message, status, and timestamp

#### Scenario: Page response schema
- **WHEN** generating OpenAPI spec
- **THEN** the system SHALL include a PagedTask schema with content, page, size, totalElements, totalPages

### Requirement: Jakarta Bean Validation annotations
The system SHALL use Jakarta Bean Validation annotations that are reflected in the OpenAPI documentation.

#### Scenario: Required field validation documented
- **WHEN** viewing the OpenAPI spec for POST /api/tasks
- **THEN** title field SHALL show as required with min/max constraints
