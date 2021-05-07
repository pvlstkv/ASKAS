package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.service.UserDataService;
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
    private final StudyGroupDataService studyGroupDataService;
    private final SubjectSemesterDataService findSubjectSemestersByIdIn;
    private final SubjectDataService subjectDataService;
    private final UserDataService userDataService;

    @Autowired
    public StudyGroupService(StudyGroupDataService studyGroupDataService, SubjectSemesterDataService findSubjectSemestersByIdIn, SubjectDataService subjectDataService, UserDataService userDataService) {
        this.findSubjectSemestersByIdIn = findSubjectSemestersByIdIn;
        this.studyGroupDataService = studyGroupDataService;
        this.subjectDataService = subjectDataService;
        this.userDataService = userDataService;
    }

    public StudyGroup create(StudyGroup studyGroup) {
        return studyGroupDataService.save(studyGroup);
    }

    public StudyGroup getById(Long id) {
        return studyGroupDataService.findByIdEquals(id);
    }

    public Set<StudyGroup> getAll() {
        return studyGroupDataService.getAll();
    }

    public Set<StudyGroup> getByIds(Set<Long> ids) {
        Set<StudyGroup> groups = studyGroupDataService.findAllByIdIn(ids);
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
        StudyGroup group = studyGroupDataService.getById(studyGroupId);
        Set<User> users = userDataService.getUsersByIdIn(userIds);
        group.getStudents().addAll(users);
        return new Message("Пользователи были добавлены в группу");
    }

    @Transactional
    public Message addSubjectSemesters(Long studyGroupId, Set<Long> subjectSemesterIds) {
        StudyGroup group = studyGroupDataService.getById(studyGroupId);

        Set<SubjectSemester> subjectSemesters = findSubjectSemestersByIdIn.findSubjectSemestersByIdIn(subjectSemesterIds);
        group.getSubjectSemesters().addAll(subjectSemesters);
        return new Message("Семестры были успешно добавлены для группы");
    }

    public Collection<StudyGroup> getGroupsByTeacher(Integer userId, UserDetailsImp userDetails){
        if (userId == null) {
            userId = userDetails.getId();
        }

        User user = userDataService.getById(userId);
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
        Subject subject = subjectDataService.getById(subjectId);
        return subject.getSemesters()
                .stream()
                .map(SubjectSemester::getStudyGroup)
                .collect(Collectors.toSet());
    }
}
