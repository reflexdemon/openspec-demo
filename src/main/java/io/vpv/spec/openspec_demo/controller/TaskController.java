package io.vpv.spec.openspec_demo.controller;

import io.vpv.spec.openspec_demo.dto.PagedResponse;
import io.vpv.spec.openspec_demo.dto.TaskCreateRequest;
import io.vpv.spec.openspec_demo.dto.TaskResponse;
import io.vpv.spec.openspec_demo.dto.TaskUpdateRequest;
import io.vpv.spec.openspec_demo.entity.TaskStatus;
import io.vpv.spec.openspec_demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Task management API")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Task created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<TaskResponse> create(
            @Valid @RequestBody TaskCreateRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        TaskResponse response = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "List all tasks with pagination and filtering")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    })
    public ResponseEntity<PagedResponse<TaskResponse>> list(
            @Parameter(description = "Filter by task status")
            @RequestParam(required = false) TaskStatus status,
            
            @Parameter(description = "Filter by assignee")
            @RequestParam(required = false) String assignee,
            
            @PageableDefault(size = 20) Pageable pageable) {
        PagedResponse<TaskResponse> response = taskService.list(status, assignee, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskResponse> getById(
            @Parameter(description = "Task ID")
            @PathVariable Long id) {
        TaskResponse response = taskService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task updated successfully"),
        @ApiResponse(responseCode = "404", description = "Task not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<TaskResponse> update(
            @Parameter(description = "Task ID")
            @PathVariable Long id,
            
            @Valid @RequestBody TaskUpdateRequest request) {
        TaskResponse response = taskService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Task ID")
            @PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
