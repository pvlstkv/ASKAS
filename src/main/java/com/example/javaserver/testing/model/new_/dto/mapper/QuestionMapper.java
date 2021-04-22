package com.example.javaserver.testing.model.new_.dto.mapper;

import com.example.javaserver.testing.model.new_.QuestionNew;
import com.example.javaserver.testing.model.new_.dto.QuestionIn;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
    QuestionIn map (QuestionNew question);
}
