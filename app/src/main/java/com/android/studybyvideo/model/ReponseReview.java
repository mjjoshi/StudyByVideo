package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/8/2020.
 */
public class ReponseReview {
    @Expose
    private ReponseRevieMain resonse;

    public ReponseRevieMain getResponse() {
        return resonse;
    }

    public void setResponse(ReponseRevieMain response) {
        this.resonse = response;
    }
}
