package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ChapterList;
import com.android.studybyvideo.model.ResponseChapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterACtivity extends AppCompatActivity {
    ArrayList<ChapterList> data=new ArrayList<>();
    RecyclerView scheduleListing_elv;
    String bookname;
    ImageView address_back;
    ArrayList<String> status_list = new ArrayList<>();
    AppCompatSpinner spnr_status;
    LinearLayout llfree,llall;
    TextView txtall,txtfree,header;
    int selected_pos=0;
    LinearLayout llprofile,llvideo,llsetting;
    RelativeLayout footer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.course_list);
        bookname=getIntent().getStringExtra("bookname");
        scheduleListing_elv=findViewById(R.id.scheduleListing_elv);
        address_back=findViewById(R.id.address_back);
        footer=findViewById(R.id.footer);
        llfree=findViewById(R.id.llfree);
        llall=findViewById(R.id.llall);
        txtall=findViewById(R.id.txtall);
        txtfree=findViewById(R.id.txtfree);
        txtfree=findViewById(R.id.txtfree);
        header=findViewById(R.id.header);
        spnr_status=findViewById(R.id.spnr_status);
        llprofile=findViewById(R.id.tvTestSeries);
        llvideo=findViewById(R.id.llvideo);
        llsetting=findViewById(R.id.llsetting);
        header.setText(bookname);
        llprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChapterACtivity.this, QuestionBank.class);
                    intent.putExtra("chapter_id", data.get(selected_pos).getChapter_Id());
                    intent.putExtra("book_id", data.get(selected_pos).getBookID());
                    startActivity(intent);
                }
            });
            llvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChapterACtivity.this, VideoPlayer1.class);
                    intent.putExtra("chapter_id", data.get(selected_pos).getChapter_Id());
                    startActivity(intent);
                }
            });

        llsetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChapterACtivity.this, FindPeopleFragment.class);
                    intent.putExtra("url", data.get(selected_pos).getChapterNotes());
                    intent.putExtra("HEADER", data.get(selected_pos).getChapter_Name());
                    startActivity(intent);
                }
            });


        llfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llfree.setBackground(getResources().getDrawable(R.drawable.theme_radius_from_left));
                llall.setBackground(getResources().getDrawable(R.drawable.transparent));
                txtfree.setTextColor(getResources().getColor(R.color.color_white));
                txtall.setTextColor(getResources().getColor(R.color.theme_color_code));
            }
        });

        llall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llfree.setBackground(getResources().getDrawable(R.drawable.transparent));
                llall.setBackground(getResources().getDrawable(R.drawable.theme_radiusfrom_right));
                txtfree.setTextColor(getResources().getColor(R.color.theme_color_code));
                txtall.setTextColor(getResources().getColor(R.color.color_white));
            }
        });
        address_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        status_list.add("All");
        status_list.add("All");
        set_schedule_status();
        getScheduleListingData();
    }

    private void set_schedule_status() {
        final ArrayAdapter adapter = new ArrayAdapter<>(ChapterACtivity.this, android.R.layout.simple_spinner_item, status_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            holder.spnr_status.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
////                    holder.is_touch=true;
//                    return false;
//                }
//            });
         spnr_status.setEnabled(true);
         spnr_status.setAdapter(adapter);
         spnr_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    cleaning_time = time_list.get(i).toString();
                String str = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnr_status.setSelection(0);
    }


    private void getScheduleListingData() {
        final ProgressDialog progressDialog = new ProgressDialog(ChapterACtivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage( "Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseChapter> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getAllChapters(
                "application/x-www-form-urlencoded",
                "getAllChapters",
                bookname

        );
        scheduleListingCall.enqueue(new Callback<ResponseChapter>() {
            @Override
            public void onResponse(Call<ResponseChapter> call, Response<ResponseChapter> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    data.clear();
                    data = response.body().getResponse().getResult();
                    if(data!=null && data.size()>0) {
                        footer.setVisibility(View.VISIBLE);
                        data.get(0).setSelected(true);
                        setAdapter();
                    }else {
                        footer.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseChapter> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void setAdapter( ) {
//        data.get(0);
        if(data.size()>0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChapterACtivity.this, LinearLayoutManager.VERTICAL, false);

            scheduleListing_elv.setLayoutManager(linearLayoutManager);

            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data);
            scheduleListing_elv.setAdapter(adapter);

        }
    }



    public class ScheduleListingAdapter extends RecyclerView.Adapter<ScheduleListingAdapter.ViewHolder> {
        Context context;
        List<ChapterList> scheduleListingList;
        List<ChapterList> list;
        FragmentManager fragmentManager;

        public ScheduleListingAdapter(Context context, List<ChapterList> scheduleListingList) {
            this.context = context;
            this.scheduleListingList = scheduleListingList;
            this.list = scheduleListingList;
        }

        @NonNull
        @Override
        public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new  ViewHolder(LayoutInflater.from(context).inflate(R.layout.course_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final  ViewHolder holder, final int position) {
            holder.txt_title.setText(data.get(position).getChapter_Name());
            holder.txt_Des.setText(data.get(position).getChapter_des());
            if (data.get(position).isSelected()){
                holder.isSelected.setVisibility(View.VISIBLE);
            }else {
                holder.isSelected.setVisibility(View.GONE);
            }
            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0; i<data.size(); i++){
                        data.get(i).setSelected(false);
                    }
                    data.get(position).setSelected(true);
                    selected_pos=position;
                    notifyDataSetChanged();
                }
            });
            Picasso.with(context).load(data.get(position).getChapter_Image()).placeholder(R.drawable.img_default).into(holder.image);

        }

        @Override
        public int getItemCount() {
            return scheduleListingList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llvideo,llnotes,llmain;
            TextView txt_title;
            TextView txt_Des;
            TextView txt_q_bank;
            ImageView image,isSelected;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                isSelected =  itemView.findViewById(R.id.isSelected);
                llvideo =  itemView.findViewById(R.id.llvideo);
                txt_title =  itemView.findViewById(R.id.txt_title);
                txt_Des =  itemView.findViewById(R.id.txt_des);
                image=itemView.findViewById(R.id.image);
                llnotes=itemView.findViewById(R.id.llnotes);
                txt_q_bank=itemView.findViewById(R.id.txt_q_bank);
                llmain=itemView.findViewById(R.id.llmain);
            }
        }
    }
}
