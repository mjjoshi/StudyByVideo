package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChapterList implements Serializable {
    @SerializedName("Chapter_Id")
    private String Chapter_Id;
    @SerializedName("Chapter_Name")
    private String Chapter_Name;
    @SerializedName("Chapter_des")
    private String Chapter_des;
    @SerializedName("Chapter_Image")
    private String Chapter_Image;
    @SerializedName("ChapterNotes")
    private String ChapterNotes;
    @SerializedName("BookID")
    private String BookID;
    @SerializedName("lock_status")
    private String lock_status;

    @SerializedName("isSelected")
    private boolean isSelected;

    public String getChapterNotes() {
        return ChapterNotes;
    }

    public void setChapterNotes(String chapterNotes) {
        ChapterNotes = chapterNotes;
    }

    public String getChapter_Image() {
        return Chapter_Image;
    }

    public void setChapter_Image(String chapter_Image) {
        Chapter_Image = chapter_Image;
    }

    public String getChapter_Id() {
        return Chapter_Id;
    }

    public void setChapter_Id(String chapter_Id) {
        Chapter_Id = chapter_Id;
    }

    public String getChapter_Name() {
        return Chapter_Name;
    }

    public void setChapter_Name(String chapter_Name) {
        Chapter_Name = chapter_Name;
    }

    public String getChapter_des() {
        return Chapter_des;
    }

    public void setChapter_des(String chapter_des) {
        Chapter_des = chapter_des;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getLock_status() {
        return lock_status;
    }

    public void setLock_status(String lock_status) {
        this.lock_status = lock_status;
    }
}
