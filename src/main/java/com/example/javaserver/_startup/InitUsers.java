package com.example.javaserver._startup;

import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitUsers implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepo userRepo;

    private final SubjectRepo subjectRepo;

    @Autowired
    public InitUsers(UserRepo userRepo, SubjectRepo subjectRepo) {
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (!subjectRepo.existsByName("АЛМ")) {
            Subject subject = new Subject();
            subject.setName("АЛМ");
            subjectRepo.save(subject);
        }
        if (!userRepo.existsByLogin("admin")) {
            User admin = new User("admin", "admin", UserRole.ADMIN);
            userRepo.save(admin);
        }
        if (!userRepo.existsByLogin("user")) {
            User admin = new User("user", "user", UserRole.USER);
            userRepo.save(admin);
        }
        if (!userRepo.existsByLogin("teacher")) {
            User admin = new User("teacher", "teacher", UserRole.TEACHER);
            userRepo.save(admin);
        }

    }
}
