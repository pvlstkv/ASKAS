package com.example.javaserver._startup;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitStudyGroups implements ApplicationListener<ContextRefreshedEvent> {
    private final StudyGroupRepo studyGroupRepo;

    @Autowired
    public InitStudyGroups(StudyGroupRepo studyGroupRepo) {
        this.studyGroupRepo = studyGroupRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        StudyGroup group1 = new StudyGroup(0001,1,1,"ИВТАП-11","Инф и выч техника",2020);
        StudyGroup group2 = new StudyGroup(0002,1,2,"ИВТАП-21","Инф и выч техника",2019);
        StudyGroup group3 = new StudyGroup(0003,1,3,"ИВТАП-31","Инф и выч техника",2018);
        studyGroupRepo.save(group1);
        studyGroupRepo.save(group2);
        studyGroupRepo.save(group3);
    }
}
