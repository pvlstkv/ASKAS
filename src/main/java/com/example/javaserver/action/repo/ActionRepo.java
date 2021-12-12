package com.example.javaserver.action.repo;

import com.example.javaserver.action.model.Action;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepo extends CrudRepository<Action, Long> {
}
