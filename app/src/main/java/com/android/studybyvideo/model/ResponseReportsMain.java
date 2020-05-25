package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by ${} on 5/10/2020.
 */
public class ResponseReportsMain {
    @Expose
    private ArrayList<ReportsList> result;

    @Expose
    private int status;

    public ArrayList<ReportsList> getResult() {
        return result;
    }

    public void setResult(ArrayList<ReportsList> result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
