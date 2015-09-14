package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Category;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONArray;
import org.json.JSONObject;

import io.vov.vitamio.utils.Log;


/**
 * Created by khtc on 15/8/27.
 */
public class FeedbackActivity extends Activity {

    private static final String TAG = FeedbackActivity.class.getSimpleName();

    private TextView feedtextinfo;
    private EditText feedbacktext, phoneNumber;

    private ProgressDialog progressDialog;

    private ImageButton infoSubmitButton;
    private ImageButton backImageButton;



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbacktext = (EditText) findViewById(R.id.feedbackText);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        infoSubmitButton = (ImageButton) findViewById(R.id.infoSubmitButton);
        infoSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(v);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public void onSubmit(View view) {
        Log.e(TAG, "ON SUBMIT");
        LocalStore store = new LocalStore(this);

        String content = this.feedbacktext.getText().toString().trim();

        if (!"".equalsIgnoreCase(content)) {
            String userid = store.getDefaultUser();
            String deviceId = DeviceUtil.getDeviceId(this);
            String contact = this.phoneNumber.getText().toString();
            JSONObject param = NetParamFactory.feedbackParam(userid, deviceId, content, contact);
            Log.e(TAG, param.toString());
            AbstractNet net = new VerifyNet(handler, param, NetConf.URL_FEEDBACK);
            progressDialog = ProgressDialog.show(this, "title", "loading");
            net.start();
        } else {
            Toast.makeText(this, "请输入文字", Toast.LENGTH_LONG).show();
        }



    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    Intent sucessInent = new Intent(FeedbackActivity.this, FeedSucessActivity.class);
                    startActivity(sucessInent);

                    finish();

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {
                    Toast.makeText(FeedbackActivity.this, "请求失败，请检查网络设置", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
