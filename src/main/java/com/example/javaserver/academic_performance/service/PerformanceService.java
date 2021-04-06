package com.example.javaserver.academic_performance.service;

import com.example.javaserver.academic_performance.model.Performance;
import com.example.javaserver.academic_performance.model.Progress;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.service.SubjectService;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.testing.model.dto.PassedTestOut;
import com.example.javaserver.testing.model.dto.PassedThemeOut;
import com.example.javaserver.testing.service.ResultService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PerformanceService {

    private SubjectService subjectService;
    private ResultService resultService;

    private int border = 50;

    public Performance formUserPerformance(Integer userId, UserDetailsImp userDetails) {
        Performance performance = new Performance();
        Progress testProgress = formTestPerformance(userId, userDetails);
        performance.setTests(testProgress);
        return performance;
    }

    public Progress formTestPerformance(Integer userId, UserDetailsImp userDetails) {
        Collection<Subject> subjects = subjectService.searchByStudentId(userId, userDetails);
        int done = 0;
        int total = 0;
        for (Subject subject : subjects) {
            List<PassedThemeOut> passedThemeOuts = resultService.fetchUserPassedThemesBySubjectIdAndUserId(userId, subject.getId(), userDetails);
            for (PassedThemeOut passedThemeOut : passedThemeOuts) {
                total++;
                if (passedThemeOut.haveOneNormalRating(border)) {
                    done++;
                }
            }
        }
        return new Progress(done, total);
    }
}

