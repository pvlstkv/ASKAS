/*
package com.example.javaserver._startup;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.common_data.repo.SubjectRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InitSubjectsAndThemes implements ApplicationListener<ContextRefreshedEvent> {

    private final SubjectRepo subjectRepo;
    private final List<String> themes = new ArrayList<>(Arrays.asList("Автоматы", "Графы", "Булевы функции"));

    public InitSubjectsAndThemes(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!subjectRepo.existsByName("АЛМ")) {
            Subject subject = new Subject("АЛМ");
            Set<Theme> set = new HashSet<>();
            for (String themeStr : themes) {
                Theme theme = new Theme(themeStr);
                theme.setSubject(subject);
                theme.setQuestionQuantityInTest(5);
                set.add(theme);
            }
            subject.setThemes(set);
            subject.setName("АЛМ");
            subjectRepo.save(subject);
        }
    }
}
*/