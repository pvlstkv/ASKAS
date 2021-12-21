package com.example.javaserver.flex_mark.model;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class FlexMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated ID")
    private Long id;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private FMConfigPerCriteria visit;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private FMConfigPerCriteria taskMark;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private FMConfigPerCriteria testMark;

    @OneToOne
    private User teacher;

    @OneToOne
    private SubjectSemester subjectSemester;

    @OneToOne
    private StudyGroup studyGroup;

}
