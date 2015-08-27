package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chnword.chnword.R;

/**
 * Created by khtc on 15/8/27.
 */
public class WebActivity extends Activity {

    TextView textView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle bundle = getIntent().getExtras();

        String title = bundle.getString("title");
        String url = bundle.getString("url");

        textView = (TextView) findViewById(R.id.titleLable);
        webView = (WebView) findViewById(R.id.webViewIden);

        textView.setText(title);
        webView.loadUrl(url);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
