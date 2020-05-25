package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class ChapterMain {
    @Expose
    private ArrayList<ChapterList> result;

    @Expose
    private int status;

    public ArrayList<ChapterList> getResult() {
        return result;
    }

    public void setResult(ArrayList<ChapterList> result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
