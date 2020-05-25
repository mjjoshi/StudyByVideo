package com.android.studybyvideo.ApiClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

   // public static final String BASE_URL_DEMO = "http://teluscare.work/demo/API/";
    public static final String BASE_URL = "http://45.15.24.149/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
//                    .addConverterFactory(new GsonConverterFactory())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
