package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/8/2020.
 */
public class TestNameList {

    @SerializedName("ttname_id")
    private String ttname_id;
    @SerializedName("test_type_name")
    private String test_type_name;

    @SerializedName("time_for_the_test")
    private String time_for_the_test;
    @SerializedName("no_of_mcqs")
    private String no_of_mcqs;
    @SerializedName("marks_for_question")
    private String marks_for_question;
    @SerializedName("negative_marking")
    private String negative_marking;
    @SerializedName("negative_marks")
    private String negative_marks;
    @SerializedName("Lock_status")
    private String Lock_status;

    public String getTtname_id() {
        return ttname_id;
    }

    public void setTtname_id(String ttname_id) {
        this.ttname_id = ttname_id;
    }

    public String getTest_type_name() {
        return test_type_name;
    }

    public void setTest_type_name(String test_type_name) {
        this.test_type_name = test_type_name;
    }

    public String getTime_for_the_test() {
        return time_for_the_test;
    }

    public void setTime_for_the_test(String time_for_the_test) {
        this.time_for_the_test = time_for_the_test;
    }

    public String getNo_of_mcqs() {
        return no_of_mcqs;
    }

    public void setNo_of_mcqs(String no_of_mcqs) {
        this.no_of_mcqs = no_of_mcqs;
    }

    public String getMarks_for_question() {
        return marks_for_question;
    }

    public void setMarks_for_question(String marks_for_question) {
        this.marks_for_question = marks_for_question;
    }

    public String getNegative_marking() {
        return negative_marking;
    }

    public void setNegative_marking(String negative_marking) {
        this.negative_marking = negative_marking;
    }

    public String getNegative_marks() {
        return negative_marks;
    }

    public void setNegative_marks(String negative_marks) {
        this.negative_marks = negative_marks;
    }

    public String getLock_status() {
        return Lock_status;
    }

    public void setLock_status(String lock_status) {
        Lock_status = lock_status;
    }
}
