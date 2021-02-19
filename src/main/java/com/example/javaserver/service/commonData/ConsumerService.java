package com.example.javaserver.service.commonData;

import com.example.javaserver.model.Consumer;
import com.example.javaserver.repository.commonData.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @Autowired
    private UserRepo userRepo;
    public boolean addConsumer(Consumer consumer){
        userRepo.save(consumer);
        return true;
    }

}
