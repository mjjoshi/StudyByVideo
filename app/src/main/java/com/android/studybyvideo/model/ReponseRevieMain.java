package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by ${} on 5/8/2020.
 */
public class ReponseRevieMain {
    @Expose
    private ArrayList<ReponseReviewList> result;

    @Expose
    private int status;

    public ArrayList<ReponseReviewList> getResult() {
        return result;
    }

    public void setResult(ArrayList<ReponseReviewList> result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
