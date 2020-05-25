package com.android.studybyvideo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ResponseLogin;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton login;
    ApiInterface apiService;
    EditText edt_mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_mobile = findViewById(R.id.edt_mobile);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String mobile = sh.getString("mobile", "");
        if (mobile != null && mobile.length() > 0) {
            startActivity(new Intent(LoginActivity.this, CoursesActivity1.class));
            finish();
        }

        login = findViewById(R.id.login);


        apiService = ApiClient.getClient().create(ApiInterface.class);
        login.setOnClickListener(v -> {
            JSONObject obj = new JSONObject();
            JSONObject paramObject = new JSONObject();
            try {
                obj.put("mobile", "9610639103");
                obj.put("api_name", "getAllCourses");
                paramObject.put("", obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (edt_mobile.getText().toString().length() > 0) {
                sendOtp();
            }

        });
    }

    private void sendOtp() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseLogin> responseCall = apiService.sendOtp("application/x-www-form-urlencoded", "sendOtp", edt_mobile.getText().toString());
        responseCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.body().getResponse().getStatus() == 200) {
                    startActivity(new Intent(LoginActivity.this, OTPActivity.class).putExtra("phoneNumber", edt_mobile.getText().toString()));
                    finish();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void verifyOtp(String otp) {
        Call<ResponseOtp> responseCall = apiService.verifyOtp("application/x-www-form-urlencoded", "verifyOtp", edt_mobile.getText().toString(), otp, "1213", "MOBILE");
        responseCall.enqueue(new Callback<ResponseOtp>() {
            @Override
            public void onResponse(Call<ResponseOtp> call, Response<ResponseOtp> response) {
//                Toast.makeText(LoginActivity.this, "response.body().getError()", Toast.LENGTH_SHORT).show();
                // Storing data into SharedPreferences
                try {
                    if (response.body().getResponse().getStatus() == 200) {
                        dialog1.dismiss();
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("mobile", edt_mobile.getText().toString());
                        myEdit.putString("client_id", response.body().getResponse().getResult().getClient_id());
                        myEdit.commit();
                        startActivity(new Intent(LoginActivity.this, CoursesActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
                }
//                if (response.body().getStatus()) {
//                    if (response.body().getData() != null) {
//
//                    }
//                } else {
//                }
            }

            @Override
            public void onFailure(Call<ResponseOtp> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Dialog dialog1;

//    public void showotp_BottomSheetDialog() {
//        dialog1 = new Dialog(LoginActivity.this);
//        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog1.setContentView(R.layout.otp_layout);
//        Button submit = dialog1.findViewById(R.id.submit);
//        final EditText edt_otp = dialog1.findViewById(R.id.edt_otp);
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (edt_otp.getText().toString().length() > 0) {
//                    verifyOtp(edt_otp.getText().toString());
//                }
//
//            }
//        });
//        dialog1.show();
//        Window window = dialog1.getWindow();
//        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    }
}
