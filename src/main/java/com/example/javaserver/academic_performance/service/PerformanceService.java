package com.example.javaserver.academic_performance.service;

import com.example.javaserver.academic_performance.model.Performance;
import com.example.javaserver.academic_performance.model.Progress;
import com.example.javaserver.academic_performance.model.TaskPerformance;
import com.example.javaserver.academic_performance.model.TaskPerformancePerUser;
import com.example.javaserver.common_data.model.Mark;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.common_data.service.SubjectService;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.TaskType;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.repo.TaskRepo;
import com.example.javaserver.study.repo.WorkRepo;
import com.example.javaserver.study.service.TaskService;
import com.example.javaserver.testing.model.dto.PassedTestOut;
import com.example.javaserver.testing.model.dto.PassedThemeOut;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.service.ResultService;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    private final SubjectService subjectService;
    private final ResultService resultService;
    private final TaskService taskService;
    private final ThemeRepo themeRepo;
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final WorkRepo workRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    @Autowired
    public PerformanceService(SubjectService subjectService, ResultService resultService, TaskService taskService, ThemeRepo themeRepo, SubjectSemesterRepo subjectSemesterRepo, TaskRepo taskRepo, UserRepo userRepo, WorkRepo workRepo) {
        this.subjectService = subjectService;
        this.resultService = resultService;
        this.taskService = taskService;
        this.themeRepo = themeRepo;
        this.subjectSemesterRepo = subjectSemesterRepo;
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.workRepo = workRepo;
    }

    public TaskPerformance formTaskPerformanceByGroup(Long groupId, Long taskId) {
        //todo check teacher access by userdetails (if other teacher makes request, who doesn't made this task (doesn't teach this subject))
        List<User> users = userRepo.findAllByStudyGroupId(groupId);
        users = users.stream().sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                .collect(Collectors.toList());
        Optional<Task> task = taskRepo.findById(taskId);
        if (!task.isPresent()) {
            String response = String.format("Задание" + doesntExistById, taskId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, response);
        }
        List<TaskPerformancePerUser> performances = new ArrayList<>();
        int cumulativeMark = 0;
        int countOfPassedTasks = 0;
        for (User user : users) {
            TaskPerformancePerUser performancePerUser = new TaskPerformancePerUser(user);
            List<Work> works = workRepo.findAllByUserIdAndTaskId(user.getId(), taskId);
            Optional<Work> bestWork = works.stream()
                    .filter(it -> (it.getMark().getValue() != Mark.NOT_PASSED.getValue()))
                    .reduce((first, second) -> second);
            if (!bestWork.isPresent()) {
                performancePerUser.setWork(null);
                continue;
            }
            performancePerUser.setWork(bestWork.get());
            if (bestWork.get().getMark().getValue() == Mark.NOT_PASSED.getValue()) {
                continue;
            }

            if (bestWork.get().getMark().getValue() == Mark.PASSED.getValue()) {
                cumulativeMark += Mark.FIVE.getValue();
            } else {
                cumulativeMark += bestWork.get().getMark().getValue();
            }
            countOfPassedTasks++;
            performances.add(performancePerUser);
        }
        double averageMark = cumulativeMark * 1.0 / countOfPassedTasks;
        return new TaskPerformance(performances, countOfPassedTasks, averageMark);
    }

    private void treatOneUser() {

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

