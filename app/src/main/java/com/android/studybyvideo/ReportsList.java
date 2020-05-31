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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ResponseReports;
import com.android.studybyvideo.model.ResponseSumbit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 5/10/2020.
 */
public class ReportsList extends AppCompatActivity {
    ArrayList<com.android.studybyvideo.model.ReportsList> data = new ArrayList<>();
    RecyclerView scheduleListing_elv;
    //    String bookname;
    ImageView address_back;
    ArrayList<String> status_list = new ArrayList<>();
    int selected_pos = 0;
    String id = "";
    TextView next;
    String client_id;
    private View layout_Progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.reports_list);
        client_id = getIntent().getStringExtra("client_id");
        scheduleListing_elv = findViewById(R.id.scheduleListing_elv);
        address_back = findViewById(R.id.address_back);
        next = findViewById(R.id.next);
        layout_Progress = findViewById(R.id.layout_Progress);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReportsList.this, R.style.LightDialogTheme);
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
//        final ProgressDialog progressDialog = new ProgressDialog(ReportsList.this);
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        layout_Progress.setVisibility(View.VISIBLE);
        Call<ResponseReports> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getTestResultList(
                "application/x-www-form-urlencoded",
                "getTestResultList",
                client_id

        );
        scheduleListingCall.enqueue(new Callback<ResponseReports>() {
            @Override
            public void onResponse(Call<ResponseReports> call, Response<ResponseReports> response) {
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
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
            public void onFailure(Call<ResponseReports> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
            }
        });
    }

    private void setAdapter() {
        if (data.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReportsList.this, LinearLayoutManager.VERTICAL, false);
            scheduleListing_elv.setLayoutManager(linearLayoutManager);
            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data);
            scheduleListing_elv.setAdapter(adapter);

        }
    }


    private void submit() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        final ProgressDialog progressDialog = new ProgressDialog(ReportsList.this);
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
               // progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
                if (response.body().getResponse().getStatus() == 200) {
                    MyApplication.sumbitmodel = response.body().getResponse().getResult();
                    startActivity(new Intent(ReportsList.this, ResultScreen.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseSumbit> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                layout_Progress.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });
    }

    public class ScheduleListingAdapter extends RecyclerView.Adapter<ScheduleListingAdapter.ViewHolder> {
        Context context;
        List<com.android.studybyvideo.model.ReportsList> scheduleListingList;
        List<com.android.studybyvideo.model.ReportsList> list;
        FragmentManager fragmentManager;

        public ScheduleListingAdapter(Context context, List<com.android.studybyvideo.model.ReportsList> scheduleListingList) {
            this.context = context;
            this.scheduleListingList = scheduleListingList;
            this.list = scheduleListingList;


        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.report_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.txt_test_name.setText("" + scheduleListingList.get(position).getTest_name());

            holder.txt_marks.setText(data.get(position).getMarks());
            holder.txt_per.setText(data.get(position).getPercentage());
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
            TextView txt_test_name, txt_marks, txt_per;
            LinearLayout llmain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_test_name = itemView.findViewById(R.id.txt_test_name);
                txt_marks = itemView.findViewById(R.id.txt_marks);
                txt_per = itemView.findViewById(R.id.txt_per);
                llmain = itemView.findViewById(R.id.llmain);
            }
        }
    }
}
