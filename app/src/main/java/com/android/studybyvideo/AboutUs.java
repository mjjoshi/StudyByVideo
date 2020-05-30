package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.AboutUsData;
import com.android.studybyvideo.model.ReponseAboutUs;
import com.android.studybyvideo.model.ReponseReview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 5/8/2020.
 */
public class AboutUs extends AppCompatActivity {
    ImageView imgBtnProfileBack;
    LinearLayout llcontact;
    TextView txt_des;
    private View layout_Progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_as_activity);

        layout_Progress = findViewById(R.id.layout_Progress);
        imgBtnProfileBack = findViewById(R.id.imgBtnProfileBack);
        //llcontact = findViewById(R.id.llcontact);
        txt_des = findViewById(R.id.txt_des);
       // llcontact.setVisibility(View.GONE);
        imgBtnProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getScheduleListingData();
    }

    AboutUsData model;

    private void getScheduleListingData() {
//        final ProgressDialog progressDialog = new ProgressDialog(AboutUs.this);
////        progressDialog.setTitle(getResources().getString(R.string.QuestionHistory));
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        layout_Progress.setVisibility(View.VISIBLE);
        Call<ReponseAboutUs> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getSupportDetails(
                "application/x-www-form-urlencoded",
                "getSupportDetails"

        );
        scheduleListingCall.enqueue(new Callback<ReponseAboutUs>() {
            @Override
            public void onResponse(Call<ReponseAboutUs> call, Response<ReponseAboutUs> response) {
               // progressDialog.dismiss();

                layout_Progress.setVisibility(View.GONE);
                if (response.body().getResponse().getStatus() == 200) {
                    model = response.body().getResponse().getResult().get(0);
                    txt_des.setText("" + model.getAbout_us_text());

                }
            }

            @Override
            public void onFailure(Call<ReponseAboutUs> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                //progressDialog.dismiss();

                layout_Progress.setVisibility(View.GONE);
            }
        });
    }
}