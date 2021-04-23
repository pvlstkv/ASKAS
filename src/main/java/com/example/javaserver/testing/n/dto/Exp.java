package com.example.javaserver.testing.n.dto;

public class Exp {
    int data;
    Object obj;

    public Exp() {
    }

    public Exp(int data, Object obj) {
        this.data = data;
        this.obj = obj;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
