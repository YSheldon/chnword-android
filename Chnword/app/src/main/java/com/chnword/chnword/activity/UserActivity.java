package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.store.LocalStore;

/**
 * Created by khtc on 15/6/16.
 */
public class UserActivity extends Activity {

    private WebView webView;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        webView = (WebView) findViewById(R.id.webView);
        textView = (TextView) findViewById(R.id.tab_user_code);

        LocalStore store = new LocalStore(this);
        textView.setText(store.getDefaultUser());

//        File f = getActivity().getAssets();
        //todo 看是否可用
        webView.loadUrl("file:///android_asset/guide.html");

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
