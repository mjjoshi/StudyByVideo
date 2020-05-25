package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/10/2020.
 */
public class OtpData {

    @SerializedName("message")
    private String message;
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("teacher_id")
    private String teacher_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
}
