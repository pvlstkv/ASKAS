package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StudyGroupService {
    private final StudyGroupRepo studyGroupRepo;

    @Autowired
    public StudyGroupService(StudyGroupRepo studyGroupRepo) {
        this.studyGroupRepo = studyGroupRepo;
    }

    @Transactional
    public ResponseEntity<?> create(StudyGroup studyGroup) {
        studyGroupRepo.save(studyGroup);
        return new ResponseEntity<>(new Message("Учебная группа успешно создана"), HttpStatus.OK);
    }
}
