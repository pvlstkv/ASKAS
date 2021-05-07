package com.example.javaserver.common_data.repo;
import com.example.javaserver.common_data.model.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface DepartmentRepo extends
        CrudRepository<Department, Long>,
        JpaSpecificationExecutor<Department>
{
    @Transactional
    void deleteAllByIdIn(Collection<Long> ids);
    Set<Department> findAllByIdIn(Set<Long> ids);
    Collection<Department> findAllBy();
    Optional<Department> findByShortName(String name);
}
