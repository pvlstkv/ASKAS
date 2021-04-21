package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.repo.TaskRepo;
import com.example.javaserver.study.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class TaskIdMapper {
    private final TaskService service;

    @Autowired
    public TaskIdMapper(TaskService service) {
        this.service = service;
    }

    public Task getById(Long id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Long getId(Task task) {
        return task == null
                ? null
                : task.getId();
    }

    public Set<Task> getByIds(Set<Long> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Long> getIds(Set<Task> tasks) {
        return tasks == null
                ? null
                : tasks.stream().map(Task::getId).collect(Collectors.toSet());
    }
}
