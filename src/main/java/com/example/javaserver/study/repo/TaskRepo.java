package com.example.javaserver.study.repo;

import com.example.javaserver.study.model.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends
        CrudRepository<Task, Long>,
        JpaSpecificationExecutor<Task>
{

}
