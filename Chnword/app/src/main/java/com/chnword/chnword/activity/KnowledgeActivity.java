package com.chnword.chnword.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chnword.chnword.R;

import org.w3c.dom.Text;

/**
 * Created by khtc on 15/9/15.
 */
public class KnowledgeActivity extends Activity {


    private View backImageButton;
    private TextView urlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        backImageButton = findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        urlTextView = (TextView) findViewById(R.id.urlTextView);
        urlTextView.setText(
                Html.fromHtml("<a href=\"http://www.baidu.com\">详情请浏览中聿华源官网。</a> "));
        urlTextView.setMovementMethod(LinkMovementMethod.getInstance());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
