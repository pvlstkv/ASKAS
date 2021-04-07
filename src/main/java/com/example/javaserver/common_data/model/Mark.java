package com.example.javaserver.common_data.model;

@SuppressWarnings("unused")
public enum Mark {
    NOT_PASSED(0),
    PASSED(1),
    UNSATISFACTORILY(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    private Mark(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
