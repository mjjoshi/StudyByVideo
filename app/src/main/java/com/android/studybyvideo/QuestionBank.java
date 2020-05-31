package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.QuestionbankList;
import com.android.studybyvideo.model.ReponseQuestionBank;
import com.android.studybyvideo.model.ResponseChapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionBank extends AppCompatActivity {
    public CustomViewPager view_pager;
    public ArrayList<QuestionbankList> questionlist=new ArrayList<>();
    String chapter_id,book_id;
    ImageView back_btn;
    private View layout_Progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.questionbank);
        view_pager=findViewById(R.id.view_pager);
        layout_Progress = findViewById(R.id.layout_Progress);

        chapter_id=getIntent().getStringExtra("chapter_id");
        book_id=getIntent().getStringExtra("book_id");
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        view_pager.setPagingEnabled(false);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getQuestionBank(chapter_id);
    }


    private void getQuestionBank(String id) {
//        final ProgressDialog progressDialog = new ProgressDialog(QuestionBank.this);
////        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
//        progressDialog.setMessage( "Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();


        layout_Progress.setVisibility(View.VISIBLE);

        Call<ReponseQuestionBank> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getQuestionBank(
                "application/x-www-form-urlencoded",
                "getQuestionBank",
                chapter_id,book_id

        );
        scheduleListingCall.enqueue(new Callback<ReponseQuestionBank>() {
            @Override
            public void onResponse(Call<ReponseQuestionBank> call, Response<ReponseQuestionBank> response) {
                //progressDialog.dismiss();


                layout_Progress.setVisibility(View.GONE);
                if (response.body().getResponse().getStatus()==200){
                    questionlist.clear();
                    questionlist = response.body().getResponse().getResult();
                  set_data();

                }
            }

            @Override
            public void onFailure(Call<ReponseQuestionBank> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
            }
        });
    }
    private void set_data(){

        QuestionabkAdapter  doctorDetailsAdapter = new QuestionabkAdapter(getApplicationContext(),questionlist,QuestionBank.this);
        view_pager.setAdapter(doctorDetailsAdapter);
    }
}
