package com.example.javaserver.journal.service;

import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.journal.controller.dto.UserVisitDto;
import com.example.javaserver.journal.controller.mapper.PagedJournal;
import com.example.javaserver.journal.controller.mapper.UserVisit;
import com.example.javaserver.journal.controller.mapper.VisitMapper;
import com.example.javaserver.journal.model.Journal;
import com.example.javaserver.journal.model.Visit;
import com.example.javaserver.journal.repo.JournalRepo;
import com.example.javaserver.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JournalService {
    private final JournalRepo journalRepo;
    private final UserService userService;
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final VisitMapper visitMapper;

    @Transactional
    public void saveJournal(Journal journal, UserDetailsImp userDetailsImp) {
        if (journal.getTeacher() == null) {
            journal.setTeacher(userService.getById(userDetailsImp.getId()));
        }
        journalRepo.save(journal);
    }

    @Transactional
    public void updateJournal(Journal journalToSave, UserDetailsImp userDetailsImp) {
        Optional<Journal> optionalJournal = journalRepo.findById(journalToSave.getId());
        if (optionalJournal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("Журнала с id {0} не существует", journalToSave.getId()));
        }
        Journal journal = optionalJournal.get();
        journal.setComment(journalToSave.getComment());
        journal.setStudyGroup(journalToSave.getStudyGroup());
        journal.setSubjectSemester(journalToSave.getSubjectSemester());
        journal.setTeacher(journalToSave.getTeacher());
        journal.putNewVisits(journalToSave.getVisits());
        journalRepo.save(journal);
    }

    public List<UserVisit> getByStudentIdAndSubjectId(Integer studentId, Long subjectId) {
        var optionalSubjectSemester = subjectSemesterRepo.findBySubjectId(subjectId).get(0);
        /*if (optionalSubjectSemester.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("У предмета с id {0} не существует одного семестра", subjectId));
        }*/
        var journals = journalRepo.findAllBySubjectSemesterId(optionalSubjectSemester.getId());
        if (journals.size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("У предмета с id {0} не существует журнала", subjectId));
        }
        List<UserVisit> studentVisits = new ArrayList<>();
        for (Journal j : journals) {
            var oneVisit = j.getVisits().stream().
                    filter(it -> it.getUser().getId().equals(studentId)).findFirst();
            oneVisit.ifPresent(visit -> studentVisits.add(new UserVisit(visit, j.getLessonDate())));
        }
        Collections.reverse(studentVisits);
        return studentVisits;
    }

    public PagedJournal getBySemesterIdAndGroupId(Long semesterId, Long groupId,
                                                  Long timeAfter, Long timeBefore,
                                                  Long pageNumber, Long pageSize) {
        if (pageSize < 1 || pageNumber < 1) {
            return getNotPagedJournal(semesterId, groupId, timeAfter, timeBefore);
        }
        return getPagedJournal(semesterId, groupId, timeAfter, timeBefore, pageNumber, pageSize);
    }

    private PagedJournal getNotPagedJournal(Long semesterId, Long groupId,
                                            Long timeAfter, Long timeBefore) {
        OffsetDateTime after = getTimeAfter(timeAfter);
        OffsetDateTime before = getTimeBefore(timeBefore);

        var journals = journalRepo.findAllBySubjectSemesterIdAndStudyGroupIdAndCreatedDateAfterAndCreatedDateBefore(
                semesterId, groupId, after, before);
        return new PagedJournal(journals);
    }

    private PagedJournal getPagedJournal(Long semesterId, Long groupId, Long timeAfter, Long timeBefore, Long pageNumber, Long pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber.intValue() - 1, pageSize.intValue(), // -1 because page starts from 0
                Sort.by("createdDate").descending());
        Page<Journal> journals;
        if (timeAfter != null || timeBefore != null) {
            OffsetDateTime after = getTimeAfter(timeAfter);
            OffsetDateTime before = getTimeBefore(timeBefore);
            journals = journalRepo.
                    findAllBySubjectSemesterIdAndStudyGroupIdAndCreatedDateAfterAndCreatedDateBefore(
                            semesterId, groupId, after, before, pageRequest);
        } else {
            journals = journalRepo.findBySubjectSemesterIdAndStudyGroupId(semesterId, groupId, pageRequest);
        }
        checkJournalSize(journals.getContent(), semesterId, groupId, timeAfter, timeBefore);
        checkPageNumber(journals, pageNumber);
        return new PagedJournal(journals.getContent(),
                (long) journals.getPageable().getPageNumber() + 1,// it starts from 0
                (long) journals.getTotalPages());
    }

    private void checkPageNumber(Page<Journal> journals, Long pageNumber) {
        if (journals.getTotalPages() < pageNumber) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("Страницы с номером {0} не сущетсвует", pageNumber));
        }
    }

    private void checkJournalSize(List<Journal> journals, Long semesterId, Long groupId, Long timeAfter, Long timeBefore) {
        if (journals.size() < 1) {
            String answer = formAnswer(timeAfter, timeBefore);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format(answer, semesterId, groupId));
        }
    }

    private String formAnswer(Long timeAfter, Long timeBefore) {
        StringBuilder answer = new StringBuilder("Журнала для группы с id {0} и семестра с id {1} во временном промежутке ");
        answer.append("c ");
        if (timeAfter != null) {
            String after = getTimeAfter(timeAfter).toString();
            answer.append(after);
        } else {
            answer.append("момента сотворения мира");
        }
        answer.append(" по ");
        if (timeBefore != null) {
            String before = getTimeBefore(timeBefore).toString();
            answer.append(before);
        } else {
            answer.append(" настоящий момент ");
        }
        answer.append(" не существует.");
        return answer.toString();
    }

    private OffsetDateTime getTimeAfter(Long timeAfter) {
        // if timeAfter is null that means to find journals before timeBefore. after will be 01.01.1970 00:00:00
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(
                timeAfter != null ? timeAfter : 0L), ZoneId.systemDefault());
    }

    private OffsetDateTime getTimeBefore(Long timeBefore) {
        // if timeBefore is null that means to find journals after timeAfter. before will be current time
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(
                timeBefore != null ? timeBefore : System.currentTimeMillis()), ZoneId.systemDefault());
    }

}
