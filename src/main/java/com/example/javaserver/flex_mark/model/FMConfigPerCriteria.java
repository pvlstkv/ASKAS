package com.example.javaserver.flex_mark.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class FMConfigPerCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated ID")
    private Long id;

    private Boolean isBinaryMark;

    private Integer passedValue;

    private Integer binaryBorder;

    private Integer fourFiveBorder;

    private Integer threeFourBorder;

    private Integer twoThreeBorder;
}
