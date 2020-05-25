package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

public class ResponseChapter {
    @Expose
    private ChapterMain resonse;

    public ChapterMain getResponse() {
        return resonse;
    }

    public void setResponse(ChapterMain response) {
        this.resonse = response;
    }
}
