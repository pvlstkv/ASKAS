package com.example.javaserver.testing.model.dto;

public class ThemeUpdateIn extends ThemeIn {
    private Long id;

    public ThemeUpdateIn() {
    }

    public ThemeUpdateIn(Long id) {
        this.id = id;
    }

    public ThemeUpdateIn(String name, String decryption, Long subjectId, Integer questionQuantityInTest, Integer attemptNumberInTest, Long id) {
        super(name, decryption, subjectId, questionQuantityInTest, attemptNumberInTest);
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
