package com.example.sportszilla;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class feedback extends AppCompatActivity {
    ProgressBar progressBar;
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        appHeader();
        browser=(WebView)findViewById(R.id.webview);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        browser.setWebViewClient(new WebViewClient());
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSeMvA6ogQkOzAOngQ17hSiGC-NK4OdnsDMB0CLcQkVsYK1_0Q/viewform?vc=0&c=0&w=1&flr=0");
    }
    public void appHeader() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.contactappbar);
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public class WebViewClient extends android.webkit.WebViewClient{
        public void onPageStarted(WebView webView, String url, Bitmap favicon){
            super.onPageStarted(webView,url,favicon);
        }
        public boolean shouldOverrideUrlLoadting(WebView webView,String url){
            webView.loadUrl(url);
            return true;
        }
        public void onPageFinished(WebView view,String url){
            super.onPageFinished(view,url);
            progressBar.setVisibility(View.GONE);
        }

    }



}