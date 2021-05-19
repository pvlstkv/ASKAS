package com.example.javaserver.testing.new_v.model.saving_result.question;

import com.example.javaserver.testing.new_v.model.saving_result.answer.Answerable;

import java.util.List;

public interface Questionable {
    List<Answerable> getAnswers();
}
