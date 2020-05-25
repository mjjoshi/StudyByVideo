package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CoursesListModel {
    @SerializedName("purchased")
    private ArrayList<CoursesPurchasedList> purchased;
    @SerializedName("offered")
    private ArrayList<CoursesPurchasedList> offered;
    @SerializedName("app_version")
    private String app_version;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public ArrayList<CoursesPurchasedList> getPurchased() {
        return purchased;
    }

    public void setPurchased(ArrayList<CoursesPurchasedList> purchased) {
        this.purchased = purchased;
    }

    public ArrayList<CoursesPurchasedList> getOffered() {
        return offered;
    }

    public void setOffered(ArrayList<CoursesPurchasedList> offered) {
        this.offered = offered;
    }
}
