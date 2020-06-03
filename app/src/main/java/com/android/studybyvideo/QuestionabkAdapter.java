package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.QuestionbankList;
import com.android.studybyvideo.model.ReponseQuestionBank;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class QuestionabkAdapter extends PagerAdapter {
    Context mContext;
    private List<QuestionbankList> feedItemList;
    private LayoutInflater mLayoutInflater;
    QuestionBank activity;
    boolean isSelected=false;
    String selectedOption="";
    CountDownTimer countDownTimer;
    String markedAnswer;
    int timeValue = 20;
    int coinValue = 0;
    public QuestionabkAdapter(Context context, List<QuestionbankList> customizedListView,QuestionBank activity) {
        this.mContext = context;
        feedItemList = customizedListView;
        this.activity=activity;

    }

    @Override
    public int getCount() {
        return feedItemList.size();
    }


    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        Handler handler=new Handler();
        Runnable runnable = null;
        final int[] time = {1000};

        CountDownTimer countDownTimer;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.question_bank_list_item, null);
        LinearLayout explll = view.findViewById(R.id.explll);
        CardView next = view.findViewById(R.id.next);
        CardView skip = view.findViewById(R.id.skip);
        TextView timerTxt = view.findViewById(R.id.timerTxt);
        TextView nextTxt = view.findViewById(R.id.nextTxt);
        TextView txt_question = view.findViewById(R.id.txt_question);
        TextView txt_option1 = view.findViewById(R.id.txt_option_one);
        TextView txt_option2 = view.findViewById(R.id.txt_option2);
        TextView txt_option3 = view.findViewById(R.id.txt_option3);
        TextView txt_option4 = view.findViewById(R.id.txt_option4);
        TextView txt_explain = view.findViewById(R.id.explanation);
        TextView resultText = view.findViewById(R.id.resultText);
        ImageView q_image = view.findViewById(R.id.q_image);
        ImageView option_a_image = view.findViewById(R.id.option_a_image);
        ImageView option_b_image = view.findViewById(R.id.option_b_image);
        ImageView option_c_image = view.findViewById(R.id.option_c_image);
        ImageView option_d_image = view.findViewById(R.id.option_d_image);
        ImageView exp_image = view.findViewById(R.id.exp_image);
        txt_explain.setText(feedItemList.get(i).getExplaination());
        if (feedItemList.size()-1==i){
            nextTxt.setText("DONE");
        }else {
            nextTxt.setText("NEXT");
        }if (feedItemList.size()-1==i){
            skip.setVisibility(View.GONE);
        }else {
            skip.setVisibility(View.VISIBLE);
        }
        if (feedItemList.get(i).getQuestion_image()!=null&&!feedItemList.get(i).getQuestion_image().equals("")) {
            q_image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(feedItemList.get(i).getQuestion_image()).placeholder(R.drawable.img_default).into(q_image);
        }
        if (feedItemList.get(i).getOption_a_image()!=null&&!feedItemList.get(i).getOption_a_image().equals("")) {
            option_a_image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(feedItemList.get(i).getOption_a_image()).placeholder(R.drawable.img_default).into(option_a_image);
        }
        if (feedItemList.get(i).getOption_b_image()!=null&&!feedItemList.get(i).getOption_b_image().equals("")) {
            option_b_image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(feedItemList.get(i).getOption_b_image()).placeholder(R.drawable.img_default).into(option_b_image);
        }
        if (feedItemList.get(i).getOption_c_image()!=null&&!feedItemList.get(i).getOption_c_image().equals("")) {
            option_c_image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(feedItemList.get(i).getOption_c_image()).placeholder(R.drawable.img_default).into(option_c_image);
        }
        if (feedItemList.get(i).getOption_d_image()!=null&&!feedItemList.get(i).getOption_d_image().equals("")) {
            option_d_image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(feedItemList.get(i).getOption_d_image()).placeholder(R.drawable.img_default).into(option_d_image);
        }
        if (feedItemList.get(i).getAunswer_image()!=null&&!feedItemList.get(i).getAunswer_image().equals("")) {
            exp_image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(feedItemList.get(i).getAunswer_image()).placeholder(R.drawable.img_default).into(exp_image);
        }
        LinearLayout ll_option1 = view.findViewById(R.id.ll_option1);
        LinearLayout ll_option2 = view.findViewById(R.id.ll_option2);
        LinearLayout ll_option3 = view.findViewById(R.id.ll_option3);
        LinearLayout ll_option4 = view.findViewById(R.id.ll_option4);

        txt_question.setText("Q. "+feedItemList.get(i).getQ_no()+") "+feedItemList.get(i).getQuestion());
        txt_option1.setText("A. "+feedItemList.get(i).getOption_a());
        txt_option2.setText("B. "+feedItemList.get(i).getOption_b());
        txt_option3.setText("C. "+feedItemList.get(i).getOption_c());
        txt_option4.setText("D. "+feedItemList.get(i).getOption_d());

        txt_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markedAnswer="A";
                if (!isSelected) {
                    explll.setVisibility(View.VISIBLE);
                    isSelected=true;
                    if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("A")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("B")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("C")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("D")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                    }
                }
            }
        });
        txt_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markedAnswer="B";
                if (!isSelected) {
                    explll.setVisibility(View.VISIBLE);
                    isSelected=true;
                    if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("A")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("B")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("C")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("D")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                    }
                }
            }
        });

        txt_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markedAnswer="C";
                if (!isSelected) {
                    explll.setVisibility(View.VISIBLE);
                    isSelected=true;
                    if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("A")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("B")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("C")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("D")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.red));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                    }
                }
            }
        });
        txt_option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markedAnswer="D";
                if (!isSelected) {
                    explll.setVisibility(View.VISIBLE);
                    isSelected=true;
                    if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("A")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.red));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("B")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.red));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("C")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.red));
                    } else if (feedItemList.get(i).getCorrect_answer().equalsIgnoreCase("D")) {
                        txt_option1.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option2.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option3.setTextColor(mContext.getResources().getColor(R.color.black));
                        txt_option4.setTextColor(mContext.getResources().getColor(R.color.light_green_color));
                    }
                }
            }
        });

//        txt_explain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                show_order_detail_BottomSheetDialog(feedItemList.get(i).getExplaination());
//            }
//        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    addAnswerOfQuestion(i, timerTxt);
                }else {
                    Toast.makeText(mContext,"Please select one option first",Toast.LENGTH_SHORT).show();
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerTxt.setText("1s");
                isSelected=false;
                activity.view_pager.setCurrentItem(i+1);
            }
        });
        container.addView(view, 0);

        runnable=new Runnable() {
            @Override
            public void run() {
                if (!isSelected && activity.view_pager.getCurrentItem()==i) {
                    time[0] += 1000;
                    int sec = (time[0] / 1000);
                    if (sec <= 60) {
                        timerTxt.setText(sec + "s");
                    } else {
                        int min = (sec / 60);
                        int sec2 = sec - min * 60;
                        if (min <= 60) {
                            timerTxt.setText(min + "m:" + sec2 + "s");
                        } else {
                            int hours = (min / 60);
                            int mins = (hours / 60);
                            int secs = mins * 60 - sec;
                            timerTxt.setText(hours + "h:" + mins + "m:" + secs + "s");
                        }
                    }
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, time[0]);
        return view;

    }

    //This method is called when time is up
    //this method will navigate user to the activity Time_Up
    public void timeUp() {
        Intent intent = new Intent(mContext, Time_Up.class);
        mContext.startActivity(intent);

    }
    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        container.removeView((RelativeLayout) obj);
    }


    public void show_order_detail_BottomSheetDialog(String description) {
        View view = activity.getLayoutInflater().inflate(R.layout.expilnation, null);

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.MyDialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        TextView txt_explain=view.findViewById(R.id.txt_explain);
        txt_explain.setText(""+description);
        dialog.show();
    }
    private void addAnswerOfQuestion(int i,TextView timerTxt) {
        SharedPreferences sharedPreferences
                = mContext.getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        final ProgressDialog progressDialog = new ProgressDialog(activity);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage( "Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ReponseQuestionBank> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).addAnswerOfQuestion(
                "application/x-www-form-urlencoded",
                "addAnswerOfQuestion",
                activity.book_id,activity.chapter_id,
                sharedPreferences.getString("mobile",""),feedItemList.get(i).getQ_no(),
                markedAnswer,feedItemList.get(i).getCorrect_answer(),
                markedAnswer.equalsIgnoreCase(feedItemList.get(i).getCorrect_answer()),timerTxt.getText().toString(),"","","",sharedPreferences.getString("client_id","")

        );
        scheduleListingCall.enqueue(new Callback<ReponseQuestionBank>() {
            @Override
            public void onResponse(Call<ReponseQuestionBank> call, Response<ReponseQuestionBank> response) {
                progressDialog.dismiss();
                if (response.body().getResponse().getStatus()==200){
                    if (feedItemList.size()-1==i){
                        activity.finish();
                    }
                    timerTxt.setText("1s");
                    isSelected=false;
                    activity.view_pager.setCurrentItem(i+1);
                }
            }

            @Override
            public void onFailure(Call<ReponseQuestionBank> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
}
