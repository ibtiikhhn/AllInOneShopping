package com.muibsols.allinoneshopping.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import com.muibsols.allinoneshopping.R;

import im.delight.android.webview.AdvancedWebView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class BrowserActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    public static final String TAG = "BROWSER";
    private AdvancedWebView mWebView;
    Intent receivedIntent;
    String url;

    ImageView backBT;
    ImageView homeBT;
    ImageView refreshBT;
    ImageView shareBT;
    ImageView exitBT;
    MaterialProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        initViews();
        receivedIntent = getIntent();
        url = receivedIntent.getStringExtra("url");

        mWebView.setListener(this, this);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setGeolocationEnabled(true);
        mWebView.loadUrl(url);

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mWebView.onBackPressed()) {
                    return;
                }
                finish();
            }
        });

        homeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadUrl(url);
            }
        });

        refreshBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.reload();
            }
        });

        shareBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        receivedIntent.getStringExtra("url"));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        exitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
    }

    @Override
    public void onExternalPageRequest(String url) {
    }

    void initViews() {
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        backBT = findViewById(R.id.backIM);
        homeBT = findViewById(R.id.homeIMG);
        refreshBT = findViewById(R.id.refreshIMG);
        shareBT = findViewById(R.id.shareIMG);
        exitBT = findViewById(R.id.exitBT);
        progressBar = findViewById(R.id.progressBar);
    }
}