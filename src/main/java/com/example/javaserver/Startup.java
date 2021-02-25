package com.example.javaserver;

import com.example.javaserver.model.User;
import com.example.javaserver.model.UserRole;
import com.example.javaserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepo userRepo;

    @Autowired
    public Startup(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if(!userRepo.existsByLogin("admin")){
            User admin = new User("admin","admin", UserRole.ADMIN);
            userRepo.save(admin);
        }
        if(!userRepo.existsByLogin("user")){
            User admin = new User("user","user", UserRole.USER);
            userRepo.save(admin);
        }
        if(!userRepo.existsByLogin("teacher")){
            User admin = new User("teacher","teacher", UserRole.TEACHER);
            userRepo.save(admin);
        }

    }
}
