package com.android.studybyvideo.model;

import com.google.gson.annotations.SerializedName;

public class VideoList {
    @SerializedName("Chapter_azure_path")
    private String Chapter_azure_path;
    @SerializedName("VideoTitle")
    private String VideoTitle;
    @SerializedName("VideoDe")
    private String VideoDe;

    public String getChapter_azure_path() {
        return Chapter_azure_path;
    }

    public void setChapter_azure_path(String chapter_azure_path) {
        Chapter_azure_path = chapter_azure_path;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }

    public String getVideoDe() {
        return VideoDe;
    }

    public void setVideoDe(String videoDe) {
        VideoDe = videoDe;
    }
}
