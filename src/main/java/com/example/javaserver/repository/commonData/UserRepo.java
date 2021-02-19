package com.example.javaserver.repository.commonData;

import com.example.javaserver.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Consumer, Long> {

}
