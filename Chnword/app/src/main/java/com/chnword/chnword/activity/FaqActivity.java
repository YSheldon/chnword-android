package com.chnword.chnword.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.chnword.chnword.R;
import com.chnword.chnword.net.NetConf;

/**
 * Created by khtc on 15/9/14.
 */
public class FaqActivity extends Activity{

    private ImageButton backImageButton;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        webView = (WebView) findViewById(R.id.faq_webview);
        webView.loadUrl(NetConf.URL_FAQ);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
