package com.example.javaserver.study.repo;

import com.example.javaserver.study.model.Literature;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public interface LiteratureRepo extends
        CrudRepository<Literature, Long>,
        JpaSpecificationExecutor<Literature>
{
    Optional<Literature> findByIdEquals(Long id);
    Literature getOne(Long id);
    @Transactional
    void deleteAllByIdIn(Set<Long> ids);
    Set<Literature> findAllByIdIn(Set<Long> ids);
}
