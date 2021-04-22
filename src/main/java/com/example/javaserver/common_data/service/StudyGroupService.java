package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.StudyGroupI;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
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
public class StudyGroupService {
    private final StudyGroupRepo studyGroupRepo;
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final SubjectRepo subjectRepo;
    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public StudyGroupService(StudyGroupRepo studyGroupRepo, SubjectSemesterRepo subjectSemesterRepo, SubjectRepo subjectRepo, UserRepo userRepo, DepartmentRepo departmentRepo) {
        this.studyGroupRepo = studyGroupRepo;
        this.subjectSemesterRepo = subjectSemesterRepo;
        this.subjectRepo = subjectRepo;
        this.userRepo = userRepo;
        this.departmentRepo = departmentRepo;
    }


    public Message create(StudyGroupI studyGroupI) {
        StudyGroup studyGroup = new StudyGroup(studyGroupI);
        Optional<Department> department = departmentRepo.findById(studyGroupI.getIdDepartment());
        if(studyGroupRepo.existsByShortName(studyGroupI.getShortName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка, данная группа уже существует");
        }
        if(department.isPresent()){
            studyGroup.setDepartment(department.get());
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка, данного департамента не существует");
        }
        studyGroupRepo.save(studyGroup);
        return new Message("Учебная группа успешно создана");
    }

    public StudyGroup getById(Long id) {
        Optional<StudyGroup> groupO = studyGroupRepo.findByIdEquals(id);
        if (groupO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Группы с указанным id не существует");
        }
        return groupO.get();
    }

    public Set<StudyGroup> getByIds(Set<Long> ids) {
        Set<StudyGroup> groups = studyGroupRepo.findAllByIdIn(ids);
        if (groups.size() == ids.size()) {
            return groups;
        } else {
            Collection<Long> foundIds = groups.stream()
                    .map(StudyGroup::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Группы с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    @Transactional
    public Message enroll(Long studyGroupId, Set<Integer> userIds) {
        Optional<StudyGroup> group = studyGroupRepo.findById(studyGroupId);
        if (group.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Учебная группа с указанным id не найдена");
        }

        Set<User> users = userRepo.getUsersByIdIn(userIds);
        group.get().getStudents().addAll(users);
        return new Message("Пользователи были добавлены в группу");
    }

    @Transactional
    public Message addSubjectSemesters(Long studyGroupId, Set<Long> subjectSemesterIds) {
        Optional<StudyGroup> group = studyGroupRepo.findById(studyGroupId);
        if (group.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Учебная группа с указанным id не найдена");
        }

        Set<SubjectSemester> subjectSemesters = subjectSemesterRepo.findSubjectSemestersByIdIn(subjectSemesterIds);
        group.get().getSubjectSemesters().addAll(subjectSemesters);
        return new Message("Семестры были успешно добавлены для группы");
    }

    public Collection<StudyGroup> getGroupsByTeacher(Integer userId, UserDetailsImp userDetails){
        if (userId == null) {
            userId = userDetails.getId();
        }

        Optional<User> userO = userRepo.findById(userId);
        if (userO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нет пользователя с указанным (явно или по токену) id");
        }
        User user = userO.get();

        if (!user.getRole().equals(UserRole.TEACHER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является преподавателем");
        }

        return user
                .getTeachingSubjects().stream()
                .flatMap(sub -> sub.getSemesters().stream())
                .map(SubjectSemester::getStudyGroup)
                .collect(Collectors.toSet());
    }

    public Collection<StudyGroup> getGroupsBySubject(Long subjectId){
        Optional<Subject> subjectO = subjectRepo.findById(subjectId);
        if (subjectO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нет предмета с указанным id");
        }
        Subject subject = subjectO.get();

        return subjectSemesterRepo
                .findAllBySubjectEquals(subject)
                .stream()
                .map(SubjectSemester::getStudyGroup)
                .collect(Collectors.toSet());
    }
}
