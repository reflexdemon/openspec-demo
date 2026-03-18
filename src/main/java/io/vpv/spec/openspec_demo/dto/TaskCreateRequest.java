package io.vpv.spec.openspec_demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Request to create a new task")
public record TaskCreateRequest(
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Schema(description = "Task title", example = "Complete project documentation")
    String title,

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    @Schema(description = "Task description", example = "Write comprehensive API documentation")
    String description,

    @Schema(description = "Task priority", example = "HIGH")
    String priority,

    @Schema(description = "Task status", example = "PENDING")
    String status,

    @Schema(description = "Due date", example = "2024-12-31")
    LocalDate dueDate,

    @Schema(description = "Assignee user ID", example = "user123")
    String assignee
) {}
