package com.example.javaserver.journal.service;

import com.example.javaserver.journal.controller.dto.VisitDto;
import com.example.javaserver.journal.model.Journal;
import com.example.javaserver.journal.model.Visit;
import com.example.javaserver.journal.repo.JournalRepo;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JournalService {
    private final JournalRepo journalRepo;
    private final UserService userService;

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
        journal.setTeacher(journalToSave.getTeacher());
        journal.setVisits(journalToSave.getVisits());
        journalRepo.save(journal);
    }

    public List<Journal> getBySemesterIdAndGroupId(Long semesterId, Long groupId) {
        List<Journal> journals = journalRepo.findBySubjectSemesterIdAndStudyGroupId(semesterId, groupId);
        if (journals.size() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("Журнала для группы с id {0} и семестра с id {1} не существует",
                            new Object[]{semesterId, groupId}));
        }
        return journals;
    }
}
