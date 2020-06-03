package com.android.studybyvideo;

import android.content.Intent;
import android.content.SharedPreferences;
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
    TextView txt_attempt_count,txt_test_name,txt_total_no_q,txt_score,txt_wrong_count, nextReports ,txt_Question_attemp , txt_marks,txt_skipped ;
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
        nextReports=findViewById(R.id.nextReports);
        txt_wrong_count=findViewById(R.id.txt_wrong_count);


        txt_Question_attemp=findViewById(R.id.txt_Question_attemp);
        txt_marks=findViewById(R.id.txt_marks);
        txt_skipped=findViewById(R.id.txt_skipped);

        txt_attempt_count.setText(""+MyApplication.sumbitmodel.getAttempted_count());
        txt_score.setText(""+MyApplication.sumbitmodel.getPercentage());
        txt_marks.setText(""+MyApplication.sumbitmodel.getMarks());

        txt_total_no_q.setText(""+MyApplication.sumbitmodel.getTotal_count());
        txt_wrong_count.setText(""+MyApplication.sumbitmodel.getNeg_mark_q_count());
        txt_test_name.setText("Test Name :"+MyApplication.sumbitmodel.getTest_name());


        imgBtnProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nextReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String client_id = sh.getString("client_id", "");
                Intent intent = new Intent(ResultScreen.this, ReportsList.class);
                intent.putExtra("client_id", client_id);
                startActivity(intent);
                finish();

            }
        });
    }
}
