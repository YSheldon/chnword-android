package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.chnword.chnword.R;

/**
 * Created by khtc on 15/11/23.
 */
public class WebActivity extends Activity {
    private final static String TAG = WebActivity.class.getSimpleName();

    public static final String URL_KEY = "URL_KEY";
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString(URL_KEY);

        webView = (WebView) findViewById(R.id.webViewWeb);
        webView.loadUrl(url);

    }
}
