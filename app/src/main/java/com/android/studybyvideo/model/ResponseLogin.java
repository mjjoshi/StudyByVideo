package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

public class ResponseLogin {
    @Expose
    private LoginResponse resonse;

    public LoginResponse getResponse() {
        return resonse;
    }

    public void setResponse(LoginResponse response) {
        this.resonse = response;
    }
}
