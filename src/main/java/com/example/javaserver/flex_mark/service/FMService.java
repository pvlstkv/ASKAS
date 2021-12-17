package com.example.javaserver.flex_mark.service;

import com.example.javaserver.common_data.model.Mark;
import com.example.javaserver.flex_mark.controller.mapper.FMConfigPerCriteriaMapper;
import com.example.javaserver.flex_mark.controller.mapper.FlexMarkMapper;
import com.example.javaserver.flex_mark.model.FMConfigPerCriteria;
import com.example.javaserver.flex_mark.model.FlexMark;
import com.example.javaserver.flex_mark.model.result.FMPerCriteria;
import com.example.javaserver.flex_mark.model.result.FlexMarkPerUser;
import com.example.javaserver.flex_mark.repo.FlexMarkRepo;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.journal.model.Journal;
import com.example.javaserver.journal.repo.JournalRepo;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.repo.TaskRepo;
import com.example.javaserver.study.repo.WorkRepo;
import com.example.javaserver.user.repo.UserRepo;
import com.example.javaserver.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FMService {

    private final UserService userService;
    private final FlexMarkMapper flexMarkMapper;

    private final FlexMarkRepo flexMarkRepo;
    private final JournalRepo journalRepo;
    private final TaskRepo taskRepo;
    private final WorkRepo workRepo;

    @Transactional
    public void save(FlexMark flexMark, UserDetailsImp userDetailsImp) {
        if (flexMark.getTeacher() == null) {
            flexMark.setTeacher(userService.getById(userDetailsImp.getId()));
        }
        flexMarkRepo.save(flexMark);
    }

    @Transactional
    public void update(FlexMark flexMarkToSave) {
        var optionalFlexMark = flexMarkRepo.findById(flexMarkToSave.getId());
        if (optionalFlexMark.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("Конфигурации гибких оценок с id {0} не существует", flexMarkToSave.getId()));
        }
        var flexMark = flexMarkMapper.entityToEntity(optionalFlexMark.get());
        flexMarkRepo.save(flexMark);
    }

    public FlexMark getConfig(Integer teacherId, Long subjectSemesterId, Long studyGroupId) {
        var config = flexMarkRepo.findByTeacherIdAndStudyGroupIdAndSubjectSemesterId(
                teacherId, studyGroupId, subjectSemesterId);
        if (config.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("Конфигурации с id {0} учителя, id {1} семестра," +
                            " id {2} группы не найдено", teacherId, subjectSemesterId, studyGroupId));
        }
        return config.get();
    }

    public FlexMarkPerUser formFlexMark(Integer studentId, Long subjectSemesterId) {
        var studyGroupId = userService.getById(studentId).getStudyGroup().getId();
        var flexMark = flexMarkRepo.findBySubjectSemesterIdAndStudyGroupId(subjectSemesterId, studyGroupId);
        if (flexMark.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("Конфигурации с id {0} семестра," +
                            " id {1} группы не найдено", subjectSemesterId, studyGroupId));
        }
        FlexMarkPerUser flexMarkPerUser = new FlexMarkPerUser();

        var visits = formVisitsMark(studentId, subjectSemesterId, flexMark.get());
        flexMarkPerUser.setVisit(visits);

        var tasks = formTasksMark(studentId, subjectSemesterId, flexMark.get().getTaskMark());
        flexMarkPerUser.setTaskMark(tasks);
        return flexMarkPerUser;
    }

    private FMPerCriteria formTasksMark(Integer studentId, Long subjectSemesterId, FMConfigPerCriteria config) {
        var tasks = taskRepo.findAllBySemestersId(subjectSemesterId);
        int totalScore = 0;
        int passedScore = 0;
        List<Long> workIds = new ArrayList<>();
        for (Task t : tasks) {
            var studentWorks = workRepo.findAllByUserIdAndTaskId(studentId, t.getId());
            var bestStudentWork = findBest(studentWorks);
            if (bestStudentWork.isPresent()) {
                workIds.add(bestStudentWork.get().getId());
                if (bestStudentWork.get().getMark().equals(Mark.PASSED)) {
                    passedScore += config.getPassedValue();
                    totalScore += config.getPassedValue();
                    continue;
                } else if (bestStudentWork.get().getMark().equals(Mark.NOT_PASSED)) {
                    passedScore += Mark.UNSATISFACTORILY.getValue();
                } else {
                    passedScore += bestStudentWork.get().getMark().getValue();
                }
            }
            totalScore += Mark.FIVE.getValue();
        }
        FMPerCriteria fmPerCriteria = new FMPerCriteria();
        fmPerCriteria.setDone(workIds.size());
        fmPerCriteria.setTotal(tasks.size());
        fmPerCriteria.setIds(workIds);
        double successPercentage = passedScore * 100.0 / totalScore;
        setMark(fmPerCriteria, config, successPercentage);
        return fmPerCriteria;
    }

    private Optional<Work> findBest(List<Work> tasks) {
        return tasks.stream().max((f, s) -> {
            if (f.getMark().getValue() > s.getMark().getValue()) {
                return 1;
            }
            return -1;
        });
    }

    private FMPerCriteria formVisitsMark(Integer studentId, Long subjectSemesterId, FlexMark flexMark) {
        var student = userService.getById(studentId);
        var studyGroup = student.getStudyGroup();
        var journals = journalRepo.findAllBySubjectSemesterIdAndStudyGroupId(
                subjectSemesterId, studyGroup.getId());
        if (journals.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    MessageFormat.format("Журнала группы с id {0} за семестр с id {1} не существует",
                            studyGroup.getId(), subjectSemesterId));
        }
        int total = journals.size();
        int visitedCount = 0;
        for (Journal j : journals) {
            visitedCount += j.getVisits().stream().filter(
                    it -> it.getIsVisited() && it.getUser().getId().equals(studentId)).count();
        }

        FMPerCriteria fmPerCriteria = new FMPerCriteria();
        fmPerCriteria.setDone(visitedCount);
        fmPerCriteria.setTotal(total);
        fmPerCriteria.setIds(journals.stream().map(Journal::getId).collect(Collectors.toList()));
        var visitConfig = flexMark.getVisit();
        double successPercentage = visitedCount * 100.0 / total;

        setMark(fmPerCriteria, visitConfig, successPercentage);
        return fmPerCriteria;
    }


    private void setMark(FMPerCriteria fmPerCriteria, FMConfigPerCriteria fmConfig, double successPercentage) {
        if (fmConfig.getIsBinaryMark()) {
            if (fmConfig.getBinaryBorder() < successPercentage) {
                fmPerCriteria.setMark(fmConfig.getPassedValue());
            } else {
                fmPerCriteria.setMark(2);// оценка 2
            }
        } else {
            if (fmConfig.getTwoThreeBorder() > successPercentage) {
                fmPerCriteria.setMark(2);
            } else if (fmConfig.getThreeFourBorder() > successPercentage) {
                fmPerCriteria.setMark(3);
            } else if (fmConfig.getFourFiveBorder() > successPercentage) {
                fmPerCriteria.setMark(4);
            } else {
                fmPerCriteria.setMark(5);
            }
        }
    }

}
