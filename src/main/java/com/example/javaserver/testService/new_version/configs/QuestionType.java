package com.example.javaserver.testService.new_version.configs;

public enum QuestionType {
    CHOOSE(0),
    WRITE(1),
    MATCH(2),
    SEQUENCE(3);

    private int id; // Could be other data type besides int
    QuestionType(int id) {
        this.id = id;
    }
    public int getIdRole(){
        return id;
    }
}
