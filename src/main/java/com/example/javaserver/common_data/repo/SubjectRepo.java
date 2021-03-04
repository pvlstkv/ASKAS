package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SubjectRepo extends
        CrudRepository<Subject, Long>,
        JpaSpecificationExecutor<Subject>
{
    Optional<Subject> findByName(String name);

    boolean existsByName(String name);
    void deleteAllByIdIn(Collection<Long> ids);
    Collection<Subject> findAllByIdIn(Set<Long> ids);
    Collection<Subject> findAllBy();
}