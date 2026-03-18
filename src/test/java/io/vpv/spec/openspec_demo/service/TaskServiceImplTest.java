package io.vpv.spec.openspec_demo.service;

import io.vpv.spec.openspec_demo.dto.PagedResponse;
import io.vpv.spec.openspec_demo.dto.TaskCreateRequest;
import io.vpv.spec.openspec_demo.dto.TaskResponse;
import io.vpv.spec.openspec_demo.dto.TaskUpdateRequest;
import io.vpv.spec.openspec_demo.entity.Priority;
import io.vpv.spec.openspec_demo.entity.TaskStatus;
import io.vpv.spec.openspec_demo.exception.TaskNotFoundException;
import io.vpv.spec.openspec_demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void create_shouldCreateTask() {
        TaskCreateRequest request = new TaskCreateRequest(
            "Test Task",
            "Test Description",
            "HIGH",
            "PENDING",
            null,
            "user123"
        );

        TaskResponse response = taskService.create(request);

        assertNotNull(response.id());
        assertEquals("Test Task", response.title());
        assertEquals("Test Description", response.description());
        assertEquals("HIGH", response.priority());
        assertEquals("PENDING", response.status());
        assertEquals("user123", response.assignee());
    }

    @Test
    void getById_shouldReturnTask() {
        TaskCreateRequest request = new TaskCreateRequest(
            "Get Test", null, "MEDIUM", "PENDING", null, null
        );
        TaskResponse created = taskService.create(request);

        TaskResponse found = taskService.getById(created.id());

        assertEquals(created.id(), found.id());
        assertEquals(created.title(), found.title());
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        assertThrows(TaskNotFoundException.class, () -> taskService.getById(999L));
    }

    @Test
    void update_shouldUpdateTask() {
        TaskCreateRequest createRequest = new TaskCreateRequest(
            "Original", null, "LOW", "PENDING", null, null
        );
        TaskResponse created = taskService.create(createRequest);

        TaskUpdateRequest updateRequest = new TaskUpdateRequest(
            "Updated", "New description", "HIGH", "IN_PROGRESS", null, "user456"
        );
        TaskResponse updated = taskService.update(created.id(), updateRequest);

        assertEquals("Updated", updated.title());
        assertEquals("New description", updated.description());
        assertEquals("HIGH", updated.priority());
        assertEquals("IN_PROGRESS", updated.status());
        assertEquals("user456", updated.assignee());
    }

    @Test
    void delete_shouldRemoveTask() {
        TaskCreateRequest request = new TaskCreateRequest(
            "To Delete", null, "LOW", "PENDING", null, null
        );
        TaskResponse created = taskService.create(request);

        taskService.delete(created.id());

        assertThrows(TaskNotFoundException.class, () -> taskService.getById(created.id()));
    }

    @Test
    void list_shouldReturnPaginatedResults() {
        for (int i = 0; i < 25; i++) {
            taskService.create(new TaskCreateRequest(
                "Task " + i, null, "MEDIUM", "PENDING", null, null
            ));
        }

        PagedResponse<TaskResponse> page1 = taskService.list(null, null, PageRequest.of(0, 10));
        PagedResponse<TaskResponse> page2 = taskService.list(null, null, PageRequest.of(1, 10));

        assertEquals(10, page1.content().size());
        assertEquals(10, page2.content().size());
        assertEquals(25, page1.totalElements());
        assertEquals(3, page1.totalPages());
    }

    @Test
    void list_shouldFilterByStatus() {
        taskService.create(new TaskCreateRequest("Task 1", null, "LOW", "PENDING", null, null));
        taskService.create(new TaskCreateRequest("Task 2", null, "LOW", "COMPLETED", null, null));
        taskService.create(new TaskCreateRequest("Task 3", null, "LOW", "PENDING", null, null));

        PagedResponse<TaskResponse> result = taskService.list(TaskStatus.PENDING, null, PageRequest.of(0, 20));

        assertEquals(2, result.content().size());
        assertTrue(result.content().stream().allMatch(t -> "PENDING".equals(t.status())));
    }

    @Test
    void list_shouldFilterByAssignee() {
        taskService.create(new TaskCreateRequest("Task 1", null, "LOW", "PENDING", null, "alice"));
        taskService.create(new TaskCreateRequest("Task 2", null, "LOW", "PENDING", null, "bob"));
        taskService.create(new TaskCreateRequest("Task 3", null, "LOW", "PENDING", null, "alice"));

        PagedResponse<TaskResponse> result = taskService.list(null, "alice", PageRequest.of(0, 20));

        assertEquals(2, result.content().size());
        assertTrue(result.content().stream().allMatch(t -> "alice".equals(t.assignee())));
    }

    @Test
    void list_shouldReturnResults() {
        taskService.create(new TaskCreateRequest("Task 1", null, "LOW", "PENDING", null, null));
        taskService.create(new TaskCreateRequest("Task 2", null, "LOW", "PENDING", null, null));

        PagedResponse<TaskResponse> result = taskService.list(null, null, PageRequest.of(0, 20));

        assertEquals(2, result.content().size());
        assertEquals(2, result.totalElements());
    }
}
