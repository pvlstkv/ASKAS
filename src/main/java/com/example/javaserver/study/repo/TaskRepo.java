package com.example.javaserver.study.repo;

import com.example.javaserver.study.model.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Set;

public interface TaskRepo extends
        CrudRepository<Task, Long>,
        JpaSpecificationExecutor<Task>
{
    @Transactional
    void deleteAllByIdIn(Set<Long> ids);
    Set<Task> findAllByIdIn(Set<Long> ids);
}