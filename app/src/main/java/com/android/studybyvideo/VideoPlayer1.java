package com.android.studybyvideo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ChapterList;
import com.android.studybyvideo.model.ResponseVideo;
import com.google.android.exoplayer2.PlaybackParameters;
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

    //private String chapter_id = "";
    private ChapterList chapterData;
    private LottieAnimationView progressBar;
    private ImageView back_btn;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView submit, txt_title, txt_Des;
    private EditText edt_q;
    private AppCompatButton btnAskQuestion;
    private LinearLayout llQB, llNotes, llBottom;
    private ImageView fullscreenButton;
    private AppCompatTextView txts2, txts1;
    private boolean fullscreen = false;

    private ImageButton exo_rew, exo_ffwd;

    private PlaybackParameters param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Intent intent = getIntent();


        back_btn = findViewById(R.id.back_btn);
        submit = findViewById(R.id.submit);
        edt_q = findViewById(R.id.edt_q);
        btnAskQuestion = findViewById(R.id.btnAskQuestion);
        txt_title = findViewById(R.id.txt_title);
        txt_Des = findViewById(R.id.txt_des);
        llQB = findViewById(R.id.llQB);
        llNotes = findViewById(R.id.llNotes);
        back_btn.setOnClickListener(v -> onBackPressed());
        //chapter_id = intent.getStringExtra("chapter_id");
        chapterData = (ChapterList) intent.getSerializableExtra("chapter_id");
        if (chapterData != null) {
            txt_title.setText(chapterData.getChapter_Name());
            txt_Des.setText(chapterData.getChapter_des());
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_q.getText().toString().length() > 0) {
                    addQuestionByStudent(edt_q.getText().toString());
                }
            }
        });


        header = findViewById(R.id.header);
        llBottom = findViewById(R.id.llBottom);
        final ImageView back_btn = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progressBarLottie);
        playerView = findViewById(R.id.player_view);
        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
        txts2= playerView.findViewById(R.id.txts2);
        txts1= playerView.findViewById(R.id.txts1);
        exo_rew = playerView.findViewById(R.id.exo_rew);
        exo_ffwd = playerView.findViewById(R.id.exo_ffwd);

//        exo_ffwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });
//
//
//        exo_rew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                playerView.setRewindIncrementMs(10000);
//
//            }
//        });


        txts1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                param = new PlaybackParameters(1.0f);
                player.setPlaybackParameters(param);

            }
        });

        txts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                param = new PlaybackParameters(2.0f);
                player.setPlaybackParameters(param);

            }
        });


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

        llQB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoPlayer1.this, QuestionBank.class);
                intent.putExtra("chapter_id", chapterData.getChapter_Id());
                intent.putExtra("book_id", chapterData.getBookID());
                startActivity(intent);
            }
        });

        llNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoPlayer1.this, FindPeopleFragment.class);
                intent.putExtra("url", chapterData.getChapterNotes());
                intent.putExtra("HEADER", chapterData.getChapter_Name());
                startActivity(intent);
            }
        });


        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullscreen) {
                    llBottom.setVisibility(View.VISIBLE);
                    header.setVisibility(View.VISIBLE);
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(VideoPlayer1.this, R.drawable.ic_fullscreen));

                    VideoPlayer1.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);



                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    Point matrics = getScreenResolution(VideoPlayer1.this);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = dpToPx(280);
                    playerView.setLayoutParams(params);

                    fullscreen = false;
                } else {
                    llBottom.setVisibility(View.GONE);
                    header.setVisibility(View.GONE);
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(VideoPlayer1.this, R.drawable.ic_fullscreen_exit));

                    VideoPlayer1.this.getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    Point matrics = getScreenResolution(VideoPlayer1.this);

                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    playerView.setLayoutParams(params);

                    fullscreen = true;
                }
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
                sharedPreferences.getString("client_id", ""), edt_qtxt, chapterData.getChapter_Id());
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
        Call<ResponseVideo> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getVideoOfChapter("application/x-www-form-urlencoded", "getVideoOfChapter", chapterData.getChapter_Id());
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
        playerView.setFastForwardIncrementMs(10000);
        playerView.setRewindIncrementMs(10000);
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
        pausePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null)
            player.release();
    }

    private void pausePlayer() {
        if (player != null && player.isPlaying()) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void startPlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
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

    private Point getScreenResolution(Context context) {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point screenResolution = new Point();

        display.getRealSize(screenResolution);

        return screenResolution;
    }

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}