package com.android.studybyvideo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ResponseTestName;
import com.android.studybyvideo.model.ResponseTestType;
import com.android.studybyvideo.model.TestNameList;
import com.android.studybyvideo.model.TestTypeList;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 5/8/2020.
 */
public class TestSeriesActivity extends AppCompatActivity {
    ArrayList<TestTypeList> data = new ArrayList<>();
    ArrayList<TestNameList> test_name_list = new ArrayList<>();
    RecyclerView scheduleListing_elv;
    ImageView address_back;
    ArrayList<String> status_list = new ArrayList<>();
    int selected_pos = 0;
    TextView header;
    RecyclerView rec_test_type_name;
    private String teacher_id = "";
    private String mobile = "";
    private View layout_Progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.test_type);
//        bookname=getIntent().getStringExtra("bookname");
        scheduleListing_elv = findViewById(R.id.scheduleListing_elv);
        rec_test_type_name = findViewById(R.id.rec_test_type_name);
        address_back = findViewById(R.id.address_back);
        layout_Progress = findViewById(R.id.layout_Progress);

        header = findViewById(R.id.header);
        address_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        mobile = sh.getString("mobile", "");
        teacher_id = sh.getString("teacher_id", "");
        getScheduleListingData();
    }


    private void getScheduleListingData() {
//        setAdapter();
//        final ProgressDialog progressDialog = new ProgressDialog(TestSeriesActivity.this);
////        progressDialog.setTitle(getResources().getString(R.string.QuestionHistory));
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        layout_Progress.setVisibility(View.VISIBLE);
        Call<ResponseTestType> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getTestTypes(
                "application/x-www-form-urlencoded",
                "getTestTypes",
                teacher_id

        );
        scheduleListingCall.enqueue(new Callback<ResponseTestType>() {
            @Override
            public void onResponse(Call<ResponseTestType> call, Response<ResponseTestType> response) {
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
                if (response.body().getResponse().getStatus() == 200) {
                    data.clear();
                    data = response.body().getResponse().getResult();
                    if (data != null && data.size() > 0) {
                        setAdapter();
                    } else {
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseTestType> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (rec_test_type_name.getVisibility() == View.VISIBLE) {
            rec_test_type_name.setVisibility(View.GONE);
            scheduleListing_elv.setVisibility(View.VISIBLE);
            setAdapter();
        } else {
            super.onBackPressed();
        }
    }

    private void gettestData(String name) {
//        setAdapter();
//        final ProgressDialog progressDialog = new ProgressDialog(TestSeriesActivity.this);
////        progressDialog.setTitle(getResources().getString(R.string.QuestionHistory));
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        layout_Progress.setVisibility(View.VISIBLE);
        Call<ResponseTestName> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getTestTypeNames(
                "application/x-www-form-urlencoded",
                "getTestTypeNames", name,mobile,teacher_id

        );
        scheduleListingCall.enqueue(new Callback<ResponseTestName>() {
            @Override
            public void onResponse(Call<ResponseTestName> call, Response<ResponseTestName> response) {
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
                if (response.body().getResponse().getStatus() == 200) {
                    test_name_list.clear();
                    test_name_list = response.body().getResponse().getResult();
                    if (test_name_list != null && test_name_list.size() > 0) {
                        setTestNameAdapter();
                    } else {
                        rec_test_type_name.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseTestName> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                rec_test_type_name.setVisibility(View.GONE);
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
            }
        });
    }

    private void setTestNameAdapter() {
        if (test_name_list.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TestSeriesActivity.this, LinearLayoutManager.VERTICAL, false);
            rec_test_type_name.setLayoutManager(linearLayoutManager);
            TestNameListingAdapter adapter = new TestNameListingAdapter(this, test_name_list);
            rec_test_type_name.setAdapter(adapter);
        }
    }

    private void setAdapter() {
        if (data.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TestSeriesActivity.this, LinearLayoutManager.VERTICAL, false);
            scheduleListing_elv.setLayoutManager(linearLayoutManager);
            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data);
            scheduleListing_elv.setAdapter(adapter);

        }
    }


    public class ScheduleListingAdapter extends RecyclerView.Adapter<ScheduleListingAdapter.ViewHolder> {
        Context context;
        List<TestTypeList> scheduleListingList;
        List<TestTypeList> list;
        FragmentManager fragmentManager;

        public ScheduleListingAdapter(Context context, List<TestTypeList> scheduleListingList) {
            this.context = context;
            this.scheduleListingList = scheduleListingList;
            this.list = scheduleListingList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.test_type_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.txt_title.setText(scheduleListingList.get(position).getTest_type());
//            holder.txt_Des.setText(data.get(position).getChapter_des());
//            if (data.get(position).isSelected()){
//                holder.isSelected.setVisibility(View.VISIBLE);
//            }else {
//                holder.isSelected.setVisibility(View.GONE);
//            }
            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gettestData(scheduleListingList.get(position).getTest_type());
                    rec_test_type_name.setVisibility(View.VISIBLE);
                    scheduleListing_elv.setVisibility(View.GONE);
                    header.setText("" + scheduleListingList.get(position).getTest_type());
                }
            });
//            Picasso.with(context).load(data.get(position).getChapter_Image()).placeholder(R.drawable.img_default).into(holder.image);

        }

        @Override
        public int getItemCount() {
            return scheduleListingList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title;
            LinearLayout llmain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_title = itemView.findViewById(R.id.txt_title);
                llmain = itemView.findViewById(R.id.llmain);
            }
        }
    }


    public class TestNameListingAdapter extends RecyclerView.Adapter<TestNameListingAdapter.ViewHolder> {
        Context context;
        List<TestNameList> scheduleListingList;
        List<TestNameList> list;
        FragmentManager fragmentManager;

        public TestNameListingAdapter(Context context, List<TestNameList> scheduleListingList) {
            this.context = context;
            this.scheduleListingList = scheduleListingList;
            this.list = scheduleListingList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.test_name_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.txt_title.setText(scheduleListingList.get(position).getTest_type_name());
            holder.txt_total_q.setText(scheduleListingList.get(position).getNo_of_mcqs() + " Q\'s");
            holder.txt_total_time.setText(scheduleListingList.get(position).getTime_for_the_test());
            holder.txt_created.setText("Live From 05/03/2020");
            if (scheduleListingList.get(position).getLock_status() != null) {
                if (scheduleListingList.get(position).getLock_status().equalsIgnoreCase("LOCK")) {
                    holder.lock.setVisibility(View.VISIBLE);
                } else {
                    holder.lock.setVisibility(View.GONE);
                }
            } else {
                holder.lock.setVisibility(View.GONE);
            }
//            holder.txt_Des.setText(data.get(position).getChapter_des());
//            if (data.get(position).isSelected()){
//                holder.isSelected.setVisibility(View.VISIBLE);
//            }else {
//                holder.isSelected.setVisibility(View.GONE);
//            }
            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_test_info(scheduleListingList.get(position));
                }
            });
//            Picasso.with(context).load(data.get(position).getChapter_Image()).placeholder(R.drawable.img_default).into(holder.image);

        }

        @Override
        public int getItemCount() {
            return scheduleListingList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title, txt_total_q, txt_total_time, txt_created;
            LinearLayout llmain;
            RelativeLayout lock;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_title = itemView.findViewById(R.id.txt_title);
                llmain = itemView.findViewById(R.id.llmain);
                txt_total_q = itemView.findViewById(R.id.txt_total_q);
                txt_total_time = itemView.findViewById(R.id.txt_total_time);
                txt_created = itemView.findViewById(R.id.txt_created);
                lock = itemView.findViewById(R.id.lock);
            }
        }
    }


    Dialog dialog1;

    public void show_test_info(TestNameList model) {

        dialog1 = new Dialog(TestSeriesActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.test_info);
        Button submit = dialog1.findViewById(R.id.submit);
        final TextView txt_test_name = dialog1.findViewById(R.id.txt_test_name);
        final TextView txt_total_no_q = dialog1.findViewById(R.id.txt_total_no_q);
        final TextView txt_time = dialog1.findViewById(R.id.txt_time);
        final TextView txt_negative = dialog1.findViewById(R.id.txt_negative);
        final TextView txt_negative_marking_info = dialog1.findViewById(R.id.txt_negative_marking_info);
        txt_test_name.setText("" + model.getTest_type_name());
        txt_total_no_q.setText("" + model.getNo_of_mcqs());
        txt_time.setText("" + model.getTime_for_the_test());
        txt_negative.setText("" + model.getNegative_marking());
        txt_negative_marking_info.setText("( Negative Marking for Each Wrong Question" + model.getNegative_marks() + " )");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestSeriesActivity.this, QuestionTestSeries.class);
                intent.putExtra("chapter_id", model.getTtname_id());
                intent.putExtra("time", model.getTime_for_the_test());
                intent.putExtra("position", 0);
                MyApplication.chapter_id = model.getTtname_id();
                MyApplication.tiime = model.getTime_for_the_test();
                startActivity(intent);
                dialog1.dismiss();
            }
        });


        dialog1.show();
        Window window = dialog1.getWindow();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
