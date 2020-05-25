package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/8/2020.
 */
public class TestTypeList {
    @SerializedName("test_type")
    private String test_type;
    @SerializedName("id")
    private String id;

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
