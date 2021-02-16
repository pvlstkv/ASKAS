package com.example.javaserver.model.commonData;

import com.example.javaserver.configuration.SubjectControlType;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    String name;
    String decryption;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SubjectSemester> semesters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    Department department;

    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

}
