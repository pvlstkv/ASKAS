package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.StudyGroupI;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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


    public ResponseEntity<?> create(StudyGroupI studyGroupI) {
        StudyGroup studyGroup = new StudyGroup(studyGroupI);
        Optional<Department> department = departmentRepo.findById(studyGroupI.getIdDepartment());
        if(studyGroupRepo.existsByShortName(studyGroupI.getShortName())){
            return new ResponseEntity<>(new Message("Ошибка, данная группа уже существует"), HttpStatus.BAD_REQUEST);
        }
        if(department.isPresent()){
            studyGroup.setDepartment(department.get());
        }else {
            return new ResponseEntity<>(new Message("Ошибка, данного департамента не существует"), HttpStatus.BAD_REQUEST);
        }
        studyGroupRepo.save(studyGroup);
        return new ResponseEntity<>(new Message("Учебная группа успешно создана"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> get(Long id){
        if(id != null){
            Optional<StudyGroup> studyGroup = studyGroupRepo.findById(id);
            if(!studyGroup.isPresent()){
                return new ResponseEntity<>(new Message("Такой группы не существует"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(studyGroup.get(), HttpStatus.OK);
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
        return new ResponseEntity<>(new Message("Семестры были успешно добавлены для группы"), HttpStatus.OK);
    }

    public ResponseEntity<?> getGroupsByUser(Integer userId, UserContext userContext){
        if (userId == null) {
            userId = userContext.getUserId();
        }

        Optional<User> userO = userRepo.findById(userId);
        if (!userO.isPresent()) {
            return new ResponseEntity<>(new Message("Нет пользователя с указанным (явно или по токену) id"), HttpStatus.BAD_REQUEST);
        }
        User user = userO.get();

        if (!user.getRole().equals(UserRole.TEACHER)) {
            return new ResponseEntity<>(new Message("Пользователь не является преподавателем"), HttpStatus.BAD_REQUEST);
        }

        Collection<StudyGroup> groups = user
                .getTeachingSubjects().stream()
                .flatMap(sub -> sub.getSemesters().stream())
                .flatMap(sem -> sem.getStudyGroups().stream())
                .collect(Collectors.toSet());

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
}
