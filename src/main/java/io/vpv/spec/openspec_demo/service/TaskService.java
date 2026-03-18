package io.vpv.spec.openspec_demo.service;

import io.vpv.spec.openspec_demo.dto.PagedResponse;
import io.vpv.spec.openspec_demo.dto.TaskCreateRequest;
import io.vpv.spec.openspec_demo.dto.TaskResponse;
import io.vpv.spec.openspec_demo.dto.TaskUpdateRequest;
import io.vpv.spec.openspec_demo.entity.TaskStatus;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponse create(TaskCreateRequest request);
    TaskResponse getById(Long id);
    TaskResponse update(Long id, TaskUpdateRequest request);
    void delete(Long id);
    PagedResponse<TaskResponse> list(TaskStatus status, String assignee, Pageable pageable);
}
