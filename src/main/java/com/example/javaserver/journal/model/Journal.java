package com.example.javaserver.journal.model;

import com.example.javaserver.audit.Auditable;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;
@Api(value = "Journal is a class responsible for visiting by one group")
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "journals")
@Data
public class Journal extends Auditable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated journal ID")
    private Long id;

    @ApiModelProperty(notes = "Any teacher comment")
    private String comment;

    @OneToOne
    @ApiModelProperty(notes = "SubjectSemester to which the journal belongs")
    private SubjectSemester subjectSemester;

    @OneToOne
    @ApiModelProperty(notes = "StudyGroup to which the journal belongs")
    private StudyGroup studyGroup;

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = false)
    @ApiModelProperty(notes = "A list of visits by students of any study group")
    private Collection<Visit> visits;

    @OneToOne
    @ApiModelProperty(notes = "A teacher who fills out a journal")
    private User teacher;

}
