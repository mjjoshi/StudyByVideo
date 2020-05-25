package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

public class LoginResponse {
    @Expose
    private String result;

    @Expose
    private int status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
