package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.Faculty;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Iterator;

public interface DepartmentRepo extends
        CrudRepository<Department, Long>,
        JpaSpecificationExecutor<Department>
{
    void deleteAllByIdIn(Collection<Long> ids);
    Collection<Department> findAllBy();
}
