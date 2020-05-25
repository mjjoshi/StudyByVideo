package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/10/2020.
 */
public class ResponseSumbit {
    @Expose
    private ResponseSubmitMain resonse;

    public ResponseSubmitMain getResponse() {
        return resonse;
    }

    public void setResponse(ResponseSubmitMain response) {
        this.resonse = response;
    }
}

