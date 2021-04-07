/*
package com.example.javaserver._startup;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InitStudyGroups implements ApplicationListener<ContextRefreshedEvent> {
    private final StudyGroupRepo studyGroupRepo;
    private final UserRepo userRepo;

    @Autowired
    public InitStudyGroups(StudyGroupRepo studyGroupRepo, UserRepo userRepo) {
        this.studyGroupRepo = studyGroupRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        StudyGroup group1 = new StudyGroup(0001,1,1,"ИВТАП-11","Инф и выч техника",2020);
        StudyGroup group2 = new StudyGroup(0002,1,2,"ИВТАП-21","Инф и выч техника",2019);
        StudyGroup group3 = new StudyGroup(0003,1,3,"ИВТАП-31","Инф и выч техника",2018);
        studyGroupRepo.save(group1);
        studyGroupRepo.save(group2);
        studyGroupRepo.save(group3);
        User user =  new User("test1","123", UserRole.USER);
        userRepo.save(user);
        user.setStudyGroup(group1);
        userRepo.save(user);
    }
}
*/