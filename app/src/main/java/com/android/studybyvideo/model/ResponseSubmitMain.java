package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by ${} on 5/10/2020.
 */
public class ResponseSubmitMain {
    @Expose
    private SubmitData result;

    @Expose
    private int status;

    public SubmitData getResult() {
        return result;
    }

    public void setResult(SubmitData result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
