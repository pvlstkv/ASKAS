package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SubjectRepo extends
        CrudRepository<Subject, Long>,
        JpaSpecificationExecutor<Subject>
{
    Optional<Subject> findByIdEquals(Long id);
    Optional<Subject> findByName(String name);
    boolean existsByName(String name);
    @Transactional
    void deleteAllByIdIn(Collection<Long> ids);
    Set<Subject> findAllByIdIn(Set<Long> ids);
    Collection<Subject> findAllBy();
}