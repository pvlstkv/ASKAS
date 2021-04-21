package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class TaskIdMapper {
    private final TaskRepo repo;

    @Autowired
    public TaskIdMapper(TaskRepo repo) {
        this.repo = repo;
    }

    public Task getById(Long id) {
        Optional<Task> taskO = repo.findByIdEquals(id);
        if (taskO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание с указанным id не существует");
        }
        return taskO.get();
    }

    public Long getId(Task task) {
        return task == null
                ? null
                : task.getId();
    }

    public Set<Task> getByIds(Set<Long> ids) {
        Set<Task> tasks = repo.findAllByIdIn(ids);
        if (tasks.size() == ids.size()) {
            return tasks;
        } else {
            Collection<Long> foundIds = tasks.stream()
                    .map(Task::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Задания с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<Task> tasks) {
        return tasks == null
                ? null
                : tasks.stream().map(Task::getId).collect(Collectors.toSet());
    }
}
