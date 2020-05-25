package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/8/2020.
 */
public class ReponseReviewList {
    @SerializedName("q_no")
    private String q_no;
    @SerializedName("is_attempted")
    private String is_attempted;

    public String getQ_no() {
        return q_no;
    }

    public void setQ_no(String q_no) {
        this.q_no = q_no;
    }

    public String getIs_attempted() {
        return is_attempted;
    }

    public void setIs_attempted(String is_attempted) {
        this.is_attempted = is_attempted;
    }
}
