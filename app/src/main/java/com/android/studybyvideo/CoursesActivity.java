package com.android.studybyvideo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.CoursesPurchasedList;
import com.android.studybyvideo.model.ResponseCoursesListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesActivity extends AppCompatActivity {
    ArrayList<CoursesPurchasedList> data = new ArrayList<>();
    ArrayList<CoursesPurchasedList> data1 = new ArrayList<>();
    RecyclerView scheduleListing_elv, offered_recycle;
    ImageView address_back;
    String mobile = "";
    private String teacher_id = "";
    Button logout;
    LinearLayout llmenu, llprofile, llsetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_layout);
        //getSupportActionBar().hide();
        scheduleListing_elv = findViewById(R.id.scheduleListing_elv);
        offered_recycle = findViewById(R.id.offered_recycle);
        address_back = findViewById(R.id.address_back);
        logout = findViewById(R.id.logout);
        llmenu = findViewById(R.id.llmenu);
        llprofile = findViewById(R.id.tvTestSeries);
        llsetting = findViewById(R.id.llsetting);
        llsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String client_id = sh.getString("client_id", "");
                Intent intent = new Intent(CoursesActivity.this, ReportsList.class);
                intent.putExtra("client_id", client_id);
                startActivity(intent);
            }
        });
        llprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoursesActivity.this, TestType.class));
            }
        });
        llmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(CoursesActivity.this, view, Gravity.RIGHT);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setGravity(Gravity.RIGHT);


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(CoursesActivity.this,
                                "Clicked popup menu item " + item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        if (item.getTitle().toString().equalsIgnoreCase("Logout")) {
                            SharedPreferences sharedPreferences
                                    = getSharedPreferences("MySharedPref",
                                    MODE_PRIVATE);

                            SharedPreferences.Editor myEdit
                                    = sharedPreferences.edit();

                            myEdit.putString(
                                    "mobile",
                                    "");

                            myEdit.commit();
                            startActivity(new Intent(CoursesActivity.this, LoginActivity.class));
                            finish();
                        } else if (item.getTitle().toString().equalsIgnoreCase("About Us")) {
                            startActivity(new Intent(CoursesActivity.this, AboutUs.class));
                        } else if (item.getTitle().toString().equalsIgnoreCase("Contact Us")) {
                            startActivity(new Intent(CoursesActivity.this, ContactUs.class));

                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences
                        = getSharedPreferences("MySharedPref",
                        MODE_PRIVATE);


                SharedPreferences.Editor myEdit
                        = sharedPreferences.edit();

                myEdit.putString(
                        "mobile",
                        "");

                myEdit.commit();
                startActivity(new Intent(CoursesActivity.this, LoginActivity.class));
                finish();
            }
        });
        address_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);


        mobile = sh.getString("mobile", "");
        teacher_id = sh.getString("teacher_id", "");
        getScheduleListingData();
    }

    private void getScheduleListingData() {
        final ProgressDialog progressDialog = new ProgressDialog(CoursesActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseCoursesListModel> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getAllCourses(
                "application/x-www-form-urlencoded",
                "getAllCourses",
                mobile,
                teacher_id

        );
        scheduleListingCall.enqueue(new Callback<ResponseCoursesListModel>() {
            @Override
            public void onResponse(Call<ResponseCoursesListModel> call, Response<ResponseCoursesListModel> response) {
                progressDialog.dismiss();
                if (response.body().getResponse().getStatus() == 200) {
                    PackageInfo pInfo = null;
                    try {
                        pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    String version = "" + pInfo.versionCode;
                    String app_version = response.body().getResponse().getResult().getApp_version();
                    if (!app_version.equalsIgnoreCase(version)) {
                        show_update_dialog();
                    }

                    data = response.body().getResponse().getResult().getPurchased();
                    data1 = response.body().getResponse().getResult().getOffered();
                    if (data != null && data.size() > 0) {
                        setAdapter();
                    }
                    if (data1 != null && data1.size() > 0) {
                        setAdapter1();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseCoursesListModel> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    Dialog dialog1;

    public void show_update_dialog() {
        dialog1 = new Dialog(CoursesActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.update_dialog);
        dialog1.setCancelable(false);
        LinearLayout llupdate = dialog1.findViewById(R.id.llupdate);
        llupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://play.google.com/store/apps/details?id=" + getPackageName();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);

            }
        });


        dialog1.show();
        Window window = dialog1.getWindow();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private void setAdapter() {
//        data.get(0);
        if (data.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoursesActivity.this, LinearLayoutManager.HORIZONTAL, false);

            scheduleListing_elv.setLayoutManager(linearLayoutManager);

            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data);
            scheduleListing_elv.setAdapter(adapter);

        }
    }

    private void setAdapter1() {
//        data.get(0);
        if (data.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoursesActivity.this, LinearLayoutManager.HORIZONTAL, false);

            offered_recycle.setLayoutManager(linearLayoutManager);

            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data);
            offered_recycle.setAdapter(adapter);

        }
    }


    public class ScheduleListingAdapter extends RecyclerView.Adapter<ScheduleListingAdapter.ViewHolder> {
        Context context;
        List<CoursesPurchasedList> scheduleListingList;
        List<CoursesPurchasedList> list;
        FragmentManager fragmentManager;

        public ScheduleListingAdapter(Context context, List<CoursesPurchasedList> scheduleListingList) {
            this.context = context;
            this.scheduleListingList = scheduleListingList;
            this.list = scheduleListingList;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lisy_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.txt_bookname.setText(data.get(position).getBookName());
            holder.book_des.setText(data.get(position).getBook_Des());
            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CoursesActivity.this, ChapterACtivity.class);
                    intent.putExtra("bookname", data.get(position).getBookName());
                    startActivity(intent);
                }
            });
            Picasso.with(context).load(data.get(position).getBookImage()).placeholder(R.drawable.img_default).into(holder.image);


        }

        @Override
        public int getItemCount() {
            return scheduleListingList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llmain, llnotes;
            TextView txt_bookname, book_des;
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                llmain = itemView.findViewById(R.id.llmain);
                txt_bookname = itemView.findViewById(R.id.txt_bookname);
                book_des = itemView.findViewById(R.id.book_des);
                image = itemView.findViewById(R.id.image);

            }
        }
    }

}
