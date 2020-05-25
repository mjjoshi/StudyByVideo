package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

public class ReponseQuestionBank {
    @Expose
    private ResponseQuestionbankMain resonse;

    public ResponseQuestionbankMain getResponse() {
        return resonse;
    }

    public void setResponse(ResponseQuestionbankMain response) {
        this.resonse = response;
    }
}
