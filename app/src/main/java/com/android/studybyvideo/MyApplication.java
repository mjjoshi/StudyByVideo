package com.android.studybyvideo;

import com.android.studybyvideo.model.SubmitData;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    public static SubmitData sumbitmodel;
    public static String tiime;
    public static String chapter_id;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
