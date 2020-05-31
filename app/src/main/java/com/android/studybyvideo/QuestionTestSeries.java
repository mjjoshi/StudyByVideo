package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.QuestionbankList;
import com.android.studybyvideo.model.ReponseQuestionBank;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 5/6/2020.
 */
public class QuestionTestSeries extends AppCompatActivity {
    public ArrayList<QuestionbankList> questionlist = new ArrayList<>();
    public ArrayList<QuestionbankList> current_question_list = new ArrayList<>();
    String chapter_id, book_id;
    ImageView back_btn;
    int timeValue = 20;
    int coinValue = 0;
    CountDownTimer countDownTimer;
    TextView timeText;
    LinearLayout ll_option1, ll_option2, ll_option3, ll_option4;
    TextView txt_option_one, txt_option_two, txt_option_three, txt_option_four, txt_question;
    int current_pos;
    CardView next;
    CardView skip;
    String minutes;
    ImageView isSelected_option4,isSelected_option3,isSelected_option2,isSelected_option1;
    CardView review;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.question_test_series);
        chapter_id = getIntent().getStringExtra("chapter_id");
        minutes = getIntent().getStringExtra("time");
        current_pos = getIntent().getIntExtra("position",0);
         back_btn = (ImageView) findViewById(R.id.back_btn);
        review =  findViewById(R.id.review);
        isSelected_option4 = (ImageView) findViewById(R.id.isSelected_option4);
        isSelected_option3 = (ImageView) findViewById(R.id.isSelected_option3);
        isSelected_option2 = (ImageView) findViewById(R.id.isSelected_option2);
        isSelected_option1 = (ImageView) findViewById(R.id.isSelected_option1);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuestionTestSeries.this,QuestionHistory.class);
                intent.putExtra("id",chapter_id);
                startActivity(intent);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        timeText = findViewById(R.id.timeText);
        ll_option1 = findViewById(R.id.ll_option1);
        ll_option2 = findViewById(R.id.ll_option2);
        ll_option3 = findViewById(R.id.ll_option3);
        ll_option4 = findViewById(R.id.ll_option4);
        txt_option_one = findViewById(R.id.txt_option_one);
        txt_option_two = findViewById(R.id.txt_option2);
        txt_option_three = findViewById(R.id.txt_option3);
        txt_option_four = findViewById(R.id.txt_option4);
        txt_question = findViewById(R.id.txt_question);
       String[] min=minutes.split(" ");
        long millis = Integer.parseInt(min[0]) * 60 * 1000;


        //countDownTimer
        countDownTimer = new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                int value= (int) (millisUntilFinished/60000);
                //here you can have your logic to set text to timeText
                timeText.setText("Time :"+String.valueOf(value)+" mins");

                //With each iteration decrement the time by 1 sec
                timeValue -= 1;

                //This means the user is out of time so onFinished will called after this iteration
                if (timeValue == -1) {

                    //Since user is out of time setText as time up
//                    resultText.setText(getString(R.string.timeup));
//
//                    //Since user is out of time he won't be able to click any buttons
//                    //therefore we will disable all four options buttons using this method
//                    disableButton();
                }
            }

            //Now user is out of time
            public void onFinish() {
                //We will navigate him to the time up activity using below method
//                timeUp();
            }
        }.start();
         next = findViewById(R.id.next);
         skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnswerOfQuestion(current_pos,questionlist.get(current_pos),"","NOT_ATTEMPTED",chapter_id);

                if (current_pos != questionlist.size() - 1) {
                    current_pos++;
                    setdata(questionlist.get(current_pos), current_pos);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (current_pos != questionlist.size() - 1) {
//                    current_pos++;
//                    setdata(questionlist.get(current_pos), current_pos);
//                }else{
                Intent intent=new Intent(QuestionTestSeries.this,QuestionHistory.class);
                intent.putExtra("id",chapter_id);
                startActivity(intent);
//                submit(current_pos,questionlist.get(current_pos),"","Submit",chapter_id);


//                }
            }
        });

        ll_option1.setOnClickListener(view -> {
            addAnswerOfQuestion(current_pos,questionlist.get(current_pos),"A","ATTEMPTED",chapter_id);

            if (current_pos != questionlist.size() - 1) {
                current_pos++;
                setdata(questionlist.get(current_pos), current_pos);
            }

        });
        ll_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnswerOfQuestion(current_pos,questionlist.get(current_pos),"B","ATTEMPTED",chapter_id);

                if (current_pos != questionlist.size() - 1) {
                    current_pos++;
                    setdata(questionlist.get(current_pos), current_pos);
                }

            }
        });
        ll_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnswerOfQuestion(current_pos,questionlist.get(current_pos),"C","ATTEMPTED",chapter_id);

                if (current_pos != questionlist.size() - 1) {
                    current_pos++;
                    setdata(questionlist.get(current_pos), current_pos);
                }

            }
        });
        ll_option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnswerOfQuestion(current_pos,questionlist.get(current_pos),"D","ATTEMPTED",chapter_id);
                if (current_pos != questionlist.size() - 1) {
                    current_pos++;
                    setdata(questionlist.get(current_pos), current_pos);
                }

            }
        });
        getQuestionBank(chapter_id);
    }


    private void addAnswerOfQuestion(int i,QuestionbankList model,String markedAnswer,String flag,String tt_id) {
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        final ProgressDialog progressDialog = new ProgressDialog(QuestionTestSeries.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage( "Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ReponseQuestionBank> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).addAnswerOfQuestion(
                "application/x-www-form-urlencoded",
                "addAnswerOfQuestion",
                "0","0",
                sharedPreferences.getString("mobile",""),model.getQ_no(),
                markedAnswer,model.getCorrect_answer(),
                markedAnswer.equalsIgnoreCase(model.getCorrect_answer()),"0s",flag,tt_id,"NO",sharedPreferences.getString("client_id","")

        );
        scheduleListingCall.enqueue(new Callback<ReponseQuestionBank>() {
            @Override
            public void onResponse(Call<ReponseQuestionBank> call, Response<ReponseQuestionBank> response) {
                progressDialog.dismiss();
                if (response.body().getResponse().getStatus()==200){
//                    if (feedItemList.size()-1==i){
//                        activity.finish();
//                    }
//                    timerTxt.setText("1s");
//                    isSelected=false;
//                    activity.view_pager.setCurrentItem(i+1);
                }
            }

            @Override
            public void onFailure(Call<ReponseQuestionBank> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    //If user press home button and come in the game from memory then this
    //method will continue the timer from the previous time it left
    @Override
    protected void onRestart() {
        super.onRestart();
        countDownTimer.start();
    }

    //When activity is destroyed then this will cancel the timer
    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    //This will pause the time
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }


    private void getQuestionBank(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(QuestionTestSeries.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ReponseQuestionBank> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getTestQuestionBank(
                "application/x-www-form-urlencoded",
                "getTestQuestionBank",
                chapter_id

        );
        scheduleListingCall.enqueue(new Callback<ReponseQuestionBank>() {
            @Override
            public void onResponse(Call<ReponseQuestionBank> call, Response<ReponseQuestionBank> response) {
                progressDialog.dismiss();
                if (response.body().getResponse().getStatus() == 200) {
                    questionlist.clear();
                    questionlist = response.body().getResponse().getResult();

                    if (questionlist.size() > 0) {
                        setdata(questionlist.get(current_pos), current_pos);
                    }

                }
            }

            @Override
            public void onFailure(Call<ReponseQuestionBank> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void setdata(QuestionbankList model, int i) {
        String marked=model.getAnswer_marked();

        if(marked!=null && !marked.equals("")){
            if(marked.equalsIgnoreCase("A")){
                isSelected_option1.setVisibility(View.VISIBLE);
                isSelected_option2.setVisibility(View.GONE);
                isSelected_option3.setVisibility(View.GONE);
                isSelected_option4.setVisibility(View.GONE);
            }else if(marked.equalsIgnoreCase("B")){
                isSelected_option1.setVisibility(View.GONE);
                isSelected_option2.setVisibility(View.VISIBLE);
                isSelected_option3.setVisibility(View.GONE);
                isSelected_option4.setVisibility(View.GONE);
            }else if(marked.equalsIgnoreCase("C")){
                isSelected_option1.setVisibility(View.GONE);
                isSelected_option2.setVisibility(View.GONE);
                isSelected_option3.setVisibility(View.VISIBLE);
                isSelected_option4.setVisibility(View.GONE);
            }else if(marked.equalsIgnoreCase("D")){
                isSelected_option1.setVisibility(View.GONE);
                isSelected_option2.setVisibility(View.GONE);
                isSelected_option3.setVisibility(View.GONE);
                isSelected_option4.setVisibility(View.VISIBLE);
            }
        }else{
            isSelected_option1.setVisibility(View.GONE);
            isSelected_option2.setVisibility(View.GONE);
            isSelected_option3.setVisibility(View.GONE);
            isSelected_option4.setVisibility(View.GONE);
        }

        TextView nextTxt = findViewById(R.id.nextTxt);
        ImageView q_image = findViewById(R.id.q_image);
        ImageView option_a_image = findViewById(R.id.option_a_image);
        ImageView option_b_image = findViewById(R.id.option_b_image);
        ImageView option_c_image = findViewById(R.id.option_c_image);
        ImageView option_d_image = findViewById(R.id.option_d_image);
        ImageView exp_image = findViewById(R.id.exp_image);
        txt_question.setText("" + model.getQuestion());
        txt_option_one.setText("" + model.getOption_a());
        txt_option_two.setText("" + model.getOption_b());
        txt_option_three.setText("" + model.getOption_c());
        txt_option_four.setText("" + model.getOption_d());
//        if (questionlist.size() - 1 == i) {
//            nextTxt.setText("DONE");
//        } else {
//            nextTxt.setText("NEXT");
//        }
        if (questionlist.size() - 1 == i) {
            skip.setVisibility(View.GONE);
        } else {
            skip.setVisibility(View.VISIBLE);
        }

        if (questionlist.get(i).getQuestion_image() != null && !questionlist.get(i).getQuestion_image().equals("")) {
            q_image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(questionlist.get(i).getQuestion_image()).placeholder(R.drawable.img_default).into(q_image);
        }
        if (questionlist.get(i).getOption_a_image() != null && !questionlist.get(i).getOption_a_image().equals("")) {
            option_a_image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(questionlist.get(i).getOption_a_image()).placeholder(R.drawable.img_default).into(option_a_image);
        }
        if (questionlist.get(i).getOption_b_image() != null && !questionlist.get(i).getOption_b_image().equals("")) {
            option_b_image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(questionlist.get(i).getOption_b_image()).placeholder(R.drawable.img_default).into(option_b_image);
        }
        if (questionlist.get(i).getOption_c_image() != null && !questionlist.get(i).getOption_c_image().equals("")) {
            option_c_image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(questionlist.get(i).getOption_c_image()).placeholder(R.drawable.img_default).into(option_c_image);
        }
        if (questionlist.get(i).getOption_d_image() != null && !questionlist.get(i).getOption_d_image().equals("")) {
            option_d_image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(questionlist.get(i).getOption_d_image()).placeholder(R.drawable.img_default).into(option_d_image);
        }
        if (questionlist.get(i).getAunswer_image() != null && !questionlist.get(i).getAunswer_image().equals("")) {
            exp_image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(questionlist.get(i).getAunswer_image()).placeholder(R.drawable.img_default).into(exp_image);
        }

    }
}


