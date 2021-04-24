package com.example.javaserver.conference.service;

import com.example.javaserver.conference.model.Conference;
import com.example.javaserver.conference.repo.ConferenceRepo;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConferenceService {
    private final ConferenceRepo conferenceRepo;
    private final UserService userService;

    @Autowired
    public ConferenceService(ConferenceRepo conferenceRepo, UserService userService) {
        this.conferenceRepo = conferenceRepo;
        this.userService = userService;
    }

    @Transactional
    public Conference create(Conference conference, UserDetailsImp userDetails) {
        User user = userService.getById(userDetails.getId());

        conference.setUser(user);
        return conferenceRepo.save(conference);
    }

    @Transactional
    public Conference delete(Long id) {
        Conference conference = getById(id);

        conferenceRepo.delete(conference);
        return conference;
    }

    @Transactional
    public Conference update(Long id, Conference conferenceToPut) {
        Conference conference = getById(id);
        conference.setStudyGroups(conferenceToPut.getStudyGroups());
        return conference;
    }

    public Set<Conference> getAll() {
        return conferenceRepo.getAllBy();
    }

    public Conference getById(Long id) {
        Optional<Conference> conference = conferenceRepo.findById(id);
        if (conference.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Конференция с id " + id + " не найдена");
        }
        return conference.get();
    }

    public Set<Conference> getByIds(Set<Long> ids) {
        Set<Conference> conferences = conferenceRepo.findAllByIdIn(ids);
        if (conferences.size() == ids.size()) {
            return conferences;
        } else {
            Collection<Long> foundIds = conferences.stream()
                    .map(Conference::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Конференции с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Conference> getByStudentId(UserDetailsImp userDetails) {
        return getByStudentId(userDetails.getId());
    }

    public Set<Conference> getByStudentId(Integer studentId) {
        User user = userService.getById(studentId);
        if (!user.getRole().equals(UserRole.USER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является студентом");
        }
        return user.getStudyGroup().getConferences();
    }
}
