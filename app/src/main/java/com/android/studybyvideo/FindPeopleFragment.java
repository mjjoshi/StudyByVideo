package com.android.studybyvideo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;


public class FindPeopleFragment extends AppCompatActivity {

    public FindPeopleFragment() {
    }


    private ImageView imgBtnProfileBack;
    private TextView txtdescription, txtTitle;
    RelativeLayout relativeLayout;
    PDFView pdfView;
    ProgressBar progress_circular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.fragment_find_people);
        String description = getIntent().getStringExtra("url");
        String headretxt = getIntent().getStringExtra("HEADER");
        progress_circular = (ProgressBar) findViewById(R.id.progress_circular);
        txtdescription = (TextView) findViewById(R.id.description);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(headretxt);
        imgBtnProfileBack = (ImageView) findViewById(R.id.imgBtnProfileBack);
//        WebView wv = (WebView) this.findViewById(R.id.webview);
////        wv.getSettings().setJavaScriptEnabled(true);
//////        wv.loadDataWithBaseURL(null, description, "text/html", "utf-8", null);
//        wv.loadUrl(description);
//        pdfView=findViewById(R.id.pdfView);
//        Uri video = Uri.parse(description);
//        pdfView.fromUri(video);
        loadurl(description);
        imgBtnProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    WebView myWebView;
    public void loadurl(String url) {
        progress_circular.setVisibility(View.VISIBLE);
         myWebView = (WebView) findViewById(R.id.webview);
        Open_Api(url);
    }
    public void Open_Api(String file_path){


        if (myWebView==null){}else {

            myWebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + file_path);
            myWebView.setWebViewClient(new MyBrowser());
            myWebView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        progress_circular.setVisibility(View.GONE);

                    } else {
                        progress_circular.setVisibility(View.VISIBLE);

                    };

                }
            });
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
                        myWebView.goBack();
                        return true;
                    }

                    return false;
                }
            });
        }
    }
    public class MyBrowser extends WebViewClient {
        ProgressDialog pd;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress_circular.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress_circular.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

    }

}
