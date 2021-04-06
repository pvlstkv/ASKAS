package com.example.javaserver.academic_performance.service;

import com.example.javaserver.academic_performance.model.Performance;
import com.example.javaserver.academic_performance.model.Progress;
import com.example.javaserver.common_data.model.Mark;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.service.SubjectService;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.TaskType;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.service.TaskService;
import com.example.javaserver.testing.model.dto.PassedTestOut;
import com.example.javaserver.testing.model.dto.PassedThemeOut;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class PerformanceService {

    private final SubjectService subjectService;
    private final ResultService resultService;
    private final TaskService taskService;
    private final ThemeRepo themeRepo;

    @Autowired
    public PerformanceService(SubjectService subjectService, ResultService resultService, TaskService taskService, ThemeRepo themeRepo) {
        this.subjectService = subjectService;
        this.resultService = resultService;
        this.taskService = taskService;
        this.themeRepo = themeRepo;
    }

    public Performance formUserPerformance(Integer userId, UserDetailsImp userDetails) {
        Performance performance = new Performance();
        Progress testProgress = formTestPerformance(userId, userDetails);
        performance.setTests(testProgress);

        List<Progress> taskProgresses = formTaskPerformance(userId, userDetails);
        performance.setLabs(taskProgresses.get(0));
        performance.setPractices(taskProgresses.get(1));
        performance.setEssays(taskProgresses.get(2));
        return performance;
    }

    public Progress formTestPerformance(Integer userId, UserDetailsImp userDetails) {
        Collection<Subject> subjects = subjectService.searchByStudentId(userId, userDetails);
        int done = 0;
        int total = 0;
        for (Subject subject : subjects) {
            total += themeRepo.findAllBySubjectId(subject.getId()).size();
            List<PassedThemeOut> passedThemeOuts = resultService.fetchUserPassedThemesBySubjectIdAndUserId(userId, subject.getId(), userDetails);
            for (PassedThemeOut passedThemeOut : passedThemeOuts) {
                int border = 50;
                if (passedThemeOut.haveOneNormalRating(border)) {
                    done++;
                }
            }
        }
        return new Progress(done, total);
    }

    public List<Progress> formTaskPerformance(Integer userId, UserDetailsImp userDetails) {
        Collection<Subject> subjects = subjectService.searchByStudentId(userId, userDetails);
        Progress labProgress = new Progress();
        Progress practiceProgress = new Progress();
        Progress essayProgress = new Progress();
        for (Subject subject : subjects) {
            Collection<Task> tasks = taskService.searchBySubjectAndStudent(subject.getId(), userId, userDetails);
            for (Task task : tasks) {
                if (task.getType() == TaskType.LAB) {
                    labProgress = treat(task.getWorks(), labProgress);
                } else if (task.getType() == TaskType.PRACTICE) {
                    practiceProgress = treat(task.getWorks(), practiceProgress);
//                    if  (task.getType() == TaskType.ESSAY)
                } else {
                    essayProgress = treat(task.getWorks(), essayProgress);
                }
            }
        }
        return new ArrayList<>(Arrays.asList(labProgress, practiceProgress, essayProgress));
    }

    private Progress treat(List<Work> works, Progress oldProgress) {
        Progress newProgress = new Progress(oldProgress);
        for (Work work : works) {
            if (!(work.getMark() == Mark.NOT_PASSED || work.getMark() == Mark.UNSATISFACTORILY)) {
                newProgress.setDone(oldProgress.getDone() + 1);
                break;
            }
        }
        newProgress.setTotal(oldProgress.getTotal() + 1);
        return newProgress;
    }
}

