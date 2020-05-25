package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/8/2020.
 */
public class ReponseAboutUs {
    @Expose
    private ReponseAboutUSMain resonse;

    public ReponseAboutUSMain getResponse() {
        return resonse;
    }

    public void setResponse(ReponseAboutUSMain response) {
        this.resonse = response;
    }
}
