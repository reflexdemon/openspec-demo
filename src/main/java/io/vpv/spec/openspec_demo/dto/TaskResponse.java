package io.vpv.spec.openspec_demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.vpv.spec.openspec_demo.entity.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Task response")
public record TaskResponse(
    @Schema(description = "Unique identifier", example = "1")
    Long id,

    @Schema(description = "Task title", example = "Complete project documentation")
    String title,

    @Schema(description = "Task description", example = "Write comprehensive API documentation")
    String description,

    @Schema(description = "Task priority", example = "HIGH")
    String priority,

    @Schema(description = "Task status", example = "PENDING")
    String status,

    @Schema(description = "Due date", example = "2024-12-31")
    LocalDate dueDate,

    @Schema(description = "Assignee user ID", example = "user123")
    String assignee,

    @Schema(description = "Creation timestamp")
    LocalDateTime createdAt,

    @Schema(description = "Last update timestamp")
    LocalDateTime updatedAt,

    @Schema(description = "Version for optimistic locking")
    Long version
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getPriority() != null ? task.getPriority().name() : null,
            task.getStatus() != null ? task.getStatus().name() : null,
            task.getDueDate(),
            task.getAssignee(),
            task.getCreatedAt(),
            task.getUpdatedAt(),
            task.getVersion()
        );
    }
}
