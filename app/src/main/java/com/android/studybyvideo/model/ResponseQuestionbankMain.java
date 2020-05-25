package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class ResponseQuestionbankMain {
    @Expose
    private ArrayList<QuestionbankList> result;

    @Expose
    private int status;

    public ArrayList<QuestionbankList> getResult() {
        return result;
    }

    public void setResult(ArrayList<QuestionbankList> result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
