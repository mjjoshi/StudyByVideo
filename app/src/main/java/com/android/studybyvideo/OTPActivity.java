package com.android.studybyvideo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    private OtpTextView otpTextView;
    ApiInterface apiService;
    private String phoneNumber;
    private AppCompatTextView txtMobileNumber;
    private AppCompatButton imgVerify;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        imgVerify = findViewById(R.id.imgVerify);
        String text = "+91" + " " + phoneNumber;
        txtMobileNumber.setText(text);
        otpTextView = findViewById(R.id.otp_view);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                hideKeyboard(OTPActivity.this);

            }
        });

        imgVerify.setOnClickListener(view -> {
            verifyOtp(otpTextView.getOTP());

        });

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void verifyOtp(String otp) {
        Call<ResponseOtp> responseCall = apiService.verifyOtp("application/x-www-form-urlencoded", "verifyOtp", phoneNumber, otp, "1213", "MOBILE");
        responseCall.enqueue(new Callback<ResponseOtp>() {
            @Override
            public void onResponse(Call<ResponseOtp> call, Response<ResponseOtp> response) {
                try {
                    if (response.body().getResponse().getStatus() == 200) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("mobile", phoneNumber);
                        myEdit.putString("client_id", response.body().getResponse().getResult().getClient_id());
                        myEdit.putString("teacher_id", response.body().getResponse().getResult().getTeacher_id());
                        myEdit.commit();
                        startActivity(new Intent(OTPActivity.this, CoursesActivity1.class));
                        finish();
                    } else {
                        Toast.makeText(OTPActivity.this, response.body().getResponse().getResult().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseOtp> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(OTPActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
