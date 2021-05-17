package com.example.javaserver.academic_performance.model.main_page;

public class Performance {
    private Progress tests;
    private Progress labs;
    private Progress practices;
    private Progress essays;

    public Progress getTests() {
        return tests;
    }

    public void setTests(Progress tests) {
        this.tests = tests;
    }

    public Progress getLabs() {
        return labs;
    }

    public void setLabs(Progress labs) {
        this.labs = labs;
    }

    public Progress getPractices() {
        return practices;
    }

    public void setPractices(Progress practices) {
        this.practices = practices;
    }

    public Progress getEssays() {
        return essays;
    }

    public void setEssays(Progress essays) {
        this.essays = essays;
    }
}
