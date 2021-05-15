package com.example.javaserver.conference.repo;

import com.example.javaserver.conference.model.Conference;
import com.example.javaserver.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ConferenceRepo extends CrudRepository<Conference, Long> {
    Set<Conference> findAllByIdIn(Set<Long> ids);
    Set<Conference> getAllBy();
    Set<Conference> findAllByUserEquals(User user);
}
