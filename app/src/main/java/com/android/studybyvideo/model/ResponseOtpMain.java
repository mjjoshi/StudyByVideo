package com.android.studybyvideo.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/10/2020.
 */
public class ResponseOtpMain {
    @Expose
    private OtpData result;

    @Expose
    private int status;

    public OtpData getResult() {
        return result;
    }

    public void setResult(OtpData result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
