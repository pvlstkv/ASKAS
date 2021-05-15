package com.example.javaserver.conference.service;

import com.example.javaserver.conference.model.Conference;
import com.example.javaserver.conference.repo.ConferenceRepo;
import com.example.javaserver.conference.service.dto.ConferenceRequestBody;
import com.example.javaserver.conference.service.dto.ConferenceResponseBody;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@PropertySource("classpath:application.properties")
@Service
public class ConferenceService {
    private final ConferenceRepo conferenceRepo;
    private final UserService userService;
    private final String conferenceServiceUrl;
    private final String conferenceServiceCreateHandle;
    private final String conferenceServiceDeleteHandle;
    private final String conferenceServiceToken;

    @Autowired
    public ConferenceService(
            ConferenceRepo conferenceRepo,
            UserService userService,
            @Value("${conference-service.url}") String conferenceServiceUrl,
            @Value("${conference-service.create-handle}") String conferenceServiceCreateHandle,
            @Value("${conference-service.delete-handle}") String conferenceServiceDeleteHandle,
            @Value("${conference-service.token}") String conferenceServiceToken
    ) {
        this.conferenceRepo = conferenceRepo;
        this.userService = userService;
        this.conferenceServiceUrl = conferenceServiceUrl;
        this.conferenceServiceCreateHandle = conferenceServiceCreateHandle;
        this.conferenceServiceDeleteHandle = conferenceServiceDeleteHandle;
        this.conferenceServiceToken = conferenceServiceToken;
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Conference create(Conference conference, UserDetailsImp userDetails) {
        User user = userService.getById(userDetails.getId());

        conference.setUser(user);

        conference = conferenceRepo.save(conference);

        try {
            RestTemplate restTemplate = new RestTemplate();

            ConferenceRequestBody body = new ConferenceRequestBody(conference.getId());
            URI uri = new URI(conferenceServiceUrl + conferenceServiceCreateHandle);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(conferenceServiceToken);

            RequestEntity<ConferenceRequestBody> requestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, uri);
            ResponseEntity<ConferenceResponseBody> response = restTemplate.exchange(requestEntity, ConferenceResponseBody.class);

            HttpStatus responseCode = response.getStatusCode();
            if (responseCode != HttpStatus.OK) {
                throw new IOException("Media service response code: " + responseCode);
            }
            ConferenceResponseBody responseBody = response.getBody();
            if (responseBody == null) {
                throw new IOException("Media service response has no body");
            }
            String roomId = responseBody.getRoomId();
            if (roomId == null) {
                throw new IOException("Media service response body has null roomId");
            }

            conference.setToken(roomId);
            return conference;
        } catch (Exception e) {
            conferenceRepo.delete(conference);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't create conference room: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Conference delete(Long id) {
        Conference conference = getById(id);

        try {
            RestTemplate restTemplate = new RestTemplate();

            ConferenceRequestBody body = new ConferenceRequestBody(conference.getId());
            URI uri = new URI(conferenceServiceUrl + conferenceServiceDeleteHandle);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(conferenceServiceToken);

            RequestEntity<ConferenceRequestBody> requestEntity = new RequestEntity<>(body, headers, HttpMethod.DELETE, uri);
            ResponseEntity<Void> response = restTemplate.exchange(requestEntity, Void.class);

            HttpStatus responseCode = response.getStatusCode();
            if (responseCode != HttpStatus.OK) {
                throw new IOException("Media service response code: " + responseCode);
            }

            conferenceRepo.delete(conference);
            return conference;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't delete conference room: " + e.getMessage(), e);
        }
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

    public Set<Conference> getByTeacherId(UserDetailsImp userDetails) {
        User user = userService.getById(userDetails.getId());
        return conferenceRepo.findAllByUserEquals(user);
    }
}
