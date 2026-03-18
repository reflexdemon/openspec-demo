package io.vpv.spec.openspec_demo.service;

import io.vpv.spec.openspec_demo.dto.PagedResponse;
import io.vpv.spec.openspec_demo.dto.TaskCreateRequest;
import io.vpv.spec.openspec_demo.dto.TaskResponse;
import io.vpv.spec.openspec_demo.dto.TaskUpdateRequest;
import io.vpv.spec.openspec_demo.entity.Priority;
import io.vpv.spec.openspec_demo.entity.Task;
import io.vpv.spec.openspec_demo.entity.TaskStatus;
import io.vpv.spec.openspec_demo.exception.TaskNotFoundException;
import io.vpv.spec.openspec_demo.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse create(TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority() != null ? Priority.valueOf(request.priority()) : Priority.MEDIUM);
        task.setStatus(request.status() != null ? TaskStatus.valueOf(request.status()) : TaskStatus.PENDING);
        task.setDueDate(request.dueDate());
        task.setAssignee(request.assignee());
        
        Task saved = taskRepository.save(task);
        return TaskResponse.from(saved);
    }

    @Override
    public TaskResponse getById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskResponse.from(task);
    }

    @Override
    public TaskResponse update(Long id, TaskUpdateRequest request) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));

        if (request.title() != null) {
            task.setTitle(request.title());
        }
        if (request.description() != null) {
            task.setDescription(request.description());
        }
        if (request.priority() != null) {
            task.setPriority(Priority.valueOf(request.priority()));
        }
        if (request.status() != null) {
            task.setStatus(TaskStatus.valueOf(request.status()));
        }
        if (request.dueDate() != null) {
            task.setDueDate(request.dueDate());
        }
        if (request.assignee() != null) {
            task.setAssignee(request.assignee());
        }

        Task updated = taskRepository.save(task);
        return TaskResponse.from(updated);
    }

    @Override
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public PagedResponse<TaskResponse> list(TaskStatus status, String assignee, Pageable pageable) {
        Page<Task> page;
        
        if (status != null && assignee != null) {
            page = taskRepository.findByStatusAndAssignee(status, assignee, pageable);
        } else if (status != null) {
            page = taskRepository.findByStatus(status, pageable);
        } else if (assignee != null) {
            page = taskRepository.findByAssignee(assignee, pageable);
        } else {
            page = taskRepository.findAll(pageable);
        }

        return new PagedResponse<>(
            page.getContent().stream().map(TaskResponse::from).toList(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
}
