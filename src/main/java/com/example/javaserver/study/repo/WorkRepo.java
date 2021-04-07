package com.example.javaserver.study.repo;

import com.example.javaserver.study.model.Work;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface WorkRepo extends
        CrudRepository<Work, Long>,
        JpaSpecificationExecutor<Work>
{
    Set<Work> getWorksByIdIn(Set<Long> ids);
    @Transactional
    void deleteAllByIdIn(Set<Long> ids);
    List<Work> findAllByUserIdAndTaskId(Integer userId, Long taskId);
}
