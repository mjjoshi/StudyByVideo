package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class ResponseCoursesListModel {
    @Expose
    private CoursesMainRaponse resonse;

    public CoursesMainRaponse getResponse() {
        return resonse;
    }

    public void setResponse(CoursesMainRaponse response) {
        this.resonse = response;
    }
}
