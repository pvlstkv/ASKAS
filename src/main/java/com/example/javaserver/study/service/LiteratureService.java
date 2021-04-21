package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.model.Literature;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.repo.LiteratureRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service
public class LiteratureService {
    private final LiteratureRepo literatureRepo;
    private final UserRepo userRepo;
    private final SubjectRepo subjectRepo;

    @Autowired
    public LiteratureService(LiteratureRepo literatureRepo, UserRepo userRepo, SubjectRepo subjectRepo) {
        this.literatureRepo = literatureRepo;
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
    }

    @Transactional
    public Literature create(Literature literature, UserDetailsImp userDetails) {
        User user = userRepo.getOne(userDetails.getId());

        literature.setUser(user);
        literature.getFiles().forEach(UserFile::incLinkCount);

        return literatureRepo.save(literature);
    }

    public Message delete(Set<Long> ids) {
        literatureRepo.deleteAllByIdIn(ids);
        return new Message("Найденные задания были успешно удалены");
    }

    @Transactional
    public Literature update(Long id, Literature literatureToPut) {
        Optional<Literature> literatureO = literatureRepo.findById(id);
        if (literatureO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литература с указанным id не найдена");
        }
        Literature literature = literatureO.get();

        Set<SubjectSemester> semesters = literatureToPut.getSemesters();

        Set<UserFile> files = literatureToPut.getFiles();
        literature.getFiles().forEach(UserFile::decLinkCount);
        files.forEach(UserFile::incLinkCount);

        literature.setSemesters(semesters);
        literature.setType(literatureToPut.getType());
        literature.setTitle(literatureToPut.getTitle());
        literature.setAuthors(literatureToPut.getAuthors());
        literature.setDescription(literatureToPut.getDescription());
        literature.setFiles(files);

        return literature;
    }

    public  Collection<Literature> getAll() {
        return literatureRepo.findAll(null);
    }

    public Collection<Literature> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Literature> specification = CommonSpecification.of(criteria);
            return literatureRepo.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public  Collection<Literature> searchByIds(Set<Long> ids) {
        return literatureRepo.findAllByIdIn(ids);
    }

    public Collection<Literature> searchBySubjectAndStudent(Long subjectId, Integer userId, UserDetailsImp userDetails) {
        if (userId == null) {
            userId = userDetails.getId();
        }

        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id пользователя инвалидный");
        }
        if (!user.get().getRole().equals(UserRole.USER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является студентом");
        }

        StudyGroup group = user.get().getStudyGroup();
        if (group == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Студент не привязан к группе");
        }

        Set<SubjectSemester> semesters = group.getSubjectSemesters();
        if (semesters.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Группа, в которой состоит студент не имеет семестров");
        }

        Optional<SubjectSemester> semester = semesters.stream().filter(s -> subjectId.equals(s.getSubject().getId())).findFirst();
        if (semester.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмета с указанным id нет у пользователя");
        }

        return semester.get().getLiterature();
    }

    public Collection<Literature> searchBySubject(Long subjectId) {
        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if (subject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмета с указанным id не существует");
        }

        return subject.get()
                .getSemesters().stream()
                .flatMap(sem -> sem.getLiterature().stream())
                .collect(Collectors.toSet());
    }
}
