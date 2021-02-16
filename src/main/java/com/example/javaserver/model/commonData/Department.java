package com.example.javaserver.model.commonData;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    String shortName;
    String fullName;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    Faculty faculty;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    List<StudyGroup> studyGroups = new ArrayList<>();
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Subject> subjects = new ArrayList<>();


}
