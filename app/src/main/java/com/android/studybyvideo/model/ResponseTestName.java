package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/8/2020.
 */
public class ResponseTestName {
    @Expose
    private TestNameMain resonse;

    public TestNameMain getResponse() {
        return resonse;
    }

    public void setResponse(TestNameMain response) {
        this.resonse = response;
    }
}
