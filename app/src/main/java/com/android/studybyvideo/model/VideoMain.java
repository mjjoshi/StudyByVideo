package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class VideoMain {
    @Expose
    private ArrayList<VideoList> result;

    @Expose
    private int status;

    public ArrayList<VideoList> getResult() {
        return result;
    }

    public void setResult(ArrayList<VideoList> result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
