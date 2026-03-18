package io.vpv.spec.openspec_demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Request to update an existing task")
public record TaskUpdateRequest(
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Schema(description = "Task title", example = "Updated project documentation")
    String title,

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    @Schema(description = "Task description", example = "Updated API documentation")
    String description,

    @Schema(description = "Task priority", example = "URGENT")
    String priority,

    @Schema(description = "Task status", example = "IN_PROGRESS")
    String status,

    @Schema(description = "Due date", example = "2025-01-15")
    LocalDate dueDate,

    @Schema(description = "Assignee user ID", example = "user456")
    String assignee
) {}
