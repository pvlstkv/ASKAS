package com.example.javaserver.academic_performance.model;

public class Progress {
    private Integer done;
    private Integer total;

    public Progress() {
        this.done = 0;
        this.total = 0;
    }

    public Progress(Progress oldProgress) {
        this.done = oldProgress.getDone();
        this.total = oldProgress.getTotal();
    }
    public Progress(Integer done, Integer total) {
        this.done = done;
        this.total = total;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
