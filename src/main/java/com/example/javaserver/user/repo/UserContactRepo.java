package com.example.javaserver.user.repo;

import com.example.javaserver.user.model.UserContact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserContactRepo extends CrudRepository<UserContact, Long> {
    Set<UserContact> deleteAllByIdIn(Set<Long> ids);
    Set<UserContact> findAllByIdIn(Set<Long> ids);
    Set<UserContact> findAllByUserId(Integer userId);
}
