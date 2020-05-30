package com.android.studybyvideo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.BuildConfig;
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

public class CoursesActivity1 extends AppCompatActivity {

    private ArrayList<CoursesPurchasedList> data = new ArrayList<>();
    private ArrayList<CoursesPurchasedList> data1 = new ArrayList<>();

    private String mobile = "";
    private String teacher_id = "";

    private View layout_Progress;
    private TextView txtCourseOffred, txtCoursePurchases;
    private AppCompatTextView tvAppVersion;
    private RecyclerView scheduleListing_elv, offered_recycle;
    private AppCompatImageView ivDrawer, ivTestSeries, ivReport, ivAboutUs, ivContactUs, ivLogout;
    private DrawerLayout drawerLayout;
    private LinearLayout llReport, llTestSeries, llAboutUs, llContactUs, llLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        layout_Progress = findViewById(R.id.layout_Progress);
        ivDrawer = findViewById(R.id.ivDrawer);
        drawerLayout = findViewById(R.id.drawer_layout_home);
        scheduleListing_elv = findViewById(R.id.scheduleListing_elv);
        offered_recycle = findViewById(R.id.offered_recycle);
        tvAppVersion = findViewById(R.id.tvAppVersion);
        txtCourseOffred = findViewById(R.id.txtCourseOffred);
        txtCoursePurchases = findViewById(R.id.txtCoursePurchases);
        llReport = findViewById(R.id.llReport);
        llTestSeries = findViewById(R.id.llTestSeries);
        llAboutUs = findViewById(R.id.llAboutUs);
        llContactUs = findViewById(R.id.llContactUs);
        llLogout = findViewById(R.id.llLogout);
        ivAboutUs = findViewById(R.id.ivAboutUs);
        ivContactUs = findViewById(R.id.ivContactUs);
        ivLogout = findViewById(R.id.ivLogout);
        tvAppVersion.setText(String.format("v%s", BuildConfig.VERSION_NAME));
        ivAboutUs.setColorFilter(Color.BLACK);
        ivLogout.setColorFilter(Color.BLACK);
        ivDrawer.setOnClickListener(view -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        llReport.setOnClickListener(view -> {
            closeNavigation();
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String client_id = sh.getString("client_id", "");
            Intent intent = new Intent(CoursesActivity1.this, ReportsList.class);
            intent.putExtra("client_id", client_id);
            startActivity(intent);
        });

        llTestSeries.setOnClickListener(view -> {
            closeNavigation();
            startActivity(new Intent(CoursesActivity1.this, TestType.class));
        });

        llAboutUs.setOnClickListener(view -> {
            closeNavigation();
            startActivity(new Intent(CoursesActivity1.this, AboutUs.class));
        });

        llContactUs.setOnClickListener(view -> {
            closeNavigation();
            startActivity(new Intent(CoursesActivity1.this, ContactUs.class));
        });

        llLogout.setOnClickListener(v -> {
            closeNavigation();
            new AlertDialog.Builder(CoursesActivity1.this, R.style.LightDialogTheme)
                    .setMessage("Are you sure you want to Logout ?")
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("mobile", "");
                            myEdit.commit();
                            startActivity(new Intent(CoursesActivity1.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        mobile = sh.getString("mobile", "");
        teacher_id = sh.getString("teacher_id", "");
        getScheduleListingData();
    }

    private void closeNavigation() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void getScheduleListingData() {

        layout_Progress.setVisibility(View.VISIBLE);
//        final ProgressDialog progressDialog = new ProgressDialog(CoursesActivity1.this);
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        Call<ResponseCoursesListModel> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getAllCourses("application/x-www-form-urlencoded", "getAllCourses", mobile, teacher_id);

        scheduleListingCall.enqueue(new Callback<ResponseCoursesListModel>() {
            @Override
            public void onResponse(Call<ResponseCoursesListModel> call, Response<ResponseCoursesListModel> response) {
                //progressDialog.dismiss();

                layout_Progress.setVisibility(View.GONE);
                if (response.body().getResponse().getStatus() == 200) {
                    txtCoursePurchases.setVisibility(View.VISIBLE);
                    txtCourseOffred.setVisibility(View.VISIBLE);
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
                Toast.makeText(CoursesActivity1.this, "SomeThing Went wrong",Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
                layout_Progress.setVisibility(View.GONE);
            }
        });
    }

    Dialog dialog1;

    public void show_update_dialog() {
        dialog1 = new Dialog(CoursesActivity1.this);
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoursesActivity1.this, LinearLayoutManager.HORIZONTAL, false);
            scheduleListing_elv.setLayoutManager(linearLayoutManager);
            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data, "PURCHASED");
            scheduleListing_elv.setAdapter(adapter);

        }
    }

    private void setAdapter1() {
//        data.get(0);
        if (data1.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoursesActivity1.this, LinearLayoutManager.HORIZONTAL, false);

            offered_recycle.setLayoutManager(linearLayoutManager);
            ScheduleListingAdapter adapter = new ScheduleListingAdapter(this, data1, "OFFERED");
            offered_recycle.setAdapter(adapter);

        }
    }


    public class ScheduleListingAdapter extends RecyclerView.Adapter<ScheduleListingAdapter.ViewHolder> {
        Context context;
        List<CoursesPurchasedList> scheduleListingList;
        List<CoursesPurchasedList> list;
        FragmentManager fragmentManager;
        String courseType;

        public ScheduleListingAdapter(Context context, List<CoursesPurchasedList> scheduleListingList, String courseType) {
            this.context = context;
            this.scheduleListingList = scheduleListingList;
            this.list = scheduleListingList;
            this.courseType = courseType;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lisy_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.txt_bookname.setText(scheduleListingList.get(position).getBookName());
            holder.book_des.setText(scheduleListingList.get(position).getBook_Des());
            if (scheduleListingList.get(position).getPrice() != null) {
                if (scheduleListingList.get(position).getPrice().equalsIgnoreCase("") && scheduleListingList.get(position).getPrice().equalsIgnoreCase("null")) {
                    holder.price.setVisibility(View.VISIBLE);
                    holder.price.setText("₹" + scheduleListingList.get(position).getPrice());
                } else {
                    holder.price.setVisibility(View.GONE);
                }
            } else {
                holder.price.setVisibility(View.GONE);
            }
            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CoursesActivity1.this, ChapterActivity1.class);
                    intent.putExtra("bookname", scheduleListingList.get(position).getBookName());
                    intent.putExtra("courseType", courseType);
                    startActivity(intent);
                }
            });
            Picasso.with(context).load(scheduleListingList.get(position).getBookImage()).placeholder(R.drawable.img_default).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return scheduleListingList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llmain, llnotes;
            TextView txt_bookname, book_des, price;
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                llmain = itemView.findViewById(R.id.llmain);
                txt_bookname = itemView.findViewById(R.id.txt_bookname);
                book_des = itemView.findViewById(R.id.book_des);
                price = itemView.findViewById(R.id.price);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}