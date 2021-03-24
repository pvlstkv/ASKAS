package com.example.javaserver.study.repo;

import com.example.javaserver.study.model.UserFile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserFileRepo extends
        CrudRepository<UserFile, Long>,
        JpaSpecificationExecutor<UserFile>
{
    Set<UserFile> getUserFilesByIdIn(Set<Long> ids);
}
