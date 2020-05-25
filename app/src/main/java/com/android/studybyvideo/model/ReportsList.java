package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/10/2020.
 */
public class ReportsList {

    @SerializedName("id")
    private String id;
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("test_name")
    private String test_name;
    @SerializedName("marks")
    private String marks;
    @SerializedName("percentage")
    private String percentage;
    @SerializedName("attempted_count")
    private String attempted_count;
    @SerializedName("neg_mark_q_count")
    private String neg_mark_q_count;
    @SerializedName("total_count")
    private String total_count;
    @SerializedName("marks_of_question")
    private String total_marks;
    @SerializedName("total_marks")
    private String rank;
    @SerializedName("rank")
    private String total_rank;
    @SerializedName("total_rank")
    private String markes;
    @SerializedName("markes")

    private String marks_of_question;
    @SerializedName("negative_marks")
    private String negative_marks;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getAttempted_count() {
        return attempted_count;
    }

    public void setAttempted_count(String attempted_count) {
        this.attempted_count = attempted_count;
    }

    public String getNeg_mark_q_count() {
        return neg_mark_q_count;
    }

    public void setNeg_mark_q_count(String neg_mark_q_count) {
        this.neg_mark_q_count = neg_mark_q_count;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTotal_rank() {
        return total_rank;
    }

    public void setTotal_rank(String total_rank) {
        this.total_rank = total_rank;
    }

    public String getMarkes() {
        return markes;
    }

    public void setMarkes(String markes) {
        this.markes = markes;
    }

    public String getMarks_of_question() {
        return marks_of_question;
    }

    public void setMarks_of_question(String marks_of_question) {
        this.marks_of_question = marks_of_question;
    }

    public String getNegative_marks() {
        return negative_marks;
    }

    public void setNegative_marks(String negative_marks) {
        this.negative_marks = negative_marks;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
