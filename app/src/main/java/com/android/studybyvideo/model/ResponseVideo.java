package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

public class ResponseVideo {
    @Expose
    private VideoMain resonse;

    public VideoMain getResponse() {
        return resonse;
    }

    public void setResponse(VideoMain response) {
        this.resonse = response;
    }
}
