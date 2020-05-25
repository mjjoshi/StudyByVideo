package com.android.studybyvideo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ${} on 5/10/2020.
 */
public class ResultScreen extends AppCompatActivity {
    TextView txt_attempt_count,txt_test_name,txt_total_no_q,txt_score,txt_wrong_count;
    ImageView imgBtnProfileBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.result_screen);
        txt_attempt_count=findViewById(R.id.txt_attempt_count);
        imgBtnProfileBack=findViewById(R.id.imgBtnProfileBack);
        txt_total_no_q=findViewById(R.id.txt_total_no_q);
        txt_test_name=findViewById(R.id.txt_test_name);
        txt_score=findViewById(R.id.txt_score);
        txt_wrong_count=findViewById(R.id.txt_wrong_count);
        txt_attempt_count.setText(""+MyApplication.sumbitmodel.getAttempted_count());
        txt_score.setText(""+MyApplication.sumbitmodel.getPercentage());
        txt_total_no_q.setText(""+MyApplication.sumbitmodel.getTotal_count());
        txt_wrong_count.setText(""+MyApplication.sumbitmodel.getNeg_mark_q_count());
        txt_test_name.setText("Test Name :"+MyApplication.sumbitmodel.getTest_name());
        imgBtnProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
