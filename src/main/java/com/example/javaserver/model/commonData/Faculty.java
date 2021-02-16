package com.example.javaserver.model.commonData;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    String shortName;
    String fullName;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Department> departments = new ArrayList<>();


    // getters/setters/constructors

}
