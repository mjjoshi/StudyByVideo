package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/8/2020.
 */
public class ResponseTestType {
    @Expose
    private TestTypeMain resonse;

    public TestTypeMain getResponse() {
        return resonse;
    }

    public void setResponse(TestTypeMain response) {
        this.resonse = response;
    }
}
