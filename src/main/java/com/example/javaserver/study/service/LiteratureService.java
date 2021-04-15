package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.controller.dto.LiteratureIn;
import com.example.javaserver.study.model.Literature;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.repo.LiteratureRepo;
import com.example.javaserver.study.repo.UserFileRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service
public class LiteratureService {
    private final LiteratureRepo literatureRepo;
    private final SubjectSemesterRepo semesterRepo;
    private final UserRepo userRepo;
    private final SubjectRepo subjectRepo;
    private final UserFileRepo userFileRepo;

    @Autowired
    public LiteratureService(LiteratureRepo literatureRepo, SubjectSemesterRepo semesterRepo, UserRepo userRepo, SubjectRepo subjectRepo, UserFileRepo userFileRepo) {
        this.literatureRepo = literatureRepo;
        this.semesterRepo = semesterRepo;
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
        this.userFileRepo = userFileRepo;
    }

    @Transactional
    public Message create(LiteratureIn literatureIn, UserDetailsImp userDetails) {
        Set<SubjectSemester> semesters = semesterRepo.findAllByIdIn(literatureIn.semesterIds);
        Collection<Long> notFoundLiteratureIds = literatureIn
                .semesterIds.stream()
                .filter(i -> semesters.stream()
                                .map(SubjectSemester::getId)
                                .noneMatch(si -> si.equals(i)))
                .collect(Collectors.toSet());
        if (!notFoundLiteratureIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестры с id: " + notFoundLiteratureIds + " не найдены");
        }

        Set<UserFile> files = userFileRepo.findAllByIdIn(literatureIn.fileIds);
        Collection<Long> notFoundFileIds = literatureIn
                .fileIds.stream()
                .filter(i -> files.stream()
                                .map(UserFile::getId)
                                .noneMatch(si -> si.equals(i)))
                .collect(Collectors.toSet());
        if (!notFoundFileIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файлы с id: " + notFoundFileIds + " не найдены");
        }

        Optional<User> user = userRepo.findById(userDetails.getId());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId токена инвалидный");
        }

        Literature literature = new Literature();
        literature.setTitle(literatureIn.title);
        literature.setAuthors(literatureIn.authors);
        literature.setDescription(literatureIn.description);
        literature.setType(literatureIn.type);
        literature.setUser(user.get());
        literature.setSemesters(semesters);
        literature.setFiles(files);
        files.forEach(UserFile::incLinkCount);

        literatureRepo.save(literature);

        return new Message("Литература успешно создана");
    }

    public Message delete(Set<Long> ids) {
        literatureRepo.deleteAllByIdIn(ids);
        return new Message("Найденные задания были успешно удалены");
    }

    @Transactional
    public Message update(Long id, LiteratureIn literatureIn) {
        Optional<Literature> literatureO = literatureRepo.findById(id);
        if (literatureO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литература с указанным id не найдена");
        }
        Literature literature = literatureO.get();

        Set<UserFile> filesToRemove = literature
                .getFiles().stream()
                .filter(f -> !literatureIn.fileIds.contains(f.getId()))
                .collect(Collectors.toSet());

        Set<UserFile> filesToAdd = new HashSet<>();
        Set<Long> fileToAddIds = literatureIn
                .fileIds.stream()
                .filter(i -> literature.getFiles().stream().noneMatch(f -> f.getId().equals(i)))
                .collect(Collectors.toSet());
        if (!fileToAddIds.isEmpty()) {
            filesToAdd.addAll(userFileRepo.getUserFilesByIdIn(fileToAddIds));
            Collection<Long> notFoundFileIds = fileToAddIds.stream()
                    .filter(i -> filesToAdd.stream()
                            .map(UserFile::getId)
                            .noneMatch(si -> si.equals(i)))
                    .collect(Collectors.toSet());
            if (!notFoundFileIds.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файлы с id: " + notFoundFileIds + " не найдены");
            }
        }

        Set<SubjectSemester> semestersToRemove = literature
                .getSemesters().stream()
                .filter(s -> !literatureIn.semesterIds.contains(s.getId()))
                .collect(Collectors.toSet());

        Set<SubjectSemester> semestersToAdd = new HashSet<>();
        Set<Long> semesterToAddIds = literatureIn
                .semesterIds.stream()
                .filter(i -> literature.getSemesters().stream().noneMatch(s -> s.getId().equals(i)))
                .collect(Collectors.toSet());
        if (!semesterToAddIds.isEmpty()) {
            semestersToAdd.addAll(semesterRepo.findAllByIdIn(semesterToAddIds));
            Collection<Long> notFoundSemesterIds = semesterToAddIds.stream()
                    .filter(i -> semestersToAdd.stream()
                            .map(SubjectSemester::getId)
                            .noneMatch(si -> si.equals(i)))
                    .collect(Collectors.toSet());
            if (!notFoundSemesterIds.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестры с id: " + notFoundSemesterIds + " не найдены");
            }
        }

        literature.setTitle(literatureIn.title);
        literature.setAuthors(literatureIn.authors);
        literature.setDescription(literatureIn.description);
        literature.setType(literatureIn.type);

        literature.getFiles().removeAll(filesToRemove);
        filesToRemove.forEach(UserFile::decLinkCount);

        literature.getFiles().addAll(filesToAdd);
        filesToAdd.forEach(UserFile::incLinkCount);

        literature.getSemesters().removeAll(semestersToRemove);

        literature.getSemesters().addAll(semestersToAdd);

        return new Message("Литература была успешно изменена");
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
