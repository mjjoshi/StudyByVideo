package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ReponseReview;
import com.android.studybyvideo.model.ReponseReviewList;
import com.android.studybyvideo.model.ResponseSumbit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 5/8/2020.
 */
public class QuestionHistory extends AppCompatActivity {
    ArrayList<ReponseReviewList> data = new ArrayList<>();
    RecyclerView scheduleListing_elv;
    View layout_Progress;
    //    String bookname;
    ImageView address_back;
    ArrayList<String> status_list = new ArrayList<>();
    int selected_pos = 0;
    String id = "";
    CardView next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.review);
        id = getIntent().getStringExtra("id");
        scheduleListing_elv = findViewById(R.id.scheduleListing_elv);
        address_back = findViewById(R.id.address_back);
        layout_Progress = findViewById(R.id.layout_Progress);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuestionHistory.this, R.style.LightDialogTheme);
                alertDialogBuilder.setMessage("Do you really want to sumbit this test");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                submit();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        address_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getScheduleListingData(id);
    }


    private void getScheduleListingData(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(QuestionHistory.this);
//        progressDialog.setTitle(getResources().getString(R.string.QuestionHistory));
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        layout_Progress.setVisibility(View.VISIBLE);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String client_id = sh.getString("client_id", "");

        Call<ReponseReview> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getReviewedQuestionsList(
                "application/x-www-form-urlencoded",
                "getReviewedQuestionsList",
                client_id,
                id
        );
        scheduleListingCall.enqueue(new Callback<ReponseReview>() {
            @Override
            public void onResponse(Call<ReponseReview> call, Response<ReponseReview> response) {
                layout_Progress.setVisibility(View.GONE);
//                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    data.clear();
                    data = response.body().getResponse().getResult();
                    if (data != null && data.size() > 0) {
                        setAdapter();
                    } else {
                    }

                }
            }

            @Override
            public void onFailure(Call<ReponseReview> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                layout_Progress.setVisibility(View.GONE);
//                progressDialog.dismiss();
            }
        });
    }

    private void setAdapter() {
//        data.get(0);

        if (data.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuestionHistory.this, LinearLayoutManager.VERTICAL, false);

            scheduleListing_elv.setLayoutManager(linearLayoutManager);

            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data);
            scheduleListing_elv.setAdapter(adapter);
        }
    }


    private void submit() {
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
//        final ProgressDialog progressDialog = new ProgressDialog(QuestionHistory.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        layout_Progress.setVisibility(View.VISIBLE);

        Call<ResponseSumbit> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).submit(
                "application/x-www-form-urlencoded",
                "addAnswerOfQuestion",
                "0", "0",
                sharedPreferences.getString("mobile", ""), "",
                "",
                "", false, "", "", id, "YES", sharedPreferences.getString("client_id", "")

        );
        scheduleListingCall.enqueue(new Callback<ResponseSumbit>() {
            @Override
            public void onResponse(Call<ResponseSumbit> call, Response<ResponseSumbit> response) {
//                progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
                if (response.body().getResponse().getStatus() == 200) {
                    MyApplication.sumbitmodel = response.body().getResponse().getResult();
                    startActivity(new Intent(QuestionHistory.this, ResultScreen.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseSumbit> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                layout_Progress.setVisibility(View.GONE);
//                progressDialog.dismiss();
            }
        });
    }

    public class ScheduleListingAdapter extends RecyclerView.Adapter<ScheduleListingAdapter.ViewHolder> {
        Context context;
        List<ReponseReviewList> scheduleListingList;
        List<ReponseReviewList> list;

        public ScheduleListingAdapter(Context context, List<ReponseReviewList> scheduleListingList) {
            this.context = context;
            this.scheduleListingList = scheduleListingList;
            this.list = scheduleListingList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.review_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.txt_title.setText("Q. " + scheduleListingList.get(position).getQ_no());
            holder.txt_Question_name.setText("" + scheduleListingList.get(position).getQuestion());
            if (data.get(position).getIs_attempted().equalsIgnoreCase("ATTEMPTED")) {
                holder.llmain.setBackground(getDrawable(R.drawable.green_border));
            } else {
                holder.llmain.setBackground(getDrawable(R.drawable.transparent));

            }
            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestionHistory.this, QuestionTestSeries.class);
                    intent.putExtra("chapter_id", MyApplication.chapter_id);
                    intent.putExtra("time", MyApplication.tiime);
                    intent.putExtra("position", position);
                    startActivity(intent);
                    finish();
                }
            });
//            holder.txt_Des.setText(data.get(position).getChapter_des());
//            if (data.get(position).isSelected()){
//                holder.isSelected.setVisibility(View.VISIBLE);
//            }else {
//                holder.isSelected.setVisibility(View.GONE);
//            }
//            holder.llmain.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for (int i=0; i<data.size(); i++){
//                        data.get(i).setSelected(false);
//                    }
//                    data.get(position).setSelected(true);
//                    selected_pos=position;
//                    notifyDataSetChanged();
//                }
//            });
//            Picasso.with(context).load(data.get(position).getChapter_Image()).placeholder(R.drawable.img_default).into(holder.image);

        }

        @Override
        public int getItemCount() {
            return scheduleListingList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title, txt_Question_name;
            LinearLayout llmain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_Question_name = itemView.findViewById(R.id.txt_Question_name);
                txt_title = itemView.findViewById(R.id.txt_title);
                llmain = itemView.findViewById(R.id.llmain);
            }
        }
    }
}