package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.AboutUsData;
import com.android.studybyvideo.model.ReponseAboutUs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 5/8/2020.
 */
public class ContactUs extends AppCompatActivity {
    ImageView imgBtnProfileBack;
    TextView txtTitle,txt_des,txt_support_mail,txt_sales_mail,txt_contact_mail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        //getSupportActionBar().hide();
        imgBtnProfileBack=findViewById(R.id.imgBtnProfileBack);
        txtTitle=findViewById(R.id.txtTitle);
        txt_des=findViewById(R.id.txt_des);
        txt_support_mail=findViewById(R.id.txt_support_mail);
        txt_sales_mail=findViewById(R.id.txt_sales_mail);
        txt_contact_mail=findViewById(R.id.txt_contact_mail);
        txtTitle.setText("Contact Us");
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
        final ProgressDialog progressDialog = new ProgressDialog(ContactUs.this);
//        progressDialog.setTitle(getResources().getString(R.string.QuestionHistory));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ReponseAboutUs> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getSupportDetails(
                "application/x-www-form-urlencoded",
                "getSupportDetails"

        );
        scheduleListingCall.enqueue(new Callback<ReponseAboutUs>() {
            @Override
            public void onResponse(Call<ReponseAboutUs> call, Response<ReponseAboutUs> response) {
                progressDialog.dismiss();
                if (response.body().getResponse().getStatus() == 200) {
                    model = response.body().getResponse().getResult().get(0);
                    txt_des.setText("" + model.getContact_us_text());
                    txt_support_mail.setText("" + model.getSupport_mail());
                    txt_sales_mail.setText("" + model.getSales_mail());
                    txt_contact_mail.setText("" + model.getContact_us());

                }
            }

            @Override
            public void onFailure(Call<ReponseAboutUs> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
}
