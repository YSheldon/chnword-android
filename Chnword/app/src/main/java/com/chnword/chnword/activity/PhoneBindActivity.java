package com.chnword.chnword.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chnword.chnword.R;

/**
 * Created by khtc on 15/9/14.
 */
public class PhoneBindActivity extends Activity {

    private ImageButton backImageButton;

    private EditText phoneNumber;
    private EditText verifyNumber;

    private ImageButton sendButton, verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebind);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        verifyNumber = (EditText) findViewById(R.id.verifyNumber);

        sendButton = (ImageButton) findViewById(R.id.sendButton);
        verifyButton = (ImageButton) findViewById(R.id.verifyButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonClicked();
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyButtonClicked();
            }
        });

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

    private void sendButtonClicked() {

    }

    private void verifyButtonClicked() {

    }


}
