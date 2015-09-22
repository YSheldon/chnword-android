package com.chnword.chnword.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chnword.chnword.R;
import com.chnword.chnword.beans.Word;
import com.chnword.chnword.net.AbstractNet;
import com.chnword.chnword.net.DeviceUtil;
import com.chnword.chnword.net.NetConf;
import com.chnword.chnword.net.NetParamFactory;
import com.chnword.chnword.net.VerifyNet;
import com.chnword.chnword.store.LocalStore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khtc on 15/9/15.
 */
public class FreewordActivity extends Activity {
    private static final String TAG = FreewordActivity.class.getSimpleName();

    private ImageButton backImageButton;
    private ProgressDialog progressDialog;
    private Word currentWord;
    private LocalStore store;

    String zoneCode = "";
    String title = "";
    int index = 0;

    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeword);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//回退
            }
        });

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        store = new LocalStore(this);

        zoneCode = b.getString("ZoneCode");
        index = b.getInt("ZoneIndex");

        title = b.getString("module_name", "");
        if ("".equalsIgnoreCase(title)){
            Log.e(TAG, "NO TITLE, BACK");
            finish();
        }
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(title);


        requestNet(zoneCode);

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

    private void requestNet(String zoneCode) {
        String userid = store.getDefaultUser();
        String deviceId = DeviceUtil.getDeviceId(this);
        JSONObject param = NetParamFactory.sharedWordParam(userid, deviceId);
        AbstractNet net = new VerifyNet(handler, param, NetConf.URL_SHARED);
        progressDialog = ProgressDialog.show(this, "提示", "loading...");
        net.start();
    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            progressDialog = null;
            try {
                if (msg.what == AbstractNet.NETWHAT_SUCESS)
                {
                    Bundle b = msg.getData();
                    String str = b.getString("responseBody");
                    Log.e(TAG, str);
                    JSONObject obj = new JSONObject(str);
                    JSONArray wordArray = obj.getJSONArray("data");

                    LocalStore store = new LocalStore(FreewordActivity.this);


                    if (wordArray != null && wordArray.length() > 0) {
                        JSONObject wordObj = wordArray.getJSONObject(0);
                        Word word = new Word();
                        word.setWord(wordObj.getString("word"));
                        word.setWordIndex(wordObj.getString("unicode"));
                        Log.e(TAG, word.getWord() + " " + word.getWordIndex());
                        currentWord = word;
                    }

                }

                if (msg.what == AbstractNet.NETWHAT_FAIL) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
