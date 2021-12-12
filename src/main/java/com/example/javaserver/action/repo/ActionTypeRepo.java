package com.example.javaserver.action.repo;

import com.example.javaserver.action.model.ActionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTypeRepo extends CrudRepository<ActionType, Long> {
}
