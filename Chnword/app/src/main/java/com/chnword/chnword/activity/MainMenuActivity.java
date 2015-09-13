package com.chnword.chnword.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.chnword.chnword.R;

/**
 * Created by khtc on 15/9/13.
 */
public class MainMenuActivity extends Activity {

    private ImageButton btn_scan, btn_study;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        btn_scan = (ImageButton) findViewById(R.id.btn_scan);
        btn_study = (ImageButton) findViewById(R.id.btn_study);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
