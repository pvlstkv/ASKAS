package com.example.javaserver.model;

import com.example.javaserver.configuration.Role;
import com.example.javaserver.model.commonData.Department;
import com.example.javaserver.model.commonData.StudyGroup;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    String login;
    String email;
    String phoneNumber;
    String password;
    String firstName;
    String lastName;
    String patronymic;
    String avatar;
    Role role;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    StudyGroup studyGroup;

}
