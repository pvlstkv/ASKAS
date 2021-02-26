package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class StudyGroupService {
    private final StudyGroupRepo studyGroupRepo;
    private final UserRepo userRepo;

    @Autowired
    public StudyGroupService(StudyGroupRepo studyGroupRepo, UserRepo userRepo) {
        this.studyGroupRepo = studyGroupRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> create(StudyGroup studyGroup) {
        studyGroupRepo.save(studyGroup);
        return new ResponseEntity<>(new Message("Учебная группа успешно создана"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> enroll(Long studyGroupId, Set<Integer> userIds) {
        Optional<StudyGroup> group = studyGroupRepo.findById(studyGroupId);
        if (!group.isPresent()) {
            return new ResponseEntity<>(new Message("Учебная группа с указанным id не найдена"), HttpStatus.BAD_REQUEST);
        }

        Set<User> users = userRepo.getUsersByIdIn(userIds);
        group.get().getStudents().addAll(users);
        return new ResponseEntity<>(new Message("Пользователи были добавлены в группу"), HttpStatus.OK);
    }
}
