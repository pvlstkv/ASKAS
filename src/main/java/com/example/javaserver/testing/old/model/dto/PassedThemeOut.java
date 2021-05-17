package com.example.javaserver.testing.old.model.dto;



import com.example.javaserver.testing.theme.Theme;

import java.util.List;

public class PassedThemeOut {
    private Theme theme;
    private List<Integer> ratings;

    public PassedThemeOut() {
    }

    public PassedThemeOut(Theme theme, List<Integer> ratings) {
        this.theme = theme;
        this.ratings = ratings;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public boolean haveOneNormalRating(int border) {
        return this.ratings.stream().anyMatch(rate-> rate > border);
    }
}
