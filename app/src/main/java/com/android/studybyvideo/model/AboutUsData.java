package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/8/2020.
 */
public class AboutUsData {
    @SerializedName("id")
    private String id;
    @SerializedName("teacherid")
    private String teacherid;

    @SerializedName("about_us_text")
    private String about_us_text;
    @SerializedName("support_mail")
    private String support_mail;

    @SerializedName("sales_mail")
    private String sales_mail;
    @SerializedName("contact_us")
    private String contact_us;
    @SerializedName("contact_us_text")
    private String contact_us_text;
    @SerializedName("mobile1")
    private String mobile1;

    @SerializedName("mobile2")
    private String mobile2;
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

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getAbout_us_text() {
        return about_us_text;
    }

    public void setAbout_us_text(String about_us_text) {
        this.about_us_text = about_us_text;
    }

    public String getSupport_mail() {
        return support_mail;
    }

    public void setSupport_mail(String support_mail) {
        this.support_mail = support_mail;
    }

    public String getSales_mail() {
        return sales_mail;
    }

    public void setSales_mail(String sales_mail) {
        this.sales_mail = sales_mail;
    }

    public String getContact_us() {
        return contact_us;
    }

    public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }

    public String getContact_us_text() {
        return contact_us_text;
    }

    public void setContact_us_text(String contact_us_text) {
        this.contact_us_text = contact_us_text;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
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
