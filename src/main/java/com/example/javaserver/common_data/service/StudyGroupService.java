package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.StudyGroupIO;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
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
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public StudyGroupService(StudyGroupRepo studyGroupRepo, SubjectSemesterRepo subjectSemesterRepo, UserRepo userRepo, DepartmentRepo departmentRepo) {
        this.studyGroupRepo = studyGroupRepo;
        this.subjectSemesterRepo = subjectSemesterRepo;
        this.userRepo = userRepo;
        this.departmentRepo = departmentRepo;
    }


    public ResponseEntity<?> create(StudyGroupIO studyGroupIO) {
        StudyGroup studyGroup = new StudyGroup(studyGroupIO);
        Optional<Department> department = departmentRepo.findByShortName(studyGroupIO.getShortName());
        if(studyGroupRepo.existsByShortName(studyGroupIO.getShortName())){
            return new ResponseEntity<>(new Message("Ошибка, данная группа уже существует"), HttpStatus.BAD_REQUEST);
        }
        if(department.isPresent()){
            studyGroup.setDepartment(department.get());
        }else {
            return new ResponseEntity<>(new Message("Ошибка, данного департамента не существует"), HttpStatus.BAD_REQUEST);
        }
        try {
            studyGroupRepo.save(studyGroup);
            return new ResponseEntity<>(new Message("Учебная группа успешно создана"), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new Message("Ошибка сервера"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> get(String nameGroup){
        Optional<StudyGroup> studyGroup = studyGroupRepo.findByShortName(nameGroup);
        if(studyGroup.isPresent()){
            StudyGroupIO studyGroupIO = new StudyGroupIO(studyGroup.get());
            return new ResponseEntity<>(studyGroupIO, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Message("Такой группы не существует"), HttpStatus.BAD_REQUEST);
        }
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

    @Transactional
    public ResponseEntity<?> addSubjectSemesters(Long studyGroupId, Set<Long> subjectSemesterIds) {
        Optional<StudyGroup> group = studyGroupRepo.findById(studyGroupId);
        if (!group.isPresent()) {
            return new ResponseEntity<>(new Message("Учебная группа с указанным id не найдена"), HttpStatus.BAD_REQUEST);
        }

        Set<SubjectSemester> subjectSemesters = subjectSemesterRepo.findSubjectSemestersByIdIn(subjectSemesterIds);
        group.get().getSubjectSemesters().addAll(subjectSemesters);
        return new ResponseEntity<>(new Message("Пользователи были добавлены в группу"), HttpStatus.OK);
    }
}
