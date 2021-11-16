package com.example.javaserver.journal.model;

import com.example.javaserver.audit.Auditable;
import com.example.javaserver.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
@Api(value = "Visit is a class responsible for visiting by one group")
@Entity
@Table(name = "visits")
@Data
public class Visit extends Auditable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated visit ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "journal_id")
    @ApiModelProperty(notes = "The journal to which the VISIT belongs")
    @ToString.Exclude
    private Journal journal;

    @ApiModelProperty(notes = "The user (student) who visits the pair")
    @OneToOne
    private User user;

    @ApiModelProperty(notes = "A boolean flag responsing for visiting a pair or not")
    private Boolean isVisited;

    @ApiModelProperty(notes = "Any teacher comment")
    private String comment;

}
