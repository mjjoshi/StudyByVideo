package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class CoursesMainRaponse {
    @Expose
    private CoursesListModel result;

    @Expose
    private int status;

    public CoursesListModel getResult() {
        return result;
    }

    public void setResult(CoursesListModel result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
