package com.android.studybyvideo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.airbnb.lottie.LottieAnimationView;
import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ResponseVideo;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoPlayer1 extends AppCompatActivity implements View.OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener {

    private static final String TAG = "MainActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static String VIDEO_URL = "";
    private RelativeLayout header;
    private int mSeekPosition;

    private String chapter_id = "";
    private LottieAnimationView progressBar;
    private ImageView back_btn;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView submit;
    private EditText edt_q;
    private AppCompatButton btnAskQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Intent intent = getIntent();
        back_btn = findViewById(R.id.back_btn);
        submit = findViewById(R.id.submit);
        edt_q = findViewById(R.id.edt_q);
        btnAskQuestion = findViewById(R.id.btnAskQuestion);
        back_btn.setOnClickListener(v -> onBackPressed());
        chapter_id = intent.getStringExtra("chapter_id");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_q.getText().toString().length() > 0) {
                    addQuestionByStudent(edt_q.getText().toString());
                }
            }
        });


        header = findViewById(R.id.header);
        final ImageView back_btn = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progressBarLottie);
        playerView = findViewById(R.id.player_view);

        btnAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_q.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                btnAskQuestion.setVisibility(View.GONE);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getVideoOfChapter();
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void preparePlayback() {

    }

    @Override
    public void onVisibilityChange(int visibility) {


    }

    private void addQuestionByStudent(String edt_qtxt) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).addQuestionByStudent("application/x-www-form-urlencoded", "addQuestionByStudent",
                sharedPreferences.getString("client_id", ""), edt_qtxt, chapter_id);
        scheduleListingCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    edt_q.setText("");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void getVideoOfChapter() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseVideo> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getVideoOfChapter("application/x-www-form-urlencoded", "getVideoOfChapter", chapter_id);
        scheduleListingCall.enqueue(new Callback<ResponseVideo>() {
            @Override
            public void onResponse(Call<ResponseVideo> call, Response<ResponseVideo> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getResponse().getResult() != null && response.body().getResponse().getResult().size() > 0) {
                        start_video(response.body().getResponse().getResult().get(0).getChapter_azure_path());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseVideo> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void start_video(String VIDEO_URL) {
        playerView.setControllerVisibilityListener(this);
        playerView.requestFocus();
        player = new SimpleExoPlayer.Builder(/* context= */ this).build();
        playerView.setPlayer(player);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "VideoStreming"));
        SsMediaSource videoSource = new SsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(VIDEO_URL));
        player.prepare(videoSource);
        player.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    btnAskQuestion.setVisibility(View.VISIBLE);
                } else if (playWhenReady) {

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            Log.d(TAG, "onSaveInstanceState Position=" + player.getCurrentPosition());
            outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }


}
