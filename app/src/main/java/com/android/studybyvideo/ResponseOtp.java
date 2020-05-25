package com.android.studybyvideo;

import com.android.studybyvideo.model.LoginResponse;
import com.android.studybyvideo.model.ResponseOtpMain;
import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 5/10/2020.
 */
public class ResponseOtp {
    @Expose
    private ResponseOtpMain resonse;

    public ResponseOtpMain getResponse() {
        return resonse;
    }

    public void setResponse(ResponseOtpMain response) {
        this.resonse = response;
    }
}
