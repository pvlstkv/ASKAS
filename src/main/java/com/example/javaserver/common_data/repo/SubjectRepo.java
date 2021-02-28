package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface SubjectRepo extends
        CrudRepository<Subject, Long>,
        JpaSpecificationExecutor<Subject>
{
    Optional<Subject> findById(Integer id); // todo удалить
    Optional<Subject> findByName(String name);
    boolean existsByName(String name);
    void deleteAllByIdIn(Collection<Integer> ids);
    Collection<Subject> findAllBy();
}