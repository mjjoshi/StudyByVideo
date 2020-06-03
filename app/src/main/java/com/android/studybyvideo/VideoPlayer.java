package com.android.studybyvideo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.studybyvideo.ApiClient.ApiClient;
import com.android.studybyvideo.ApiClient.ApiInterface;
import com.android.studybyvideo.model.ResponseCoursesListModel;
import com.android.studybyvideo.model.ResponseVideo;
import com.google.android.exoplayer2.PlaybackPreparer;
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


public class VideoPlayer extends AppCompatActivity implements View.OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener {

    private static final String TAG = "MainActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static String VIDEO_URL = "";

    View mBottomLayout;
    View mVideoLayout;
    TextView mStart;
    RelativeLayout header;
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
    VideoView videoView;
    String chapter_id = "";
    ProgressBar progressBar;
    ImageView back_btn;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    TextView submit;
    EditText edt_q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        //getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Intent intent = getIntent();
        back_btn = findViewById(R.id.back_btn);
        submit = findViewById(R.id.submit);
        edt_q = findViewById(R.id.edt_q);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        VIDEO_URL= intent.getStringExtra("video_path");
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
        progressBar = findViewById(R.id.progressBar);
        videoView = findViewById(R.id.vdVw);
        playerView = findViewById(R.id.player_view);


        //Set MediaController  to enable play, pause, forward, etc options.

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
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        final ProgressDialog progressDialog = new ProgressDialog(VideoPlayer.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseBody> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).addQuestionByStudent(
                "application/x-www-form-urlencoded",
                "addQuestionByStudent",
                sharedPreferences.getString("client_id", ""), edt_qtxt, chapter_id

        );
        scheduleListingCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    edt_q.setText("");
//                    data = response.body().getResponse().getResult();
//                    if(data!=null && data.size()>0) {
//                        setAdapter();
//                    }
//                    if (response.body().getResponse().getResult() != null && response.body().getResponse().getResult().size() > 0) {
//                        start_video(response.body().getResponse().getResult().get(0).getChapter_azure_path());
//                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }


    private void getVideoOfChapter() {
        final ProgressDialog progressDialog = new ProgressDialog(VideoPlayer.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseVideo> scheduleListingCall = ApiClient.getClient().create(ApiInterface.class).getVideoOfChapter(
                "application/x-www-form-urlencoded",
                "getVideoOfChapter",
                chapter_id

        );
        scheduleListingCall.enqueue(new Callback<ResponseVideo>() {
            @Override
            public void onResponse(Call<ResponseVideo> call, Response<ResponseVideo> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
//                    data = response.body().getResponse().getResult();
//                    if(data!=null && data.size()>0) {
//                        setAdapter();
//                    }
                    if (response.body().getResponse().getResult() != null && response.body().getResponse().getResult().size() > 0) {
                        start_video(response.body().getResponse().getResult().get(0).getChapter_azure_path());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseVideo> call, Throwable t) {
                Log.e("ScheduleListing", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
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
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        //Starting VideView By Setting MediaController and URI
//        videoView.setMediaController(mediaController);
//        Uri video = Uri.parse(VIDEO_URL);
////        videoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
//        videoView.setVideoURI(video);
//        videoView.requestFocus();
//        videoView.start();
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                final MediaPlayer.OnInfoListener onInfoToPlayStateListener = new MediaPlayer.OnInfoListener() {
//
//                    @Override
//                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                        if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
//                            progressBar.setVisibility(View.GONE);
//                        }
//                        if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
//                            progressBar.setVisibility(View.VISIBLE);
//                        }
//                        if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
//                            progressBar.setVisibility(View.VISIBLE);
//                        }
//                        return false;
//                    }
//                };
//                videoView.setOnInfoListener(onInfoToPlayStateListener);
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
//        if (mVideoView != null && mVideoView.isPlaying()) {
//            mSeekPosition = mVideoView.getCurrentPosition();
//            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
//            mVideoView.pause();
//        }
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

    private void switchTitleBar(boolean show) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }


    @Override
    public void onBackPressed() {
//        if (this.isFullscreen) {
//            mVideoView.setFullscreen(false);
//        } else {
        super.onBackPressed();
//        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            header.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            header.setVisibility(View.VISIBLE);
            setStatusBarColor();
        }
    }

    public void setStatusBarColor() {

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

}
