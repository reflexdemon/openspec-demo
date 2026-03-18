package io.vpv.spec.openspec_demo.repository;

import io.vpv.spec.openspec_demo.entity.Task;
import io.vpv.spec.openspec_demo.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findByAssignee(String assignee, Pageable pageable);
    Page<Task> findByStatusAndAssignee(TaskStatus status, String assignee, Pageable pageable);
}
