package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Faculty;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface FacultyRepo extends
        CrudRepository<Faculty, Long>,
        JpaSpecificationExecutor<Faculty>
{
    @Transactional
    void deleteAllByIdIn(Collection<Long> ids);
    Collection<Faculty> findAllByIdIn(Set<Long> ids);
    Collection<Faculty> findAllBy();
}