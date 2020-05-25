package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/10/2020.
 */
public class ResponseReports {
    @Expose
    private ResponseReportsMain resonse;

    public ResponseReportsMain getResponse() {
        return resonse;
    }

    public void setResponse(ResponseReportsMain response) {
        this.resonse = response;
    }
}
